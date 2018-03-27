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
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import structures.*;
import structures.enums.Elem;
import web.WebService;
import java.util.ArrayList;


/**
 * Displays the main window of the app with a menu, search bar, and two panels for models and information.
 * @author  Sarah Larkin
 * CS3141, Spring 2018, Team ATOM
 * Date Last Modified: March 25, 2018
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
        textPane.setMinSize(600, 700);
        pane.add(textPane, 1, 0);

        BorderPane mainPane = new BorderPane();
        mainPane.setPrefSize(stage.getWidth(), stage.getHeight());
        GridPane topGrid = new GridPane();
        topGrid.add(makeMenuBar(stage), 0, 0);
        topGrid.add(searchBar(), 1, 0);

        mainPane.setTop(topGrid);
        mainPane.setCenter(pane);
        Scene scene = new Scene(mainPane, 1200, 800);

        // Set default selection of subscene upon entry or click
        sub.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                sub.requestFocus();
                subf = true;
            }
        });
        sub.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                sub.requestFocus();
                oldX = event.getX();
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


    /**
     * Makes a subscene which is an alternate option for displaying a 3D model
     * @param scene the parent scene, or window
     * @param w the width of the subscene
     * @param h the height of the subscene
     * @return the subscene
     */
    private SubScene sub (Parent scene, double w, double h) {
        SubScene scene1 = new SubScene(scene, w, h);
        scene1.setWidth(500);
        scene1.setHeight(600);

        // Set the subscene camera
        Camera cam = new PerspectiveCamera(true);
        cam.setNearClip(0.1);
        cam.setFarClip(100000);
        cam.getTransforms().add(new Translate(0, 0, -1500));
        scene1.setOnKeyPressed(new EventHandler<KeyEvent>() {

            double dist = 50;
            double theta = 10;

            @Override
            public void handle(KeyEvent event) {
                scene1.requestFocus();

                // rotate commands
                if (event.getCode() == KeyCode.L) {
                    scene.getTransforms().add(new Rotate(theta, 0, 0, -100, Rotate.Y_AXIS));
                    if (!scene1.isFocused()) {
                        scene1.requestFocus();
                    }
                    subf = true;
                } else if (event.getCode() == KeyCode.J) {
                    scene.getTransforms().add(new Rotate(-theta, 0, 0, 0, Rotate.Y_AXIS));
                } else if (event.getCode() == KeyCode.I) {
                    scene.getTransforms().add(new Rotate(1, 0, 0, 0, Rotate.X_AXIS));
                } else if (event.getCode() == KeyCode.K) {
                    scene.getTransforms().add(new Rotate(-1, 0, 0, 0, Rotate.X_AXIS));
                } else if (event.getCode() == KeyCode.Y) {
                    scene.getTransforms().add(new Rotate(1, 0, 0, 0, Rotate.Z_AXIS));
                } else if (event.getCode() == KeyCode.H) {
                    scene.getTransforms().add(new Rotate(-1, 0, 0, 0, Rotate.Z_AXIS));
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
                scene1.requestFocus();
            }
        });
        scene1.setCamera(cam);
        return scene1;
    }

    /**
     * Depicts a hard-coded salt molecule using colors from the element enum.
     * @return the group of shapes that represent the salt molecule
     */
    public Group saltMolecule() {
        Group group = new Group();
        Color c = Elem.getBySymbol("Cl").getColor();
        Color s = Elem.getBySymbol("Na").getColor();
        int bond = 300;

        Sphere c1 = new Sphere(50);
        c1.setMaterial(new PhongMaterial(c));
        c1.setTranslateX(300);
        c1.setTranslateY(0);
        c1.setTranslateZ(0);

        Sphere c2 = new Sphere(50);
        c2.setMaterial(new PhongMaterial(c));
        c2.setTranslateX(0);
        c2.setTranslateY(300);
        c2.setTranslateZ(0);

        Sphere c3 = new Sphere(50);
        c3.setMaterial(new PhongMaterial(c));
        c3.setTranslateX(300);
        c3.setTranslateY(300);
        c3.setTranslateZ(300);

        Sphere c4 = new Sphere(50);
        c4.setMaterial(new PhongMaterial(c));
        c4.setTranslateX(0);
        c4.setTranslateY(0);
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
        s3.setTranslateY(0);
        s3.setTranslateZ(300);

        Sphere s4 = new Sphere(50);
        s4.setMaterial(new PhongMaterial(s));
        s4.setTranslateX(0);
        s4.setTranslateY(300);
        s4.setTranslateZ(300);

        double p = 150;
        ArrayList<Cylinder> bondList = new ArrayList<>();
        for (int i = 0; i < 12; i++ ) {
            Cylinder cy = new Cylinder(10, bond);
            cy.setMaterial(new PhongMaterial(Color.LIGHTGREY));
            bondList.add(cy);
        }

        for (int i = 0; i < 4; i++) {
            Cylinder cy = bondList.get(i);
            cy.setTranslateX(150);
            cy.setRotationAxis(Rotate.Z_AXIS);
            cy.setRotate(90);

            switch(i) {
                case 0:
                    cy.setTranslateZ(0);
                    break;
                case 1:
                    cy.setTranslateZ(300);
                    break;
                case 2:
                    cy.setTranslateZ(300);
                    cy.setTranslateY(300);
                    break;
                case 3:
                    cy.setTranslateZ(0);
                    cy.setTranslateY(300);
                    break;
            }
        }

        for (int i = 4; i < 8; i++) {
            Cylinder cy = bondList.get(i);
            cy.setTranslateY(150);

            switch(i) {
                case 4:
                    cy.setTranslateX(0);
                    cy.setTranslateZ(0);
                    break;
                case 5:
                    cy.setTranslateX(0);
                    cy.setTranslateZ(300);
                    break;
                case 6:
                    cy.setTranslateX(300);
                    cy.setTranslateZ(0);
                    break;
                case 7:
                    cy.setTranslateX(300);
                    cy.setTranslateZ(300);
                    break;
            }
        }

        for (int i = 8; i < 12; i++) {
            Cylinder cy = bondList.get(i);
            cy.setTranslateZ(150);
            cy.setRotationAxis(Rotate.X_AXIS);
            cy.setRotate(90);

            switch(i) {
                case 8:
                    cy.setTranslateX(0);
                    cy.setTranslateY(0);
                    break;
                case 9:
                    cy.setTranslateX(300);
                    cy.setTranslateY(0);
                    break;
                case 10:
                    cy.setTranslateX(300);
                    cy.setTranslateY(300);
                    break;
                case 11:
                    cy.setTranslateX(0);
                    cy.setTranslateY(300);
                    break;
            }
        }
        for (Cylinder cy: bondList) {
            group.getChildren().add(cy);
        }

        group.getChildren().addAll(s1, s2, s3, s4, c1, c2, c3, c4);

        return group;
    }

    /**
     * Depicts a hard-coded water molecule.
     * @return the group containing the shapes representing the water molecule.
     */
    public Group waterMolecule() {
        Group group = new Group();

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


        Button go = new Button("GO");
        go.setPrefSize(100, 40);
        go.setTextFill(Color.GREEN);
        grid.setRight(go);
        go.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String searchText = search.getText();
                try {
                    WebService spider = new WebService();
                    String formula = spider.getFormula(searchText);

                    System.out.println("Input was  : " + searchText);
                    System.out.println("We got this: " + formula);

                    //If we get a bad input, alert the user and don't proceed.
                    if (formula == null) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("No result found for \"" + searchText + "\"");
                        alert.setContentText("We couldn't seem to find a chemical formula that corresponds to the input! Please try again or try a different input.");

                        alert.showAndWait();
                        return;
                    }

                    TextArea info = new TextArea();
                    Molecule mol = new Molecule(formula, searchText);

                    String printout = String.format("%s has the formula %s.\n\n", searchText.substring(0,1).toUpperCase() + searchText.substring(1), formula);
                    String output = printout + mol;

                    info.setText(output);
                    info.setPrefSize(textPane.widthProperty().doubleValue(), textPane.heightProperty().doubleValue());
                    textPane.setContent(info);

//                    ArrayList<Atom> elemList = new ChemicalFormula(formula).getAtoms();
//                    String chemicals = String.format("It contains the following elements:\n");
//                    String atoms = "";
//                    for (int i = 0; i < elemList.size(); i++) {
//                        atoms += elemList.get(i).getElement().getName();
//                        if (i < elemList.size() - 1) {
//                            atoms += ", ";
//                        }
//                        atoms += "\n";
//                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
