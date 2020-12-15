package sokobanfx.state;

import sokobanfx.Main;

import java.io.IOException;

public class StopState implements GameState {

    @Override
    public void setState(Context context) {
        context.setGameState(this);
    }

    @Override
    public void showHighScore() {
        try {
            Main.setRoot("/fxml/score");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
