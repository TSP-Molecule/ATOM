package GUI;

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import structures.enums.Elem;

import java.util.ArrayList;

/**
 * Depicts test molecules developed as fillers and to gain understanding of 3D structures
 * The three molecules can be viewed by repeatedly clicking the screen.
 *
 * @author Sarah Larkin
 *
 * CS3141, R02, Spring 2018, Team ATOM
 * Date Last Modified: April 15, 2018
 */
public class TestMolecules extends Application {

    int mol = 1;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group group = saltMolecule();

        SubScene sub = new SubScene(group, 500, 700, true, SceneAntialiasing.BALANCED);
        BorderPane gridPane = new BorderPane();
        gridPane.setCenter(sub);
        group.getTransforms().add(new Translate(200, 200, 0));
        group.getTransforms().add(new Rotate(30, 200, 200, 0, Rotate.Y_AXIS));
        group.getTransforms().add(new Rotate(30, 200, 200, 0, Rotate.Z_AXIS));
        Scene scene = new Scene(group, 800, 800, true, SceneAntialiasing.BALANCED);
        scene.setOnMouseClicked(event -> {
            mol++;
            if (mol == 4) {
                mol = 0;
            }
            if (mol == 2) {
                scene.setRoot(salt());
                scene.getRoot().getTransforms().add(new Translate(200, 200, 0));
                scene.getRoot().getTransforms().add(new Rotate(30, 200, 200, 0, Rotate.Y_AXIS));
                scene.getRoot().getTransforms().add(new Rotate(30, 200, 200, 0, Rotate.Z_AXIS));
            } else if (mol == 3) {
                scene.setRoot(waterMolecule());
                scene.getRoot().getTransforms().add(new Translate(200, 200, 0));
                scene.getRoot().getTransforms().add(new Rotate(30, 200, 200, 0, Rotate.Y_AXIS));
                scene.getRoot().getTransforms().add(new Rotate(30, 200, 200, 0, Rotate.Z_AXIS));
            } else {
                scene.setRoot(saltMolecule());
                scene.getRoot().getTransforms().add(new Translate(200, 200, 0));
                scene.getRoot().getTransforms().add(new Rotate(30, 200, 200, 0, Rotate.Y_AXIS));
                scene.getRoot().getTransforms().add(new Rotate(30, 200, 200, 0, Rotate.Z_AXIS));
            }

        });
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // TEST MOLECULES

    /**
     * Depicts a hard-coded salt molecule using colors from the element enum.
     *
     * @return the group of shapes that represent the salt molecule
     */
    public Group saltMolecule() {
        Group group = new Group();
        Color c = Elem.getBySymbol("Cl").getColor();
        Color s = Elem.getBySymbol("Na").getColor();
        int bond = 300;

        Sphere c1 = new Sphere(50);
        c1.setMaterial(new PhongMaterial(c));
        c1.setTranslateX(300);
        c1.setTranslateY(0);
        c1.setTranslateZ(0);

        Sphere c2 = new Sphere(50);
        c2.setMaterial(new PhongMaterial(c));
        c2.setTranslateX(0);
        c2.setTranslateY(300);
        c2.setTranslateZ(0);

        Sphere c3 = new Sphere(50);
        c3.setMaterial(new PhongMaterial(c));
        c3.setTranslateX(300);
        c3.setTranslateY(300);
        c3.setTranslateZ(300);

        Sphere c4 = new Sphere(50);
        c4.setMaterial(new PhongMaterial(c));
        c4.setTranslateX(0);
        c4.setTranslateY(0);
        c4.setTranslateZ(300);

        Sphere s1 = new Sphere(50);
        s1.setMaterial(new PhongMaterial(s));
        s1.setTranslateX(0);
        s1.setTranslateY(0);
        s1.setTranslateZ(0);

        Sphere s2 = new Sphere(50);
        s2.setMaterial(new PhongMaterial(s));
        s2.setTranslateX(300);
        s2.setTranslateY(300);
        s2.setTranslateZ(0);

        Sphere s3 = new Sphere(50);
        s3.setMaterial(new PhongMaterial(s));
        s3.setTranslateX(300);
        s3.setTranslateY(0);
        s3.setTranslateZ(300);

        Sphere s4 = new Sphere(50);
        s4.setMaterial(new PhongMaterial(s));
        s4.setTranslateX(0);
        s4.setTranslateY(300);
        s4.setTranslateZ(300);

        double p = 150;
        ArrayList<Cylinder> bondList = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            Cylinder cy = new Cylinder(10, bond);
            PhongMaterial mat = new PhongMaterial(Color.LIGHTGREY);
            mat.setSpecularColor(Color.LIGHTGREY);
            mat.setDiffuseColor(Color.LIGHTGREY);
            cy.setMaterial(mat);
            bondList.add(cy);
        }

