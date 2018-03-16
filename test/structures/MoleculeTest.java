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

        atoms.add(new Atom(Elem.Hydrogen));
        atoms.add(new Atom(Elem.Hydrogen));
        atoms.add(new Atom(Elem.Oxygen));

        Bond bond1 = new Bond(BondOrder.SINGLE, atoms.get(0), atoms.get(2));
        Bond bond2 = new Bond(BondOrder.SINGLE, atoms.get(1), atoms.get(2));

        atoms.get(0).getAttachedBonds().add(bond1);
        atoms.get(2).getAttachedBonds().add(bond1);

        atoms.get(1).getAttachedBonds().add(bond2);
        atoms.get(2).getAttachedBonds().add(bond2);

        bonds.add(bond1);
        bonds.add(bond2);

        Molecule water = new Molecule(atoms, bonds);
        System.out.println(water.toString());
    }
}