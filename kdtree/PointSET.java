/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

public class PointSET {

    private SET<Point2D> point2DSET;

    public PointSET() {
        // construct an empty set of points
        point2DSET = new SET<>();
    }

    public boolean isEmpty() {
        // is the set empty?
        return point2DSET.isEmpty();
    }

    public int size() {
        // number of points in the set
        return point2DSET.size();
    }

    public void insert(Point2D p) {
        // add the point to the set (if it is not already in the set)
        if (p == null) throw new IllegalArgumentException();
        point2DSET.add(p);
    }

    public boolean contains(Point2D p) {
        // does the set contain point p?
        if (p == null) throw new IllegalArgumentException();
        return point2DSET.contains(p);
    }

    public void draw() {
        // draw all points to standard draw
        for (Point2D point2D:point2DSET) {
            point2D.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        // all points that are inside the rectangle (or on the boundary)
        if (rect == null) throw new IllegalArgumentException();
        SET<Point2D> resultSET = new SET<>();
        for (Point2D point2D:point2DSET) {
            if (rect.contains(point2D)) resultSET.add(point2D);
        }
        return resultSET;
    }

    public Point2D nearest(Point2D p) {
        // a nearest neighbor in the set to point p; null if the set is empty
        if (p == null) throw new IllegalArgumentException();
        if (point2DSET.isEmpty()) return null;
        Point2D nearestPoint = null;
        for (Point2D point2D:point2DSET) {
            if (nearestPoint == null) {
                nearestPoint = point2D;
            } else {
                if (p.distanceSquaredTo(point2D) < p.distanceSquaredTo(nearestPoint)) {
                    nearestPoint = point2D;
                }
            }
        }
        return nearestPoint;
    }

    public static void main(String[] args) {

    }
}
