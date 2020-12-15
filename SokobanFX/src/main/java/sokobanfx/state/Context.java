package sokobanfx.state;

/**
 * Context class is used for choosing the appropriate game context.
 */
public class Context {

    /**
     * The game state
     */
    private GameState gameState;

    /**
     * The constructor of game Context
     */
    public Context(){
        gameState = null;
    }

    /**
     * Sets the game state.
     *
     * @param gameState the game state
     */
    public void setGameState(GameState gameState){
        this.gameState = gameState;
    }

    /**
     * Gets the game state.
     *
     * @return the game state
     */
    public GameState getGameState(){
        return gameState;
    }
}
