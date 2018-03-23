package GUI;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import structures.*;
import structures.enums.Elem;
import web.WebService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

/**
 * Displays the main window of the app with a menu and two panels for models and information.
 * @author  Sarah Larkin
 * CS3141, Spring 2018, Team ATOM
 * Date Last Modified: March 22, 2018
 */
public class GeneralViewer extends Application {

    Atominomicon atominimicon;
    ScrollPane textPane = new ScrollPane();
    SubScene imageScene;
    double oldX = 400;
    double oldY = 0;
    double oldZ = 0;
    boolean subf = true;

    @Override
    public void start(Stage primaryStage) {
        primaryStage = window();
        primaryStage.show();
    }

    /**
     * Displays the full window with a split view
     * @return the stage containing the window
     */
    public Stage window() {
        Stage stage = new Stage();
        Group group = new Group();
        GridPane pane = new GridPane();
        //pane.setPrefSize(stage.getWidth(), stage.getHeight());
        pane.setBackground(new Background(new BackgroundFill(Color.rgb(255, 240, 200), new CornerRadii(2), new Insets(2))));
        pane.setPrefSize(1200, 800);

        TextArea left = new TextArea("Molecule here");
        left.setMinSize(600, 700);
        left.setBackground(new Background(new BackgroundFill(Color.rgb(255, 200, 220), new CornerRadii(2), new Insets(0))));
        SubScene sub = sub(waterMolecule(), 600, 700);

        pane.add(sub, 0, 0);

        TextArea right = new TextArea("Molecule Info will go here");
        right.setMinSize(600, 700);
        right.setBackground(new Background(new BackgroundFill(Color.rgb(200, 255, 220), new CornerRadii(2), new Insets(0))));
        textPane.setContent(right);
        //textPane.setPrefWidth(600);
        textPane.setMinSize(600, 700);
        pane.add(textPane, 1, 0);


       // group.getChildren().add(pane);
        BorderPane mainPane = new BorderPane();
        mainPane.setPrefSize(stage.getWidth(), stage.getHeight());
        GridPane topGrid = new GridPane();
        topGrid.add(makeMenuBar(stage), 0, 0);
        topGrid.add(searchBar(), 1, 0);

     //   mainPane.setTop(makeMenuBar(stage));
        mainPane.setTop(topGrid);
        mainPane.setCenter(pane);
        Scene scene = new Scene(mainPane, 1200, 800);
        sub.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println(" blue moon noon ");
                sub.requestFocus();
                subf = true;
            }
        });
        sub.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                sub.requestFocus();
                oldX = event.getX();
                System.out.println(oldX);
            }
        });




        stage.setScene(scene);
        stage.setTitle("ATOM - A Tiny Object Modeler");
        return stage;

    }

    /**
     * Makes a central pane that can be used for organizing panels.
     * @param stage the window of display
     * @return the grid pane
     */
    private GridPane displayPane(Stage stage) {
        GridPane p = new GridPane();
       // p.setMinSize(stage.getWidth()/2, stage.getHeight() *2/3);
        p.setBackground(new Background(new BackgroundFill(Color.rgb(255, 0, 0), new CornerRadii(2), new Insets(2))));
        return p;
    }

