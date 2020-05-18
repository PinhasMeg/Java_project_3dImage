package renderer;

import elements.*;
import geometries.Intersectable;
import primitives.*;
import scene.Scene;

import java.util.List;

import geometries.Intersectable.GeoPoint;

import static primitives.Util.alignZero;

/**
 * create, from the scene, the matrice's color
 */
public class Render {
    private final ImageWriter _imageWriter;
    private final Scene _scene;

    public Render(ImageWriter imageWriter, Scene scene) {
        this._imageWriter = imageWriter;
        this._scene = scene;
    }

    /**
     * This function print the grid on the image
     *
     * @param interval
     * @param color
     */
    public void printGrid(int interval, java.awt.Color color) {
        int Nx = _imageWriter.getNx();
        int Ny = _imageWriter.getNy();
        for (int i = 0; i < Ny; i++) {
            for (int j = 0; j < Nx; j++) {
                if (i % interval == 0 || j % interval == 0) {
                    _imageWriter.writePixel(j, i, color);
                }
            }
        }
    }

    public void writeToImage() {
        _imageWriter.writeToImage();
    }

    public void renderImage() {
        Camera camera = _scene.getCamera();
        Intersectable geometries = _scene.getGeometries();
        java.awt.Color background = _scene.getBackground().getColor();
        AmbientLight ambientLight = _scene.getAmbientLight();
        double distance = _scene.getDistance();

        int Nx = _imageWriter.getNx();
        int Ny = _imageWriter.getNy();
        double width = _imageWriter.getWidth();
        double height = _imageWriter.getHeight();

        for (int row = 0; row < Ny; row++) {
            for (int collumn = 0; collumn < Nx; collumn++) {
                Ray ray = camera.constructRayThroughPixel(Nx, Ny, collumn, row, distance, width, height);
                List<GeoPoint> intersectionPoints = geometries.findIntersections(ray);
                if (intersectionPoints == null) {
                    _imageWriter.writePixel(collumn, row, background);
                } else {
                    GeoPoint closestPoint = getClosestPoint(intersectionPoints);
                    java.awt.Color pixelColor = calcColor(closestPoint).getColor();
                    _imageWriter.writePixel(collumn, row, pixelColor);
                }
            }
        }
    }

    private GeoPoint getClosestPoint(List<GeoPoint> intersectionPoints) {
        GeoPoint result = null;
        double mindist = Double.MAX_VALUE;

        Point3D p0 = this._scene.getCamera().getP0();

        for (GeoPoint geo : intersectionPoints) {
            Point3D pt = geo.getPoint();
            double distance = p0.distance(pt);
            if (distance < mindist) {
                mindist = distance;
                result = geo;
            }
        }
        return result;
    }

    /**
     * Calculate the color intensity in a point
     *
     * @param coloredPoint intersection the point for which the color is required
     * @return the color intensity
     */
    private Color calcColor(GeoPoint coloredPoint) {
        List<LightSource> lightSources = _scene.getLightsSources();
        Color result = _scene.getAmbientLight().getIntensity();
        result = result.add(coloredPoint.getGeometry().getEmissionLight());

        Vector v = coloredPoint.getPoint().subtract(_scene.getCamera().getP0()).normalize();
        Vector n = coloredPoint.getGeometry().getNormal(coloredPoint.getPoint());

        Material material = coloredPoint.getGeometry().getMaterial();
        int nShininess = material.getnShininess();
        double kd = material.getKd();
        double ks = material.getKs();

        if (lightSources != null) {
            for (LightSource lightSource : lightSources) {
                Vector l = lightSource.getL(coloredPoint.getPoint());
                double nl = alignZero(n.dotProduct(l));
                double nv = alignZero(n.dotProduct(v));

                if (sign(nl) == sign(nv)) {
                    Color ip = lightSource.getIntensity(coloredPoint.getPoint());
                    result = result.add(
                            calcDiffusive(kd, nl, ip),
                            calcSpecular(ks, l, n, nl, v, nShininess, ip));
                }
            }
        }
        return result;
    }

    /**
     * Calculate Specular component of light reflection.
     *
     * @param ks         specular component coef
     * @param l          direction from light to point
     * @param n          normal to surface at the point
     * @param nl         dot-product n*l
     * @param v          direction from point of view to point
     * @param nShininess shininess level
     * @param ip         light intensity at the point
     * @return specular component light effect at the point
     * <p>
     * Finally, the Phong model has a provision for a highlight, or specular, component, which reflects light in a
     * shiny way. This is defined by [rs,gs,bs](-V.R)^p, where R is the mirror reflection direction vector
     * (and also used for ray tracing), and where p is a specular power. The higher the value of p, the shinier
     * the surface.
     */
    private Color calcSpecular(double ks, Vector l, Vector n, double nl, Vector v, int nShininess, Color ip) {
        double p = nShininess;

        Vector R = l.add(n.scale(-2 * nl)); // nl must not be zero!
        double minusVR = -alignZero(R.dotProduct(v));
        if (minusVR <= 0) {
            return Color.BLACK; // view from direction opposite to r vector
        }
        // [rs,gs,bs](-V.R)^p
        return ip.scale(ks * Math.pow(minusVR, p));
    }

    /**
     * Calculate Diffusive component of light reflection.
     *
     * @param kd diffusive component coef
     * @param nl dot-product n*l
     * @param ip light intensity at the point
     * @return diffusive component of light reflection
     * <p>
     * The diffuse component is dot product n•L. It approximates light, originally
     * from light source L, reflecting from a surface which is diffuse, or non-glossy. One example of a non-glossy
     * surface is paper. In general, you'll also want this to have a non-gray color value,
     * so this term would in general be a color defined as: [rd,gd,bd](n•L)
     */
    private Color calcDiffusive(double kd, double nl, Color ip) {
        if (nl < 0) {
            nl = -nl;
        }

        return ip.scale(nl * kd);
    }

    /**
     * @param val
     * @return
     */
    private boolean sign(double val) {
        return (val > 0d);
    }
}