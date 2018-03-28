package structures;

import structures.enums.BondOrder;
import structures.enums.Elem;
import structures.enums.Geometry;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ArrayList;

/**
 * @author Emily Anible
 * CS3141, Spring 2018, Team ATOM
 * <p>
 * Atom. Each instance of this object is a "physical" atom.
 * It's best to think of an Atom as a vertex in a graph, with more properties.
 */

public class Atom implements Serializable {


    private final Elem element;
    private ArrayList<Bond> attachedBonds;   //Attached bonds
    private ArrayList<Atom> attachedAtoms;
    private int valenceShell;       // Number of electrons currently in valence shell (includes electrons currently being shared from other atoms)
    private int availableElectrons; //Number of free electrons not yet bonded.
    private Geometry geometry;      //Molecular Geometry type of this atom within a molecule. Not set until in a molecule.
    private int lonePairs;          //Valence shell - (# of bonds)

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
        } //Massive oversimplification
    }


    /**
     * Attempts to add bond to list of attached bonds.
     *
     * @param bond Bond to attach
     * @return Bond attached if successful, null if not.
     */
    public Bond addBond(Bond bond) {
        if (getAttachedBonds().contains(bond)) return bond;

        if (isBondable()) {
            this.getAttachedBonds().add(bond);
            incrementAttachedElectrons();
            attachedAtoms.add(bond.getAtoms().get(0).equals(this) ? bond.getAtoms().get(0) : bond.getAtoms().get(1));
            return bond;
        } else {
            return null;
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
