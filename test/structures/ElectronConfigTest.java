package structures;

import org.junit.Assert;
import org.junit.Test;
import structures.enums.Elem;

import java.util.ArrayList;

public class ElectronConfigTest {

    @Test
    public void ElectronConfig() {
        String eConfig = "[Xe]-4f14-5d10-6s2-6p1";
        ArrayList<String> expectedConfig = new ArrayList<>();
        expectedConfig.add("4f14");
        expectedConfig.add("5d10");
        expectedConfig.add("6s2");
        expectedConfig.add("6p1");

        ElectronConfig ecp = new ElectronConfig(eConfig);
        Assert.assertEquals(Elem.Xenon, ecp.getNobleHead());
        Assert.assertEquals(expectedConfig, ecp.getConfig());
    }

    @Test
    public void ElectronConfigSmall() {
        String eConfig =  "1s1"; /* Hydrogen */
        ArrayList<String> expectedConfig = new ArrayList<>();
        expectedConfig.add("1s1");

        ElectronConfig ecp = new ElectronConfig(eConfig);
        Assert.assertEquals(Elem.NULL, ecp.getNobleHead());
        Assert.assertEquals(expectedConfig, ecp.getConfig());
    }

}