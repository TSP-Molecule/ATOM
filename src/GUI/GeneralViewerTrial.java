package GUI;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import structures.MolFile;
import structures.Molecule;
import structures.enums.Elem;
import web.WebService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;


/**
 * Displays the main window of the app with a menu, search bar, and two panels for models and information.
 *
 * @author Sarah Larkin, Emily Anible
  @author CS3141, Spring 2018, Team ATOM
 * Date Last Modified: March 30, 2018
 */
public class GeneralViewerTrial extends Application {

    private ScrollPane textPane = new ScrollPane();
    private SubScene imageScene;
    private Stage periodic = new PeriodicTableView();
    private Molecule mol;
    private double oldX = 400;
    private double oldY = 0;
    private double oldZ = 0;
    private boolean subf = true;
    private int failCount = 0;

    @Override
    public void start(Stage primaryStage) {
        primaryStage = window();
        primaryStage.show();
    }

    SubScene sub;
    /**
     * Displays the full window with a split view
     *
     * @return the stage containing the window
     */
    public Stage window() {
        Stage stage = new Stage();
        stage.setMaxWidth(1000);
        stage.setMaxHeight(700);
        Group group = new Group();
        GridPane pane = new GridPane();
        pane.setBackground(new Background(new BackgroundFill(Color.rgb(255, 240, 200), new CornerRadii(2), new Insets(2))));
        pane.setPrefSize(1000, 700);

        TextArea left = new TextArea("Molecule here");
        left.setPrefSize(500, 770);
        left.setBackground(new Background(new BackgroundFill(Color.rgb(255, 200, 220), new CornerRadii(2), new Insets(0))));
        Group g = salt();
        SubScene sub = sub(new MoleculeView(new Molecule("C_{3}_H_{6}_O", "acetone")), 500, 700, true, SceneAntialiasing.DISABLED);
System.out.println("bah bah black sheep");
        pane.add(sub, 0, 0);

        TextArea right = new TextArea();
        right.setPromptText("Molecule information will be displayed here.");
        right.setEditable(false);
        right.setOnMouseClicked(event -> sub.requestFocus());
        right.setPrefSize(500, 700);
        right.setBackground(new Background(new BackgroundFill(Color.rgb(200, 255, 220), new CornerRadii(2), new Insets(0))));
        textPane.setContent(right);
        textPane.setMinSize(600, 700);
        pane.add(textPane, 1, 0);

        BorderPane mainPane = new BorderPane();

        //HBox for Menu and Searchbar
        HBox topBar = new HBox();
        topBar.getChildren().addAll(makeMenuBar(stage), searchBar());

        mainPane.setTop(topBar);
        mainPane.setCenter(pane);

        Scene scene = new Scene(mainPane, 1200, 800);

        // Set default selection of subscene upon entry or click
        sub.setOnMouseEntered(event -> {
            sub.requestFocus();
            subf = true;
        });
        sub.setOnMousePressed(event -> {
            sub.requestFocus();
            oldX = event.getX();
        });
        stage.setScene(scene);
        stage.setTitle("ATOM - A Tiny Object Modeler");
        pane.requestFocus();
        return stage;

    }

