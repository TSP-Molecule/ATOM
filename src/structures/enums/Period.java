package structures.enums;

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
    public int getInt() {
        return num;
    }

}