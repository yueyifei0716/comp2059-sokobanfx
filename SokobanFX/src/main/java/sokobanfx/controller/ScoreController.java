package sokobanfx.controller;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import sokobanfx.model.ScoreList;
import sokobanfx.business.GameEngine;
import sokobanfx.util.ScoreRecord;
import sokobanfx.model.UserScore;

/**
 * ScoreController class is used to show the UserScore list to high score list.
 */
public class ScoreController {
    /**
     * The ListView in FXML
     */
    @FXML ListView<UserScore> listView;
    /**
     * The info in FXML
     */
    @FXML Text info;
    /**
     * The highest image in FXML
     */
    @FXML ImageView highest;
    /**
     * The scoreList
     */
    private ScoreList scoreList;
    /**
     * Initialize scoreRecord
     */
    ScoreRecord scoreRecord = new ScoreRecord();

    /**
     * Initializes the high score list.
     */
    @FXML
    public void initialize() {
        info.setText("Your number of moves is:   " + GameEngine.getMovesCount());
        scoreList = new ScoreList();
        scoreRecord.sortScore();
        scoreRecord.readScoreRecord(scoreList);
        showHighest();
        listView.setItems(FXCollections.observableList(scoreList.getScoreList()));
        listView.setStyle("-fx-font-size: 30; -fx-font-family: Arial; ");
        scoreList.getScoreList().addListener((ListChangeListener<? super UserScore>) c -> {
        });
    }


    /**
     * Shows the highest mark if the
     * score of the user is the highest.
     */
    public void showHighest() {
        Image award = new Image(getClass().getResource("/images/notation/highest.png").toString());
        highest.setImage(award);
        int score = GameEngine.getMovesCount();
        highest.setVisible(score == scoreList.userScores.get(0).getScore());

    }

    /**
     * Exits the game.
     *
     * @param actionEvent the click event
     */
    public void onExitPressed(ActionEvent actionEvent) {
        System.exit(0);
    }

}
