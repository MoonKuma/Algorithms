/* *****************************************************************************
 *  Name: MoonKuma
 *  Date: 20190614
 *  Description: Percolation assignment.
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private int trials;
    private double[] results;
    private double mean=-1.0;
    private double stdv=-1.0;
    private double confidenceLo=-1.0;
    private double confidenceHi=-1.0;

    public PercolationStats(int n, int trials) {
        // perform trials independent experiments on an n-by-n grid

        if (n<=0 || trials<=0){
            throw new IllegalArgumentException("Illegal input");
        }else{
            this.trials = trials;
            results = new double[trials];
            int[] shuffle_list = new int[n*n];
            for(int i=0;i<n*n;i++){shuffle_list[i] = i+1;}
            for(int i=0;i<trials;i++){
                Percolation percolation = new Percolation(n);
                StdRandom.shuffle(shuffle_list);
                for(int j=0;j<n*n;j++){
                    int pos = shuffle_list[j];
                    if (pos%n==0){
                        percolation.open(pos/n,n);
                    }else{
                        percolation.open(pos/n+1,pos%n);
                    }
                    if(percolation.percolates()){break;}
                }
                double perc = percolation.numberOfOpenSites()/((double)n*n);
                results[i] = perc;
            }
            this.mean();
            this.stddev();
            this.confidenceHi();
            this.confidenceLo();
        }
    }
    public double mean(){
        // sample mean of percolation threshold
        if(this.mean==-1.0){
            this.mean = StdStats.mean(results);
        }
        return this.mean;
    }
    public double stddev(){
        // sample standard deviation of percolation threshold
        if(this.stdv==-1.0){
            this.stdv = StdStats.stddev(results);
        }
        return this.stdv;
    }
    public double confidenceLo(){
        // low  endpoint of 95% confidence interval
        if(this.confidenceLo==-1.0){
            this.confidenceLo=this.mean - 1.96*this.stdv/(Math.sqrt(this.trials));
        }
        return this.confidenceLo;
    }

    public double confidenceHi(){
        // high endpoint of 95% confidence interval
        if(this.confidenceHi==-1.0){
            this.confidenceHi = this.mean + 1.96*this.stdv/(Math.sqrt(this.trials));
        }
        return this.confidenceHi;
    }

    public static void main(String[] args){
        // test client (described below)
        // mean                    = 0.5929934999999997
        // stddev                  = 0.00876990421552567
        // 95% confidence interval = [0.5912745987737567, 0.5947124012262428]
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats percolationStats = new PercolationStats(n,trials);
        System.out.printf("mean                    = %f\n",percolationStats.mean());
        System.out.printf("stddev                  = %f\n",percolationStats.stddev());
        System.out.printf("%s confidence interval = [%f,%f]\n","95%",percolationStats.confidenceLo(),percolationStats.confidenceHi());
    }

}
