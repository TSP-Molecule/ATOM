package structures.enums;

/**
 *      @author Emily Anible
 *      CS3141, Spring 2018, Team ATOM
 *
 *      Enumeration for the state of an Element. Stores the state that
 *      the element is naturally found in, set in Elem.java.
 */
public enum State {
    NULL    (-1),
    SOLID   (0),
    LIQUID  (1),
    GAS     (2);

    /**
     * value of the state (-1 through 2)
     */
    private int state;

    State(int state) throws IllegalArgumentException {
        if (state < -1 || state > 2) throw new IllegalArgumentException();

        this.state = state;
    }
}
