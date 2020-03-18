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
        return new Point3D(_head._x,_head._y,_head._z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return _head.equals(vector._head);
    }

    public Vector crossProduct(Vector edge2) {
        //todo
        return new Vector(Point3D.ZERO);
    }

    /**
     *
     * @param other
     * @return
     */
    public double dotProduct(Vector other) {

        return  _head._x.get()*other._head._x.get()+
                _head._y.get()*other._head._y.get()+
                _head._z.get()*other._head._z.get();
    }
}
