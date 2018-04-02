package GUI;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import structures.enums.Elem;
import web.WebService;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class AtomInfo extends GridPane {


    public AtomInfo(Elem element) throws IOException {
        super();
        setPrefSize(500,600);

        String wiki = WebService.getWikiAsString(element.getName());

        TextArea text = new TextArea();
        text.setText(wiki);
        text.setEditable(false);
        text.setWrapText(true);
//        text.setMinSize(, gridText.getHeight());
        add(text, 0, 0);

        Button buttonBrowser = new Button("View In Browser");

        buttonBrowser.setOnMouseClicked(event -> {
            if (Desktop.isDesktopSupported()) {
                try {
                    Desktop.getDesktop().browse(new URI("http://www.wikipedia.com/wiki/" + element.getName()));
                } catch (IOException | URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        });

        add(buttonBrowser, 0, 1);
        setHalignment(buttonBrowser, HPos.CENTER);

        // Grid Properties
        RowConstraints rowConstraints0 = new RowConstraints();
        rowConstraints0.setPercentHeight(90);
        getRowConstraints().add(rowConstraints0);

        RowConstraints rowConstraints1 = new RowConstraints();
        rowConstraints1.setPercentHeight(10);
        getRowConstraints().add(rowConstraints1);


    }


}

