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
    private BondOrder order;
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
        System.out.println("bonded " + one.getElement().getName() + " to " + two.getElement().getName()
                    + ". Remaining " + one.getAvailableElectrons() + ":" + one.isBondable() + ", " + two.getAvailableElectrons() + ":" + two.isBondable()
                    + "    Equal: " + one.equals(two));
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
        StringBuilder str = new StringBuilder();
        str.append("<bond: ");
        for( Atom a: getAtoms() ) {
            str.append(a.getElement().getName()+ " ");
        }
        str.append(", " + order + ">");
        return str.toString();
    }


    public double getBondingAngle() {
        return bondingAngle;
    }

    public void setOrder(BondOrder order) {
        this.order = order;
    }

    public BondOrder increaseOrder() {
        if (order.ordinal() < BondOrder.values().length - 1) {
            order = BondOrder.values()[order.ordinal() + 1];
            getAtoms().get(0).incrementAttachedElectrons();
            getAtoms().get(1).incrementAttachedElectrons();
            System.out.println("INCREASED ORDER OF " + this + " " + this.getAtoms().get(0).getAvailableElectrons() + ", " + this.getAtoms().get(1).getAvailableElectrons());
        }
        return order;
    }

    public void setBondingAngle(double bondingAngle) {
        this.bondingAngle = bondingAngle;
    }
}
