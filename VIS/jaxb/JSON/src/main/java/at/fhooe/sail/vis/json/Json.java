package at.fhooe.sail.vis.json;

import org.eclipse.persistence.jaxb.MarshallerProperties;
import jakarta.xml.bind.*;

import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.SimpleDateFormat;

/**
 * Demonstrates the process of marshalling (serializing) a Java object into JSON format
 * and unmarshalling (deserializing) JSON back into a Java object using JAXB with EclipseLink MOXy.
 * This class specifically deals with converting a {@link Pet} object to and from JSON.
 *
 * @Authors: Benjamin Golić (MC) & Amel Šahbaz (AC)
 */
public class Json {

    /**
     * Main method to execute the marshalling and unmarshalling process.
     * It first creates a {@link Pet} object, sets its properties, and then serializes it to JSON.
     * Afterward, it demonstrates deserializing the JSON back to a {@link Pet} object.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        try {
            // Creating and setting properties of the Pet object
            Pet pet = new Pet();
            pet.setmName("Thomas");
            pet.setmNickname("Tom");
            pet.setmBirthday(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").parse("1940-02-10T13:51:30.673+01:00"));
            pet.setmType(Pet.Type.CAT);
            pet.setmVaccinations(new String[]{"cat flu", "feline distemper", "rabies", "leucosis"});
            pet.setmID("123456789");

            // JAXB context initialization for Pet class
            JAXBContext context = JAXBContext.newInstance(Pet.class);

            // Marshaller configuration for JSON output
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(MarshallerProperties.MEDIA_TYPE, "application/json");
            marshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, false);

            // Marshalling Pet object to JSON
            StringWriter jsonWriter = new StringWriter();
            marshaller.marshal(pet, jsonWriter);
            String jsonString = jsonWriter.toString();
            System.out.println(jsonString);

            // Unmarshaller configuration for JSON input
            Unmarshaller unmarshaller = context.createUnmarshaller();
            unmarshaller.setProperty(MarshallerProperties.MEDIA_TYPE, "application/json");
            unmarshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, false);

            // Unmarshalling JSON back to Pet object
            StreamSource jsonSource = new StreamSource(new StringReader(jsonString));
            JAXBElement<Pet> jaxbElement = unmarshaller.unmarshal(jsonSource, Pet.class);
            Pet deserializedPet = jaxbElement.getValue();

            // Verification output
            System.out.println("The new Pet object contains the same data as in the source object: " + pet.equals(deserializedPet));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
