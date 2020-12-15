package sokobanfx.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import sokobanfx.Main;

import java.io.IOException;

/**
 * LevelSelectController class is used to select a level to play.
 */
public class LevelSelectController {

    /**
     * Creates a gameController object to load game file.
     */
    GameController gameController = new GameController();

    /**
     * Loads an level file.
     *
     * @param file the level file
     * @throws IOException if the file is not found or unable to read
     */
    private void loadLevelFile(String file) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/sokoban.fxml"));
        Parent root = fxmlLoader.load();
        Main.getScene().setRoot(root);
        // Load the window of sokoban game
        gameController = fxmlLoader.getController();
        gameController.loadDefaultSaveFile(Main.getPrimaryStage(), file);
    }

    /**
     * Loads the level-1 file.
     *
     * @param actionEvent the click event
     * @throws IOException if the file is not found or unable to read
     */
    public void onLevel1Pressed(ActionEvent actionEvent) throws IOException {
        loadLevelFile("level/Level1");
    }

    /**
     * Loads the level-2 file.
     *
     * @param actionEvent the click event
     * @throws IOException if the file is not found or unable to read
     */
    public void onLevel2Pressed(ActionEvent actionEvent) throws IOException {
        loadLevelFile("level/Level2");
    }

    /**
     * Loads the level-3 file.
     *
     * @param actionEvent the click event
     * @throws IOException if the file is not found or unable to read
     */
    public void onLevel3Pressed(ActionEvent actionEvent) throws IOException {
        loadLevelFile("level/Level3");
    }

    /**
     * Loads the level-4 file.
     *
     * @param actionEvent the click event
     * @throws IOException if the file is not found or unable to read
     */
    public void onLevel4Pressed(ActionEvent actionEvent) throws IOException {
        loadLevelFile("level/Level4");
    }

    /**
     * Loads the level-5 file.
     *
     * @param actionEvent the click event
     * @throws IOException if the file is not found or unable to read
     */
    public void onLevel5Pressed(ActionEvent actionEvent) throws IOException {
        loadLevelFile("level/Level5");
    }

    /**
     * Loads the normal mode file.
     *
     * @param actionEvent the click event
     * @throws IOException if the file is not found or unable to read
     */
    public void onNormalPressed(ActionEvent actionEvent) throws IOException {
        loadLevelFile("level/Normal");
    }

    /**
     * Loads an level file.
     *
     * @param actionEvent the click event
     * @throws IOException if the file is not found or unable to read
     */
    public void onLoadPressed(ActionEvent actionEvent) throws IOException {
        gameController.loadGame();
    }

    /**
     * Goes back to the main menu.
     *
     * @param actionEvent the click event
     * @throws IOException if the file is not found or unable to read
     */
    public void onBackPressed(ActionEvent actionEvent) throws IOException {
        Main.setRoot("/fxml/menu");
    }

}
