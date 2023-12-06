
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

/**
 * I, Maksim Pavlicic, attest that this code is my original work and was written in compliance with the class Academic
 * Integrity and Collaboration Policy found in the syllabus.
 */

public class Solver {

    private final SearchNode finalNode;

    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }

        MinPQ<SearchNode> gameTree = new MinPQ<>();
        MinPQ<SearchNode> twinTree = new MinPQ<>();

        SearchNode initialNode = new SearchNode(initial, 0, null);
        SearchNode twinInitial = new SearchNode(initial.twin(), 0, null);
        //this.initialNode = initialNode;

        gameTree.insert(initialNode);
        twinTree.insert(twinInitial);

        SearchNode deletedNode = gameTree.delMin();
        SearchNode twinDeleted = twinTree.delMin();

        while (!deletedNode.nodeBoard.isGoal() && !twinDeleted.nodeBoard.isGoal()) {
            for (Board neighbor : deletedNode.nodeBoard.neighbors()) {
                if (deletedNode.numMoves == 0) {
                    gameTree.insert(new SearchNode(neighbor, deletedNode.numMoves + 1, deletedNode));
                } else if (!neighbor.equals(deletedNode.previousNode.nodeBoard)) {
                    gameTree.insert(new SearchNode(neighbor, deletedNode.numMoves + 1, deletedNode));
                }
            }
            deletedNode = gameTree.delMin();

            for (Board neighbor : twinDeleted.nodeBoard.neighbors()) {
                if (twinDeleted.numMoves == 0) {
                    twinTree.insert(new SearchNode(neighbor, twinDeleted.numMoves + 1, twinDeleted));
                } else if (!neighbor.equals(twinDeleted.previousNode.nodeBoard)) {
                    twinTree.insert(new SearchNode(neighbor, twinDeleted.numMoves + 1, twinDeleted));
                }
            }
            twinDeleted = twinTree.delMin();
        }
        
        finalNode = deletedNode;
    }

    public boolean isSolvable() {
        return finalNode.nodeBoard.isGoal();
    }

    public int moves() {
        if (!isSolvable()) {
            return -1;
        }

        return finalNode.numMoves;
    }

    public Iterable<Board> solution() {
        if (!isSolvable()) {
            return null;
        }

        ArrayList<Board> solutionSet = new ArrayList<>();
        SearchNode currentNode = finalNode;

        while (currentNode != null) {
            solutionSet.add(0, currentNode.nodeBoard);
            currentNode = currentNode.previousNode;
        }

        return solutionSet;
    }

    private class SearchNode implements Comparable<SearchNode>{
        private final Board nodeBoard;
        private final int numMoves;
        private final SearchNode previousNode;
        private final int priority;

        public SearchNode(Board board, int moves, SearchNode node) {
            nodeBoard = board;
            numMoves = moves;
            previousNode = node;
            priority = nodeBoard.manhattan() + numMoves;

        }

        @Override
        public int compareTo(SearchNode other) {
            return (this.priority - other.priority);
        }
    }

    // test client (see below)
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

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
