package structures;
import structures.enums.BondOrder;

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
     *  BondOrder - 1 for single bonds, 2 for double, 3 for triple.
     */
    private final BondOrder order;

    /**
     *  Bond has an unordered set of atoms its attached to
     *  //TODO: Logic or data structure to limit this to an unordered pair.
     */
    private Set<Atom> bondedAtoms;

    public Bond(BondOrder order, Atom tailAtom, Set<Atom> bondedAtoms) {
        this.order = order;
        this.bondedAtoms = bondedAtoms;
    }

    public BondOrder getOrder() {
        return order;
    }

    public Set<Atom> getBondedAtoms() {
        return bondedAtoms;
    }
}
