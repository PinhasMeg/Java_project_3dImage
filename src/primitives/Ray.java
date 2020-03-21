package primitives;

import java.util.Objects;

import static java.lang.StrictMath.sqrt;

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
        if (lengthRay(point, vector) != 1)
            throw new IllegalArgumentException(" This vector is not a unit vector! ");
        else {
            this._origin = point;
            this._vector = vector;
        }
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
     * this function calculates the length of the ray
     *
     * @param point
     * @param vector
     * @return the size (double) of the ray
     */
    public double lengthRay(Point3D point, Vector vector) {
        return sqrt((point._x.get() - vector._head._x.get()) * (point._x.get() - vector._head._x.get())
                + (point._y.get() - vector._head._y.get()) * (point._y.get() - vector._head._y.get())
                + (point._z.get() - vector._head._z.get()) * (point._z.get() - vector._head._z.get())
        );
    }
}
