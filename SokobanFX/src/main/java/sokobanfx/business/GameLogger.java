package sokobanfx.business;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * GameLogger class is used for logging the game running information,
 * which is also a singleton class.
 */
public final class GameLogger extends Logger {

    /**
     * The single objects of GameLogger
     */
    private static GameLogger gameLogger;
    /**
     * The logger
     */
    private static final Logger logger = Logger.getLogger("GameLogger");
    /**
     * The date format
     */
    private static final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    /**
     * The calender
     */
    private static final Calendar calendar = Calendar.getInstance();
    /**
     * if the debug mode is active
     */
    private static boolean debug = false;

    /**
     * Instantiates a new Game logger.
     *
     * @throws IOException if the file is not found or can not be read
     */
    private GameLogger() throws IOException {
        super("com.aes2dms.sokobanfx", null);

        File directory = new File(System.getProperty("user.dir") + "/" + "logs");
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                return;
            }
        }
        FileHandler fh = new FileHandler(directory + "/" + GameEngine.GAME_NAME + ".log");
        logger.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();
        fh.setFormatter(formatter);
    }

    /**
     * Create gameLogger.
     *
     * @throws IOException if the file is not found or can not be read
     */
    public static void getInstance() throws IOException {
        if (gameLogger == null) {
            gameLogger = new GameLogger();
        }
    }

    /**
     * Check is debug activated
     *
     * @return the boolean
     */
    public static boolean isDebugActive() {
        return debug;
    }

    /**
     *  Toggle debug mode
     */
    public static void toggleDebug() {
        debug = !debug;
    }

    /**
     * Create formatted message string.
     *
     * @param message the message
     * @return the format message string
     */
    private static String createFormattedMessage(String message) {
        return dateFormat.format(calendar.getTime()) + " -- " + message;
    }

    /**
     * Show info message
     *
     * @param message the message
     */
    public static void showInfo(String message) {
        logger.info(createFormattedMessage(message));
    }

    /**
     * Show warning message
     *
     * @param message the message
     */
    public static void showWarning(String message) {
        logger.warning(createFormattedMessage(message));
    }

    /**
     * Show severe message
     *
     * @param message the message
     */
    public static void showSevere(String message) {
        logger.severe(createFormattedMessage(message));
    }
}