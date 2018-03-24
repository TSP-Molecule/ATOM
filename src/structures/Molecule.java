package structures;

import structures.enums.Elem;

import java.util.ArrayList;

/**
 * Main data structure for a Molecule. Handles all molecule logic.
 *
 * @author Emily Anible
 * CS3141, Spring 2018, Team ATOM
 */
public class Molecule {

    //TODO: Tree-Structure for Molecule for ease of drawing and computation.
    private final ArrayList<Atom> atoms; //List of all of the atoms in the molecule
    private final ArrayList<Bond> bonds; //List of all of the bonds in the molecule
    private Atom center;                 //Atom in the center (lowest eNeg, not Hydrogen)

    public Molecule(ArrayList<Atom> atoms, ArrayList<Bond> bonds) {
        this.atoms = atoms;
        this.bonds = bonds;

        center = findCenter(atoms);
    }

    /**
     * Finds the center atom of the molecule based on Lowest Electronegativity
     * Excludes Hydrogen!
     *
     * @param atoms List of Atoms in the molecule
     * @return Center Atom
     */
    private Atom findCenter(ArrayList<Atom> atoms) {
        Atom center = atoms.get(0);

        for (Atom a : atoms) {
            if ((center.getElement() == Elem.Hydrogen)
                    || (a.getElement().geteNegativity() < center.getElement().geteNegativity()
                    && a.getElement() != Elem.Hydrogen)) {
                center = a;
            }
        }

        return center;
    }

    public Atom getCenter() {
        return center;
    }

    public ArrayList<Bond> getBonds() {
        return bonds;
    }

    public ArrayList<Atom> getAtoms() {
        return atoms;
    }



    @Override
    public String toString() {
        return "Molecule{" +
                " \n  atoms = " + atoms +
//                ",\n  bonds = " + bonds +
                ",\n\nCENTER= " + center +
                "\n}";
    }


}
