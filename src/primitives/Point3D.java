package primitives;

/**
 * Point3D basic point with coordinates in X, Y, Z axes
 */

public class Point3D {

    /**
     *  Class Point 3D
     */
    Coordinate _x;
    Coordinate _y;
    Coordinate _z;

    /**
     *
     * @param _x x coordinate
     * @param _y y coordinate
     * @param _z z coordinate
     */
    public Point3D(Coordinate _x, Coordinate _y, Coordinate _z) {
        this._x = _x;
        this._y = _y;
        this._z = _z;
    }

    public  final static Point3D ZERO = new Point3D(0.0,0.0,0.0);

    public Point3D(double _x, double _y, double _z) {
        this(new Coordinate(_x),new Coordinate(_y),new Coordinate(_z));
    }

    /**
     *
     * @return new coordinate based on _x
     */
    public Coordinate get_x() {
        return new Coordinate(_x);
    }
    /**
     *
     * @return new coordinate based on _y
     */
    public Coordinate get_y() {
        return new Coordinate(_y);
    }
    /**
     *
     * @return new coordinate based on _z
     */
    public Coordinate get_z() {
        return new Coordinate(_z);
    }

    /**
     *
     * @param o
     * @return if the object is equals to the one we compare
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point3D point3D = (Point3D) o;
        return _x.equals(point3D._x) &&
                _y.equals(point3D._y) &&
                _z.equals(point3D._z);
    }

    @Override
    public String toString() {
        return "(" + _x + ", " + _y + ", " + _z + ')';
    }

    public Vector subtract(Point3D p) {
        return new Vector(new Point3D(
                p._x.get() - this._x.get(),
                p._y.get() - this._y.get(),
                p._z.get() - this._z.get()
        ));
    }
}