        for (int i = 0; i < 4; i++) {
            Cylinder cy = bondList.get(i);
            cy.setTranslateX(150);
            cy.setRotationAxis(Rotate.Z_AXIS);
            cy.setRotate(90);

            switch (i) {
                case 0:
                    cy.setTranslateZ(0);
                    break;
                case 1:
                    cy.setTranslateZ(300);
                    break;
                case 2:
                    cy.setTranslateZ(300);
                    cy.setTranslateY(300);
                    break;
                case 3:
                    cy.setTranslateZ(0);
                    cy.setTranslateY(300);
                    break;
            }
        }

        for (int i = 4; i < 8; i++) {
            Cylinder cy = bondList.get(i);
            cy.setTranslateY(150);

            switch (i) {
                case 4:
                    cy.setTranslateX(0);
                    cy.setTranslateZ(0);
                    break;
                case 5:
                    cy.setTranslateX(0);
                    cy.setTranslateZ(300);
                    break;
                case 6:
                    cy.setTranslateX(300);
                    cy.setTranslateZ(0);
                    break;
                case 7:
                    cy.setTranslateX(300);
                    cy.setTranslateZ(300);
                    break;
            }
        }

        for (int i = 8; i < 12; i++) {
            Cylinder cy = bondList.get(i);
            cy.setTranslateZ(150);
            cy.setRotationAxis(Rotate.X_AXIS);
            cy.setRotate(90);

            switch (i) {
                case 8:
                    cy.setTranslateX(0);
                    cy.setTranslateY(0);
                    break;
                case 9:
                    cy.setTranslateX(300);
                    cy.setTranslateY(0);
                    break;
                case 10:
                    cy.setTranslateX(300);
                    cy.setTranslateY(300);
                    break;
                case 11:
                    cy.setTranslateX(0);
                    cy.setTranslateY(300);
                    break;
            }
        }
        for (Cylinder cy : bondList) {
            group.getChildren().add(cy);
        }

        group.getChildren().addAll(s1, s2, s3, s4, c1, c2, c3, c4);

