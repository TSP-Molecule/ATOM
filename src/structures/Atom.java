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
    private int valenceShell;     // Number of electrons currently in valence shell (includes electrons currently being shared from other atoms)
    private int bondedElectrons;  // Number of electrons in the atom already being bonded
    private final int maxBonds;   // Maximum number of covalent attachedBonds this atom can form

    /**
     * Create an Atom with only an element, no attached attachedBonds.
     *
     * @param element The Atom's element.
     */
    public Atom(Elem element) {
        this.element = element;
        this.attachedBonds = new ArrayList<>();
        this.maxBonds = 8 - element.getGroup().getValenceE();
        this.valenceShell = element.getGroup().getValenceE();
    }

    /**
     * Attempts to add bond to list of attached bonds.
     *
     * @param bond Bond to attach
     * @return     Bond attached if successful, null if not.
     */
    public Bond addBond(Bond bond) {
        if ( getAttachedBonds().contains(bond)) return bond;

        if ( getAvailableElectrons() >= 2 ) {
            this.getAttachedBonds().add(bond);
            bondedElectrons += 2;
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

    /**
     * @return number of available electrons
     */
    public int getAvailableElectrons() {
        int ret = valenceShell;
        for (Bond b : getAttachedBonds()) {
            ret -= b.getOrder().getNum();
        }

        // check for program error.
        if (ret != (valenceShell - bondedElectrons)) try {
            throw new Exception();
        } catch (Exception e) {
            System.err.println("Bonds are desynced!");
            e.printStackTrace();
        }

        return ret;
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

    @Override
    public String toString() {
        return "\nAtom{" +
                "\n  element=" + element.getName() +
                ",\n  attachedBonds=" + attachedBonds +
                '}';
    }
}
