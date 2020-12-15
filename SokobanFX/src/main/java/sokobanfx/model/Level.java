package sokobanfx.model;

import sokobanfx.business.GameEngine;
import sokobanfx.business.GameLogger;
import sokobanfx.model.direction.DirectionFactory;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *  Level class is used for generates two game
 *  grids and detect the game status of current level.
 */
public final class Level implements Iterable<GameObject> {
    /**
     * The objects grid
     */
    private final GameGrid objectsGrid;
    /**
     * The potential grid
     */
    private final GameGrid potentialGrid;

    /**
     * The level name
     */
    private final String name;
    /**
     * The level index
     */
    private final int index;
    /**
     * The number of diamonds
     */
    private int numberOfDiamonds = 0;
    /**
     * The player direction
     */
    private String playerDirection;
    /**
     * The gate position
     */
    private Point gate;
    /**
     * The move history arraylist
     */
    public final ArrayList<MoveHistory> moveHistory;
    /**
     * If the player is dead in this level
     */
    private boolean die;
    /**
     * If the player's direction is reversed
     */
    private boolean reverse;

    /**
     * The direction factory used for handling wall attack
     */
    DirectionFactory directionFactory = new DirectionFactory();


    /**
     * Creates a level using the first parameter as the level name and the second parameter as {@link List} of
     * {@link String}, each one containing the characters corresponding to a specific game object
     *
     * @param levelName  the name of the level
     * @param levelIndex the number used as index for the levels
     * @param rawLevel  the raw data of the level
     */
    public Level(String levelName, int levelIndex, List<String> rawLevel) {

        name = levelName;
        index = levelIndex;
        playerDirection = "DOWN";
        gate = new Point(0, 0);
        moveHistory = new ArrayList<>();
        reverse = false;
        die = false;

        int rows = rawLevel.size();
        int columns = rawLevel.get(0).trim().length();

        // create two GameGrid which are objects and diamonds
        objectsGrid = new GameGrid(rows, columns);
        potentialGrid = new GameGrid(rows, columns);

        if (GameLogger.isDebugActive()) {
            System.out.printf("[ADDING LEVEL] LEVEL [%d]: %s\n", levelIndex, levelName);
        }
        parseLevel(rawLevel);
    }

    /**
     * Converts the row data to two game grids
     *
     * @param rawLevel the raw data of the level
     */
    private void parseLevel(List<String> rawLevel) {
        for (int col = 0; col < rawLevel.size(); col++) {
            for (int row = 0; row < rawLevel.get(col).trim().length(); row++) {
                GameObject curTile = GameObject.fromChar(rawLevel.get(col).charAt(row));
                if (curTile == GameObject.DIAMOND) {
                    numberOfDiamonds++;
                    potentialGrid.putGameObjectAt(curTile, col, row);
                    curTile = GameObject.FLOOR;
                } else if (curTile == GameObject.PAD) {
                    potentialGrid.putGameObjectAt(curTile, col, row);
                    curTile = GameObject.FLOOR;
                } else if (curTile == GameObject.GATE) {
                    gate = new Point(col, row);
                }
                objectsGrid.putGameObjectAt(curTile, col, row);
            }
        }
    }

    /**
     * Gets the objects grid
     *
     * @return the objects grid
     */
    public GameGrid getObjectsGrid() {
        return objectsGrid;
    }

    /**
     * Gets the potential grid
     *
     * @return the potential grid
     */
    public GameGrid getPotentialGrid() {
        return potentialGrid;
    }

    /**
     * Returns the name of this level
     *
     * @return the name of this level
     */
    public String getLevelName() {
        return name;
    }

    /**
     * Returns the level index
     *
     * @return the level index
     */
    public int getLevelIndex() {
        return index;
    }

    /**
     * Gets the position of keeper
     *
     * @return the position of keeper
     */
    public Point getKeeperPosition() {
        return objectsGrid.getKeeper();
    }

    /**
     * Gets the position of crates
     *
     * @return the crates position
     */
    public ArrayList<Point> getCratesPosition() {
        return objectsGrid.getCrates();
    }

    /**
     * Gets the position of walls
     *
     * @return the walls position
     */
    public ArrayList<Point> getWallsPosition() {
        return objectsGrid.getWalls();
    }

    /**
     * Gets the gate.
     *
     * @return the gate position
     */
    public Point getGate() {
        return gate;
    }

    /**
     * Checks if the current level is complete.
     *
     * @return true if the level is complete, otherwise not
     */
    public boolean isLevelComplete() {
        int cratedDiamondsCount = 0;
        for (int row = 0; row < objectsGrid.ROWS; row++) {
            for (int col = 0; col < objectsGrid.COLUMNS; col++) {
                // if crate is on diamond
                if (objectsGrid.getGameObjectAt(col, row) == GameObject.CRATE && potentialGrid.getGameObjectAt(col, row) == GameObject.DIAMOND) {
                    cratedDiamondsCount++;
                }
            }
        }
        return cratedDiamondsCount >= numberOfDiamonds;
    }

    /**
     * Gets the direction of player
     *
     * @return the direction of player
     */
    public String getPlayerDirection() {
        return playerDirection;
    }

    /**
     * Sets the direction of player
     *
     * @param playerDirection the direction of player
     */
    public void setPlayerDirection(String playerDirection) {
        this.playerDirection = playerDirection;
    }


    /**
     * Returns of the player is dead
     *
     * @return a boolean
     */
    public boolean isDie() {
        return die;
    }

    /**
     * Sets the death of player
     *
     * @param die die or not
     */
    public void setDie(boolean die) {
        this.die = die;
    }

