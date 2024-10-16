package at.fhooe.sail.vis.rest.hellorestserver;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

/**
 * Demonstrates the handling of different types of parameters in JAX-RS.
 * Provides endpoints to test path parameters, query parameters, and JAXB entity responses.
 *
 * @Authors: Benjamin Golić (MC) & Amel Šahbaz (AC)
 */
@Path("/param")
public class ParameterBranch {

    /**
     * Provides a landing page with links to various parameter handling demonstrations.
     *
     * @return A {@link Response} object containing an HTML representation of the landing page.
     */
    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response getLandingPage() {
        StringBuilder wendy = new StringBuilder();
        wendy.append("<html><head><title>HelloRestServer</title></head><body>" +
                "<h1>Parameter REST Branch</h1><br/>" +
                "<a href=\"param/url/content/moin\">Content Type</a><br>" +
                "<a href=\"param/url/parameter?output=moinsen&stuff=moinmoin\">Parameter</a><br>" +
                "<a href=\"param/jaxb\">Jaxb</a><br>" +
                "</body></html>");
        Response.ResponseBuilder bob = Response.ok();
        bob.type(MediaType.TEXT_HTML);
        bob.entity(wendy.toString());
        return bob.build();
    }

    /**
     * Returns XML content based on the provided path parameter.
     *
     * @param _type The content type specified as a path parameter.
     * @return A {@link Response} object containing XML representation of the type.
     */
    @GET
    @Path("/url/content/{type}")
    @Produces(MediaType.TEXT_XML)
    public Response getXMLContent(@PathParam("type") String _type) {
        Response.ResponseBuilder bob = Response.ok();
        bob.type(MediaType.APPLICATION_XML);
        bob.entity("<type>"+_type+"</type>");
        return bob.build();
    }

    /**
     * Returns XML content based on the provided query parameters.
     *
     * @param _output The output content specified as a query parameter.
     * @param _stuff Additional content specified as a query parameter.
     * @return A {@link Response} object containing XML representation of the parameters.
     */
    @GET
    @Path("/url/parameter")
    @Produces(MediaType.TEXT_XML)
    public Response getXMLParameter(@QueryParam("output") String _output, @QueryParam("stuff") String _stuff) {
        Response.ResponseBuilder bob = Response.ok();
        bob.type(MediaType.APPLICATION_XML);
        bob.entity("<parameter><output>"+_output+"</output><stuff>"+_stuff+"</stuff></parameter>");
        return bob.build();
    }

    /**
     * Returns a JAXB entity in the format specified by the query parameter (XML or JSON).
     * Defaults to XML if no output format is specified.
     *
     * @param _output The desired output format specified as a query parameter.
     * @return A {@link Response} object containing the JAXB entity in the specified format.
     */
    @GET
    @Path("/jaxb")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_PLAIN})
    public Response getJAXB(@QueryParam("output") @DefaultValue("XML") String _output) {
        Response.ResponseBuilder bob = null;
        String output = _output.toUpperCase();
        Vape vape = new Vape("Salt", "Red Blossom");
        switch (output){
            case "XML":
                bob = Response.ok();
                bob.type(MediaType.APPLICATION_XML);
                bob.entity(vape);
                break;
            case "JSON":
                bob = Response.ok();
                bob.type(MediaType.APPLICATION_JSON);
                bob.entity(vape);
                break;
            default:
                bob = Response.serverError();
                bob.type(MediaType.TEXT_PLAIN);
                bob.entity("Invalid output format");
        }
        return bob.build();
    }

}
