package GUI;

import com.sun.javafx.sg.prism.NGNode;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.paint.Color;

public class ViewMolecule3D extends Application {

    double camX = 0;
    double camY = 0;
    double camZ = 0;
    double theta = 0;
    double centerX = camX + 400 * Math.sin(theta);
    double centerY = camZ + 400 * Math.cos(theta);
    double centerZ = 0;// Math.tan(Math.PI/6);

    @Override
    public void start(Stage primaryStage) {
        Group group = grouper();
//        Camera cam = new PerspectiveCamera(true);
//        cam.setOnKeyPressed(new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(KeyEvent event) {
//                System.out.println("otto");
//                System.out.println(event.getCode());
//                cam.getTransforms().add(new Rotate(45, 200, 0, 0, Rotate.Y_AXIS));
//            }
//        });
//        group.getChildren().add(cam);
//        group = grouper();
//        group.setOnKeyPressed(new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(KeyEvent event) {
//                System.out.println(event.getCharacter());
//            }
//        });
//        group.getTransforms().add(new Translate(200, 200, 0));
//        SubScene subScene = new SubScene(group, 400, 400);
//        subScene.setCamera(cam);
//        Group fin = new Group();
//        fin.getChildren().add(subScene);

        Scene scene = new Scene(group, 400, 400, true);
        Camera cam = new PerspectiveCamera(true);
        cam.setNearClip(0.1);
        cam.setFarClip(10000);
        scene.setCamera(cam);
      //  scene.getCamera().getTransforms().add(new Rotate(0, 0, 0, 0));
        scene.getCamera().getTransforms().add(new Translate(0, 0, 0));
        //camX = -200;
        //scene.getCamera().setLayoutX(0);
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

            double dist = 50;
            double theta = 10;
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.RIGHT) {
                    cam.getTransforms().add(new Rotate(theta, 0, 0, 0, Rotate.Y_AXIS));
                    System.out.println(cam.getTranslateX());
                } else if (event.getCode() == KeyCode.LEFT){
                    cam.getTransforms().add(new Rotate(-theta, 0, 0, 0, Rotate.Y_AXIS));
                } else if (event.getCode() == KeyCode.UP) {
                    cam.getTransforms().add(new Rotate(1, 0, 0, 0, Rotate.X_AXIS));
                } else if (event.getCode() == KeyCode.DOWN) {
                    cam.getTransforms().add(new Rotate(-1, 0, 0, 0, Rotate.X_AXIS));
                } else if (event.getCode() == KeyCode.W) {
                    cam.getTransforms().add(new Translate(0, 0, dist));
                }  else if (event.getCode() == KeyCode.S) {
                    cam.getTransforms().add(new Translate(0, 0, -dist));
                }  else if (event.getCode() == KeyCode.A) {
                    cam.getTransforms().add(new Translate(-dist, 0, 0));
                }  else if (event.getCode() == KeyCode.D) {
                    cam.getTransforms().add(new Translate(dist, 0, 0));
                }  else if (event.getCode() == KeyCode.R) {
                    cam.getTransforms().add(new Translate(0, -dist, 0));
                }  else if (event.getCode() == KeyCode.F) {
                    cam.getTransforms().add(new Translate(0, dist, 0));
                }
            }


  /*          @Override
            public void handle(KeyEvent event) {
                System.out.println("ono");
                System.out.println(event.getCode());
                if (event.getCode() == KeyCode.RIGHT) {
                   // System.out.println(scene.getCamera().getLayoutX());
                    System.out.println("blue; " + scene.getCamera().getTranslateZ());
                    theta += Math.PI/12;
                   scene.getCamera().getTransforms().add(new Rotate(45, 0, 0, 0, Rotate.Y_AXIS));
                } else if (event.getSource() == KeyCode.LEFT) {
                    theta -= Math.PI/12;
                    scene.getCamera().getTransforms().add(new Rotate(45 * -1, 0, 0, 0, Rotate.Y_AXIS));
                } else if (event.getCode() == KeyCode.S) {
//                    centerX = camX + 400 * Math.sin(theta);
//                    centerZ = camZ + 400 * Math.cos(theta);
                    scene.getCamera().getTransforms().add(new Translate(0, 0, -50));
                } else if (event.getCode() == KeyCode.W) {
//                    centerX = camX + 400 * Math.sin(theta);
//                    centerZ = camZ + 400 * Math.cos(theta);
                    scene.getCamera().getTransforms().add(new Translate(0, 0, 50));
                } else if (event.getCode() == KeyCode.A) {
                    camX -= 100;
//                    centerX = camX + 400 * Math.sin(theta);
//                    centerZ = camZ + 400 * Math.cos(theta);
                    scene.getCamera().getTransforms().add(new Translate(-50, 0, 0));

                    System.out.println("camX " + camX);
                } else if (event.getCode() == KeyCode.D) {
                    camX += 100;
//                    centerX = camX + 400 * Math.sin(theta);
//                    centerZ = camZ + 400 * Math.cos(theta);
                    scene.getCamera().getTransforms().add(new Translate(50, 0, 0));

                } else if (event.getCode() == KeyCode.R) {
                    scene.getCamera().getTransforms().add(new Translate(0, -50, 0));
                } else if (event.getCode() == KeyCode.F) {
                    scene.getCamera().getTransforms().add(new Translate(0, 50, 0));
                }
                System.out.println("CAMX: " + camX );
            }
            */
        });

        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public Group grouper() {
        Group group = makeScene();
//        Camera cam = new PerspectiveCamera(true);
//        cam.setOnKeyPressed(new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(KeyEvent event) {
//                System.out.println("otto");
//                System.out.println(event.getCode());
//                cam.getTransforms().add(new Rotate(45, 200, 200, -200, Rotate.Y_AXIS));
//            }
//        });
//        group.getChildren().add(cam);
//        SubScene subScene = new SubScene(group, 400, 400);
//        subScene.setCamera(cam);
//        Group fin = new Group();
//        fin.getChildren().add(subScene);
        return group;

    }


    public Group makeScene() {
        Group molecule = new Group();
        Sphere sphere = new Sphere(100);
        sphere.setMaterial(new PhongMaterial(Color.RED));
        sphere.setTranslateX(0);
        sphere.setTranslateY(0);
        sphere.setTranslateZ(0);

        /**
         *        Y
         *
         *  B     R     P
         *
         *        G
         */

        Sphere sphere1 = new Sphere(100);
        sphere1.setMaterial(new PhongMaterial(Color.YELLOW));

        sphere1.setTranslateX(0);
        sphere1.setTranslateZ(-200);


        Sphere sphere2 = new Sphere(100);
        sphere2.setMaterial(new PhongMaterial(Color.PINK));

        sphere2.setTranslateX(200);
        sphere2.setTranslateZ(0);

        Sphere sphere3 = new Sphere(100);
        sphere3.setMaterial(new PhongMaterial(Color.GREEN));

        sphere3.setTranslateX(0);
        sphere3.setTranslateZ(200);

        Sphere sphere4 = new Sphere(100);
        sphere4.setMaterial(new PhongMaterial(Color.BLUE));

        sphere4.setTranslateX(-200);
        sphere4.setTranslateZ(0);

        Cylinder spear = new Cylinder(10, 1000);
        spear.setMaterial(new PhongMaterial(Color.BROWN));
        spear.getTransforms().add(new Rotate(90, 500, 0, 0, Rotate.X_AXIS));

        Cylinder bond1 = new Cylinder(20, 100);
        bond1.setMaterial(new PhongMaterial(Color.YELLOW));
        bond1.setTranslateX(250);
        bond1.setTranslateZ(-100);
        bond1.getTransforms().add(new Rotate(30, 30, 30));
//        bond1.setRotationAxis(Rotate.Z_AXIS);
//        bond1.setRotate(new Rotate(30));
        double angle1 = 120;

        molecule.getChildren().addAll(spear, sphere, sphere4, sphere3, sphere2, sphere1);
//        molecule.getChildren().add(bond1);
        molecule.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                System.out.println(event.getCode());
                if (event.getCode() == KeyCode.RIGHT) {
                    System.out.println("r");
                    molecule.getTransforms().add(new Rotate(30, 200, 200, -100, Rotate.Z_AXIS));
                }
            }
        });
        return molecule;
    }


}
