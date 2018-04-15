package GUI;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import structures.Atom;
import structures.Bond;
import structures.Molecule;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Creates and tests Lewis Dot Structure to represent a molecule.
 * @author  Sarah Larkin
 *
 * CS3141, R02, Spring 2018, Team ATOM
 * Date Last Modified:  April 15, 2018
 */
public class LewisTest extends Application {

    private Group group = new Group();
    private   Molecule mole = /*new Molecule("H_{2}_O", "water" );// = */new Molecule("C_{3}_H_{6}_O", "acetone");
    private String [][] list = new String[0][0];
    private int iter = 0;

    /**
     * Creates the Lewis Structure by calling helper methods
     * @param mole  the molecule to be represented
     */
    private void lewisStructure(Molecule mole) {
        // Set up structures
        Atom cen = mole.getCenter();
        int max = mole.getAtoms().size();
        list = new String[max][max];
        list[max / 2][max / 2] = cen.getElement().getSymbol();
        HashMap<Atom, Atom> map = new HashMap<>();
        map.put(cen, cen);
        Atom[][] atoms = new Atom[max][max];
        atoms[max / 2][max / 2] = cen;

        // Call recursive method to populate structures
        addition(cen, list, atoms, map, max / 2, max / 2);

        int[] size = spacing(list);
        int width = size[0];
        int height = size[1];
        double spin = 500 / Math.max(width, height);

        drawStructure(list, atoms, spin);

        int minCol = size[2];
        int minRow = size[3];
        group.getTransforms().add(new Translate(-spin * minCol + spin, -spin * minRow + spin));


    }

    /**
     * Draws the Lewis Structure into the group
     * @param list   an array of atom names
     * @param atoms  an array of atoms
     * @param spin   the distance between atoms
     */
    private void drawStructure(String[][] list, Atom[][] atoms, double spin) {
        for (int i = 0; i < list.length; i++) {
            for (int j = 0; j < list[0].length; j++) {
                if (list[i][j] != null) {
                    drawBonds(atoms, i, j, spin);
                    Text text = new Text(j * spin, i * spin, list[i][j]);
                    text.setFont(new Font(0.3 * spin));
                    group.getChildren().add(text);
                }
            }
        }
    }

    /**
     * Determines the spacing between atoms and the positioning of the group
     * @param list  the list of atoms by element symbol
     * @return      an array with the width and height of the molecule in number
     *              of atoms plus the minimum row and column
     */
    private int[] spacing (String[][] list) {
        ArrayList<Integer> size = new ArrayList<>();
        int minCol = list.length;
        int minRow = list.length;
        int maxCol = 0;
        int maxRow = 0;
        for (int i = 0; i < list.length; i++) {
            for (int j = 0; j < list[0].length; j++) {
                if (list[i][j] != null) {
                    if (i < minRow) {
                        minRow = i;
                    }
                    if (j < minCol) {
                        minCol = j;
                    }
                    if (j > maxCol) {
                        maxCol = j;
                    }
                    if (i > maxRow) {
                        maxRow = i;
                    }
                }
            }
        }
        int width = maxCol - minCol + 2;
        int height = maxRow - minRow + 2;
        return new int[]{width, height, minCol, minRow};
    }

