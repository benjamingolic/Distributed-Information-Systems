package at.fhooe.sail.vis.parser.json;

/**
 * Represents a high score entry with a player's name, score, and time.
 * This class can be used to model high score data for games or applications
 * that track high scores.
 *
 * @Authors: Benjamin Golić (MC) & Amel Šahbaz (AC)
 */
public class HighScore {
    private String name;
    private int score;
    private int time;

    /**
     * Constructs a new HighScore instance with specified name, score, and time.
     *
     * @param name  the name of the player
     * @param score the score achieved by the player
     * @param time  the time taken by the player to achieve the score, in seconds
     */
    public HighScore(String name, int score, int time) {
        this.name = name;
        this.score = score;
        this.time = time;
    }

    // Getter and setter methods

    /**
     * Gets the name of the player.
     *
     * @return the player's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the player.
     *
     * @param name the new name of the player
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the score achieved by the player.
     *
     * @return the player's score
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the score achieved by the player.
     *
     * @param score the new score of the player
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Gets the time taken by the player to achieve the score.
     *
     * @return the time in seconds
     */
    public int getTime() {
        return time;
    }

    /**
     * Sets the time taken by the player to achieve the score.
     *
     * @param time the new time in seconds
     */
    public void setTime(int time) {
        this.time = time;
    }

    /**
     * Returns a string representation of the HighScore instance.
     *
     * @return a string containing the name, score, and time of the high score
     */
    @Override
    public String toString() {
        return  "Name: " + name +
                " | Score: " + score +
                " | Time: " + time;
    }
}

