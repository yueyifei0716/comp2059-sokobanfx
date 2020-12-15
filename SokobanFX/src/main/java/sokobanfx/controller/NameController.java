package sokobanfx.controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import sokobanfx.Main;

/**
 * NameController class is used to get
 * the name of user from the user' input.
 */
public class NameController {

    @FXML
    private TextField userName;
    public static String name;

    public void initialize() { }

    /**
     * Go back to the level selection menu
     * when press on the Confirm button
     * @param actionEvent click action
     * @throws IOException if the level_selection file is not found
     */
    public void onConfirmPressed(ActionEvent actionEvent) throws IOException {
        if (userName.getText() == null || userName.getText().trim().isEmpty()) {
            return;
        }
        name = userName.getText();
        Main.setRoot("/fxml/level_selection");
    }


    /**
     * Go back to the main menu
     * when press on the Back
     * button
     * @param actionEvent click action
     * @throws IOException if the menu file is not found
     */
    public void onBackPressed(ActionEvent actionEvent) throws IOException {
        Main.setRoot("/fxml/menu");
    }

}
