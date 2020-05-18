package elements;

import primitives.Color;

public class AmbientLight extends Light {

    public AmbientLight(Color _intensity, double kA) {
        super(_intensity.scale(kA));
    }
    //private final Color _iA;
//    double _kA;

    /*public AmbientLight(Color iA, double kA) {
        _iA = iA.scale(kA);
    }

    public AmbientLight(Color iA) {
        this(iA, 1.d);
    }

    public Color getIntensity() {
        return _iA;
    }
*/
}