package at.fhooe.sail.vis.rest.hellorestserver;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Main configuration and entry point for the HelloRestServer application.
 * It extends {@link ResourceConfig} to configure JAX-RS resources and providers.
 * This class also provides several REST endpoints for demonstration purposes.
 *
 * @Authors: Benjamin Golić (MC) & Amel Šahbaz (AC)
 */
@ApplicationPath("/rest")
@Path("/")
public class HelloRestServer extends ResourceConfig {

    /**
     * Configures the REST application and registers resource classes.
     */
    public HelloRestServer() {
        register(this.getClass());
        register(ExceptionBranch.class);
        register(ParameterBranch.class);
        register(HeaderBranch.class);
    }

    /**
     * Provides the landing page for the REST server with links to various test endpoints.
     *
     * @return A {@link Response} containing HTML content for the landing page.
     */
    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response getLandingPage() {
        StringBuilder wendy = new StringBuilder();
        wendy.append("<html><head><title>HelloRestServer</title></head>" +
                "<body>" +
                "<h1>Hello RestServer</h1>" +
                "<br/>" +
                "<a href=\"rest/test\">Test XML</a>" +
                "<br/>" +
                "<a href=\"rest/exception\">Exception</a>" +
                "<br/>" +
                "<a href=\"rest/param\">Parameter</a>" +
                "<br/>" +
                "<a href=\"rest/header\">Header</a>" +
                "<br/>" +
                "</body></html>");
        Response.ResponseBuilder bob = Response.ok();
        bob.type(MediaType.TEXT_HTML);
        bob.entity(wendy.toString());
        return bob.build();
    }

    /**
     * Provides a test XML response.
     *
     * @return A {@link Response} containing a simple XML message.
     */
    @GET
    @Path("/test")
    @Produces(MediaType.APPLICATION_XML)
    public Response test() {
        Response.ResponseBuilder bob = Response.ok();
        bob.type(MediaType.APPLICATION_XML);
        bob.entity("<message>This is a Test XML message</message>");
        return bob.build();
    }

    /**
     * Provides a test HTML page for triggering an exception.
     *
     * @return A {@link Response} containing HTML content with a link to test exception handling.
     */
    @GET
    @Path("/exception")
    @Produces(MediaType.TEXT_HTML)
    public Response exception() {
        StringBuilder wendy = new StringBuilder();
        wendy.append("<html><head><title>HelloRestServer</title></head>" +
                "<body>" +
                "<h1>Exception REST Branch</h1>" +
                "<br/>" +
                "<a href=\"exception/test\">Test exception</a>" +
                "<br/>" +
                "</body></html>");
        Response.ResponseBuilder bob = Response.ok();
        bob.type(MediaType.TEXT_HTML);
        bob.entity(wendy.toString());
        return bob.build();
    }

    /**
     * A demonstration of an endpoint that intentionally throws an exception to test the exception handling mechanism.
     *
     * @return A {@link Response} containing HTML content or throws an Exception.
     * @throws Exception to simulate an error condition.
     */
    @GET
    @Path("/exception/test")
    @Produces(MediaType.TEXT_HTML)
    public Response exceptionTest() throws Exception {
        // Intentionally causes an ArrayIndexOutOfBoundsException to test exception handling.
        int numbers[] = new int[25];
        for (int i = 0; i < 26; i++) {
            numbers[i] = i;
        }
        StringBuilder wendy = new StringBuilder();
        wendy.append("<html><head><title>HelloRestServer</title></head>" +
                "<body>" +
                "<h1>Exception Test</h1>" +
                "<br/>" +
                "<div>Numbers: " + numbers.toString() + "</div>" +
                "</body></html>");
        Response.ResponseBuilder bob = Response.ok();
        bob.type(MediaType.TEXT_HTML);
        bob.entity(wendy.toString());
        return bob.build();
    }
}