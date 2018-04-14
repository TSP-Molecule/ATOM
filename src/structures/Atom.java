package structures;

import structures.enums.Elem;
import structures.enums.Geometry;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Emily Anible
 * @author CS3141, Spring 2018, Team ATOM
 * <p>
 * Atom. Each instance of this object is a "physical" atom.
 * It's best to think of an Atom as a vertex in a graph, with more properties.
 */

public class Atom implements Serializable {
    /**
     * Element associated with the atom.
     */
    private final Elem element;
    /**
     * ArrayList of Bonds attached to the atom.
     */
    private ArrayList<Bond> attachedBonds;

    /**
     * ArrayList of atoms attached by bonds.
     */
    private ArrayList<Atom> attachedAtoms;

    /**
     * Number of electrons currently in the valence shell of the atom.
     */
    private int valenceShell;

    /**
     * Number of free electrons in the atom.
     */
    private int availableElectrons;

    /**
     * Number of lone pairs in the valence shell of the atom.
     */
    private int lonePairs;

    /**
     * Molecular geometry of the atom within a molecule.
     * <p>
     * Can be null, and will definitely be if not within a Molecule.
     */
    private Geometry geometry;

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
        this.geometry = null; //Null until center atom in a molecule.
        if (element.getNum() > 2) {
            this.availableElectrons = 8 - valenceShell;
        } else {
            this.availableElectrons = 2 - valenceShell;
        } //Massive oversimplification. Way too many edge-cases to consider everything.
    }


    /**
     * Attempts to add bond to list of attached bonds.
     *
     * @param bond Bond to attach
     */
    public void addBond(Bond bond) {
        if (getAttachedBonds().contains(bond)) return;

        if (isBondable()) {
            this.getAttachedBonds().add(bond);
            incrementAttachedElectrons();
            attachedAtoms.add(bond.getAtoms().get(1).equals(this) ? bond.getAtoms().get(0) : bond.getAtoms().get(1));
        }
    }

    /**
     * @param bond the bond to check for
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
        return attachedAtoms;
    }

    /**
     * Increment counts for electron stats
     */
    public void incrementAttachedElectrons() {
        valenceShell += 1;
        availableElectrons -= 1;
        updateLonePairs();
    }

    /**
     * @return number of lone pairs on the atom.
     */
    public int updateLonePairs() {
        int bondCount = 0;
        for (Bond b : attachedBonds) {
            switch (b.getOrder()) {
                case SINGLE:
                    bondCount += 2;
                    break;
                case DOUBLE:
                    bondCount += 4;
                    break;
                case TRIPLE:
                    bondCount += 6;
                    break;
            }
        }
        lonePairs = (valenceShell - bondCount) / 2;
        return lonePairs;
    }

    @Override
    public String toString() {
        return element.getName() + ": " + attachedBonds;
    }

    /**
     * @return Electron geometry of the atom. Null unless center atom in a molecule.
     */
    public Geometry getGeometry() {
        return geometry;
    }


    /**
     * @return Number of Lone Pairs in Atom.
     */
    public int getLonePairs() {
        return lonePairs;
    }

    /**
     * @param geometry Geometry of Atom in context of molecule
     */
    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }
}
