package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTest {

    /**
     * Test method for {@link Plane#getNormal()}
     */
    @Test
    void testGetNormal() {
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

    /**
     * Test method for {@link Plane#findIntersections(Ray)}
     */
    @Test
    void testFindIntersections() {
        Plane plane = new Plane(
                new Point3D(1.0, 0.0, 0.0),
                new Point3D(0.0, 1.0, 0.0),
                new Point3D(0.0, 0.0, 1.0));
        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray intersects the plane (1 points)
        assertEquals(List.of(new Point3D(1, 0, 0)),
                plane.findIntersections(new Ray(new Point3D(-3, 0.0, 0), new Vector(6, 0, 0))),
                "Ray not intersected the plane as expected");

        // TC02: Ray doesn't intersect the plane (0 points)
        assertNull(plane.findIntersections(new Ray(new Point3D(0.5, 0, 0), new Vector(-5, 0, 0))),
                "Sphere behind Ray");

        // =============== Boundary Values Tests ==================

        // **** Group: Ray is parallel to the plane
        // TC03: Ray is include in the plane (1 points)

        // TC04: Ray doesn't include in the plane (0 points)

        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        // TC14: Ray starts at sphere and goes inside (1 points)
        // TC15: Ray starts inside (1 points)
        // TC16: Ray starts at the center (1 points)
        // TC17: Ray starts at sphere and goes outside (0 points)
        // TC18: Ray starts after sphere (0 points)

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point


    }
}