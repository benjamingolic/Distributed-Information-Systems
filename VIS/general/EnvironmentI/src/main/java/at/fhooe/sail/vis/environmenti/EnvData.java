/**
 * @Authors: Benjamin Golić (MC) & Amel Šahbaz (AC)
 */

package at.fhooe.sail.vis.environmenti;

import java.io.Serializable;

public class EnvData implements Serializable {
    private String mSensorName;
    private long mTimestamp;
    private int[] mValues;

    /**
     * Default constructor for creating an empty {@code EnvData} object.
     */

    public EnvData() {}

    /**
     * Constructs a new {@code EnvData} object with specified sensor name, timestamp, and values.
     *
     * @param mSensorN The name of the sensor.
     * @param mTime    The timestamp of the data collection.
     * @param mValue   The values recorded by the sensor.
     */
    public EnvData(String mSensorN, long mTime, int[] mValue) {
        mSensorName = mSensorN;
        mTimestamp = mTime;
        mValues = mValue;
    }

    /**
     * Provides a string representation of the environmental data, including sensor name,
     * timestamp, and values.
     *
     * @return A string representation of the {@code EnvData} object.
     */
    @Override
    public String toString() {
        String rs = "Sensor: " + mSensorName + " Timestamp: " + mTimestamp + " Values: ";
        for (int mValue : mValues) {
            rs += mValue + " ";
        }
        return rs;
    }

    /**
     * Gets the name of the sensor.
     *
     * @return The sensor name.
     */
    public String getmSensorName() {
        return mSensorName;
    }

    /**
     * Gets the timestamp of the data.
     *
     * @return The timestamp.
     */
    public long getmTimestamp() {
        return mTimestamp;
    }

    /**
     * Gets the values recorded by the sensor.
     *
     * @return An array of sensor values.
     */
    public int[] getmValues() {
        return mValues;
    }
}