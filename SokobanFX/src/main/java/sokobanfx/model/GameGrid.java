package sokobanfx.model;

import sokobanfx.business.GameLogger;

import java.awt.*;
import java.util.ArrayList;


/**
 * GameGrid class is used to create a 2D grid with gameObjects.
 */
public class GameGrid {
    /**
     * The number of columns
     */
    public final int COLUMNS;
    /**
     * The number of rows
     */
    public final int ROWS;
    /**
     * The grid containing every GameObject
     */
    private final GameObject[][] gameObjects;

    /**
     * Creates a grid using columns and rows to set the maximum size.
     *
     * @param columns the number of columns
     * @param rows    the number of rows
     */
    public GameGrid(int columns, int rows) {
        COLUMNS = columns;
        ROWS = rows;
        // Initialize the array
        gameObjects = new GameObject[COLUMNS][ROWS];
    }

    /**
     * Returns the point whose position is delta away from the source point.
     *
     * @param sourceLocation the source point
     * @param delta          the distance between the Original GameObject point and the target point
     * @return               the target point which is a delta away from the source point
     */
    public static Point translatePoint(Point sourceLocation, Point delta) {
        Point translatedPoint = new Point(sourceLocation);
        translatedPoint.translate((int) delta.getX(), (int) delta.getY());
        return translatedPoint;
    }

    /**
     * Returns the size of the GameGrid as {@link Dimension}.
     * @return a {@link Dimension} the size of the grid
     */
    // Get the dimension of the GameGrid
    public Dimension getDimension() {
        return new Dimension(COLUMNS, ROWS);
    }

    /**
     * Returns the keeper point.
     * @return the keeper point
     */
    // Get the position pf keeper
    public Point getKeeper() {
        Point keeper = null;
        for (int col = 0; col < COLUMNS; col++) {
            for (int row = 0; row < ROWS; row++) {
                if (getGameObjectAt(col, row).getCharSymbol() == 'S') {
                    keeper = new Point(col, row);
                }
            }
        }
        return keeper;
    }

    /**
     * Returns the crate points list
     * @return the crate points list
     */
    // Get a list to save the positions of crates
    public ArrayList<Point> getCrates() {
        ArrayList<Point> crates = new ArrayList<>();

        for (int col = 0; col < COLUMNS; col++) {
            for (int row = 0; row < ROWS; row++) {
                if (getGameObjectAt(col, row).getCharSymbol() == 'C') {
                    crates.add(new Point(col, row));
                }
            }
        }
        return crates;
    }

    /**
     * Returns the wall points list
     * @return the wall points list
     */
    public ArrayList<Point> getWalls() {
        ArrayList<Point> walls = new ArrayList<>();

        for (int col = 0; col < COLUMNS; col++) {
            for (int row = 0; row < ROWS; row++) {
                if (getGameObjectAt(col, row).getCharSymbol() == 'W') {
                    walls.add(new Point(col, row));
                }
            }
        }
        return walls;
    }

    /** Returns the pipes points list
     * @return the pipes points list
     */
    public ArrayList<Point> getPipes() {
        ArrayList<Point> pipes = new ArrayList<>();

        for (int col = 0; col < COLUMNS; col++) {
            for (int row = 0; row < ROWS; row++) {
                if (getGameObjectAt(col, row).getCharSymbol() == 'U'
                        || getGameObjectAt(col, row).getCharSymbol() == 'D'
                        || getGameObjectAt(col, row).getCharSymbol() == 'L'
                        || getGameObjectAt(col, row).getCharSymbol() == 'R') {
                    pipes.add(new Point(col, row));
                }
            }
        }
        return pipes;
    }

    /**
     * Gets the GameObject which is delta away from the origin GameObject.
     *
     * @param source the source GameObject location
     * @param delta the distance from source
     * @return the target GameObject
     */
    public GameObject getTargetFromSource(Point source, Point delta) {
        return getGameObjectAt(translatePoint(source, delta));
    }

