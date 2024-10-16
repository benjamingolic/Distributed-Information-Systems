package at.fhooe.sail.vis.rest.environmentrestserver;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Configures the RESTful web service for the EnvironmentRestServer application.
 * This class extends {@link ResourceConfig} to configure the services and provide
 * a simple HTML landing page that links to the various parts of the RESTful web service.
 *
 * @Authors: Benjamin Golić (MC) & Amel Šahbaz (AC)
 */
@ApplicationPath("/")
@Path("/")
public class EnvironmentRestServer extends ResourceConfig {

    /**
     * Registers resource classes and configures the RESTful service.
     */
    public EnvironmentRestServer() {
        // Registers this class as a resource and also registers other resource branches.
        register(this.getClass());
        register(InfoBranch.class);
        register(DataBranch.class);
    }

    /**
     * Provides a landing page for the RESTful web service.
     * The landing page includes links to the InfoBranch and DataBranch sections of the service.
     *
     * @return A {@link Response} object containing HTML for the landing page.
     */
    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response getLandingPage() {
        // Creates an HTML string that forms the landing page content.
        // Builds and returns the response with the HTML content.
        Response.ResponseBuilder bob = Response.ok();
        StringBuilder wendy = new StringBuilder();
        wendy.append("<html><head><title>EnvironmentRestServer</title></head>" +
                "<body>" +
                "<h1>Environment RestServer</h1>" +
                "<br/>" +
                "<a href=\"info\">Info Branch</a><br>" +
                "<a href=\"data\">Data Branch</a><br>" +
                "</body></html>");
        bob.type(MediaType.TEXT_HTML);
        bob.entity(wendy.toString());
        return bob.build();
    }
}