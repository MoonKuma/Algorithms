/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;

import java.util.HashSet;

public class Solver {

    private Board lastBoard;

    public Solver(Board initial){
        // find a solution to the initial board (using the A* algorithm)
        lastBoard = solve(initial);

    }
    public boolean isSolvable(){
        // is the initial board solvable?
        return lastBoard!=null;
    }
    public int moves() {
        // min number of moves to solve initial board; -1 if unsolvable
        if( isSolvable() ) {
            return lastBoard.getMoves();
        }
        return -1;
    }
    public Iterable<Board> solution(){
        // sequence of boards in a shortest solution; null if unsolvable
        return null;
    }
    public static void main(String[] args){
        // solve a slider puzzle (given below)
    }

    private Board solve(Board initial) {
        Queue<Board> solution = new Queue<Board>();
        MinPQ<Board> minPQ = new MinPQ<>();
        HashSet<Board> visited = new HashSet<Board>();
        Board last = initial;
        int currentMaxPriority = initial.getPriority()+1;
        minPQ.insert(initial);
        visited.add(initial);
        while (!minPQ.isEmpty()) {
            Board topBoard = minPQ.delMin();
            last = topBoard;
            solution.enqueue(topBoard);
            if (topBoard.isGoal()) {
                break;
            }
            currentMaxPriority = topBoard.getPriority();
            for (Board neigh:topBoard.neighbors()) {
                if (!visited.contains(neigh)) {
                    minPQ.insert(neigh);
                    visited.add(neigh);
                }
            }
        }
        if (last.isGoal()) {
            return last;
        } else {
            return null;
        }
    }
}
