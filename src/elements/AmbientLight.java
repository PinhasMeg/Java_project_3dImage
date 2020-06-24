package elements;

import primitives.Color;

public class AmbientLight extends Light {

    public AmbientLight(Color _intensity, double kA) {
        super(_intensity.scale(kA));
    }
}