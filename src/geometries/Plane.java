package geometries;

import primitives.*;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class Plane extends FlatGeometry {

    Point3D _p; //Q
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
        //from FlatGeometry
        this._plane = null;

        _p = new Point3D(p1);

        Vector U = new Vector(p1, p2);
        Vector V = new Vector(p1, p3);

        Vector N = U.crossProduct(V);
        N.normalize();

        _normal = N;
    }

    /**
     * @param emissionLight
     * @param p1
     * @param p2
     * @param p3
     */
    public Plane(Color emissionLight, Point3D p1, Point3D p2, Point3D p3) {
        this(emissionLight, new Material(0, 0, 0), p1, p2, p3);
    }

    /**
     * @param p1
     * @param p2
     * @param p3
     */
    public Plane(Point3D p1, Point3D p2, Point3D p3) {
        this(Color.BLACK, p1, p2, p3);
    }

    /**
     * @param _p
     * @param _normal
     */
    public Plane(Point3D _p, Vector _normal) {
        super(Color.BLACK, new Material(0, 0, 0));

        this._p = new Point3D(_p);
        this._normal = new Vector(_normal);
        this._plane = null;
    }

    /**
     * @param emissionLight
     * @param material
     * @param _p
     * @param _normal
     */
    public Plane(Color emissionLight, Material material, Point3D _p, Vector _normal) {
        super(emissionLight, material);

        this._p = new Point3D(_p);
        this._normal = new Vector(_normal);
        this._plane = null;
    }

    /**
     * @param emissionLight
     * @param _p
     * @param _normal
     */
    public Plane(Color emissionLight, Point3D _p, Vector _normal) {
        super(emissionLight, new Material(0, 0, 0));

        this._p = new Point3D(_p);
        this._normal = new Vector(_normal);
        this._plane = null;
    }

    /**
     * @param p
     * @return
     */
    @Override
    public Vector getNormal(Point3D p) {
        return _normal;
    }

    /**
     * @param ray
     * @param maxDistance
     * @return
     */
    @Override
    public List<GeoPoint> findIntersections(Ray ray, double maxDistance) {
        Vector p0Q;
        try {
            p0Q = _p.subtract(ray.get_origin());
        } catch (IllegalArgumentException e) {
            return null; // ray starts from point Q - no intersections
        }

        double nv = _normal.dotProduct(ray.get_vector());
        if (isZero(nv)) { // ray is parallel to the plane - no intersections
            return null;
        }
        double t = alignZero(_normal.dotProduct(p0Q) / nv);
        double tdist = alignZero(maxDistance - t);

        if ((t <= 0) || (tdist <= 0)) {
            return null;
        } else {
            return List.of(new GeoPoint(this, ray.getTargetPoint(t)));
        }
    }
}