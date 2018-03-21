package GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import structures.enums.Elem;
import structures.enums.Type;

import javax.lang.model.element.Element;
import java.util.ArrayList;

/**
 * Displays the periodic table of the elements with the ability to click on any
 * element to select it, as well as scroll-over highlighting.
 * @author  Sarah Larkin
 * CS3141, Spring 2018, Team ATOM
 * Date Last Modified: March 1, 2018
 */
public class PeriodicTableView extends Stage {

    private ArrayList<Button> buttonList = new ArrayList<Button>();
    private Text currElem;

    public PeriodicTableView() {
        Group group = new Group();
        GridPane pane = new GridPane();
        makeTable(pane);
        pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                for(Button b: buttonList) {
                    ((PeriodicTableButton)b).normalize();
                    ((PeriodicTableButton)b).setHov(false);
                    ((PeriodicTableButton)b).setSelected(false);
                }
            }
        });
        group.getChildren().add(pane);
        Text lanthanides = new Text(145, 620, "Lanthanides");
        group.getChildren().add(lanthanides);
        Line line1 = new Line(122,440, 142, 680 );
        Line line1h = new Line(142, 680, 178, 680);
        Text actinides = new Text(70, 760, "Actinides");
        Line line2 = new Line(122, 520, 142, 760);
        Line line2h = new Line(142, 760, 178, 760);
        group.getChildren().addAll(line1, line1h, actinides, line2, line2h);
        Rectangle display = new Rectangle(250, 20, 200, 200);
        display.setFill(Color.rgb(225, 255, 195));
        Elem hydrogen = Elem.get(1);
        String origElem = hydrogen.getNum() +  "\n" + hydrogen.getSymbol() + "\n" + hydrogen.getName() + "\n" + hydrogen.getAtomicMass();
        currElem = new Text(260, 75, origElem);
        // number, symbol, name mass electron config top right
        currElem.setFill(Color.rgb(0, 0, 0));
        currElem.setFont(new Font(28));
        group.getChildren().add(display);
        group.getChildren().add(currElem);
        Scene s = new Scene(group, 1080, 800);
        setScene(s);
        setTitle("The Periodic Table of the Elements");
        show();
    }

    private boolean hover = false;
    private boolean selected = false;

    Button [][] table = new Button [10][18];

    /**
     * Fills in the full table of elements in a grid pane.
     * @param pane
     */
    private void makeTable(GridPane pane) {
        initializeGrid();
        addElementsToGrid();
        displayGrid(pane);
    }

    /**
     * Initializes all the buttons in the grid as placeholders.
     */
    private void initializeGrid() {
        for (int i = 0, j = 0; (i * j) < 154; i++) {
            if ((i > 0) && (i % 18 == 0)){
                i = 0;
                j++;
            }
            Button b = new PeriodicTableButton("null", Type.NONE, null);
            b.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    for (Button button : buttonList) {
                        ((PeriodicTableButton) button).setSelected(false);
                        ((PeriodicTableButton) button).setHov(false);
                        ((PeriodicTableButton) button).normalize();
                    }
                }
            });
            table[j][i] = b;
        }
    }

    /**
     * Adds all the elements to the grid by series and period.
     */
    private void addElementsToGrid() {
        int act = 3;
        int lan = 3;
        for(int i = 1; i < 119; i++) {
            String s = String.format("%d\n%s\n%-5.2f", Elem.get(i).getNum(), Elem.get(i).getSymbol(), Elem.get(i).getAtomicMass());
            PeriodicTableButton b = new PeriodicTableButton(s, Elem.get(i).getType(), Elem.get(i));

            buttonList.add(b);
            highlightElem(b);
            selectElem(b);

            int  row = Elem.get(i).getPeriod().getInt() - 1;
            int col = Elem.get(i).getGroup().getInt() - 1;

            if ( col < 18) {
                table[row][col] = b;
            } else if (col == 18) {
                table[9][act] = b;
                act++;
            } else if (col == 19) {
                table[8][lan] = b;
                lan++;
            }
        }
    }

    /**
     * Adds all the elements to a visible grid pane, setting their button sizes.
     * @param pane the pane to receive the elements.
     */
    private void displayGrid(GridPane pane) {
        for (int i = 0, j = 0; (i * j) <= 17*9; i++) {

            if (i > 0 && i % 18 == 0) {
                i = 0;
                j++;
            }
            Button b = table[j][i];
            b.setPrefSize(60, 80);
            pane.add(b, i, j);
        }
    }


    /**
     * Adds event listening utility for highlighting and un-highlighting each
     * element by attaching an event listener
     * @param b the button to be highlighted
     */
    private void highlightElem(PeriodicTableButton b) {
        b.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(!anySelected()) {
                    for (Button blue: buttonList) {
                        ((PeriodicTableButton)blue).setHov(false);
                        ((PeriodicTableButton)blue).normalize();
                    }
                    b.setHov(true);
                    b.highlight();
                    Elem elem = b.getElement();
                    String elemInfo = String.format("%d\n%s\n%s\n%f\n", elem.getNum(), elem.getSymbol(), elem.getName(), elem.getAtomicMass());
                    currElem.setText(elemInfo);
                }
            }
        });
        b.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                if(!anySelected()) {
                    for (Button b: buttonList) {
                        ((PeriodicTableButton)b).setHov(false);
                        ((PeriodicTableButton) b).normalize();
                    }
                }
            }
        });
    }

    /**
     * Provides utility of selecting only one button at a time by attaching an
     * event listener.
     * @param b button to be selected
     */
    private void selectElem(PeriodicTableButton b) {
        b.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                if (!b.getSelected()) {
                    for(Button button: buttonList) {
                        ((PeriodicTableButton)button).setHov(false);
                        ((PeriodicTableButton)button).setSelected(false);
                        ((PeriodicTableButton)button).normalize();
                    }
                    b.setSelected(true);
                    b.choose();
                } else {
                    b.setSelected(false);
                    b.normalize();
                }
            }
        });
    }

    /**
     * Checks if any buttons are currently selected
     * @return whether any buttons are selected.
     */
    private boolean anySelected() {
        for(Button b: buttonList) {
            if (((PeriodicTableButton)b).getSelected()) {
                return true;
            }
        }
        return false;
    }




}
