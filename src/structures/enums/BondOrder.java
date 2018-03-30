package structures.enums;

import structures.Bond;

/**
 * Enum to clarify what order a bond is (Single, Double, Triple, etc).
 *
 * @author  Emily Anible
 * CS3141, Spring 2018, Team ATOM
 */
public enum BondOrder {
    NONE    (0, ""),
    SINGLE  (1, "-"),
    DOUBLE  (2, "="),
    TRIPLE  (3, "≡");

    private final int num;

    private final String symbol;

    BondOrder(int num, String symbol) {
        this.num = num;
        this.symbol = symbol;
    }

    public int getNum() {
        return num;
    }


    public String getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
