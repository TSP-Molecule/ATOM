package structures;

import org.junit.Test;
import structures.enums.BondOrder;
import structures.enums.Elem;

import java.util.ArrayList;
import java.util.Set;

import static org.junit.Assert.*;

public class MoleculeTest {

    @Test
    public void CreateAWaterMolecule() {
        ArrayList<Atom> atoms = new ArrayList<>();
        ArrayList<Bond> bonds = new ArrayList<>();

        //List of elements to add can be grabbed when parsing chemical formula, e.g. H_{2}O gives the following:
        atoms.add(new Atom(Elem.Hydrogen));
        atoms.add(new Atom(Elem.Hydrogen));
        atoms.add(new Atom(Elem.Oxygen));

        //TODO: figure out what gets bonded to what -- may or may not be able to do programmatically
        Bond bond1 = new Bond(BondOrder.SINGLE, atoms.get(0), atoms.get(2));
        Bond bond2 = new Bond(BondOrder.SINGLE, atoms.get(1), atoms.get(2));

        //Add bonds to bond list after creation
        bonds.add(bond1);
        bonds.add(bond2);

        //Create a new molecule "water", containing the created atoms and bonds
        Molecule water = new Molecule(atoms, bonds);

        //Print out data structure
        System.out.println(water.toString());
    }

    public void testerooni(ArrayList<Elem> elems) {
        ArrayList<Atom> atoms = new ArrayList<>();

        for (Elem e : elems) {
            atoms.add(new Atom(e));
        }
    }
}