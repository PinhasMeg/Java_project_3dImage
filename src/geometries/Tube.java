package geometries;

import primitives.Ray;

public class Tube extends RadialGeometry {

    public Ray _axisRay;

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
}
