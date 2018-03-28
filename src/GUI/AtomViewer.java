package GUI;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import structures.enums.Elem;

import java.util.Arrays;

/**
 * This class will provide a view of a single atom at the subatomic level.
 * It is currently just a shell in which to program new things for Sprint 3.
 * @author Sarah Larkin
 * CS3141, Spring 2018
 * Date Last Modified:  March 25, 2018.
 */
public class AtomViewer extends Application {

    private int numElectrons = 8;
    private final int [] shells = {2, 8, 8, 18, 18, 32, 32};
    private final double [] thetas = {Math.PI, 2 * Math.PI/8, 2 * Math.PI/8, 2 * Math.PI/18, 2 * Math.PI/18, 2 * Math.PI/32, 2 * Math.PI/32};
    private int [] numAtoms = new int[7];
    private Group orbits = new Group();

//    public AtomViewer(int numElectrons) {
//        this.numElectrons = numElectrons;
//    }

    public void fillShells() {
        int shell = 0;
        while (numElectrons > 0) {
            System.out.println(numElectrons);
            if (numAtoms[shell] < shells[shell]) {
                numAtoms[shell]++;
                System.out.println("shell: atoms " + shell + ":  " + numAtoms[shell]);
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
        double radius = shellNum * 25 + 75;
        double x = 250;
        double y = 250;
        Ellipse ellipse = new Ellipse(x, y, radius, radius);
        ellipse.setStrokeWidth(1);
        ellipse.setStroke(Color.BLACK);
        ellipse.setFill(Color.PALETURQUOISE);
        g.getChildren().add(ellipse);

        // Draw atoms
        double numAtom = numAtoms[shellNum];
        System.out.println(numAtom + " : " + shellNum);
        double theta = (Math.PI * 2) / (double)shells[shellNum];
        double er = 10;
        for (int i = 0; i < numAtom; i++) {

            System.out.println("red");

            double ex = 250 + radius * Math.cos(theta);
            double ey = 250 + radius * Math.sin(theta);
            Ellipse e = new Ellipse(ex, ey, er, er);
            e.setFill(Color.RED);
            g.getChildren().add(e);
            theta += thetas[shellNum];
            System.out.println("Theta: " + theta);
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
    private Group bohr(int num) {
        numElectrons = num;
        fillShells();
        Group group = new Group();
        for (int i = shells.length - 1; i >= 0; i--) {
            if (numAtoms[i] > 0) {
                drawOrbit(group, i);
            }
        }
        Ellipse center = new Ellipse(250, 250, 50, 50);
        center.setFill(Elem.get(numElectrons).getColor());
        group.getChildren().add(center);
        String string = Elem.get(num).getSymbol();
        System.out.println("string: " + string);
        Text text = new Text(225, 260, string);
        text.setFill(Color.BLACK);
        text.setFont(new Font(50));
        group.getChildren().add(text);
        return group;
    }

    Group group = new Group();
    int ind = 0;
    Scene s;
    @Override
    public void start(Stage stage) throws Exception {
       // Group group = new Group();
      //  group.getChildren().add(new Ellipse(100, 100, 100, 100));
      //  Rectangle rectangle = new Rectangle(20, 20, 50, 300);
      //  rectangle.setFill(Color.RED);
        //group.getChildren().add(rectangle);
        //fillShells();
        //drawOrbit(group, 1);
        int i = 0;
        group = bohr(90);
        group.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                ind++;
                group = bohr(ind);
                System.out.println(ind);
            }
        });
        s = new Scene(group, 500, 500);
        s.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ind++;
                group = bohr(ind);
                System.out.println(ind);
                s = new Scene(group, 500, 500);
                stage.setScene(s);
            }
        });
        stage.setScene(s);
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
