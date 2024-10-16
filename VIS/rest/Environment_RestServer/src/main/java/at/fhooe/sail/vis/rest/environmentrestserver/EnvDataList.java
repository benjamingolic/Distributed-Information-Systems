package at.fhooe.sail.vis.rest.environmentrestserver;

import jakarta.xml.bind.annotation.*;

/**
 * Represents a list of environmental data entries. This class is designed to
 * aggregate multiple {@link EnvData} instances for serialization and
 * deserialization processes, especially when handling collections of data in XML/JSON formats.
 *
 * @Authors: Benjamin Golić (MC) & Amel Šahbaz (AC)
 */
@XmlRootElement(name = "ListEnvData")
@XmlAccessorType(XmlAccessType.FIELD)
public class EnvDataList {

    /**
     * An array of {@link EnvData} objects representing the environmental data entries.
     */
    @XmlElementWrapper(name = "EnvDatas")
    @XmlElement(name = "EnvData")
    private EnvData[] mEnvData;

    /**
     * The size of the {@code mEnvData} array, indicating the number of {@link EnvData} objects it contains.
     */
    @XmlElement(name = "Size")
    private int mSize;

    /**
     * Constructs an {@link EnvDataList} instance with a specified initial capacity for environmental data entries.
     *
     * @param _size The initial capacity of the list.
     */
    public EnvDataList(int _size) {
        mEnvData = new EnvData[_size];
    }

    /**
     * Default constructor that initializes the list with a default capacity.
     */
    public EnvDataList() {
        // Initially creates an array with a single element capacity, which can be resized later.
        mEnvData = new EnvData[1];
    }

    /**
     * Adds an {@link EnvData} object to the list. The method dynamically adjusts the array size to accommodate new entries.
     *
     * @param _envData The {@link EnvData} object to be added to the list.
     */
    public void add(EnvData _envData) {
        mEnvData[mSize] = _envData;
        mSize++;
    }

    /**
     * Retrieves the array of {@link EnvData} objects contained in this list.
     *
     * @return An array of {@link EnvData} objects.
     */
    public EnvData[] getEnvData() {
        return mEnvData;
    }
}