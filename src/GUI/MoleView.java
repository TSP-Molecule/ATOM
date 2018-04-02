package GUI;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Point3D;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
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
       //System.out.println("mol: " + mole.getAtoms());

        // push center to the stack
        // pop center and store in atomMap -> Atom, visited
        // draw all the bonds
        // push attached atoms
        // pop the next atom and continue
        Point3D axis = Rotate.Z_AXIS;
        atoms.push(new AtomNode(cen, 0, 0, 0, null, Rotate.Z_AXIS));

//        double x = 0;
//        double y = 0;
//        double z = 0;

        double atomTheta = 0;
        double bondTheta = 0;

        while (!atoms.isEmpty()) {
            AtomNode atomNode = atoms.pop();
            Atom atom = atomNode.getAtom();
            if (!atom.equals(cen)) {
                //angle = 90;
                if (atomNode.getParent().getX() - atomNode.getX() < 1 ) {
                    System.out.println("par: " + atomNode.getParent().getX());
                    System.out.println("atom: " + atomNode.getX());
                    atomTheta = 270;
                   // angle = 60;
                } else {
                   // atomTheta = 180;
                }
                //atomTheta = 180;
//                if (atomNode.getParent().getAxis()== Rotate.Z_AXIS) {
//                    axis = Rotate.X_AXIS;
//                }
            }
//            axis = atomNode.getAxis();
           // System.out.println("bon: " + atom.getAttachedAtoms());
            if (atom.equals(cen)) {
                drawAtom(atom, 0, 0, 0, group);
                System.out.println(atom.getElement());
            } else {
                drawAtom(atom, atomNode.getX(), atomNode.getY(), atomNode.getZ(), group);
//                ArrayList<Bond> attache = atom.getAttachedBonds();
//                Bond same = null;
//                for (int i = 0; i < attache.size(); i++) {
//                    Bond bobo = attache.get(i);
//                    Atom par = atomNode.getParent().getAtom();
//                    if (bobo.getAtoms().get(0).equals(par) || bobo.getAtoms().get(1).equals(par)) {
//                        angle = bobo.getBondingAngle();
//                        System.out.println("angle " + angle);
//                    }
//                }
               // BondNode bon  = atom.getAttachedBonds();
            }
           // System.out.println("atom: " + atom);
            atomMap.put(atom, atom);
            int numKids = atom.getAttachedAtoms().size();

           // System.out.println("kids: " + atom.getAttachedAtoms());
          //  System.out.println(mole);
          //  System.out.println("numK; " + numKids);

            // Draw the bonds
            for (int i = 0; i < numKids; i++) {
                Bond b = atom.getAttachedBonds().get(i);
                atomTheta = i * toRadians(angle);
                double x = 0;
                double y = 0;
                double z = 0;
//                if (atom.equals(cen)) {
//                     x = (200 + BOND_LENGTH) / 2 * Math.cos(atomTheta ) + atomNode.getX();
//                     y = (200 + BOND_LENGTH) / 2 * Math.sin(atomTheta ) + atomNode.getY();
//                     z = 0;
//                } else {

                     x = BOND_LENGTH / 2 * Math.cos(atomTheta) + atomNode.getX();
                     y = BOND_LENGTH / 2 * Math.sin(atomTheta) + atomNode.getY();
                     z = 0;
//                }
//                if (axis == Rotate.X_AXIS) {
//                    z = x;
//                    x = atomNode.getX();
//                }
                //  bondTheta += angle;

                if (bondMap.get(b) == null) {

                    drawBond(b, x, y, z, angle * i + 90, axis, group);
                    bondMap.put(b, b);
                }

            }

            // Add the kids
//            if (axis == Rotate.Z_AXIS) {
//                System.out.println(atomNode.getAxis());
//                axis = Rotate.X_AXIS;
//            } else if (axis == Rotate.X_AXIS) {
//                axis = Rotate.Z_AXIS;
//            }
            if (!atom.equals(cen)) {
                atomTheta = angle + 360/numKids;
            }
            for (int i = 0; i < numKids; i++) {
                atomTheta = i * toRadians(angle);
//                if (atomTheta == 0) {
//                    atomTheta = 45;
//                }
                double x = BOND_LENGTH * Math.cos(atomTheta) + atomNode.getX();
//                if (atomNode.getX() - x < 0) {
//                    x = atomNode.getX() + Math.abs(x);
//                }
                double y = BOND_LENGTH * Math.sin(atomTheta) + atomNode.getY();
                double z = 0;
                //System.out.println()
                Atom next = atom.getAttachedAtoms().get(i);
               // System.out.println("kid " + i + ": " + atomMap.get(next));
                if (atomMap.get(next) == null) {
                  //  System.out.println("new node");
                    if (axis == Rotate.Z_AXIS)
                        atoms.push(new AtomNode(next, x, y, z, atomNode, Rotate.X_AXIS));
                    else if (axis == Rotate.X_AXIS)
                        atoms.push(new AtomNode(next, x, y, x, atomNode, Rotate.Z_AXIS));
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
        //System.out.println("blah");
        group.setTranslateX(300);
        group.setTranslateY(300);
        //group.setTranslateZ(200);
        Scene scene = new Scene(group, 600, 600, true);
        scene.setFill(Color.ANTIQUEWHITE);



        Camera cam = new PerspectiveCamera(true);
        cam.setNearClip(0.1);
        cam.setFarClip(100000);
        cam.getTransforms().add(new Translate(0, 0, -1500));
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

            double dist = 50;
            double theta = 10;

            @Override
            public void handle(KeyEvent event) {
               // scene1.requestFocus();

                // rotate commands
                if (event.getCode() == KeyCode.L) {
                    group.getTransforms().add(new Rotate(theta, 0, 0, -100, Rotate.Y_AXIS));
//                    if (!scene1.isFocused()) {
//                        scene1.requestFocus();
//                    }
                    //subf = true;
                } else if (event.getCode() == KeyCode.J) {
                    group.getTransforms().add(new Rotate(-theta, 0, 0, 0, Rotate.Y_AXIS));
                } else if (event.getCode() == KeyCode.I) {
                    group.getTransforms().add(new Rotate(1, 0, 0, 0, Rotate.X_AXIS));
                } else if (event.getCode() == KeyCode.K) {
                    group.getTransforms().add(new Rotate(-1, 0, 0, 0, Rotate.X_AXIS));
                } else if (event.getCode() == KeyCode.Y) {
                    group.getTransforms().add(new Rotate(1, 0, 0, 0, Rotate.Z_AXIS));
                } else if (event.getCode() == KeyCode.H) {
                    group.getTransforms().add(new Rotate(-1, 0, 0, 0, Rotate.Z_AXIS));
                } else
                    // translate commands
                    if (event.getCode() == KeyCode.W) {
                        cam.getTransforms().add(new Translate(0, 0, dist));
                    } else if (event.getCode() == KeyCode.S) {
                        cam.getTransforms().add(new Translate(0, 0, -dist));
                    } else if (event.getCode() == KeyCode.A) {
                        cam.getTransforms().add(new Translate(-dist, 0, 0));
                    } else if (event.getCode() == KeyCode.D) {
                        cam.getTransforms().add(new Translate(dist, 0, 0));
                    } else if (event.getCode() == KeyCode.R) {
                        cam.getTransforms().add(new Translate(0, -dist, 0));
                    } else if (event.getCode() == KeyCode.F) {
                        cam.getTransforms().add(new Translate(0, dist, 0));
                    }
                //scene.requestFocus();
            }
        });
        scene.setCamera(cam);


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

    private void drawBond(Bond b, double x, double y, double z, double theta, Point3D axis, Group g) {
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
        cylinder.setRotationAxis(axis);
        cylinder.setRotate(theta);

        g.getChildren().add(cylinder);
    }
}
