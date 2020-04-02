package geometries;

import primitives.*;

import java.util.List;
import static primitives.Util.isZero;

public class Tube extends RadialGeometry {

    protected final Ray _axisRay;

    /**
     * constructor with parameters
     *
     * @param radius
     * @param axisRay
     */
    public Tube(double radius, Ray axisRay) {
        super(radius);
        this._axisRay = axisRay;
    }

    /**
     * constructor with parameters
     *
     * @param radialGeometry
     * @param axisRay
     */
    public Tube(RadialGeometry radialGeometry, Ray axisRay) {
        super(radialGeometry);
        this._axisRay = axisRay;
    }

    /**
     * get the axisRay
     *
     * @return
     */
    public Ray get_axisRay() {
        return _axisRay;
    }

    @Override
    public String toString() {
        return "the Tube's axisRay= is :" + _axisRay + ", the radius is" + _radius + '.';
    }

    @Override
    public Vector getNormal(Point3D point) {
        //The vector from the point of the cylinder to the given point
        Point3D p = _axisRay.get_origin();
        Vector v = _axisRay.get_vector();

        Vector vector1 = point.subtract(p);

        //We need the projection to multiply the _direction unit vector
        double projection = vector1.dotProduct(v);
        if (!isZero(projection)) {
            // projection of P-O on the ray:
            return (point.subtract(p.add(v.scale(projection))).normalized());
        }

        //This vector is orthogonal to the direction vector of the tube.
        return point.subtract(p).normalized();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Tube))
            return false;
        if (this == obj)
            return true;
        Tube other = (Tube) obj;

        //the two vectors needs to be in the same direction,
        //but not necessary to have the same length.
        try {
            Vector v = _axisRay.get_vector().crossProduct(other._axisRay.get_vector());
        } catch (IllegalArgumentException ex) {
            return (Util.isZero(this._radius - other._radius) && _axisRay.get_origin().equals((_axisRay.get_origin())));
        }
        throw new IllegalArgumentException("direction cross product with parameter.direction == Vector(0,0,0)");
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        return null;
    }
}
