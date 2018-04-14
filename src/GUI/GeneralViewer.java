package GUI;

import com.sun.javafx.PlatformUtil;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
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
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import structures.MolFile;
import structures.Molecule;
import web.WebService;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Optional;


/**
 * Displays the main window of the app with a menu, search bar, and two panels for models and information.
 *
 * @author Sarah Larkin, Emily Anible
 *
 * CS3141, Spring 2018, Team ATOM
 * Date Last Modified: March 30, 2018
 */
public class GeneralViewer extends Application {

    /**
     * Default width of the program window
     */
    private static final int PROG_WIDTH = 1000;

    /**
     * Default height of the program window
     */
    private static final int PROG_HEIGHT = 700;

    /**
     * Main 2x2 Grid. Holds entire main contents.
     */
    private GridPane gridPane;

    /**
     * Info area. Updates wiki information.
     */
    private TextArea textInfo;
    /**
     * Search text field.
     */
    private TextField searchBox;
    /**
     * Subscene. Contains molecule image.
     */
    private SubScene sub;
    /**
     * Camera to control subscene
     */
    Camera cam;

    /**
     * Subscene. Contains molecule image.
     */
    private SubScene sub2D;

    /**
     * Subscene. Contains molecule image.
     */
    private SubScene sub3D;
    /**
     * Boolean to track 2D vs. 3D display
     */
    private boolean dim3D = true;
    /**
     * Null counter. Gives an error to user if it reaches a specified value.
     */
    private int nullSearchCount = 0;

    /**
     * Molecule loaded in to subscene.
     */
    private Molecule mol;

    private boolean offlineMode = false;

    private Stage stage = new Stage();

    @Override
    public void start(Stage primaryStage) {
        primaryStage = window();
        primaryStage.show();
        stage = primaryStage;
    }

    /**
     * Displays the full window with a split view
     *
     * @return the stage containing the window
     */
    private Stage window() {
        Stage stage = new Stage();
        stage.setMinWidth(PROG_WIDTH);
        stage.setMinHeight(PROG_HEIGHT);

        stage.heightProperty().addListener(observable -> {
            if (mol != null) {
                updateView();
            }
        });
        stage.widthProperty().addListener(observable -> {
            if (mol != null) {
                updateView();
            }
        });


        //Main Grid of Program. 2x2.
        gridPane = new GridPane();
        gridPane.setBackground(new Background(new BackgroundFill(Color.rgb(195, 195, 195), new CornerRadii(2), new Insets(2))));
//        gridPane.gridLinesVisibleProperty().set(true);

        Group g = new Group(); //Test Molecule line
        sub = sub(g, 500, 600, true, SceneAntialiasing.BALANCED);
        sub2D = new SubScene(new Group(), 500, 600);
        sub3D = new SubScene(new Group(), 500, 600, true, SceneAntialiasing.BALANCED);
        //Components of GridPane
        gridPane.add(sub, 0, 1);
        gridPane.add(makeMenuBar(stage), 0, 0);
        gridPane.add(searchArea(), 1, 0);
        gridPane.add(informationArea(), 1, 1);

        //GridPane Row/Col Dimensions
        RowConstraints row0 = new RowConstraints();
        RowConstraints row1 = new RowConstraints();
        ColumnConstraints col0 = new ColumnConstraints();
        ColumnConstraints col1 = new ColumnConstraints();
        row0.setPercentHeight(4);
        row1.setPercentHeight(96);
        col0.setPercentWidth(50);
        col1.setPercentWidth(50);
        gridPane.getRowConstraints().addAll(row0, row1);
        gridPane.getColumnConstraints().addAll(col0, col1);


        //Set up Scene
        Scene scene = new Scene(gridPane, PROG_WIDTH, PROG_HEIGHT);
        stage.setScene(scene);
        stage.setTitle("ATOM - A Tiny Object Modeler");
        gridPane.requestFocus();

        //Sub Listeners
        sub.setOnMouseEntered(event -> {
            sub.requestFocus();
        });

        sub.setOnMousePressed(event -> {
            sub.requestFocus();
        });

        return stage;
    }

