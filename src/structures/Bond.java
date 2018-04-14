package structures;

import structures.enums.BondOrder;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Bond structure.
 * It's best to think of a bond as an edge in a graph, with more properties.
 *
 * @author Emily Anible
 * CS3141, Spring 2018, Team ATOM
 */
public class Bond implements Serializable {

    /**
     * BondOrder - 1 for single bonds, 2 for double, 3 for triple.
     */
    private BondOrder order;
    private double bondingAngle;

    /**
     * Atoms that the bond attaches together.
     * NO MORE THAN 2 ALLOWED.
     */
    private final ArrayList<Atom> atoms = new ArrayList<>();

    public Bond(Atom one, Atom two) {
        this(one, two, BondOrder.SINGLE);
    }

    public Bond(Atom one, Atom two, BondOrder order) {
        atoms.add(one);
        atoms.add(two);
        this.order = order;

        one.addBond(this);
        two.addBond(this);
//        System.out.println("bonded " + one.getElement().getName() + " to " + two.getElement().getName()
//                    + ". Remaining " + one.getAvailableElectrons() + ":" + one.isBondable() + ", " + two.getAvailableElectrons() + ":" + two.isBondable()
//                    + "    Equal: " + one.equals(two));
    }


    /**
     * Used to increment the Bond's BondOrder if possible.
     * @return new order of Bond
     */
    public BondOrder increaseOrder() {
        if (order.ordinal() < BondOrder.values().length - 1) {
            order = BondOrder.values()[order.ordinal() + 1];
            getAtoms().get(0).incrementAttachedElectrons();
            getAtoms().get(1).incrementAttachedElectrons();
            //System.out.println("INCREASED ORDER OF " + this + " " + this.getAtoms().get(0).getAvailableElectrons() + ", " + this.getAtoms().get(1).getAvailableElectrons());
        }
        return order;
    }

    /**
     * @param bond bond with which to check equality
     * @return true if bonds are equal, false otherwise.
     */
    public boolean equals(Bond bond) {
        boolean oneOne = getAtoms().get(1).equals(bond.getAtoms().get(1));
        boolean oneTwo = getAtoms().get(1).equals(bond.getAtoms().get(2));
        boolean twoOne = getAtoms().get(2).equals(bond.getAtoms().get(1));
        boolean twoTwo = getAtoms().get(2).equals(bond.getAtoms().get(2));
        if (oneOne && twoTwo) {
            return true;
        }
        if (oneTwo && twoOne) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return getAtoms().get(0).getElement().getSymbol() + getOrder() + getAtoms().get(1).getElement().getSymbol();
    }

    /**
     * @param bondingAngle Bonding Angle desired for the bond. Currently unused.
     */
    public void setBondingAngle(double bondingAngle) {
        this.bondingAngle = bondingAngle;
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


    /**
     * @return bondingAngle
     */
    public double getBondingAngle() {
        return bondingAngle;
    }

    /**
     * @param order Order of the Bond
     */
    public void setOrder(BondOrder order) {
        this.order = order;
    }

}
