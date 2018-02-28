package GUI;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import structures.Atominomicon;
import structures.enums.Elem;

import java.util.ArrayList;

public class GeneralViewer extends Application {

    Atominomicon atominimicon;

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
        pane.setBackground(new Background(new BackgroundFill(Color.rgb(255, 0, 0), new CornerRadii(2), new Insets(2))));
        pane.setPrefSize(1200, 600);

        TextArea left = new TextArea("Molecule here");
        left.setMinSize(600, 700);
        left.setBackground(new Background(new BackgroundFill(Color.rgb(255, 200, 220), new CornerRadii(2), new Insets(0))));
        pane.add(left, 0, 0);

        TextArea right = new TextArea("Molecule Info will go here");
        right.setMinSize(600, 700);
        right.setBackground(new Background(new BackgroundFill(Color.rgb(200, 255, 220), new CornerRadii(2), new Insets(0))));
        pane.add(right, 1, 0);


        group.getChildren().add(pane);
        BorderPane mainPane = new BorderPane();
        mainPane.setPrefSize(stage.getWidth(), stage.getHeight());
        mainPane.setTop(makeMenuBar(stage));
        mainPane.setCenter(group);
        Scene scene = new Scene(mainPane, 1200, 800);
        stage.setScene(scene);
        return stage;

    }


    private GridPane displayPane(Stage stage) {
        GridPane p = new GridPane();
       // p.setPrefSize(stage.getWidth()/2, stage.getHeight()*2/3);
        p.setMinSize(stage.getWidth()/2, stage.getHeight() *2/3);
        p.setBackground(new Background(new BackgroundFill(Color.rgb(255, 0, 0), new CornerRadii(2), new Insets(2))));
        return p;
    }

    private FlowPane viewPane(Stage stage) {
        FlowPane pane = new FlowPane();
        Text info = new Text(pane.getWidth()/2, pane.getHeight(), "This will display a molecule");
        pane.getChildren().add(info);
        pane.setPrefSize(stage.getWidth()/2, stage.getHeight());
        pane.setBackground( new Background(new BackgroundFill(Color.rgb(255, 250, 0), new CornerRadii(2), new Insets(2))));

        return pane;
    }

    private SubScene sub (Parent scene, double w, double h) {
        SubScene scene1 = new SubScene(scene, w, h);
        scene1.setWidth(500);
        scene1.setHeight(600);
        scene1.setFill(Color.rgb(180, 255, 180));
        return scene1;
    }


    /**
     * Makes the search bar.
     * @param p
     * @return
     */
    private FlowPane searchBar(BorderPane p) {
        FlowPane f = new FlowPane();
        TextField search = new TextField();
        search.setEditable(true);
        search.setPrefSize(800, 90);
        f.getChildren().add(search);
        Button go = new Button("GO");
        go.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String s = search.getText();
                try {
                    atominimicon = new Atominomicon();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // We really need a Search class that will run the search through the atominomicon and database.
                atominimicon.readElem(Elem.valueOf(s).getNum());
            }
        });
        return f;
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
                        // Display molecule
                      //  Window w;
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
       // bar.getMenus().add

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

    private Menu makeMenu(String s, MenuItem ... args ) {
        return null;
    }

    private MenuItem makeMenuItem(String s) {
        MenuItem item = new MenuItem(s);
        item.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                switch (s) {
                    case "save":
                }
            }
        });
        return null;
    }


//    Menu file = new Menu("File");
//    MenuItem open = new MenuItem("Open");
//    MenuItem saveImage = new MenuItem("Save");
//    MenuItem exit = new MenuItem("Exit");
}
