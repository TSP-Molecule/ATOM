package structures;

/**
 * structures.Bond , Chemical structures.Bond -
 *      @author Emily Anible
 *      structures.Bond Object.
 *          A bond forms between two atoms (head, tail) and has a couple of properties.
 *
 *      //TODO: What happens when we create a structures.Bond?
 *      //TODO: What happens when we break  a structures.Bond?
 */
public class Bond {

    /**
     * Order -- 1, 2, 3 for Single, Double, or Triple bond, respectively.
     */
    private final int order;


    /**
     * Atom at the "head" of the bond.
     */
    private AtomObject headAtom;


    /**
     * Atom at the "tail" of the bond.
     */
    private AtomObject tailAtom;

    public Bond(int order, AtomObject headAtom, AtomObject tailAtom) {
        this.order = order;
        this.headAtom = headAtom;
        this.tailAtom = tailAtom;
    }

    public AtomObject getTailAtom() {
        return tailAtom;
    }

    public void setTailAtom(AtomObject tailAtom) {
        this.tailAtom = tailAtom;
    }

    public AtomObject getHeadAtom() {
        return headAtom;
    }

    public void setHeadAtom(AtomObject headAtom) {
        this.headAtom = headAtom;
    }

    public int getOrder() {
        return order;
    }
}
