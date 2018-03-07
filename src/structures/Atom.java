package structures;

import structures.enums.Elem;

import java.util.ArrayList;

/**
 * Atom -
 *
 * @author Emily Anible
 * Atom. Each instance of this object is a "physical" atom, with attributes
 * - Element - What element is this atom?
 * - Bonds    - What bonds object are bonded here?
 * - (maybe atom.getBonded() to get a list of the atoms it's bonded to)
 */
public class Atom {


    private final Elem element;
    private ArrayList<Bond> bonds = null;   //Attached bonds
    private int filledShells;     //Number of full electron shells
    private int valenceShell;     //Number of electrons in valence shell
    private int maxBonds;
    /**
     * Create an Atom with both an element and attached bonds.
     *
     * @param element The Atom's element.
     * @param bonds   ArrayList of attached bonds.
     */
    public Atom(Elem element, ArrayList<Bond> bonds) {
        this.element = element;
        this.bonds = bonds;
        this.maxBonds = 8 - element.getGroup().getInt();
    }


    /**
     * Create an Atom with only an element, no attached bonds.
     *
     * @param element The Atom's element.
     */
    public Atom(Elem element) {
        this.element = element;
    }

    public Elem getElement() {
        return element;
    }

    public ArrayList<Bond> getBonds() {
        return bonds;
    }

    @Override
    public String toString() {
        return "Atom{" +
                "element=" + element +
                ", bonds=" + bonds +
                '}';
    }
}
