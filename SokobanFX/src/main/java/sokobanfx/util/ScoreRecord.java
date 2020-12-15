package sokobanfx.util;

import sokobanfx.business.GameLogger;
import sokobanfx.business.GameEngine;
import sokobanfx.model.ScoreList;
import sokobanfx.model.UserScore;

import java.io.*;
import java.util.ArrayList;

/**
 * ScoreRecord class is used for handling
 * the IO events of game score files which
 * includes saving score, sorting score
 * and reading score.
 */
public class ScoreRecord {

    /**
     * Saves the the user name and score
     * to the selected level score file.
     *
     * @param levelName the level name
     * @param userName the user name
     * @param movesCount the moves count
     */
    public void saveScore(String levelName, String userName, int movesCount) {
        try {
            File directory = new File(System.getProperty("user.dir") + "/" + "scores");
            if (!directory.exists()) {
                if (!directory.mkdirs()) {
                    return;
                }
            }
            File scoreFile = new File(directory + "/" + levelName + ".txt");
            // Write username and movesCount into score file
            FileWriter writer = new FileWriter(scoreFile, true);
            writer.write(userName);
            writer.write("\t");
            writer.write(String.valueOf(movesCount));
            writer.write("\r\n");   // write new line
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sorts the the score file in order
     * to show the high score list in
     * ascending order.
     *
     */
    public void sortScore() {

        BufferedReader reader;
        try {
            String levelName = String.valueOf(GameEngine.getCurrentLevel().getLevelName());
            String levelFile = "scores/" + levelName + ".txt";
            reader = new BufferedReader(new FileReader(levelFile));

            ArrayList<UserScore> userScores = new ArrayList<>();

            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                String[] strings = line.split("\\s+");
                String name = strings[0];
                int moves = Integer.parseInt(strings[1]);
                userScores.add(new UserScore(name, moves));
            }
            userScores.sort(new ScoreCompare());

            BufferedWriter writer = new BufferedWriter(new FileWriter(levelFile));

            for (UserScore user : userScores) {
                writer.write(user.getUserName());
                writer.write("\t"+user.getScore());
                writer.newLine();
            }
            //Closing the resources
            reader.close();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads the first eight lines of the score file
     * and parses each line to score list.
     *
     * @param scoreList the score list
     */
    public void readScoreRecord(ScoreList scoreList) {
        BufferedReader reader;
        try {
            String levelName = String.valueOf(GameEngine.getCurrentLevel().getLevelName());
            String levelFile = "scores/" + levelName + ".txt";
            reader = new BufferedReader(new FileReader(levelFile));
            for (int i = 0; i < 8; i++) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                if (line.equals("")) {
                    continue;
                }
                String[] str = line.split("\\s+");
                scoreList.addNewScore(str[0], Integer.parseInt(str[1]));
            }
            reader.close();
        } catch (IOException e) {
            GameLogger.showSevere("Error trying to load the score file: " + e);
        } catch (NullPointerException e) {
            GameLogger.showSevere("Cannot open the requested file: " + e);
        }
    }

}
