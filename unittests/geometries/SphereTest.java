package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import static org.junit.jupiter.api.Assertions.*;

class SphereTest {

    /**
     * Test method for {@link Sphere#getNormal(Point3D)}
     */
    @Test
    void getNormal() {
        Point3D center = new Point3D(1, 2, 3);
        double radius = 5;
        Sphere sphere = new Sphere(radius, center);
        Point3D point = new Point3D(0.5, 0.5, 0.5);
        Vector normal = new Vector(sphere.getNormal(point));
        Vector ExpResult = new Vector(-0.1690308509457033, -0.50709255283711, -0.8451542547285166);
        assertEquals(normal, ExpResult);
    }
}