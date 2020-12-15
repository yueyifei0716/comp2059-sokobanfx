package sokobanfx.controller;

import java.awt.*;
import java.io.*;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.effect.Effect;
import javafx.scene.effect.MotionBlur;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sokobanfx.business.GameEngine;
import sokobanfx.business.GameLogger;
import sokobanfx.business.MusicPlayer;
import sokobanfx.model.*;
import sokobanfx.state.Context;
import sokobanfx.state.StopState;
import sokobanfx.view.RenderObjects;


/**
 * GameController class is used for handling
 * event includes loading game file, handling
 * button click events.
 */
public class GameController {

    /**
     * The game grid pane.
     */
    public GridPane gameGrid;
    /**
     * The game engine.
     */
    private GameEngine gameEngine;
    /**
     * The primary stage.
     */
    private Stage primaryStage;
    /**
     * The count label to show counter.
     */
    @FXML Label countLabel;
    /**
     * The level label to show level name.
     */
    @FXML Label levelLabel;
    /**
     * The user label to show user name.
     */
    @FXML Label userLabel;
    /**
     * The counter which shows the moves count.
     */
    IntegerProperty counter = new SimpleIntegerProperty(0);
    /**
     * The level name which shows the name of current level.
     */
    StringProperty levelName = new SimpleStringProperty();
    /**
     * The user name which shows the user name from the input.
     */
    StringProperty userName = new SimpleStringProperty();
    /**
     * The game state context.
     */
    Context context = new Context();
    /**
     * Initialize musicPlayer.
     */
    MusicPlayer musicPlayer = new MusicPlayer();


    /**
     * Initialize game status bar which
     * includes levelName, userName and counter.
     */
    @FXML
    public void initialize() {
        levelLabel.textProperty().bind(levelName);
        userLabel.textProperty().bind(userName);
        countLabel.textProperty().bind(counter.asString());
    }

    /**
     * Sets game counter.
     *
     * @param counter the counter
     */
    public void setCounter(int counter) {
        this.counter.set(counter);
    }

    /**
     * Sets level name.
     *
     * @param levelName the level name
     */
    public void setLevelName(String levelName) {
        this.levelName.set(levelName);
    }

    /**
     * Sets user name.
     *
     * @param userName the user name
     */
    public void setUserName(String userName) {
        this.userName.set(userName);
    }

    /**
     * Loads a default saved file
     *
     * @param primaryStage the primaryStage
     * @param skbFile the skb file
     */
    public void loadDefaultSaveFile(Stage primaryStage, String skbFile) {
        this.primaryStage = primaryStage;
        InputStream in = getClass().getClassLoader().getResourceAsStream(skbFile + ".skb");
        initializeGame(in);
        setEventFilter();
    }

    /**
     * Initialize a game.
     *
     * @param input the input
     */
    private void initializeGame(InputStream input) {
        gameEngine = GameEngine.getInstance(input, true);
        setLevelName(gameEngine.levelName);
        setUserName(gameEngine.userName);
        reloadGrid();
    }

