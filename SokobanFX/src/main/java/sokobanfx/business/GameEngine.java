package sokobanfx.business;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;
import sokobanfx.controller.NameController;
import sokobanfx.model.GameGrid;
import sokobanfx.model.GameObject;
import sokobanfx.model.Level;
import sokobanfx.util.FileHandler;
import sokobanfx.util.ScoreRecord;

/**
 *  GameEngine is a singleton class which
 *  provide the whole control of the game.
 */
public class GameEngine {

    /**
     * The game name
     */
    public static final String GAME_NAME = "Sokoban";
    /**
     * The score recorder
     */
    public static ScoreRecord recorder;
    /**
     * The user name
     */
    public String userName;
    /**
     * The moves count
     */
    private static int movesCount = 0;
    /**
     * The level name
     */
    public String levelName;
    /**
     * The current level
     */
    private static Level currentLevel;
    /**
     * The level list which contains all
     * of the levels in a map set
     */
    private List<Level> levels;
    /**
     * The score of a user
     */
    public HashMap<String, Integer> score;
    /**
     * If the whole game is completed
     */
    private boolean gameComplete = false;
    /**
     * Initialize keyHandler
     */
    public KeyHandler keyHandler = new KeyHandler();
    /**
     * Initialize pressurePad
     */
    PressurePad pressurePad = new PressurePad();
    /**
     * Initialize fileHandler
     */
    FileHandler fileHandler = new FileHandler();
    /**
     * Initialize musicPlayer
     */
    MusicPlayer music = new MusicPlayer();

    /**
     *  Initialize private gameEngine object
     */
    private static GameEngine gameEngine = null;

    /**
     * Uses a {@link File} to load the game map containing all the levels or a single level.
     *
     * @param input the input file
     * @param production true if using the engine in live mode, false
     *                   only for testing mode.
     */
    private GameEngine(InputStream input, boolean production) {
        try {
            recorder = new ScoreRecord();
            levels = fileHandler.loadGameFile(input, this);
            currentLevel = getNextLevel();
            score = new HashMap<>();
            userName = NameController.name;

            if (production) {
               music.musicPlayer();
            }

        } catch (NoSuchElementException e) {
            GameLogger.showWarning("Cannot load the default save file: " + Arrays.toString(e.getStackTrace()));
        }
    }

    /**
     * Uses a {@link File} to load the game map containing all the levels or a single level.
     *
     * @param input the input file
     * @param production true if using the engine in live mode, false
     *                   only for testing mode.
     * @return the GameEngine object
     */
    public static GameEngine getInstance(InputStream input, boolean production) {
        if (gameEngine == null) {
            synchronized (GameEngine.class){
                if (gameEngine == null) {
                    gameEngine = new GameEngine(input, production);
                }
            }
        }
        return gameEngine;
    }

    /**
     * Returns the current level.
     *
     * @return the current level.
     */
    public static Level getCurrentLevel() {
        return currentLevel;
    }

    /**
     * Returns the next level in the list of levels.
     *
     * @return the next level loaded from the save file.
     */
    public Level getNextLevel() {

        if (levels.size() == 0) {
            return null;
        }
        if (currentLevel == null) {
            return levels.get(0);
        }
        int currentLevelIndex = currentLevel.getLevelIndex();
        if (currentLevelIndex + 1 < levels.size()) {
            return levels.get(currentLevelIndex + 1);
        } else {
            gameComplete = true;
            return null;
        }
    }


    /**
     * Handles the movement of the keeper and the objects that collide with it
     *
     * @param delta the movement delta
     */
    public void move(Point delta) {

        int x = (int) delta.getX();
        int y = (int) delta.getY();
        // get the position of keeper
        Point keeperPosition = currentLevel.getKeeperPosition();
        // create a keeper object based on the previous position
        GameObject keeper = currentLevel.getObjectsGrid().getGameObjectAt(keeperPosition);
        // get the target position of the keeper (the position after moving), which can also be the position of the current position of crate
        Point targetObjectPoint = GameGrid.translatePoint(keeperPosition, delta);
        // create a keeper target object based on the previous position (the position after moving)
        GameObject keeperTarget = currentLevel.getObjectsGrid().getGameObjectAt(targetObjectPoint);

        if (GameLogger.isDebugActive()) {
            System.out.println("Current level state:");
            System.out.println(currentLevel.toString());
            System.out.println("Keeper pos: " + keeperPosition);
            System.out.println("Movement source obj: " + keeper);
            System.out.printf("Target object: %s at [%s]", keeperTarget, targetObjectPoint);
        }
        // if the keeper can move
        boolean keeperMoved = false;

        switch (keeperTarget) {
            case WALL:
            case BOUNDARY:
            case GATE:
                break;

            case TRAP_M:
                currentLevel.setDie(true);
                setIsGameComplete(true);
                break;

            case TRAP_R:
                currentLevel.saveMove();
                currentLevel.setReverse(true);
                moveFloor(delta);
                break;

            // if the position after moving is Crate
            case CRATE:
                currentLevel.saveMove();
                keeperMoved = moveCrate(delta, targetObjectPoint, keeperTarget);
                break;

            // if the crate target is FLOOR
            case FLOOR:
                currentLevel.saveMove();
                keeperMoved = moveFloor(delta);
                break;

            case PIPE_UP:
                currentLevel.saveMove();
                if (x == 1 && y == 0) {
                    keeperMoved = movePipe(x, y, targetObjectPoint);
                }
                break;

            case PIPE_DOWN:
                currentLevel.saveMove();
                if (x == -1 && y == 0) {
                    keeperMoved = movePipe(x, y, targetObjectPoint);
                }
                break;

            case PIPE_LEFT:
                currentLevel.saveMove();
                if (x == 0 && y == 1) {
                    keeperMoved = movePipe(x, y, targetObjectPoint);
                }
                break;

            case PIPE_RIGHT:
                currentLevel.saveMove();
                if (x == 0 && y == -1) {
                    keeperMoved = movePipe(x, y, targetObjectPoint);
                }
                break;

            default:
                GameLogger.showSevere("The object to be moved was not a recognised GameObject.");
                throw new AssertionError("This should not have happened. Report this problem to the developer.");
        }

        if (keeperMoved) {
            keeperPosition.translate(x, y);
            movesCount++;
            pressurePad.updatePressurePad(getCurrentLevel());

            if (currentLevel.isLevelComplete()) {
                if (GameLogger.isDebugActive()) {
                    System.out.println("Level complete!");
                }
                if (getNextLevel() != null) {
                    currentLevel = getNextLevel();
                } else {
                    setIsGameComplete(true);
                }
            }
        }
    }

