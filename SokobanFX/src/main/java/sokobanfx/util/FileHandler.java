package sokobanfx.util;

import sokobanfx.business.GameLogger;
import sokobanfx.business.GameEngine;
import sokobanfx.model.Level;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 *  FileHandler is a class to handle the load and save game file events.
 */
public class FileHandler {

    /**
     * Loads a game from the input stream.
     *
     * @param input the input file stream
     * @param gameEngine the game engine
     * @return a level list which is parsed from the input stream
     */
    public List<Level> loadGameFile(InputStream input, GameEngine gameEngine) {
        List<Level> levels = new ArrayList<>(5);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
            boolean parsedFirstLevel = false;
            List<String> rawLevel = new ArrayList<>();
            gameEngine.levelName = "";
            int levelIndex = 0;

            while (true) {
                String line = reader.readLine();

                if (line == null) {
                    if (rawLevel.size() != 0) {
                        Level parsedLevel = new Level(gameEngine.levelName, levelIndex, rawLevel);
                        levels.add(parsedLevel);
                    }
                    break;
                }

                if (line.contains("LevelName")) {
                    if (parsedFirstLevel) {
                        Level parsedLevel = new Level(gameEngine.levelName, levelIndex++, rawLevel);
                        levels.add(parsedLevel);
                        rawLevel.clear();
                    } else {
                        parsedFirstLevel = true;
                    }

                    gameEngine.levelName = line.replace("LevelName: ", "");
                    continue;
                }
                // Delete the white space of the beginning and end of the line
                line = line.trim();
                line = line.toUpperCase();
                if (line.matches(".*P.*P.*")) {
                    rawLevel.add(line);
                }
            }

        } catch (IOException e) {
            GameLogger.showSevere("Error trying to load the game file: " + e);
        } catch (NullPointerException e) {
            GameLogger.showSevere("Cannot open the requested file: " + e);
        }

        return levels;
    }
}
