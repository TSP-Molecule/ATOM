package GUI;
import javafx.scene.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

/**
 * Creates camera controls for the 3D Molecule subscene.
 * @author Sarah Larkin
 * CS3141, R02, Spring 2018
 * Date Last Modified:  April 11, 2018
 *
 */
public class MoleculeCamera extends PerspectiveCamera {

    private SubScene sub;
    private final double dist = 50;
    private final double theta = 10;
    private boolean is3D = true;

    public MoleculeCamera (SubScene sub, boolean b) {
        this.sub = sub;
//        makeCamera();
        is3D = b;
        if (b) {
            makeCamera();
        }
    }

    /**
     * Makes a camera and attaches key controls
     * @return the camera
     */
    public Camera makeCamera() {
        Camera cam = new PerspectiveCamera(true);
        setNearClip(0.1);
        setFarClip(100000);
        if (is3D) {
            getTransforms().add(new Translate(-100, -200, -1500));
        }
        sub.setOnKeyPressed(event -> {
            camControls(event);
        });
        return cam;
    }


    /**
     * Creates camera controls based on a key event.
     * @param event the key event
     */
    private void camControls(KeyEvent event) {
        if(is3D) {
            rotationControls(event);
            setRotationAxis(Rotate.Y_AXIS);
            setRotate(45);
        } else {
            sub.getRoot().getTransforms().add(new Rotate(50, 0, 0, 0, Rotate.Y_AXIS));
            sub.getRoot().getTransforms().add(new Translate(-300, -200, -100));
        }
        translationControls(event);
    }

    /**
     * Controls rotation of the scene objects based on keyboard input
     * @param event the key event
     */
    private void rotationControls(KeyEvent event) {
        if (!sub.isFocused()) {
            sub.requestFocus();
        }
        switch (event.getCode()) {
            case L:
                sub.getRoot().getTransforms().add(new Rotate(theta, 0, 0, -100, Rotate.Y_AXIS));
                break;
            case J:
                sub.getRoot().getTransforms().add(new Rotate(-theta, 0, 0, 0, Rotate.Y_AXIS));
                break;
            case I:
                sub.getRoot().getTransforms().add(new Rotate(1, 0, 0, 0, Rotate.X_AXIS));
                break;
            case K:
                sub.getRoot().getTransforms().add(new Rotate(-1, 0, 0, 0, Rotate.X_AXIS));
                break;
            case Y:
                sub.getRoot().getTransforms().add(new Rotate(1, 0, 0, 0, Rotate.Z_AXIS));
                break;
            case H:
                sub.getRoot().getTransforms().add(new Rotate(-1, 0, 0, 0, Rotate.Z_AXIS));
                break;
        }
    }

    /**
     * Controls translateion of the camera based on keyboard input
     * @param event the key event
     */
    private void translationControls(KeyEvent event) {
        switch (event.getCode()) {
            case W:
                getTransforms().add(new Translate(0, dist, 0));
                break;
            case S:
                getTransforms().add(new Translate(0, -dist, 0));
                break;
            case A:
                getTransforms().add(new Translate(dist, 0, 0));
                break;
            case D:
                getTransforms().add(new Translate(-dist, 0, 0));
                break;
            case R:
                getTransforms().add(new Translate(0, 0, -dist));
                break;
            case F:
                getTransforms().add(new Translate(0, 0, dist));
                break;
        }
    }
}
