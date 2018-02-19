import org.junit.Test;
import structures.Elem;

import java.io.IOException;

public class AtominomiconTest {

    /**
     * Not a valid test, just making sure that the Atominomicon creates itself.
     * @throws IOException
     */
    @Test
    public void readElements() throws IOException {
        Atominomicon atominomicon = new Atominomicon();
        String filename = "src/resources/elements.dat";
        try {
            atominomicon.readElements(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Print out result
//        for (int i = 1; i <= 118; i++) {
//            System.out.println(atominomicon.getAtominomicon().get(i).toString());
//        }

        // Try printing out color of a Zinc atom:
        int zincColor0 = atominomicon.readElem(30).getColor();
        int zincColor1 = atominomicon.readElem(30, 0).getColor();

        int zincColor2 = atominomicon.readAtom(30).getElement().getColor();
        int zincColor3 = atominomicon.readAtom(30, 0).getElement().getColor();

        int zincCol  = atominomicon.getColor(30);

        int test = atominomicon.getColor( Elem.Nickel.get() );

        System.out.println(Integer.toHexString(test));

    }
}