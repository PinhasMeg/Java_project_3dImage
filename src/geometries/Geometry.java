package geometries;

import primitives.*;

/**
 * interface Geometry is the basic interface for all geometric objects
 * who are implementing getNormal method.
 */
public abstract class Geometry implements Intersectable {

    protected Color _emission;
    protected Material _material;

    /**
     * @param emission
     * @param material
     */
    public Geometry(Color emission, Material material) {
        this._emission = new Color(emission);
        this._material = new Material(material);
    }

    /**
     * @param _emission
     */
    public Geometry(Color _emission) {
        this(_emission, new Material(0d, 0d, 0));
    }

    /**
     *
     */
    public Geometry() {
        this(Color.BLACK);
    }

    /**
     * get the emmission light
     *
     * @return
     */
    public Color getEmissionLight() {
        return (_emission);
    }

    /**
     * get material
     *
     * @return
     */
    public Material getMaterial() {
        return _material;
    }

    /**
     * @param p
     * @return
     */
    abstract public Vector getNormal(Point3D p);
}