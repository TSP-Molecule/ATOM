import org.junit.Test;

import static org.junit.Assert.fail;

public class WebServiceTest {

    @Test
    public void getFormula() {
        try {
            String water = WebService.getFormula("water");
            if (!water.equals("H_{2}O")) {
                System.out.println(water);
                fail("water should be H_{2}O");
            }
            String glucose = WebService.getFormula("glucose");
            if (!glucose.equals("C_{6}H_{12}O_{6}")) {
                System.out.println(glucose);
                fail("glucose should be C_{6}H_{12}O_{6}");
            }
            String nitricAcid = WebService.getFormula("nitric acid");
            if (!nitricAcid.equals("HNO_{3}")) {
                System.out.println(nitricAcid);
                fail("nitric acid should be HNO_{3}");
            }
            String isopropanol = WebService.getFormula("ethyl methyl ether");
            if (!isopropanol.equals("C_{3}H_{8}O")) {
                System.out.println(isopropanol);
                fail("ethyl methyl ether should be C_{3}H_{8}O");
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail("Python script missing OR python dependencies not installed\n\tCheck that Python & chemspipy are installed");
        }
    }

    @Test
    public void getName() {
        try {
            String water = WebService.getName("H_{2}O");
            if (!water.equals("Water")) {
                System.out.println(water);
                fail("H_{2}O should be Water");
            }
            String glucose = WebService.getName("C_{6}H_{12}O_{6}");
            if (!glucose.equals("D-(+)-Glucose")) {
                System.out.println(glucose);
                fail("C_{6}H_{12}O_{6} should be D-(+)-Glucose");
            }
            String nitricAcid = WebService.getName("HNO_{3}");
            if (!nitricAcid.equals("Nitric acid")) {
                System.out.println(nitricAcid);
                fail("HNO_{3} should be Nitric acid");
            }
            String isopropanol = WebService.getName("C_{3}H_{8}O");
            if (!isopropanol.equals("Isopropanol")) {
                System.out.println(isopropanol);
                fail("C_{3}H_{8}O should be Isopropanol");
            }
        } catch (Exception e) {
            System.out.println(System.getProperty("user.dir"));
            e.printStackTrace();
            fail("Python script missing OR python dependencies not installed\n\tCheck that Python & chemspipy are installed");
        }
    }
}