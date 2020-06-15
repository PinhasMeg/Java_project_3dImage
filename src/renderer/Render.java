package renderer;

import elements.*;
import geometries.*;
import org.junit.jupiter.api.Disabled;
import primitives.*;
import scene.Scene;

import java.util.List;

import static geometries.Intersectable.GeoPoint;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 *
 */
public class Render {
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;

    private final ImageWriter _imageWriter;
    private final Scene _scene;
    private final int SPARE_THREADS = 2;
    private double _supersamplingDensity = 0d;
    private int _rayCounter = 1;
    private int _threads = 1;
    private boolean _print = false;

    public Render(ImageWriter imageWriter, Scene scene) {
        this._imageWriter = imageWriter;
        this._scene = scene;
        this._supersamplingDensity = 0d;
    }

    /**
     *
     * @return
     */
    public double getSupersamplingDensity() {
        return _supersamplingDensity;
    }

    /**
     *
     * @param supersamplingDensity
     * @return
     */
    public Render setSupersamplingDensity(double supersamplingDensity) {
        _supersamplingDensity = supersamplingDensity;
        return this;
    }

    /**
     *
     * @return
     */
    public int getRayCounter() {
        return _rayCounter;
    }

    /**
     *
     * @param rayCounter
     * @return
     */
    public Render setRayCounter(int rayCounter) {
        _rayCounter = rayCounter;
        return this;
    }

    /**
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

    /**
     *
     */
    public void writeToImage() {
        _imageWriter.writeToImage();
    }

    /**
     *
     */
    public void renderImage() {
        Camera camera = _scene.getCamera();
        Intersectable geometries = _scene.getGeometries();
        Color background = _scene.getBackground();
        AmbientLight ambientLight = _scene.getAmbientLight();
        double distance = _scene.getDistance();

        int Nx = _imageWriter.getNx();
        int Ny = _imageWriter.getNy();
        double width = _imageWriter.getWidth();
        double height = _imageWriter.getHeight();

        final Pixel thePixel = new Pixel(Ny, Nx);

        Thread[] threads = new Thread[_threads];
        for (int i = _threads - 1; i >= 0; --i) {
            threads[i] = new Thread(() -> {
                Pixel pixel = new Pixel();
                Color resultingColor;
                while (thePixel.nextPixel(pixel)) {
                    if (_supersamplingDensity == 0d) {//         without supersampling
                        resultingColor = getPixelRayColor(camera, background, distance, Nx, Ny, width, height, pixel);
                    } else {
                        resultingColor = getPixelRaysBeamColor(camera, distance, Nx, Ny, width, height, pixel);
                    }
                    _imageWriter.writePixel(pixel.col, pixel.row, resultingColor.getColor());
                }
            });
        }
        // Start threads
        for (Thread thread : threads) thread.start();
        // Wait for all threads to finish
        for (Thread thread : threads)
            try {
                thread.join();
            } catch (Exception e) {
            }
        if (_print) System.out.printf("\r100%%\n");
    }

    /**
     * @param camera
     * @param distance
     * @param nx
     * @param ny
     * @param width
     * @param height
     * @param pixel
     * @return
     */
    private Color getPixelRaysBeamColor(Camera camera, double distance, int nx, int ny, double width, double height, Pixel pixel) {
        List<Ray> rays = camera.constructRayBeamThroughPixel(nx, ny, pixel.col, pixel.row, distance, width, height, _supersamplingDensity, _rayCounter);
        Color resultColor = calcColor(rays);
        resultColor = resultColor.add(_scene.getAmbientLight().getIntensity());
        return resultColor;
    }

    /**
     * @param camera
     * @param background
     * @param distance
     * @param nx
     * @param ny
     * @param width
     * @param height
     * @param pixel
     * @return
     */
    private Color getPixelRayColor(Camera camera, Color background, double distance, int nx, int ny, double width, double height, Pixel pixel) {
        Ray ray = camera.constructRayThroughPixel(nx, ny, pixel.col, pixel.row, distance, width, height);
        GeoPoint closestPoint = findClosestIntersection(ray);
        Color resultingColor = background;
        if (closestPoint != null) {
            resultingColor = calcColor(closestPoint, ray);
        }
        resultingColor = resultingColor.add(_scene.getAmbientLight().getIntensity());
        return resultingColor;
    }


