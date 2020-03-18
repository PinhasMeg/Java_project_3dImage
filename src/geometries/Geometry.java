package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * Geometry is the commone interface for all geometries
 * using the Getnormal() funtion
 */
public interface Geometry {
    Vector getNormal(Point3D p);
}
