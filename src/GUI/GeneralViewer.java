package GUI;

import javafx.application.Application;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class GeneralViewer extends Application {

    @Override
    public void start(Stage primaryStage) {





    }


    private void makeMenu() {
        MenuBar bar = new MenuBar();
        Menu file = new Menu("File");
        MenuItem open = new MenuItem("Open");
        MenuItem saveImage = new MenuItem("Save");
        MenuItem exit = new Menu("Exit");

        bar.getMenus().add(file);
        file.getItems().add(open);
        file.getItems().add(saveImage);
        file.getItems().add(exit);

        Menu navigate = new Menu("Navigation");


       // bar


        //Menu mainMenu = new
    }
}
