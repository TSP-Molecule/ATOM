package GUI;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.ArrayList;

public class PeriodicTableViewer extends Application{


    GridPane panel = new GridPane();

    @Override
    public void start(Stage primaryStage) {
        Group group = new Group();
        group.getChildren().add(makeTable());
        Line line = new Line(110, 450, 150, 635);
        group.getChildren().add(line);
        Scene scene = new Scene(group, 900, 800);
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
        buttons.add(new PTB(3,1, 0, pane));
        buttons.add(new PTB(4,1, 1, pane));

        for(int i = 2; i < 12; i++) {
            buttons.add(new PTB(-1, 1, i, pane));
        }
        for (int i = 12, j = 5; i < 18; i++, j++) {
            buttons.add(new PTB(j, 1, i, pane));
        }

        buttons.add(new PTB(11, 2, 0, pane));
        buttons.add(new PTB(12,2, 1, pane));
        for(int i = 2; i < 12; i++) {
            buttons.add(new PTB(-1, 2, i, pane));
        }
        for (int i = 12, j = 13; i < 18; i++, j++) {
            buttons.add(new PTB(j, 2, i, pane));
        }
        for(int i = 0, j = 19, r = 3; i < 18; i++, j++ ) {
            buttons.add(new PTB(j,r,i,pane ));
            if (i == 17 && j == 36) {
                i = -1;
                r = 4;
            }
        }
        buttons.add(new PTB(55, 5, 0, pane));
        buttons.add(new PTB(56, 5, 1, pane));
        for(int i = 3, j = 72; i < 18; i++, j++) {
            buttons.add(new PTB(j, 5, i, pane));
        }

        buttons.add(new PTB(87, 6, 0, pane));
        buttons.add(new PTB(88, 6, 1, pane));
        for(int i = 3, j = 104; i < 18; i++, j++) {
            buttons.add(new PTB(j, 6, i, pane));
        }
        for(int i = 0; i < 18; i++) {
            buttons.add(new PTB(-1, 7, i, pane));
        }
        for(int i = 0; i < 3; i++) {
            buttons.add(new PTB(-1, 8, i, pane));
        }
        for(int i = 3, j = 57; i < 18; i++, j++) {
            buttons.add(new PTB(j, 8, i, pane));
        }
        for(int i = 0; i < 3; i++) {
            buttons.add(new PTB(-1, 9, i, pane));
        }
        for(int i = 3, j = 89; i < 18; i++, j++) {
            buttons.add(new PTB(j, 9, i, pane));
        }
       // buttons.add(new PTB(19, 3, 0, pane));
       // buttons.add(new PTB(4,1,13,pane));
       // buttons.add(new PTB(5,1,14, pane));


      //  buttons.add(new PTB(3,1, 0, pane));
        return pane;
    }
}
