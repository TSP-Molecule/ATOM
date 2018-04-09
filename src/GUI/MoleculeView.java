package GUI;

import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import structures.Atom;
import structures.Bond;
import structures.Molecule;
import structures.enums.Geometry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import static structures.enums.Geometry.*;


public class MoleculeView extends Group {

    private final double ATOM_RADIUS = 50;
    private final double BOND_RADIUS = 10;
    private final double BOND_LENGTH = 200;

    public MoleculeView(Molecule molecule) {
        gugu(molecule);
    }

    private Group gugu(Molecule mole) {
        Group group = new Group();
        Atom center = mole.getCenter();
        HashMap<Atom, AtomNode> atoms = new HashMap<>();
        HashMap<Bond, BondNode> bonds = new HashMap<>();

        // Initialize the atoms and bonds to have no nodes
        for (Atom atom : mole.getAtoms()) {
            System.out.println("atom: " + atom);
            atoms.put(atom, null);
        }
        System.out.println(atoms);
//        for (int i = 0; i < mole.getAtoms().size(); i++) {
//            Atom m = mole.getAtoms().get(i);
//            if (atoms.get(m) == null) {
//                atoms.put(mole.getAtoms().get(i), i);
//            }
//        }
        System.out.println(atoms);
//        for (Bond bond : mole.getBonds()) {
//            bonds.put(bond, null);
//        }

        // Iterate over the molecule via the atoms and a stack
        AtomNode first = new AtomNode(mole.getCenter(), 0, 0, 0, null, Rotate.Z_AXIS);
        Stack<AtomNode> stack = new Stack<>();
        stack.push(first);
      //  System.out.println("Red roses");
        while (!stack.isEmpty()) {
           // System.out.println("blubber");
            AtomNode node = stack.pop();
            Atom atom = node.getAtom();
            Geometry geo = atom.getGeometry();
            int kids = atom.getAttachedAtoms().size();
            double ang = 360.0 / kids;

            if (atoms.get(atom) == null) {
                System.out.println("al: " + atom);
                // System.out.println("Blue baskets of brouhaha");
                drawAtom(atom, 0, 0, 0, group);
                atoms.put(atom, node);
                if (geo != null) {
                    switch (geo) {
                        case Tetrahedral:
                            System.out.println("boo");
                            drawTetrahedralGeo(node, atoms, bonds, stack, group);
                            break;
                        default:
                            System.out.println("blueee blueee");
                            drawBasic(node, atoms, bonds, stack, group);
                    }

                }
            } else {
                System.out.println(atoms.get(atom));
            }
        }
        System.out.println("bonds size: " + bonds.size());


//        HashMap<AtomNode, Boolean> atomMap = new HashMap<>();
//        HashMap<BondNode, Boolean> bondMap = new HashMap<>();
        // HashMap<Atom, >
//        for (Atom a: mole.getAtoms()) {
//            atomMap.put(new AtomNode(atom, x, y, z, parent, axis));
//        }
        return group;

        // draw bonds, draw atoms

    }

    private void drawBasic(AtomNode node, HashMap<Atom, AtomNode> atoms, HashMap<Bond, BondNode> bonds, Stack<AtomNode> stack, Group group) {
        Atom atom = node.getAtom();
        int numKids = atom.getAttachedBonds().size();
       // double atomTheta = 0;
        double angle = 360.0 / numKids;
        for (int i = 0; i < numKids; i++) {
            Bond b = atom.getAttachedBonds().get(i);
            double  atomTheta = i * toRadians(angle);
            double x = BOND_LENGTH * Math.cos(atomTheta) + node.getX();
            double y = BOND_LENGTH * Math.sin(atomTheta) + node.getY();
            double z = 0;
            Atom kid = atom.getAttachedAtoms().get(i);
            AtomNode child = new AtomNode(kid, x, y, z, node, Rotate.Z_AXIS);
            if (atoms.get(kid) == null) {
                drawAtom(kid, child.getX(), child.getY(), child.getZ(), group);
               // atoms.put(kid, child);
                stack.push(child);
            }
            if (bonds.get(b) == null) {
                drawBond(b, x/2, y/2, z/2, angle * i + 90, group);
                bonds.put(b, new BondNode(b, 0, 0, 0, 0));
            }

        }
    }

