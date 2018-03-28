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
        System.out.println("Isopropyl alcohol is " + formula);

        Molecule mol = new Molecule(formula);
        System.out.println(mol);
    }

    @Test
    public void acetoneTest() throws IOException {
        WebService ws = new WebService();
        String formula = ws.getFormula("acetone");
        System.out.println("Acetone is " + formula);

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

    @Test
    public void alcoholSave() throws IOException {
        String chem = "Isopropyl Alcohol";
        String formula = WebService.getFormula(chem);
        System.out.println(chem + " is " + formula);

        Molecule mol = new Molecule(formula, chem);

        System.out.println(mol);

        System.out.println("Attempting to save...");
        MolFile.saveMolecule(mol, "alcohol");
    }

    @Test
    public void alcoholLoad() throws IOException, ClassNotFoundException {
        Molecule mol = MolFile.loadMolecule("alcohol");
        System.out.println(mol);
    }
}