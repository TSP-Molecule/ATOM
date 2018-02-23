package structures;

import structures.enums.Elem;

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

    private ArrayList<HashMap<Integer, Atom>> atominomicon;


    /**
     * Generates a copy of the Atominomicon in memory.
     */
    public Atominomicon() throws IOException {

        atominomicon = new ArrayList<>();
        HashMap<Integer, Atom> atomMap = new HashMap<>();

        for (int i = 0; i < 119; i++) {
            Atom atom = new Atom ( Elem.get(i) );
            atomMap.put(0, atom);
            atominomicon.add(i, atomMap);
            System.out.println(atominomicon.get(i));
        }
    }

    /**
     * Reads Element from Atominomicon
     *
     * @param atomicNumber atomic number of element
     * @param isotope      isotope value
     * @return element corresponding to atomic number and isotope
     */
    public Elem readElem(int atomicNumber, int isotope) {
        return atominomicon.get(atomicNumber).get(isotope).getElement();
    }

    /**
     * Reads Element from Atominomicon
     *
     * @param atomicNumber atomic number of element
     * @return element corresponding to atomic number
     */
    public Elem readElem(int atomicNumber) {
        return atominomicon.get(atomicNumber).get(0).getElement();
    }

    /**
     * Reads Atom from Atominomicon
     *
     * @param atomicNumber atomic number of atom
     * @param isotope      isotope value
     * @return atom corresponding to atomic number and isotope
     */
    public Atom readAtom(int atomicNumber, int isotope) {
        return atominomicon.get(atomicNumber).get(isotope);
    }
}
