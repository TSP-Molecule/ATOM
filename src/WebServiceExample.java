import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class WebServiceExample {

    public static void main (String args[]) throws IOException {
        String chem = args[0];
        Process p = Runtime.getRuntime().exec(new String[]{"python","ChemSpider.py","-f",chem});
        BufferedReader pin = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String s = null;
        while ((s = pin.readLine()) != null) {
            System.out.println(s);
        }
    }
}