/**
 * @Authors: Benjamin Golić (MC) & Amel Šahbaz (AC)
 */

package at.fhooe.sail.vis.infoservlet;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet(name = "InfoServlet", urlPatterns = {"/info", "/i"})
public class Info_Servlet extends HttpServlet {
    /**
     * Processes GET requests by responding with an HTML page that displays various pieces of
     * information about the client's request and the server. This includes the client's IP address,
     * browser type, accepted MIME types, communication protocol, server port, server name, and any
     * specific parameters included in the request.
     *
     * @param _request  The {@link HttpServletRequest} object containing the client's request information.
     * @param _response The {@link HttpServletResponse} object for sending the response back to the client.
     * @throws ServletException If the request could not be handled.
     * @throws IOException      If an input or output error occurs while the servlet is handling the GET request.
     */
    @Override
    public void doGet(HttpServletRequest _request, HttpServletResponse _response) throws ServletException, IOException {
        _response.setContentType("text/html");
        String clientIP = _request.getRemoteAddr();
        String clientBrowser = _request.getHeader("User-Agent");
        String clientMimeTypes = _request.getHeader("Accept");
        String clientProtocol = _request.getProtocol();
        int port = _request.getServerPort();
        String serverName = _request.getServerName();
        String param = _request.getParameter("param");

        PrintWriter out = _response.getWriter();
        out.println("<html><head><title>Info Servlet</title></head><body>");
        out.println("<h1>Info Servlet</h1>");
        out.println("<p>Client IP: " + clientIP + "</p>");
        out.println("<p>Client Browser: " + clientBrowser + "</p>");
        out.println("<p>Client MIME Types: " + clientMimeTypes + "</p>");
        out.println("<p>Client Protocol: " + clientProtocol + "</p>");
        out.println("<p>Port: " + port + "</p>");
        out.println("<p>Server Name: " + serverName + "</p>");
        out.println("<p>Param: " + param + "</p>");
        out.println("</body></html>");
    }
}