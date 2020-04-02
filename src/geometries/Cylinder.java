package geometries;

import primitives.*;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class Cylinder extends Tube {
    public double _height;

    /**
     * constructor with parameters
     *
     * @param radius
     * @param axisRay
     * @param height
     */
    public Cylinder(double radius, Ray axisRay, double height) {
        super(radius, axisRay);
        if (height == 0)
            throw new IllegalArgumentException("ERROR: Height Zero is not valid!");
        else this._height = height;
    }

    /**
     * constructor with parameters
     *
     * @param radialGeometry
     * @param axisRay
     * @param height
     */
    public Cylinder(RadialGeometry radialGeometry, Ray axisRay, double height) {
        super(radialGeometry, axisRay);
        if (height == 0)
            throw new IllegalArgumentException("ERROR: Height Zero is not valid!");
        else this._height = height;
    }

    /**
     * get height
     *
     * @return double
     */
    public double get_height() {
        return _height;
    }

    @Override
    public String toString() {
        return "Cylinder 's height is: " + _height + ", axisRay is: " + _axisRay + ", radius is" + _radius + '.';
    }

    /**
     * Calculating the normal vector of the Cylinder in specific point
     *
     * @param point is Point object
     * @return new vector that is normal to that cylinder
     */
    public Vector getNormal(Point3D point) {
        //The vector from the point of the cylinder to the given point
        Point3D p = _axisRay.get_origin();
        Vector v = _axisRay.get_vector();

        Vector vector1 = point.subtract(p);

        // We need the projection to multiply the _direction unit vector
        double projection = vector1.dotProduct(v);
        double projectionVectorLength = (v.scale(projection)).length();

        // the point is on the sides of the tube
        if (_height > projectionVectorLength && projection != 0) {
            return (point.subtract(p.add(v.scale(projection))).normalized());
        }
        // the point is on a base
        return get_axisRay().get_vector();
    }
}
