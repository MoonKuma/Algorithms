/* *****************************************************************************
 *  Name: MoonKuma
 *  Date: 20190625
 *  Description: Brutal force solution.
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class BruteCollinearPoints {

    private LineSegment[] lineSegments;
    private int numberOfSegments = 0;

    public BruteCollinearPoints(Point[] points1) {
        // finds all line segments containing 4 points
        // don't waste time micro-optimizing the brute-force solution, won't help a lot! XD
        if (points1 == null) throw new IllegalArgumentException();
        Point[] points = new Point[points1.length];
        for (int i = 0; i < points1.length; i++) {
            if (points1[i] == null) throw new IllegalArgumentException();
            points[i] = points1[i];
        }
        lineSegments = new LineSegment[points.length];
        Arrays.sort(points);
        int totalLength = points.length;
        int index = 0;
        for (int i0 = 0; i0 < totalLength; i0++) {
            Point p = points[i0];
            for (int i1 = i0+1; i1 < totalLength; i1++) {
                Point q = points[i1];
                if (p.compareTo(q) == 0) throw new IllegalArgumentException();
                for (int i2 = i1+1; i2 < totalLength; i2++) {
                    Point r = points[i2];
                    for (int i3 = i2+1; i3 < totalLength; i3++) {
                        Point s = points[i3];
                        if (p.slopeTo(q) == p.slopeTo(r) && p.slopeTo(r) == p.slopeTo(s)) {
                            lineSegments[index++] = new LineSegment(p, s);
                            numberOfSegments++;
                        }
                    }
                }
            }
        }
        LineSegment[] tmpLineSegments = new LineSegment[numberOfSegments];
        for (int i = 0; i < numberOfSegments; i++) {
            tmpLineSegments[i] = lineSegments[i];
        }
        lineSegments = tmpLineSegments;
    }

    public int numberOfSegments() {
        // the number of line segments
        return numberOfSegments;
    }
    public LineSegment[] segments() {
        // the line segments
        LineSegment[] result = new LineSegment[lineSegments.length];
        for (int i = 0; i < lineSegments.length; i++) {
            result[i] = lineSegments[i];
        }
        return result;
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
