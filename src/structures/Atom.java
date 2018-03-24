package structures;

import structures.enums.Elem;

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
     * Create an Atom with both an element and attached attachedBonds.
     *
     * @param element The Atom's element.
     * @param bonds   ArrayList of attached attachedBonds.
     */
    public Atom(Elem element, ArrayList<Bond> bonds){
        this.element = element;
        this.attachedBonds = bonds;
        this.maxBonds = 8 - element.getGroup().getValenceE();
        this.valenceShell = element.getGroup().getValenceE();
    }


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
     * @return Element associated to Atom.
     */
    public Elem getElement() {
        return element;
    }

    /**
     * @param attachedBonds bonds to attach to atom.
     */
    public void setAttachedBonds(ArrayList<Bond> attachedBonds) {
        this.attachedBonds = attachedBonds;
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
