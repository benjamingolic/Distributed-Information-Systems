package at.fhooe.sail.vis.rest.environmentrestserver;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import org.eclipse.persistence.jaxb.MarshallerProperties;

import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.text.ParseException;

/**
 * A RESTful web service endpoint class that provides access to environmental data.
 *
 * @Authors: Benjamin Golić (MC) & Amel Šahbaz (AC)
 */
@Path("/data")
public class DataBranch {

    /**
     * Provides a landing page with links to various environmental data endpoints.
     *
     * @return A {@link Response} object containing an HTML page with links.
     */
    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response getLandingPage() {
        Response.ResponseBuilder bob = Response.ok();
        StringBuilder wendy = new StringBuilder();
        wendy.append("<html><head><title>EnvironmentRestServer</title></head>" +
                "<body>" +
                "<h1>Environment RestServer - DataBranch</h1>" +
                "<br/>" +
                "<a href=\"data/humidity?output=XML\">Humidity Data</a>" +
                "<br/>" +
                "<a href=\"data/ALL?output=JSON\">ALL Data</a>" +
                "<br/>" +
                "</body></html>");
        bob.type(MediaType.TEXT_HTML);
        bob.entity(wendy.toString());
        return bob.build();
    }

    /**
     * Returns environmental humidity data in either XML or JSON format based on the query parameter.
     *
     * @param _output The desired output format, either "XML" or "JSON".
     * @return A {@link Response} object containing humidity data in the requested format.
     * @throws JAXBException If there is an error during XML/JSON marshalling.
     * @throws ParseException If there is an error parsing the data.
     */
    @GET
    @Path("/humidity")
    @Produces(MediaType.TEXT_XML)
    public Response getHumidtyOverview(@QueryParam("output") @DefaultValue("XML") String _output) throws JAXBException, ParseException {
        EnvData envData = new EnvData("humidity", System.currentTimeMillis(), new int[]{(int) (Math.random() * 10)});

        Response.ResponseBuilder bob = Response.ok();
        String output = _output.toUpperCase();
        StringBuilder wendy = new StringBuilder();
        JAXBContext context = JAXBContext.newInstance(EnvData.class);
        Marshaller marshaller = context.createMarshaller();
        switch (output) {
            case "XML":
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);

                StringWriter stringWriter = new StringWriter();
                StreamResult streamResult = new StreamResult(stringWriter);
                marshaller.marshal(envData, streamResult);
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
                marshaller.marshal(envData, jsonResult);
                wendy.append(jsonWriter);

                bob.type(MediaType.APPLICATION_JSON);
                bob.entity(wendy.toString());
                break;
        }
        return bob.build();
    }

    /**
     * Returns all available environmental data in either XML or JSON format based on the query parameter.
     *
     * @param _output The desired output format, either "XML" or "JSON".
     * @return A {@link Response} object containing all environmental data in the requested format.
     * @throws JAXBException If there is an error during XML/JSON marshalling.
     * @throws ParseException If there is an error parsing the data.
     */
    @GET
    @Path("/ALL")
    @Produces(MediaType.TEXT_XML)
    public Response getAllOverview(@QueryParam("output") @DefaultValue("XML") String _output) throws JAXBException, ParseException {
        EnvDataList envDataList = new EnvDataList(3);

        for (int i = 0; i < 3; i++) {
            envDataList.add(new EnvData("humidity", System.currentTimeMillis(), new int[]{(int) (Math.random() * 10)}));
        }
        Response.ResponseBuilder bob = Response.ok();
        String output = _output.toUpperCase();
        StringBuilder wendy = new StringBuilder();
        JAXBContext context = JAXBContext.newInstance(EnvDataList.class);
        Marshaller marshaller = context.createMarshaller();
        switch (output) {
            case "XML":
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);

                StringWriter stringWriter = new StringWriter();
                StreamResult streamResult = new StreamResult(stringWriter);
                marshaller.marshal(envDataList, streamResult);
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
                marshaller.marshal(envDataList, jsonResult);
                wendy.append(jsonWriter);

                bob.type(MediaType.APPLICATION_JSON);
                bob.entity(wendy.toString());
                break;
        }
        return bob.build();
    }
}