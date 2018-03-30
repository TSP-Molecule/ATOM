package GUI;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import structures.Atom;
import structures.Bond;
import structures.Molecule;
import GUI.AtomNode;
import GUI.BondNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class MoleView extends Application {

    private final double ATOM_RADIUS = 50;
    private final double BOND_RADIUS = 10;
    private final double BOND_LENGTH = 200;

//    class Tree {
//
//    }
//
//    class TreeNode {
//        Atom atom = null;
//
//        // position
//        double x = 0;
//        double y = 0;
//        double z = 0;
//
//    }
//
//    class BondNode {
//        Bond bond = null;
//        double x = 0;
//        double y = 0;
//        double z = 0;
//        double ang = 0;
//
//        Atom one = null;
//        Atom two = null;
//
//        public BondNode(Bond b, double x, double y, double z, double ang) {
//            bond = b;
//            this.x = x;
//            this.y = y;
//            this.z = z;
//            this.ang = ang;
//
//            one = bond.getAtoms().get(0);
//            two = bond.getAtoms().get(1);
//
//        }
//
//        public double getX() {
//            return x;
//        }
//
//        public double getY() {
//            return y;
//        }
//
//        public double getZ() {
//            return z;
//        }
//
//        public double getAng() {
//            return ang;
//        }
//
//        public Atom getOne() {
//            return one;
//        }
//
//        public Atom getTwo() {
//            return two;
//        }
//
//    }
//
//    class AtomNode {
//        AtomNode parent = null;
//        Atom self = null;
//        double x = 0;
//        double y = 0;
//        double z = 0;
//
//        public AtomNode(Atom self, double x, double y, double z, AtomNode parent) {
//            this.x = x;
//            this.y = y;
//            this.z = z;
//            this.parent = parent;
//            this.self = self;
//        }
//
//
//        public double getX() {
//            return x;
//        }
//
//        public double getY() {
//            return y;
//        }
//
//        public double getZ() {
//            return z;
//        }
//
//        public AtomNode getParent() {
//            return parent;
//        }
//
//        public Atom getAtom() {
//            return self;
//        }
//
//    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group group = new Group();
        Molecule mole = new Molecule("C_{3}_H_{6}_O", "acetone");//Acetone 10 : c3 h6 O
        // DFS traversal

        ArrayList<Bond> bonds = new ArrayList<>();
        Stack <AtomNode> atoms = new Stack<>();
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

//        double x = 0;
//        double y = 0;
//        double z = 0;

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








//        atoms.push(cen);
//        double theta = 0;
//        double bondLen = 300;
//        double halfBondLen = bondLen / 2;
//        double bondRad = 10;
//        double currX = 0;
//        double currY = 0;
//        double ang = 0;
//        Atom prev = null;
//        while (!atoms.isEmpty()) {
//            if (prev != null) {
//                currX = bondLen * Math.cos(toRadians(ang + angle)) + currX;
//                currY = bondLen * Math.sin(toRadians(ang + angle)) + currY;
//            }
//            Atom atom = atoms.pop();
//            Sphere elem = new Sphere(50);
//            elem.setTranslateX(currX);
//            elem.setTranslateY(currY);
//            group.getChildren().add(elem);
//            map.put(atom, atom);
//            ArrayList<Bond> aBonds = atom.getAttachedBonds();
//            if (atom.equals(cen)) {
//                angle = mole.getCenterGeometry().getBondAngle();
//            } else {
//                angle = 90;
//            }
//            for (int i = 0; i < aBonds.size(); i++) {
//                Cylinder cyl = new Cylinder(bondRad, bondLen);
//                double x = Math.cos(toRadians(angle)) * halfBondLen + currX;
//                double y = Math.sin(toRadians(angle)) * halfBondLen + currY;
//                cyl.setTranslateX(x);
//                cyl.setTranslateY(y);
//                cyl.setTranslateY(0);
//                cyl.setRotationAxis(Rotate.Z_AXIS);
//                cyl.setRotate(theta + angle);
//                theta += angle;
//                group.getChildren().add(cyl);
//                bonds.add(aBonds.get(i));
//            }
//            for (int i = 0; i < aBonds.size(); i++) {
//                Bond b = aBonds.get(i);
//                Atom one = b.getAtoms().get(0);
//                Atom two = b.getAtoms().get(1);
//                if (one.equals(atom)) {
//                    if (map.get(two) == null)
//                    atoms.push(two);
//                } else {
//                    if (map.get(one) == null)
//                    atoms.push(one);
//                }
//            }
//            prev = atom;
//
//        }
        System.out.println("blah");
        group.setTranslateX(300);
        group.setTranslateY(300);
        Scene scene = new Scene(group, 600, 600, true);
        primaryStage.setScene(scene);
        primaryStage.show();


    }

    public static void main(String [] args) {
        launch(args);
    }


    private double toRadians (double inDegrees) {
        return inDegrees * Math.PI / 180;
    }

    private double toDegrees(double inRadians) {
        return inRadians * 180 / Math.PI;
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
}
