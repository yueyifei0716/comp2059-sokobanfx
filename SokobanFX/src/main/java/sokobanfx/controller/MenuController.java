package sokobanfx.controller;

import javafx.event.ActionEvent;
import java.io.IOException;
import sokobanfx.Main;

/**
 * MenuController class is used to specify event handling.
 */
public class MenuController {

    /**
     * Goes to start menu.
     *
     * @param event the click event
     * @throws IOException if the file is not found or unable to read
     */
    public void onStartPressed(ActionEvent event) throws IOException {
        Main.setRoot("/fxml/username");
    }

    /**
     * Goes to about menu.
     *
     * @param event the click event
     * @throws IOException if the file is not found or unable to read
     */
    public void onAboutPressed(ActionEvent event) throws IOException {
        Main.setRoot("/fxml/info");
    }

    /**
     * Shuts down the game
     *
     * @param event the click event
     */
    public void onExitPressed(ActionEvent event) {
        System.exit(0);
    }

}
