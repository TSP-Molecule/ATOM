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

    private  int iter = 0; // used to count the iterations required to fill the array

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
    }

    /**
     * Makes a group to draw the Lewis Dot Structure of acetone - includes
     * a test of the printout to ensure the molecule has been added correctly
     * @return
     */
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

    /**
     * Draws the bonds for an atom
     * @param atoms     array representing the molecule
     * @param i         the row for the current atom
     * @param j         the column for the current atom
     * @param spin      the distance between atoms
     * @param group     the group in which to draw the bond
     */
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

    /**
     * Helper method draws vertical bonds
     * @param b         the bond to draw
     * @param i         x offset in number of atoms
     * @param j         y offset in number of atoms
     * @param spin      distance between atoms
     */
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

    /**
     * Helper method draws horizontal bonds
     * @param b         the bond to draw
     * @param i         x offset in number of atoms
     * @param j         y offset in number of atoms
     * @param spin      distance between atoms
     */
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


    /**
     * Recursive method:  populates a given array with atoms to represent the molecule
     * @param curr      the current atom (the center of the molecule)
     * @param list      array of atoms by element symbol
     * @param atoms     array of atoms
     * @param map       map to determine if an atom has been added to the array
     * @param row       the current row of the array
     * @param col       the current column of the array
     */
    private void addition (Atom curr, String [][] list, Atom [][] atoms, HashMap<Atom, Atom> map, int row, int col) {
        iter++;
        System.out.println("iter: " + iter); // prints out the number of each iteration for testing
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

}
