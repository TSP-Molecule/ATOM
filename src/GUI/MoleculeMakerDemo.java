package GUI;

import structures.Atom;
import structures.Bond;
import structures.ChemicalFormula;
import structures.Molecule;
import structures.enums.BondOrder;
import structures.enums.Elem;

import java.util.ArrayList;

/**
 * Demonstrates the current way of combining bond lists with atom lists to create molecules.
 * @author Sarah Larkin
 * CS3141, Spring 2018
 * Date Last Modified:  March 25, 2018
 */
public class MoleculeMakerDemo {

    /**
     * Creates and returns a water molecule.
     * @return the water molecule
     */
    public Molecule water (String formulaString) {
        // Demonstrating parsing of formula for atoms
        ChemicalFormula formula = new ChemicalFormula(formulaString);
        ArrayList<Atom>atoms = formula.getAtoms();

        ArrayList<Bond> bonds = new ArrayList<>();

        // Hardcoded in the methane bonds for now
        for (int i = 1; i < atoms.size(); i++) {
            Bond bond = new Bond(atoms.get(0), atoms.get(i));
            bond.setBondingAngle(120);
            bonds.add(bond);
        }

        //  Create and return the water molecule
        Molecule water = new Molecule(atoms, bonds);
        return water;
    }

    /**
     * Creates and returns a methane molecule.
     * @return the methane molecule
     */
    public Molecule methane(String formulaString) {
        // Demonstrating parsing of formula for atoms
        ChemicalFormula formula = new ChemicalFormula(formulaString);
        ArrayList<Atom>atoms = formula.getAtoms();

        ArrayList<Bond> bonds = new ArrayList<>();

        // Hardcoded in the methane bonds for now
        for (int i = 1; i < atoms.size(); i++) {
            Bond bond = new Bond(atoms.get(0), atoms.get(i));
            bond.setBondingAngle(110);
            bonds.add(bond);
        }

        // Create the methane molecule and return it.
        Molecule methane = new Molecule(atoms, bonds);
        return methane;
    }

    /**
     * Creates and returns a carbonDioxide molecule.
     * @return the carbonDioxide molecule
     */
    public Molecule carbonDioxide(String formulaString) {
        // Demonstrating parsing of formula for atoms
        ChemicalFormula formula = new ChemicalFormula(formulaString);
        ArrayList<Atom>atoms = formula.getAtoms();

        ArrayList<Bond> bonds = new ArrayList<>();

        // Hardcoded in the methane bonds for now
        for (int i = 1; i < atoms.size(); i++) {
            Bond bond = new Bond(atoms.get(0), atoms.get(i));
            bond.setBondingAngle(120);
            bonds.add(bond);
        }

        // Create the carbon dioxide molecule and return it.
        Molecule cO2 = new Molecule(atoms, bonds);
        return cO2;
    }


}
