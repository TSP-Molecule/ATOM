package GUI;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import structures.enums.Elem;
import structures.enums.Type;

/**
 * Wrapper class allows additional properties for the periodic table buttons.
 * @author  Sarah Larkin
 * CS3141, Spring 2018, Team ATOM
 * Date Last Modified: March 1, 2018
 */
public class PeriodicTableButton extends Button {

    private boolean hover = false;
    private boolean selected = false;
    private Elem element = null;

    private static double q = 0.75;// opacity of color

    private static Color plainCols [] = {
            Color.rgb(188, 255, 128, 1), Color.rgb(240, 128, 128, 1), Color.rgb(255, 209, 128, 1), Color.rgb(166, 160, 67, 1),
            Color.rgb(94, 224, 255, 1), Color.rgb(134, 252, 110, 1), Color.rgb(215, 184, 255, 1), Color.rgb(255, 95, 168, 1),// 55 128
            Color.rgb(255, 114, 247, 1), Color.rgb(230, 180, 180, 1), Color.rgb(195, 195, 195, 1), Color.rgb(120, 120, 120, 1),
            Color.rgb(255, 185, 105, 1)
    };

//    private static Color selectCols [] = {
//            Color.rgb(188, 255, 128, q), Color.rgb(240, 128, 128, q), Color.rgb(255, 209, 128, q), Color.rgb(166, 160, 67, q),
//            Color.rgb(94, 224, 255, q), Color.rgb(134, 252, 110, q), Color.rgb(132, 118, 255, q), Color.rgb(255, 55, 128, q),
//            Color.rgb(255, 114, 247, q), Color.rgb(166, 67, 160, q), Color.rgb(195, 195, 195, q), Color.rgb(120, 120, 120, q),
//            Color.rgb(255, 185, 105, q)
//    };

    private CornerRadii cnorm = new CornerRadii(3);// normal corner radius
    private CornerRadii chigh = new CornerRadii(5);// highlighted state corner radius
    private Insets inorm = new Insets(5);// normal insets
    private Insets ihigh = new Insets(0);// highlighted state insets
    private int colorNum = -1; // index to color arrays


    /**
     * Assigns a color scheme to the element button based on the element type.
     * @param type the type of the element
     */
    private void assignColors(Type type) {
        switch(type) {
            case NONMETAL:  colorNum = 0; break;
            case NOBLE_GAS: colorNum = 1; break;
            case ALKALI_METAL: colorNum = 2; break;
            case ALKALINE_EARTH_METAL: colorNum = 3; break;
            case METALLOID: colorNum = 4; break;
            case HALOGEN: colorNum = 5; break;
            case METAL: colorNum = 6; break;
            case TRANSITION_METAL: colorNum = 7; break;
            case POST_TRANSITION_METAL: colorNum = 9; break;
            case ACTINIDE: colorNum = 10; break;
            case LANTHANIDE: colorNum = 12; break;
            default: colorNum = -1;
                break;
        }
    }


    public boolean getHov() {
        return hover;
    }

    public void setHov(boolean newHover) {
        hover = newHover;
    }

    public boolean getSelected() {
        return selected;
    }

    public void setSelected(boolean sel) {
        selected = sel;
    }

    /**
     * Sets the button to its highlighted state.
     */
    public void highlight() {
        setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 215), chigh, ihigh)));
    }

    /**
     * Sets the button to its selected state.
     */
    public void choose() {
        setBackground(new Background(new BackgroundFill(plainCols[colorNum], chigh, ihigh)));
    }

    /**
     * Sets the button to its default state.
     */
    public void normalize() {
        setBackground(new Background(new BackgroundFill(plainCols[colorNum], cnorm, inorm)));
    }


    public PeriodicTableButton(String s, Type t, Elem elem) {
        setText(s);
        element = elem;
        if (s.equalsIgnoreCase("null")) {
            setVisible(false);
        } else {
            assignColors(t);
            normalize();
        }
        hover = false;
        selected = false;
    }

    public Elem getElement() {
        return element;
    }



}
