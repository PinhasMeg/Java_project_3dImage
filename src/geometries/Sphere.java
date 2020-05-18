package geometries;

import primitives.*;

import java.util.List;
import java.util.Objects;

import static primitives.Util.alignZero;

public class Sphere extends RadialGeometry {
    Point3D _center;

    /**
     * constructor for a new sphere object.
     *
     * @param radius the radius of the sphere
     * @param center the center point of the sphere
     * @throws Exception in case of negative or zero radius from RadialGeometry constructor
     */
    public Sphere(Color emissionLight, Material material, double radius, Point3D center) {
        super(emissionLight, radius, material);
        this._center = new Point3D(center);
    }

    /**
     * constructor
     *
     * @param emissionLight
     * @param radius
     * @param center
     */
    public Sphere(Color emissionLight, double radius, Point3D center) {
        this(emissionLight, new Material(0, 0, 0), radius, center);
    }

    /**
     * constructor
     *
     * @param radius
     * @param center
     */
    public Sphere(double radius, Point3D center) {
        this(Color.BLACK, new Material(0, 0, 0), radius, center);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Sphere)) return false;
        Sphere sphere = (Sphere) o;
        return this._center.equals(sphere._center) && (Util.isZero(this._radius - sphere._radius));
    }

    /**
     * get the normal to this sphere in a given point
     */
    @Override
    public Vector getNormal(Point3D point) {
        Vector orthogonal = new Vector(point.subtract(_center));
        return orthogonal.normalized();
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

    /**
     * find sphere intersections
     *
     * @param ray ray pointing toward a Geometry
     * @return
     */
    @Override
    public List<GeoPoint> findIntersections(Ray ray) {
        Point3D p0 = ray.get_origin();
        Vector v = ray.get_vector();
        Vector u;
        try {
            u = _center.subtract(p0);
        } catch (IllegalArgumentException e) {
            return List.of(new GeoPoint(this, ray.getTargetPoint(this._radius)));
        }
        double tm = alignZero(v.dotProduct(u));
        double dSquared = tm == 0 ? u.lengthSquared() : u.lengthSquared() - tm * tm;
        double thSquared = alignZero(_radius * _radius - dSquared);
        if (thSquared <= 0) return null;
        double th = alignZero(Math.sqrt(thSquared));
        if (th == 0) return null;
        double t1 = alignZero(tm - th);
        double t2 = alignZero(tm + th);
        if (t1 <= 0 && t2 <= 0) return null;
        if (t1 > 0 && t2 > 0) return List.of(new GeoPoint(this, ray.getTargetPoint(t1)),
                new GeoPoint(this, ray.getTargetPoint(t2)));
        if (t1 > 0)
            return List.of(new GeoPoint(this, ray.getTargetPoint(t1)));
        else
            return List.of(new GeoPoint(this, ray.getTargetPoint(t2)));
    }
}
