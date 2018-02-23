package structures.enums;

public enum BondOrder {
    NONE    (0),
    SINGLE  (1),
    DOUBLE  (2),
    TRIPLE  (3);

    private final int numOfBonds;

    BondOrder(int numOfBonds) {
        this.numOfBonds = numOfBonds;
    }

    public int getNumOfBonds() {
        return numOfBonds;
    }
}
