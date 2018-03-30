package GUI;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;
import structures.Atom;
import structures.Bond;
import structures.Molecule;

import java.util.ArrayList;
import java.util.Stack;

public class MoleView extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        Group group = new Group();
        Molecule mole = new Molecule("H_{2}_O", "Water");
        ArrayList<Bond> bonds = new ArrayList<>();
        Stack <Atom> atoms = new Stack<>();
        Atom cen = mole.getCenter();
        atoms.push(cen);
        while (!atoms.isEmpty()) {
            Atom atom = atoms.pop();
            Sphere elem = new Sphere(50);
            group.getChildren().add(elem);
            ArrayList<Bond> aBonds = atom.getAttachedBonds();
            if (aBonds.size() == 2) {

            }

        }

    }
}
