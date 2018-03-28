package structures;

import java.io.*;

/**
 * Handles saving and loading of Molecules from file.
 *
 * @author Emily Anible
 * CS3141, Spring 2018, Team ATOM
 */
public class MolFile {

    /**
     * Saves a Molecule object to a user-specified file.
     *
     * @param mol      Molecule to save to file
     * @param filename Filename to save, without extension.
     * @return Molecule that was saved to file
     * @throws IOException If something goes horribly wrong.
     */
    public static Molecule saveMolecule(Molecule mol, String filename) throws IOException {
        FileOutputStream file = new FileOutputStream(filename.contains(".mol") ? filename : filename + ".mol");
        ObjectOutputStream oos = new ObjectOutputStream(file);
        oos.writeObject(mol);
        oos.close();

        return mol;
    }

    /**
     * Loads a Molecule object from a user-specified file.
     *
     * @param filename Filename to load from, with or without extension.
     * @return Molecule that was loaded from file
     * @throws IOException If something goes horribly wrong.
     * @throws ClassNotFoundException If something goes even more horribly wrong.
     */
    public static Molecule loadMolecule(String filename) throws IOException, ClassNotFoundException {
        FileInputStream file = new FileInputStream(filename.contains(".mol") ? filename : filename + ".mol");
        ObjectInputStream ois = new ObjectInputStream(file);

        Molecule mol = null;
        Object obj = ois.readObject();
        if (obj instanceof Molecule) {
            mol = (Molecule) obj;
        }
        ois.close();

        return mol;
    }
}
