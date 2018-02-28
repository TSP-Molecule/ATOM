package structures.enums;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class ElemTest {

    @Test
    public void getBySymbol() {
        String symbol = "Xe";
        Assert.assertEquals(Elem.getBySymbol("Xe"), Elem.Xenon);
    }
}