    /**
     * Returns if the player is reverse
     *
     * @return if the player is reverse
     */
    public boolean isReverse() {
        return reverse;
    }

    /**
     * Sets the player is reverse or not
     *
     * @param reverse reverse or not
     */
    public void setReverse(boolean reverse) {
        this.reverse = reverse;
    }

    /**
     * Saves the state of current movement
     */
    public void saveMove() {
        moveHistory.add(new MoveHistory(getKeeperPosition(), getCratesPosition(), getWallsPosition()));
    }

    /**
     * Returns to the previous state
     *
     * @return if undo
     */
    public boolean undo() {
        int stateSize = moveHistory.size();
        int stateIndex = stateSize - 1;
        if (stateSize > 0) {
            // Remove keeper and crates in the current state
            if (objectsGrid.removeGameObjectAt(getKeeperPosition())
                    && objectsGrid.removeGameObjectAt(getCratesPosition())
                    && objectsGrid.removeGameObjectAt(getWallsPosition())) {

                // Get keeper and crates in the previous state
                Point keeper = moveHistory.get(stateIndex).getKeeper();
                ArrayList<Point> crates = moveHistory.get(stateIndex).getCrates();
                ArrayList<Point> walls = moveHistory.get(stateIndex).getWalls();
                // Put keeper and crates in the previous position
                if (objectsGrid.putGameObjectAt(GameObject.KEEPER, keeper)
                        && objectsGrid.putGameObjectAt(GameObject.CRATE, crates)
                        && objectsGrid.putGameObjectAt(GameObject.WALL, walls)) {
                    moveHistory.remove(stateIndex);
                }

            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns to the previous initial state
     *
     * @return if reset
     */
    public boolean reset() {
        if (moveHistory.size() > 0) {
            // Remove keeper and crates in the current state
            if (objectsGrid.removeGameObjectAt(getKeeperPosition())
                    && objectsGrid.removeGameObjectAt(getCratesPosition())
                    && objectsGrid.removeGameObjectAt(getWallsPosition())) {
                // Get keeper and crates in the initial state
                Point keeper = moveHistory.get(0).getKeeper();
                ArrayList<Point> crates = moveHistory.get(0).getCrates();
                ArrayList<Point> walls = moveHistory.get(0).getWalls();
                // Clear the all of the game states
                moveHistory.clear();
                // Put keeper and crates in the initial position
                if (objectsGrid.putGameObjectAt(GameObject.KEEPER, keeper)
                        && objectsGrid.putGameObjectAt(GameObject.CRATE, crates)
                        && objectsGrid.putGameObjectAt(GameObject.WALL, walls)) {
                    setPlayerDirection("DOWN");
                }
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return a String which represents the object grid
     */
    @Override
    public String toString() {
        return objectsGrid.toString();
    }


    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<GameObject> iterator() {
        return new LevelIterator();
    }


    /**
     * LevelIterator provides the interface to iterate through the {@link GameGrid}
     * containing the {@link GameObject}s for the current {@link Level}.
     *
     * @see Iterator
     */
    public class LevelIterator implements Iterator<GameObject> {

        int column = 0;
        int row = 0;

        @Override
        public boolean hasNext() {
            return !(row == objectsGrid.ROWS - 1 && column == objectsGrid.COLUMNS);
        }

        @Override
        public GameObject next() {
            if (column >= objectsGrid.COLUMNS) {
                column = 0;
                row++;
            }
            // Get the objects in the same position from objectsGrid and diamondsGrid
            GameObject object = objectsGrid.getGameObjectAt(column, row);
            GameObject diamond = potentialGrid.getGameObjectAt(column, row);
            GameObject retObj = object;
            column++;

            if (diamond == GameObject.DIAMOND) {
                if (object == GameObject.CRATE) {
                    // crate is on diamond
                    retObj = GameObject.CRATE_ON_DIAMOND;
                } else if (object == GameObject.FLOOR) {
                    // diamond is on floor
                    retObj = diamond;
                }
            } else if (diamond == GameObject.PAD) {
                if (object == GameObject.CRATE) {
                    retObj = GameObject.CRATE_ON_PAD;
                } else if (object == GameObject.FLOOR) {
                    retObj = diamond;
                }
            }
            return retObj;
        }
        // Get the current point in the position
        public Point getCurrentPosition() {
            return new Point(column, row);
        }
    }

    /**
     * attack the wall towards the player's direction
     */
    public void attackWall() {
        Point keeper = getKeeperPosition();
        String playerDirection = GameEngine.getCurrentLevel().getPlayerDirection();
        String wallDirection;

        ArrayList<Point> adjacentWalls = new ArrayList<>();
        adjacentWalls.add(new Point((int) keeper.getX() + 1, (int) keeper.getY()));
        adjacentWalls.add(new Point((int) keeper.getX() - 1, (int) keeper.getY()));
        adjacentWalls.add(new Point((int) keeper.getX(), (int) keeper.getY() + 1));
        adjacentWalls.add(new Point((int) keeper.getX(), (int) keeper.getY() - 1));
        adjacentWalls.removeIf(obj -> objectsGrid.getGameObjectAt(obj) != GameObject.WALL);

        for (Point obj : adjacentWalls) {
            int colDelta = (int) obj.getX() - (int) keeper.getX();
            int rowDelta = (int) obj.getY() - (int) keeper.getY();
            Point delta = new Point(colDelta, rowDelta);

            wallDirection = directionFactory.getDirection(playerDirection).getObject(delta);

            if (wallDirection.equals(playerDirection)) {
                objectsGrid.removeGameObjectAt(obj);
            }
        }
    }

}