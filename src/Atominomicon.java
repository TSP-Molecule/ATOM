import structures.Atom;
import structures.Element;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Atominomicon -
 *
 * @author Emily Anible
 * Each instance of Atominomicon provides a full set of Atoms and their isotopes to be used as reference or
 * for building molecules.
 * <p>
 * ArrayList of HashMaps by atomic number
 * - Each Hashmap has
 * key - isotope number
 * value - Atom object corresponding to that isotope
 */
public class Atominomicon {

    public final String ELEMENTS_FILE = "src/resources/elements.dat";
    private final ArrayList<HashMap<Integer, Atom>> atominomicon;

    /**
     * Generates a copy of the Atominomicon in memory.
     */
    public Atominomicon() throws IOException {
        atominomicon = readElements(ELEMENTS_FILE);
    }


    /**
     * Reads source file and creates a HashMap for each element with all valid isotopes,
     * storing each HashMap in the ArrayList.
     * <p>
     * The zero element in a HashMap is the default atom, non-isotopic //TODO: Figure out isotopes
     *
     * @param filename elements.dat file to read
     * @return HashMap for an atom
     */
    public ArrayList<HashMap<Integer, Atom>> readElements(String filename) throws IOException {

        ArrayList<HashMap<Integer, Atom>> nomicon = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filename));

        nomicon.add(0, null);   // Zero element should be null
        String line = br.readLine();         // Read in heading line before processing data.

        while ((line = br.readLine()) != null) {
            line = line.replace(" ", ""); //Remove Whitespace
            String[] s = line.split(",");           //Split line by commas
            for (int i = 0; i < s.length; i++) if (s[i].equals("")) s[i] = "000000";


            Element element = new Element(
                    Integer.parseInt(s[0]),             // Atomic number
                    s[1],                               // Symbol
                    s[2],                               // Name
                    Double.parseDouble(s[3]),           // Atomic Mass
                    Integer.parseInt(s[4], 16),    // CPK Color
                    s[5],                               // E Config
                    Double.parseDouble(s[6]),           // E Negativity
                    Integer.parseInt(s[7]),             // Atomic Radius
                    s[8],                               // Bonding Type
                    Double.parseDouble(s[9]),           // Density
                    s[10],                              // Group
                    Integer.parseInt(s[11])             // State
            );

            HashMap<Integer, Atom> atomMap = new HashMap<>();
            Atom atom = new Atom(element);
            atomMap.put(0, atom);           //Place base element in map at zero position

            nomicon.add(element.getAtomicNumber(), atomMap);
        }
        return nomicon;
    }

    /**
     * Reads Element from Atominomicon
     *
     * @param atomicNumber  atomic number of element
     * @param isotope       isotope value
     * @return  element corresponding to atomic number and isotope
     */
    public Element readElem(int atomicNumber, int isotope) {
        return atominomicon.get(atomicNumber).get(isotope).getElement();
    }

    /**
     * Reads Element from Atominomicon
     *
     * @param atomicNumber  atomic number of element
     * @return  element corresponding to atomic number
     */
    public Element readElem(int atomicNumber) {
        return atominomicon.get(atomicNumber).get(0).getElement();
    }

    /**
     * Reads Atom from Atominomicon
     *
     * @param atomicNumber  atomic number of atom
     * @param isotope       isotope value
     * @return  atom corresponding to atomic number and isotope
     */
    public Atom readAtom(int atomicNumber, int isotope) {
        return atominomicon.get(atomicNumber).get(isotope);
    }

    /**
     * @param atomicNumber  atomic number of atom
     * @return  atom corresponding to atomic number and isotope
     *
     */
    public Atom readAtom(int atomicNumber) {
        return atominomicon.get(atomicNumber).get(0);
    }

    /**
     * @param atomicNumber atomic number of atom
     * @return Symbol associated with the atom at atomicNumber
     */
    public String getSymbol(int atomicNumber) {
        return this.readElem(atomicNumber).getSymbol();
    }
    /**
     * @param atomicNumber atomic number of atom
     * @return Name associated with the atom at atomicNumber
     */
    public String getName(int atomicNumber) {
        return this.readElem(atomicNumber).getName();
    }

    /**
     * @param atomicNumber atomic number of atom
     * @return Atomic Mass associated with the atom at atomicNumber
     */
    public double getAtomicMass(int atomicNumber) {
        return this.readElem(atomicNumber).getAtomicMass();
    }
    /**
     * @param atomicNumber atomic number of atom
     * @return Atomic Radius associated with the atom at atomicNumber
     */
    public int getAtomicRadius(int atomicNumber) {
        return this.readElem(atomicNumber).getAtomicRadius();
    }
    /**
     * @param atomicNumber atomic number of atom
     * @return Density associated with the atom at atomicNumber
     */
    public double getDensity(int atomicNumber) {
        return this.readElem(atomicNumber).getDensity();
    }

    /**
     * @param atomicNumber atomic number of atom
     * @return Color associated with the atom at atomicNumber
     */
    public int getColor(int atomicNumber) {
        return this.readElem(atomicNumber).getColor();
    }

    /**
     * @param atomicNumber atomic number of atom
     * @return Electron Configuration associated with the atom at atomicNumber
     */
    public String geteConfig(int atomicNumber) {
        return this.readElem(atomicNumber).geteConfig();
    }

    /**
     * @param atomicNumber atomic number of atom
     * @return Bonding Type associated with the atom at atomicNumber
     */
    public String getBondingType(int atomicNumber) {
        return this.readElem(atomicNumber).getBondingType();
    }

    /**
     * @param atomicNumber atomic number of atom
     * @return Group associated with the atom at atomicNumber
     */
    public String getGroup(int atomicNumber) {
        return this.readElem(atomicNumber).getGroup();
    }

    /**
     * @param atomicNumber atomic number of atom
     * @return State associated with the atom at atomicNumber
     */
    public int getState(int atomicNumber) {
        return this.readElem(atomicNumber).getState();
    }

    /**
     * @param atomicNumber atomic number of atom
     * @return Electronegativity associated with the atom at atomicNumber
     */
    public double geteNegativity(int atomicNumber) {
        return this.readElem(atomicNumber).geteNegativity();
    }
}
