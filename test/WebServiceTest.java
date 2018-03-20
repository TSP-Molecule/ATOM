import org.junit.Test;
import static junit.framework.TestCase.fail;

class WebServiceTest {

    @Test
    void getFormula() {
        try {
            if (WebService.getFormula("water").equals("H_{2}O"))
            {
                fail("water should be H_{2}O");
            }
            if (WebService.getFormula("glucose").equals("C_{6}H_{12}O_{6}"))
            {
                fail("glucose should be C_{6}H_{12}O_{6}");
            }
            if (WebService.getFormula("nitric acid").equals("HNO_{3}"))
            {
                fail("nitric acid should be HNO_{3}");
            }
            if (WebService.getFormula("ethyl methyl ether").equals("C_{3}H_{8}O"))
            {
                fail("ethyl methyl ether should be C_{3}H_{8}O");
            }
        } catch (Exception e) {
            //fail("Python script missing");
            e.printStackTrace();
        }
    }

    @Test
    void getName() {
    }
}