//    /**  OBSOLETED
//     * Makes a flow pane that can contain the molecule model
//     * @param stage the window of display
//     * @return the flow pane
//     */
//    private FlowPane viewPane(Stage stage) {
//        FlowPane pane = new FlowPane();
//        Text info = new Text(pane.getWidth()/2, pane.getHeight(), "This will display a molecule");
//        pane.getChildren().add(info);
//        pane.setPrefSize(stage.getWidth()/2, stage.getHeight());
//        pane.setBackground( new Background(new BackgroundFill(Color.rgb(255, 250, 0), new CornerRadii(2), new Insets(2))));
//        return pane;
//    }

    /**
     * Makes a subscene which is an alternate option for displaying a 3D model
     * @param scene the parent scene, or window
     * @param w the width of the subscene
     * @param h the height of the subscene
     * @return the subscene completed
     */
    private SubScene sub (Parent scene, double w, double h) {
        SubScene scene1 = new SubScene(scene, w, h);
        scene1.setWidth(500);
        scene1.setHeight(600);
        Camera cam = new PerspectiveCamera(true);
        cam.setNearClip(0.1);
        cam.setFarClip(100000);
        cam.getTransforms().add(new Translate(0, 0, -1500));
        scene1.setOnKeyPressed(new EventHandler<KeyEvent>() {

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
                scene1.requestFocus();
                if (event.getCode() == KeyCode.L) {

                    scene1.requestFocus();
                    scene.getTransforms().add(new Rotate(theta, 0, 0, -100, Rotate.Y_AXIS));
                    //cam.getTransforms().add(new Translate(100 * Math.cos(theta), 0, -100 * Math.sin(theta)));
                    System.out.println(cam.getTranslateX());
                    if (!scene1.isFocused()) {
                        scene1.requestFocus();
                    }
                    subf = true;
                } else if (event.getCode() == KeyCode.J) {
                    scene.getTransforms().add(new Rotate(-theta, 0, 0, 0, Rotate.Y_AXIS));
                   // cam.getTransforms().add(new Translate(100 * Math.cos(-theta), 0, -100 * Math.sin(-theta)));
                } else if (event.getCode() == KeyCode.I) {
                    scene.getTransforms().add(new Rotate(1, 0, 0, 0, Rotate.X_AXIS));
                } else if (event.getCode() == KeyCode.K) {
                    scene.getTransforms().add(new Rotate(-1, 0, 0, 0, Rotate.X_AXIS));
                } else if (event.getCode() == KeyCode.Y) {
                    scene.getTransforms().add(new Rotate(1, 0, 0, 0, Rotate.Z_AXIS));
                } else if (event.getCode() == KeyCode.H) {
                    scene.getTransforms().add(new Rotate(-1, 0, 0, 0, Rotate.Z_AXIS));
                }else if (event.getCode() == KeyCode.W) {
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
                scene1.requestFocus();
            }
        });
        scene1.setCamera(cam);
        //scene1.setFill(Color.rgb(180, 255, 180));

        return scene1;
    }

    public Group saltMolecule() {
        Group group = new Group();
        Color c = Elem.getBySymbol("Cl").getColor();
        Color s = Elem.getBySymbol("Na").getColor();
//        Sphere s1 = new Sphere(50);
//        PhongMaterial whiteMat = new PhongMaterial();
//        whiteMat.setDiffuseColor(Elem.getBySymbol("Na").getColor());
//        whiteMat.setSpecularColor(Elem.getBySymbol("Na").getColor());
//        s1.setMaterial(whiteMat);
//        s1.setTranslateX(0);
//        s1.setTranslateZ(0);
        int bond = 200;

        Sphere c1 = new Sphere(50);
        c1.setMaterial(new PhongMaterial(c));
        c1.setTranslateX(300);
        c1.setTranslateZ(0);

        Sphere c2 = new Sphere(50);
        c2.setMaterial(new PhongMaterial(c));
        c2.setTranslateX(0);
        c2.setTranslateY(300);

        Sphere c3 = new Sphere(50);
        c3.setMaterial(new PhongMaterial(c));
        c3.setTranslateX(300);
        c3.setTranslateY(300);
        c3.setTranslateZ(300);


        Sphere c4 = new Sphere(50);
        c4.setMaterial(new PhongMaterial(c));
        c4.setTranslateX(0);
        c4.setTranslateZ(300);

        Sphere s1 = new Sphere(50);
        s1.setMaterial(new PhongMaterial(s));
        s1.setTranslateX(0);
        s1.setTranslateY(0);
        s1.setTranslateZ(0);

        Sphere s2 = new Sphere(50);
        s2.setMaterial(new PhongMaterial(s));
        s2.setTranslateX(300);
        s2.setTranslateY(300);
        s2.setTranslateZ(0);

        Sphere s3 = new Sphere(50);
        s3.setMaterial(new PhongMaterial(s));
        s3.setTranslateX(300);
        s3.setTranslateZ(300);

        Sphere s4 = new Sphere(50);
        s4.setMaterial(new PhongMaterial(s));
        s4.setTranslateY(300);
        s4.setTranslateZ(300);



        double p = 150;

        for (int i = 0; i < 4; i++) {
            Cylinder cy = new Cylinder(10, bond);
            cy.setMaterial(new PhongMaterial(Color.LIGHTGREY));
            cy.setTranslateX(p);
            if (i % 2 == 0) {
                cy.setTranslateY(300);
            }
            if (i > 1) {
                cy.setTranslateZ(300);
            }
            cy.setRotationAxis(Rotate.Z_AXIS);
            cy.setRotate(90);
           group.getChildren().add(cy);
        }

        for (int i = 0; i < 4; i++) {
            Cylinder cy = new Cylinder(10, bond);
            cy.setMaterial(new PhongMaterial(Color.LIGHTGREY));

            if (i > 1) {
                cy.setTranslateX(300);
            }

            if (i % 2 == 0) {
                cy.setTranslateZ(300);
            }
            cy.setTranslateY(p);
            cy.setRotationAxis(Rotate.Y_AXIS);
            cy.setRotate(90);
            group.getChildren().add(cy);
        }
        for (int i = 0; i < 4; i++) {
            Cylinder cy = new Cylinder(10, bond);
            cy.setMaterial(new PhongMaterial(Color.LIGHTGREY));
            cy.setTranslateX(300);
            if (i > 1) {
                cy.setTranslateZ(150);
            }

            if (i % 2 == 0) {
                cy.setTranslateY(300);
            }
            //cy.setTranslateX(300);
            cy.setRotationAxis(Rotate.X_AXIS);
            cy.setRotate(90);
            group.getChildren().add(cy);
        }

//        Sphere oxygen = new Sphere(50);
//        PhongMaterial redMat = new PhongMaterial();
//        redMat.setSpecularColor(Color.RED);
//        redMat.setDiffuseColor(Color.RED);
//        oxygen.setMaterial(redMat);
//
//        Sphere hydrogen1 = new Sphere(50);
//        hydrogen1.setMaterial(whiteMat);
//
//        Cylinder b1 = new Cylinder(10,bond);
//        PhongMaterial blackM = new PhongMaterial();
//        blackM.setDiffuseColor(Color.BLACK);
//        blackM.setSpecularColor(Color.BLACK);
//        b1.setMaterial(blackM);
//
//        Cylinder b2 = new Cylinder(10, bond);
//        b2.setMaterial(blackM);
//
//        oxygen.setTranslateX(0);
//        oxygen.setTranslateY(0);
//        oxygen.setTranslateZ(0);
//
//        hydrogen.setTranslateX(bond * Math.cos(-1 * Math.PI/3));
//        hydrogen.setTranslateY(bond * Math.sin(-1 * Math.PI/3));
//        hydrogen.setTranslateZ(0);
//
//        hydrogen1.setTranslateX(bond * Math.cos(Math.PI/3));
//        hydrogen1.setTranslateY(bond * Math.sin(Math.PI/3));
//        hydrogen1.setTranslateZ(0);

//        b1.setTranslateX(bond * Math.cos(-1 * Math.PI/3)/2 - 0);
//        b1.setTranslateY(bond * Math.sin(-1 * Math.PI/3)/2 + 0);
//        b1.setTranslateZ(0);
//        b1.setRotationAxis(Rotate.Z_AXIS);
//        b1.setRotate(30);
//
//        b2.setTranslateX(bond * Math.cos(Math.PI/3)/2);
//        b2.setTranslateY(bond * Math.sin(Math.PI/3)/2);
//        b2.setTranslateZ(0);
//        b2.setRotationAxis(Rotate.Z_AXIS);
//        b2.setRotate(150);

        group.getChildren().addAll(s1, s2, s3, s4, c1, c2, c3, c4);

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

//    public Group waterMolecule() {
//        Group group = new Group();
//
//        Sphere oxygen = new Sphere(50);
//        oxygen.setMaterial(new PhongMaterial(Elem.getBySymbol("O").getColor()));
//        group.getChildren().add(oxygen);
//
//        Cylinder bond1 = new Cylinder(50, 100);
//        bond1.setMaterial(new PhongMaterial(Color.LIGHTGREY));
//        bond1.getTransforms().add(new Translate(100 * Math.cos(-60), 100 * Math.sin(-60), 0));
//        bond1.getTransforms().add(new Rotate(-60, 100 * Math.cos(-60), 100 * Math.sin(-60), 0, Rotate.Z_AXIS));
//        group.getChildren().add(bond1);
//
//        Sphere h1 = new Sphere(50);
//        h1.setMaterial(new PhongMaterial(Elem.getBySymbol("H").getColor()));
//        h1.getTransforms().add(new Translate(200 * Math.cos(-60), 200 * Math.sin(-60), 0));
//        group.getChildren().add(h1);
//
//        Cylinder bond2 = new Cylinder(50, 100);
//        bond2.setMaterial(new PhongMaterial(Color.LIGHTGREY));
//        bond2.getTransforms().add(new Translate(-100 * Math.cos(-60), 100 * Math.sin(-60), 0));
//        bond2.getTransforms().add(new Rotate(-60, -100 * Math.cos(-60), 100 * Math.sin(-60), 0, Rotate.Z_AXIS));
//        group.getChildren().add(bond2);
//
//        Sphere h2 = new Sphere(50);
//        h2.setMaterial(new PhongMaterial(Elem.getBySymbol("H").getColor()));
//        h2.getTransforms().add(new Translate(-200 * Math.cos(-60), -200 * Math.sin(-60), 0));
//        group.getChildren().add(h2);
//
//        return group;
//
//    }

    public Group ammoniaMolecule() {
        Group group = new Group();

        double x = 0;
        double y = 0;
        double z = 0;
        double bondLength = 100;

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



        Cylinder bond1 = new Cylinder(50, 100);
        bond1.setMaterial(new PhongMaterial(Color.LIGHTGREY));
        bond1.getTransforms().add(new Translate(bondX, bondY, bondZ));
        bond1.getTransforms().add(new Rotate(thetaY, bondX, bondY, bondZ, Rotate.Z_AXIS));
        group.getChildren().add(bond1);

        thetaX += ANGLE;
        thetaZ += ANGLE;

        Sphere h1 = new Sphere(100);
        h1.setMaterial(new PhongMaterial(Elem.getBySymbol("H").getColor()));
        h1.getTransforms().add(new Translate(x + bondX * 2, y + bondY * 2, z + bondZ * 2));
        group.getChildren().add(h1);

        Cylinder bond2 = new Cylinder(50, 100);
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

        Cylinder bond3 = new Cylinder(50, 100);
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

        group.getTransforms().add(new Translate(0, 200, 1500));
        return group;
    }


    /**
     * A work in progress - will eventually develop a tree to represent the bonding structure
     * and make traversal drawing easy.
     * @param mole
     * @return
     */
    private Group molecule3D(Molecule mole) {
        Group molecule = new Group();
        Atom center = mole.getCenter();

        HashMap<Atom, Boolean> drawnMap = new HashMap<>();
        HashMap<Bond, Boolean> bondMap = new HashMap<>();
        Stack<Atom> toDraw = new Stack<>();
        double x = 0;
        double y = 0;
        double z = 0;
        double bondX = 0;
        double bondY = 0;
        double bondZ = 0;
        double thetaX = 0;
        double thetaY = 0;
        double thetaZ = 0;
        toDraw.push(center);
        while (!toDraw.isEmpty()) {
            Atom atom = toDraw.pop();
            Sphere sphere = new Sphere(100);
            sphere.setMaterial(new PhongMaterial(atom.getElement().getColor()));
            sphere.getTransforms().add(new Translate(x, y, z));
            molecule.getChildren().add(sphere);
            for (Bond bond : atom.getAttachedBonds()) {
                if (bondMap.get(bond) == null) {
                    double angle = bond.getBondingAngle();
                    bondX = x + 50 + 50 * Math.cos(angle + thetaX);
                    thetaX += angle;
                    bondY = y + 50 + Math.sin(angle + thetaY);
                    bondZ = z + 50 + Math.sin(angle + thetaZ);
                    thetaZ += angle;
                    Cylinder cylinder = new Cylinder(40, 100);
                    cylinder.setMaterial(new PhongMaterial(Color.LIGHTGREY));
                    cylinder.getTransforms().add(new Translate(bondX, bondY, bondZ));
                    cylinder.getTransforms().add(new Rotate(angle, bondX, bondY, bondZ, Rotate.Y_AXIS));
                    molecule.getChildren().add(cylinder);

                    // Add atom at opposite end to the stack if it has not been drawn
                    Atom one = bond.getAtomOne();
                    Atom two = bond.getAtomTwo();
                    if (!one.equals(atom) && drawnMap.get(one) == null) {
                        toDraw.push(one);
                    } else if (drawnMap.get(two) == null) {
                        toDraw.push(two);
                    }
                }
            }
        }





//        for (Atom a: mole.getAtoms()) {
//
//        }
//        Sphere e1 = new Sphere(100);
//        e1.setMaterial(new PhongMaterial(Color.web(center.getElement().getColor() + "")));
//        for (Bond b: center.getAttachedBonds()) {
//            Object [] bond = b.getBondedAtoms().toArray();
//            if (bond[0] == center) {
//
//            }
//            Cylinder c = new Cylinder(50, 100);
//            c.getTransforms().add(new Translate(50, 0, 0 ));
//            c.getTransforms().add(new Rotate(100, 50, 0, 0));
//        }
        return molecule;
    }




    /**
     * Makes the search bar.
     * @return the search bar
     */
    private BorderPane searchBar() {
        BorderPane grid = new BorderPane();
        TextField search = new TextField();
        search.setText("search");
        search.setEditable(true);
        search.setPrefSize(800, 40);
        grid.setLeft(search);
     //   grid.getChildren().add(search);

        Button go = new Button("GO");
        go.setPrefSize(100, 40);
        go.setTextFill(Color.GREEN);
        grid.setRight(go);
       // f.getChildren().add(go);
        go.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String searchText = search.getText();
                try {
                    WebService spider = new WebService();
                    String formula = spider.getFormula(searchText);
                    TextArea info = new TextArea();
                    String printout = String.format("%s has the formula %s.\n  ", searchText, formula);
                    ArrayList<Atom> elemList = new ChemicalFormula(formula).getAtoms();
                    String chemicals = String.format("It contains the following elements:\n");
                    String atoms = "";
                    for (int i = 0; i < elemList.size(); i++) {
                        atoms += elemList.get(i).getElement().getName();
                        if (i < elemList.size() - 1) {
                            atoms += ", ";
                        }
                        atoms += "\n";
                    }
                    String output = printout + chemicals + atoms;
                    info.setText(output);
                    textPane.setContent(info);
                    //atominimicon = new Atominomicon();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // We really need a Search class that will run the search through the atominomicon and database.
                //atominimicon.readElem(Elem.valueOf(s).getNum());
            }
        });
        return grid;
    }



    /**
     * Creates and returns a pane to display results of a search
     * @param p
     * @param results
     * @return
     */
    private GridPane resultPane(BorderPane p, ArrayList<String> results) {
        GridPane pane = new GridPane();
        Label res = new Label("Results");
        pane.addRow(0, res);
        pane.addRow(1, new Label("Results will be listed here"));
        pane.addRow(2, new Label("Click a result to view the molecule"));
        for (Node node: pane.getChildren()) {
            node.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (node instanceof Label) {
                        Label l = (Label) node;
                        String s = l.getText();
                        // TODO: Display molecule
                    }
                }
            });
        }
       return pane;
    }


    /**
     * Makes and returns a menu bar to be displayed in the given stage.
     * @param p  the stage in which to display the menu
     * @return the new menu bar
     */
    private MenuBar makeMenuBar(Stage p) {
        MenuBar bar = new MenuBar();
        bar.getMenus().add(makeFileMenu(p));

        Menu navigation = new Menu("Navigation");
        MenuItem view3D = new MenuItem("3D View");
        MenuItem view2D = new MenuItem("2D View");
        MenuItem search = new MenuItem("Search");
        MenuItem viewInfoWindow = new MenuItem("View molecule info in new window");
        MenuItem viewPeriodicTable = new MenuItem("View periodic table");
        viewPeriodicTable.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                PeriodicTableView view = new PeriodicTableView();
                view.show();
            }
        });
        navigation.getItems().add(view2D);
        navigation.getItems().add(view3D);
        navigation.getItems().add(search);
        navigation.getItems().add(viewInfoWindow);
        navigation.getItems().add(viewPeriodicTable);
        bar.getMenus().add(navigation);

        Menu help = new Menu("Help");
        MenuItem userManual = new MenuItem("User Manual");
        MenuItem about = new MenuItem("About");
        MenuItem sources = new MenuItem("Sources");
        help.getItems().addAll(userManual, about, sources);
        bar.getMenus().add(help);
        bar.setPrefHeight(40);
        bar.setPrefWidth(300);
        return bar;
    }


    public void makeSearchBar(MenuBar bar) {
        // may need a panel to hold both menubar and search bar
        TextField search = new TextField();
        Button go = new Button("GO");
        go.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String newSearch = search.getText();
                // perform a search
                search.setText("");
            }
        });
       // bar.getMenus().add
    }

    /**
     * Makes a "file" menu with options to open, save, and exit
     * @param p the stage in which the menu is displayed
     * @return the new menu
     */
    private Menu makeFileMenu(Stage p) {
        Menu file = new Menu("File");
        MenuItem open = new MenuItem("Open");
        open.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser chooser = new FileChooser();
                chooser.showOpenDialog(p);
            }
        });
        MenuItem saveImage = new MenuItem("Save");
        saveImage.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser chooser = new FileChooser();
                chooser.showSaveDialog(p);
            }
        });
        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                p.close();
            }
        });
        file.getItems().addAll(open, saveImage, exit);
        return file;
    }

}
