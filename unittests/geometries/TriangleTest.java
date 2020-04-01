package geometries;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import primitives.*;

class TriangleTest {

    /**
     * Test method for {@link Triangle#getNormal(Point3D)}
     */
    @Test
    void getNormal() {
        Point3D p1 = new Point3D(1, 2, 3);
        Point3D p2 = new Point3D(1, 3, 2);
        Point3D p3 = new Point3D(2, 1, 3);
        Triangle triangle = new Triangle(p1, p2, p3);

        Vector vec = new Vector(-0.5773502691896258, -0.5773502691896258, -0.5773502691896258);
        Vector ExpResult = new Vector(triangle.getNormal(p1));
        assertEquals(ExpResult, vec);

    }
}