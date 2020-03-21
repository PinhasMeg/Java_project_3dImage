package geometries;

import primitives.Point3D;

public class Sphere extends RadialGeometry {
    Point3D _center;

    /**
     * constructor with radius and center
     *
     * @param radius
     */
    public Sphere(double radius, Point3D center) {
        super(radius);
        _center = center;
    }

    /**
     * constructor with radialGeometry and center
     *
     * @param radialGeometry
     * @param center
     */
    public Sphere(RadialGeometry radialGeometry, Point3D center) {
        super(radialGeometry);
        _center = center;
    }

    /**
     * function to get center
     *
     * @return center
     */
    public Point3D get_center() {
        return _center;
    }

    @Override
    public String toString() {
        return " The Sphere center is: " + _center + ", radius is" + _radius + '.';
    }
}
