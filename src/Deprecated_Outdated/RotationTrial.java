package GUI;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class RotationTrial extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group g = new Group();
        Cylinder c = new Cylinder(10, 200);
        c.setMaterial(new PhongMaterial(Color.RED));
        g.getChildren().add(c);
        c.setTranslateY(200);
        c.setTranslateX(200);
        c.setRotationAxis(Rotate.Z_AXIS);
        c.setRotate(120);

        Scene scene = new Scene(g, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String [] args) {
        launch(args);
    }
}
