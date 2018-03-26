package structures;

import structures.enums.BondOrder;

import java.util.ArrayList;

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
    private double bondingAngle;

    /**
     * Atoms that the bond attaches together.
     * NO MORE THAN 2 ALLOWED.
     */
    private final ArrayList<Atom> atoms = new ArrayList<>();

    public Bond(Atom one, Atom two) {
        this(one, two, BondOrder.SINGLE); //TODO: Logic for automatically determining order, if possible.
    }

    public Bond(Atom one, Atom two, BondOrder order){
        atoms.add(one);
        atoms.add(two);
        this.order = order;

        one.addBond(this);
        two.addBond(this);
    }

    /**
     * @return order of bond
     */
    public BondOrder getOrder() {
        return order;
    }

    /**
     * @return attached atoms
     */
    public ArrayList<Atom> getAtoms() {
        return atoms;
    }

    @Override
    public String toString() {
        return "<bond: " + atoms + ">";
    }


    public double getBondingAngle() {
        return bondingAngle;
    }

    public void setBondingAngle(double bondingAngle) {
        this.bondingAngle = bondingAngle;
    }
}