    /**
     * Set multithreading <br>
     * - if the parameter is 0 - number of coress less 2 is taken
     *
     * @param threads number of threads
     * @return the Render object itself
     */
    public Render setMultithreading(int threads) {
        if (threads < 0)
            throw new IllegalArgumentException("Multithreading parameter must be 0 or higher");
        if (threads != 0)
            _threads = threads;
        else {
            int cores = Runtime.getRuntime().availableProcessors() - SPARE_THREADS;
            _threads = cores <= 2 ? 1 : cores;
        }
        return this;
    }

    /**
     * Set debug printing on
     *
     * @return the Render object itself
     */
    public Render setDebugPrint() {
        _print = true;
        return this;
    }

    private GeoPoint getClosestPoint(List<GeoPoint> intersectionPoints) {

        if (intersectionPoints == null) {
            return null;
        }

        GeoPoint result = null;

        Point3D p0 = _scene.getCamera().getP0();
        double minDist = Double.MAX_VALUE;
        double currentDistance;

        for (GeoPoint geoPoint : intersectionPoints) {
            currentDistance = p0.distance(geoPoint.getPoint());
            if (currentDistance < minDist) {
                minDist = currentDistance;
                result = geoPoint;
            }
        }
        return result;
    }

    /**
     * Find intersections of a ray with the scene geometries and get the
     * intersection point that is closest to the ray head. If there are no
     * intersections, null will be returned.
     *
     * @param ray intersecting the scene
     * @return the closest point
     */
    private GeoPoint findClosestIntersection(Ray ray) {

        if (ray == null) {
            return null;
        }

        GeoPoint closestPoint = null;
        double closestDistance = Double.MAX_VALUE;
        Point3D ray_p0 = ray.get_origin();

        List<GeoPoint> intersections = _scene.getGeometries().findIntersections(ray);
        if (intersections == null)
            return null;

        for (GeoPoint geoPoint : intersections) {
            double distance = ray_p0.distance(geoPoint.getPoint());
            if (distance < closestDistance) {
                closestDistance = distance;
                closestPoint = geoPoint;
            }
        }
        return closestPoint;
    }

