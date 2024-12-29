import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int[][] grid; // creates grid
    private int openSites; // creates variable to track open sites
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF fullUf;
    private final int topSite = 0; // index of 0 represents top site
    private int bottomSite;

    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("N must be greater than 0");
        }
        uf = new WeightedQuickUnionUF(N * N + 2);
        fullUf = new WeightedQuickUnionUF(N * N + 1);
        bottomSite = N * N + 1;
        grid = new int[N][N]; // initializes NxN grid
        for (int i = 0; i < N; i++) { // filling rows
            for (int j = 0; j < N; j++) { // filling columns
                grid[i][j] = 0; // value of 0 to mean blocked
            }
        }
        openSites = 0; // no open sites after initializing grid
    }

    public void open(int row, int col) {
        if (row < 0 || row >= grid.length || col < 0 || col >= grid.length) {
            throw new IndexOutOfBoundsException("Invalid index: (" + row + ", " + col + ")");
        }

        if (grid[row][col] == 0) { // value of 0 to mean blocked
            grid[row][col] = 1; // value of 1 to mean open
            openSites++; // this site now open, add 1 to num of open sites
            // connecting to virtual top site
            if (row == 0) {
                uf.union(topSite, ijTo1D(row, col));
                fullUf.union(topSite, ijTo1D(row, col));
            }
            // connecting to virtual bottom site
            if (row == grid.length - 1) {
                uf.union(ijTo1D(row, col), bottomSite);
            }
            /* simplifying neighbors check thanks to
            https://codereview.stackexchange.com/questions/
            62160/checking-for-neighbours-more-elegantly-in-conways-game-of-life */
            int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
            for (int[] dir : directions) {
                int newRow = row + dir[0];
                int newCol = col + dir[1];
                if (isValid(newRow, newCol) && isOpen(newRow, newCol)) {
                    uf.union(ijTo1D(row, col), ijTo1D(newRow, newCol));
                    fullUf.union(ijTo1D(row, col), ijTo1D(newRow, newCol));
                }
            }
        }
    }

    public boolean isOpen(int row, int col) {
        if (row < 0 || row >= grid.length || col < 0 || col >= grid.length) {
            throw new IndexOutOfBoundsException("Invalid index: (" + row + ", " + col + ")");
        }
        return grid[row][col] == 1; // value of 1 to mean open
    }

    public boolean isFull(int row, int col) {
        if (row < 0 || row >= grid.length || col < 0 || col >= grid.length) {
            throw new IndexOutOfBoundsException("Invalid index: (" + row + ", " + col + ")");
        }
        return fullUf.find(topSite) == fullUf.find(ijTo1D(row, col));
    }

    public int numberOfOpenSites() {
        return openSites; // return variable that tracks num of open sites
    }

    public boolean percolates() {
        return uf.find(topSite) == uf.find(bottomSite);
    }

    public int ijTo1D(int i, int j) {
        return ((i * grid.length) + j + 1); // 1D grid indexing starts at 1
    }

    private boolean isValid(int row, int col) {
        return row >= 0 && row < grid.length && col >= 0 && col < grid.length;
    }

}
