import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class WebServiceExample {

    public static void main (String args[]) throws IOException {
        String chem = "water";
        String file = System.getProperty("user.dir");
        System.out.println(file);
        Process p = Runtime.getRuntime().exec(new String[]{"C:\\Program Files (x86)\\Python36-32\\python.exe", "./src/ChemSpider.py","-f",chem});
        BufferedReader pin = new BufferedReader(new InputStreamReader(p.getInputStream()));
        BufferedReader perr = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        System.out.println("Process output: ");
        String s = null;
        while ((s = pin.readLine()) != null) {
            System.out.println(s);
        }

        System.out.println("\nProcess error: ");
        while ((s = perr.readLine()) != null) {
            System.out.println(s);
        }
    }
}