package at.fhooe.sail.vis.rest.environmentrestserver;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import org.eclipse.persistence.jaxb.MarshallerProperties;

import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.text.ParseException;

/**
 * Provides information about the sensor types available in the EnvironmentRestServer.
 * This branch of the RESTful service offers an endpoint to retrieve sensor types
 * in both XML and JSON formats based on the client's request.
 *
 * @Authors: Benjamin Golić (MC) & Amel Šahbaz (AC)
 */
@Path("/info")
public class InfoBranch {

    public static Sensors sensors = new Sensors(new String[]{"humidity"});

    /**
     * Provides a landing page for the Info Branch with links to the sensor types endpoint.
     *
     * @return A {@link Response} object containing an HTML page with navigation links.
     */
    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response getLandingPage() {
        Response.ResponseBuilder bob = Response.ok();
        StringBuilder wendy = new StringBuilder();
        wendy.append("<html><head><title>EnvironmentRestServer</title></head>" +
                "<body>" +
                "<h1>Environment RestServer - Info Branch</h1>" +
                "<br/>" +
                "<a href=\"info/sensortypes?output=XML\">XML Data</a>" +
                "<br/>" +
                "<a href=\"info/sensortypes?output=JSON\">JSON Data</a>" +
                "<br/>" +
                "</body></html>");
        bob.type(MediaType.TEXT_HTML);
        bob.entity(wendy.toString());
        return bob.build();
    }

    /**
     * Retrieves the sensor types available in the EnvironmentRestServer.
     * This endpoint can return data in XML or JSON format, depending on the query parameter "output".
     *
     * @param _output The desired output format, either "XML" or "JSON".
     * @return A {@link Response} object containing sensor types in the requested format.
     * @throws JAXBException If an error occurs during the marshalling process.
     * @throws ParseException If an error occurs parsing the sensor data.
     */
    @GET
    @Path("/sensortypes")
    @Produces(MediaType.TEXT_XML)
    public Response getSensorOverview(@QueryParam("output") @DefaultValue("XML") String _output) throws JAXBException, ParseException {
        Response.ResponseBuilder bob = Response.ok();
        String output = _output.toUpperCase();
        StringBuilder wendy = new StringBuilder();
        JAXBContext context = JAXBContext.newInstance(Sensors.class);
        Marshaller marshaller = context.createMarshaller();
        switch (output) {
            case "XML":
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);

                StringWriter stringWriter = new StringWriter();
                StreamResult streamResult = new StreamResult(stringWriter);
                marshaller.marshal(sensors, streamResult);
                wendy.append(stringWriter);

                bob.type(MediaType.APPLICATION_XML);
                bob.entity(wendy.toString());
                break;
            case "JSON":
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                marshaller.setProperty(MarshallerProperties.MEDIA_TYPE, "application/json");
                marshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, false);

                StringWriter jsonWriter = new StringWriter();
                StreamResult jsonResult = new StreamResult(jsonWriter);
                marshaller.marshal(sensors, jsonResult);
                wendy.append(jsonWriter);

                bob.type(MediaType.APPLICATION_JSON);
                bob.entity(wendy.toString());
                break;
        }
        return bob.build();
    }
}
