package structures;

import structures.enums.Elem;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Emily Anible
 * CS3141, Spring 2018, Team ATOM
 *
 * Parses and stores the electron configuration for each element in Elem
 * so that we can obtain some useful information about it.
 */
public class ElectronConfig {

    private final String eConfigString;
    private ArrayList<String> eConfig;

    private Elem nobleHead; // uwu

    public ElectronConfig(String eConfigString) {
        this.eConfigString = eConfigString;

        String noble = null;

        if (eConfigString.contains("[") && eConfigString.length() > 4) {
            noble = eConfigString.substring(1, 3);

            nobleHead = Elem.getBySymbol(noble);
            eConfig = initConfig(eConfigString.substring(5));
        } else {
            nobleHead = Elem.NULL;
            eConfig = initConfig(eConfigString);
        }
    }

    private ArrayList<String> initConfig(String eConfigString) {
        ArrayList<String> ret = new ArrayList<>();
        ret.addAll(Arrays.asList(eConfigString.split("-")));
        return ret;
    }

    public String geteConfigString() {
        return eConfigString;
    }

    public Elem getNobleHead() {
        return nobleHead;
    }

    public ArrayList<String> getConfig() {
        return eConfig;
    }
}
