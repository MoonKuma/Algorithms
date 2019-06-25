/* *****************************************************************************
 *  Name: MoonKuma
 *  Date: 20190625
 *  Description: for duplicated issue,
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class FastCollinearPoints {

    private LineSegment[] lineSegments;
    private int numberOfSegments = 0;

    public FastCollinearPoints(Point[] points) {
        // finds all line segments containing 4 points
        LineSegment[] tmpLineSegments = new LineSegment[points.length];
        int segIndex = 0;
        int totalLength = points.length;
        for (int index = 0; index < totalLength; index++) {
            Arrays.sort(points);
            Point orign = points[index];
            Arrays.sort(points, orign.slopeOrder());
            // identifing duplicated points here in the future
            int i0 = 1;
            int j0;
            while (i0 < totalLength) {
                if (points[i0].compareTo(orign) == 0) throw new IllegalArgumentException();
                double slopeRefer = orign.slopeTo(points[i0]);
                j0 = i0+1;
                while (j0 <= totalLength) {
                    if (j0 < totalLength && slopeRefer == orign.slopeTo(points[j0])) {
                        j0++;
                        continue;
                    }
                    else if (j0-i0 >= 3) {
                        //possible target
                        Arrays.sort(points, i0, j0);
                        if (orign.compareTo(points[i0]) < 0) {
                            // real target (when orign is the minimum)
                            tmpLineSegments[segIndex++] = new LineSegment(orign, points[j0-1]);
                            numberOfSegments++;
                        }
                        break;
                    } else {
                        break;
                    }
                }
                i0 = j0;
            }
        }
        lineSegments = new LineSegment[numberOfSegments];
        for (int i = 0; i < lineSegments.length; i++) {
            lineSegments[i] = tmpLineSegments[i];
        }
    }

    public int numberOfSegments() {
        // the number of line segments
        return numberOfSegments;
    }
    public LineSegment[] segments() {
        // the line segments
        return lineSegments;
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
