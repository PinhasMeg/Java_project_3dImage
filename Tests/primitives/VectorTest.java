package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VectorTest {

    @Test
    void crossProduct() {
    }

    @Test
    void dotProduct() {
    }

    @Test
    void subtract() {
    }

    @Test
    void add() {
        Vector v1 = new Vector(0.0, 1.0, 0.0);
        Vector v2 = new Vector(1.0, 0.0, 0.0);
        Vector v3 = v1.add(v2);
        assertEquals(new Vector(1.0, 1.0, 0.0), v3);
    }

    @Test
    void scale() {
    }

    @Test
    void length() {
    }

    @Test
    void normalize() {
    }
}