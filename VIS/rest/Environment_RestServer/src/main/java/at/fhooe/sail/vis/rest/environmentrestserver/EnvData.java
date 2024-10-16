package at.fhooe.sail.vis.rest.environmentrestserver;
import jakarta.xml.bind.annotation.*;

import java.io.Serializable;

/**
 * Represents environmental data from a sensor, including the sensor name, timestamp, and measured values.
 * This class is designed to be serialized into and deserialized from XML/JSON formats.
 *
 * @Authors: Benjamin Golić (MC) & Amel Šahbaz (AC)
 */
@XmlRootElement(name = "EnvData")
@XmlAccessorType(XmlAccessType.FIELD)
public class EnvData implements Serializable {
    @XmlElement(name = "SensorName")
    private String mSensorName; // The name of the sensor

    @XmlElement(name = "Timestamp")
    private long mTimestamp; // The timestamp of the data collection

    @XmlElementWrapper(name = "Values")
    @XmlElement(name = "Value")
    private int[] mValues; // The sensor values

    /**
     * Constructs a new instance of EnvData with specified sensor name, timestamp, and values.
     *
     * @param _sensorName The name of the sensor.
     * @param _timestamp The timestamp of the data collection.
     * @param _values The sensor values.
     */
    public EnvData(String _sensorName, long _timestamp, int[] _values) {
        mSensorName = _sensorName;
        mTimestamp = _timestamp;
        mValues = _values;
    }

    /**
     * Default constructor for JAXB serialization/deserialization.
     */
    public EnvData() {}

    /**
     * Returns a string representation of the EnvData instance, including sensor name, timestamp, and values.
     *
     * @return A string representation of the EnvData object.
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Sensor: ");
        sb.append(mSensorName);
        sb.append(", Timestamp: ");
        sb.append(mTimestamp);
        sb.append(", Values: ");
        for (int i = 0; i < mValues.length; i++) {
            sb.append(mValues[i]);
            if (i < mValues.length - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    // Getters for all fields
    public String getSensorName() {
        return mSensorName;
    }

    public long getTimestamp() {
        return mTimestamp;
    }

    public int[] getValues() {
        return mValues;
    }
}
