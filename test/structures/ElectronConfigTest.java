package structures;

import org.junit.Test;

public class ElectronConfigTest {

    @Test
    public void ElectronConfigParser() {
        String eConfig = "[Xe]-4f14-5d10-6s2-6p1";

        ElectronConfig ecp = new ElectronConfig(eConfig);
    }

}