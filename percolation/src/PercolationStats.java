import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final double[] percolationSet;
    private final double confidenceInterval = 1.95;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        percolationSet = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation trial = new Percolation(n);
            while (!trial.percolates()) {
                int row = StdRandom.uniformInt(1, n + 1);
                int col = StdRandom.uniformInt(1, n + 1);
                if (!trial.isOpen(row, col))
                    trial.open(row, col);
            }

            percolationSet[i] = (double) trial.numberOfOpenSites() / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(percolationSet);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(percolationSet);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - ((confidenceInterval * stddev()) / percolationSet.length);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + ((confidenceInterval * stddev()) / percolationSet.length);
    }

    // test client (see below)
    public static void main(String[] args) {
        PercolationStats percolationStats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        System.out.println("mean                    = " + percolationStats.mean() + "\nstddev                  = "
                + percolationStats.stddev() + "\n95% confidence interval = [" + percolationStats.confidenceLo() + ", "
                + percolationStats.confidenceHi() + "]");
    }
}
