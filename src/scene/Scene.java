package scene;

import elements.AmbientLight;
import elements.Camera;
import elements.LightSource;
import geometries.Geometries;
import geometries.Geometry;
import geometries.Intersectable;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;

public class Scene {
    private final String _name;
    private Color _background;
    private AmbientLight _ambientLight;
    private Camera _camera;
    private double _distance;
    private Geometries _geometries = null;
    private List<LightSource> _lights = new LinkedList<LightSource>();

    /**
     * get the LightSource's list
     *
     * @return
     */
    public List<LightSource> get_lights() {
        return _lights;
    }

    /**
     * @param name
     */
    public Scene(String name) {
        this._name = name;
    }

    /**
     * @return
     */
    public Color getBackground() {
        return _background;
    }

    /**
     * @param background
     */
    public void setBackground(Color background) {
        _background = background;
    }

    /**
     * @return
     */
    public AmbientLight getAmbientLight() {
        return _ambientLight;
    }

    /**
     * @param ambientLight
     */
    public void setAmbientLight(AmbientLight ambientLight) {
        _ambientLight = ambientLight;
    }

    /**
     * @return
     */
    public Camera getCamera() {
        return _camera;
    }

    /**
     * @param camera
     */
    public void setCamera(Camera camera) {
        _camera = camera;
    }

    /**
     * @return
     */
    public double getDistance() {
        return _distance;
    }

    /**
     * @param distance
     */
    public void setDistance(double distance) {
        _distance = distance;
    }

    /**
     * @param geometries
     */
    public void addGeometries(Intersectable... geometries) {
        if (_geometries == null) {
            _geometries = new Geometries(geometries);
        } else {
            _geometries.addAll(geometries);
        }
    }

    /**
     * get geometries
     *
     * @return
     */
    public Geometries getGeometries() {
        return _geometries;
    }

    /**
     * add lights to the list
     *
     * @param light
     */
    public void addLights(LightSource... light) {
        if (_lights == null) {
            _lights = new LinkedList<>();
        }
        for (LightSource i : light) {
            _lights.add(i);
        }

    }
}