    /**
     * @param inRays List of surrounding rays
     * @return average color
     * @author Reuven Smadja
     */
    private Color calcColor(List<Ray> inRays) {
        Color bkg = _scene.getBackground();
        Color color = Color.BLACK;
        for (Ray ray : inRays) {
            GeoPoint gp = findClosestIntersection(ray);
            if (gp == null) {
                color = color.add(bkg);
            } else {
                color = color.add(calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, 1d));
            }
        }
//        color = color.add(_scene.getAmbientLight().getIntensity());
        int size = inRays.size();
        return (size == 1) ? color : color.reduce(size);
    }

    /**
     * @param geoPoint
     * @param inRay
     * @return
     */
    private Color calcColor(GeoPoint geoPoint, Ray inRay) {
        Color color = calcColor(geoPoint, inRay, MAX_CALC_COLOR_LEVEL, 1.0);
//        color = color.add(_scene.getAmbientLight().getIntensity());
        return color;
    }

    /**
     * @param geoPoint
     * @param inRay
     * @param level
     * @param k
     * @return
     */
    private Color calcColor(GeoPoint geoPoint, Ray inRay, int level, double k) {
        if (level == 1) {
            return Color.BLACK;
        }

        Point3D pointGeo = geoPoint.getPoint();
        Geometry geometryGeo = geoPoint.getGeometry();
        Color color = geometryGeo.getEmissionLight();

        Material material = geometryGeo.getMaterial();
        int nShininess = material.getnShininess();
        double kd = material.getkD();
        double ks = material.getkS();

        Vector v = pointGeo.subtract(_scene.getCamera().getP0()).normalize();
        Vector n = geometryGeo.getNormal(pointGeo);

        color = getColorLightSources(geoPoint, k, color, v, n, nShininess, kd, ks);

        double kr = geometryGeo.getMaterial().getKr();
        double kkr = k * kr;

        if (kkr > MIN_CALC_COLOR_K) {
            color = getColorSecondaryRay(level, color, kr, kkr, constructReflectedRay(pointGeo, inRay, n));
        }

        double kt = geometryGeo.getMaterial().getKt();
        double kkt = k * kt;

        if (kkt > MIN_CALC_COLOR_K) {
            color = getColorSecondaryRay(level, color, kt, kkt, constructRefractedRay(pointGeo, inRay, n));
        }
        return color;
    }

    /**
     * @param level
     * @param color
     * @param krt
     * @param kkrt
     * @param secondaryRay
     * @return
     */
    private Color getColorSecondaryRay(int level, Color color, double krt, double kkrt, Ray secondaryRay) {
        GeoPoint geoPoint = findClosestIntersection(secondaryRay);
        if (geoPoint != null) {
            color = color.add(calcColor(geoPoint, secondaryRay, level - 1, kkrt).scale(krt));
        }
        return color;
    }

    /**
     * @param geoPoint
     * @param k
     * @param color
     * @param v
     * @param n
     * @param nShininess
     * @param kd
     * @param ks
     * @return
     */
    private Color getColorLightSources(GeoPoint geoPoint, double k, Color color, Vector v, Vector n, int nShininess, double kd, double ks) {
        Point3D pointGeo = geoPoint.getPoint();
        if (_scene.getLightSources() != null) {
            for (LightSource lightSource : _scene.getLightSources()) {
                Vector l = lightSource.getL(pointGeo);
                double nl = n.dotProduct(l);
                double nv = n.dotProduct(v);
                double ktr;
                if (nl * nv > 0) {
//              if( nl != 0 && nv != 0 && sign(nv)==sign(nl) ) {
//                if (unshaded(lightSource, l, n, geoPoint)) {
//                    ktr = 1d;
//                } else {
//                    ktr = 0d;
//                }
                    ktr = transparency(lightSource, l, n, geoPoint);
                    if (ktr * k > MIN_CALC_COLOR_K) {
                        Color lightIntensity = lightSource.getIntensity(pointGeo).scale(ktr);
                        color = color.add(
                                calcDiffusive(kd, nl, lightIntensity),
                                calcSpecular(ks, l, n, nl, v, nShininess, lightIntensity));
                    }
                }
            }
        }
        return color;
    }

    /**
     * @param pointGeo
     * @param inRay
     * @param n
     * @return
     */
    private Ray constructRefractedRay(Point3D pointGeo, Ray inRay, Vector n) {
        return new Ray(pointGeo, inRay.get_vector(), n);
    }

    /**
     *
     * @param pointGeo
     * @param inRay
     * @param n
     * @return
     */
    private Ray constructReflectedRay(Point3D pointGeo, Ray inRay, Vector n) {
        //r = v - 2.(v.n).n
        Vector v = inRay.get_vector();
        double vn = v.dotProduct(n);

        if (vn == 0) {
            return null;
        }

        Vector r = v.subtract(n.scale(2 * vn));
        return new Ray(pointGeo, r, n);
    }

    /**
     * Calculate Specular component of light reflection.
     *
     * @param ks         specular component coef
     * @param l          direction from light to point
     * @param n          normal to surface at the point
     * @param nl         dot-product n*l
     * @param V          direction from point of view to point
     * @param nShininess shininess level
     * @param ip         light intensity at the point
     * @return specular component light effect at the point
     * @author Dan Zilberstein ( modified by me)
     * <p>
     * Specular light is light from a point light source which will be
     * reflected specularly.
     * <p>
     * Specularly reflected light is light which is reflected in a
     * mirror-like fashion, as from a shiny surface. As shown in Figure IV.3,
     * specularly reflected light leaves a surface with its angle of reflection
     * approximately equal to its angle of incidence. This is the main part of
     * the reflected light from a polished or glossy surface. Specular reflections
     * are the cause of “specular highlights,” i.e., bright spots on curved surfaces
     * where intense specular reflection occurs.
     * <p>
     * Finally, the Phong model has a provision for a highlight, or specular, component, which reflects light in a
     * shiny way. This is defined by [rs,gs,bs](-V.R)^p, where R is the mirror reflection direction vector we discussed
     * in class (and also used for ray tracing), and where p is a specular power. The higher the value of p, the shinier
     * the surface.
     */
    private Color calcSpecular(double ks, Vector l, Vector n, double nl, Vector V, int nShininess, Color ip) {
        if (isZero(nl)) {
            throw new IllegalArgumentException("nl cannot be Zero for scaling the normal vector");
        }
        Vector R = l.add(n.scale(-2 * nl)); // nl must not be zero!
        double VR = alignZero(V.dotProduct(R));
        if (VR >= 0) {
            return Color.BLACK; // view from direction opposite to r vector
        }
        // [rs,gs,bs]ks(-V.R)^p
        return ip.scale(ks * Math.pow(-1d * VR, nShininess));
    }

    /**
     * Calculate Diffusive component of light reflection.
     * <p>
     * Diffuse light is light from a point light source which will be
     * reflected diffusely
     *
     * @param kd diffusive component coef
     * @param nl dot-product n*l
     * @param ip light intensity at the point
     * @return diffusive component of light reflection
     * @author Dan Zilberstein (modified by E.G.)
     * <p>
     * Diffusely reflected light is light which is reflected evenly
     * in all directions away from the surface. This is the predominant mode of
     * reflection for non-shiny surfaces
     * <p>
     * The diffuse component is that dot product n•L. It approximates light, originally from light source L,
     * reflecting from a surface which is diffuse, or non-glossy. One example of a non-glossysurface is paper.
     * In general, you'll also want this to have a non-gray color value,
     * so this term would in general be a color defined as: [rd,gd,bd](n•L)
     */
    private Color calcDiffusive(double kd, double nl, Color ip) {
        return ip.scale(Math.abs(nl) * kd);
    }

    private boolean sign(double val) {
        return (val > 0d);
    }

    private double transparency(LightSource light, Vector l, Vector n, GeoPoint geopoint) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(geopoint.getPoint(), lightDirection, n);
        Point3D pointGeo = geopoint.getPoint();
        List<Ray> shadowsRays = lightRay.getBeamThroughPoint(pointGeo, 5, 2, 80);
        //TODO
        List<GeoPoint> intersections = _scene.getGeometries().findIntersections(lightRay);
        if (intersections == null) {
            return 1d;
        }
        double lightDistance = light.getDistance(pointGeo);
        double ktr = 1d;
        for (GeoPoint gp : intersections) {
            if (alignZero(gp.getPoint().distance(pointGeo) - lightDistance) <= 0) {
                ktr *= gp.getGeometry().getMaterial().getKt();
                if (ktr < MIN_CALC_COLOR_K) {
                    return 0.0;
                }
            }
        }
        return ktr;
    }

    @Deprecated
    @Disabled("for demonstration purposes")
    private boolean unshaded(LightSource light, Vector l, Vector n, GeoPoint geopoint) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(geopoint.getPoint(), lightDirection, n);
        Point3D pointGeo = geopoint.getPoint();

        List<GeoPoint> intersections = _scene.getGeometries().findIntersections(lightRay);
        if (intersections == null) {
            return true;
        }
        double lightDistance = light.getDistance(pointGeo);
        for (GeoPoint gp : intersections) {
            if (alignZero(gp.getPoint().distance(pointGeo) - lightDistance) <= 0
                    && gp.getGeometry().getMaterial().getKt() == 0) {
                return false;
            }
        }
        return true;
    }

    @Deprecated
    @Disabled("for demonstration purposes")
    private boolean unshaded_0(LightSource light, Vector l, Vector n, GeoPoint geopoint) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(geopoint.getPoint(), lightDirection, n);
        Point3D pointGeo = geopoint.getPoint();

        List<GeoPoint> intersections = _scene.getGeometries().findIntersections(lightRay);
        if (intersections == null) {
            return true;
        }
        // Flat geometry cannot self intersect
        if (geopoint.getGeometry() instanceof FlatGeometry) {
            intersections.remove(geopoint);
        }

        double lightDistance = light.getDistance(pointGeo);
        for (GeoPoint gp : intersections) {
            double temp = gp.getPoint().distance(pointGeo) - lightDistance;
            if (alignZero(temp) <= 0)
                return false;
        }
        return true;
    }

    @Deprecated
    @Disabled("for demonstration purposes")
    private boolean occluded(LightSource light, Vector l, Vector n, GeoPoint geopoint) {
        Point3D geometryPoint = geopoint.getPoint();
        Vector lightDirection = light.getL(geometryPoint);
        lightDirection.scale(-1);

        Vector epsVector = geopoint.getGeometry().getNormal(geometryPoint);
        epsVector.scale(epsVector.dotProduct(lightDirection) > 0 ? 2 : -2);
        geometryPoint.add(epsVector);
        Ray lightRay = new Ray(geometryPoint, lightDirection);
        List<GeoPoint> intersections = _scene.getGeometries().findIntersections(lightRay);

        // Flat geometry cannot self intersect
        if (geopoint.getGeometry() instanceof FlatGeometry) {
            intersections.remove(geopoint);
        }

        for (GeoPoint entry : intersections)
            if (entry.getGeometry().getMaterial().getKt() == 0)
                return true;
        return false;
    }

    /**
     * Pixel is an internal helper class whose objects are associated with a Render object that
     * they are generated in scope of. It is used for multithreading in the Renderer and for follow up
     * its progress.<br/>
     * There is a main follow up object and several secondary objects - one in each thread.
     *
     * @author Dan
     */
    private class Pixel {
        public volatile int row = 0;
        public volatile int col = -1;
        private long _maxRows = 0;
        private long _maxCols = 0;
        private long _pixels = 0;
        private long _counter = 0;
        private int _percents = 0;
        private long _nextCounter = 0;

        /**
         * The constructor for initializing the main follow up Pixel object
         *
         * @param maxRows the amount of pixel rows
         * @param maxCols the amount of pixel columns
         */
        public Pixel(int maxRows, int maxCols) {
            _maxRows = maxRows;
            _maxCols = maxCols;
            _pixels = maxRows * maxCols;
            _nextCounter = _pixels / 100;
            if (Render.this._print) System.out.printf("\r %02d%%", _percents);
        }

        /**
         * Default constructor for secondary Pixel objects
         */
        public Pixel() {
        }

        /**
         * Internal function for thread-safe manipulating of main follow up Pixel object - this function is
         * critical section for all the threads, and main Pixel object data is the shared data of this critical
         * section.<br/>
         * The function provides next pixel number each call.
         *
         * @param target target secondary Pixel object to copy the row/column of the next pixel
         * @return the progress percentage for follow up: if it is 0 - nothing to print, if it is -1 - the task is
         * finished, any other value - the progress percentage (only when it changes)
         */
        private synchronized int nextP(Pixel target) {
            ++col;
            ++_counter;
            if (col < _maxCols) {
                target.row = this.row;
                target.col = this.col;
                if (_print && _counter == _nextCounter) {
                    ++_percents;
                    _nextCounter = _pixels * (_percents + 1) / 100;
                    return _percents;
                }
                return 0;
            }
            ++row;
            if (row < _maxRows) {
                col = 0;
                if (_print && _counter == _nextCounter) {
                    ++_percents;
                    _nextCounter = _pixels * (_percents + 1) / 100;
                    return _percents;
                }
                return 0;
            }
            return -1;
        }

        /**
         * Public function for getting next pixel number into secondary Pixel object.
         * The function prints also progress percentage in the console window.
         *
         * @param target target secondary Pixel object to copy the row/column of the next pixel
         * @return true if the work still in progress, -1 if it's done
         */
        public boolean nextPixel(Pixel target) {
            int percents = nextP(target);
            if (_print && percents > 0)
                System.out.printf("\r %02d%%", percents);
            if (percents >= 0)
                return true;
            if (_print) System.out.printf("\r %02d%%", 100);
            return false;

        }
    }
}
