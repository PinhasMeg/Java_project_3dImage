package geometries;

import primitives.*;

public class Plane implements Geometry {
    Point3D _p;
    Vector _normal;

    /**
     * constructor with 3 points
     *
     * @param p1
     * @param p2
     * @param p3
     */
    public Plane(Point3D p1, Point3D p2, Point3D p3) {
        _p = p1;
        _normal = getNormal(p1);
    }

    /**
     * constructor with point and normal
     *
     * @param point
     * @param vector
     */
    public Plane(Point3D point, Vector vector) {
        _p = point;
        _normal = vector;
    }

    @Override
    public Vector getNormal(Point3D p) {
        return null;
    }

    public Vector getNormal() {
        return null;
    }

    @Override
    public String toString() {
        return " The Plane's point is: " + _p + ", and the normal is: " + _normal + '.';
    }
}
