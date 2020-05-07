package scene;

import elements.AmbientLight;
import elements.Camera;
import geometries.Geometries;
import geometries.Geometry;
import geometries.Intersectable;
import primitives.Color;

public class Scene {
    private final String _name;
    private Color _background;
    private AmbientLight _ambientLight;
    private Camera _camera;
    private double _distance;
    private Geometries _geometries = null;

    public Scene(String name) {
        this._name = name;
    }

    public Color getBackground() {
        return _background;
    }

    public void setBackground(Color background) {
        _background = background;
    }

    public AmbientLight getAmbientLight() {
        return _ambientLight;
    }

    public void setAmbientLight(AmbientLight ambientLight) {
        _ambientLight = ambientLight;
    }

    public Camera getCamera() {
        return _camera;
    }

    public void setCamera(Camera camera) {
        _camera = camera;
    }

    public double getDistance() {
        return _distance;
    }

    public void setDistance(double distance) {
        _distance = distance;
    }

    public void addGeometries(Intersectable... geometries) {
        if (_geometries == null) {
            _geometries = new Geometries(geometries);
        } else {
            _geometries.addAll(geometries);
        }
    }

    public Geometries getGeometries() {
        return _geometries;
    }
}