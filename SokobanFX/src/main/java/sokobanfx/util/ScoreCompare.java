package sokobanfx.util;

import sokobanfx.model.UserScore;

import java.util.Comparator;

/**
 * ScoreCompare class is used to compare to UserScore according to the score.
 */
public class ScoreCompare implements Comparator<UserScore> {

    /**
     * Compares two UserScores object according to the score.
     *
     * @param user1 a UserScore
     * @param user2 a UserScore
     * @return an negative integer when user1 is less than user2, and versa sa
     */
    @Override
    public int compare(UserScore user1, UserScore user2)
    {
        return user1.getScore() - user2.getScore();
    }

}
