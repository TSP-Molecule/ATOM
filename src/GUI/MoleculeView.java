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
import structures.enums.Geometry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

/**
 * Draws a 3D molecule as a group
 *
 * @author Sarah Larkin
 *
 * CS3141, R02, Spring 2018, Team ATOM
 * Date Last Modified:  April 15, 2018
 *
 */
public class MoleculeView extends Group {

    private final double ATOM_RADIUS = 50;
    private final double BOND_RADIUS = 10;

    public MoleculeView(Molecule molecule) {
        drawPic(molecule);
    }

    /**
     * Draws a bond between two atoms.
     * Uses code from an external source, noted with begin external code and end external code.
     * The source can be found at: http://netzwerg.ch/blog/2015/03/22/javafx-3d-line/
     *
     * @param origin the atom at one end of the bond
     * @param target the atom at the other end of the bond
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

        Cylinder bond = new Cylinder(BOND_RADIUS, height);
        bond.getTransforms().addAll(moveToMidpoint, rotateAroundCenter);
        // End external code

        PhongMaterial bondMaterial = new PhongMaterial();
        bondMaterial.setSpecularColor(color.brighter());
        bondMaterial.setDiffuseColor(color);
        bond.setMaterial(bondMaterial);
        getChildren().add(bond);
    }


    /**
     * Inner class provides a way to track the location of an atom and its attached atoms.
     */
    private class Atomus {
        double x;
        double y;
        double z;