    /**
     *  Sets an event filter which
     *  records the counter and
     *  keyboard event.
     */
    private void setEventFilter() {
        primaryStage.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (!gameEngine.isGameComplete()) {
                if (event.getCode().toString().equals("A")) {
                    GameEngine.setMovesCount(GameEngine.getMovesCount() + 10);
                }
                gameEngine.keyHandler.handleKey(event.getCode(), gameEngine);
                setCounter(GameEngine.getMovesCount());
            }
            reloadGrid();
        });
    }

    /**
     * Loads a new game from skb file.
     *
     * @throws FileNotFoundException if a file is not found
     */
    private void loadGameFile() throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Save File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Sokoban save file", "*.skb"));
        File saveFile = fileChooser.showOpenDialog(primaryStage);

        if (saveFile != null) {
            if (GameLogger.isDebugActive()) {
                GameLogger.showInfo("Loading save file: " + saveFile.getName());
            }
            initializeGame(new FileInputStream(saveFile));
        }
    }

    /**
     * reload the current game grid,
     * if the game is ended, show
     * the relevant dialog.
     */
    private void reloadGrid() {
        StopState stopState = new StopState();
        // if the whole game is complete, show the victory message
        if (GameEngine.getCurrentLevel().isDie()) {
            showFailMessage();
            stopState.setState(context);
            context.getGameState().showHighScore();
            return;
        } else if (gameEngine.isGameComplete()) {
            gameEngine.saveScore();
            showVictoryMessage();
            stopState.setState(context);
            context.getGameState().showHighScore();
            return;
        }
        // else
        Level currentLevel = GameEngine.getCurrentLevel();
        Level.LevelIterator levelGridIterator = (Level.LevelIterator) currentLevel.iterator();
        gameGrid.getChildren().clear();
        while (levelGridIterator.hasNext()) {
            addObjectToGrid(levelGridIterator.next(), levelGridIterator.getCurrentPosition());
        }
        gameGrid.autosize();
        primaryStage.sizeToScene();
    }

    /**
     * Shows the victory message.
     */
    private void showVictoryMessage() {
        String dialogTitle = "Game Over!";
        String dialogMessage = "You completed " + gameEngine.levelName + " in " + GameEngine.getMovesCount() + " moves!";
        MotionBlur mb = new MotionBlur(2, 3);

        newDialog(dialogTitle, dialogMessage, mb);
    }

    /**
     * Shows the failure message.
     */
    private void showFailMessage() {
        String dialogTitle = "Game Over!";
        String dialogMessage = "Sorry, You are failed!";
        MotionBlur mb = new MotionBlur(2, 3);
        newDialog(dialogTitle, dialogMessage, mb);
    }

    /**
     * Defines a new dialog.
     *
     * @param dialogTitle the dialog title
     * @param dialogMessage the dialog message
     * @param dialogMessageEffect the dialog message effect
     */
    private void newDialog(String dialogTitle, String dialogMessage, Effect dialogMessageEffect) {
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        dialog.setResizable(false);
        dialog.setTitle(dialogTitle);

        Text text1 = new Text(dialogMessage);
        text1.setTextAlignment(TextAlignment.CENTER);
        text1.setFont(javafx.scene.text.Font.font(14));

        if (dialogMessageEffect != null) {
            text1.setEffect(dialogMessageEffect);
        }

        VBox dialogVbox = new VBox(20);
        dialogVbox.setAlignment(Pos.CENTER);
        dialogVbox.setBackground(Background.EMPTY);
        dialogVbox.getChildren().add(text1);

        Scene dialogScene = new Scene(dialogVbox, 350, 150);
        dialog.setScene(dialogScene);
        dialog.show();
    }

    /**
     * Renders a GameObject to grid pane.
     *
     * @param gameObject the GameObject
     * @param location the GameObject location
     */
    private void addObjectToGrid(GameObject gameObject, Point location) {
        RenderObjects renderObjects = new RenderObjects();
        gameGrid.add(renderObjects.getObjectImage(gameObject), location.y, location.x);
    }

    /**
     * Exist the game.
     */
    public void closeGame() {
        System.exit(0);
    }

    /**
     * Save the current game.
     */
    public void saveGame() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save the game");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Files", "*.skb"));
    }

    /**
     * Load a new game.
     */
    public void loadGame() {
        try {
            loadGameFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Undo the previous movement.
     */
    public void undo() {
        Level level = GameEngine.getCurrentLevel();
        if (level.undo()) {
            GameEngine.setMovesCount(GameEngine.getMovesCount() - 1);
            setCounter(GameEngine.getMovesCount());
            reloadGrid();
        }
    }

    /**
     * Reset the whole level.
     */
    public void resetLevel() {
        Level level = GameEngine.getCurrentLevel();
        if (level.reset()) {
            GameEngine.setMovesCount(0);
            setCounter(GameEngine.getMovesCount());
            reloadGrid();
        }
    }

    /**
     * Show the author message.
     */
    public void showAbout() {
        String title = "About this game";
        String message = "Game created by Yifei YUE, 20125896\n";

        newDialog(title, message, null);
    }

    /**
     * Toggle music.
     */
    public void toggleMusic() {
        if (musicPlayer.isMusicActive()) {
            musicPlayer.stopMusic();
        } else {
            musicPlayer.playMusic();
        }
    }

    /**
     * Toggle debug.
     */
    public void toggleDebug() {
        GameLogger.toggleDebug();
        reloadGrid();
    }

}
