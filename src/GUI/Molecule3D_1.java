package GUI;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import structures.Atom;
import structures.Bond;
import structures.Molecule;

public class Molecule3D_1 extends Application {


    @Override
    public void start(Stage primaryStage) {
    //    Group mole = molecule();

    }

//    public Group molecule(Molecule m) {
//        Group molecule = new Group();
//        Atom center = m.getCenter();
//        for (Atom a: m.getAtoms()) {
//
//        }
//        Sphere e1 = new Sphere(100);
//        e1.setMaterial(new PhongMaterial(Color.web(center.getElement().getColor() + "")));
////        for (Bond b: center.getBonds()) {
////            Object [] bond = b.getBondedAtoms().toArray();
////            if (bond[0] == center) {
////
////            }
////            Cylinder c = new Cylinder(50, 100);
////            c.getTransforms().add(new Translate(50, 0, 0 ));
////            c.getTransforms().add(new Rotate(100, 50, 0, 0));
////        }
////        return molecule;
//    }



}
