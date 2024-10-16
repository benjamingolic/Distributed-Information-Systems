package at.fhooe.sail.vis.xml;

import javax.xml.bind.annotation.*;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

/**
 * Represents a Pet entity with various attributes including name, nickname,
 * birthday, type, vaccinations, and an ID. This class is annotated for XML
 * serialization and deserialization using JAXB.
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
    @XmlSchemaType(name = "date")
    private Date mBirthday;

    /**
     * Enum for the type of pets supported.
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
     * Default constructor for JAXB serialization/deserialization.
     */
    public Pet() {
        super();
    }

    // Setter methods for the class fields
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
     * Compares this pet to the specified object. The result is true if and only if
     * the argument is not null and is a Pet object that has the same values for
     * all fields as this object.
     *
     * @param o the object to compare this {@code Pet} against.
     * @return true if the given object represents a {@code Pet} equivalent to this pet, false otherwise.
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
     * Returns a hash code for this pet. The hash code is computed by considering
     * all relevant fields, ensuring consistency with the {@link #equals(Object)} method.
     *
     * @return a hash code value for this pet.
     */
    @Override
    public int hashCode() {
        int result = Objects.hash(mName, mNickname, mBirthday, mType, mID);
        result = 31 * result + Arrays.hashCode(mVaccinations);
        return result;
    }
}
