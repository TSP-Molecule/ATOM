package structures;

import org.junit.Test;
import structures.Atominomicon;
import structures.enums.Elem;

import java.io.IOException;

public class AtominomiconTest {

    /**
     * Not a valid test, just making sure that the Atominomicon creates itself.
     * @throws IOException
     */
    @Test
    public void Atominomicon() throws IOException {
        Atominomicon atominomicon = new Atominomicon();

        System.out.println( "Atomic Mass: "  + Elem.Nickel.getAtomicMass());

      //  Elem.get( i ).getAtomicMass();
          }
}