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
import structures.enums.BondOrder;
import structures.enums.Elem;
import structures.enums.Geometry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

// crl alt L format file
// ctrl alt O optimize imports


public class MoleculeView extends Group {

    private final double ATOM_RADIUS = 50;
    private final double BOND_RADIUS = 10;
    private final double BOND_LENGTH = 200;

    public MoleculeView(Molecule molecule) {
       drawPic(molecule);
    }

    /**
     * Draws a bond between two atoms.
     * Uses code from an external source, noted with begin external code and end external code.
     * The source can be found at: http://netzwerg.ch/blog/2015/03/22/javafx-3d-line/
     * @param origin
     * @param target
     */
    private void drawBond(Point3D origin, Point3D target, Color color) {

        // Begin external code
        Point3D yAxis = new Point3D(0, 1, 0);
        Point3D diff = target.subtract(origin);
        double height = diff.magnitude();

        Point3D mid = target.midpoint(origin);
        Translate moveToMidpoint = new Translate(mid.getX(), mid.getY(), mid.getZ());

        Point3D axisOfRotation = diff.crossProduct(yAxis);
        double angle = Math.acos(diff.normalize().dotProduct(yAxis));
        Rotate rotateAroundCenter = new Rotate(-Math.toDegrees(angle), axisOfRotation);

        Cylinder bond = new Cylinder(10, height);
        bond.getTransforms().addAll(moveToMidpoint, rotateAroundCenter);
       // End external code

        PhongMaterial bondMaterial = new PhongMaterial();
        bondMaterial.setSpecularColor(Color.RED);
        bondMaterial.setDiffuseColor(color);
        bond.setMaterial(bondMaterial);
        getChildren().add(bond);
    }



    private class Atomus {
        double x;
        double y;
        double z;

        Point3D loc;
        Atomus par;
        ArrayList<Atom> kids = new ArrayList<>();
        ArrayList<Atom> sibs = new ArrayList<>();
        Atom atom;

        public Atomus(Atom atom, double x, double y, double z, Atomus par) {
            this.atom = atom;
            this.x = x;
            this.y = y;
            this.z = z;
            this.par = par;
            fillKids();
            setCoordinates(x, y, z);
        }

        public void setCoordinates(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
            loc = new Point3D(x, y, z);
        }

        public void fillKids() {
            if (par == null) {
                kids = atom.getAttachedAtoms();
            } else {
                for (Atom a: atom.getAttachedAtoms()) {
                    if (!par.atom.equals(a)) {
                        kids.add(a);
                    }
                }
            }
        }

    }


    public void drawPic(Molecule mole) {
        Atom cen = mole.getCenter();
        Atomus adam = new Atomus(cen, 0, 0, 0, null);

        Stack<Atomus> stack = new Stack<>();
        HashMap<Atomus, Boolean> drawn = new HashMap<>();
        HashMap<Atomus, Boolean> visited = new HashMap<>();

        stack.push(adam);
        int counter = 0;
        while(!stack.isEmpty() && counter < mole.getAtoms().size() * 10) {
            System.err.println(stack.size());
            counter++;
            Atomus node = stack.pop();
            if (drawn.get(node) == null) {
                drawAtom(node.atom, node.x, node.y, node.z);
            }
            if (visited.get(node) != null) {
                continue;
            }
            Geometry geo = node.atom.getGeometry();
            if (geo == null) {
                continue;
            }
            switch (geo) {
                case Tetrahedral:
                    tetra(node, stack, drawn);
                    break;
                case TrigonalPlanar:
                    trig(node, stack, drawn);
                    break;
                case Bent:
                    bent(node, stack, drawn);
                    break;
                default:
                    System.out.println("hi im a " + geo);
            }
        }
    }

