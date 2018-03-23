package GUI;

import structures.Atom;
import structures.Bond;
import structures.ChemicalFormula;
import structures.Molecule;
import structures.enums.BondOrder;
import structures.enums.Elem;

import java.util.ArrayList;

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
            Bond bond = new Bond(BondOrder.SINGLE, atoms.get(0), atoms.get(i));
            bond.setBondingAngle(120);
            bonds.add(bond);
        }

        //  Create and return the water molecule
        Molecule water = new Molecule(atoms, bonds);
        return water;
    }

    public Molecule methane(String formulaString) {
        // Demonstrating parsing of formula for atoms
        ChemicalFormula formula = new ChemicalFormula(formulaString);
        ArrayList<Atom>atoms = formula.getAtoms();

        ArrayList<Bond> bonds = new ArrayList<>();

        // Hardcoded in the methane bonds for now
        for (int i = 1; i < atoms.size(); i++) {
            Bond bond = new Bond(BondOrder.SINGLE, atoms.get(0), atoms.get(i));
            bond.setBondingAngle(110);
            bonds.add(bond);
        }

        // Create the methane molecule and return it.
        Molecule methane = new Molecule(atoms, bonds);
        return methane;
    }

    public Molecule carbonDioxide(String formulaString) {
        // Demonstrating parsing of formula for atoms
        ChemicalFormula formula = new ChemicalFormula(formulaString);
        ArrayList<Atom>atoms = formula.getAtoms();

        ArrayList<Bond> bonds = new ArrayList<>();

        // Hardcoded in the methane bonds for now
        for (int i = 1; i < atoms.size(); i++) {
            Bond bond = new Bond(BondOrder.SINGLE, atoms.get(0), atoms.get(i));
            bond.setBondingAngle(120);
            bonds.add(bond);
        }

        // Create the carbon dioxide molecule and return it.
        Molecule cO2 = new Molecule(atoms, bonds);
        return cO2;
    }

    public Molecule defaultDemo(String formulaString) {

        // Manually entered bonds and atoms for now
        // Will be automated by end of 3rd sprint

        ArrayList<Atom>atoms = new ArrayList<>();
        ArrayList<Bond> bonds = new ArrayList<>();



        // Hardcoded in the methane bonds for now
        for (int i = 1; i < atoms.size(); i++) {
            Bond bond = new Bond(BondOrder.SINGLE, atoms.get(0), atoms.get(i));
            bond.setBondingAngle(120);
            bonds.add(bond);
        }

        // Create the carbon dioxide molecule and return it.
        Molecule cO2 = new Molecule(atoms, bonds);
        return cO2;
    }
}
