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
import structures.enums.Elem;
import structures.enums.Geometry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
//http://netzwerg.ch/blog/2015/03/22/javafx-3d-line/


public class MoleculeViewCopy extends Group {

    private final double ATOM_RADIUS = 50;
    private final double BOND_RADIUS = 10;
    private final double BOND_LENGTH = 200;

    public MoleculeViewCopy(Molecule molecule) {

      // molecule = new Molecule("C_H_{4}", "methane");
       //  molecule = new Molecule("C_{2}H_{4}O_{2}", "acetic acid");//CH3COOH
       // molecule = new Molecule("C_{2}H_{4}O_{2}", "acetic acid");
       drawPic(molecule);
//        gugu(molecule);

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
            double x = 2 * bondLen * in;
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
                atoms.put(kid, child);
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
    // begin external code : link
    public Cylinder createConnection(Point3D origin, Point3D target) {
        Point3D yAxis = new Point3D(0, 1, 0);
        Point3D diff = target.subtract(origin);
        double height = diff.magnitude();

        Point3D mid = target.midpoint(origin);
        Translate moveToMidpoint = new Translate(mid.getX(), mid.getY(), mid.getZ());

        Point3D axisOfRotation = diff.crossProduct(yAxis);
        double angle = Math.acos(diff.normalize().dotProduct(yAxis));
        Rotate rotateAroundCenter = new Rotate(-Math.toDegrees(angle), axisOfRotation);

        Cylinder line = new Cylinder(10, height);
       // end external code:
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

    public Group pleaseWork(Molecule mole) {
        Group group = new Group();

//        Cylinder can = new Cylinder(50, 200);
//        can.getTransforms().add(new Translate(0, 0, 0));
//        can.setMaterial(new PhongMaterial(Color.PAPAYAWHIP));
//        getChildren().add(can);

        double rad = 200;
        double thetaZY = 0;//s
        double thetaXY = 0;//t
        double angY = 60;
        double angZ = 120;

        double x = 0;
        double y = 0;
        double z = 0;
        Point3D origin = new Point3D(0, 0, 0);
        drawAtom(new Atom(Elem.Carbon), x, y, z, group);
        for (int i = 0; i < 3; i++) {
            x = rad + rad * Math.cos(Math.toRadians(thetaZY)) * Math.sin(Math.toRadians(thetaXY));
            y = rad * Math.sin(Math.toRadians(thetaZY)) * Math.sin(Math.toRadians(thetaXY));
            z = rad * Math.cos(Math.toRadians(thetaXY));
            System.out.printf("bee:  (%f, %f, %f) \n", x, y, z);
            thetaZY += angY;
            thetaXY += angZ;
            Point3D curr = new Point3D(x, y, z);
            drawAtom(new Atom(Elem.Hydrogen), x, y, z, group);
//            if (i > 0) {
                Cylinder sil = createConnection(origin, curr);
                getChildren().add(sil);

                System.out.println(sil.getHeight() + ", " + sil.getRadius() + ", " + sil.getTranslateX() + ", " + sil.getTranslateY() + ", " + sil.getTranslateZ());
//            }
        }

        Sphere sphere = new Sphere(600);
        sphere.setMaterial(new PhongMaterial(Color.BLUE));
        group.getChildren().add(sphere);
        return group;
    }


    class Atomus {
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

        while(!stack.isEmpty()) {
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


    /*@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@*/
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
//            thetaZY = node.par.loc.angle(node.loc);
            System.out.println("parent: " + deltaPar);
//            yAxis = node.par.loc;

        }
        int j = 0;

        Point3D origin = node.loc;// new Point3D(0, 0, 0);
        if (node.par == null) {
            j = 1;
            Atom a = node.kids.get(0);
            double x = node.x - rad;
            double y = node.y;
            double z = node.z;
            drawAtom(node.kids.get(0), node.x - rad, node.y, node.z);
            Point3D curr = new Point3D(x, y, z);
            Atomus atomus = new Atomus(a, x, y, z, node);
           // drawAtom(atom, x, y, z);
            drawn.put(atomus, true);
            stack.push(atomus);
            getChildren().add(createConnection(origin, curr));

        }



        double x = 0;
        double y = 0;
        double z = 0;


       // drawAtom(new Atom(Elem.Carbon), x, y, z);
        for (int i = j; i < node.kids.size(); i++) {
            Atom atom = node.kids.get(i);
            x = rad + rad * Math.cos(Math.toRadians(thetaZY)) * Math.sin(Math.toRadians(thetaXY));
            y = -rad * Math.sin(Math.toRadians(thetaZY)) * Math.sin(Math.toRadians(thetaXY));
            z = draw * rad * Math.cos(Math.toRadians(thetaXY));
            x = node.x + rad * Math.cos(Math.toRadians(thetaZY)) * Math.sin(Math.toRadians(thetaXY));
            y = node.y + rad * Math.sin(Math.toRadians(thetaZY)) * Math.sin(Math.toRadians(thetaXY));
            z = node.z + draw * rad * Math.cos(Math.toRadians(thetaXY));
            System.out.printf("bee:  (%f, %f, %f) \n", x, y, z);
            thetaZY += angY;
            thetaXY += angZ;
            Point3D curr = new Point3D(x, y, z);
            Atomus atomus = new Atomus(atom, x, y, z, node);
            drawAtom(atom, x, y, z);
            drawn.put(atomus, true);
            stack.push(atomus);
            getChildren().add(createConnection(origin, curr));

        }
       // getChildren().add(group);
    }

    private void terrat(Atomus node, Stack<Atomus> stack, HashMap<Atomus, Boolean> drawn) {
        Group group = tetra2(new Atomus(new Atom(Elem.Carbon),0, 0, 0, node.par), stack, drawn);
        group.getTransforms().add(new Translate(node.x, node.y, node.z));
        getChildren().add(group);
    }

    private Group tetra2(Atomus node, Stack<Atomus> stack, HashMap<Atomus, Boolean> drawn) {
        Group group = new Group();
        double rad = 200;
        double thetaZY = 0;//s
        double thetaXY = 0;//t
        double angY = 60;
        double angZ = 120;

        if (node.par != null) {
            //thetaZY = node.par.loc.angle(node.loc);
            //System.out.println("parent");
//            yAxis = node.par.loc;

        }



        double x = 0;
        double y = 0;
        double z = 0;

        Point3D origin = node.loc;// new Point3D(0, 0, 0);
        // drawAtom(new Atom(Elem.Carbon), x, y, z);
        for (int i = 0; i < node.kids.size(); i++) {
            Atom atom = node.kids.get(i);
            x = rad + rad * Math.cos(Math.toRadians(thetaZY)) * Math.sin(Math.toRadians(thetaXY));
            y = rad * Math.sin(Math.toRadians(thetaZY)) * Math.sin(Math.toRadians(thetaXY));
            z = rad * Math.cos(Math.toRadians(thetaXY));
            System.out.printf("bee:  (%f, %f, %f) \n", x, y, z);
            thetaZY += angY;
            thetaXY += angZ;
            Point3D curr = new Point3D(x, y, z);
            Atomus atomus = new Atomus(atom, x, y, z, node);
            drawAtom(atom, x, y, z, group);
            drawn.put(atomus, true);
            stack.push(atomus);
            group.getChildren().add(createConnection(origin, curr));

        }
        return group;
        // getChildren().add(group);
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
        // drawAtom(new Atom(Elem.Carbon), x, y, z);
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
            drawn.put(atomus, true);
            stack.push(atomus);
            getChildren().add(createConnection(origin, curr));

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
            getChildren().add(createConnection(origin, curr));

        }
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
