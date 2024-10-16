package at.fhooe.sail.vis.rest.environmentrestserver;

import jakarta.xml.bind.annotation.*;

/**
 * Represents a collection of sensor types available in the system.
 * This class is designed for JAXB to facilitate easy serialization and deserialization
 * of sensor type information to and from XML/JSON formats.
 *
 * @Authors: Benjamin Golić (MC) & Amel Šahbaz (AC)
 */
@XmlRootElement(name = "sensors")
@XmlAccessorType(XmlAccessType.FIELD)

public class Sensors {

    /**
     * An array of strings representing the types of sensors available.
     */
    @XmlElement(name = "sensorTypes")
    private String[] mSensorTypes;

    /**
     * Default constructor required for JAXB serialization and deserialization.
     */
    public Sensors() {
    }

    /**
     * Constructs a {@code Sensors} instance with specified sensor types.
     *
     * @param _sensorTypes An array of strings representing the sensor types.
     */
    public Sensors(String[] _sensorTypes) {
        mSensorTypes = _sensorTypes;
    }

    /**
     * Gets the array of sensor types.
     *
     * @return An array of strings representing the sensor types.
     */
    @XmlElement
    public String[] getSensorTypes() {
        return mSensorTypes;
    }
}
