import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private static final int TOP_UF_INDEX = 0;

    private final boolean[][] model;

    private final int size;

    private final int bottomUFIndex;

    private final WeightedQuickUnionUF weightedQuickUnionUF;

    private final WeightedQuickUnionUF weightedQuickUnionUFWithoutBottomIndex;

    private int numberOfOpenSites;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        model = new boolean[n][n];
        size = n;
        bottomUFIndex = n * n - 1;
        weightedQuickUnionUF = new WeightedQuickUnionUF(n * n + 2);
        weightedQuickUnionUFWithoutBottomIndex = new WeightedQuickUnionUF(n * n + 1);
        numberOfOpenSites = 0;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);

        if (!isOpen(row, col)) {
            model[row - 1][col - 1] = true;
            int openedUFIndex = getUFIndex(row, col);

            if (row == 1) {
                weightedQuickUnionUF.union(openedUFIndex, TOP_UF_INDEX);
                weightedQuickUnionUFWithoutBottomIndex.union(openedUFIndex, TOP_UF_INDEX);
            }
            if (row == size) {
                weightedQuickUnionUF.union(openedUFIndex, bottomUFIndex);
            }

            // union possible neighbor(s)
            if (row > 1 && isOpen(row - 1, col)) {
                weightedQuickUnionUF.union(openedUFIndex, getUFIndex(row - 1, col));
                weightedQuickUnionUFWithoutBottomIndex
                        .union(openedUFIndex, getUFIndex(row - 1, col));
            }
            if (row < size && isOpen(row + 1, col)) {
                weightedQuickUnionUF.union(openedUFIndex, getUFIndex(row + 1, col));
                weightedQuickUnionUFWithoutBottomIndex
                        .union(openedUFIndex, getUFIndex(row + 1, col));
            }
            if (col > 1 && isOpen(row, col - 1)) {
                weightedQuickUnionUF.union(openedUFIndex, getUFIndex(row, col - 1));
                weightedQuickUnionUFWithoutBottomIndex
                        .union(openedUFIndex, getUFIndex(row, col - 1));
            }
            if (col < size && isOpen(row, col + 1)) {
                weightedQuickUnionUF.union(openedUFIndex, getUFIndex(row, col + 1));
                weightedQuickUnionUFWithoutBottomIndex
                        .union(openedUFIndex, getUFIndex(row, col + 1));
            }

            numberOfOpenSites++;
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);

        return model[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);

        int ufIndex = getUFIndex(row, col);

        return isOpen(row, col) && isConnected(weightedQuickUnionUFWithoutBottomIndex, ufIndex, TOP_UF_INDEX);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return numberOfOpenSites > 0 && isConnected(weightedQuickUnionUF, TOP_UF_INDEX, bottomUFIndex);
    }

    private void validate(int row, int col) {
        if (row <= 0 || row > size || col <= 0 || col > size) {
            throw new IllegalArgumentException();
        }
    }

    private int getUFIndex(int row, int col) {
        return size * (row - 1) + col;
    }

    private boolean isConnected(WeightedQuickUnionUF wquf, int p, int q) {
        return wquf.find(p) == wquf.find(q);
    }

    // test client (optional)
    public static void main(String[] args) {
        // empty
    }
}