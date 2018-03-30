package web;

import java.io.*;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebService {

    private static final String PY = "python";
    private static final String SRC = "scripts/";

    private static final String CHEMSPIDER = "ChemSpider.PY";
    private static final String WIKI = "Wikipedia.PY";

    /**
     * @param chem - Chemical name
     * @return formumla - Chemical formula
     * (null if no formula found)
     * @throws IOException - thrown if python script is missing
     */
    public static HashMap<String, String> getFormula(String chem) throws IOException {
        String name;
        String formula;
        HashMap<String, String> results = new HashMap<>();

        // Process allows for us to run external scripts and receive their output.
        // -f flag for formula
        Process p = Runtime.getRuntime().exec(new String[]{PY, SRC + CHEMSPIDER, "-f", chem});

        // pin is the Process IN, which allows for us to read the output from the executed script.
        BufferedReader pin = new BufferedReader(new InputStreamReader(p.getInputStream()));


        //name and formula responses in line by line, returning the full HashMap at the end.
        while ((name = pin.readLine()) != null && (formula = pin.readLine()) != null) {
            results.put(name, formula);
        }

        return results;
    }

    /**
     * @param form - Chemical formula
     * @return name - Chemical name
     * (null if no name found)
     * @throws IOException - thrown if python script is missing
     */
    public static String getName(String form) throws IOException {
        String formEdit = form.replace("_", "").replace("{", "").replace("}", "");
        // ^^reformats the string passed in (which is in our standard form) to work with the python script

        //getName & getFormula behave the same from here out. -n flag for name
        Process p = Runtime.getRuntime().exec(new String[]{PY, SRC + CHEMSPIDER, "-n", formEdit});

        BufferedReader pin = new BufferedReader(new InputStreamReader(p.getInputStream()));

        return pin.readLine(); //Returns null if not found or issue occurs
    }

    /**
     * @param term - Search Term
     * @return name - First paragrah of wikipedia article
     * (null if no article found)
     * @throws IOException - thrown if python script is missing
     */
    public static String getWiki(String term) throws IOException {

        Process p = Runtime.getRuntime().exec(new String[]{PY, SRC + WIKI, term});

        BufferedReader pin = new BufferedReader(new InputStreamReader(p.getInputStream()));

        return pin.readLine(); //Returns null if not found or issue occurs
    }

    /**
     * Simplifies mol formula of form Xx_{n}X_{nn}
     *
     * @param formula
     * @return
     */
    public static String simplifyFormula(String formula, boolean addSpaces) {
        formula = formula.replace("{", "").replace("}", "").replace("_", "");
        if (!addSpaces) return formula;
        Pattern pattern = Pattern.compile("([A-Z][a-z]?[0-9]?[0-9]*)");
        Matcher matcher = pattern.matcher(formula);

        StringBuilder withSpaces = new StringBuilder();

        while (matcher.find()) {
            withSpaces.append(matcher.group() + " ");
        }
        return withSpaces.toString();
    }
}
