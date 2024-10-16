/**
 * @Authors: Benjamin Golić (MC) & Amel Šahbaz (AC)
 */

package at.fhooe.sail.vis.sessionservlet;

import java.io.*;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet(name = "SessionServlet", urlPatterns = {"/session", "/s"})
public class Session_Servlet extends HttpServlet {
    private String mMagicNumber = "";

    /**
     * Processes GET requests by responding with an HTML page that displays the type of client browser and
     * a "magic number" parameter. It also shows the magic number from the previous visit.
     *
     * @param _request  The {@link HttpServletRequest} object containing the client's request information.
     * @param _response The {@link HttpServletResponse} object for sending the response back to the client.
     * @throws ServletException If the request could not be handled.
     * @throws IOException      If an input or output error occurs while the servlet is handling the GET request.
     */
    @Override
    public void doGet(HttpServletRequest _request, HttpServletResponse _response) throws ServletException, IOException {
        _response.setContentType("text/html");
        String clientBrowser = _request.getHeader("User-Agent");
        String param = _request.getParameter("param");

        PrintWriter out = _response.getWriter();
        out.println("<html><head><title>Session Servlet</title></head><body>");
        out.println("<p>You are currently using " + clientBrowser + " (current magic number: " + param + ")." + "</p>");
        out.println("<p>Last time you visited, your magic number was " + mMagicNumber + "!" + "</p>");
        out.println("</body></html>");
        mMagicNumber = param;
    }
}