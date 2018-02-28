package structures;

import org.junit.Test;

import static org.junit.Assert.*;

public class ElectronConfigParserTest {

    @Test
    public void ElectronConfigParser() {
        String eConfig = "[Xe]-4f14-5d10-6s2-6p1";

        ElectronConfigParser ecp = new ElectronConfigParser(eConfig);
    }

}