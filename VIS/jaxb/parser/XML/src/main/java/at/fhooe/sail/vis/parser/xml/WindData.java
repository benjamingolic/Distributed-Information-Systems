package at.fhooe.sail.vis.parser.xml;

/**
 * Represents wind data, including speed and direction (degree).
 *
 * @Authors: Benjamin Golić (MC) & Amel Šahbaz (AC)
 */
public class WindData {
    private double speed; // Wind speed
    private int degree; // Wind direction in degrees

    /**
     * Retrieves the wind speed.
     *
     * @return The wind speed.
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Sets the wind speed.
     *
     * @param speed The wind speed to set.
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    /**
     * Retrieves the wind direction in degrees.
     *
     * @return The wind direction in degrees.
     */
    public int getDegree() {
        return degree;
    }

    /**
     * Sets the wind direction in degrees.
     *
     * @param degree The wind direction to set.
     */
    public void setDegree(int degree) {
        this.degree = degree;
    }
}

