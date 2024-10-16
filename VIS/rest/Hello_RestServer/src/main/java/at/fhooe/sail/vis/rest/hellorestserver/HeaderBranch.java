package at.fhooe.sail.vis.rest.hellorestserver;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

/**
 * A resource class that demonstrates returning different types of responses
 * for a RESTful service using JAX-RS annotations.
 *
 * @Authors: Benjamin Golić (MC) & Amel Šahbaz (AC)
 */
@Path("/header")
public class HeaderBranch {

    /**
     * Handles HTTP GET requests and produces a plain text response.
     * This method demonstrates responding with an HTML page as plain text.
     *
     * @return A {@link Response} object containing HTML content with MediaType.TEXT_HTML.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getDataPlain() {
        Response.ResponseBuilder bob = Response.ok();
        bob.type(MediaType.TEXT_HTML);
        bob.entity("<html><head><title>HelloRestServer</title></head><body>"
                + "<h1>Plain page</h1><br/>"
                + "</body></html>");
        return bob.build();
    }

    /**
     * Handles HTTP GET requests and produces a JSON response.
     * This method demonstrates responding with a JSON formatted message.
     *
     * @return A {@link Response} object containing a JSON string with MediaType.APPLICATION_JSON.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDataJson() {
        Response.ResponseBuilder bob = Response.ok();
        bob.type(MediaType.APPLICATION_JSON);
        bob.entity("{\"message\" : \"JSON Message\"}");
        return bob.build();
    }

    /**
     * Handles HTTP GET requests and produces an XML response.
     * This method demonstrates responding with an XML formatted message.
     *
     * @return A {@link Response} object containing an XML string with MediaType.APPLICATION_XML.
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Response getDataXml() {
        Response.ResponseBuilder bob = Response.ok();
        bob.type(MediaType.APPLICATION_XML);
        bob.entity("<message>XML Message</message>");
        return bob.build();
    }
}
