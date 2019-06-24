/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */


import edu.princeton.cs.algs4.Queue;

public class Board implements Comparable<Board> {
    private int n;
    private int[][] tiles;
    private int[] zeroPos;
    private int[][] goalTiles;
    private Board twin = null;
    private Board parent = null;
    private Iterable<Board> neighbors = null;
    private String series;
    private String goal;
    private int hamming = -1;
    private int manhattan = -1;
    // private int priority = -1;
    private int moves = 0;

    public Board(int[][] blocks) {
        // construct a board from an n-by-n array of blocks
        // (where blocks[i][j] = block in row i, column j)
        n = blocks[0].length;
        tiles = new int[n][n];
        goalTiles = new int[n][n];
        zeroPos = new int[2];
        StringBuilder seriesBuilder = new StringBuilder();
        StringBuilder goalBuilder = new StringBuilder();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = blocks[i][j];
                goalTiles[i][j] = i*n+j+1;
                seriesBuilder.append("|"+String.valueOf(blocks[i][j]));
                if (blocks[i][j] == 0) {
                    zeroPos[0] = i;
                    zeroPos[1] = j;
                }
            }
        }
        goalTiles[n-1][n-1] = 0;
        this.series = seriesBuilder.toString();
        for (int i = 1; i < n*n; i++) {
            goalBuilder.append("|"+String.valueOf(i));
        }
        goalBuilder.append("|0");
        this.goal = goalBuilder.toString();
        this.manhattan();
    }

    public String getSeries() {
        // get id of certain board
        return series;
    }

    public void setMoves(int moves) {
        this.moves = moves;
    }

    public int getMoves() {
        return this.moves;
    }

    public int dimension() {
        // board dimension n
        return n;
    }

    public int hamming() {
        // number of blocks out of place
        if (this.hamming == -1) {
            int ham = 0;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (this.tiles[i][j] != this.goalTiles[i][j] && this.tiles[i][j]!=0) {
                        ham++;
                    }
                }
            }
            this.hamming = ham;
        }
        return this.hamming;
    }

    public int manhattan() {
        // sum of Manhattan distances between blocks and goal
        if (this.manhattan == -1) {
            int manh = 0;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (this.tiles[i][j] != this.goalTiles[i][j] && this.tiles[i][j] !=0) {
                        int iTar = (int) tiles[i][j]/n;
                        int jTar = tiles[i][j] % n - 1 ;
                        manh += Math.abs(i-iTar) + Math.abs(j-jTar);
                    }
                }
            }
            this.manhattan = manh;
        }
        return this.manhattan;
    }

    public boolean isGoal() {
        // is this board the goal board?
        if (this.series.equals(this.goal)) {
            return true;
        }
        return false;
    }

    public Board twin() {
        // a board that is obtained by exchanging any pair of blocks
        if (twin == null) {
            int[][] twinTiles = new int[n][n];
            int first = -1;
            int[] pos = new int[2];
            int tmp = 0;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    twinTiles[i][j] = this.tiles[i][j];
                    if (twinTiles[i][j] != 0) {
                        if (first == -1) {
                            tmp = twinTiles[i][j];
                            pos[0] = i;
                            pos[1] = j;
                            first = 1;
                        } else if (first == 1) {
                            twinTiles[i][j] = tmp;
                            twinTiles[pos[0]][pos[1]] = this.tiles[i][j];
                            first = 0;
                        }
                    }
                }
            }
            this.twin = new Board(twinTiles);
        }
        return twin;
    }

    public boolean equals(Object anObject) {
        // does this board equal y?
        if (this == anObject) {
            return true;
        }
        if (anObject instanceof Board) {
            Board anotherBoard = (Board) anObject;
            String id = this.series;
            if (id.equals(anotherBoard.series)) {
                return true;
            }
        }
        return false;
    }

    public Board getParent() {
        return this.parent;
    }

    public Iterable<Board> neighbors() {
        // all neighboring boards
        if (neighbors == null) {
            Queue<Board> neigh = new Queue<>();
            int i0 = zeroPos[0];
            int j0 = zeroPos[1];
            if (i0-1 >= 0) {
                Board tmpBoard = new Board(swapedTile(i0, j0, i0-1, j0));
                tmpBoard.setMoves(this.moves + 1);
                tmpBoard.parent = this;
                neigh.enqueue(tmpBoard);
            }
            if (i0+1 < n) {
                Board tmpBoard = new Board(swapedTile(i0, j0, i0+1, j0));
                tmpBoard.setMoves(this.moves + 1);
                tmpBoard.parent = this;
                neigh.enqueue(tmpBoard);
            }
            if (j0-1 >= 0) {
                Board tmpBoard = new Board(swapedTile(i0, j0, i0, j0-1));
                tmpBoard.setMoves(this.moves + 1);
                tmpBoard.parent = this;
                neigh.enqueue(tmpBoard);
            }
            if (j0+1 < n) {
                Board tmpBoard = new Board(swapedTile(i0, j0, i0, j0+1));
                tmpBoard.setMoves(this.moves + 1);
                tmpBoard.parent = this;
                neigh.enqueue(tmpBoard);
            }
            neighbors = neigh;
        }
        return neighbors;
    }

    public String toString() {
        // string representation of this board (in the output format specified below)
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    private int[][] swapedTile(int i0, int j0, int i1, int j1) {
        int[][] tmpTiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tmpTiles[i][j] = tiles[i][j];
            }
        }
        tmpTiles[i0][j0] = tiles[i1][j1];
        tmpTiles[i1][j1] = tiles[i0][j0];
        return tmpTiles;
    }

    public static void main(String[] args) {
        // unit tests (not graded)
    }

    public int getPriority() {
        return this.manhattan() + this.moves;
    }

    @Override
    public int compareTo(Board anotherBoard) {
        if (this.getPriority() > anotherBoard.getPriority()) {
            return 1;
        }
        if (this.getPriority() < anotherBoard.getPriority()) {
            return -1;
        }
        return 0;
    }
}
