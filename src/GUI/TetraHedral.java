package GUI;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Line;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

public class TetraHedral extends Application {

    double x = 0;
    double y = 0;
    @Override
    public void start(Stage primaryStage) throws Exception {
        Group group = new Group(); //bah(); // new Group();
       // tetrahed(group);
        hum(group);
        bug(group);
        Scene scene = new Scene(group, 500, 500, true);

        Camera cam = new PerspectiveCamera(true);
        cam.setNearClip(0.1);
        cam.setFarClip(100000);
        cam.getTransforms().add(new Translate(0, 0, -1500));
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

            double dist = 50;
            double theta = 10;

            @Override
            public void handle(KeyEvent event) {
                // scene1.requestFocus();

                // rotate commands
                if (event.getCode() == KeyCode.L) {
                    group.getTransforms().add(new Rotate(theta, 0, 0, -100, Rotate.Y_AXIS));
//                    if (!scene1.isFocused()) {
//                        scene1.requestFocus();
//                    }
                    //subf = true;
                } else if (event.getCode() == KeyCode.J) {
                    group.getTransforms().add(new Rotate(-theta, 0, 0, 0, Rotate.Y_AXIS));
                } else if (event.getCode() == KeyCode.I) {
                    group.getTransforms().add(new Rotate(1, 0, 0, 0, Rotate.X_AXIS));
                } else if (event.getCode() == KeyCode.K) {
                    group.getTransforms().add(new Rotate(-1, 0, 0, 0, Rotate.X_AXIS));
                } else if (event.getCode() == KeyCode.Y) {
                    group.getTransforms().add(new Rotate(1, 0, 0, 0, Rotate.Z_AXIS));
                } else if (event.getCode() == KeyCode.H) {
                    group.getTransforms().add(new Rotate(-1, 0, 0, 0, Rotate.Z_AXIS));
                } else
                    // translate commands
                    if (event.getCode() == KeyCode.W) {
                        cam.getTransforms().add(new Translate(0, 0, dist));
                    } else if (event.getCode() == KeyCode.S) {
                        cam.getTransforms().add(new Translate(0, 0, -dist));
                    } else if (event.getCode() == KeyCode.A) {
                        cam.getTransforms().add(new Translate(-dist, 0, 0));
                    } else if (event.getCode() == KeyCode.D) {
                        cam.getTransforms().add(new Translate(dist, 0, 0));
                    } else if (event.getCode() == KeyCode.R) {
                        cam.getTransforms().add(new Translate(0, -dist, 0));
                    } else if (event.getCode() == KeyCode.F) {
                        cam.getTransforms().add(new Translate(0, dist, 0));
                    }
                //scene.requestFocus();
            }
        });
        Cylinder cylinder = new Cylinder(10, 200);
        cylinder.setMaterial(new PhongMaterial(Color.PALEGOLDENROD));
        cylinder.getTransforms().add(new Rotate(90, 0, 0, 0, Rotate.Z_AXIS));
      //  group.getChildren().add(cylinder);

        scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                x = event.getX();
                y = event.getY();
            }
        });
        scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double theta = y - event.getY();
                double ang = 0;
                if (theta > 0) {
                    ang = theta * 180 / 500;
                } else {
                    ang = theta * 180 / 500;
                }
                System.out.println(ang);
                x = event.getX();
                y = event.getY();
                cylinder.getTransforms().add(new Rotate(ang, 0, 0, 0, Rotate.Z_AXIS));
            }
        });
        scene.setCamera(cam);
        primaryStage.setScene(scene);

        primaryStage.show();

    }

    private double toRadians (double inDegrees) {
        return inDegrees * Math.PI / 180;
    }

    private double toDegrees(double inRadians) {
        return inRadians * 180 / Math.PI;
    }

    public void tetrahed(Group group) {
        Sphere sphere = new Sphere(50);
        sphere.setMaterial(new PhongMaterial(Color.RED));
        group.getChildren().add(sphere);
        double bondLen = 200;
        double angle = 120;
        //spheres
        for (int i = 0; i < 3; i++) {
            double z = bondLen * Math.cos(toRadians(i * angle));
            double y = bondLen * Math.sin(toRadians(i * angle));
            double x = bondLen;
            Sphere hy = new Sphere(50);
            hy.setMaterial(new PhongMaterial(Color.ANTIQUEWHITE));
            hy.getTransforms().add(new Translate(x, y, z));
            System.out.println(x + ", " + y + ", " + z);
            group.getChildren().add(hy);
        }


//        Sphere spree = new Sphere(30);
//        spree.setMaterial(new PhongMaterial(Color.BURLYWOOD));
//        double m = bondLen  * Math.cos(toRadians(0));
//        double n = bondLen  * Math.sin(toRadians(0));
//        double p = Math.sqrt(3)/2 * bondLen;
//        spree.getTransforms().add(new Translate(m, n, 0));
       // group.getChildren().add(spree);
        for (int i = 0; i < 3; i++) {
            double z = bondLen * Math.cos(toRadians(i * angle));
            double y = bondLen * Math.sin(toRadians(i * angle));
            double x = bondLen;
           // Sphere hy = new Sphere(25);
            Cylinder cyl = new Cylinder(10, 1000/*bondLen + bondLen * Math.cos(toRadians(30))*/);
            cyl.setMaterial(new PhongMaterial(Color.OLIVEDRAB));
            cyl.getTransforms().add(new Translate(x, y, z));
            cyl.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    cyl.getTransforms().add(new Translate(event.getX(), event.getY(), 0));
                }
            });
         //   cyl.getTransforms().add(new Rotate(90, x, y, z, Rotate.Z_AXIS));
            cyl.getTransforms().add(new Rotate((90 - angle) * i, x, y, z, Rotate.X_AXIS));
            cyl.getTransforms().add(new Rotate((180 - angle) * (i), 0, 0, 0, Rotate.Y_AXIS));
            cyl.getTransforms().add(new Rotate((180 - angle) * (i), 0, 0, 0, Rotate.Z_AXIS));
            group.getChildren().addAll(cyl);
        }
    }


    public Group bah () {
        Group group = new Group();
        double ken = 200;
        Cylinder c1 = new Cylinder(10, ken);
        c1.setTranslateX(-0.5 * 200);
        c1.setTranslateY(Math.sqrt(3) / 2 * 200);
        c1.setTranslateZ(0);
        c1.setRotationAxis(Rotate.Z_AXIS);
        c1.setRotate(30);
        group.getChildren().add(c1);
        
        Cylinder c2 = new Cylinder(10, ken);
        c2.setTranslateX(0.25 * 200);
        c2.setTranslateY(Math.sqrt(3) / 2 * 200);
        c2.setTranslateZ(Math.sqrt(3) / 4 * 200);
        c2.getTransforms().add(new Rotate(60, 0, 0, 0, Rotate.Y_AXIS));
        c2.getTransforms().add(new Rotate(30, 0.25 * 200, Math.sqrt(3) / 2 * 200, Math.sqrt(3) / 4 * 200, Rotate.Z_AXIS));
//        c2.setRotationAxis(Rotate.Z_AXIS);
//        c2.setRotate(150);
//        c2.getTransforms().add(new Rotate(60, 0, 0, 0, Rotate.Y_AXIS));
////        c2.setRotationAxis(Rotate.Y_AXIS);
////        c2.setRotate(120);
        group.getChildren().add(c2);

        Cylinder c3 = new Cylinder(10, 200);
        c3.setTranslateX(0.5 * 200);
        c3.setTranslateY(Math.sqrt(3) / 2 * 200);
        c3.setTranslateZ(Math.sqrt(3) / 4 * 200);
        c3.getTransforms().add(new Rotate(-60, 0, 0, 0, Rotate.Y_AXIS));
        c3.getTransforms().add(new Rotate(30, 0.25 * 200, Math.sqrt(3) / 2 * 200, Math.sqrt(3) / 4 * 200, Rotate.Z_AXIS));

//        c3.setRotationAxis(Rotate.Z_AXIS);
//        c3.setRotate(150);
//        c3.getTransforms().add(new Rotate(240, 0, 0, 0, Rotate.Y_AXIS));
//        c3.setRotationAxis(Rotate.Y_AXIS);
//        c3.setRotate(240);
        group.getChildren().add(c3);
        
        return group;
    }


    public void hum(Group group) {
      //  Group group = new Group();
        Box box1 = new Box(10, 800, 10);
        box1.setMaterial(new PhongMaterial(Color.RED));
        box1.getTransforms().add(new Rotate(90, 0, 0, 0, Rotate.Z_AXIS));

        Box box2 = new Box(10, 800, 10);
        box2.setMaterial(new PhongMaterial(Color.GREEN));
        box2.getTransforms().add(new Rotate(90, 0, 0, 0, Rotate.X_AXIS));

        Box box3 = new Box(10, 800, 10);
        box3.setMaterial(new PhongMaterial(Color.BLUE));
        box3.getTransforms().add(new Rotate(90, 0, 0, 0, Rotate.Y_AXIS));

        Box box4 = new Box(10, 800, 10);
        box4.setMaterial(new PhongMaterial(Color.YELLOW));
        box4.getTransforms().add(new Rotate(10, 0, 0, 0, Rotate.Z_AXIS));



        group.getChildren().addAll(box1, box2, box3, box4);

    }
    
    public void bug(Group group) {
        double i = 0;
        double angle = 120;
        double bondLen = 400;
        double z = bondLen * Math.cos(toRadians(i * angle));
        double y = bondLen * Math.sin(toRadians(i * angle));
        double x = bondLen;
        Cylinder cy1 = new Cylinder(10, 300);
        cy1.setMaterial(new PhongMaterial(Color.TAN));
        cy1.getTransforms().add(new Translate(100, 0, 0));
        cy1.getTransforms().add(new Rotate(120, 0, 0, 0, Rotate.Z_AXIS));
      //  cy1.getTransforms().add(new Translate(0, 100, 100));

        i = 1;
         z = bondLen * Math.cos(toRadians(i * angle));
         y = bondLen * Math.sin(toRadians(i * angle));
         x = bondLen;

        Cylinder cy2 = new Cylinder(10, 300);
        cy2.setMaterial(new PhongMaterial(Color.TEAL));
        cy2.getTransforms().add(new Translate(100, 0, 0));
        cy2.getTransforms().add(new Rotate(120, 0, 0, 0, Rotate.Y_AXIS));
        cy2.getTransforms().add(new Rotate(120, 0, 0, 0, Rotate.Z_AXIS));
     //   cy2.getTransforms().add(new Translate(0, 100, 100));
      //  cy2.getTransforms().add(new Translate(100, 0, 0));
        cy2.getDrawMode();


        i = 2;
        z = bondLen * Math.cos(toRadians(i * angle));
        y = bondLen * Math.sin(toRadians(i * angle));
        x = bondLen;
        Cylinder cy3 = new Cylinder(10, 300);
        cy3.setMaterial(new PhongMaterial(Color.TOMATO));
        cy3.getTransforms().add(new Translate(100,0, 0));
        cy3.getTransforms().add(new Rotate(240, 0, 0, 0, Rotate.Y_AXIS));
        cy3.getTransforms().add(new Rotate(120, 0, 0, 0, Rotate.Z_AXIS));
       // cy3.getTransforms().add(new Translate(0, y, z));
      //  cy3.getTransforms().add(new Translate(100, 0, 0));


        group.getChildren().addAll(cy1, cy2, cy3);
        
        
    }



    private double pointX(float x1, float y1, float y2, float theta, double radius) {
        double x = radius * Math.cos(theta) + x1;
        return x;
    }

    private double pointZ(float x1, float y1, float z1, float theta, double radius) {
        double z = radius * Math.sin(theta) + z1;
        return z;
    }

    private double pointY (float x1, float y1, float z1, float theta, double radius) {
        return radius * Math.sin(theta) + y1;
    }

    private double pointZ2(float x1, float y1, float z1, float z2, float theta, double radius) {
        return radius * Math.cos(theta) + z1;
    }


    private double thetaXZrotY(double x, double y, double z, double x1, double z1, double a, double b) {
        double xDist = x1 - x;
        double angX = xDist / a;
        double zDist = z1 - z;
        double angZ = zDist / b;
        double thetaX = Math.acos(angX);
        //Line line = new Line(10, 10, 20, 20, 30, 30);
        return 0.0;
    }
}
