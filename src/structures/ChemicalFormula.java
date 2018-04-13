package structures;

import structures.enums.Elem;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Stores Chemical Formula, Parses from string.
 * Used to create proper molecule.
 *
 * @author Emily Anible
 * CS3141, Spring 2018, Team ATOM
 */
public class ChemicalFormula {

    private ArrayList<Atom> atoms;

    private ArrayList<String> chem;


    /**
     * @param chemFormula String result from Chem spider
     */
    public ChemicalFormula(String chemFormula) {
        chem = chemStringParser(chemFormula);
        atoms = chemParser(chem);

//        System.out.println(chem);
//        System.out.println(atoms);

    }

    /**
     * Splits passed in string to ArrayList of elements with total number
     *
     * @param chemFormula
     * @return
     */
    private ArrayList<String> chemStringParser(String chemFormula) {
        ArrayList<String> chems = new ArrayList<>();

        Pattern pattern = Pattern.compile("(([A-Z])([a-z])?(_\\{[0-9]*}|[0-9]*))");
        Matcher matcher = pattern.matcher(chemFormula);

        while (matcher.find()) {
            chems.add(matcher.group());
        }


        return chems;
    }

    /**
     * Takes in parsed chemical ArrayList and gives back an ArrayList with each of the atoms in the molecule.
     *
     * @param chem
     * @return
     */
    private ArrayList<Atom> chemParser(ArrayList<String> chem) {
        ArrayList<Atom> atoms = new ArrayList<>();

        for (String s : chem) {
            Pattern symbol = Pattern.compile("(([A-Z])([a-z])?)");
            Pattern number = Pattern.compile("([0-9])+");

            Matcher symMatcher = symbol.matcher(s);
            Matcher numMatcher = number.matcher(s);

            int count = 1;
            if (numMatcher.find()) {
                count = Integer.parseInt(numMatcher.group());
            }


            if (symMatcher.find()) {
                Elem elem = Elem.getBySymbol(symMatcher.group());
//                System.out.println("We found " + elem);

                // It shouldn't be at this point, but you never know.
                if (elem != null) {
                    for (int i = 1; i <= count; i++) {
                        atoms.add(new Atom(elem));
                    }
                }
            }

        }

        return atoms;
    }

    public ArrayList<Atom> getAtoms() {
        return atoms;
    }

    public ArrayList<String> getChem() {
        return chem;
    }
}
