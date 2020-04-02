package geometries;

import primitives.*;

import java.util.List;

/**
 * Interface for all geometries with a list of point3D
 */
public interface Intersectable {
    List<Point3D> findIntersections(Ray ray);
}
