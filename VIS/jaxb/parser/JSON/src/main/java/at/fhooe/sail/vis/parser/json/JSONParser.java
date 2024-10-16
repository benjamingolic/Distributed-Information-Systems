package at.fhooe.sail.vis.parser.json;

import org.json.JSONArray;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.List;

/**
 * Provides functionality to parse high score data from a JSON string into a list of {@link HighScore} objects.
 *
 * @Authors: Benjamin Golić (MC) & Amel Šahbaz (AC)
 */
public class JSONParser {

    /**
     * Parses a JSON string representing high scores into a list of {@link HighScore} objects.
     * The JSON string must contain an array named "Highscore" with objects that have "Name", "Score", and "Time" fields.
     *
     * @param jsonStr A JSON string containing high score information.
     * @return A list of {@link HighScore} objects parsed from the JSON string.
     */
    public static List<HighScore> parseHighScores(String jsonStr) {
        List<HighScore> highScores = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(jsonStr);
        JSONArray highScoresArray = jsonObject.getJSONArray("Highscore");

        for (int i = 0; i < highScoresArray.length(); i++) {
            JSONObject highScoreObject = highScoresArray.getJSONObject(i);
            String name = highScoreObject.getString("Name");
            int score = highScoreObject.getInt("Score");
            int time = highScoreObject.getInt("Time");
            highScores.add(new HighScore(name, score, time));
        }

        return highScores;
    }

    /**
     * Main method to demonstrate the parsing of high scores from a JSON string.
     * It creates a JSON string, calls {@link #parseHighScores(String)} to parse it,
     * and prints the resulting list of {@link HighScore} objects.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        String jsonStr = "{ \"Highscore\" : [" +
                "{ \"Name\": \"TBD Name 01\", \"Score\":10, \"Time\":10 }," +
                "{ \"Name\": \"TBD Name 02\", \"Score\":10, \"Time\":11 }," +
                "{ \"Name\": \"TBD Name 03\", \"Score\":9, \"Time\":9 }" +
                "]}";

        List<HighScore> highScores = parseHighScores(jsonStr);

        System.out.println("Highscores:");
        for (HighScore record : highScores) {
            System.out.println(record);
        }
    }
}
