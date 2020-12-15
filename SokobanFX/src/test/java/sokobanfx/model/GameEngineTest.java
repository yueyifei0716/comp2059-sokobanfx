//package sokobanfx.model;
//
//import javafx.scene.input.KeyCode;
//import java.awt.*;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import sokobanfx.business.GameEngine;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import java.io.InputStream;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//
//public class GameEngineTest {
//
//    private GameEngine engine;
//    private Level level;
//
//    @BeforeEach
//    public void setUp() throws Exception {
//        InputStream input = getClass().getClassLoader().getResourceAsStream("debugLevel.skb");
//        engine = new GameEngine(input, false);
//        level = GameEngine.getCurrentLevel();
//    }
//
//    @Test
//    public void testConstructor() {
//        assertNotNull(engine);
//    }
//
//    @Test
//    public void testHandleKey() {
//        // The left side is WALL, so the KEEPER can not move
//        engine.keyHandler.handleKey(KeyCode.LEFT, engine);
//        assertEquals(0, GameEngine.getMovesCount());
//        // The right side is FLOOR, so the KEEPER can move
//        engine.keyHandler.handleKey(KeyCode.RIGHT, engine);
//        assertEquals(1, GameEngine.getMovesCount());
//        // The right side of CRATE is DIAMOND, so the KEEPER can move
//        engine.keyHandler.handleKey(KeyCode.RIGHT, engine);
//        assertEquals(2, GameEngine.getMovesCount());
//    }
//
//    @Test
//    public void testMove() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
//        Method move = GameEngine.class.getDeclaredMethod("move", Point.class);
//        move.setAccessible(true);
//        String output = (String) move.invoke(engine, new Point(0, 1));
//        assertTrue(GameEngine.getMovesCount() > 0);
//    }
//
//    @Test
//    public void testIsGameNotComplete() {
//        assertFalse(engine.isGameComplete());
//    }
//
//    @Test
//    public void testIsGameComplete() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
//        // Move to the right for twice to complete the game according to the input file
//        this.testMove();
//        this.testMove();
//        assertTrue(engine.isGameComplete());
//    }
//
//    @Test
//    public void testGetNextLevel() {
//        assertNotEquals(level, engine.getNextLevel());
//    }
//
//    @Test
//    public void testGetCurrentLevel() {
//        assertEquals(level, GameEngine.getCurrentLevel());
//    }
//
//}