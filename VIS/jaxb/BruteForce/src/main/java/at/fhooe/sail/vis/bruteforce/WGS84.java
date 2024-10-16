package at.fhooe.sail.vis.bruteforce;

/**
 * Represents a geographic coordinate using the WGS84 system.
 * This class stores the latitude and longitude values.
 *
 * @Authors: Benjamin Golić (MC) & Amel Šahbaz (AC)
 */
public class WGS84 {
    private double latitude;
    private double longitude;

    /**
     * Gets the latitude value of this geographic coordinate.
     *
     * @return The latitude of this WGS84 coordinate.
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Sets the latitude value of this geographic coordinate.
     *
     * @param latitude The latitude to set for this WGS84 coordinate.
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * Gets the longitude value of this geographic coordinate.
     *
     * @return The longitude of this WGS84 coordinate.
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Sets the longitude value of this geographic coordinate.
     *
     * @param longitude The longitude to set for this WGS84 coordinate.
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
