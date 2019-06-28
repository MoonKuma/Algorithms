/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

    private class Node implements Comparable<Node> {


        private Node parent;
        private int moves;
        private int priority;
        private Board board;

        public Node(Board board, Node parent, int moves) {
            this.board = board;
            this.parent = parent;
            this.moves = moves;
            this.priority = this.moves + this.board.manhattan();
        }

        private boolean isGoal() {
            return board.isGoal();
        }

        private Iterable<Node> getNeighbors() {
            Queue<Node> queue = new Queue<>();
            for (Board neigh:board.neighbors()) {
                if (parent == null || !neigh.equals(parent.board)) {
                    queue.enqueue(new Node(neigh, this, moves+1));
                }
            }
            return queue;
        }

        private Iterable<Board> traceBackParents() {
            Stack<Board> stack = new Stack<>();
            Node node = this;
            stack.push(node.board);
            while (node.parent != null) {
                node = node.parent;
                stack.push(node.board);
            }
            return stack;
        }

        @Override
        public int compareTo(Node otherNode) {
            return Integer.compare(this.priority, otherNode.priority);
        }
    }

    private Node lastNode;
    private boolean solvable = false;

    public Solver(Board initial) {
        // find a solution to the initial board (using the A* algorithm)
        if (initial == null) throw new IllegalArgumentException();
        MinPQ<Node> initialMinPQ = new MinPQ<>();
        MinPQ<Node> twinMinPQ = new MinPQ<>();
        initialMinPQ.insert(new Node(initial, null, 0));
        twinMinPQ.insert(new Node(initial.twin(), null, 0));
        while (!initialMinPQ.isEmpty()) {
            Node topNode = initialMinPQ.delMin();
            Node topNodeTwin = twinMinPQ.delMin();
            if (topNode.isGoal()) {
                lastNode = topNode;
                solvable = true;
                break;
            }
            if (topNodeTwin.isGoal()) {
                lastNode = null;
                solvable = false;
                break;
            }
            for (Node node:topNode.getNeighbors()) {
                initialMinPQ.insert(node);
            }
            for (Node node:topNodeTwin.getNeighbors()) {
                twinMinPQ.insert(node);
            }
        }
    }

    public boolean isSolvable() {
        // is the initial board solvable?
        return solvable;
    }

    public int moves() {
        // min number of moves to solve initial board; -1 if unsolvable
        if (!isSolvable()) return -1;
        return lastNode.moves;
    }

    public Iterable<Board> solution() {
        // sequence of boards in a shortest solution; null if unsolvable
        if (!isSolvable()) return null;
        return lastNode.traceBackParents();
    }

    public static void main(String[] args) {
        // solve a slider puzzle (given below)
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}
