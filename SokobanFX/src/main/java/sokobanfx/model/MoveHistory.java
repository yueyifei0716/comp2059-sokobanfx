package sokobanfx.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * MoveHistory class is used to save the objects
 * position before every keeper movement.
 */
public class MoveHistory {
    /**
     * The position of keeper
     */
    private final Point keeper;
    /**
     * The positions of crates
     */
    private final ArrayList<Point> crates;
    /**
     * The positions of walls
     */
    private final ArrayList<Point> walls;

    /**
     * Saves keeper, crates and walls position.
     *
     * @param keeper the keeper position
     * @param crates the crates position
     * @param walls the walls position
     */
    public MoveHistory(Point keeper, ArrayList<Point> crates, ArrayList<Point> walls) {
        this.keeper = (Point) keeper.clone();
        this.crates = crates.stream()
                .map(p -> ((Point) p.clone()))
                .collect(Collectors.toCollection(ArrayList::new));
        this.walls = walls.stream()
                .map(p -> ((Point) p.clone()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Gets the keeper position
     *
     * @return the keeper position
     */
    public Point getKeeper() {
        return keeper;
    }

    /**
     * Gets the crates position
     *
     * @return the crates position
     */
    public ArrayList<Point> getCrates() {
        return crates;
    }

    /**
     * Gets the walls position
     *
     * @return the walls position
     */
    public ArrayList<Point> getWalls() {
        return walls;
    }

}
