package structures;

import structures.enums.Elem;
import structures.enums.Geometry;
import web.WebService;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Main data structure for a Molecule. Handles all molecule logic.
 * It's best to think of a Molecule as a Graph, with Atoms for vertices and Bonds for edges.
 *
 * @author Emily Anible
 * CS3141, Spring 2018, Team ATOM
 */
public class Molecule implements Serializable {

    private static final long serialVersionUID = 7355608;

    /**
     * Chemical formula associated with Molecule
     */
    private String formula;

    /**
     * List of all of the atoms in the molecule
     */
    private ArrayList<Atom> atoms;
    /**
     * List of all of the bonds in the molecule
     */
    private ArrayList<Bond> bonds;
    /**
     * Atom in the center (lowest eNeg, not Hydrogen)
     */
    private Atom center;

    /**
     * Molecule name.
     */
    private String name;

    /**
     * Dynamically creates a molecule given a chemical formula string, e.g. "CH_{4}"
     *
     * @param chemFormula chemical formula
     */
    public Molecule(String chemFormula) {
        this(chemFormula, WebService.simplifyFormula(chemFormula, true));
    }

    /**
     * Dynamically creates a molecule given a chemical formula string, e.g. "CH_{4}"
     *
     * @param chemFormula chemical formula
     * @param name        name of molecule
     */
    public Molecule(String chemFormula, String name) {
        ChemicalFormula chem = new ChemicalFormula(chemFormula);
        buildMolecule(chem.getAtoms());

        this.formula = WebService.simplifyFormula(chemFormula, false);
        if (name.length() > 1) this.name = name.substring(0, 1).toUpperCase() + name.substring(1);
        else this.name = name;
    }

    /**
     * Creates a molecule given atoms and bonds.
     * Note, assumes that these are finished, and will not attempt to update them upon object creation.
     *
     * @param atoms list of atoms
     * @param bonds list of bonds
     */
    public Molecule(ArrayList<Atom> atoms, ArrayList<Bond> bonds) {
        this.atoms = atoms;
        this.bonds = bonds;

        center = findCenter(atoms);
    }


    /**
     * Dynamically creates a molecule given a list of atoms.
     *
     * @param atoms list of atoms.
     */
    public Molecule(ArrayList<Atom> atoms) {
        buildMolecule(atoms);
    }

    /**
     * Attempts to create a molecule with bonds from a molecule with a list of atoms.
     * @param initAtoms initial list of atoms
     */
    private void buildMolecule(ArrayList<Atom> initAtoms) {
        //Molecule's ArrayList of Atoms.
        atoms = sortByENeg(initAtoms); //sorts atoms by eneg

        center = findCenter(atoms);
        bonds = new ArrayList<>();

        int i = 0;
        for (Atom a : atoms.subList(i, atoms.size())) {
            if (a.isBondable() && i > 0)
                //Attempt to add to center first. :D
                if (atoms.get(0).isBondable()) {
                    bonds.add(new Bond(a, center));

                    continue;
                }
            for (Atom b : atoms.subList(0, i)) {
                if (b.isBondable()) {
                    bonds.add(new Bond(a, b));
                }
            }
            i++;
        }

        //Now that we're done with the initial atoms, attempt to add hydrogen atoms.
        ArrayList<Atom> addedH = new ArrayList<>(); //Stores added hydrogen atoms.
        for (Atom a : atoms) {

            if (a.equals(center)) continue; //We'll add hydrogen to the center later, if there's any left.
            if (a.isBondable() && initAtoms.size() > 0) {
                while (a.isBondable() && initAtoms.size() > 0) {
                    Atom h = initAtoms.remove(0);
                    bonds.add(new Bond(a, h));
                    addedH.add(h);
                }
            }
        }
        //Now consider the center.
        if (center.isBondable() && initAtoms.size() > 0) {
            while (center.isBondable() && initAtoms.size() > 0) {
                Atom h = initAtoms.remove(0);
                bonds.add(new Bond(center, h));
                addedH.add(h);
            }
        }
        atoms.addAll(addedH);

        //If we still have leftover electrons in connected atoms, attempt to make double bonds instead of single bonds.
        for (Atom a : atoms) {
            if (a.isBondable()) {
                //System.out.println(a.getElement().getName() + " is bondable. has the following bonds: " + a.getAttachedBonds());
                for (Bond b : a.getAttachedBonds()) {
                    Atom other = b.getAtoms().get(0).equals(a) ? b.getAtoms().get(1) : b.getAtoms().get(0);
                    if (other.isBondable()) {
                        //System.out.println("About to increase " + a.getElement().getName() + " and " + other.getElement().getName() + ",
                        //who have " + a.getAvailableElectrons() + "," + other.getAvailableElectrons());
                        b.increaseOrder();
                    }
                }
            }
        }

        //Check Validity of Molecule
        boolean check = true;
        for (Atom a : atoms) {
            if (a.getAvailableElectrons() % 2 != 0) {
                check = false;
            }
            if (!check) System.err.println("------ THE MOLECULE PROBABLY DIDN'T BUILD PROPERLY ------");
        }

        //Now that we're done, try to check the geometry!
        Geometry.calculateGeometry(this);
    }

    /**
     * Sorts list of atoms by electronegativity, placing Hydrogen at the end of the list.
     *
     * @param init atoms to sort
     * @return sorted atoms
     */
    private ArrayList<Atom> sortByENeg(ArrayList<Atom> init) {
        ArrayList<Atom> ret = new ArrayList<>();
        while (init.size() > 0) {
            Atom center = findCenter(init);
            if (center != null) {
                ret.add(center);
                init.remove(center);
            } else break;
        }
        return ret;
    }

    /**
     * Finds the center atom of the molecule based on Lowest Electronegativity
     * Excludes Hydrogen!
     *
     * @param atoms List of Atoms in the molecule
     * @return Center Atom
     */
    private Atom findCenter(ArrayList<Atom> atoms) {
        Atom center = null;

        for (Atom a : atoms) {
            if (a.getElement() == Elem.Hydrogen) continue;
            if (center == null || (a.getElement().geteNegativity() < center.getElement().geteNegativity())) center = a;
        }

        return center;
    }

    @Override
    public String toString() {
        StringBuilder molstr = new StringBuilder();
//        molstr.append("Molecule ");
        molstr.append(name != null ? name : "");
        molstr.append(formula != null ? " " + formula : "");
        molstr.append(":");
        for (Atom a : atoms) {
            molstr.append("\n  " + a);
        }
        molstr.append("\n");
        if (getCenterGeometry() != null) {
            molstr.append("\n Center Geometry: " + getCenterGeometry().getName());
            molstr.append("\n Bond Angle: " + getCenterGeometry().getBondAngle());
        }

        return molstr.toString();
    }

    /**
     * @return center
     */
    public Atom getCenter() {
        return center;
    }

    /**
     * @return bonds
     */
    public ArrayList<Bond> getBonds() {
        return bonds;
    }

    /**
     * @return atoms
     */
    public ArrayList<Atom> getAtoms() {
        return atoms;
    }

    /**
     * @return Center Atom's Geometry
     */
    public Geometry getCenterGeometry() {
        return center.getGeometry();
    }

    /**
     * @return formula
     */
    public String getFormula() {
        return formula;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name name desired for the Molecule
     */
    public void setName(String name) {
        this.name = name;
    }
}
