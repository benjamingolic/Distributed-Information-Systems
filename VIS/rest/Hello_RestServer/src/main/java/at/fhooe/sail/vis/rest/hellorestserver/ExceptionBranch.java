package at.fhooe.sail.vis.rest.hellorestserver;

import jakarta.ws.rs.core.*;
import jakarta.ws.rs.ext.ExceptionMapper;

/**
 * Custom exception mapper that converts any {@link Throwable} encountered during
 * the processing of a RESTful request into an HTTP response with a formatted HTML message.
 *
 * @Authors: Benjamin Golić (MC) & Amel Šahbaz (AC)
 */
public class ExceptionBranch implements ExceptionMapper<Throwable> {
    @Context
    private UriInfo mUriInfo; // Injects information about the URI of the request that caused the exception

    /**
     * Converts a thrown exception into a {@link Response} object that contains
     * an HTML representation of the error for display. This method is automatically
     * called by the JAX-RS runtime when an exception is thrown.
     *
     * @param _e The exception that was thrown during the processing of the RESTful request.
     * @return A {@link Response} object containing the HTML formatted error message.
     */
    @Override
    public Response toResponse(Throwable _e) {
        StringBuilder wendy = new StringBuilder();
        wendy.append("<html><head><title>HelloRestServer</title></head>" +
                "<body><h1>Exception thrown</h1><br/>" +
                "<p>Exception: " + _e.getMessage() + "</p></body></html>");
        Response.ResponseBuilder bob = Response.ok();
        bob.type(MediaType.TEXT_HTML);
        bob.entity(wendy.toString());
        return bob.build();
    }
}
