package GUI;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * This class will provide a view of a single atom at the subatomic level.
 * It is currently just a shell in which to program new things for Sprint 3.
 * To test all the atoms, just click the window to move to the next, or press any key.
 *
 * @author Sarah Larkin
 *
 * CS3141, Spring 2018, Team ATOM
 * Date Last Modified:  April 15, 2018.
 */
public class AtomTestViewer extends Application {

    int num = 1;
    Group group = new Group();
    Scene scene;

    @Override
    public void start(Stage primaryStage) throws Exception {

        group = new AtomView(num);
        scene = new Scene(group, 500, 500);
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                num++;
                if (num < 119) {
                    group = new AtomView(num);
                    scene.setRoot(group);
                    System.out.println(num);
                }
            }
        });
        scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                num++;
                if (num < 119) {
                    group = new AtomView(num);
                    scene.setRoot(group);
                    System.out.println(num);
                }
            }
        });
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String [] args) {
        launch(args);
    }


}
