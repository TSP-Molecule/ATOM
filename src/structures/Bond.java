package structures;
import java.util.Set;

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
     *  Bond has an unordered set of atoms it's attached to
     *  //TODO: Logic or data structure to limit this to an unordered pair.
     */
    private Set<AtomObject> bondedAtoms;

    public Bond(int order, AtomObject tailAtom, Set<AtomObject> bondedAtoms) {
        this.order = order;
        this.bondedAtoms = bondedAtoms;
    }

    public int getOrder() {
        return order;
    }

    public Set<AtomObject> getBondedAtoms() {
        return bondedAtoms;
    }
}
