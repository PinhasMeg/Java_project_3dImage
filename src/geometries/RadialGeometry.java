package geometries;

import primitives.Point3D;
import primitives.Vector;

public class RadialGeometry implements Geometry {
    double _radius;

    /**
     * constructor with parameter
     *
     * @param radius
     */
    public RadialGeometry(double radius) {
        _radius = radius;
    }

    /**
     * copy constructor
     *
     * @param radialGeometry
     */
    public RadialGeometry(RadialGeometry radialGeometry) {
        _radius = radialGeometry._radius;
    }

    /**
     * get the radius
     *
     * @return double
     */
    public double get_radius() {
        return _radius;
    }

    @Override
    public Vector getNormal(Point3D p) {
        return null;
    }

    @Override
    public String toString() {
        return "The radius is: " + _radius + '.';
    }
}
