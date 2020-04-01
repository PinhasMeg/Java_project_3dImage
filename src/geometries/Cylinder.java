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
     * @param _height
     */
    public Cylinder(double radius, Ray axisRay, double _height) {
        super(radius, axisRay);
        this._height = _height;
    }

    /**
     * constructor with parameters
     *
     * @param radialGeometry
     * @param axisRay
     * @param _height
     */
    public Cylinder(RadialGeometry radialGeometry, Ray axisRay, double _height) {
        super(radialGeometry, axisRay);
        this._height = _height;
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
     * @param p is Point object
     * @return new vector that is normal to that cylinder
     */
    public Vector getNormal(Point3D p) {
        Point3D o = _axisRay.get_origin();
        Vector v = _axisRay.get_vector();

        // projection of P-O on the ray:
        double t;
        try {
            t = alignZero(p.subtract(o).dotProduct(v));
        } catch (IllegalArgumentException e) { // P = O
            return v;
        }

        // if the point is at a base
        if (t == 0 || isZero(_height - t)) // if it's close to 0, we'll get ZERO vector exception
            return v;

        o = o.add(v.scale(t));
        return p.subtract(o).normalize();


    }
}
