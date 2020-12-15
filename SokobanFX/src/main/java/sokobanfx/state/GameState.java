package sokobanfx.state;

import java.io.IOException;

/**
 * GameState class is used for
 * choosing the game state.
 */
public interface GameState {

    /**
     * Sets the game state.
     *
     * @param context the game context
     * @throws IOException if the score list is found or not
     */
    void setState(Context context) throws IOException;

    /**
     * Shows the high score list
     */
    void showHighScore();
}
