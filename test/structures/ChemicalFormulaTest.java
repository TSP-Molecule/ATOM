package structures;

import org.junit.Test;

import java.util.ArrayList;


public class ChemicalFormulaTest {

    @Test
    public void chemform() {
        String glucose = "C_{6}H_{12}O_{6}";
//        String eme = "H_{2}O";

        ChemicalFormula chm = new ChemicalFormula(glucose);

        ArrayList<Atom> atoms = chm.getAtoms();

        for (Atom a : atoms) {
            System.out.println(a.getElement().getName());
        }

    }
}