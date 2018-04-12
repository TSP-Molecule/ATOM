package GUI;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.stage.Stage;
import structures.Atom;
import structures.Bond;
import structures.Molecule;

import java.util.ArrayList;
import java.util.HashMap;

public class LewisDotStructure extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

    }

    public static void main(String [] args) {
        launch(args);
    }

    public Group grump() {
        Group group = new Group();
        Molecule molecule = new Molecule("H{2}_O");
        Atom atom = molecule.getCenter();
        ArrayList<Atom> list = new ArrayList<>();
        double angle = 0;
        double theta = 0;
        int numBonds = molecule.getCenterGeometry().getBondedAtoms();
        HashMap<Atom, Atom> map = new HashMap<>();
        while(!list.isEmpty()) {
            list.remove(atom);
//            draw(atom);
            map.put(atom, atom);
            if (atom.equals(molecule.getCenter())) {
                angle = molecule.getCenterGeometry().getBondAngle();

            } else {
                angle = 90;
                ArrayList<Bond> bonds = atom.getAttachedBonds();
                // lib gen
                for (int i = 0; i < bonds.size(); i++) {
                    Bond b = bonds.get(i);
                    Atom one = b.getAtoms().get(0);
                    Atom two = b.getAtoms().get(1);
                    if (one.equals(atom)) {
//                        draw (b);
                        atom = two;
                    } else {
//                        draw(b);
                        atom = one;
                    }
                }
            }

        }
        return group;
    }
}
