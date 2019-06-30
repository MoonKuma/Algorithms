import edu.princeton.cs.algs4.StdRandom;

/**
 * @author MoonKuma
 * @description Try to write a QuickSort based on memory
 * @since 2019/6/25
 */

public class QuickSort {

    public static void sort(Comparable[] a)
    {
        // public sort
        StdRandom.shuffle(a);
        sort(a, 0, a.length-1);
    }
    private static void sort(Comparable[] a, int lo, int hi)
    {
        // private sort, sort after shuffle & sort recursively
        if (lo>=hi) return;
        int mid = partition(a,lo,hi);
        sort(a, lo, mid-1);
        sort(a, mid+1, hi);
    }
    private static int partition(Comparable[] a, int lo, int hi) {
        // partition
        int i = lo;

        return 0;
    }
}
