package GUI;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import structures.enums.Elem;

/**
 * This class will provide a view of a single atom at the subatomic level.
 * It is currently just a shell in which to program new things for Sprint 3.
 *
 * @author Sarah Larkin
 *
 * CS3141, Spring 2018, Team ATOM
 * Date Last Modified:  March 25, 2018.
 */
public class AtomView extends Group {

    private int numElectrons = 0;
    private final int[] shells = {2, 8, 8, 18, 18, 32, 32};
    private final double[] thetas = {Math.PI, 2 * Math.PI / 8, 2 * Math.PI / 8, 2 * Math.PI / 18, 2 * Math.PI / 18, 2 * Math.PI / 32, 2 * Math.PI / 32};
    private int[] numAtoms = new int[7];

    public AtomView(int num) {
        this.numElectrons = num;
        bohr(num);
    }

    /**
     * Fills the shells with the correct number of electrons in the data representation
     */
    public void fillShells() {
        int shell = 0;
        while (numElectrons > 0 && shell < shells.length) {
            if (numAtoms[shell] < shells[shell]) {
                numAtoms[shell]++;
                numElectrons--;
            } else {
                shell++;
            }
        }
    }

    /**
     * Draws the orbit electrons for a given shell
     *
     * @param shellNum the number of the shell
     */
    public void drawOrbit(int shellNum) {
        double radius = shellNum * 25 + 75;
        double x = 250;
        double y = 250;
        Ellipse ellipse = new Ellipse(x, y, radius, radius);
        ellipse.setStrokeWidth(1);
        ellipse.setStroke(Color.BLACK);
        ellipse.setFill(Color.PALETURQUOISE);
        getChildren().add(ellipse);

        // Draw atoms
        double numAtom = numAtoms[shellNum];
        double theta = (Math.PI * 2) / (double) shells[shellNum];
        double er = 10;
        for (int i = 0; i < numAtom; i++) {
            double ex = 250 + radius * Math.cos(theta);
            double ey = 250 + radius * Math.sin(theta);
            Ellipse e = new Ellipse(ex, ey, er, er);
            e.setFill(Color.RED);
            getChildren().add(e);
            theta += thetas[shellNum];
        }
    }

    /**
     * Creates a new group for drawing the Bohr model of an atom
     *
     * @param num number of electrons
     */
    private void bohr(int num) {
        numElectrons = num;
        fillShells();
        for (int i = shells.length - 1; i >= 0; i--) {
            if (numAtoms[i] > 0) {
                drawOrbit(i);
            }
        }
        Ellipse center = new Ellipse(250, 250, 50, 50);
        center.setFill(Elem.get(numElectrons).getColor());
        getChildren().add(center);
        String string = Elem.get(num).getSymbol();
        Text text = new Text(225, 260, string);
        text.setFill(Color.BLACK);
        text.setFont(new Font(50));
        getChildren().add(text);
    }

}
