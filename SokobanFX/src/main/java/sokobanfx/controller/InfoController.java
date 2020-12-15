package sokobanfx.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import java.io.IOException;
import sokobanfx.Main;

/**
 * Showing the game operation tutorial in fxml.
 */
public class InfoController {

    /**
     * Go back to the main menu
     * when press on the Back
     * button
     * @param actionEvent mouse action
     * @throws IOException if the menu file is not found
     */
    public void onBackPressed(ActionEvent actionEvent) throws IOException {
        Main.setRoot("/fxml/menu");
    }

}
