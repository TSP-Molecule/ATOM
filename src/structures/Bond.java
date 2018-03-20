package structures;
import structures.enums.BondOrder;

import java.util.ArrayList;
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

    private final Atom atomOne;
    private final Atom atomTwo;

    public Bond(BondOrder order, Atom atomOne, Atom atomTwo) {
        //TODO: Proper checking that the bond can be formed.
        this.order = order;
        this.atomOne = atomOne;
        this.atomTwo = atomTwo;

        //Add newly created bond to bond lists of both atoms.
        atomOne.getAttachedBonds().add(this); //TODO: Method to handle bond addition in Atoms.
        atomTwo.getAttachedBonds().add(this);
    }

    public BondOrder getOrder() {
        return order;
    }

    public ArrayList<Atom> getBondedAtoms() {
        ArrayList<Atom> bondedAtoms = new ArrayList<>(2);
        bondedAtoms.set(0, atomOne);
        bondedAtoms.set(1, atomTwo);

        return bondedAtoms;
    }

    public Atom getAtomOne() {
        return atomOne;
    }

    public Atom getAtomTwo() {
        return atomTwo;
    }

    @Override
    public String toString() {
        return "\n    Bond{ \n      atoms: "
                + atomOne.getElement().getName() + ", " + atomTwo.getElement().getName()
                +  ", \n      order: " + order + "}\n";
    }
}
