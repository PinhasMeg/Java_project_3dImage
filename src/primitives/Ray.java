package primitives;

import java.util.Objects;

import static java.lang.StrictMath.sqrt;
import static primitives.Util.isZero;

/**
 * This class contains a 3Dpoint and a vector
 */

public class Ray {
    Point3D _origin;
    Vector _vector;

    //********** Constructors ***********//

    /**
     * Constructor with parameters
     *
     * @param vector is initializes
     * @param point  is initialize
     */
    public Ray(Point3D point, Vector vector) {
        this._origin = new Point3D(point);
        this._vector = new Vector(vector.normalized());
    }

    /**
     * copy constructor
     *
     * @param r is a Ray
     */
    public Ray(Ray r) {
        this._origin = r._origin;
        this._vector = r._vector;
    }

    /**
     * this function returns the value of the Point3D _origin
     *
     * @return _origin
     */
    public Point3D get_origin() {
        return _origin;
    }

    /**
     * this function returns the vector of the Ray
     *
     * @return _vector
     */
    public Vector get_vector() {
        return _vector;
    }

    @Override
    public String toString() {
        return "Ray: \nOrigin is: " + _origin + "\nVector is: " + _vector;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return _origin.equals(ray._origin) &&
                _vector.equals(ray._vector);
    }

    /**
     * @param length
     * @return
     */
    public Point3D getTargetPoint(double length) {
        return isZero(length) ? _origin : new Point3D(_origin).add(_vector.scale(length));
    }
}
