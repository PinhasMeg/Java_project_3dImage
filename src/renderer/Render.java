package renderer;

import elements.*;
import geometries.Geometries;
import geometries.Intersectable;
import primitives.*;
import scene.Scene;

import java.util.List;

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
                List<Point3D> intersectionPoints = geometries.findIntersections(ray);
                if (intersectionPoints == null) {
                    _imageWriter.writePixel(collumn, row, background);
                } else {
                    Point3D closestPoint = getClosestPoint(intersectionPoints);
                    _imageWriter.writePixel(collumn, row, calcColor(closestPoint).getColor());
                }
            }
        }
    }

    private Point3D getClosestPoint(List<Point3D> intersectionPoints) {
        Point3D p0 = _scene.getCamera().get_p0();
        Point3D pt = null;
        double minDist = Double.MAX_VALUE;
        double currentDistance = 0;

        for (Point3D point : intersectionPoints) {
            currentDistance = p0.distance(point);
            if (currentDistance < minDist) {
                minDist = currentDistance;
                pt = point;
            }
        }
        return pt;
    }

    private Color calcColor(Point3D closestPoint) {
        return _scene.getAmbientLight().getIntensity();
    }
}