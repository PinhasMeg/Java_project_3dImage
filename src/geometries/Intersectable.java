package geometries;

import primitives.*;


import java.util.List;
import java.util.Objects;

/**
 * Intersectable is a common interface for all geometries that are able
 * to intersect from a ray to their entity (Shape)
 */
public interface Intersectable {

    /**
     * GeoPoint is just a tuple holding
     * references to a specific point in a specific geometry
     */
    public static class GeoPoint {

        protected final Geometry _geometry;
        protected final Point3D _point;

        public GeoPoint(Geometry _geometry, Point3D pt) {
            this._geometry = _geometry;
            _point = pt;
        }

        public Point3D getPoint() {
            return _point;
        }

        public Geometry getGeometry() {
            return _geometry;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return _geometry.equals(geoPoint._geometry) &&
                    _point.equals(geoPoint._point);
        }
    }

    /**
     * @param ray ray pointing toward a Geometry
     * @return List<GeoPoint> return values
     */
    List<GeoPoint> findIntersections(Ray ray);
}