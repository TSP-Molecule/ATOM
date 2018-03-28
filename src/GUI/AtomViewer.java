package GUI;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.stage.Stage;

import java.util.Arrays;

/**
 * This class will provide a view of a single atom at the subatomic level.
 * It is currently just a shell in which to program new things for Sprint 3.
 * @author Sarah Larkin
 * CS3141, Spring 2018
 * Date Last Modified:  March 25, 2018.
 */
public class AtomViewer extends Application {

    private int numElectrons = 0;
    private final int [] shells = {2, 10, 18, 36, 54, 86};
    private int [] numAtoms = new int[6];
    private Group orbits = new Group();

    public AtomViewer(int numElectrons) {
        this.numElectrons = numElectrons;
    }

    public void fillShells() {
        int shell = 0;
        while (numElectrons > 0) {
            System.out.println(numElectrons);
            if (numAtoms[shell] < shells[shell]) {
                numAtoms[shell]++;
                System.out.println(shell + ":  " + numAtoms[shell]);
                numElectrons--;
            } else {
                shell++;
            }
        }
    }

    public void drawOrbit(Group g, int shellNum) {
        /*
               *   ***   *
              *   *   *   *
               *   ***   *
                 *     *
                   ***
             MAX ORBITALS:  6

            TOTAL RADIUS:  CENTER + SPACE + 6*SPACE
            CENTER:  50
            SPACE: 50
            SCREEN 500x 500

         */
        double radius = shellNum * 50 + 100;
        double x = 250;
        double y = 250;
        Ellipse ellipse = new Ellipse(x, y, radius, radius);
        ellipse.setStrokeWidth(1);
        ellipse.setStroke(Color.BLACK);
        ellipse.setFill(Color.PALETURQUOISE);
        g.getChildren().add(ellipse);

        // Draw atoms
        double numAtom = numAtoms[shellNum];
        double theta = (Math.PI * 2) / shells[shellNum];
        double er = 40;
        for (int i = 0; i < numAtom; i++) {
            double ex = radius * Math.cos(theta * i);
            double ey = radius * Math.sin(theta * i);
            Ellipse e = new Ellipse(ex, ey, er, er);
            e.setFill(Color.RED);
            g.getChildren().add(e);
        }

    }

    public static void main(String [] args) {
//        AtomViewer at = new AtomViewer(8);
//        at.fillShells();
//       // Arrays.toString(at.numAtoms);
//        System.out.println("ba");
//        for (int i = 0; i < at.numAtoms.length; i++) {
//            System.out.println(i + ": " + at.numAtoms[i]);
//        }
        launch(args);
    }

    /**
     * Creates a new group for drawing the Bohr model of an atom
     * @return the group
     */
    private Group bohr() {
        Group group = new Group();
        //TODO:  implement the drawing of the atom
        return group;
    }

    @Override
    public void start(Stage stage) throws Exception {
        Group group = new Group();
        fillShells();
        drawOrbit(group, 1);
        Scene s = new Scene(group, 500, 500);
        stage.show();
    }

    // Note of the display pattern for electron shells.
    /*  s = 2, p = 6, d = 10
    1s  has 2 electrons
    2s has 2 electrons
    2p has 6 electrons
    3s has 2 electrons
    3p has 6 electrons
    4s has 2 electrons
    3d has 10 electrons
    4p has 6 electrons
    4d has 10 electrons
    5s has 2 electrons
     */



}
