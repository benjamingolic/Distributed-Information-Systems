package at.fhooe.sail.vis.json;

import jakarta.xml.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

/**
 * Represents a pet with properties such as name, nickname, birthday, type, vaccinations, and ID.
 * This class is designed to be used with JAXB for XML and JSON serialization and deserialization.
 *
 * @Authors: Benjamin Golić (MC) & Amel Šahbaz (AC)
 */
@XmlRootElement(name = "Pet")
@XmlAccessorType(XmlAccessType.FIELD)
public class Pet {
    @XmlElement(name = "Name")
    private String mName;

    @XmlAttribute(name = "nickname")
    private String mNickname;

    @XmlElement(name = "Birthday")
    @XmlSchemaType(name = "dateTime")
    private Date mBirthday;

    /**
     * Enumeration for the type of pets.
     */
    public enum Type {
        CAT, DOG, MOUSE, BIRD
    }

    @XmlElement(name = "Type")
    private Type mType;

    @XmlElementWrapper(name = "Vaccinations")
    @XmlElement(name = "Vaccination")
    private String[] mVaccinations;

    @XmlElement(name = "ID")
    private String mID;


    /**
     * Default constructor for creating a new Pet instance.
     */
    public Pet() {
        super();
    }

    // Setter and getter methods below
    public void setmName(String mName) {
        this.mName = mName;
    }

    public void setmNickname(String mNickname) {
        this.mNickname = mNickname;
    }

    public void setmBirthday(Date mBirthday) {
        this.mBirthday = mBirthday;
    }

    public void setmType(Type mType) {
        this.mType = mType;
    }

    public void setmVaccinations(String[] mVaccinations) {
        this.mVaccinations = mVaccinations;
    }

    public void setmID(String mID) {
        this.mID = mID;
    }

    /**
     * Overrides the equals method to compare pet objects based on their properties.
     *
     * @param o The object to compare this instance with.
     * @return boolean True if the specified object is equal to this object.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pet pet = (Pet) o;
        return Objects.equals(mName, pet.mName) &&
                Objects.equals(mNickname, pet.mNickname) &&
                Objects.equals(mBirthday, pet.mBirthday) &&
                mType == pet.mType &&
                Arrays.equals(mVaccinations, pet.mVaccinations) &&
                Objects.equals(mID, pet.mID);
    }

    /**
     * Generates a hash code for a pet.
     *
     * @return int A hash code value for this object.
     */
    @Override
    public int hashCode() {
        int result = Objects.hash(mName, mNickname, mBirthday, mType, mID);
        result = 31 * result + Arrays.hashCode(mVaccinations);
        return result;
    }
}
