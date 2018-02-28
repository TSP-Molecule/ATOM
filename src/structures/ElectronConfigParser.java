package structures;

import structures.enums.Elem;

/**
 *  ElectronConfig Parser
 *      @author Emily Anible
 *      Parses the electron configuration stored for each element so that we can obtain some useful information about it.
 */
public class ElectronConfigParser {

    private final String electronConfiguration;

    private Elem nobleHead; // uwu

    public ElectronConfigParser(String electronConfiguration) {
        this.electronConfiguration = electronConfiguration;

        String noble = null;

        if (electronConfiguration.contains("[") && electronConfiguration.length() > 3){
            noble = electronConfiguration.substring(1, 3);

            nobleHead = Elem.getBySymbol(noble);

        }
    }

    public String getElectronConfiguration() {
        return electronConfiguration;
    }

    public Elem getNobleHead() {
        return nobleHead;
    }
}
