package GUI;

import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import structures.Atom;
import structures.Bond;
import structures.Molecule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class MoleculeView extends Group {

    private final double ATOM_RADIUS = 50;
    private final double BOND_RADIUS = 10;
    private final double BOND_LENGTH = 200;

    public MoleculeView(Molecule molecule) {
       makeMole(molecule);
    }

   private Group makeMole(Molecule mole) {
        Group group = new Group();
       // Molecule mole = new Molecule("C_{3}_H_{6}_O", "acetone");//Acetone 10 : c3 h6 O
        // DFS traversal

        ArrayList<Bond> bonds = new ArrayList<>();
        Stack<AtomNode> atoms = new Stack<>();
        HashMap<Atom, Atom> atomMap = new HashMap<>();
        HashMap<Bond, Bond> bondMap = new HashMap<>();

        Atom cen = mole.getCenter();
        double angle = mole.getCenterGeometry().getBondAngle();
        System.out.println("mol: " + mole.getAtoms());

        // push center to the stack
        // pop center and store in atomMap -> Atom, visited
        // draw all the bonds
        // push attached atoms
        // pop the next atom and continue

        atoms.push(new AtomNode(cen, 0, 0, 0, null));


        double atomTheta = 0;
        double bondTheta = 0;

        while (!atoms.isEmpty()) {
            AtomNode atomNode = atoms.pop();
            Atom atom = atomNode.getAtom();
            System.out.println("bon: " + atom.getAttachedAtoms());
            if (atom.equals(cen)) {
                drawAtom(atom, 0, 0, 0, group);
            } else {
                drawAtom(atom, atomNode.getX(), atomNode.getY(), atomNode.getZ(), group);

            }
            System.out.println("atom: " + atom);
            atomMap.put(atom, atom);
            int numKids = atom.getAttachedAtoms().size();
            System.out.println("kids: " + atom.getAttachedAtoms());
            System.out.println(mole);
            System.out.println("numK; " + numKids);

            for (int i = 0; i < numKids; i++) {
                atomTheta = i * toRadians(angle);
                double x = BOND_LENGTH * Math.cos(atomTheta) + atomNode.getX();
                double y = BOND_LENGTH * Math.sin(atomTheta) + atomNode.getY();
                double z = 0;
                //System.out.println()
                Atom next = atom.getAttachedAtoms().get(i);
                System.out.println("kid " + i + ": " + atomMap.get(next));
                if (atomMap.get(next) == null) {
                    System.out.println("new node");
                    atoms.push(new AtomNode(next, x, y, z, atomNode));
                }
            }

            for (int i = 0; i < numKids; i++) {
                Bond b = atom.getAttachedBonds().get(i);
                atomTheta = i * toRadians(angle);
                double x = BOND_LENGTH / 2 * Math.cos(atomTheta ) + atomNode.getX();
                double y = BOND_LENGTH / 2 * Math.sin(atomTheta ) + atomNode.getY();
                double z = 0;
                //  bondTheta += angle;
                if (bondMap.get(b) == null) {
                    drawBond(b,x, y, z, angle * i + 90, group);
                    bondMap.put(b, b);
                }

            }


        }
        return group;
    }

    private double toDegrees(double inRadians) {
        return inRadians * 180 / Math.PI;
    }

    private double toRadians (double inDegrees) {
        return inDegrees * Math.PI / 180;
    }

    private void drawBond(Bond b, double x, double y, double z, double theta, Group g) {
        Color color = Color.LIGHTGREY;
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(color);
        material.setSpecularColor(color);

        Cylinder cylinder = new Cylinder (BOND_RADIUS, BOND_LENGTH);
        cylinder.setMaterial(material);

        cylinder.setTranslateX(x);
        cylinder.setTranslateY(y);
        cylinder.setTranslateZ(z);

//        cylinder.getTransforms().add(new Rotate(theta, x, y, z, Rotate.Z_AXIS ));
        cylinder.setRotationAxis(Rotate.Z_AXIS);
        cylinder.setRotate(theta);

        g.getChildren().add(cylinder);
    }

    /**
     * Draws an atom with the proper color and location.
     * @param atom
     * @param x
     * @param y
     * @param z
     */
    private void drawAtom(Atom atom, double x, double y, double z, Group g) {
        Color color = atom.getElement().getColor();
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(color);
        material.setSpecularColor(color);

        Sphere sphere = new Sphere (ATOM_RADIUS);
        sphere.setMaterial(material);

        sphere.setTranslateX(x);
        sphere.setTranslateY(y);
        sphere.setTranslateZ(z);

        g.getChildren().add(sphere);
    }
}