    private void drawTetrahedralGeo(AtomNode node, HashMap<Atom, AtomNode> atoms, HashMap<Bond, BondNode> bonds, Stack<AtomNode> stack, Group group) {
        AtomNode par = node.getParent();
        Point3D axis = node.getAxis();
        Point3D parent = new Point3D(par.getX(), par.getY(), par.getZ());
        Atom atom = node.getAtom();
        ArrayList<Atom> kids = atom.getAttachedAtoms();
        ArrayList<Bond> bonder = atom.getAttachedBonds();
        Point3D origin = new Point3D(node.getX(), node.getY(), node.getZ());
        double bondLen = 200;
        double angle = 120;
        double theta = 0;
        double in = 0;
        System.out.println("an: " + (node.getX() - par.getX()));
        if (node.getX() - par.getX() > 0) {
            theta = 0;
            in = 1;
        }else{
            theta = 270;
            in = -1;
        }
        for (int i = 1; i < 4; i++) {
            double z = bondLen * Math.cos(toRadians(theta + i * angle ));
            double y = bondLen * Math.sin(toRadians(theta + i * angle ));
            double x = bondLen * in;
            Point3D curr = new Point3D(x, y, z);
            System.out.println("blue" + curr);
            Bond b = bonder.get(i);
            if (bonds.get(b) == null) {
                bonds.put(bonder.get(i), new BondNode(bonder.get(i), 0, 0, 0, 0));
                getChildren().add(createConnection(origin, curr));
            }
            System.out.println(atom.getAttachedAtoms());

            //  System.out.println("angle: " + origin.angle(xAxis, curr));
            Atom kid = kids.get(i);
            //Atom kid = atom.getAttachedAtoms().get(i);
            AtomNode child = new AtomNode(kid, x, y, z, node, axis.crossProduct(parent));
            if (atoms.get(kid) == null) {
                drawAtom(kid, x, y, z, group);
                atoms.put(kid, new AtomNode(atom, 0, 0, 0, node, Rotate.Z_AXIS));
                stack.push(child);
//                Sphere hy = new Sphere(50);
//                hy.setMaterial(new PhongMaterial(kid.getElement().getColor()));
//                hy.getTransforms().add(new Translate(x, y, z));
                // System.out.println(x + ", " + y + ", " + z);
                //group.getChildren().add(hy);
            }
        }
//        Sphere sphere = new Sphere(50);
//        sphere.setMaterial(new PhongMaterial(Color.RED));
//        group.getChildren().add(sphere);

    }

    // start From online
    public Cylinder createConnection(Point3D origin, Point3D target) {
        Point3D yAxis = new Point3D(0, 1, 0);
        Point3D diff = target.subtract(origin);
        double height = diff.magnitude();

        Point3D mid = target.midpoint(origin);
        Translate moveToMidpoint = new Translate(mid.getX(), mid.getY(), mid.getZ());

        Point3D axisOfRotation = diff.crossProduct(yAxis);
        double angle = Math.acos(diff.normalize().dotProduct(yAxis));
        Rotate rotateAroundCenter = new Rotate(-Math.toDegrees(angle), axisOfRotation);

        Cylinder line = new Cylinder(35, height);
        PhongMaterial phony = new PhongMaterial();
        phony.setSpecularColor(Color.GRAY);
        phony.setDiffuseColor(Color.GRAY);
        line.setMaterial(phony);

        line.getTransforms().addAll(moveToMidpoint, rotateAroundCenter);

        return line;
    }  // end from online

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

        atoms.push(new AtomNode(cen, 0, 0, 0, null, Rotate.Z_AXIS));


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
//            System.out.println("atom: " + atom);
            atomMap.put(atom, atom);
            int numKids = atom.getAttachedAtoms().size();
//            System.out.println("kids: " + atom.getAttachedAtoms());
//            System.out.println(mole);
//            System.out.println("numK; " + numKids);

            for (int i = 0; i < numKids; i++) {
                atomTheta = i * toRadians(angle);
                double x = BOND_LENGTH * Math.cos(atomTheta) + atomNode.getX();
                double y = BOND_LENGTH * Math.sin(atomTheta) + atomNode.getY();
                double z = 0;
                //System.out.println()
                Atom next = atom.getAttachedAtoms().get(i);
                System.out.println("kid " + i + ": " + atomMap.get(next));

                if (atomMap.get(next) == null) {
//                    System.out.println("new node");
                    if (!atom.getGeometry().getName().equals("Tetrahedral")) {
                        atoms.push(new AtomNode(next, x, y, z, atomNode, Rotate.Z_AXIS));
                    } else {

                    }
                }
            }

            for (int i = 0; i < numKids; i++) {
                Bond b = atom.getAttachedBonds().get(i);
                atomTheta = i * toRadians(angle);
                double x = BOND_LENGTH / 2 * Math.cos(atomTheta) + atomNode.getX();
                double y = BOND_LENGTH / 2 * Math.sin(atomTheta) + atomNode.getY();
                double z = 0;
                //  bondTheta += angle;
                if (bondMap.get(b) == null) {
                    drawBond(b, x, y, z, angle * i + 90, group);
                    bondMap.put(b, b);
                }

            }


        }
        return group;
    }

    private double toDegrees(double inRadians) {
        return inRadians * 180 / Math.PI;
    }

    private double toRadians(double inDegrees) {
        return inDegrees * Math.PI / 180;
    }

    private void drawBond(Bond b, double x, double y, double z, double theta, Group g) {
        Color color = Color.LIGHTGREY;
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(color);
        material.setSpecularColor(color);

        Cylinder cylinder = new Cylinder(BOND_RADIUS, BOND_LENGTH);
        cylinder.setMaterial(material);

        cylinder.setTranslateX(x);
        cylinder.setTranslateY(y);
        cylinder.setTranslateZ(z);

//        cylinder.getTransforms().add(new Rotate(theta, x, y, z, Rotate.Z_AXIS ));
        cylinder.setRotationAxis(Rotate.Z_AXIS);
        cylinder.setRotate(theta);

        getChildren().add(cylinder);
    }

    /**
     * Draws an atom with the proper color and location.
     *
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

        Sphere sphere = new Sphere(ATOM_RADIUS);
        sphere.setMaterial(material);

        sphere.setTranslateX(x);
        sphere.setTranslateY(y);
        sphere.setTranslateZ(z);

        getChildren().add(sphere);
    }
}
