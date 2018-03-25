package web;

import com.sun.javafx.PlatformUtil;

import java.io.*;

public class WebService {

    private static String pythonPath = null;

    public WebService() throws IOException {
        if (PlatformUtil.isWindows()) {
            pythonPath = System.getenv("PYTHONPATH");
            System.out.println("Hey, you're running windows! Neat!");
            System.out.println(pythonPath);

            if (pythonPath == null) {
                System.out.println("There's an issue with the path!");
                throw new FileNotFoundException(); //TODO: Custom exception class.
            }
        } else {
            pythonPath = "python";
            System.out.println("NOT WINDOWS");
        }
    }

    /**
     *
     * @param chem - Chemical name
     * @return formumla - Chemical formula
     *          (null if no formula found)
     * @throws IOException - thrown if python script is missing
     */
    public static String getFormula(String chem) throws IOException {

        // -f flag for formula
        Process p = Runtime.getRuntime().exec(new String[]{pythonPath, "./src/ChemSpider.py", "-f", chem});

        String[] test = new String[]{pythonPath,"./src/ChemSpider.py", "-f", chem};
        for (String s: test) System.out.print(s + " ");

        // Process allows for us to run external scripts and receive their output. ProcessBuilder makes the process.

        BufferedReader pin = new BufferedReader(new InputStreamReader(p.getInputStream()));
        // pin is the Process IN, which allows for us to read the output from the executed script.

        String form;
        if ((form = pin.readLine()) != null) {
            return form;
        } else {
            return null;
        }
        // Pulls the first line (There should only be 1 line returned by the script).
        //    Returns null if there is no output from the script

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
        Process p = Runtime.getRuntime().exec(new String[]{pythonPath, "./src/ChemSpider.py", "-n", formEdit});

        BufferedReader pin = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String name;
        if ((name = pin.readLine()) != null) {
            return name;
        } else {
            return null;
        }
    }

}