    /**
     * Makes a subscene which is an alternate option for displaying a 3D model
     *
     * @param scene the parent scene, or window
     * @param w     the width of the subscene
     * @param h     the height of the subscene
     * @return the subscene
     */
    private SubScene sub(Parent scene, double w, double h, boolean b, SceneAntialiasing s) {
        SubScene scene1 = new SubScene(scene, w, h, b, SceneAntialiasing.BALANCED);
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


    public Sphere atom(Elem elem) {
        Color c = elem.getColor();
        PhongMaterial material = new PhongMaterial();
        material.setSpecularColor(c);
        material.setDiffuseColor(c);
        Sphere sphere = new Sphere(50);
        sphere.setMaterial(material);
        return sphere;
    }

    /**
     * Search bar: handles searching for molecules
     *
     * @return the search bar
     */
    private BorderPane searchBar() {
        BorderPane grid = new BorderPane();
        TextField search = new TextField();
        search.setPromptText("search");
        search.setEditable(true);
        search.setPrefWidth(390);
        grid.setLeft(search);

        Button go = new Button("GO");
        go.setPrefWidth(100);
        go.setTextFill(Color.GREEN);
        grid.setRight(go);

        //If a user presses "GO" after entering search input, search for a molecule.
        go.setOnAction(event -> {
            try {
                searchForMolecule(search.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //If a user presses the enter key after searching, search for a molecule.
        search.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(KeyCode.ENTER)) {
                try {
                    searchForMolecule(search.getText());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        return grid;
    }

    /**
     * Makes and returns a menu bar to be displayed in the given stage.
     *
     * @param p the stage in which to display the menu
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
        viewPeriodicTable.setOnAction(event -> {
            PeriodicTableView view = new PeriodicTableView();
            view.show();
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
//        bar.setPrefHeight(40);
        bar.setPrefWidth(500);
        return bar;
    }

    /**
     * Makes a "file" menu with options to open, save, and exit
     *
     * @param p the stage in which the menu is displayed
     * @return the new menu
     */
    private Menu makeFileMenu(Stage p) {
        Menu file = new Menu("File");

        //Open Action. Opens a .mol file to a Molecule.
        MenuItem open = new MenuItem("Open");
        open.setOnAction(event -> {
            FileChooser chooser = new FileChooser();
            chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("ATOM File", "*.mol"));

            File molLoad = chooser.showOpenDialog(p.getScene().getWindow());

            if (molLoad != null) {
                String filename = molLoad.getAbsolutePath();
                try {
                    mol = MolFile.loadMolecule(filename);
                    System.out.println("We loaded in: " + mol);
                    //TODO: Update information and model with loaded molecule
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        //Save Action. Saves a Molecule to a .mol.
        MenuItem save = new MenuItem("Save");
        save.setOnAction(event -> {
            FileChooser chooser = new FileChooser();
//            chooser.showSaveDialog(p);
            chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("ATOM File", "*.mol"));

            File molsave = chooser.showSaveDialog(p.getScene().getWindow());

            if (molsave != null) {
                String filename = molsave.getAbsolutePath();
                try {
                    MolFile.saveMolecule(mol, filename);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        //TODO: SaveImage. Saves an image of the 3D Model
        MenuItem saveImage = new MenuItem("Save Image");
        saveImage.setOnAction(event -> {
            FileChooser chooser = new FileChooser();
            chooser.showSaveDialog(p);
        });

        //Exit Action. Closes the program.
        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(event -> p.close());
        file.getItems().addAll(open, save, saveImage, exit);
        return file;
    }

    /**
     * Searches for molecule and attempts to update relevant information
     *
     * @param searchText User-inputted search text
     * @return Molecule if found, else null.
     * @throws IOException
     */
    private Molecule searchForMolecule(String searchText) throws IOException {
        String name = null;
        String formula = null;
        HashMap<String, String> results = WebService.getFormula(searchText);

        // What to do based on the size (or existence) of results returned
        if (results == null) {
            failCount++;
            if (failCount < 3) {
                alert(Alert.AlertType.ERROR,
                        "No result found for \"" + searchText + "\"",
                        "We couldn't seem to find a chemical formula that corresponds to the input! "
                                + "Please try again or try a different input.");
            } else {
                //If we get 3 fails in a row, let the user know that there's something strange in their neighborhood.
                alert(Alert.AlertType.ERROR,
                        "No result found for \"" + searchText + "\"",
                        "We couldn't seem to find a chemical formula that corresponds to the input! "
                                + "Please try again or try a different input."
                                + "\n\nIf there appears to be an issue unrelated to your search query "
                                + "or internet connection, "
                                + "try running the standalone ChemSpider.py script outside of ATOM.");
            }
            return null;
        } else if (results.size() == 1) {
            // Not really a for loop... just gets the first (only) value.
            for (String s : results.keySet()) {
                name = s;
                formula = results.get(s);
            }
        } else if (results.size() > 1) {
            name = selectResult(results);
            formula = (name != null) ? results.get(name) : null;
        }

        //If the user didn't select something or something went wrong, we're done.
        if (name == null || formula == null) {
            return null;
        }

        //From here, we have a name and formula, and can proceed.
        mol = new Molecule(formula, name);  //Create and build molecule with formula, named user input.

        //Display Molecule information in TextArea
        TextArea info = new TextArea();
        info.setText(String.format("%s has the formula %s.\n\n %s", name, formula, mol));
        info.setEditable(false);
        info.setPrefSize(textPane.widthProperty().doubleValue(), textPane.heightProperty().doubleValue());
        textPane.setContent(info);

        // Display Molecule in 3D graphics
        sub = sub(new MoleculeView(mol), 500, 700, true, SceneAntialiasing.DISABLED);





        failCount = 0; //Reset fail counter
        return mol;
    }

    /**
     * Dialogue window to select a result if search returns multiple results.
     *
     * @param searchResults HashMap of results from search
     * @return String of name in HashMap, or null if the user selects nothing.
     */
    private String selectResult(HashMap<String, String> searchResults) {
        Dialog<ButtonType> dialog = new Dialog<>();
        VBox vbox = new VBox();

        vbox.setMinWidth(dialog.getDialogPane().getWidth());
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(5);
        ComboBox<String> comboBox;

        ObservableList<String> comboBoxData = FXCollections.observableArrayList();
        comboBoxData.addAll(searchResults.keySet());

        dialog.setTitle("Multiple Results Found");
        dialog.getDialogPane().setContent(vbox);

        comboBox = new ComboBox<>(comboBoxData);
        comboBox.setMinWidth(200);
        comboBox.getSelectionModel().selectFirst();

        ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType select = new ButtonType("Select", ButtonBar.ButtonData.OK_DONE);

        Label helpText = new Label();
        Label selected = new Label();

        helpText.setText("Select one of the results, or try refining your search.");
        selected.setText("Selected: " + WebService.simplifyFormula(searchResults.get(comboBox.getValue()), true));

        dialog.getDialogPane().getButtonTypes().addAll(cancel, select);
        vbox.getChildren().addAll(helpText, comboBox, selected);

        //Update Selected text if the user changes their selection
        HashMap<String, String> finalResults = searchResults; //final form of results for lambda
        comboBox.valueProperty().addListener((ob, oldV, newV) -> {
            selected.setText("Selected: " + WebService.simplifyFormula(finalResults.get(newV), true));
        });

        //Update the name and formula if the user OKs the input
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == select) {
            return comboBox.getValue();
        } else return null;
    }

    /**
     * Creates an Alert and displays it.
     *
     * @param alertType    Type that the alert should be
     * @param header       header of the alert
     * @param alertMessage message of the alert
     */
    private void alert(Alert.AlertType alertType, String header, String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(alertType.toString());
        alert.setHeaderText(header);
        alert.setContentText(alertMessage);
        alert.showAndWait();
    }

    // TEST MOLECULES

    /**
     * Depicts a hard-coded salt molecule using colors from the element enum.
     *
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
        for (int i = 0; i < 12; i++) {
            Cylinder cy = new Cylinder(10, bond);
            PhongMaterial mat = new PhongMaterial(Color.LIGHTGREY);
            mat.setSpecularColor(Color.LIGHTGREY);
            mat.setDiffuseColor(Color.LIGHTGREY);
            cy.setMaterial(mat);
            bondList.add(cy);
        }

        for (int i = 0; i < 4; i++) {
            Cylinder cy = bondList.get(i);
            cy.setTranslateX(150);
            cy.setRotationAxis(Rotate.Z_AXIS);
            cy.setRotate(90);

            switch (i) {
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

            switch (i) {
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

            switch (i) {
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
        for (Cylinder cy : bondList) {
            group.getChildren().add(cy);
        }

        group.getChildren().addAll(s1, s2, s3, s4, c1, c2, c3, c4);

        return group;
    }

    /**
     * Depicts a hard-coded water molecule.
     *
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

        Cylinder b1 = new Cylinder(10, bond);
        PhongMaterial blackM = new PhongMaterial();
        blackM.setDiffuseColor(Color.BLACK);
        blackM.setSpecularColor(Color.BLACK);
        b1.setMaterial(blackM);

        Cylinder b2 = new Cylinder(10, bond);
        b2.setMaterial(blackM);

        oxygen.setTranslateX(0);
        oxygen.setTranslateY(0);
        oxygen.setTranslateZ(0);

        hydrogen.setTranslateX(bond * Math.cos(-1 * Math.PI / 3));
        hydrogen.setTranslateY(bond * Math.sin(-1 * Math.PI / 3));
        hydrogen.setTranslateZ(0);

        hydrogen1.setTranslateX(bond * Math.cos(Math.PI / 3));
        hydrogen1.setTranslateY(bond * Math.sin(Math.PI / 3));
        hydrogen1.setTranslateZ(0);

        b1.setTranslateX(bond * Math.cos(-1 * Math.PI / 3) / 2 - 0);
        b1.setTranslateY(bond * Math.sin(-1 * Math.PI / 3) / 2 + 0);
        b1.setTranslateZ(0);
        b1.setRotationAxis(Rotate.Z_AXIS);
        b1.setRotate(30);

        b2.setTranslateX(bond * Math.cos(Math.PI / 3) / 2);
        b2.setTranslateY(bond * Math.sin(Math.PI / 3) / 2);
        b2.setTranslateZ(0);
        b2.setRotationAxis(Rotate.Z_AXIS);
        b2.setRotate(150);

        group.getChildren().addAll(b1, b2, hydrogen, oxygen, hydrogen1);

        return group;

    }

    public Group salt() {
        Group group = new Group();
        Color c = Elem.getBySymbol("Cl").getColor();
        Color s = Elem.getBySymbol("Na").getColor();
        int bond = 300;

        Sphere c1 = atom(Elem.getBySymbol("Cl"));
        //        c1.setMaterial(new PhongMaterial(c));
        c1.setTranslateX(300);
        c1.setTranslateY(0);
        c1.setTranslateZ(0);

        Sphere c2 = atom(Elem.getBySymbol("Cl"));
        //        c2.setMaterial(new PhongMaterial(c));
        c2.setTranslateX(0);
        c2.setTranslateY(300);
        c2.setTranslateZ(0);

        Sphere c3 = atom(Elem.getBySymbol("Cl"));
        //        c3.setMaterial(new PhongMaterial(c));
        c3.setTranslateX(300);
        c3.setTranslateY(300);
        c3.setTranslateZ(300);

        Sphere c4 = atom(Elem.getBySymbol("Cl"));
        //        c4.setMaterial(new PhongMaterial(c));
        c4.setTranslateX(0);
        c4.setTranslateY(0);
        c4.setTranslateZ(300);

        Sphere s1 = atom(Elem.getBySymbol("Na"));
        //        s1.setMaterial(new PhongMaterial(s));
        s1.setTranslateX(0);
        s1.setTranslateY(0);
        s1.setTranslateZ(0);

        Sphere s2 = atom(Elem.getBySymbol("Na"));
        //        s2.setMaterial(new PhongMaterial(s));
        s2.setTranslateX(300);
        s2.setTranslateY(300);
        s2.setTranslateZ(0);

        Sphere s3 = atom(Elem.getBySymbol("Na"));
        //        s3.setMaterial(new PhongMaterial(s));
        s3.setTranslateX(300);
        s3.setTranslateY(0);
        s3.setTranslateZ(300);

        Sphere s4 = atom(Elem.getBySymbol("Na"));
        //        s4.setMaterial(new PhongMaterial(s));
        s4.setTranslateX(0);
        s4.setTranslateY(300);
        s4.setTranslateZ(300);

        double p = 150;
        ArrayList<Cylinder> bondList = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            Cylinder cy = new Cylinder(10, bond);
            cy.setMaterial(new PhongMaterial(Color.LIGHTGREY));
            bondList.add(cy);
        }

        for (int i = 0; i < 4; i++) {
            Cylinder cy = bondList.get(i);
            cy.setTranslateX(150);
            cy.setRotationAxis(Rotate.Z_AXIS);
            cy.setRotate(90);

            switch (i) {
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

            switch (i) {
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

            switch (i) {
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
        for (Cylinder cy : bondList) {
            group.getChildren().add(cy);
        }

        group.getChildren().addAll(s1, s2, s3, s4, c1, c2, c3, c4);

        return group;
    }
}
