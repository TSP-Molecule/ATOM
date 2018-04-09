package GUI;

import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import structures.Atom;
import structures.Bond;
import structures.Molecule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class Lewis extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        Group group = lew2();
        group.getTransforms().add(new Translate(200, 200));

        Scene scene = new Scene(group, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    class Atomnode {
        Atomnode par;
        double x;
        double y;
        //  double z;
        Atom atom;

        ArrayList<Atomnode> kids = new ArrayList<>();
        ArrayList<Atom> kido = new ArrayList<>();

        Atomnode(Atom a, double ax, double ay, Atomnode pa) {
            atom = a;
            x = ax;
            y = ay;
            //  z = az;
            par = pa;
            if (pa == null) {
                for (Atom alone : atom.getAttachedAtoms()) {
                    kido.add(alone);
                    kids.add(new Atomnode(alone, 0, 0, this));
                }
            } else {
                for (Atom alone : atom.getAttachedAtoms()) {
                    if (!pa.getAtom().equals(alone)) {
                        kido.add(alone);
                        kids.add(new Atomnode(alone, 0, 0, this));
                    }
                }
            }
        }


//                else {
//                    if (!pa.getAtom().equals(alone)) {
//                        kido.add(alone);
//                        kids.add(new Atomnode(atom, 0, 0, this));
//                    }
//                }
//            }
//        }

        public Atom getAtom() {
            return atom;
        }
    }

    public Group lew() {
        Group group = new Group();
        Molecule mole = new Molecule("C_{3}_H_{6}_O", "acetone");
       // mole = new Molecule("H_{2}_O", "water");
        Stack<Atomnode> stack = new Stack<>();
        Atom cen = mole.getCenter();
        Atomnode node = new Atomnode(cen, 0, 0, null);
        stack.push(node);
        while (!stack.isEmpty()) {
            Atomnode nod = stack.pop();
            drawAtom(group, nod);
            System.out.println("blue bayou");
            double ang = 0;
            double rad = 100;
            if (nod.par != null) {
                double deltaX = nod.x - nod.par.x;
                double deltaY = nod.x - nod.par.y;
                ang = Math.atan2(deltaY, deltaX);
                // ang = new Point2D(nod.x, nod.y).angle(nod.par.x, nod.par.y);
                System.out.printf("%s(%f, %f) to %s(%f, %f) has an angle of %f\n", nod.par.atom.getElement().getSymbol(), nod.par.x, nod.par.y, nod.atom.getElement().getSymbol(), nod.x, nod.y, ang);
            }
            double theta = 360.0 / (nod.kids.size() + 1);
            System.out.println("theta" + theta);
            System.out.println("num kids: " + nod.kids.size());
            for (int i = 0; i < nod.kids.size(); i++) {
                Atomnode kid = nod.kids.get(i);
                kid.x = rad * Math.cos(ang + i * theta) + nod.x;
                kid.y = rad * Math.sin(ang + i * theta) + nod.y;
                Line lin = new Line(nod.x, nod.y, kid.x, kid.y);
                group.getChildren().add(lin);
                stack.push(kid);
            }
        }


//        Group group = new Group();
//        HashMap<Atom, Boolean> atoms = new HashMap<>();
//        HashMap<Bond, Boolean> bonds = new HashMap<>();
//
//        Stack<Atom> stack = new Stack<>();
//        stack.push(mole.getCenter());
//        while(!stack.isEmpty()) {
//            Atom atom = stack.pop();
//            if (atoms.get(atom == null)) {
//                drawAtom();
//
//            }
//            ArrayList<Atom> kids = atom.getAttachedAtoms();
//            double angle = 360.0 / kids.size();
//            double theta = 0;
//
//        }
        return group;
    }

    public Group lew2() {
        Group group = new Group();
        Molecule mole = new Molecule("C_{3}_H_{6}_O", "acetone");
        Stack<Atomnode> stack = new Stack<>();
        Atom cen = mole.getCenter();
        Atomnode node = new Atomnode(cen, 0, 0, null);
        stack.push(node);
        while (!stack.isEmpty())

        {
            Atomnode nod = stack.pop();
            drawAtom(group, nod);
            System.out.println("blue bayou");
            double ang = 0;
            double rad = 100;
            if (nod.par != null) {
                double deltaX = nod.x - nod.par.x;
                double deltaY = nod.x - nod.par.y;
               // ang = Math.atan2(deltaY, deltaX);
                // ang = new Point2D(nod.x, nod.y).angle(nod.par.x, nod.par.y);
                System.out.printf("%s(%f, %f) to %s(%f, %f) has an angle of %f\n", nod.par.atom.getElement().getSymbol(), nod.par.x, nod.par.y, nod.atom.getElement().getSymbol(), nod.x, nod.y, ang);
            }
            //double theta = 360.0 / (nod.kids.size() + 1);
          //  System.out.println("theta" + theta);
            System.out.println("num kids: " + nod.kids.size());
            for (int i = 0; i < nod.kids.size(); i++) {
                Atomnode kid = nod.kids.get(i);
                kid.x = rad * Math.cos(i * 90) + nod.x;
                kid.y = rad * Math.sin(i * 90) + nod.y;
                Line lin = new Line(nod.x, nod.y, kid.x, kid.y);
                group.getChildren().add(lin);
                stack.push(kid);
            }
        }
        return  group;
    }


    public Group lew3() {
        Group group = new Group();
        Molecule mole = new Molecule("C_{3}_H_{6}_O", "acetone");
        // mole = new Molecule("H_{2}_O", "water");
        Stack<Atomnode> stack = new Stack<>();
        Atom cen = mole.getCenter();
        Atomnode node = new Atomnode(cen, 0, 0, null);
        stack.push(node);
        while (!stack.isEmpty()) {
            Atomnode nod = stack.pop();
            drawAtom(group, nod);
            System.out.println("blue bayou");
            double ang = 0;
            double rad = 100;

            if (nod.par != null) {
                double deltaX = nod.x - nod.par.x;
                double deltaY = nod.x - nod.par.y;
                if (deltaX < 0)
                    ang = 180;
                else if (deltaX > 0)
                    ang = 0;
                else if (deltaY < 0)
                    ang = 90;
                else
                    ang = 270;
                //ang = Math.atan2(deltaY, deltaX);
                // ang = new Point2D(nod.x, nod.y).angle(nod.par.x, nod.par.y);
                System.out.printf("%s(%f, %f) to %s(%f, %f) has an angle of %f\n", nod.par.atom.getElement().getSymbol(), nod.par.x, nod.par.y, nod.atom.getElement().getSymbol(), nod.x, nod.y, ang);
            }
            double theta = 90;//360.0 / (nod.kids.size() + 1);
            System.out.println("theta" + theta);
            System.out.println("num kids: " + nod.kids.size());
            for (int i = 0; i < nod.kids.size(); i++) {
                Atomnode kid = nod.kids.get(i);
                kid.x = rad * Math.cos(Math.toRadians(ang + i * 90)) + nod.x;
                kid.y = rad * Math.sin(Math.toRadians(ang + i * 90)) + nod.y;
                System.out.printf("%f, %f)\n", kid.x, kid.y);
                Line lin = new Line(nod.x, nod.y, kid.x, kid.y);
                group.getChildren().add(lin);
                stack.push(kid);
            }
        }
        return  group;
    }

    public Group lulu() {
        Group group = new Group();
        Ellipse eli = new Ellipse(0, 0, 100, 100);
        eli.setFill(Color.RED);
        Ellipse ellie = new Ellipse(100, 0, 100, 100);
        ellie.setFill(Color.TURQUOISE);
        double ang = Math.atan2(eli.getCenterY() - ellie.getCenterY(), eli.getCenterX() - ellie.getCenterX());
        System.out.printf("angle between (%f, %f) and (%f, %f) is %f\n", eli.getCenterX(), eli.getCenterY(), ellie.getCenterX(), ellie.getCenterY(), Math.toDegrees(ang));
        Line linseed = new Line(eli.getCenterX(), eli.getCenterY(), ellie.getCenterX(), ellie.getCenterY());
        group.getChildren().addAll(eli, ellie, linseed);
        ellie.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ellie.setCenterX(event.getX());
                ellie.setCenterY(event.getY());
                linseed.setEndX(event.getX());
                linseed.setEndY(event.getY());
                double ang = Math.atan2(eli.getCenterY() - ellie.getCenterY(), eli.getCenterX() - ellie.getCenterX());
                System.out.printf("angle between (%f, %f) and (%f, %f) is %f\n", eli.getCenterX(), eli.getCenterY(), ellie.getCenterX(), ellie.getCenterY(), ang * 180 / Math.PI);

            }
        });

        /*
                 90
        0                180
                -90
         */
       return group;
    }

    class Node {
        double x;
        double y;
        Node par;
        Node top;
        Node bottom;
        Node left;
        Node right;
        Atom atom;

        public Node(Atom atom, double x, double y, Node par) {
            this.x = x;
            this.y = y;
            this.atom = atom;
            this.par = par;
        }
    }

    public Group blahb() {
        Group group = new Group();
        Molecule mole = new Molecule("C_{3}_H_{6}_O", "acetone");
        Atom cen = mole.getCenter();
        Node nod = new Node(cen, 0, 0, null);
        atom(group, nod);
        ArrayList<Atom> list = cen.getAttachedAtoms();
        Stack<Node> nodes = new Stack<>();
        HashMap<Node, Node> map = new HashMap<>();
        while(!nodes.isEmpty()) {
            Node node = nodes.pop();
            recur(node, nodes, map, group);


        }
        //recur(nod, group);
        return group;
    }

    private void recur(Node nod, Stack<Node> stack, HashMap<Node, Node> map, Group group) {
        ArrayList<Atom> list = nod.atom.getAttachedAtoms();
        if (list.size() == 1)
            return;
        for (int i = 0; i < list.size(); i++) {
            Atom atom = list.get(i);
            if (atom.equals(nod.par.atom)) {
                continue;
            }
            double x = 0;
            double y = 0;
            Node node = new Node(atom, nod.x + x, nod.y + y, nod);
             if (nod.left == null) {
                x = -100;
                y = 0;
                node.x = x + nod.x;
                node.y = y + nod.y;
                nod.left = node;
                node.right = nod;
            }else if (nod.right == null) {
                x = 100;
                y = 0;
                node.x = x + nod.x;
                node.y = y + nod.y;
                nod.right = node;
                node.left = nod;
            }  else if (nod.top == null) {
                x = 0;
                y = -100;
                node.x = x + nod.x;
                node.y = y + nod.y;
                nod.top = node;
                node.bottom = nod;
            }else if (nod.bottom == null) {
                x = 0;
                y = 100;
                node.x = x + nod.x;
                node.y = y + nod.y;
                nod.bottom = node;
                node.top = nod;
            }
            atom(group, node);
            // stack.push(node);
           // recur(node, group);
        }
        return;
    }

//    private double [] top() {
//        double [] tope = {0, -100};
//    }

    public void atom(Group group, Node node) {
        Text text = new Text(node.x, node.y, node.atom.getElement().getSymbol());
        text.setFill(Color.BLACK);
        group.getChildren().add(text);
    }

    public void drawAtom(Group g, Atomnode node) {
        Text text = new Text(node.x, node.y, node.getAtom().getElement().getSymbol());
        text.setFill(Color.BLACK);
        g.getChildren().add(text);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
