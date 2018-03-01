package GUI;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import structures.enums.Elem;

import java.util.ArrayList;

public class TrialPeriodic2 extends Stage {

    private ArrayList<Button> buttonList = new ArrayList<Button>();

    public TrialPeriodic2() {
        GridPane pane = new GridPane();
        drawTable(pane);
        Scene s = new Scene(pane, getWidth(), getHeight());
        setScene(s);
        setTitle("The periodic table II");
        show();
    }

    private boolean hover = false;
    private boolean selected = false;


    private void drawTable(GridPane pane) {

        for(int i = 1; i < 119; i++) {
            String s = String.format("%d\n%s\n%.2f", Elem.get(i).getNum(), Elem.get(i).getSymbol(), Elem.get(i).getAtomicMass());
            Button b = new PBJ(s);

            buttonList.add(b);
            highlightElem((PBJ) b);
            openInfo((PBJ) b);
            b.setMinWidth(pane.getWidth()/18);
            b.setMinHeight(pane.getHeight()/9);
            int row = getRow(i);// = Elem.get(i).getSeries().getInt()-1;
            // row = Elem.get(i).getSeries().getInt()-1;
            int col = Elem.get(i).getGroup().getInt() - 1;
            int act = 3;
            int lan = 3;
            if (col < 19) {
                pane.add(b, col, row);
            } else if (col == 19) {
                pane.add(b, lan, row);
                lan++;
            } else if (col == 20) {
                pane.add(b, act, row);
                act++;
            }

        }
    }

    private void highlightElem(PBJ b) {
        b.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                b.setHov(true);
                b.highlight();
            }
        });
    }

    private void openInfo(PBJ b) {
        b.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!b.getSelected()) {
                    for(Button button: buttonList) {
                        ((PBJ)button).setHov(false);
                        ((PBJ)button).setSelected(false);
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


    /*
    Temp util method for testing
     */
    private int getRow(int i) {
        int row = 0;
        if (i < 2) {
            row = 0;
        } else if(i <= 10) {
            row = 1;
        } else if (i <= 18) {
            row = 2;
        } else if (i <= 36) {
            row = 3;
        } else if (i <= 54) {
            row = 4;
        } else if ((i > 54 && i < 57) || (i >=72 && i <= 86)){
            row = 5;
        } else if (( i > 86 && i < 89)|| (i >=104 && i <= 118)) {
            row = 6;
        } else if (57 <= i && i <=71) {
            row = 8;
        } else if (89 <= i && i <= 103) {
            row = 9;
        }
        return row;
    }





}
