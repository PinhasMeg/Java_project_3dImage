package geometries;

import primitives.*;

import java.util.Objects;

public class Sphere extends RadialGeometry {
    Point3D _center;

    /**
     * constructor with radius and center
     *
     * @param radius
     */
    public Sphere(double radius, Point3D center) {
        super(radius);
        _center = new Point3D(center);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Sphere)) return false;
        Sphere sphere = (Sphere) o;
        return this._center.equals(sphere._center) && (Util.isZero(this._radius - sphere._radius));
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
