package GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Translate;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import structures.enums.Elem;
import structures.enums.Type;
import web.WebService;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Displays the periodic table of the elements with the ability to click on any
 * element to select it and open a window with further information.  It also provides scroll-over highlighting with the
 * large rectangle at the top displaying information on the current element.
 *
 * @author Sarah Larkin
 * CS3141, Spring 2018, Team ATOM
 * Date Last Modified: March 25, 2018
 */
public class PeriodicTableView extends Stage {

    private ArrayList<Button> buttonList = new ArrayList<Button>();
    private Text currElem;
    private Color plainCols[] = {
            Color.rgb(188, 255, 128, 1), Color.rgb(240, 128, 128, 1),
            Color.rgb(255, 209, 128, 1), Color.rgb(166, 160, 67, 1),
            Color.rgb(94, 224, 255, 1), Color.rgb(134, 252, 110, 1),
            Color.rgb(215, 184, 255, 1), Color.rgb(255, 95, 168, 1),
            Color.rgb(230, 180, 180, 1), Color.rgb(195, 195, 195, 1),
            Color.rgb(255, 185, 105, 1)
    };

    public PeriodicTableView() {
        Group group = new Group();
        GridPane pane = new GridPane();
        makeTable(pane);
        pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                for (Button b : buttonList) {
                    ((PeriodicTableButton) b).normalize();
                    ((PeriodicTableButton) b).setHov(false);
                    ((PeriodicTableButton) b).setSelected(false);
                }
            }
        });
        group.getChildren().add(pane);

        // Display the text indicating the lanthanides and actinides
        Text lanthanides = new Text(145, 620, "Lanthanides");
        group.getChildren().add(lanthanides);
        Line line1 = new Line(122, 440, 142, 680);
        Line line1h = new Line(142, 680, 178, 680);
        Text actinides = new Text(70, 760, "Actinides");
        Line line2 = new Line(122, 520, 142, 760);
        Line line2h = new Line(142, 760, 178, 760);
        group.getChildren().addAll(line1, line1h, actinides, line2, line2h);

        // Display the current element - defaults to hydrogen when first opened
        Rectangle display = new Rectangle(250, 20, 200, 200);
        display.setFill(Color.rgb(225, 255, 195));
        Elem hydrogen = Elem.get(1);
        String origElem = hydrogen.getNum() + "\n" + hydrogen.getSymbol() + "\n" + hydrogen.getName() + "\n" + hydrogen.getAtomicMass();
        currElem = new Text(260, 75, origElem);

        currElem.setFill(Color.rgb(0, 0, 0));
        currElem.setFont(new Font(28));

        // Display the key
        for (int i = 0; i < 11; i++) {
            Rectangle rectangle = new Rectangle(500, 20 + i * 17, 180, 17);
            rectangle.setFill(plainCols[i]);
            group.getChildren().add(rectangle);
            String s = "";
            switch (i + 1) {
                case 1:
                    s = "NONMETAL";
                    break;
                case 2:
                    s = "NOBLE_GAS";
                    break;
                case 3:
                    s = "ALKALI_METAL";
                    break;
                case 4:
                    s = "ALKALINE_EARTH_METAL";
                    break;
                case 5:
                    s = "METALLOID";
                    break;
                case 6:
                    s = "HALOGEN";
                    break;
                case 7:
                    s = "METAL";
                    break;
                case 8:
                    s = "TRANSITION_METAL";
                    break;
                case 9:
                    s = "POST_TRANSITION_METAL";
                    break;
                case 10:
                    s = "ACTINIDE";
                    break;
                case 11:
                    s = "LANTHANIDE";
                    break;
            }
            Text text = new Text(510, 34 + i * 17, s);
            text.setFont(new Font(12));
            group.getChildren().add(text);

        }

        group.getChildren().add(display);
        group.getChildren().add(currElem);
        Scene s = new Scene(group, 1080, 800);
        setScene(s);
        setTitle("The Periodic Table of the Elements");

        // show();
    }

    Button[][] table = new Button[10][18];

    /**
     * Fills in the full table of elements in a grid pane.
     *
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
            if ((i > 0) && (i % 18 == 0)) {
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
        for (int i = 1; i < 119; i++) {
            String s = String.format("%d\n%s\n%-5.2f", Elem.get(i).getNum(), Elem.get(i).getSymbol(), Elem.get(i).getAtomicMass());
            PeriodicTableButton b = new PeriodicTableButton(s, Elem.get(i).getType(), Elem.get(i));

            buttonList.add(b);
            highlightElem(b);
            selectElem(b);

            int row = Elem.get(i).getPeriod().getInt() - 1;
            int col = Elem.get(i).getGroup().getInt() - 1;

            if (col < 18) {
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
     *
     * @param pane the pane to receive the elements.
     */
    private void displayGrid(GridPane pane) {
        for (int i = 0, j = 0; (i * j) <= 17 * 9; i++) {

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
     *
     * @param b the button to be highlighted
     */
    private void highlightElem(PeriodicTableButton b) {
        b.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!anySelected()) {
                    for (Button blue : buttonList) {
                        ((PeriodicTableButton) blue).setHov(false);
                        ((PeriodicTableButton) blue).normalize();
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

                if (!anySelected()) {
                    for (Button b : buttonList) {
                        ((PeriodicTableButton) b).setHov(false);
                        ((PeriodicTableButton) b).normalize();
                    }
                }
            }
        });
    }

    private Stage infoStage = new Stage();

    /**
     * Provides utility of selecting only one button at a time by attaching an
     * event listener.
     *
     * @param b button to be selected
     */
    private void selectElem(PeriodicTableButton b) {
        b.setOnMouseClicked(event -> {
            if (!b.getSelected()) {
                for (Button button : buttonList) {
                    ((PeriodicTableButton) button).setHov(false);
                    ((PeriodicTableButton) button).setSelected(false);
                    ((PeriodicTableButton) button).normalize();
                }
                b.setSelected(true);
                b.choose();
            } else {
                b.setSelected(false);
                b.normalize();
            }

            if (event.getButton() == MouseButton.PRIMARY) {
                System.out.println("LEFT" + b.getElement());

                Group bohr = new AtomView(b.getElement().getNum());
                bohr.prefWidth(500);

                AtomInfo info = null;
                try {
                    info = new AtomInfo(b.getElement());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Scene sceneBohr;
                //This ain't a Scene, it's a god damn
                GridPane grid = new GridPane();

                sceneBohr = new Scene(grid, 1000, 600);

                // Grid Properties
                ColumnConstraints column = new ColumnConstraints();
                column.setPercentWidth(50);
                grid.getColumnConstraints().add(column);
//                grid.gridLinesVisibleProperty().set(true);
                grid.setMinSize( infoStage.getWidth(), infoStage.getHeight() );

                BorderPane bp = new BorderPane();
//                bp.getChildren().add(bohr);
                bp.setCenter(bohr);

                //Add bohr model and information panel to grid.
                grid.add(bp, 0, 0);
                grid.add(info, 1, 0);

                infoStage.setScene(sceneBohr);

                infoStage.setMaxWidth(grid.getWidth());
                infoStage.setMaxHeight(grid.getHeight());
                infoStage.show();

            }
        });
    }

    @Deprecated
    private Group bohrModel(Stage s, Elem elem) {
        Group group = new Group();
//        for (int i = 0; i < 360; i += 60) {
//            double x = 40 * Math.cos(i);
//            double y = 40 * Math.sin(i);
//            System.out.println("x:  " + x + "  y: " + y);
//            Ellipse e = new Ellipse(x, y, 20, 20);
//            group.getChildren().add(e);
//        }
        int electrons = elem.getNum();

        int[] shells = {2, 10, 18, 36, 54, 86};
        int[] elec = new int[shells.length];
        for (int i = 0; i < shells.length; i++) {
            int rem = electrons - shells[i];
            if (rem >= 0) {
                elec[i] = shells[i];
            } else {
                elec[i] = rem;
            }
        }
        for (int i = 7; i > 1; i--) {
            // System.out.println("blue bayou");
            Ellipse e = new Ellipse(0, 0, i * 50, i * 50);
            e.setStroke(Color.BLACK);
            e.setStrokeWidth(1);
            // e.setFill(Color.GREEN);
            group.getChildren().add(e);
            double d = 360 / shells[i - 2];
            double rad = 15;
            double radi = i * 50;
            System.out.println("Shell " + i + ":  " + elec[i - 2]);
            for (int j = 0; j < elec[i - 2]; j++) {

                double x = radi * Math.cos(d * i);
                double y = radi * Math.sin(d * i);
                Ellipse electron = new Ellipse(x, y, rad, rad);
                electron.setFill(Color.TURQUOISE);
                group.getChildren().add(electron);
            }
        }

        Text atom = new Text(elem.getSymbol());
        atom.setFont(new Font(18));
        Ellipse cen = new Ellipse(0, 0, 50, 50);
        cen.setFill(elem.getType().getFill());
        group.getChildren().addAll(cen, atom);
        group.getTransforms().add(new Translate(s.getWidth() / 2, s.getHeight() / 2));
        return group;
    }


    /**
     * Checks if any buttons are currently selected
     *
     * @return whether any buttons are selected.
     */
    private boolean anySelected() {
        for (Button b : buttonList) {
            if (((PeriodicTableButton) b).getSelected()) {
                return true;
            }
        }
        return false;
    }


}