    private void tetra(Atomus node, Stack<Atomus> stack, HashMap<Atomus, Boolean> drawn) {
        Group group = new Group();
        double rad = 200;
        double thetaZY = 0;//s
        double thetaXY = 0;//t
        double angY = 60;
        double angZ = 120;

        boolean big = false;
        int draw = 1;

        if (node.par != null) {
            Point3D deltaPar = node.par.loc.subtract(node.loc);
            if (deltaPar.getZ() > 0) {
                draw = -1;
            }
        }
        int j = 0;

        Point3D origin = node.loc;
        if (node.par == null) {
            j = 1;
            Atom atom = node.kids.get(0);
            double x = node.x - rad;
            double y = node.y;
            double z = node.z;
            drawAtom(node.kids.get(0), node.x - rad, node.y, node.z);
            Point3D curr = new Point3D(x, y, z);
            Atomus atomus = new Atomus(atom, x, y, z, node);
            drawn.put(atomus, true);
            stack.push(atomus);
//            Bond bond = null;
//            for (Bond b: atom.getAttachedBonds()) {
//                if (b.getAtoms().get(0).equals(atom) && b.getAtoms().get(1).equals(node.atom)) {
//                    bond = b;
//                } else if (b.getAtoms().get(1).equals(atom) && b.getAtoms().get(0).equals(node.atom)) {
//                    bond = b;
//                } else {
//                    throw new RuntimeException("no bond!");
//                }
//            }
//            Color color = Color.YELLOW;
//            if (bond.getOrder() == BondOrder.SINGLE) {
//                color = Color.GRAY;
//            } else if (bond.getOrder() == BondOrder.DOUBLE) {
//                color = Color.BLACK;
//            } else if (bond.getOrder() == BondOrder.TRIPLE) {
//                color = Color.PURPLE;
//            }
            drawBond(origin, curr, Color.GRAY);

        }

        double x = 0;
        double y = 0;
        double z = 0;

        for (int i = j; i < node.kids.size(); i++) {
            Atom atom = node.kids.get(i);
            x = node.x + rad * Math.cos(Math.toRadians(thetaZY)) * Math.sin(Math.toRadians(thetaXY));
            y = node.y + rad * Math.sin(Math.toRadians(thetaZY)) * Math.sin(Math.toRadians(thetaXY));
            z = node.z + draw * rad * Math.cos(Math.toRadians(thetaXY));
           // System.out.printf("bee:  (%f, %f, %f) \n", x, y, z);
            thetaZY += angY;
            thetaXY += angZ;
            Point3D curr = new Point3D(x, y, z);
            Atomus atomus = new Atomus(atom, x, y, z, node);

            if (drawn.get(atomus) == null) {
                drawAtom(atom, x, y, z);
                drawn.put(atomus, true);
                stack.push(atomus);
            }
            Bond bond = null;
            for (Bond b: atom.getAttachedBonds()) {
                if (b.getAtoms().get(0).equals(atom) && b.getAtoms().get(1).equals(node.atom)) {
                    bond = b;
                } else if (b.getAtoms().get(1).equals(atom) && b.getAtoms().get(0).equals(node.atom)) {
                    bond = b;
                } else {
                   throw new RuntimeException("no bond!");
                }
            }
            Color color = Color.YELLOW;
            if (bond.getOrder() == BondOrder.SINGLE) {
                 color = Color.GRAY;
            } else if (bond.getOrder() == BondOrder.DOUBLE) {
                 color = Color.BLACK;
            } else if (bond.getOrder() == BondOrder.TRIPLE) {
                 color = Color.PURPLE;
            }

            drawBond(origin, curr, color);

        }
    }


