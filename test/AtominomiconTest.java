import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

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
        for (int i = 1; i <= 118; i++) {
            System.out.println(atominomicon.getAtominomicon().get(i).toString());
        }
    }
}