    /**
     * Creates the Search Box and Button
     *
     * @return grid of search box and button
     */
    private GridPane searchArea() {
        GridPane gridSearch = new GridPane();

        searchBox = new TextField();
        Button buttonGo = new Button("GO");
        buttonGo.setTextFill(Color.GREEN);

        gridSearch.add(searchBox, 0, 0);
        gridSearch.add(buttonGo, 1, 0);

        searchBox.setPromptText("Search for a molecule");

        // Column Sizing
        ColumnConstraints col0 = new ColumnConstraints();
        ColumnConstraints col1 = new ColumnConstraints();
        col0.setPercentWidth(80);
        col1.setPercentWidth(20);
        gridSearch.getColumnConstraints().addAll(col0, col1);

        searchBox.prefWidthProperty().bind(gridSearch.widthProperty());
        searchBox.prefHeightProperty().bind(gridSearch.heightProperty());
        buttonGo.prefWidthProperty().bind(gridSearch.widthProperty());
        buttonGo.prefHeightProperty().bind(gridSearch.heightProperty());

        buttonGo.setOnAction(event -> {
            try {
                searchForMolecule(searchBox);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        searchBox.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(KeyCode.ENTER)) {
                try {
                    searchForMolecule(searchBox);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        return gridSearch;
    }

    /**
     * Creates information area, contains info and button to go to wikipedia
     *
     * @return information grid
     */
    private GridPane informationArea() {
        GridPane gridInfo = new GridPane();
        ScrollPane scrollInfo = new ScrollPane();

        textInfo = new TextArea();
        textInfo.setPromptText("Molecule information will be displayed here.");
        textInfo.setEditable(false);
        textInfo.setWrapText(true);
        textInfo.setOnMouseClicked(event -> sub.requestFocus());

        Button buttonInfo = new Button("View In Browser");


        gridInfo.add(textInfo, 0, 0);
        gridInfo.add(buttonInfo, 0, 1);

        // Column Sizing
        RowConstraints row0 = new RowConstraints();
        RowConstraints row1 = new RowConstraints();
        row0.setPercentHeight(95);
        row1.setPercentHeight(5);
        gridInfo.getRowConstraints().addAll(row0, row1);

        gridInfo.prefWidthProperty().bind(gridPane.widthProperty());
        gridInfo.prefHeightProperty().bind(gridPane.heightProperty());
        scrollInfo.prefWidthProperty().bind(gridInfo.widthProperty());
        scrollInfo.prefHeightProperty().bind(gridInfo.heightProperty());
        textInfo.prefWidthProperty().bind(gridInfo.widthProperty());
        textInfo.prefHeightProperty().bind(gridInfo.heightProperty());
        buttonInfo.prefWidthProperty().bind(gridInfo.widthProperty());
        buttonInfo.prefHeightProperty().bind(gridInfo.heightProperty());

        buttonInfo.setOnMouseClicked(event -> {

            if (Desktop.isDesktopSupported() && searchBox.getText().replaceAll(" ", "").length() > 0) {
                try {
                    Desktop.getDesktop().browse(new URI("http://www.wikipedia.com/wiki/" + searchBox.getText().replaceAll(" ", "_")));
                } catch (IOException | URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        });

        return gridInfo;
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
        bar.getMenus().add(makeNavMenu(p));
        bar.getMenus().add(makeHelpMenu(p));

        bar.prefWidthProperty().bind(gridPane.widthProperty());
        bar.prefHeightProperty().bind(gridPane.heightProperty());
        return bar;
    }

    /**
     * Makes a "navigation" menu with options to view the 3D and 2D models, as well as
     * to view the periodic table of elements in a new window.
     *
     * @param p the stage in which the menu is displayed
     * @return the new menu
     */
    private Menu makeNavMenu(Stage p) {
        Menu navigation = new Menu("Navigation");
        MenuItem view3D = new MenuItem("3D View");
        MenuItem view2D = new MenuItem("2D View");
        MenuItem viewPeriodicTable = new MenuItem("View periodic table");
        navigation.getItems().addAll(view2D, view3D, viewPeriodicTable);


        viewPeriodicTable.setOnAction(event -> {
            PeriodicTableView view = new PeriodicTableView();
            view.show();
        });

        view2D.setOnAction(event -> {
            if (mol == null) return;
            dim3D = false;
            updateView();
        });
        view3D.setOnAction(event -> {
            if (mol == null) return;
            dim3D = true;
            updateView();
        });
        return navigation;
    }

    /**
     * Makes a "help" menu with options to view the user menu, program info, and sources.
     *
     * @param p the stage in which the menu is displayed
     * @return the new menu
     */
    private Menu makeHelpMenu(Stage p) {
        Menu help = new Menu("Help");
        MenuItem userManual = new MenuItem("User Manual");
        MenuItem about = new MenuItem("About");
        MenuItem sources = new MenuItem("Sources");
        help.getItems().addAll(userManual, about, sources);

        userManual.setOnAction(event -> openInBrowser("http://www.github.com/TSP-Molecule/ATOM/wiki"));

        sources.setOnAction(event -> openInBrowser("https://github.com/TSP-Molecule/ATOM/wiki/Sources"));
        about.setOnAction(event -> about());

        return help;
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
            if (mol == null) return;
            FileChooser chooser = new FileChooser();
            chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("ATOM File", "*.mol"));

            String userHome = System.getProperty("user.home") + (PlatformUtil.isWindows() ? "/Desktop" : "");
            chooser.setInitialDirectory(new File(userHome));
            File molLoad = chooser.showOpenDialog(p.getScene().getWindow());

            if (molLoad != null) {
                String filename = molLoad.getAbsolutePath();
                try {
                    mol = MolFile.loadMolecule(filename);
                    if (mol != null) {
                        textInfo.setText(WebService.getWikiAsString(mol.getName()));
                        searchBox.setText(mol.getName());
                        sub.setRoot(new MoleculeView(mol));
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        //Save Action. Saves a Molecule to a .mol.
        MenuItem save = new MenuItem("Save");
        save.setOnAction(event -> {
            if (mol == null) return;
            FileChooser chooser = new FileChooser();
//            chooser.showSaveDialog(p);
            chooser.setInitialFileName(searchBox.getText());

            String userHome = System.getProperty("user.home") + (PlatformUtil.isWindows() ? "/Desktop" : "");
            chooser.setInitialDirectory(new File(userHome));
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

        MenuItem saveImage = new MenuItem("Save Image");
        saveImage.setOnAction(event -> {
            if (mol == null) return;
            FileChooser chooser = new FileChooser();
            String userHome = System.getProperty("user.home") + (PlatformUtil.isWindows() ? "/Desktop" : "");

            chooser.setInitialFileName(searchBox.getText() + "_img");
            chooser.setInitialDirectory(new File(userHome));
            chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG (*.png)", "*.png"));
            File imageFile = chooser.showSaveDialog(p);

            try {
                SnapshotParameters param = new SnapshotParameters();
                param.setFill(Color.TRANSPARENT);
                WritableImage img = new WritableImage((int) sub.getWidth(), (int) sub.getHeight());
                sub.snapshot(param, img);
                ImageIO.write(SwingFXUtils.fromFXImage(img, null), "png", imageFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        CheckMenuItem offlineToggle = new CheckMenuItem("Offline Mode");
        offlineToggle.setOnAction(event -> {
            offlineMode = offlineToggle.isSelected();

            if ( offlineToggle.isSelected() ) {
                alert(Alert.AlertType.INFORMATION,
                        "Offline Mode Toggled!",
                        "You have enabled offline mode. ATOM will no longer validate your input, and will instead " +
                                "create a molecule based on the formula entered. Please use entries of the form " +
                                "At_{#}At_{#}... to ensure that your formula will be parsed correctly." +
                                "\n\n- Debug information will be shown in place of the typical wikipedia entries." +
                                "\n- The element information on the periodic table will continue to attempt to use web functionality.");
            }
        });




        //Exit Action. Closes the program.
        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(event -> p.close());
        file.getItems().addAll(open, save, saveImage, offlineToggle, exit);
        return file;
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
        cam = new MoleculeCamera(scene1, true);
        cam.setRotationAxis(Rotate.Y_AXIS);
        cam.setRotate(45);

        cam.setTranslateX(-scene1.getWidth() * 2.5);
        cam.setTranslateY(-scene1.getHeight() * 0.5);
        cam.setTranslateZ(-700);
        sub.setCamera(cam);
        return scene1;
    }

    /**
     * Searches for molecule and attempts to update relevant information
     *
     * @param search User-inputted search text
     * @return Molecule if found, else null.
     * @throws IOException
     */
    private Molecule searchForMolecule(TextField search) throws IOException {
        String searchText = search.getText();

        String name = null;
        String formula = null;
        HashMap<String, String> results = null;
        //Don't use the webservice if we're offline.
        if (offlineMode) {
            name = search.getText();
            formula = search.getText();
        } else {
            results = WebService.getFormula(searchText);
        }

        // What to do based on the size (or existence) of results returned
        if (results == null && name == null && formula == null) {
            nullSearchCount++;
            if (nullSearchCount < 3) {
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
        } else if (results != null && results.size() == 1) {
            // Not really a for loop... just gets the first (only) value.
            for (String s : results.keySet()) {
                name = s;
                formula = results.get(s);
            }
        } else if (results != null && results.size() > 1) {
            name = selectResult(results);
            if (name != null) search.setText(name);
            formula = (name != null) ? results.get(name) : null;
        }

        //If the user didn't select something or something went wrong, we're done.
        if (name == null || formula == null) {
            return null;
        }

        //From here, we have a name and formula, and can proceed.
        mol = new Molecule(formula, name);  //Create and build molecule with formula, named user input.

        //Display Molecule information in TextArea

        textInfo.setText(String.format("%s has the formula %s.\n\n %s",
                name,
                formula,
                mol));

//        sub3D.setRoot(new MoleculeView(mol));
//        sub.setRoot(new Lewis(mol));
//        sub.getRoot().getTransforms().add(new Rotate(50, 0, 0, 0, Rotate.Y_AXIS));
//        sub.getRoot().getTransforms().add(new Translate(-300, -200, -1500));
//       // sub = sub2D;
//        //sub.setRoot(new Lewis(mol));
//       // sub.setRoot(new MoleculeView(mol));
//        sub.requestFocus();
        updateView();
        if (mol != null && !offlineMode) textInfo.setText(WebService.getWikiAsString(mol.getName()));

        nullSearchCount = 0; //Reset fail counter
        return mol;
    }

    /**
     * Update the viewer to the present molecule
     */
    private void updateView() {
        sub.setWidth(gridPane.widthProperty().doubleValue());
        sub.setHeight(gridPane.heightProperty().doubleValue());
        sub.getRoot().prefWidth(gridPane.widthProperty().doubleValue());
        sub.getRoot().prefHeight(gridPane.heightProperty().doubleValue());
      //  searchBox.prefHeightProperty().bind(gridPane.heightProperty());
        if (dim3D) {

            sub.setRoot(new MoleculeView(mol));
            sub.setWidth(stage.getWidth()/2);
            sub.setHeight(stage.getHeight() * 7 / 8);
            sub.setCamera(new MoleculeCamera(sub, true));
            sub.requestFocus();
        } else {
            sub.setRoot(new Lewis(mol));
            sub.setCamera(new MoleculeCamera(sub, false));
        }
//        sub.getRoot().minWidth(sub.getWidth());
//        sub.getRoot().minHeight(sub.getHeight());

    }

    /**
     * Dialogue window -- about screen
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
        Label spacer = new Label("");
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

    /**
     * Returns whether 3D is true
     */
    public boolean get3D() {
        return dim3D;
    }
}
