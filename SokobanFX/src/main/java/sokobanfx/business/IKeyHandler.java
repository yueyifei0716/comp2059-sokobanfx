package sokobanfx.business;

import javafx.scene.input.KeyCode;

public interface IKeyHandler {
    void handleKey(KeyCode code, GameEngine gameEngine);
    void updateDirection(KeyCode code);
}