    /**
     * Gets the GameObject at (x, y).
     *
     * @param col the col of the GameObject
     * @param row the row of the GameObject
     * @return GameObject
     * @throws ArrayIndexOutOfBoundsException if the location is outside the grid boundary
     */
    public GameObject getGameObjectAt(int col, int row) throws ArrayIndexOutOfBoundsException {
        if (isPointOutOfBounds(col, row)) {
            if (GameLogger.isDebugActive()) {
                System.out.printf("Trying to get null GameObject from COL: %d  ROW: %d", col, row);
            }
            throw new ArrayIndexOutOfBoundsException("The point [" + col + ":" + row + "] is outside the map.");
        }
        return gameObjects[col][row];
    }

    /**
     * Gets the GameObject at a point.
     *
     * @param p the point of the GameObject
     * @return GameObject
     */
    public GameObject getGameObjectAt(Point p) {
        if (p == null) {
            throw new IllegalArgumentException("Point cannot be null.");
        }
        return getGameObjectAt((int) p.getX(), (int) p.getY());
    }

    /**
     * Removes an GameObject at a certain point.
     *
     * @param p the point where the GameObject will be removed
     * @return boolean true if it was possible to remove the GameObject, false otherwise
     */
    public boolean removeGameObjectAt(Point p) {
        return putGameObjectAt(GameObject.FLOOR, p);
    }

    /**
     * Removes an GameObject at a certain point.
     *
     * @param points the points list where the GameObjects will be removed
     * @return boolean true if it was possible to remove the GameObjects, false otherwise
     */
    // Remove a certain type of objects at certain points
    public boolean removeGameObjectAt(ArrayList<Point> points) {
        return putGameObjectAt(GameObject.FLOOR, points);
    }

    /**
     * Puts a GameObject on a GameGrid location (x, y).
     *
     * @param gameObject the GameObject to put
     * @param x  the x coordinate
     * @param y  the y coordinate
     * @return true if the GameObject is put, false otherwise
     */

    public boolean putGameObjectAt(GameObject gameObject, int x, int y) {
        if (isPointOutOfBounds(x, y)) {
            return false;
        }
        gameObjects[x][y] = gameObject;
        return gameObjects[x][y] == gameObject;
    }

    /**
     * Puts an {@link GameObject} on a point.
     *
     * @param gameObject the GameObject to be put
     * @param p the point that the GameObject will be put
     * @return true if the GameObject is put, false otherwise
     */

    public boolean putGameObjectAt(GameObject gameObject, Point p) {
        return p != null && putGameObjectAt(gameObject, (int) p.getX(), (int) p.getY());
    }

    /**
     * Puts a certain type of objects on certain points.
     *
     * @param gameObject the certain type of object to be put
     * @param points the points list where the GameObject will be put into
     * @return true if the GameObjects are put, false otherwise.
     */
    //
    public boolean putGameObjectAt(GameObject gameObject, ArrayList<Point> points) {
        boolean isPut = true;
        for (Point point : points) {
            isPut = putGameObjectAt(gameObject, point);
        }
        return isPut;
    }

    /**
     * Check if a point is out of bounds by its point location
     *
     * @param x the x position of grid
     * @param y the y position of grid
     * @return true if the point is outside the grid, false otherwise.
     */
    //
    private boolean isPointOutOfBounds(int x, int y) {
        return (x < 0 || y < 0 || x >= COLUMNS || y >= ROWS);
    }

    /**
     * Gets a String of the current GameGrid.
     *
     * @return the current GameGrid in a String
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(gameObjects.length);

        for (GameObject[] gameObject : gameObjects) {
            for (GameObject aGameObject : gameObject) {
                if (aGameObject == null) {
                    aGameObject = GameObject.DEBUG_OBJECT;
                }
                sb.append(aGameObject.getCharSymbol());
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}