    /**
     * Draws the bonds between the atoms
     * @param atoms     array representing the molecule
     * @param i         current row of array
     * @param j         current column of array
     * @param spin      the spacing between atoms
     */
    private void drawBonds(Atom[][] atoms, int i, int j, double spin) {
        Atom a = atoms[i][j];
        ArrayList<Bond> bonds = a.getAttachedBonds();
        for (int k = 0; k < bonds.size(); k++) {
            Bond b = bonds.get(k);
            System.out.println("bond; " + b.getAtoms());
            Atom up = null, left = null;
            if (i > 0) {
                up = atoms[i - 1][j];
            }
            if (j > 0) {
                left = atoms[i][j - 1];
            }
            if (b.getAtoms().contains(up)) {
                bondUp(b, i, j, spin);
            }
            if (b.getAtoms().contains(left)) {
                bondLeft(b, i, j, spin);
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
    private void bondUp(Bond b, int i, int j, double spin) {
        switch (b.getOrder()) {
            case SINGLE:
                Line line = new Line((j) * spin + spin / 9, (i - 1) * spin + spin / 4, j * spin + spin / 9, i * spin - spin / 3);
                group.getChildren().add(line);
                break;
            case DOUBLE:
                Line d = new Line((j) * spin + spin / 12, (i - 1) * spin + spin / 4, j * spin + spin / 12, i * spin - spin / 3);
                Line lin = new Line((j) * spin + spin / 6, (i - 1) * spin + spin / 4, j * spin + spin / 6, i * spin - spin / 3);
                group.getChildren().addAll(d, lin);
                break;
            case TRIPLE:
                Line a = new Line((j) * spin + spin / 12, (i - 1) * spin + spin / 4, j * spin + spin / 12, i * spin - spin / 3);
                Line c = new Line((j) * spin + spin / 6, (i - 1) * spin + spin / 4, j * spin + spin / 6, i * spin - spin / 3);
                Line f = new Line((j) * spin + spin / 3, (i - 1) * spin + spin / 4, j * spin + spin / 3, i * spin - spin / 3);
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
    private void bondLeft(Bond b, int i, int j, double spin) {
        switch (b.getOrder()) {
            case SINGLE:
                Line line = new Line((j - 1) * spin + spin / 2, i * spin - spin / 8, j * spin - spin / 4, i * spin - spin / 8);
                group.getChildren().add(line);
                break;
            case DOUBLE:
                Line d = new Line((j - 1) * spin + spin / 2, i * spin - spin / 12, j * spin - spin / 4, i * spin - spin / 12);
                Line lin = new Line((j - 1) * spin + spin / 2, i * spin - spin / 6, j * spin - spin / 4, i * spin - spin / 6);
                group.getChildren().addAll(d, lin);
                break;
            case TRIPLE:
                Line a = new Line((j - 1) * spin + spin / 2, i * spin - spin / 12, j * spin - spin / 4, i * spin - spin / 12);
                Line c = new Line((j - 1) * spin + spin / 2, i * spin - spin / 6, j * spin - spin / 4, i * spin - spin / 6);
                Line f = new Line((j - 1) * spin + spin / 2, i * spin - spin / 3, j * spin - spin / 4, i * spin - spin / 3);
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
    private void addition(Atom curr, String[][] list, Atom[][] atoms, HashMap<Atom, Atom> map, int row, int col) {
        iter++;
        for (Atom atom : curr.getAttachedAtoms()) {
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

    /**
     * Tests the building of the molecule array by printing out the molecule
     * @param list  the array representing the molecule
     */
    public void lewisTest(String[][] list) {

        // Test the printout
        for (int row = 0; row < list.length; row++) {
            for (int col = 0; col < list[0].length; col++) {
                String s = list[row][col];
                if (s == null) {
                    System.out.print("  ");
                } else {
                    System.out.print(" " + list[row][col]);
                }
            }
            System.out.println();
        }
    }

    /**
     * Tests the number of iterations required to build the molecule and compares it to the number of atoms
     */
    public void buildTest() {
        Atom cen = mole.getCenter();
        int max = mole.getAtoms().size();
        String[][] list = new String[max][max];
        list[max / 2][max / 2] = cen.getElement().getSymbol();
        HashMap<Atom, Atom> map = new HashMap<>();
        map.put(cen, cen);
        Atom[][] atoms = new Atom[max][max];
        atoms[max / 2][max / 2] = cen;

        // Call recursive method to populate structures
        addition(cen, list, atoms, map, max / 2, max / 2);
        if (iter != mole.getAtoms().size() * 2) {
            System.out.println("atoms should be " + mole.getAtoms().size() * 2 + " but are " + iter);
        }

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(group, 500, 500);
        lewisStructure(mole);
        lewisTest(list);
        buildTest();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String [] args) {
        launch(args);
    }
}
