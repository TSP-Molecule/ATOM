package structures;

import structures.enums.Elem;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Emily Anible
 * CS3141, Spring 2018, Team ATOM
 * <p>
 * Parses and stores the electron configuration for each element in Elem
 * so that we can obtain some useful information about it.
 */
public class ElectronConfig {

    /**
     * Electron Config string, unparsed
     */
    private final String eConfigString;

    /**
     * Electron config, parsed
     */
    private ArrayList<String> eConfig;

    /**
     * Noble gas head of the electron config
     */
    private Elem nobleHead; // uwu

    ElectronConfig(String eConfigString) {
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

    /**
     * @param eConfigString Unparsed config string
     * @return split ArrayList containing the components of the eConfig.
     */
    private ArrayList<String> initConfig(String eConfigString) {
        return new ArrayList<>(Arrays.asList(eConfigString.split("-")));
    }

    /**
     * @return eConfigString
     */
    public String geteConfigString() {
        return eConfigString;
    }

    /**
     * @return nobleHead
     */
    public Elem getNobleHead() {
        return nobleHead;
    }

    /**
     * @return eConfig
     */
    public ArrayList<String> getConfig() {
        return eConfig;
    }
}
