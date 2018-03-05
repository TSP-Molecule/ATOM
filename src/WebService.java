import java.io.*;

public class WebService {

    /**
     *
     * @param chem - Chemical name
     * @return formumla - Chemical formula
     *          (null if no formula found)
     * @throws IOException - thrown if python script is missing
     */
    public static String getFormula(String chem) throws IOException {

        Process p = new ProcessBuilder("python", "./src/ChemSpider.py", "-f", "water").start();
        BufferedReader pin = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String form = null;
        if ((form = pin.readLine()) != null) {
            return form;
        } else {
            return null;
        }
    }

    /**
     *
     * @param form - Chemical formula
     * @return name - Chemical name
     *          (null if no name found)
     * @throws IOException - thrown if python script is missing
     */
    public static String getName(String form) throws IOException {
        String formEdit = form.replace("_", "").replace("{","").replace("}","");
        Process p = Runtime.getRuntime().exec(new String[]{"python","./src/ChemSpider.py", "-n", formEdit});
        BufferedReader pin = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String name = null;
        if ((name = pin.readLine()) != null) {
            return name;
        } else {
            return null;
        }
    }

}
