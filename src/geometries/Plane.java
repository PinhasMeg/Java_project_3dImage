package geometries;

import primitives.*;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class Plane extends Geometry {
    Point3D _p;
    Vector _normal;

    /**
     * @param emissionLight
     * @param material
     * @param p1
     * @param p2
     * @param p3
     */
    public Plane(Color emissionLight, Material material, Point3D p1, Point3D p2, Point3D p3) {
        super(emissionLight, material);
        initNormal(p1, p2, p3);
    }

    /**
     * constructor
     *
     * @param emissionLight
     * @param material
     * @param _p
     * @param _normal
     */
    public Plane(Color emissionLight, Material material, Point3D _p, Vector _normal) {
        super(emissionLight, material);
        this._p = new Point3D(_p);
        this._normal = new Vector(_normal);
    }

    /**
     * constructor
     *
     * @param emissionLight
     * @param p1
     * @param p2
     * @param p3
     */
    public Plane(Color emissionLight, Point3D p1, Point3D p2, Point3D p3) {
        super(emissionLight);
        initNormal(p1, p2, p3);
    }

    /**
     * constructor
     *
     * @param p1
     * @param p2
     * @param p3
     */
    public Plane(Point3D p1, Point3D p2, Point3D p3) {
        super();
        initNormal(p1, p2, p3);
    }

    /**
     * constructor
     *
     * @param emissionLight
     * @param _p
     * @param _normal
     */
    public Plane(Color emissionLight, Point3D _p, Vector _normal) {
        super(emissionLight);
        this._p = new Point3D(_p);
        this._normal = new Vector(_normal);
    }

    /**
     * constructor
     *
     * @param _p
     * @param _normal
     */
    public Plane(Point3D _p, Vector _normal) {
        super();
        this._p = new Point3D(_p);
        this._normal = new Vector(_normal);
    }

    /**
     * initiate the normal from 3 points
     *
     * @param p1
     * @param p2
     * @param p3
     */
    private void initNormal(Point3D p1, Point3D p2, Point3D p3) {
        _p = new Point3D(p1);

        Vector U = new Vector(p1, p2);
        Vector V = new Vector(p1, p3);
        Vector N = U.crossProduct(V);
        N.normalize();

        _normal = N;
    }

    @Override
    public Vector getNormal(Point3D p) {
        return _normal;
    }

    public Vector getNormal() {
        return getNormal(null);
    }

    @Override
    public String toString() {
        return " The Plane's point is: " + _p + ", and the normal is: " + _normal + '.';
    }

    @Override
    public List<GeoPoint> findIntersections(Ray ray) {
        Vector p0Q;
        try {
            p0Q = _p.subtract(ray.get_origin());
        } catch (IllegalArgumentException e) {
            return null; // ray starts from point Q - no intersections
        }

        double nv = _normal.dotProduct(ray.get_vector());
        if (isZero(nv)) // ray is parallel to the plane - no intersections
            return null;

        double t = alignZero(_normal.dotProduct(p0Q) / nv);

        return t <= 0 ? null : List.of(new GeoPoint(this, ray.getTargetPoint(t)));
    }
}
