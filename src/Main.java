//Pinhas Meguideche - 1362626 - pinoubg@live.fr
//Yoel Zeitoun - 1308634 - yoelzeit@gmail.com

import elements.*;
import geometries.*;
import primitives.*;
import renderer.*;
import scene.*;

import static java.lang.System.out;
import static primitives.Util.*;

public final class Main {

    /**
     * Mini Project 1
     *
     * @param args
     */
    public static void main(String[] args) {

        Scene scene;
        scene = new Scene.SceneBuilder("Mini Project1")
                .addAmbientLight(new AmbientLight(Color.BLACK, 0))
                .addCamera(
                        new Camera(
                                new Point3D(0, -400, 0),
                                new Vector(0, 0, 1),
                                new Vector(0, -1, 0)))
                .addDistance(100)
                .addBackground(new Color(java.awt.Color.gray))
                .build();


        for (long i = -2000; i < 2001; i += 500) {
            for (long j = 0; j < 2001; j += 500) {
                scene.addGeometries(
                        new Triangle(
                                new Color(32, 32, 32),
                                new Material(0.5, 0.1, 60),
                                new Point3D(i, 0, j),
                                new Point3D(i + 500, 0, j),
                                new Point3D(i, 0, j + 500)),

                        new Triangle(
                                new Color(70, 70, 70),
                                new Material(0.5, 0.1, 60),
                                new Point3D(i, 0, j + 500),
                                new Point3D(i + 500, 0, j + 500),
                                new Point3D(i + 500, 0, j))
                );
            }
        }

        scene.addGeometries(
                new Plane(
                        new Color(40, 0, 50),
                        new Point3D(-2000, 0, -2000),
                        new Vector(1, 0, 0)),

                new Plane(
                        new Color(50, 0, 50),
                        new Point3D(0, 0, 2000),
                        new Vector(0, 0, 1)),

                new Plane(
                        new Color(40, 0, 50),
                        new Point3D(2000, 0, 0),
                        new Vector(1, 0, 0)
                ),
                new Sphere(
                        new Color(java.awt.Color.BLUE),
                        new Material(0.5, 0.9, 80), 150,
                        new Point3D(0, -150, 750)),

                new Sphere(new Color(java.awt.Color.GREEN),
                        new Material(0.5, 0.9, 50), 300,
                        new Point3D(-750, -300, 750)),

                new Sphere(new Color(java.awt.Color.YELLOW),
                        new Material(0.5, 0.9, 60), 100,
                        new Point3D(1000, -100, 1000)),

                new Sphere(new Color(java.awt.Color.magenta),
                        new Material(0.5, 0.9, 70), 250,
                        new Point3D(0, -250, 1500)),

                new Sphere(new Color(java.awt.Color.RED),
                        new Material(0.5, 0.9, 90), 150,
                        new Point3D(1000, -150, 750))

        );


//        scene.addLights(new DirectionalLight(new Color(300, 150, 150), new Vector(0, 0, 1)));
//        scene.addLights(new PointLight(new Color(50, 150, 55),
//                new Point3D(100, -100, 200),1, 1E-5, 1.5E-7));
        scene.addLights(
                new SpotLight(
                        new Color(255, 0, 100),
                        new Point3D(-400, -1000, -400),
                        new Vector(1, 1, 1),
                        1, 1E-5, 1.5E-7));
        scene.addLights(
                new SpotLight(
                        new Color(255, 0, 100),
                        new Point3D(0, -1000, 0),
                        new Vector(0, 1, 0),
                        1, 1E-5, 1.5E-7));
        scene.addLights(
                new SpotLight(
                        new Color(255, 100, 0),
                        new Point3D(400, -1000, -400),
                        new Vector(-1, 1, 1),
                        1, 1E-5, 1.5E-7));

        ImageWriter imageWriter = new ImageWriter("MiniProject1", 400, 200, 800, 400);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }
}
