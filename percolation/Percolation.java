import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/* *****************************************************************************
 *  Name: MoonKuma
 *  Date: 20190614
 *  Description: Percolation assignment.
 *  The trick of using vitural sites help to decide the percolate problem quickly.
 *  Yet this leads to a backwash problem
 *  In solving which, another WQUF is involved at the cost of extra memory.
 **************************************************************************** */
public class Percolation {

    private WeightedQuickUnionUF weightedQuickUnionUF;
    private WeightedQuickUnionUF weightedQuickUnionUFFullJudge;
    private int[] grid;
    private int n;
    private int numOpen=0;

    // create n-by-n grid, with all sites blocked
    public Percolation (int n) {
        if(n<=0){
            throw new IllegalArgumentException("Expecting input n as a positive integer");
        }else{
            weightedQuickUnionUF = new WeightedQuickUnionUF(n*n+2);
            weightedQuickUnionUFFullJudge = new WeightedQuickUnionUF(n*n+2);
            grid = new int[n*n];
            for(int i=0;i<n*n;i++){
                grid[i] = 0;
            }
            this.n = n;
        }
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        if(row<=0 || col<=0 || row>n || col>n){
            throw new IllegalArgumentException("Illeagal input row or col");
        }else {
            if (!isOpen(row, col)) {
                grid[(row - 1) * n + col - 1] = 1;
                numOpen++;
                // connected grid nearby
                if (row+1 <= n && isOpen(row + 1, col)) {
                    weightedQuickUnionUF.union((row - 1) * n + col, (row) * n + col);
                    weightedQuickUnionUFFullJudge.union((row - 1) * n + col, (row) * n + col);
                }
                if (row - 1 > 0 && isOpen(row - 1, col)) {
                    weightedQuickUnionUF.union((row - 1) * n + col, (row - 2) * n + col);
                    weightedQuickUnionUFFullJudge.union((row - 1) * n + col, (row - 2) * n + col);
                }
                if (col + 1<=n && isOpen(row, col + 1)) {
                    weightedQuickUnionUF.union((row - 1) * n + col, (row - 1) * n + col + 1);
                    weightedQuickUnionUFFullJudge.union((row - 1) * n + col, (row - 1) * n + col + 1);
                }
                if (col - 1 > 0 && isOpen(row, col - 1)) {
                    weightedQuickUnionUF.union((row - 1) * n + col, (row - 1) * n + col - 1);
                    weightedQuickUnionUFFullJudge.union((row - 1) * n + col, (row - 1) * n + col - 1);
                }
                // connected top & bottom
                if (row == 1) {
                    weightedQuickUnionUF.union((row - 1) * n + col, 0);
                    weightedQuickUnionUFFullJudge.union((row - 1) * n + col, 0);
                }
                if (row == n) {
                    weightedQuickUnionUF.union((row - 1) * n + col, n*n + 1);
                }
            }
        }
    }

    // is site (row, col) open? & also whether this site exist
    public boolean isOpen(int row, int col) {
        if(row<=0 || col<=0 || row>n || col>n){
            throw new IllegalArgumentException("Illeagal input row or col");
        }else{
            return grid[((row-1)*n + col -1)]==1;
        }
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        if(row<=0 || col<=0 || row>n || col>n){
            throw new IllegalArgumentException("Illeagal input row or col");
        }else{
            return weightedQuickUnionUFFullJudge.connected(0,(row-1)*n + col -1 + 1);
        }
    }

    // number of open sites
    public int numberOfOpenSites(){
        return numOpen;
    }

    // does the system percolate?
    public boolean percolates(){
        // this method helps making exact image as the example, yet there is a cost of efficiency
        // for (int col=1;col<=n;col++){
        //     if (weightedQuickUnionUF.connected(n*(n-1) + col, 0)){
        //         return true;
        //     }
        // }
        // return false;
        return weightedQuickUnionUF.connected(0,n*n+1);
    }

    // test client (optional)
    public static void main(String[] args) {

    }
}
