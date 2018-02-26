import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class WebServiceTest {

    public static void main (String args[]) throws IOException {
        String chem = "\'nitric acid\'";
        Process p = Runtime.getRuntime().exec("python ChemSpider.py " + chem);
        BufferedReader pin = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String s = null;
        while ((s = pin.readLine()) != null) {
            System.out.println(s);
        }
    }
}