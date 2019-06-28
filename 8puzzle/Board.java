/* *****************************************************************************
 *  Name: MoonKuma
 *  Date: 2019-06-26
 *  Description: Board elements
 **************************************************************************** */


import edu.princeton.cs.algs4.Queue;

public class Board {
    private int n;
    private int[][] tiles;
    // private int[] zeroPos;
    // private String series;
    private int hamming = -1;
    private int manhattan = -1;

    public Board(int[][] blocks) {
        // construct a board from an n-by-n array of blocks
        // (where blocks[i][j] = block in row i, column j)
        n = blocks[0].length;
        tiles = new int[n][n];
        int[][] goalTiles = new int[n][n];
        // zeroPos = new int[2];
        // StringBuilder seriesBuilder = new StringBuilder();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = blocks[i][j];
                goalTiles[i][j] = i*n+j+1;
                // seriesBuilder.append("|"+String.valueOf(blocks[i][j]));
            }
        }
        goalTiles[n-1][n-1] = 0;
        // this.series = seriesBuilder.toString();
        this.setManhattan(goalTiles);
        this.setHamming(goalTiles);

    }

    // private String getSeries() {
    //     // get id of certain board
    //     return series;
    // }

    // private void setMoves(int moves) {
    //     this.moves = moves;
    // }
    //
    // private int getMoves() {
    //     return this.moves;
    // }

    public int dimension() {
        // board dimension n
        return n;
    }

    public int hamming() {
        return this.hamming;
    }

    private void setHamming(int[][] goalTiles) {
        // number of blocks out of place
        if (this.hamming == -1) {
            int ham = 0;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (this.tiles[i][j] != goalTiles[i][j] && this.tiles[i][j]!=0) {
                        ham++;
                    }
                }
            }
            this.hamming = ham;
        }
    }

    public int manhattan() {
        return this.manhattan;
    }

    private void setManhattan(int[][] goalTiles) {
        // sum of Manhattan distances between blocks and goal
        if (this.manhattan == -1) {
            int manh = 0;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (this.tiles[i][j] != goalTiles[i][j] && this.tiles[i][j] != 0) {
                        int iTar = tiles[i][j] % n == 0 ? (tiles[i][j]/n-1) : (tiles[i][j]/n);
                        int jTar = tiles[i][j] % n == 0 ? (n - 1) : (tiles[i][j] % n-1);
                        manh += Math.abs(i-iTar) + Math.abs(j-jTar);
                    }
                }
            }
            this.manhattan = manh;
        }
    }

    public boolean isGoal() {
        // is this board the goal board?
        // if (this.series.equals(this.goal)) {
        //     return true;
        // }
        if (this.manhattan() == 0) return true;
        return false;
    }

    public Board twin() {
        // a board that is obtained by exchanging any pair of blocks
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
        return new Board(twinTiles);
    }

    public boolean equals(Object anObject) {
        // does this board equal y?
        if (this == anObject) {
            return true;
        }
        if (anObject == null) return false;
        if (anObject.getClass() == this.getClass()) {
            Board anotherBoard = (Board) anObject;
            if (anotherBoard.n != this.n) return false;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (this.tiles[i][j] != anotherBoard.tiles[i][j]) return false;
                }
            }
            return true;
        }
        return false;
    }

    // private Board getParent() {
    //     return this.parent;
    // }

    public Iterable<Board> neighbors() {
        // all neighboring boards
        Queue<Board> neigh = new Queue<>();
        int i0 = 0;
        int j0 = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    i0 = i;
                    j0 = j;
                }
            }
        }

        if (i0+1 < n) {
            Board tmpBoard = new Board(swapedTile(i0, j0, i0+1, j0));
            neigh.enqueue(tmpBoard);
        }

        if (j0+1 < n) {
            Board tmpBoard = new Board(swapedTile(i0, j0, i0, j0+1));
            neigh.enqueue(tmpBoard);
        }

        if (i0-1 >= 0) {
            Board tmpBoard = new Board(swapedTile(i0, j0, i0-1, j0));
            neigh.enqueue(tmpBoard);
        }

        if (j0-1 >= 0) {
            Board tmpBoard = new Board(swapedTile(i0, j0, i0, j0-1));
            neigh.enqueue(tmpBoard);
        }


        return neigh;
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

    // private int getPriority() {
    //     return this.manhattan() + this.moves;
    // }

    // @Override
    // public int compareTo(Board anotherBoard) {
    //     if (this.getPriority() > anotherBoard.getPriority()) {
    //         return 1;
    //     }
    //     if (this.getPriority() < anotherBoard.getPriority()) {
    //         return -1;
    //     }
    //     return 0;
    // }
}
