package sokobanfx.model;

import java.io.Serializable;

/**
 *  UserScore class is used to bind the user name
 *  and its corresponding score
 */
public class UserScore implements Serializable {

    /**
     * The user name that entered by the user
     */
    private String userName;
    /**
     * The score of the user
     */
    private int score;

    /**
     * The default constructor
     */
    public UserScore() {}

    /**
     * Creates a UserScore to contain the user name and score.
     *
     * @param userName the user name
     * @param score    the user score
     */
    public UserScore(String userName, int score) {
        this.userName = userName;
        this.score = score;
    }

    /**
     * Gets the score.
     *
     * @return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the score.
     *
     * @param score the score
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Gets the user name.
     *
     * @return the user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the user name.
     *
     * @param userName the user name
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Returns a String which includes the user name and it score.
     *
     * @return the user name and its score
     */
    @Override
    public String toString(){
        return (this.userName + "\t\t\t\t" + this.score);
    }

}
