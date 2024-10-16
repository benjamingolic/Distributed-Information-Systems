/**
 * @Authors: Benjamin Golić (MC) & Amel Šahbaz (AC)
 */

package at.fhooe.sail.vis.helloservlet;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
/**
 * A simple servlet that responds to GET requests with a "Hello World" message.
 * @Authors: Benjamin Golić (MC) & Amel Šahbaz (AC)
 */
@WebServlet(name = "HelloWorldServlet", urlPatterns = {"/hello", "/moin"})
public class Hello_Servlet extends HttpServlet {
    private int mCounter = 0;

    /**
     * Processes GET requests by responding with an HTML page containing a "Hello World"
     * message and a counter indicating the number of times the servlet has been accessed.
     * This method increments the counter with each call.
     *
     * @param _request  The {@link HttpServletRequest} object that contains the request the client has made to the servlet.
     * @param _response The {@link HttpServletResponse} object that contains the response the servlet sends to the client.
     * @throws ServletException If the request could not be handled.
     * @throws IOException      If an input or output error occurs while the servlet is handling the GET request.
     */
    public void doGet(HttpServletRequest _request, HttpServletResponse _response) throws ServletException, IOException {
        mCounter++;

        _response.setContentType("text/html");
        PrintWriter out = _response.getWriter();
        out.println("<html><head><title>Hello Servlet</title></head><body>");
        out.println("<h1>Hello World</h1>");
        out.println("<p>Servlet-Calls counter: " + mCounter + "</p>");
        out.println("</body></html>");
    }
}