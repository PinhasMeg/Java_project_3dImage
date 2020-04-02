package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;
import geometries.*;

import static org.junit.jupiter.api.Assertions.*;

class TubeTest {

    @Test
    void getNormal() {
/**
 * Test method for {@link Tube#getNormal(Point3D)}
 */
        Point3D point = new Point3D(1, 2, 3);
        Vector vec = new Vector(2.0, 2.0, 3.0);
        Ray ray = new Ray(point, vec);
        double radius = 5;
        Tube tube = new Tube(radius, ray);
        Point3D p = new Point3D(0.5, 0.5, 0.5);
        Vector normal = tube.getNormal(p);


        Vector ExpResult = new Vector(-0.1690308509457033, -0.50709255283711, -0.8451542547285166);
        assertEquals(normal, ExpResult);


//        Point3D point1 = new Point3D(0, 0, 0);
//        Vector vec1 = new Vector(1.0, 0.0, 0.0);
//        Ray ray1 = new Ray(point1, vec1);
//        double radius1 = 1;
//        Tube tube1 = new Tube(radius1, ray1);
//        Point3D p1 = new Point3D(0.0, 1.0, 0.0);
//        Vector normal1 = tube1.getNormal(p1);
//
//
//        Vector ExpResult1 = new Vector(1.0, 0.0, 0.0);
//        assertEquals(normal1, ExpResult1);
    }
}