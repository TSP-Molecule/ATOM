package structures;

import structures.enums.Elem;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Main data structure for a Molecule. Handles all molecule logic.
 *
 * @author Emily Anible
 * CS3141, Spring 2018, Team ATOM
 */
public class Molecule {

    private ArrayList<Atom> atoms; //List of all of the atoms in the molecule
    private ArrayList<Bond> bonds; //List of all of the bonds in the molecule
    private Atom center;                 //Atom in the center (lowest eNeg, not Hydrogen)

    public Molecule(ArrayList<Atom> atoms, ArrayList<Bond> bonds) {
        this.atoms = atoms;
        this.bonds = bonds;

        center = findCenter(atoms);
    }

    public Molecule(ArrayList<Atom> atoms) {
        buildMolecule(atoms);
    }

    public Molecule(String chemFormula) {
        ChemicalFormula chem = new ChemicalFormula(chemFormula);
        buildMolecule(chem.getAtoms());
    }

    /**
     * Attempts to create a molecule with bonds from a molecule with a list of atoms.
     *
     * @return built Molecule.
     */
    private void buildMolecule(ArrayList<Atom> initAtoms) {
        boolean ret = false;
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

        //Now that we're done with the initial atoms, attempt to add hydrogens.
        ArrayList<Atom> addedH = new ArrayList<>(); //Stores added hydrogens.
        for (Atom a : atoms) {

            if (a.equals(center)) continue;
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
            while (center.isBondable()) {
                Atom h = initAtoms.remove(0);
                bonds.add(new Bond(center, h));
                addedH.add(h);
            }
        }
        atoms.addAll(addedH);

        System.out.println("Checking availability:");
        for (Atom a : atoms) {
            System.out.println(a.getElement().getName() + ": " + a.getAvailableElectrons() + ". Bondable: " + a.isBondable());
        }

        //TODO: If we still have leftover electrons in connected atoms, attempt to make double bonds instead of single bonds.
        for (Atom a : atoms) {
            if (a.isBondable()) {
                System.out.println(a.getElement().getName() + " is bondable. has the following bonds: " + a.getAttachedBonds());

                for (Bond b : a.getAttachedBonds()) {
                    Atom other = b.getAtoms().get(0).equals(a) ? b.getAtoms().get(1) : b.getAtoms().get(0);
                    if (other.isBondable()) {
                        System.out.println("About to increase " + a.getElement().getName() + " and " + other.getElement().getName()
                                + ", who have " + a.getAvailableElectrons() + "," + other.getAvailableElectrons());
                        b.increaseOrder();
                    }
                }
            }
        }

            //Check Validity of Molecule
            boolean check = true;
            for (Atom a: atoms) {
                if (a.getAvailableElectrons() % 2 != 0) {
                    check = false;
                    System.err.println("------ THE MOLECULE PROBABLY DIDN'T BUILD PROPERLY ------");
                }
            }
            if (check) return;

            //TODO: If we need to repeat the creation... rip.
    }


    /**
     * Sorts list of atoms by electronegativity, placing Hydrogen at the end of the list.
     *
     * @param init
     * @return
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
        return "Molecule{" + atoms + "}";
    }


}
