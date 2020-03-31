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
        _p = new Point3D(p1);
        Vector U = new Vector(p1, p2);
        Vector V = new Vector(p1, p3);
        Vector N = U.crossProduct(V);
        N.normalize();
        _normal = N;
        //_normal = N.scale(-1);
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
        return _normal;
    }

    public Vector getNormal() {
        return getNormal(null);
    }

    @Override
    public String toString() {
        return " The Plane's point is: " + _p + ", and the normal is: " + _normal + '.';
    }
}