        return group;
    }

    /**
     * Depicts a hard-coded water molecule.
     *
     * @return the group containing the shapes representing the water molecule.
     */
    public Group waterMolecule() {
        Group group = new Group();

        Sphere hydrogen = new Sphere(50);
        PhongMaterial whiteMat = new PhongMaterial();
        whiteMat.setDiffuseColor(Color.ANTIQUEWHITE);
        whiteMat.setSpecularColor(Color.ANTIQUEWHITE);
        hydrogen.setMaterial(whiteMat);
        int bond = 200;

        Sphere oxygen = new Sphere(50);
        PhongMaterial redMat = new PhongMaterial();
        redMat.setSpecularColor(Color.RED);
        redMat.setDiffuseColor(Color.RED);
        oxygen.setMaterial(redMat);

        Sphere hydrogen1 = new Sphere(50);
        hydrogen1.setMaterial(whiteMat);

        Cylinder b1 = new Cylinder(10, bond);
        PhongMaterial blackM = new PhongMaterial();
        blackM.setDiffuseColor(Color.BLACK);
        blackM.setSpecularColor(Color.BLACK);
        b1.setMaterial(blackM);

        Cylinder b2 = new Cylinder(10, bond);
        b2.setMaterial(blackM);

        oxygen.setTranslateX(0);
        oxygen.setTranslateY(0);
        oxygen.setTranslateZ(0);

        hydrogen.setTranslateX(bond * Math.cos(-1 * Math.PI / 3));
        hydrogen.setTranslateY(bond * Math.sin(-1 * Math.PI / 3));
        hydrogen.setTranslateZ(0);

        hydrogen1.setTranslateX(bond * Math.cos(Math.PI / 3));
        hydrogen1.setTranslateY(bond * Math.sin(Math.PI / 3));
        hydrogen1.setTranslateZ(0);

        b1.setTranslateX(bond * Math.cos(-1 * Math.PI / 3) / 2 - 0);
        b1.setTranslateY(bond * Math.sin(-1 * Math.PI / 3) / 2 + 0);
        b1.setTranslateZ(0);
        b1.setRotationAxis(Rotate.Z_AXIS);
        b1.setRotate(30);

        b2.setTranslateX(bond * Math.cos(Math.PI / 3) / 2);
        b2.setTranslateY(bond * Math.sin(Math.PI / 3) / 2);
        b2.setTranslateZ(0);
        b2.setRotationAxis(Rotate.Z_AXIS);
        b2.setRotate(150);

        group.getChildren().addAll(b1, b2, hydrogen, oxygen, hydrogen1);

        return group;

    }

    public Group salt() {
        Group group = new Group();
        Color c = Elem.getBySymbol("Cl").getColor();
        Color s = Elem.getBySymbol("Na").getColor();
        int bond = 300;

        Sphere c1 = atom(Elem.getBySymbol("Cl"));
        //        c1.setMaterial(new PhongMaterial(c));
        c1.setTranslateX(300);
        c1.setTranslateY(0);
        c1.setTranslateZ(0);

        Sphere c2 = atom(Elem.getBySymbol("Cl"));
        //        c2.setMaterial(new PhongMaterial(c));
        c2.setTranslateX(0);
        c2.setTranslateY(300);
        c2.setTranslateZ(0);

        Sphere c3 = atom(Elem.getBySymbol("Cl"));
        //        c3.setMaterial(new PhongMaterial(c));
        c3.setTranslateX(300);
        c3.setTranslateY(300);
        c3.setTranslateZ(300);

        Sphere c4 = atom(Elem.getBySymbol("Cl"));
        //        c4.setMaterial(new PhongMaterial(c));
        c4.setTranslateX(0);
        c4.setTranslateY(0);
        c4.setTranslateZ(300);

        Sphere s1 = atom(Elem.getBySymbol("Na"));
        //        s1.setMaterial(new PhongMaterial(s));
        s1.setTranslateX(0);
        s1.setTranslateY(0);
        s1.setTranslateZ(0);

        Sphere s2 = atom(Elem.getBySymbol("Na"));
        //        s2.setMaterial(new PhongMaterial(s));
        s2.setTranslateX(300);
        s2.setTranslateY(300);
        s2.setTranslateZ(0);

        Sphere s3 = atom(Elem.getBySymbol("Na"));
        //        s3.setMaterial(new PhongMaterial(s));
        s3.setTranslateX(300);
        s3.setTranslateY(0);
        s3.setTranslateZ(300);

        Sphere s4 = atom(Elem.getBySymbol("Na"));
        //        s4.setMaterial(new PhongMaterial(s));
        s4.setTranslateX(0);
        s4.setTranslateY(300);
        s4.setTranslateZ(300);

        double p = 150;
        ArrayList<Cylinder> bondList = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            Cylinder cy = new Cylinder(10, bond);
            cy.setMaterial(new PhongMaterial(Color.LIGHTGREY));
            bondList.add(cy);
        }

        for (int i = 0; i < 4; i++) {
            Cylinder cy = bondList.get(i);
            cy.setTranslateX(150);
            cy.setRotationAxis(Rotate.Z_AXIS);
            cy.setRotate(90);

            switch (i) {
                case 0:
                    cy.setTranslateZ(0);
                    break;
                case 1:
                    cy.setTranslateZ(300);
                    break;
                case 2:
                    cy.setTranslateZ(300);
                    cy.setTranslateY(300);
                    break;
                case 3:
                    cy.setTranslateZ(0);
                    cy.setTranslateY(300);
                    break;
            }
        }

        for (int i = 4; i < 8; i++) {
            Cylinder cy = bondList.get(i);
            cy.setTranslateY(150);

            switch (i) {
                case 4:
                    cy.setTranslateX(0);
                    cy.setTranslateZ(0);
                    break;
                case 5:
                    cy.setTranslateX(0);
                    cy.setTranslateZ(300);
                    break;
                case 6:
                    cy.setTranslateX(300);
                    cy.setTranslateZ(0);
                    break;
                case 7:
                    cy.setTranslateX(300);
                    cy.setTranslateZ(300);
                    break;
            }
        }

        for (int i = 8; i < 12; i++) {
            Cylinder cy = bondList.get(i);
            cy.setTranslateZ(150);
            cy.setRotationAxis(Rotate.X_AXIS);
            cy.setRotate(90);

            switch (i) {
                case 8:
                    cy.setTranslateX(0);
                    cy.setTranslateY(0);
                    break;
                case 9:
                    cy.setTranslateX(300);
                    cy.setTranslateY(0);
                    break;
                case 10:
                    cy.setTranslateX(300);
                    cy.setTranslateY(300);
                    break;
                case 11:
                    cy.setTranslateX(0);
                    cy.setTranslateY(300);
                    break;
            }
        }
        for (Cylinder cy : bondList) {
            group.getChildren().add(cy);
        }

        group.getChildren().addAll(s1, s2, s3, s4, c1, c2, c3, c4);

        return group;
    }

    public Sphere atom(Elem elem) {
        Color c = elem.getColor();
        PhongMaterial material = new PhongMaterial();
        material.setSpecularColor(c);
        material.setDiffuseColor(c);
        Sphere sphere = new Sphere(50);
        sphere.setMaterial(material);
        return sphere;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
