package at.fhooe.sail.vis.rest.hellorestserver;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;


/**
 * Represents a vaping device with brand and flavour attributes.
 * This class is annotated for JAXB to enable easy XML and JSON marshalling.
 *
 * @Authors: Benjamin Golić (MC) & Amel Šahbaz (AC)
 */
@XmlRootElement(name = "vape")
public class Vape {

    @XmlElement(name = "brand")
    private String mBrand; // The brand of the vape

    @XmlElement(name = "flavour")
    private String mFlavour; // The flavour of the vape


    /**
     * Default constructor for JAXB serialization.
     */
    public Vape() {
    }


    /**
     * Constructs a new Vape instance with specified brand and flavour.
     *
     * @param mBrand The brand of the vape.
     * @param mFlavour The flavour of the vape.
     */
    public Vape(String mBrand, String mFlavour) {
        this.mBrand = mBrand;
        this.mFlavour = mFlavour;
    }

    /**
     * Gets the brand of the vape.
     *
     * @return The brand of the vape.
     */
    public String getBrand() {
        return mBrand;
    }

    /**
     * Sets the brand of the vape.
     *
     * @param mBrand The brand to set for the vape.
     */
    public void setBrand(String mBrand) {
        this.mBrand = mBrand;
    }

    /**
     * Gets the flavour of the vape.
     *
     * @return The flavour of the vape.
     */
    public String getFlavour() {
        return mFlavour;
    }

    /**
     * Sets the flavour of the vape.
     *
     * @param mFlavour The flavour to set for the vape.
     */
    public void setFlavour(String mFlavour) {
        this.mFlavour = mFlavour;
    }
}
