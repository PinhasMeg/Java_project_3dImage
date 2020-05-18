package geometries;

import primitives.Point3D;
import primitives.Ray;

import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Geometries implements Intersectable {
    private List<Intersectable> _geometries = new ArrayList<Intersectable>();

    public Geometries(Intersectable... _geometries) {
        add(_geometries);
    }

    public void add(Intersectable... geometries) {
        for (Intersectable geo : geometries) {
            _geometries.add(geo);
        }
    }

    public void addAll(Intersectable... geometries) {
        for (Intersectable geo : geometries) {
            _geometries.add(geo);
        }
    }

    /**
     * Na le Hasbir befrotrot
     *
     * @param ray
     * @return list of Point3D that intersect the osef
     */
    @Override
    public List<GeoPoint> findIntersections(Ray ray) {
        List<GeoPoint> intersections = null;

        for (Intersectable geo : _geometries) {
            List<GeoPoint> tempIntersections = geo.findIntersections(ray);
            if (tempIntersections != null) {
                if (intersections == null)
                    intersections = new LinkedList<>();
                intersections.addAll(tempIntersections);
            }
        }
        return intersections;
    }

    /**
     * @param intersectables
     */
    public void remove(Intersectable... intersectables) {
        for (Intersectable geo : _geometries) {
            _geometries.remove(geo);
        }
    }
}
