
import java.util.ArrayList;

/**
 * I, Maksim Pavlicic, attest that this code is my original work and was written in compliance with the class Academic
 * Integrity and Collaboration Policy found in the syllabus.
 */

public class Board {

    private final short[][] board;
    private final int dimension;

    public Board(int[][] tiles) {
        dimension = tiles.length;
        board = new short[dimension][dimension];

        for (int r = 0; r < tiles.length; r++) {
            for (int c = 0; c < tiles.length; c++) {
                board[r][c] = (short) tiles[r][c];
            }
        }

    }

    public String toString() {
        String result = "";
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board.length; c++) {
                result += " " + board[r][c];
            }
            result += "\n";
        }

        return dimension + "\n" + result;
    }

    public int dimension() {
        return dimension;
    }

    public int hamming() {
        int distance = 0;
        short count = 0;
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board.length; c++) {
                count++;
                if (board[r][c] != 0 && board[r][c] != count) {
                    distance++;
                }
            }
        }

        return distance;
    }

    public int manhattan() {
        int goalRow;
        int goalCol;
        int manhattenDistance = 0;

        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board.length; c++) {
                if (board[r][c] != 0) {
                    goalRow = (board[r][c] - 1) / board.length;
                    goalCol = ((board[r][c] - 1) % board.length);

                    manhattenDistance += Math.abs(r - goalRow) + Math.abs(c - goalCol);
                }

            }
        }

        return manhattenDistance;
    }

    public boolean isGoal() {
        short[][] goalBoard = new short[dimension][dimension];
        short count = 0;
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board.length; c++) {
                count++;
                goalBoard[r][c] = count;
            }
        }
        goalBoard[dimension - 1][dimension - 1] = 0;

        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board.length; c++) {
                if (board[r][c] != goalBoard[r][c]) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean equals(Object y) {
        if (y == null) {
            return false;
        }
        if (y == this) {
            return true;
        }
        if (this.getClass() != y.getClass()) {
            return false;
        }

        Board other = (Board) y;
        if (this.dimension != other.dimension) {
            return false;
        }
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board.length; c++) {
                if (board[r][c] != other.board[r][c]) {
                    return false;
                }
            }
        }

        return true;
    }

    public Iterable<Board> neighbors() {
        ArrayList<Board> neighbors = new ArrayList<>();
        int[][] copyBoard = new int[board.length][board.length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                copyBoard[i][j] = board[i][j];
            }
        }

        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board.length; c++) {
                if (board[r][c] == 0) {
                    if (r - 1 >= 0) {
                        int temp = copyBoard[r - 1][c];
                        copyBoard[r - 1][c] = 0;
                        copyBoard[r][c] = temp;
                        neighbors.add(new Board(copyBoard));
                        copyBoard[r - 1][c] = temp;
                        copyBoard[r][c] = 0;
                    }

                    if (r + 1 < board.length) {
                        int temp = copyBoard[r + 1][c];
                        copyBoard[r + 1][c] = 0;
                        copyBoard[r][c] = temp;
                        neighbors.add(new Board(copyBoard));
                        copyBoard[r + 1][c] = temp;
                        copyBoard[r][c] = 0;
                    }

                    if (c - 1 >= 0) {
                        int temp = copyBoard[r][c - 1];
                        copyBoard[r][c - 1] = 0;
                        copyBoard[r][c] = temp;
                        neighbors.add(new Board(copyBoard));
                        copyBoard[r][c - 1] = temp;
                        copyBoard[r][c] = 0;
                    }

                    if (c + 1 < board.length) {
                        int temp = copyBoard[r][c + 1];
                        copyBoard[r][c + 1] = 0;
                        copyBoard[r][c] = temp;
                        neighbors.add(new Board(copyBoard));
                        copyBoard[r][c + 1] = temp;
                        copyBoard[r][c] = 0;
                    }
                }
            }
        }

        return neighbors;
    }

    /**
     * To be honest, this twin() method was challenging for me. I had no idea what its purpose was.
     * I initially thought it just randomly swapped two tiles. I did not see that there was a specific
     * way to implement this method, and that led to several frustrated groans as I got the same grade
     * over and over again from the autograder.
     */
    public Board twin() {
        int rowZero = 0;
        int colZero = 0;
        int[][] copyBoard = new int[board.length][board.length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                copyBoard[i][j] = board[i][j];
                if (copyBoard[i][j] == 0) {
                    rowZero = i;
                    colZero = j;
                }
            }
        }

        int temp = 0;
        if (rowZero == 0) {
            temp = copyBoard[1][0];
            copyBoard[1][0] = copyBoard[1][1];
            copyBoard[1][1] = temp;
        } else {
            temp = copyBoard[0][0];
            copyBoard[0][0] = copyBoard[0][1];
            copyBoard[0][1] = temp;
        }

        return new Board(copyBoard);
    }

    // unit testing (not graded)
    public static void main(String[] args) {

    }

}