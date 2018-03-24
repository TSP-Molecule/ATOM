package structures.enums;

/**
 * Period of an element. Used for placement in periodic table.
 * @author  Emily Anible
 * CS3141, Spring 2018, Team ATOM
 */
public enum Period {
    Period1 (1),
    Period2 (2),
    Period3 (3),
    Period4 (4),
    Period5 (5),
    Period6 (6),
    Period7 (7),
    Period8 (8);


    private final int num;


    Period (int num) {
        this.num = num;
    }

    /**
     * @return Integer value of period
     */
    public int getInt() {
        return num;
    }

}