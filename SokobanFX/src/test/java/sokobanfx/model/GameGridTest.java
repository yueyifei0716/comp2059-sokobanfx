package sokobanfx.model;

import java.awt.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameGridTest {

    private GameGrid sampleGrid;
    private final int COLUMNS = 10;
    private final int ROWS = 10;


    @BeforeEach
    public void setUp() {
        sampleGrid = new GameGrid(COLUMNS, ROWS);
    }

    @Test
    public void testConstructor() {
        assertNotNull(sampleGrid);
    }

    @Test
    public void testTranslatePoint() {
        Point sourcePoint = new Point(2, 1);
        Point actualPoint = GameGrid.translatePoint(sourcePoint, new Point(0, 1));
        assertEquals(new Point(2, 2), actualPoint);
    }

    @Test
    public void testGetDimension() {
        assertEquals(sampleGrid.getDimension(), new Dimension(COLUMNS, ROWS));
    }

    @Test
    public void testPutGameObjectAt() {
        // Test if an object can be put on the grid by putGameObjectAt and getGameObjectAt method
        sampleGrid.putGameObjectAt(GameObject.FLOOR, 1, 1);
        assertEquals(GameObject.FLOOR, sampleGrid.getGameObjectAt(1, 1));

        // Put an object within bounds
        int col = COLUMNS - 1;
        int row = ROWS - 1;
        assertTrue(sampleGrid.putGameObjectAt((GameObject.FLOOR), col, row));

        // Put an object out of bounds
        col = COLUMNS + 1;
        row = ROWS + 1;
        assertFalse(sampleGrid.putGameObjectAt((GameObject.FLOOR), col, row));

        // Put an object on the bounds
        col = COLUMNS;
        row = ROWS;
        assertFalse(sampleGrid.putGameObjectAt((GameObject.FLOOR), col, row));
    }

    @Test
    public void testRemoveGameObjectAt() {
        sampleGrid.putGameObjectAt(GameObject.CRATE, 2, 2);
        assertTrue(sampleGrid.removeGameObjectAt(new Point(2, 2)));
    }
}