package geometries;

import primitives.Point3D;
import primitives.Vector;

import static primitives.Util.isZero;

public abstract class RadialGeometry implements Geometry {
    double _radius;

    /**
     * @param _radius
     */
    public RadialGeometry(double _radius) {
        if (isZero(_radius) || (_radius < 0.0))
            throw new IllegalArgumentException("radius " + _radius + " is not valid");
        this._radius = _radius;
    }

    /**
     *
     * @param other
     */
    public RadialGeometry(RadialGeometry other) {
        this._radius = other._radius;
    }

    /**
     *
     * @return
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
