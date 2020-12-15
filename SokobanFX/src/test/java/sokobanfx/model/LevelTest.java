//package sokobanfx.model;
//
//import java.io.InputStream;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.awt.*;
//import java.util.ArrayList;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//
//public class LevelTest {
//
//    private Level level;
//    ArrayList<MoveHistory> sampleState;
//
//    @BeforeEach
//    public void setUp() throws Exception {
//        InputStream input = getClass().getClassLoader().getResourceAsStream("debugLevel.skb");
//        GameEngine engine = new GameEngine(input, false);
//        sampleState = new ArrayList<>();
//        level = GameEngine.getCurrentLevel();
//    }
//
//    @Test
//    public void testConstructor() {
//        assertNotNull(level);
//    }
//
//    @Test
//    public void testGetKeeperPosition() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
//        Method getKeeperPosition = Level.class.getDeclaredMethod("getKeeperPosition");
//        getKeeperPosition.setAccessible(true);
//        Point output = (Point) getKeeperPosition.invoke(level);
//        assertEquals(output, new Point(1, 1));
//    }
//
//    @Test
//    void testSaveMove() {
//        level.setKeeperPosition(new Point(1, 1));
//        level.setCratesPosition(new ArrayList<>());
//        level.saveMove();
//        sampleState.add(new MoveHistory(new Point(1, 1), new ArrayList<>()));
//        assertEquals(sampleState.get(0).getKeeper(), level.moveHistory.get(0).getKeeper());
//    }
//
//    @Test
//    void testUndo() {
//        level.setKeeperPosition(new Point(1, 1));
//        level.setCratesPosition(new ArrayList<>());
//        level.saveMove();
//
//        level.setKeeperPosition(new Point(2, 2));
//        level.setCratesPosition(new ArrayList<>());
//        level.saveMove();
//        sampleState.add(new MoveHistory(new Point(1, 1), new ArrayList<>()));
//        level.undo();
//
//        assertEquals(sampleState.get(0).getKeeper(), level.moveHistory.get(level.moveHistory.size() - 1).getKeeper());
//    }
//
//    @Test
//    void testReset() {
//        level.setKeeperPosition(new Point(1, 1));
//        level.setCratesPosition(new ArrayList<>());
//        level.saveMove();
//
//        level.setKeeperPosition(new Point(2, 2));
//        level.setCratesPosition(new ArrayList<>());
//        level.saveMove();
//
//        level.reset();
//
//        assertEquals(0, level.moveHistory.size());
//    }
//}