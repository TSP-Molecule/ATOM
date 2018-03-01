package GUI;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import java.util.ArrayList;

/**
 * Displays the periodic table of the elements.
 * @author Sarah Larkin
 *
 */
public class PeriodicTableView extends Stage {

    public PeriodicTableView() {
        Group group = new Group();
        group.getChildren().add(makeTable());
        Line line = new Line(110, 450, 145, 685);
        Line line2 = new Line(110, 530, 145, 760);
        group.getChildren().addAll(line, line2);
        Scene scene = new Scene(group, 900, 800);
        setTitle("Periodic Table of the Elements");
        setScene(scene);
        show();
    }

    private ArrayList<Button> buttons = new ArrayList<>();

    /**
     * Gets the list of buttons in the table
     * @return the button list
     */
    public ArrayList<Button> getButtons() {
        return buttons;
    }

    /**
     * Puts all the buttons in the table - hardcoded for now but will be rewritten in the near future.
     * @return the pane with all the buttons in it.
     */
    private GridPane makeTable() {
        GridPane pane = new GridPane();
        ArrayList<Button> buttons = new ArrayList<>();
        buttons.add(new PTB(1, 0, 0, pane, this));
        buttons.add(new PTB(3,1, 0, pane, this));
        for(int i = 1; i < 17; i++) {
            buttons.add(new PTB(-1, 0, i, pane, this));
        }
        buttons.add(new PTB(2, 0, 17, pane, this));
        buttons.add(new PTB(3,1, 0, pane, this));
        buttons.add(new PTB(4,1, 1, pane, this));

        for(int i = 2; i < 12; i++) {
            buttons.add(new PTB(-1, 1, i, pane, this));
        }
        for (int i = 12, j = 5; i < 18; i++, j++) {
            buttons.add(new PTB(j, 1, i, pane, this));
        }

        buttons.add(new PTB(11, 2, 0, pane, this));
        buttons.add(new PTB(12,2, 1, pane, this));
        for(int i = 2; i < 12; i++) {
            buttons.add(new PTB(-1, 2, i, pane, this));
        }
        for (int i = 12, j = 13; i < 18; i++, j++) {
            buttons.add(new PTB(j, 2, i, pane, this));
        }
        for(int i = 0, j = 19, r = 3; i < 18; i++, j++ ) {
            buttons.add(new PTB(j,r,i,pane, this ));
            if (i == 17 && j == 36) {
                i = -1;
                r = 4;
            }
        }
        buttons.add(new PTB(55, 5, 0, pane, this));
        buttons.add(new PTB(56, 5, 1, pane, this));
        for(int i = 3, j = 72; i < 18; i++, j++) {
            buttons.add(new PTB(j, 5, i, pane, this));
        }

        buttons.add(new PTB(87, 6, 0, pane, this));
        buttons.add(new PTB(88, 6, 1, pane, this));
        for(int i = 3, j = 104; i < 18; i++, j++) {
            buttons.add(new PTB(j, 6, i, pane, this));
        }
        for(int i = 0; i < 18; i++) {
            buttons.add(new PTB(-1, 7, i, pane, this));
        }
        for(int i = 0; i < 3; i++) {
            buttons.add(new PTB(-1, 8, i, pane, this));
        }
        for(int i = 3, j = 57; i < 18; i++, j++) {
            buttons.add(new PTB(j, 8, i, pane, this));
        }
        for(int i = 0; i < 3; i++) {
            buttons.add(new PTB(-1, 9, i, pane, this));
        }
        for(int i = 3, j = 89; i < 18; i++, j++) {
            buttons.add(new PTB(j, 9, i, pane, this));
        }

        return pane;
    }
}
