package structures;

import structures.enums.BondOrder;

import java.util.ArrayList;
import java.util.Set;

/**
 * Bond structure. Connects two atoms.
 * @author  Emily Anible
 * CS3141, Spring 2018, Team ATOM
 */
public class Bond {

    /**
     * BondOrder - 1 for single bonds, 2 for double, 3 for triple.
     */
    private final BondOrder order;

    private final Atom atomOne;
    private final Atom atomTwo;
    private double bondingAngle;

    public Bond(BondOrder order, Atom atomOne, Atom atomTwo) {
        //TODO: Proper checking that the bond can be formed.
        this.order = order;
        this.atomOne = atomOne;
        this.atomTwo = atomTwo;

        //Add newly created bond to bond lists of both atoms.
        atomOne.getAttachedBonds().add(this); //TODO: Method to handle bond addition in Atoms.
        atomTwo.getAttachedBonds().add(this);
    }

    /**
     * @return order of bond
     */
    public BondOrder getOrder() {
        return order;
    }

    /**
     * @return atoms attached to bond
     */
    public ArrayList<Atom> getBondedAtoms() {
        ArrayList<Atom> bondedAtoms = new ArrayList<>(2);
        bondedAtoms.set(0, atomOne);
        bondedAtoms.set(1, atomTwo);

        return bondedAtoms;
    }

    //TODO: We don't care what atom one or atom two is... will just be confusing in the future.
    public Atom getAtomOne() {
        return atomOne;
    }

    public Atom getAtomTwo() {
        return atomTwo;
    }

    /**
     * @return bonding angle of bond.
     * //TODO... maybe integrate this into molecule instead.
     */
    public double getBondingAngle() {
        return bondingAngle;
    }

    /**
     * Sets bonding angle of bond
     * @param angle angle to set
     */ //TODO... Maybe integrate this into molecule instead
    public void setBondingAngle(double angle) {
        bondingAngle = angle;
    }

    @Override
    public String toString() {
        return "\n    Bond{ \n      atoms: "
                + atomOne.getElement().getName() + ", " + atomTwo.getElement().getName()
                + ", \n      order: " + order + "}\n";
    }
}
