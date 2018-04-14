package structures.enums;

/**
 * Enum to clarify what order a bond is (Single, Double, Triple, etc).
 *
 * @author Emily Anible
 * CS3141, Spring 2018, Team ATOM
 */
public enum BondOrder {
    NONE(0, ""),
    SINGLE(1, "-"),
    DOUBLE(2, "="),
    TRIPLE(3, "≡");

    /**
     * Number value of the bond
     */
    private final int num;

    /**
     * Symbol used to represent the bond in ASCII
     */
    private final String symbol;

    BondOrder(int num, String symbol) {
        this.num = num;
        this.symbol = symbol;
    }

    /**
     * @return number associated with BondOrder
     */
    public int getNum() {
        return num;
    }


    /**
     * @return symbol associated with BondOrder
     */
    public String getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
