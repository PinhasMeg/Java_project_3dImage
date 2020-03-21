package geometries;

import primitives.*;

/**
 * Geometry is the common interface for all geometries
 * using the Getnormal() function
 */
public interface Geometry {
    Vector getNormal(Point3D p);
}
