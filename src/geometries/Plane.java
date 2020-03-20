package geometries;

import primitives.*;

public class Plane implements Geometry {
    Point3D _p;
    Vector _normal;

    public Plane(Point3D vertex_x, Point3D vertex_y, Point3D vertex_z) {
        _p = vertex_x;
    }

    @Override
    public Vector getNormal(Point3D p) {
        return null;
    }
    public Vector getNormal() {
        return getNormal(Point3D.ZERO);
    }
}