        Point3D loc;
        Atomus par;
        ArrayList<Atom> kids = new ArrayList<>();
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
                for (Atom a : atom.getAttachedAtoms()) {
                    if (!par.atom.equals(a)) {
                        kids.add(a);
                    }
                }
            }
        }

    }

    /**
     * Draws the 3D image of the atom.
     *
     * @param mole
     */
    public void drawPic(Molecule mole) {
        Atom cen = mole.getCenter();
        Atomus adam = new Atomus(cen, 0, 0, 0, null);
        Stack<Atomus> stack = new Stack<>();
        HashMap<Atomus, Boolean> drawn = new HashMap<>();
        HashMap<Atomus, Boolean> visited = new HashMap<>();

        stack.push(adam);
        int counter = 0;

        // Perform a depth-first iteration of the molecule
        while (!stack.isEmpty() && counter < mole.getAtoms().size() * 10) {
            counter++;
            Atomus node = stack.pop();
            if (drawn.get(node) == null) {
                drawAtom(node.atom, node.x, node.y, node.z);
                drawn.put(node, true);
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
                case Linear:
                    trig(node, stack, drawn);
                default:
                    trig(node, stack, drawn);
                    //System.out.println("hi I'm a " + geo);
            }
        }
    }

    /**
     * Draws a tetrahedral configuration around the given atom node
     *
     * @param node  the node around which to draw the tetrahedron
     * @param stack the stack tracking which nodes still need to be visitec
     * @param drawn a map representing all nodes already drawn
     */
    private void tetra(Atomus node, Stack<Atomus> stack, HashMap<Atomus, Boolean> drawn) {
        double rad = 200;
        double thetaZY = 0;
        double thetaXY = 0;
        double angY = 60;
        double angZ = 120;

        int draw = 1;

        if (node.par != null) {
            Point3D deltaPar = node.par.loc.subtract(node.loc);
            if (deltaPar.getZ() > 0) {
                draw = -1;
            }
        }

        int j = 0;

        Point3D origin = node.loc;

        //  Draw the fourth node if it isn't already drawn
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
            Bond bond = null;

            for (Bond b : atom.getAttachedBonds()) {
                if (b.getAtoms().contains(atom)) {
                    bond = b;
                }
            }
            drawBond(origin, curr, setBondColor(bond));
        }


        for (int i = j; i < node.kids.size(); i++) {
            Atom atom = node.kids.get(i);
            double x = node.x + rad * Math.cos(Math.toRadians(thetaZY)) * Math.sin(Math.toRadians(thetaXY));
            double y = node.y + rad * Math.sin(Math.toRadians(thetaZY)) * Math.sin(Math.toRadians(thetaXY));
            double z = node.z + draw * rad * Math.cos(Math.toRadians(thetaXY));
            // System.out.printf("bee:  (%f, %f, %f) \n", x, y, z); // prints out the location of the atom
            thetaZY += angY;
            thetaXY += angZ;
            Point3D curr = new Point3D(x, y, z);
            Atomus atomus = new Atomus(atom, x, y, z, node);

            // Draw the attached atoms
            if (drawn.get(atomus) == null) {
                drawAtom(atom, x, y, z);
                drawn.put(atomus, true);
                stack.push(atomus);
            }

            // Draw the attached bonds
            Bond bond = null;
            for (Bond b : atom.getAttachedBonds()) {
                if (b.getAtoms().contains(atom)) {
                    bond = b;
                }
            }
            drawBond(origin, curr, setBondColor(bond));

        }
    }

    /**
     * Colour the bonds based on bond order
     *
     * @param bond the bond to be coloured
     * @return the colour of the bond
     */
    private Color setBondColor(Bond bond) {
        Color color = Color.YELLOW;
        if (bond.getOrder() == BondOrder.SINGLE) {
            color = Color.LIGHTGREY;
        } else if (bond.getOrder() == BondOrder.DOUBLE) {
            color = Color.rgb(21, 244, 238);
            ;
        } else if (bond.getOrder() == BondOrder.TRIPLE) {
            color = Color.PURPLE;
        }
        return color;
    }

    /**
     * Draws atoms around a central node in a trigonal pattern
     *
     * @param node  the node representing the atom around which to draw
     * @param stack the representation of nodes still to be traversed
     * @param drawn the representation of nodes already drawn
     */
    private void trig(Atomus node, Stack<Atomus> stack, HashMap<Atomus, Boolean> drawn) {
        double rad = 200;
        double thetaZY = 0;//s
        double thetaXY = 0;//t
        double angY = 0;
        double angZ = 120;

        double x = 0;
        double y = 0;
        double z = 0;
        Point3D origin = node.loc;
        for (int i = 0; i < node.kids.size(); i++) {
            Atom atom = node.kids.get(i);
            x = rad * Math.cos(Math.toRadians(thetaZY)) * Math.sin(Math.toRadians(thetaXY));
            y = rad * Math.sin(Math.toRadians(thetaZY)) * Math.sin(Math.toRadians(thetaXY));
            z = rad * Math.cos(Math.toRadians(thetaXY));
            // System.out.printf("bee:  (%f, %f, %f) \n", x, y, z); // prints out the location of the atom
            thetaZY += angY;
            thetaXY += angZ;
            Point3D curr = new Point3D(x, y, z);
            Atomus atomus = new Atomus(atom, x, y, z, node);

            // Draw the atom
            drawAtom(atom, x, y, z);
            if (drawn.get(atomus) == null) {
                drawn.put(atomus, true);
                stack.push(atomus);
            }

            // Draw the bonds
            Bond bond = null;
            for (Bond b : atom.getAttachedBonds()) {
                if (b.getAtoms().contains(atom)) {
                    bond = b;
                }
            }
            drawBond(origin, curr, setBondColor(bond));
        }
    }


    /**
     * Draws atoms around a central node in a bent pattern
     *
     * @param node  the node representing the atom around which to draw
     * @param stack the representation of nodes still to be traversed
     * @param drawn the representation of nodes already drawn
     */
    private void bent(Atomus node, Stack<Atomus> stack, HashMap<Atomus, Boolean> drawn) {
        double rad = 200;
        double thetaZY = 0;
        double thetaXY = 0;
        double angY = 0;
        double angZ = 120;

        double x = 0;
        double y = 0;
        double z = 0;
        Point3D origin = node.loc;
        int draw = 1;

        // Determine the direction in which to draw
        if (node.par == null) {
            draw = 1;
        } else {
            Point3D deltaPar = node.par.loc.subtract(node.loc);
            if (deltaPar.getZ() >= 0) {
                draw = -1;
            }
        }

        for (int i = 0; i < node.kids.size(); i++) {
            Atom atom = node.kids.get(i);
            x = draw * rad * Math.cos(Math.toRadians(thetaZY)) * Math.sin(Math.toRadians(thetaXY));
            y = rad * Math.sin(Math.toRadians(thetaZY)) * Math.sin(Math.toRadians(thetaXY));
            z = draw * rad * Math.cos(Math.toRadians(thetaXY));
            //System.out.printf("bee:  (%f, %f, %f) \n", x, y, z); // print the location of the atom
            thetaZY += angY;
            thetaXY += angZ;
            Point3D curr = new Point3D(x, y, z);
            Atomus atomus = new Atomus(atom, x, y, z, node);

            if (drawn.get(atomus) == null) {
                drawAtom(atom, x, y, z);
                drawn.put(atomus, true);
            }
            stack.push(atomus);

            // Draw the bonds
            Bond bond = null;
            for (Bond b : atom.getAttachedBonds()) {
                if (b.getAtoms().contains(atom)) {
                    bond = b;
                }
            }
            drawBond(origin, curr, setBondColor(bond));
        }
    }

    /**
     * Draws an atom with the proper color and location.
     *
     * @param atom the atom to be drawn
     * @param x    the x-coordinate of the atom
     * @param y    the y-coordinate of the atom
     * @param z    the z-coordinate of the atom
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
