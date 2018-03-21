package structures.enums;


import javafx.scene.paint.Color;

/**
 *  Enum Type
 *      @author Emily Anible
 *      Stores the type of an element. Set in Elem.java.
 */
public enum Type {
    NONE(Color.rgb(195, 195, 195), 0),
    NONMETAL(Color.CHARTREUSE, 1),
    NOBLE_GAS(Color.MISTYROSE, 2),
    ALKALI_METAL(Color.MISTYROSE, 2),
    ALKALINE_EARTH_METAL(Color.MISTYROSE, 2),
    METALLOID(Color.MISTYROSE, 2),
    HALOGEN(Color.MISTYROSE, 2),
    METAL(Color.MISTYROSE, 2),
    TRANSITION_METAL(Color.MISTYROSE, 2),
    POST_TRANSITION_METAL(Color.MISTYROSE, 2),
    ACTINIDE(Color.MISTYROSE, 2),
    LANTHANIDE(Color.MISTYROSE, 2);


    private int ordinal = 0;
    private Color fill = null;

    Type(Color color, int num) {
        fill = color;
        ordinal = num;
    }

    public Color getFill() {
        return fill;
    }

    public int getOrdinal() {
        return ordinal;
    }
}
