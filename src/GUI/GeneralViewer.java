package GUI;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;


/**
 * Displays the main window of the app with a menu, search bar, and two panels for models and information.
 *
 * @author Sarah Larkin, Emily Anible
 * CS3141, Spring 2018, Team ATOM
 * Date Last Modified: March 30, 2018
 */
public class GeneralViewer extends Application {

    private ScrollPane textPane = new ScrollPane();
    private SubScene imageScene;
    private Stage periodic = new PeriodicTableView();
    private Molecule mol;
    private double oldX = 400;
    private double oldY = 0;
    private double oldZ = 0;
    private boolean subf = true;
    private int failCount = 0;
    private TextArea right;
    private SubScene sub;

    @Override
    public void start(Stage primaryStage) {
        primaryStage = window();
        primaryStage.show();
    }


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
        Group g = new Group();
        sub = sub(g, 500, 700, true, SceneAntialiasing.BALANCED);

        pane.add(sub, 0, 0);

        right = new TextArea();
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
        sub = scene1;
        scene1.setWidth(500);
        scene1.setHeight(700);
        Camera cam = new MoleculeCamera(scene1);
        sub.setCamera(cam);
        return scene1;
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
                searchForMolecule(search);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //If a user presses the enter key after searching, search for a molecule.
        search.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(KeyCode.ENTER)) {
                try {
                    searchForMolecule(search);
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

        userManual.setOnAction(event -> openInBrowser("http://www.github.com/TSP-Molecule/ATOM/wiki"));

        sources.setOnAction(event -> openInBrowser("https://github.com/TSP-Molecule/ATOM/wiki/Sources"));
        about.setOnAction(event -> about());

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
     * @param search User-inputted search text
     * @return Molecule if found, else null.
     * @throws IOException
     */
    private Molecule searchForMolecule(TextField search) throws IOException {
        //search.set
        right.setText("... searching ...");
        textPane.setContent(right);
        String searchText = search.getText();
        System.out.println(searchText);

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
        System.out.println(sub.getRoot());
        sub.setRoot(new MoleculeView(mol));

       // sub = sub(new MoleculeView(mol), 500, 700, true, SceneAntialiasing.BALANCED);
        sub.requestFocus();



        failCount = 0; //Reset fail counter
        return mol;
    }

    /**
     * Dialogue window -- about screen
     *
     */
    private void about() {
        Dialog<ButtonType> dialog = new Dialog<>();
        VBox vbox = new VBox();

        vbox.setMinWidth(dialog.getDialogPane().getWidth());
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(5);

        dialog.setTitle("About");
        dialog.getDialogPane().setContent(vbox);


        Label labelTitle = new Label("ATOM - A Tiny Object Modeler");
        Label labelClass = new Label("CS3141 R02 - Spring 2018");
        Label labelGroup = new Label("Emily Anible | Sarah Larkin | Crystal Fletcher");
        Label spacer     = new Label("");
        Hyperlink hyperlink = new Hyperlink("Project Source");
        hyperlink.setText("Project Source");

        hyperlink.setOnMouseClicked(event -> openInBrowser("https://www.github.com/TSP-Molecule/ATOM"));

        ButtonType select = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(select);
        vbox.getChildren().addAll(labelTitle, labelClass, labelGroup, spacer, hyperlink);

        //Update the name and formula if the user OKs the input
        dialog.showAndWait();
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
        comboBox.valueProperty().addListener((ob, oldV, newV) -> {
            selected.setText("Selected: " + WebService.simplifyFormula(searchResults.get(newV), true));
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

    /**
     * Attempts to open a given link in the user's default web browser.
     *
     * @param link Link to open in Browser
     * @return True if successful, false if not.
     */
    private boolean openInBrowser(String link) {
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(new URI(link));
                return true;
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        }
        return false;
    }


}
