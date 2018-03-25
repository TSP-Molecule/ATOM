package GUI;

import com.sun.javafx.sg.prism.NGNode;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.paint.Color;
import structures.enums.Elem;

/**
 * A test class used throughout development to try new ideas for displaying multiple scenes together
 * and for testing new UI ideas before incorporating them.  Currently displays a 3D scene on the left side,
 * which can be rotated using mouse input and panned and zoomed with keyboard input (standard wasd with r and f for
 * moving vertically).  The right panel is an editable textbox.
 * @author Sarah Larkin
 * CS3141, Spring 2018
 * Date Last Modified:  March 25, 2018
 */
public class ViewMolecule3D extends Application {

    double camX = 0;
    double camY = 0;
    double camZ = 0;
    double theta = 0;
    double centerX = camX + 400 * Math.sin(theta);
    double centerY = camZ + 400 * Math.cos(theta);
    double centerZ = 0;// Math.tan(Math.PI/6);
    double oldX = 400;
    double oldY = 0;
    double oldZ = 0;
    boolean subf = true;
    Group group;

    @Override
    public void start(Stage primaryStage) {
        group = new Group();  //grouper();
      //  Camera cam = new PerspectiveCamera(true);
//        cam.setOnKeyPressed(new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(KeyEvent event) {
//                System.out.println("otto");
//                System.out.println(event.getCode());
//                cam.getTransforms().add(new Rotate(45, 200, 0, 0, Rotate.Y_AXIS));
//            }
//        });
       // group.getChildren().add(cam);
       group = grouper();
//        group.setOnKeyPressed(new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(KeyEvent event) {
//                System.out.println(event.getCharacter());
//            }
//        });
     //   group.getTransforms().add(new Translate(200, 200, 0));
//        SubScene subScene = new SubScene(group, 400, 400);
//        subScene.setCamera(cam);
//        Group fin = new Group();
//        fin.getChildren().add(subScene);
        Camera cam = new PerspectiveCamera(true);
        cam.setNearClip(0.1);
        cam.setFarClip(10000);

        SubScene sub = new SubScene(group, 400, 400);
        sub.setCamera(cam);
        cam.getTransforms().add(new Translate(0, 0, -100));
        GridPane pane = new GridPane();
        pane.setPrefSize(800, 600);
        pane.add(sub, 0, 0);
        Button bob = new Button("BLUE\nBELLS\nBLOOM");
        bob.setPrefSize(200, 200);

       // pane.add(bob, 1, 0);
        TextField job = new TextField("blah");
       // job.setEditable(false);
        job.setPrefSize(200, 400);
        pane.add(job, 1, 0);
//        pane.add(job, 1, 0);
        Scene scene = new Scene(pane, 800, 600, true);

      //  scene.setCamera(cam);
      //  scene.getCamera().getTransforms().add(new Rotate(0, 0, 0, 0));
        //scene.getCamera().setLayoutX(0);
        job.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.RIGHT) {
                    if (subf) {
                        sub.requestFocus();
                        group.getTransforms().add(new Rotate(theta, 0, 0, 0, Rotate.Y_AXIS));
                    }
                }
            }
        });

        sub.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                sub.requestFocus();
                subf = true;
            }
        });
      job.setOnMouseEntered(new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
             // job.requestFocus();
              subf = false;
          }
      });
      sub.setOnMousePressed(new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
              oldX = event.getX();
              System.out.println(oldX);
          }
      });
        sub.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double x = event.getX();
                double y = event.getY();
                if (x < oldX && Math.abs(x - oldX) > 20) {
                    System.out.println("BAAA");
                    //cam.getTransforms().add(new Rotate(5, 0, 0, 0, Rotate.Y_AXIS));
                    group.getTransforms().add(new Rotate(5, 0, 0, 0, Rotate.Y_AXIS));
                    oldX = x;
                } else if (x > oldX && Math.abs(x - oldX) > 20) {
                    group.getTransforms().add(new Rotate(-5, 0, 0, 0, Rotate.Y_AXIS));
                    oldX = x;
                }
                if (y > oldY && Math.abs(y - oldY) > 20) {
                    group.getTransforms().add(new Rotate(1, 0, 0, 0, Rotate.X_AXIS));
                    oldY = y;
                } else if (y < oldY && Math.abs(y - oldY) > 20) {
                    group.getTransforms().add(new Rotate(-1, 0, 0, 0, Rotate.X_AXIS));
                    oldY = y;
                }
            }
        });
        sub.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println(event.getX());
            }
        });

        sub.setOnKeyPressed(new EventHandler<KeyEvent>() {

            double dist = 50;
            double theta = 10;

            @Override
            public void handle(KeyEvent event) {

//                sub.setOnKeyPressed(new EventHandler<KeyEvent>() {
//                    @Override
//                    public void handle(KeyEvent event) {
//                        if (event.getCode().isLetterKey()) {
//                            System.out.println("MyKey");
//                        }
//                    }
//                });
                sub.requestFocus();
                if (event.getCode() == KeyCode.RIGHT) {

                    sub.requestFocus();
                    cam.getTransforms().add(new Rotate(theta, 0, 0, -100, Rotate.Y_AXIS));
                    cam.getTransforms().add(new Translate(100 * Math.cos(theta), 0, -100 * Math.sin(theta)));
                    System.out.println(cam.getTranslateX());
                    if (job.isFocused()) {
                        sub.requestFocus();
                    }
                    subf = true;
                } else if (event.getCode() == KeyCode.LEFT){
                    cam.getTransforms().add(new Rotate(-theta, 0, 0, 0, Rotate.Y_AXIS));
                    cam.getTransforms().add(new Translate(100 * Math.cos(-theta), 0, -100 * Math.sin(-theta)));
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
                sub.requestFocus();
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


    public Group ammoniaMolecule() {
        Group group = new Group();

        double x = 0;
        double y = 0;
        double z = 0;
        double bondLength = 200;

        final double ANGLE = 120;
        double thetaX = 0;
        double thetaY = ANGLE;
        double thetaZ = 0;

        double bondX = bondLength * Math.cos(thetaX);
        double bondY = Math.sin(thetaY);
        double bondZ = bondLength * Math.sin(thetaZ);


        Sphere carbon = new Sphere(100);
        carbon.setMaterial(new PhongMaterial(Elem.getBySymbol("C").getColor()));
        carbon.getTransforms().add(new Translate(x, y, z));
        group.getChildren().add(carbon);

        double theta = 45;

        Cylinder bond1 = new Cylinder(10, 100);
        bond1.setMaterial(new PhongMaterial(Color.LIGHTGREY));
        bond1.getTransforms().add(new Translate(bondLength * Math.cos(theta)/2, -bondLength * Math.cos(theta)/2, 0));
        bond1.getTransforms().add(new Rotate(theta,bondLength * Math.cos(theta)/2, -bondLength * Math.cos(theta)/2, 0, Rotate.Z_AXIS));
        group.getChildren().add(bond1);

        thetaX += ANGLE;
        thetaZ += ANGLE;

        Sphere h1 = new Sphere(100);
        h1.setMaterial(new PhongMaterial(Elem.getBySymbol("H").getColor()));
        h1.getTransforms().add(new Translate(bondLength * Math.cos(theta), -bondLength * Math.cos(theta), 0));
        group.getChildren().add(h1);

        Cylinder bond2 = new Cylinder(10, 100);
        bond2.setMaterial(new PhongMaterial(Color.LIGHTGREY));
        bond2.getTransforms().add(new Translate(bondX, bondY, bondZ));
        bond2.getTransforms().add(new Rotate(thetaY, bondX, bondY, bondZ, Rotate.Z_AXIS));
        group.getChildren().add(bond2);

        thetaX += ANGLE;
        thetaZ += ANGLE;

        Sphere h2 = new Sphere(100);
        h2.setMaterial(new PhongMaterial(Elem.getBySymbol("H").getColor()));
        h2.getTransforms().add(new Translate(x + bondX * 2, y + bondY * 2, z + bondZ * 2));
        group.getChildren().add(h2);

        Cylinder bond3 = new Cylinder(10, 100);
        bond3.setMaterial(new PhongMaterial(Color.LIGHTGREY));
        bond3.getTransforms().add(new Translate(bondX, bondY, bondZ));
        bond3.getTransforms().add(new Rotate(thetaY, bondX, bondY, bondZ, Rotate.Z_AXIS));
        group.getChildren().add(bond3);

        thetaX += ANGLE;
        thetaZ += ANGLE;

        Sphere h3 = new Sphere(100);
        h3.setMaterial(new PhongMaterial(Elem.getBySymbol("H").getColor()));
        h3.getTransforms().add(new Translate(x + bondX * 2, y + bondY * 2, z + bondZ * 2));
        group.getChildren().add(h3);

        group.getTransforms().add(new Translate(0, 0, 1500));
        return group;
    }

    public Group waterMolecule() {
        Group group = new Group();

       /* Sphere oxygen = new Sphere(50);
        oxygen.setMaterial(new PhongMaterial(Elem.getBySymbol("O").getColor()));
        group.getChildren().add(oxygen);

        Cylinder bond1 = new Cylinder(50, 100);
        bond1.setMaterial(new PhongMaterial(Color.LIGHTGREY));
        bond1.getTransforms().add(new Translate(100 * Math.cos(-60), 100 * Math.sin(-60), 0));
        bond1.getTransforms().add(new Rotate(-60, 100 * Math.cos(-60), 100 * Math.sin(-60), 0, Rotate.Z_AXIS));
        group.getChildren().add(bond1);

        Sphere h1 = new Sphere(50);
        h1.setMaterial(new PhongMaterial(Elem.getBySymbol("H").getColor()));
        h1.getTransforms().add(new Translate(200 * Math.cos(-60), 200 * Math.sin(-60), 0));
        group.getChildren().add(h1);

        Cylinder bond2 = new Cylinder(50, 100);
        bond2.setMaterial(new PhongMaterial(Color.LIGHTGREY));
        bond2.getTransforms().add(new Translate(-100 * Math.cos(-60), 100 * Math.sin(-60), 0));
        bond2.getTransforms().add(new Rotate(-60, -100 * Math.cos(-60), 100 * Math.sin(-60), 0, Rotate.Z_AXIS));
        group.getChildren().add(bond2);

        Sphere h2 = new Sphere(50);
        h2.setMaterial(new PhongMaterial(Elem.getBySymbol("H").getColor()));
        h2.getTransforms().add(new Translate(-200 * Math.cos(-60), -200 * Math.sin(-60), 0));
        group.getChildren().add(h2);*/

        Sphere hydrogen = new Sphere(50);
        PhongMaterial whiteMat = new PhongMaterial();
        whiteMat.setDiffuseColor(Color.ANTIQUEWHITE);
        whiteMat.setSpecularColor(Color.ANTIQUEWHITE);
        hydrogen.setMaterial(whiteMat);
        int bond = 200;

        Sphere oxygen = new Sphere(50);
        PhongMaterial redMat = new PhongMaterial();
        redMat.setSpecularColor(Color.RED);
        redMat.setDiffuseColor(Color.RED);
        oxygen.setMaterial(redMat);

        Sphere hydrogen1 = new Sphere(50);
        hydrogen1.setMaterial(whiteMat);

        Cylinder b1 = new Cylinder(10,bond);
        PhongMaterial blackM = new PhongMaterial();
        blackM.setDiffuseColor(Color.BLACK);
        blackM.setSpecularColor(Color.BLACK);
        b1.setMaterial(blackM);

        Cylinder b2 = new Cylinder(10, bond);
        b2.setMaterial(blackM);

        oxygen.setTranslateX(0);
        oxygen.setTranslateY(0);
        oxygen.setTranslateZ(0);

        hydrogen.setTranslateX(bond * Math.cos(-1 * Math.PI/3));
        hydrogen.setTranslateY(bond * Math.sin(-1 * Math.PI/3));
        hydrogen.setTranslateZ(0);

        hydrogen1.setTranslateX(bond * Math.cos(Math.PI/3));
        hydrogen1.setTranslateY(bond * Math.sin(Math.PI/3));
        hydrogen1.setTranslateZ(0);

        b1.setTranslateX(bond * Math.cos(-1 * Math.PI/3)/2 - 0);
        b1.setTranslateY(bond * Math.sin(-1 * Math.PI/3)/2 + 0);
        b1.setTranslateZ(0);
        b1.setRotationAxis(Rotate.Z_AXIS);
        b1.setRotate(30);

        b2.setTranslateX(bond * Math.cos(Math.PI/3)/2);
        b2.setTranslateY(bond * Math.sin(Math.PI/3)/2);
        b2.setTranslateZ(0);
        b2.setRotationAxis(Rotate.Z_AXIS);
        b2.setRotate(150);

        group.getChildren().addAll(b1, b2, hydrogen, oxygen, hydrogen1);

        return group;

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
