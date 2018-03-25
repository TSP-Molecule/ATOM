package web;

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

        // Process allows for us to run external scripts and receive their output.
        // -f flag for formula
        Process p = Runtime.getRuntime().exec(new String[]{"python", "ChemSpider.py", "-f", chem});

        // pin is the Process IN, which allows for us to read the output from the executed script.
        BufferedReader pin = new BufferedReader(new InputStreamReader(p.getInputStream()));

        return pin.readLine();
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
        // ^^reformats the string passed in (which is in our standard form) to work with the python script

        //getName & getFormula behave the same from here out. -n flag for name
        Process p = Runtime.getRuntime().exec(new String[]{"python", "ChemSpider.py", "-n", formEdit});

        BufferedReader pin = new BufferedReader(new InputStreamReader(p.getInputStream()));

        return pin.readLine(); //Returns null if not found or issue occurs
    }

}
