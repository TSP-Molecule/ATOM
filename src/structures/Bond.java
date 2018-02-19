package structures;
import java.util.Set;

/**
 * Bond , Chemical structures.Bond -
 *      @author Emily Anible
 *          A bond forms between two atoms and has a couple of properties.
 *
 *      //TODO: What happens when we create a Bond?
 *      //TODO: What happens when we break  a Bond?
 */
public class Bond {

    /**
     * Order -- 1, 2, 3 for Single, Double, or Triple bond, respectively.
     */
    private final int order;

    /**
     *  Bond has an unordered set of atoms its attached to
     *  //TODO: Logic or data structure to limit this to an unordered pair.
     */
    private Set<Atom> bondedAtoms;

    public Bond(int order, Atom tailAtom, Set<Atom> bondedAtoms) {
        this.order = order;
        this.bondedAtoms = bondedAtoms;
    }

    public int getOrder() {
        return order;
    }

    public Set<Atom> getBondedAtoms() {
        return bondedAtoms;
    }
}
