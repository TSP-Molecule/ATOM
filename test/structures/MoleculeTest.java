package structures;

import org.junit.Test;
import structures.enums.BondOrder;
import structures.enums.Elem;
import web.WebService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import static org.junit.Assert.*;

@SuppressWarnings("Duplicates")
public class MoleculeTest {

    @Test
    public void dynamicWaterTest() throws IOException {
        WebService ws = new WebService();
        String formula = ws.getFormula("water");

        Molecule mol = new Molecule(formula);
        System.out.println(mol);
    }

    @Test
    public void methaneTest() throws IOException {
        WebService ws = new WebService();
        String formula = ws.getFormula("methane");
        System.out.println("methane is " + formula);

        Molecule mol = new Molecule(formula);
        System.out.println(mol);
    }


    @Test
    public void alcoholTest() throws IOException {
        WebService ws = new WebService();
        String formula = ws.getFormula("isopropyl alcohol");
        System.out.println("isopropyl alcohol is " + formula);

        Molecule mol = new Molecule(formula);
        System.out.println(mol);
    }

    @Test
    public void acetoneTest() throws IOException {
        WebService ws = new WebService();
        String formula = ws.getFormula("acetone");
        System.out.println("acetone is " + formula);

        Molecule mol = new Molecule(formula);
//        System.out.println("center: " + mol.getCenter());
        System.out.println(mol);
    }

    @Test
    public void oxygenTest() throws IOException {
        WebService ws = new WebService();
        String formula = ws.getFormula("O2");
        System.out.println("O2 is " + formula);

        Molecule mol = new Molecule(formula);
        System.out.println(mol);
//        System.out.println("center: " + mol.getCenter());
    }

    //    @Test
    //    public void CreateAWaterMolecule() {
    //        ArrayList<Atom> atoms = new ArrayList<>();
    //        ArrayList<Bond> bonds = new ArrayList<>();
    //
    //        //List of elements to add can be grabbed when parsing chemical formula, e.g. H_{2}O gives the following:
    //        atoms.add(new Atom(Elem.Hydrogen));
    //        atoms.add(new Atom(Elem.Hydrogen));
    //        atoms.add(new Atom(Elem.Oxygen));
    //
    //        Bond bond1 = new Bond(atoms.get(0), atoms.get(2));
    //        Bond bond2 = new Bond(atoms.get(1), atoms.get(2));
    //
    //        //Add bonds to bond list after creation
    //        bonds.add(bond1);
    //        bonds.add(bond2);
    //
    //        //Create a new molecule "water", containing the created atoms and bonds
    //        Molecule water = new Molecule(atoms, bonds);
    //
    //        //Print out data structure
    //        System.out.println(water.toString());
    //    }
}