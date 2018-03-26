package structures.enums;

/**
 * Enum to clarify what order a bond is (Single, Double, Triple, etc).
 * @author  Emily Anible
 * CS3141, Spring 2018, Team ATOM
 */
public enum BondOrder {
    NONE    (0),
    SINGLE  (1),
    DOUBLE  (2),
    TRIPLE  (3);

    private final int num;

    BondOrder(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }
}
