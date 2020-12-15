package sokobanfx.business;

import javafx.scene.input.KeyCode;
import sokobanfx.controller.GameController;

import java.awt.*;

/**
 * KeyHandler class is used for handling keyboard input.
 */
public class KeyHandler implements IKeyHandler {

    /**
     * Converts keyboard input to point movement,
     * if stepped on the reverse trap before,
     * then reverse move direction.
     *
     * @param code the keyInput code
     * @param gameEngine the game engine
     */
    @Override
    public void handleKey(KeyCode code, GameEngine gameEngine) {
        int direct = 1;
        if (GameEngine.getCurrentLevel().isReverse()) {
            direct = -1;
        }
        switch (code) {
            case UP:
                gameEngine.move(new Point(-direct, 0));
                updateDirection(code);
                break;

            case RIGHT:
                gameEngine.move(new Point(0, direct));
                updateDirection(code);
                break;

            case DOWN:
                gameEngine.move(new Point(direct, 0));
                updateDirection(code);
                break;

            case LEFT:
                gameEngine.move(new Point(0, -direct));
                updateDirection(code);
                break;

            case A:
                GameEngine.getCurrentLevel().saveMove();
                GameEngine.getCurrentLevel().attackWall();
                break;

            default:
                // TODO: implement something funny.
        }

        if (GameLogger.isDebugActive()) {
            System.out.println(code);
        }
    }

    /**
     * Changes the keeper direction.
     *
     * @param code the keyInput code
     */
    @Override
    public void updateDirection(KeyCode code) {
        GameEngine.getCurrentLevel().setPlayerDirection(code.toString());
    }
}
