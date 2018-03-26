package structures;

import structures.enums.Elem;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.ArrayList;

/**
 * @author  Emily Anible
 * CS3141, Spring 2018, Team ATOM
 *
 * Atom. Each instance of this object is a "physical" atom, with attributes
 * - Element - What element is this atom?
 * - Bonds    - What attachedBonds object are bonded here?
 * - (maybe atom.getBonded() to get a list of the atoms it's bonded to)
 */

public class Atom {


    private final Elem element;
    private ArrayList<Bond> attachedBonds;   //Attached bonds
    private ArrayList<Atom> attachedAtoms;
    private int valenceShell;       // Number of electrons currently in valence shell (includes electrons currently being shared from other atoms)
//    private int bondedElectrons;  // Number of electrons in the atom already being bonded
    private int availableElectrons; //Number of free electrons not yet bonded.
    /**
     * Create an Atom with only an element, no attached attachedBonds.
     *
     * @param element The Atom's element.
     */
    public Atom(Elem element) {
        this.element = element;
        this.attachedBonds = new ArrayList<>();
        this.attachedAtoms = new ArrayList<>();
        this.valenceShell = element.getGroup().getValenceE();
        if (element.getNum() > 2) {
            this.availableElectrons = 8 - valenceShell;
        } else {
            this.availableElectrons = 2 - valenceShell;
        } //Massive oversimplification
    }


    /**
     * Attempts to add bond to list of attached bonds.
     *
     * @param bond Bond to attach
     * @return     Bond attached if successful, null if not.
     */
    public Bond addBond(Bond bond) {
        if ( getAttachedBonds().contains(bond)) return bond;

        if ( isBondable() ) {
            this.getAttachedBonds().add(bond);
            incrementAttachedElectrons();
            attachedAtoms.add(bond.getAtoms().get(0).equals(this) ? bond.getAtoms().get(0) : bond.getAtoms().get(1));
            return bond;
        } else {
            return null;
        }
    }

    /**
     * @param  bond the bond to check for
     * @return True if the bond is attached to this atom.
     */
    public boolean containsBond(Bond bond) {
        return getAttachedBonds().contains(bond);
    }

    public boolean isBondable() {
        return (availableElectrons >= 1);
    }

    /**
     * @return Element associated to Atom.
     */
    public Elem getElement() {
        return element;
    }

    /**
     * @return bonds attached to atom
     */
    public ArrayList<Bond> getAttachedBonds() {
        return attachedBonds;
    }

    /**
     * @return number of available electrons
     */
    public int getAvailableElectrons() {
        return availableElectrons;
    }

    /**
     * @return list of attached atoms
     */
    public ArrayList<Atom> getAttachedAtoms() {
//        ArrayList<Atom> ret = new ArrayList<>();
//        for (Bond b: getAttachedBonds()) {
//            Atom one = b.getAtoms().get(0);
//            Atom two = b.getAtoms().get(1);
//            if (one.equals(this)) ret.add(one);
//            if (two.equals(this)) ret.add(two);
//        }
//
//        return ret;
        return attachedAtoms;
    }

    public void incrementAttachedElectrons() {
        valenceShell += 1;
        availableElectrons -= 1;
    }

    @Override
    public String toString() {
        return "\n  Atom[" +element.getName() + ", bonds: " + attachedBonds.size() + ": " + attachedBonds + "]";
    }
}
