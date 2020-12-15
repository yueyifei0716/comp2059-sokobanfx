package sokobanfx.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.awt.*;

/**
 * ScoreList class is used to record the UserScore objects which
 * includes user name and score.
 */
public class ScoreList {

    /**
     * The userScores list.
     */
    public ObservableList<UserScore> userScores;

    /**
     * Gets the {@link UserScore} list.
     */
    public ScoreList() {
        userScores = FXCollections.observableArrayList();
    }

    /**
     * Gets the UserScore list.
     *
     * @return the {@link UserScore} list
     */
    public ObservableList<UserScore> getScoreList() {
        return userScores;
    }

    /**
     * Adds a new user name and score to the userScore list.
     *
     * @param name the user name
     * @param moves the user moves count
     */
    public void addNewScore(String name, int moves) {
        UserScore userObject = new UserScore();
        userObject.setUserName(name);
        userObject.setScore(moves);
        userScores.add(userObject);
    }

}
