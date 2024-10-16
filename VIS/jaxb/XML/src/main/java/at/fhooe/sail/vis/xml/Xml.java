package at.fhooe.sail.vis.xml;

import javax.xml.bind.*;
import java.io.StringWriter;
import java.text.SimpleDateFormat;

/**
 * Demonstrates the serialization and deserialization of a Pet object to and from XML using JAXB.
 *
 * @Authors: Benjamin Golić (MC) & Amel Šahbaz (AC)
 */
public class Xml {

    /**
     * The main method demonstrates the marshalling and unmarshalling process.
     * It first creates a Pet object, sets its properties, marshals it to XML,
     * and then unmarshals it back to a new Pet object to compare the original and the new object.
     *
     * @param args The command line arguments (not used).
     */
    public static void main(String[] args) {
        try {
            // Initialize and set up a Pet object
            Pet pet = new Pet();
            pet.setmName("Thomas");
            pet.setmNickname("Tom");
            pet.setmBirthday(new SimpleDateFormat("dd.MM.yyyy").parse("10.02.1940"));
            pet.setmType(Pet.Type.CAT);
            pet.setmVaccinations(new String[]{"cat flu", "feline distemper", "rabies", "leucosis"});
            pet.setmID("123456789");

            // Create JAXB context and marshaller for Pet class
            JAXBContext context = JAXBContext.newInstance(Pet.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);

            // Marshal the Pet object to XML
            StringWriter writer = new StringWriter();
            marshaller.marshal(pet, writer);
            String xmlContent = writer.toString();
            System.out.println(xmlContent);

            // Unmarshal XML back to a Pet object
            Unmarshaller unmarshaller = context.createUnmarshaller();
            Pet newPet = (Pet) unmarshaller.unmarshal(new java.io.StringReader(xmlContent));

            // Output to verify that the Pet object was correctly marshalled and unmarshalled
            System.out.println("The new Pet object contains the same data as in the source object: " + pet.equals(newPet));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
