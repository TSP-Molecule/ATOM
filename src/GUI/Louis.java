package GUI;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import structures.Atom;
import structures.Bond;
import structures.Molecule;
import structures.enums.BondOrder;
import structures.enums.Geometry;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Louis extends Application {

   // Scene scene;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Molecule mole =/* new Molecule("H_{2}_O", "water" );// = */new Molecule("C_{3}_H_{6}_O", "acetone");
        Group group = new Lewis(mole);
        Scene scene = new Scene(group, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String [] args) {
        launch(args);
//        Louis lew = new Louis();
//        Group group = lew.gogol();
    }

    class Noddy {
        Noddy left;
        Noddy right;
        Noddy top;
        Noddy bottom;

        Atom atom;

        ArrayList<Bond> bondList = new ArrayList<>();
        Noddy [] friends = {null, null, null, null};

        public Noddy() {

        }

    }

    public Group gogol () {
        Group group = new Group();
        Molecule mole =/* new Molecule("H_{2}_O", "water" );// = */new Molecule("C_{3}_H_{6}_O", "acetone");
        Atom cen = mole.getCenter();
        int max = mole.getAtoms().size();
        String [][] list = new String[max][max];
        list [max/2][max/2] = cen.getElement().getSymbol();
        HashMap<Atom, Atom> map = new HashMap<>();
        map.put(cen, cen);
        Atom [][] atoms = new Atom[max][max];
        atoms[max/2][max/2] = cen;
        addition(cen, list, atoms, map, max/2, max/2);


        // testing the printout
        for (int row = 0; row < list.length; row++) {
            for (int col = 0; col < list[0].length; col++) {
                String s = list[row][col];
                if (s == null) {
                    System.out.print("  ");
                } else{
                    System.out.print(" " + list[row][col]);
                }
            }
            System.out.println();
        }

        double spin = 0;
        int minCol = list.length;
        int minRow = list.length;
        int maxCol = 0;
        int maxRow = 0;
        for (int i = 0; i < list.length; i++) {
            for (int j = 0; j < list[0].length; j++) {
                if (list [i][j] != null) {
                    if ( i < minRow)
                        minRow = i;
                    if ( j < minCol)
                        minCol = j;
                    if (j > maxCol)
                        maxCol = j;
                    if (i > maxRow)
                        maxRow = i;
                }
            }
        }
        System.out.println("min row, min col" + minRow + ",  " + minCol);
        int width = maxCol - minCol + 2;
        int height = maxRow - minRow + 2;
        spin = 400 / Math.max(width, height);

        for (int i = 0; i < list.length; i++) {
            for (int j = 0; j < list[0].length; j++) {
                if (list[i][j] != null) {
                    drawBonds(atoms, i, j, spin, group);
                    Text text = new Text(j * spin, i*spin, list[i][j]);
                    text.setFont(new Font(0.3 * spin));
                    group.getChildren().add(text);
                }

            }
        }




        group.getTransforms().add(new Translate(-spin * minCol + spin, -spin * minRow + spin));
        return group;

    }

    private void drawBonds(Atom [][] atoms, int i, int j, double spin, Group group) {
        Atom a = atoms[i][j];
        ArrayList<Bond> bonds = a.getAttachedBonds();
        for (int k = 0; k < bonds.size(); k++) {
            Bond b = bonds.get(k);
            System.out.println("bond; " + b.getAtoms());
            Atom up = null,  left = null;
            if (i > 0) {
                up = atoms[i - 1][j];
            }
            if (j > 0) {
                left = atoms[i][j - 1];
            }
            if (b.getAtoms().contains(up)) {
                bondUp(b, i, j, spin, group);
            }
            if (b.getAtoms().contains(left)) {
                bondLeft(b, i, j, spin, group);
            }
        }
    }

    private void bondUp(Bond b, int i, int j, double spin, Group group) {
        switch (b.getOrder()) {
            case SINGLE:
                Line line = new Line( (j ) * spin + spin/9, (i - 1)  * spin + spin / 4, j * spin + spin/9, i * spin - spin / 3);
                group.getChildren().add(line);
                break;
            case DOUBLE:
                Line d = new Line( (j ) * spin + spin/12, (i - 1)  * spin + spin / 4, j * spin + spin/12, i * spin - spin / 3);
                Line lin = new Line( (j ) * spin + spin/6, (i - 1)  * spin + spin / 4, j * spin + spin/6, i * spin - spin / 3);
                group.getChildren().addAll(d, lin);
                break;
            case TRIPLE:
                Line a = new Line( (j ) * spin + spin/12, (i - 1)  * spin + spin / 4, j * spin + spin/12, i * spin - spin / 3);
                Line c = new Line( (j ) * spin + spin/6, (i - 1)  * spin + spin / 4, j * spin + spin/6, i * spin - spin / 3);
                Line f = new Line( (j ) * spin + spin/3, (i - 1)  * spin + spin / 4, j * spin + spin/3, i * spin - spin / 3);
                group.getChildren().addAll(a, c, f);
                break;
        }
    }

    private void bondLeft(Bond b, int i, int j, double spin, Group group) {
        switch (b.getOrder()) {
            case SINGLE:
                Line line = new Line( (j - 1) * spin + spin/2, i * spin - spin/8, j * spin - spin/4, i * spin - spin/8);
                group.getChildren().add(line);
                break;
            case DOUBLE:
                Line d = new Line( (j - 1) * spin + spin/2, i * spin - spin/12, j * spin - spin/4, i * spin - spin/12);
                Line lin = new Line( (j - 1) * spin + spin/2, i * spin - spin/6, j * spin - spin/4, i * spin - spin/6);
                group.getChildren().addAll(d, lin);
                break;
            case TRIPLE:
                Line a = new Line( (j - 1) * spin + spin/2, i * spin - spin/12, j * spin - spin/4, i * spin - spin/12);
                Line c = new Line( (j - 1) * spin + spin/2, i * spin - spin/6, j * spin - spin/4, i * spin - spin/6);
                Line f = new Line( (j - 1) * spin + spin/2, i * spin - spin/3, j * spin - spin/4, i * spin - spin/3);
                group.getChildren().addAll(a, c, f);
                break;
        }
    }



    int iter = 0;

//    private void bondUp(int i, int j, double spin, Group group) {
//        Line line = new Line( (j ) * spin + spin/9, (i - 1)  * spin + spin / 4, j * spin + spin/9, i * spin - spin / 3);
//        group.getChildren().add(line);
//    }

//    private void bondLeft(int i, int j, double spin, Group group) {
//        Line line = new Line( (j - 1) * spin + spin/2, i * spin - spin/8, j * spin - spin/4, i * spin - spin/8);
//        group.getChildren().add(line);
//    }
//
//
//
//    private void doubleBondUp(int i, int j, double spin, Group group) {
//        // draws for up
//        Line line = new Line( (j ) * spin + spin/12, (i - 1)  * spin + spin / 4, j * spin + spin/12, i * spin - spin / 3);
//        Line lin = new Line( (j ) * spin + spin/6, (i - 1)  * spin + spin / 4, j * spin + spin/6, i * spin - spin / 3);
//        group.getChildren().addAll(line, lin);
//    }
//
//    private void doubleBondLeft(int i, int j, double spin, Group group) {
//        Line line = new Line( (j - 1) * spin + spin/2, i * spin - spin/12, j * spin - spin/4, i * spin - spin/12);
//        Line lin = new Line( (j - 1) * spin + spin/2, i * spin - spin/6, j * spin - spin/4, i * spin - spin/6);
//        group.getChildren().addAll(line, lin);
//    }

//    private void doubleBond(int x1, int y1, int x2, int y2, double spin, Group group) {
//        // draws for up
//        Line line = new Line( x1  * spin + spin/12, y1  * spin + spin / 4, x2 * spin + spin/12, y2 * spin - spin / 3);
//        Line lin = new Line( x1) * spin + spin/6,  y1 * spin + spin / 4, j * spin + spin/6, i * spin - spin / 3);
//        group.getChildren().addAll(line, lin);
//    }



    private void addition (Atom curr, String [][] list, Atom [][] atoms, HashMap<Atom, Atom> map, int row, int col) {
        iter++;
        System.out.println("iter: " + iter);
        for (Atom atom: curr.getAttachedAtoms()) {
            if (map.get(atom) != null) {
                continue;
            }
            if (col > 0) {
                if (list[row][col - 1] == null) {
                    list[row][col - 1] = atom.getElement().getSymbol();
                    atoms[row][col - 1] = atom;
                    map.put(atom, atom);
                    addition(atom, list, atoms, map, row, col - 1);
                    continue;
                }
            }
            if (col < list[0].length - 1) {
                if (list[row][col + 1] == null) {
                    list[row][col + 1] = atom.getElement().getSymbol();
                    atoms[row][col + 1] = atom;
                    map.put(atom, atom);
                    addition(atom, list, atoms, map, row, col + 1);
                    continue;
                }
            }
            if (row > 0) {
                if (list[row - 1][col] == null) {
                    list[row - 1][col] = atom.getElement().getSymbol();
                    atoms[row - 1][col] = atom;
                    map.put(atom, atom);
                    addition(atom, list, atoms, map, row - 1, col);
                    continue;
                }
            }
            if (row < list.length - 1) {
                if (list[row + 1][col] == null) {
                    list[row + 1][col] = atom.getElement().getSymbol();
                    atoms[row + 1][col] = atom;

                    map.put(atom, atom);
                    addition(atom, list, atoms, map, row + 1, col);
                    continue;
                }
            }
        }
        return;
    }

   /* public Group gogolOld () {
        Group group = new Group();
        Molecule mole =/* new Molecule("H_{2}_O", "water" );// = *///new Molecule("C_{3}_H_{6}_O", "acetone");
       /* Atom cen = mole.getCenter();
        int max = mole.getAtoms().size();
        String [][] list = new String[max][max];
        list [max/2][max/2] = cen.getElement().getSymbol();
        HashMap<Atom, Atom> map = new HashMap<>();
        map.put(cen, cen);
        Atom [][] atoms = new Atom[max][max];
        atoms[max/2][max/2] = cen;
        addition(cen, list, atoms, map, max/2, max/2);


        // testing the printout
        for (int row = 0; row < list.length; row++) {
            for (int col = 0; col < list[0].length; col++) {
                String s = list[row][col];
                if (s == null) {
                    System.out.print("  ");
                } else{
                    System.out.print(" " + list[row][col]);
                }
            }
            System.out.println();
        }

        double spin = 0;
        int minCol = list.length;
        int minRow = list.length;
        int maxCol = 0;
        int maxRow = 0;
        for (int i = 0; i < list.length; i++) {
            for (int j = 0; j < list[0].length; j++) {
                if (list [i][j] != null) {
                    if ( i < minRow)
                        minRow = i;
                    if ( j < minCol)
                        minCol = j;
                    if (j > maxCol)
                        maxCol = j;
                    if (i > maxRow)
                        maxRow = i;
                }
            }
        }
        System.out.println("min row, min col" + minRow + ",  " + minCol);
        int width = maxCol - minCol + 2;
        int height = maxRow - minRow + 2;
        spin = 400 / Math.max(width, height);

        for (int i = 0; i < list.length; i++) {
            for (int j = 0; j < list[0].length; j++) {
                Text text = new Text(j * spin, i*spin, list[i][j]);
                text.setFont(new Font(0.3 * spin));
                group.getChildren().add(text);
            }
        }

//        for (int i = 0; i < list.length; i++) {
//            for (int j = 0; j < list[0].length; j++) {
//                if (j > 0) {
//                    if (list [i][j - 1] != null) {
//                        Line line = new Line( (j - 1) * spin + spin/2, i * spin - spin/8, j * spin - spin/4, i * spin - spin/8);
//                        group.getChildren().add(line);
//                    }
//                }
////                Text text = new Text(j * spin, i*spin, list[i][j]);
////                text.setFont(new Font(0.3 * spin));
////                group.getChildren().add(text);
//            }
//        }
        for (int i = 0; i < list.length; i++) {
            for (int j = 0; j < list[0].length; j++) {
                if (list[i][j] != null) {
                    Atom a = atoms[i][j];
//                    for (Atom atom: a.getAttachedAtoms()) {
//                        if (atom.getAttachedAtoms().contains(a)) {
//                            System.out.println("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");
//                        }
//                    }
                    ArrayList<Bond> bonds = a.getAttachedBonds();
                    // System.out.println("be: " + bonds.size());
                    for (int k = 0; k < bonds.size(); k++) {
                        Bond b = bonds.get(k);
                        System.out.println("bond; " + b.getAtoms());
                        Atom up = null, down = null, left = null, right = null;
                        if (i > 0)
                            up = atoms[i - 1][j];
                        if (i < list.length - 1)
                            down = atoms[i+1][j];
                        if (j > 0)
                            left = atoms[i][j - 1];
                        if (j < list.length - 1)
                            right = atoms[i][j + 1];
                        //if (i > 0) {
                        if (b.getAtoms().contains(up)) {
                            System.out.println("UP");

//                            Line line = new Line( (j ) * spin + spin/9, (i - 1)  * spin + spin / 4, j * spin + spin/9, i * spin - spin / 3);
                            if (b.getOrder() == BondOrder.DOUBLE) {
                                doubleBondUp(i, j, spin, group);
//                                Line line = new Line( (j ) * spin + spin/12, (i - 1)  * spin + spin / 4, j * spin + spin/12, i * spin - spin / 3);
//                                Line lin = new Line( (j ) * spin + spin/6, (i - 1)  * spin + spin / 4, j * spin + spin/6, i * spin - spin / 3);
//
////                                line.setFill(Color.RED);
////                                line.setStroke(Color.RED);
//                                group.getChildren().addAll(line, lin);
                            }


                        }
//                        if (b.getAtoms().contains(down)) {
//                            System.out.println("DOWN");
//                        }
                        if (b.getAtoms().contains(left)) {
                            System.out.println("LEFT");
                            Line line = new Line( (j - 1) * spin + spin/2, i * spin - spin/8, j * spin - spin/4, i * spin - spin/8);
                            group.getChildren().add(line);
                        }
//                        if (b.getAtoms().contains(right)) {
//                            System.out.println("RIGHT");
//                            Line line = new Line( (j + 1) * spin + spin/4, i * spin - spin/8, j * spin - spin/2, i * spin - spin/8);
//                            group.getChildren().add(line);
//
//                        }
                        // }
                    }
                }
//                if (j > 0) {
//                    if (list [i][j - 1] != null) {
//                        Line line = new Line( (j - 1) * spin + spin/2, i * spin - spin/8, j * spin - spin/4, i * spin - spin/8);
//                        group.getChildren().add(line);
//                    }
//                }
//                Text text = new Text(j * spin, i*spin, list[i][j]);
//                text.setFont(new Font(0.3 * spin));
//                group.getChildren().add(text);
            }
        }



        group.getTransforms().add(new Translate(-spin * minCol + spin, -spin * minRow + spin));
        return group;

        // return null;

    }*/

//    public Group drawIn(String [][] list) {
//        Group group = new Group();
//return null;
//    }

//    Molecule mole = new Molecule()
//    Atom cen = mole.getCenter();
//    MoleculeView.Atomus adam = new MoleculeView.Atomus(cen, 0, 0, 0, null);
//    // System.out.println("central bonds: " + );
//    Stack<MoleculeView.Atomus> stack = new Stack<>();
//    HashMap<MoleculeView.Atomus, Boolean> drawn = new HashMap<>();
//    HashMap<MoleculeView.Atomus, Boolean> visited = new HashMap<>();
//
//        stack.push(adam);
//    int counter = 0;
//        while(!stack.isEmpty() && counter < mole.getAtoms().size() * 10) {
//        System.err.println(stack.size());
//        counter++;
//        MoleculeView.Atomus node = stack.pop();
//        System.out.println("bonds: " + node.atom.getAttachedBonds());
//        if (drawn.get(node) == null) {
//            drawAtom(node.atom, node.x, node.y, node.z);
//        }
//        if (visited.get(node) != null) {
//            continue;
//        }
//        Geometry geo = node.atom.getGeometry();
//        if (geo == null) {
//            continue;
//        }
//        switch (geo) {
//            case Tetrahedral:
//                tetra(node, stack, drawn);
//                break;
//            case TrigonalPlanar:
//                trig(node, stack, drawn);
//                break;
//            case Bent:
//                bent(node, stack, drawn);
//                break;
//            case VShape:
//                System.out.println("centre: " + cen + "  \nkids: "  + cen.getAttachedAtoms().size() );
//            default:
//                System.out.println("hi im a " + geo);
//        }
//    }
//}
}
