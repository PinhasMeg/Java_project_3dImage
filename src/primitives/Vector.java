package primitives;

import javafx.geometry.Point2D;

import java.util.Objects;

public class Vector {
    Point3D _head;

    public Vector(Point3D _head) {
        this._head = _head;
    }

//    public Vector(Point3D _p1, Point3D _p2) {
//        this._head = _p2.subtract(_p1);
//    }

    public Point3D get_head() {
        return new Point3D(_head._x, _head._y, _head._z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return _head.equals(vector._head);
    }

    /**
     * this function multiplies two vectors and give a new vector perpendicular to both vectors
     *
     * @param vector is multiplied with the currant vector
     * @return a new vector perpendicular to both vectors
     */
    public Vector crossProduct(Vector vector) {

        double x = (this._head.get_y()._coord * vector._head.get_z()._coord - this._head.get_z()._coord * vector._head.get_y()._coord);
        double y = (this._head.get_x()._coord * vector._head.get_z()._coord - this._head.get_z()._coord * vector._head.get_x()._coord);
        double z = (this._head.get_x()._coord * vector._head.get_y()._coord - this._head.get_y()._coord * vector._head.get_x()._coord);
        return new Vector(new Point3D(x, y, z));
    }

    /**
     * @param other
     * @return double number of the dot product between two vectors
     */
    public double dotProduct(Vector other) {

        return _head._x.get() * other._head._x.get() +
                _head._y.get() * other._head._y.get() +
                _head._z.get() * other._head._z.get();
    }
}
