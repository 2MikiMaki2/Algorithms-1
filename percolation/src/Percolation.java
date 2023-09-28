import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int openSites;
    private final int topVNode;
    private final int bottomVNode;
    private final int len;
    private final WeightedQuickUnionUF UFVirtual;
    private final WeightedQuickUnionUF UFBackwash;
    private final boolean[][] grid;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        UFVirtual = new WeightedQuickUnionUF(n * n + 2);
        UFBackwash = new WeightedQuickUnionUF(n * n + 1);
        topVNode = 0;
        bottomVNode = n * n + 1;
        grid = new boolean[n][n];
        len = n;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        checkException(row, col);
        if (!isOpen(row, col)) {
            openSites++;
            int p = convertIndex(row, col);
            grid[row - 1][col - 1] = true;

            if (row - 1 > 0 && isOpen(row - 1, col)) {
                UFVirtual.union(p, convertIndex(row - 1, col));
                UFBackwash.union(p, convertIndex(row - 1, col));
            } else if (row == 1) {
                UFVirtual.union(p, topVNode);
                UFBackwash.union(p, topVNode);
            }

            if (row != grid.length && isOpen(row + 1, col)) {
                UFVirtual.union(p, convertIndex(row + 1, col));
                UFBackwash.union(p, convertIndex(row + 1, col));
            } else if (row + 1 > grid.length) {
                UFVirtual.union(p, bottomVNode);
            }

            if (col > 1 && isOpen(row, col - 1)) {
                UFVirtual.union(p, convertIndex(row, col - 1));
                UFBackwash.union(p, convertIndex(row, col - 1));
            }

            if (col< grid.length && isOpen(row, col + 1)) {
                UFVirtual.union(p, convertIndex(row, col + 1));
                UFBackwash.union(p, convertIndex(row, col + 1));
            }
        }

    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkException(row, col);
        return grid[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        checkException(row, col);

        return isOpen(row, col) && UFBackwash.find(0) == UFBackwash.find(convertIndex(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return UFVirtual.find(0) == UFVirtual.find(grid.length * grid.length + 1);
    }

    private void checkException(int row, int col) {
        if ((row < 1 || col < 1) || (row > grid.length || col > grid.length)) {
            throw new IllegalArgumentException();
        }
    }

    private int convertIndex(int row, int col) {
        return (row - 1) * len + col;
    }

    // test client (optional)
    public static void main(String[] args) {
    }
}