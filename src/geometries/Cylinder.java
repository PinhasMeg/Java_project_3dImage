package geometries;

import primitives.Ray;

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
}