    private void trig(Atomus node, Stack<Atomus> stack, HashMap<Atomus, Boolean> drawn) {
        double rad = 200;
        double thetaZY = 0;//s
        double thetaXY = 0;//t
        double angY = 0;
        double angZ = 120;

        double x = 0;
        double y = 0;
        double z = 0;
        Point3D origin = node.loc;// new Point3D(0, 0, 0);
//         drawAtom(new Atom(Elem.Carbon), x, y, z);
        System.out.println("trig!");
        for (int i = 0; i < node.kids.size(); i++) {
            Atom atom = node.kids.get(i);
            x = rad * Math.cos(Math.toRadians(thetaZY)) * Math.sin(Math.toRadians(thetaXY));
            y = rad * Math.sin(Math.toRadians(thetaZY)) * Math.sin(Math.toRadians(thetaXY));
            z = rad * Math.cos(Math.toRadians(thetaXY));
            // System.out.printf("bee:  (%f, %f, %f) \n", x, y, z);
            thetaZY += angY;
            thetaXY += angZ;
            Point3D curr = new Point3D(x, y, z);
            Atomus atomus = new Atomus(atom, x, y, z, node);
            drawAtom(atom, x, y, z);
            if (drawn.get(atomus) == null) {
                drawn.put(atomus, true);
                stack.push(atomus);
            }
           // getChildren().add(createConnection(origin, curr));
//            Bond bond = null;
//            for (Bond b: atom.getAttachedBonds()) {
//                if (b.getAtoms().get(0).equals(atom) && b.getAtoms().get(1).equals(node.atom)) {
//                    bond = b;
//                } else if (b.getAtoms().get(1).equals(atom) && b.getAtoms().get(0).equals(node.atom)) {
//                    bond = b;
//                } else {
//                    throw new RuntimeException("no bond!");
//                }
//            }
//            Color color = Color.YELLOW;
//            if (bond.getOrder() == BondOrder.SINGLE) {
//                color = Color.GRAY;
//            } else if (bond.getOrder() == BondOrder.DOUBLE) {
//                color = Color.BLACK;
//            } else if (bond.getOrder() == BondOrder.TRIPLE) {
//                color = Color.PURPLE;
//            }

            drawBond(origin, curr, Color.GRAY);
        }
    }


    private void bent(Atomus node, Stack<Atomus> stack, HashMap<Atomus, Boolean> drawn) {
        double rad = 200;
        double thetaZY = 0;//s
        double thetaXY = 0;//t
        double angY = 0;
        double angZ = 120;

        double x = 0;
        double y = 0;
        double z = 0;
        Point3D origin = node.loc;// new Point3D(0, 0, 0);
        int draw = 1;
        if (node.par != null) {
            Point3D deltaPar = node.par.loc.subtract(node.loc);
            if (deltaPar.getZ() > 0) {
                draw = -1;
            }
//            thetaZY = node.par.loc.angle(node.loc);
            System.out.println("parent: " + deltaPar);
//            yAxis = node.par.loc;

        }
        // drawAtom(new Atom(Elem.Carbon), x, y, z);
        for (int i = 0; i < node.kids.size(); i++) {
            Atom atom = node.kids.get(i);
            x = rad * Math.cos(Math.toRadians(thetaZY)) * Math.sin(Math.toRadians(thetaXY));
            y = rad * Math.sin(Math.toRadians(thetaZY)) * Math.sin(Math.toRadians(thetaXY));
            z = draw * rad * Math.cos(Math.toRadians(thetaXY));
            // System.out.printf("bee:  (%f, %f, %f) \n", x, y, z);
            thetaZY += angY;
            thetaXY += angZ;
            Point3D curr = new Point3D(x, y, z);
            Atomus atomus = new Atomus(atom, x, y, z, node);
            drawAtom(atom, x, y, z);
            drawn.put(atomus, true);
            stack.push(atomus);
           // getChildren().add(createConnection(origin, curr));
//            Bond bond = null;
//            for (Bond b: atom.getAttachedBonds()) {
//                if (b.getAtoms().get(0).equals(atom) && b.getAtoms().get(1).equals(node.atom)) {
//                    bond = b;
//                } else if (b.getAtoms().get(1).equals(atom) && b.getAtoms().get(0).equals(node.atom)) {
//                    bond = b;
//                } else {
//                    throw new RuntimeException("no bond!");
//                }
//            }
//            Color color = Color.YELLOW;
//            if (bond.getOrder() == BondOrder.SINGLE) {
//                color = Color.GRAY;
//            } else if (bond.getOrder() == BondOrder.DOUBLE) {
//                color = Color.BLACK;
//            } else if (bond.getOrder() == BondOrder.TRIPLE) {
//                color = Color.PURPLE;
//            }
            drawBond(origin, curr, Color.GRAY);
        }
    }

    private double toDegrees(double inRadians) {
        return inRadians * 180 / Math.PI;
    }

    private double toRadians(double inDegrees) {
        return inDegrees * Math.PI / 180;
    }

    /**
     * Draws an atom with the proper color and location.
     *
     * @param atom
     * @param x
     * @param y
     * @param z
     */
    private void drawAtom(Atom atom, double x, double y, double z) {
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
