package GUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class PeriodicTableViewer extends Application{


    GridPane panel = new GridPane();

    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(makeTable(), 900, 800);
        primaryStage.setTitle("Periodic Table of the Elements");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private GridPane makeTable() {
        GridPane pane = new GridPane();
        ArrayList<Button> buttons = new ArrayList<>();
        buttons.add(new PTB(1, 0, 0, pane));
        buttons.add(new PTB(3,1, 0, pane));
        for(int i = 1; i < 17; i++) {
            buttons.add(new PTB(-1, 0, i, pane));
        }
        buttons.add(new PTB(2, 0, 17, pane));
      //  buttons.add(new PTB(3,1, 0, pane));
        return pane;
    }
}