    /**
     * Returns true if the game is complete.
     *
     * @return true if the game is complete, false otherwise
     */
    public boolean isGameComplete() {
        return gameComplete;
    }

    /**
     * Sets if the game is complete or not
     *
     * @param gameComplete if the game complete
     */
    public void setIsGameComplete(boolean gameComplete) {
        this.gameComplete = gameComplete;
    }

    /**
     * Saves the score which includes level name,
     * user name and moves count.
     */
    public void saveScore() {
        recorder.saveScore(levelName, userName, movesCount);
    }

    /**
     * Gets the moves count.
     *
     * @return the moves count
     */
    public static int getMovesCount() {
        return movesCount;
    }

    /**
     * Sets the moves count.
     *
     * @param movesCount the moves count
     */
    public static void setMovesCount(int movesCount) {
        GameEngine.movesCount = movesCount;
    }

    /**
     * Check if the keeper can move through pipes
     *
     * @param x delta in x
     * @param y delta in y
     * @param targetObjectPoint the targetObject of keeper
     * @return if the move is crossed the pipes
     */
    public boolean movePipe(int x, int y, Point targetObjectPoint) {
        if (x == 0 && y == -1) {
            x = 1;
        }
        for (Point pipe: currentLevel.getObjectsGrid().getPipes()) {
            if (!pipe.equals(targetObjectPoint)) {
                Point nextPosition = new Point((int) pipe.getX() + x, (int) pipe.getY() - y);
                if (currentLevel.getObjectsGrid().getGameObjectAt(nextPosition) == GameObject.FLOOR) {
                    // put keeper in the keeper target position
                    currentLevel.getObjectsGrid().removeGameObjectAt(currentLevel.getKeeperPosition());
                    currentLevel.getObjectsGrid().putGameObjectAt(GameObject.KEEPER, nextPosition);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Moves on floor.
     *
     * @param delta the distance
     * @return true
     */
    public boolean moveFloor(Point delta) {
        Point keeper = currentLevel.getKeeperPosition();
        currentLevel.getObjectsGrid().removeGameObjectAt(currentLevel.getKeeperPosition());
        currentLevel.getObjectsGrid().putGameObjectAt(GameObject.KEEPER, GameGrid.translatePoint(keeper, delta));

        return true;
    }

    /**
     * Moves the crate.
     *
     * @param delta the distance
     * @param targetObjectPoint the target object point
     * @param keeperTarget the target object of keeper
     * @return true if the crate can be moved, otherwise false
     */
    public boolean moveCrate(Point delta, Point targetObjectPoint, GameObject keeperTarget) {

        GameObject crateTarget = currentLevel.getObjectsGrid().getTargetFromSource(targetObjectPoint, delta);
        // if the crate target is not FLOOR, then can not move
        if (crateTarget != GameObject.FLOOR) {
            return false;
        }
        Point keeperPosition = currentLevel.getKeeperPosition();
        // put the crate target in the previous crate position
        currentLevel.getObjectsGrid().putGameObjectAt(currentLevel.getObjectsGrid().getGameObjectAt(GameGrid.translatePoint(targetObjectPoint, delta)), targetObjectPoint);
        // put crate in the crate target position
        currentLevel.getObjectsGrid().putGameObjectAt(keeperTarget, GameGrid.translatePoint(targetObjectPoint, delta));
        // put keeper target in the previous keeper position
        currentLevel.getObjectsGrid().putGameObjectAt(currentLevel.getObjectsGrid().getGameObjectAt(GameGrid.translatePoint(keeperPosition, delta)), keeperPosition);
        // put keeper in the keeper target position
        currentLevel.getObjectsGrid().putGameObjectAt(GameObject.KEEPER, GameGrid.translatePoint(keeperPosition, delta));
        return true;
    }

}