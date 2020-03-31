package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTest {

    @Test
    void getNormal() {
        Plane p1 = new Plane(
                new Point3D(0.0, 1.0, 0.0),
                new Point3D(1.0, 0.0, 0.0),
                new Point3D(0.0, 0.0, 1.0));
        Vector v1 = p1.getNormal();


        Plane p2 = new Plane(
                new Point3D(0.0, 0.0, 1.0),
                new Point3D(1.0, 0.0, 0.0),
                new Point3D(0.0, 1.0, 0.0));
        Vector v2 = p2.getNormal();

        Plane p3 = new Plane(

                new Point3D(1.0, 0.0, 0.0),
                new Point3D(0.0, 0.0, 1.0),
                new Point3D(0.0, 1.0, 0.0));
        Vector v3 = p3.getNormal();

        assertNotEquals(v1, v2);
        assertEquals(v1, v3, "not same direction!");
    }
}