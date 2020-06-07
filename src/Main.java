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
                                new Point3D(0, 0, -1000),
                                new Vector(0, 0, 1),
                                new Vector(0, -1, 0)))
                .addDistance(500)
                .addBackground(Color.BLACK)
                .build();

        scene.addGeometries(
                new Sphere(
                        new Color(java.awt.Color.BLUE),
                        new Material(0.5, 0.5, 30), 20,
                        new Point3D(0, 0, 0)),

                new Sphere(new Color(java.awt.Color.GREEN),
                        new Material(0.5, 0.5, 30), 50,
                        new Point3D(20, 0, 0)),

                new Sphere(new Color(java.awt.Color.YELLOW),
                        new Material(0.5, 0.5, 30), 60,
                        new Point3D(-150, 0, 0)),

                new Sphere(new Color(java.awt.Color.magenta),
                        new Material(0.5, 0.5, 30), 50,
                        new Point3D(170, 0, -150)),

                new Sphere(new Color(java.awt.Color.RED),
                        new Material(0.5, 0.5, 30), 40,
                        new Point3D(75, 0, -250)));

//                new Plane(new Color(java.awt.Color.RED),
//                        new Material(0.8, 0.2, 300),
//                        new Point3D(-150, 150, 150),
//                        new Point3D(-70, -70, 50),
//                        new Point3D(75, -75, 150))
//                        );


//        scene.addLights(new DirectionalLight(new Color(300, 150, 150), new Vector(0, 0, 1)));
//        scene.addLights(new PointLight(new Color(50, 150, 55),
//                new Point3D(100, -100, 200),1, 1E-5, 1.5E-7));
        scene.addLights(
                new SpotLight(
                        new Color(400, 240, 0),
                        new Point3D(-400, -100, -400),
                        new Vector(1, 1, 1),
                        1, 1E-5, 1.5E-7));
        scene.addLights(
                new SpotLight(
                        new Color(400, 240, 0),
                        new Point3D(400, -100, -400),
                        new Vector(-1, 1, 1),
                        1, 1E-5, 1.5E-7));

        ImageWriter imageWriter = new ImageWriter("MiniProject1", 400, 200, 800, 400);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }
}
