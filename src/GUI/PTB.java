package GUI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import structures.Atominomicon;
//import structures.Element;
import structures.enums.Elem;

import javafx.scene.control.Button;

import java.io.IOException;

public class PTB extends Button {

    private int row;
    private int col;
    private int num;
    private String label;
   // private Element element;
    private Color plain;
    private Color highlighted =  Color.rgb(250, 250, 195);
    private Color selected;
    private boolean chosen;
    private boolean hover;
    Atominomicon atom;

    private static double q = 0.75;

    private static Color plainCols [] = {
            Color.rgb(188, 255, 128, 1), Color.rgb(240, 128, 128, 1), Color.rgb(255, 209, 128, 1), Color.rgb(166, 160, 67, 1),
            Color.rgb(94, 224, 255, 1), Color.rgb(134, 252, 110, 1), Color.rgb(132, 118, 255, 1), Color.rgb(255, 255, 128, 1),
            Color.rgb(255, 114, 247, 1), Color.rgb(166, 67, 160, 1), Color.rgb(195, 195, 195, 1), Color.rgb(120, 120, 120, 1)
    };

    private static Color selectCols [] = {
            Color.rgb(188, 255, 128, q), Color.rgb(240, 128, 128, q), Color.rgb(255, 209, 128, q), Color.rgb(166, 160, 67, q),
            Color.rgb(94, 224, 255, q), Color.rgb(134, 252, 110, q), Color.rgb(132, 118, 255, q), Color.rgb(255, 255, 128, q),
            Color.rgb(255, 114, 247, q), Color.rgb(166, 67, 160, q), Color.rgb(195, 195, 195, q), Color.rgb(120, 120, 120, q)
    };

    private CornerRadii cnorm = new CornerRadii(3);
    private CornerRadii chigh = new CornerRadii(5);
    private Insets inorm = new Insets(5);
    private Insets ihigh = new Insets(0);
    private Background bl;
    private BackgroundFill b;

    private void assignColors(String s) {
        switch(s) {
            case "nonmetal":  plain = plainCols[0]; selected = selectCols[0]; break;
            case "noblegas": plain = plainCols[1]; selected = selectCols[1]; break;
            case "alkalimetal": plain =/* Color.rgb(255, 0, 0); */plainCols[2]; selected = selectCols[2];break;
            case "alkalineearthmetal": plain = plainCols[3]; selected = selectCols[3]; break;
            case "metalloid": plain = plainCols[4]; selected = selectCols[4];break;
            case "halogen": plain = plainCols[5]; selected = selectCols[5];break;
            case "transitionmetal": plain = plainCols[6]; selected = selectCols[6];break;
            case "lanthanoid": plain = plainCols[7]; selected = selectCols[7];break;
            case "actinoid": plain = plainCols[8]; selected = selectCols[8];break;
            case "post-transitionmetal": plain = plainCols[9]; selected = selectCols[9];break;
            default: plain = Color.rgb(195, 195, 195);
                     selected = Color.rgb(195, 195, 195);
                     highlighted = Color.rgb(195, 195, 195);
                     break;
        }

    }

    public PTB (int n, int r, int c, GridPane pane) {
        num = n;
        row = r;
        col = c;
        chosen = false;
        hover = false;
        try {
            atom = new Atominomicon();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //atomic num, atomSymbol, atomicWeight, name
        if (num > 0) {
            label = String.format("%d\n%s\n%f%s", num, Elem.get(num).getSymbol(), Elem.get(num).getAtomicMass(), Elem.get(num).getName());
            setText(label);
        }

        if (num > 0) {
            assignColors(Elem.get(num).getType());
        } else {
            assignColors("");
        }
        this.setBackground(new Background(new BackgroundFill(plain,cnorm, inorm )));
     //   Button button = new Button();
        this.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!chosen) {
                    chosen = true;
                    setBackground(new Background(new BackgroundFill(selected,chigh, ihigh )));
                    //TODO: display window
                    //display new window
                }
            }
        });
        this.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(!hover) {
                    hover = true;
                    setBackground(new Background(new BackgroundFill(highlighted,chigh, ihigh )));
                }
            }

        });
        this.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(hover) {
                    hover = false;
                    setBackground(new Background(new BackgroundFill(plain, cnorm, inorm )));
                }
            }
        });
      //  this.setWidth(100);
      ////  this.setHeight(100);
        setPrefSize(50, 80);
        if (n ==-1) {
            setVisible(false);
        }
        pane.add(this, col, row);
        //element;
    }


}
