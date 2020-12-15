package sokobanfx.state;

public class StartState implements GameState {

    @Override
    public void setState(Context context) {
        context.setGameState(this);
    }

    @Override
    public void showHighScore() {}

}
