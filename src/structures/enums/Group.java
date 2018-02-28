package structures.enums;

public enum Group {
    Group1      (1  ,"IA"),
    Group2      (2  ,"IIA"),
    Group3      (3  ,"IIIB"),
    Group4      (4  ,"IVB"),
    Group5      (5  ,"VB"),
    Group6      (6  ,"VIB"),
    Group7      (7  ,"VIIB"),
    Group8      (8  ,"VIIIB"),
    Group9      (9  ,"VIIIB"),
    Group10     (10  ,"VIIIB"),
    Group11     (11  ,"IB"),
    Group12     (12  ,"IIB"),
    Group13     (13  ,"IIIA"),
    Group14     (14  ,"IVA"),
    Group15     (15  ,"VA"),
    Group16     (16  ,"VIA"),
    Group17     (17  ,"VIIA"),
    Group18     (18  ,"VIIIA");

    private final int num;
    private final String group;

    Group(int num, String group) {
        this.num = num;
        this.group = group;
    }

    /**
     * @param group The group name
     * @return      Group associated with the group name
     */
    public static Group getByName(String group) {
        for( Group g: Group.values()) {
            if (g.toString().equals(group)) return g;
        }
        return null;
    }

    /**
     * @param num   Group Number
     * @return      Group associated with the group number
     */
    public static Group getByNum(int num) {
        for ( Group g: Group.values()) {
            if(g.getInt() == num) return g;
        }
        return null;
    }

    public int getInt() {
        return num;
    }

    public String getGroup() {
        return group;
    }
}
