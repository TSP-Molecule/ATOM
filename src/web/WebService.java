package web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utilizes Python scripts to access information about chemicals from various databases.
 *
 * @author Crystal Fletcher
 * CS3141, Spring 2018, Team ATOM
 */
public class WebService {

    private static final String PY = "python";
    private static final String SRC = "scripts/";

    private static final String CHEMSPIDER = "ChemSpider.py";
    private static final String WIKI = "Wikipedia.py";

    /**
     * @param chem - Chemical name
     * @return Chemical formula
     * (null if no formula found)
     * @throws IOException - thrown if python script is missing
     */
    public static HashMap<String, String> getFormula(String chem) throws IOException {
        HashMap<String, String> results = new HashMap<>();
        String name;
        String formula;

        BufferedReader pin = pyScript(new String[]{PY, SRC + CHEMSPIDER, "-f", chem});

        //name and formula responses in line by line, returning the full HashMap at the end.
        while ((name = pin.readLine()) != null && (formula = pin.readLine()) != null) {
            results.put(name, formula);
        }

        return (results.size() > 0 ? results : null);
    }

    /**
     * @param form - Chemical formula
     * @return name - Chemical name
     * (null if no name found)
     * @throws IOException - thrown if python script is missing
     */
    public static String getName(String form) throws IOException {
        BufferedReader pin = pyScript(new String[]{PY, SRC + CHEMSPIDER, "-n", simplifyFormula(form, false)});
        return pin.readLine(); //Returns null if not found or issue occurs
    }

    /**
     * ]
     *
     * @param term - Search Term
     * @return name - First paragrah of wikipedia article
     * (null if no article found)
     * @throws IOException - thrown if python script is missing
     */
    public static ArrayList<String> getWiki(String term) throws IOException {
        ArrayList<String> ret = new ArrayList<>();
        String read;

        BufferedReader pin = pyScript(new String[]{PY, SRC + WIKI, term});

        while ((read = pin.readLine()) != null) {
            String fix = Normalizer.normalize(read, Normalizer.Form.NFC);
            fix = fix.replaceAll("(\\\\x[a-z]?[a-z]?[0-9]*)+", " [] ");
            ret.add(fix);
        }
        return ret;
    }

    public static String getWikiAsString(String term) throws IOException {
        ArrayList<String> wiki = getWiki(term);
        StringBuilder ret = new StringBuilder();

        for (String s : wiki) {
            ret.append("\n" + s);
        }

//        System.out.println("ret.toString() = " + ret.toString());
        return ret.toString();
    }

    /**
     * Simplifies chemical formula
     *
     * @param formula   Chemical Formula
     * @param addSpaces true/false add spaces between elements
     * @return parsed chemical formula
     */
    public static String simplifyFormula(String formula, boolean addSpaces) {
        formula = formula.replace("{", "").replace("}", "").replace("_", "");
        if (!addSpaces) return formula;
        Pattern pattern = Pattern.compile("([A-Z][a-z]?[0-9]?[0-9]*)");
        Matcher matcher = pattern.matcher(formula);

        StringBuilder withSpaces = new StringBuilder();

        while (matcher.find()) {
            String append = matcher.group() + " ";
            withSpaces.append(append);
        }
        return withSpaces.toString();
    }

    /**
     * Calls Process to run Python Script with passed in arguments
     *
     * @param args Array of terms { PY, script name, arg0, arg1, ..., argn }
     * @return BufferedReader from Python Script
     * @throws IllegalArgumentException If the user doesn't specify args to pass.
     * @throws IOException              If something goes horribly wrong.
     */
    private static BufferedReader pyScript(String[] args) throws IllegalArgumentException, IOException {
        if (args == null || args.length == 0) throw new IllegalArgumentException();
        Process p = Runtime.getRuntime().exec(args);

        return new BufferedReader(new InputStreamReader(p.getInputStream(), "UTF-8"));
    }
}
