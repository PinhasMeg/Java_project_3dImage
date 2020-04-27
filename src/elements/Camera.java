package elements;

import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import static primitives.Util.isZero;

public class Camera {
    Point3D _p0;
    Vector _vTo;
    Vector _vUp;
    Vector _vRight;

    /**
     * @param _p0
     * @param _vTo
     * @param _vUp
     */
    public Camera(Point3D _p0, Vector _vTo, Vector _vUp) {

        //if the the vectors are not orthogonal, throw exception.
        if (_vUp.dotProduct(_vTo) != 0)
            throw new IllegalArgumentException("the vectors must be orthogonal");

        this._p0 = new Point3D(_p0);
        this._vTo = _vTo.normalized();
        this._vUp = _vUp.normalized();

        _vRight = this._vTo.crossProduct(this._vUp).normalize();

    }

    /**
     * @param nX             coordinate
     * @param nY             coordinate
     * @param j              index of the pixel
     * @param i              index of the pixel
     * @param screenDistance
     * @param screenWidth
     * @param screenHeight
     * @return the RAY through the pixel
     */
    public Ray constructRayThroughPixel(int nX, int nY,
                                        int j, int i, double screenDistance,
                                        double screenWidth, double screenHeight) {
        if (isZero(screenDistance)) {
            throw new IllegalArgumentException("distance cannot be 0");
        }


        double Ry = screenHeight / nY;
        double Rx = screenWidth / nX;

        double yi = ((i - nY / 2d) * Ry + Ry / 2d);
        double xj = ((j - nX / 2d) * Rx + Rx / 2d);

        Point3D Pij = _p0.add(_vTo.scale(screenDistance));

        if (!isZero(xj)) {
            Pij = Pij.add(_vRight.scale(xj));
        }
        if (!isZero(yi)) {
            Pij = Pij.subtract(_vUp.scale(yi)); // Pij.add(_vUp.scale(-yi))
        }

        Vector Vij = Pij.subtract(_p0);

        return new Ray(_p0, Vij);

    }

    /**
     * get_p0
     *
     * @return
     */
    public Point3D get_p0() {
        return new Point3D(_p0);
    }

    /**
     * get_vTo
     *
     * @return
     */
    public Vector get_vTo() {
        return new Vector(_vTo);
    }

    /**
     * get_vUp
     *
     * @return
     */
    public Vector get_vUp() {
        return new Vector(_vUp);
    }

    /**
     * get_vRight
     *
     * @return
     */
    public Vector get_vRight() {
        return new Vector(_vRight);
    }
}