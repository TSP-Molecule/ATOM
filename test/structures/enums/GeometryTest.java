package structures.enums;

import org.junit.Test;
import structures.Molecule;
import web.WebService;

import java.io.IOException;

import static org.junit.Assert.*;

public class GeometryTest {

    @Test
    public void geometryTest() throws IOException {
        WebService ws = new WebService();
        String formula = ws.getFormula("Water");
        System.out.println("Isopropyl alcohol is " + formula);

        Molecule mol = new Molecule(formula);


        System.out.println(mol);
    }

}