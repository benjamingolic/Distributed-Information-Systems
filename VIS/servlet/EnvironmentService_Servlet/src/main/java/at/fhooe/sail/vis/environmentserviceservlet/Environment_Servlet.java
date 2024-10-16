/**
 * @Authors: Benjamin Golić (MC) & Amel Šahbaz (AC)
 */

package at.fhooe.sail.vis.environmentserviceservlet;

import java.io.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;
import java.util.List;

import at.fhooe.sail.vis.environmentclient.Environment_SocketClient;
import at.fhooe.sail.vis.environmenti.EnvData;
import at.fhooe.sail.vis.environmenti.IEnvService;
import at.fhooe.sail.vis.rest.environmentrestclient.Environment_RestClient;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

/**
 * Servlet that acts as a gateway to environmental data from various sources including a C++ server via sockets,
 * an RMI server, and a RESTful web service. It consolidates and displays this data in an HTML page.
 *
 * @Authors: Benjamin Golić (MC) & Amel Šahbaz (AC)
 */
@WebServlet(name = "Environment_Servlet", urlPatterns = {"/environmentServlet", "/envS"})
public class Environment_Servlet extends HttpServlet {

    /**
     * Handles GET requests by aggregating environmental data from multiple sources and presenting
     * it on a web page. Supports toggling an auto-reload feature that refreshes the page periodically.
     *
     * @param _request  The HttpServletRequest object containing the client's request.
     * @param _response The HttpServletResponse object containing the servlet's response.
     * @throws ServletException If the request for the GET could not be handled.
     * @throws IOException If an I/O error occurs during the handling of the GET request.
     */
    @Override
    public void doGet(HttpServletRequest _request, HttpServletResponse _response) throws ServletException, IOException {
        // Implementation details including fetching data from C++ server, RMI server, and RESTful web service.
        // The method then dynamically constructs an HTML response that includes the fetched data and control
        // for enabling/disabling the auto-reload feature of the page.
        IEnvService envSocketClient = new Environment_SocketClient("127.0.0.1", 4949);

        Registry reg = null;
        IEnvService rmiEnvClient = null;


        try {
            reg = LocateRegistry.getRegistry();
            rmiEnvClient = (IEnvService) reg.lookup("EnvironmentServiceI");
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<EnvData> cppServerData = null;
        try {
            cppServerData = List.of(envSocketClient.requestAll());
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<EnvData> rmiServerData = null;
        try {
            rmiServerData = List.of(rmiEnvClient.requestAll());
        } catch (Exception e) {
            e.printStackTrace();
        }

        HttpSession session = _request.getSession(true);

        String autoReloadParam = _request.getParameter("autoReload");
        if (autoReloadParam != null) {
            session.setAttribute("autoReload", Boolean.parseBoolean(autoReloadParam));
        }

        boolean autoReload = false;
        if (session.getAttribute("autoReload") != null) {
            autoReload = (Boolean) session.getAttribute("autoReload");
        }

        Environment_RestClient restClient = new Environment_RestClient();
        at.fhooe.sail.vis.rest.environmentrestserver.EnvData restServerData = null;
        try {
            restServerData = restClient.requestEnvironmentData("humidity");
        } catch (Exception e) {
            e.printStackTrace();
        }

        PrintWriter out = _response.getWriter();
        // HTML content construction for displaying environmental data and auto-reload controls.
        out.println("<html><head><title>Environment Service</title><link href=\"https://fonts.googleapis.com/css2?family=Roboto:wght@100&display=swap\" rel=\"stylesheet\"><style type='text/css'>");
        out.println("body {" +
                "    margin: 0;" +
                "    padding: 0;" +
                "    background-color: #f3f3f3;" +
                "    overflow: hidden;" +
                "    font-family: 'Roboto', sans-serif;" +
                "}");
        out.println(".wrapper {" +
                "    display: flex;" +
                "    flex-direction: column;" +
                "    align-items: center;" +
                "    justify-content: center;" +
                "    height: 85vh;" +
                "    width: 100vw;" +
                "}");
        out.println(".material-table {\n" +
                "    width: 35%;\n" +
                "    border-collapse: collapse;\n" +
                "    margin-bottom: 1rem;\n" +
                "    font-size: 0.9em;\n" +
                "    box-shadow: 0 0 20px rgba(0, 0, 0, 0.15);\n" +
                "    table-layout: fixed;\n" +
                "}\n" +
                ".material-table th {\n" +
                "    font-weight: 100;\n" +
                "    background-color: #009688;\n" +
                "    color: white;\n" +
                "}\n" +
                ".material-table th,\n" +
                ".material-table td {\n" +
                "    padding: 12px 15px;\n" +
                "    text-align: center;\n" +
                "    width: 33.33%;\n" +
                "}\n" +
                "\n" +
                ".material-table tbody tr {\n" +
                "    border-bottom: 1px solid #dddddd;\n" +
                "}\n" +
                "\n" +
                ".material-table tbody tr:nth-of-type(even) {\n" +
                "    background-color: #f3f3f3;\n" +
                "}\n" +
                "\n" +
                ".material-table tbody tr:last-of-type {\n" +
                "    border-bottom: 2px solid #009688;\n" +
                "}\n" +

                "\n" +
                ".material-button {\n" +
                "    background-color: #009688;\n" +
                "    color: white;\n" +
                "    padding: 10px 20px;\n" +
                "    border: none;\n" +
                "    border-radius: 5px;\n" +
                "    cursor: pointer;\n" +
                "    font-size: 0.9em;\n" +
                "    transition: background 0.3s;\n" +
                "}\n" +
                "\n" +
                ".material-button:hover {\n" +
                "    background-color: #00796b;\n" +
                "}" +
                ".material-button:disabled {\n" +
                "    background-color: #bdbdbd;\n" +
                "    cursor: not-allowed;\n" +
                "}" +
                "</style>");
        out.println("<script type='text/javascript'>" +
                "var autoReloadInterval;" +
                "function setAutoReload(isEnabled) {" +
                "  var reloadButton = document.getElementById('reloadButton');" +
                "  clearInterval(autoReloadInterval);" +
                "  if (isEnabled) {" +
                "    autoReloadInterval = setInterval(function() { window.location.reload(); }, 5000);" +
                "    reloadButton.disabled = true;" +
                "    window.history.replaceState(null, '', '?autoReload=true');" +
                "  } else {" +
                "    reloadButton.disabled = false;" +
                "    window.history.replaceState(null, '', window.location.pathname);" +
                "  }" +
                "}" +
                "window.onload = function() {" +
                "  var autoReloadCheckbox = document.getElementById('autoReloadCheckbox');" +
                "  var autoReloadParam = new URLSearchParams(window.location.search).get('autoReload');" +
                "  var autoReload = autoReloadParam === 'true';" +
                "  autoReloadCheckbox.checked = autoReload;" +
                "  setAutoReload(autoReload);" +
                "};" +
                "</script>");

        out.println("</head><body><div class=\"wrapper\"><h1 style=\"text-align:center\">Server Data</h1>");

        if (cppServerData == null) {
            out.println("<p>The c++ server is currently not available.</p>");
        } else {
            out.println("<h2>C++ Server Data</h2><table class=\"material-table\"><tr>");
            out.println("<th>Timestamp</th>");
            out.println("<th>Sensor</th>");
            out.println("<th>Value</th>");
            out.println("</tr>");
            for (EnvData data : cppServerData) {
                out.println("<tr>");
                out.println("<td>" + data.getmTimestamp() + "</td>");
                out.println("<td>" + data.getmSensorName() + "</td>");
                out.println("<td>" + Arrays.toString(data.getmValues()).replaceAll("\\[", "").replaceAll("\\]", "") + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");
        }
        if (rmiServerData == null) {
            out.println("<p>The rmi server is currently not available.</p>");
        } else {
            out.println("<h2>RMI Server Data</h2><table class=\"material-table\"><tr>");
            out.println("<th>Timestamp</th>");
            out.println("<th>Sensor</th>");
            out.println("<th>Value</th>");
            out.println("</tr>");
            for (EnvData data : rmiServerData) {
                out.println("<tr>");
                out.println("<td>" + data.getmTimestamp() + "</td>");
                out.println("<td>" + data.getmSensorName() + "</td>");
                out.println("<td>" + Arrays.toString(data.getmValues()).replaceAll("\\[", "").replaceAll("\\]", "") + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");
        }
        if (restServerData == null) {
            out.println("<p>The rest server is currently not available.</p>");
        } else {
            out.println("<h2>REST Server Data</h2><table class=\"material-table\"><tr>");
            out.println("<th>Timestamp</th>");
            out.println("<th>Sensor</th>");
            out.println("<th>Value</th>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td>" + restServerData.getTimestamp() + "</td>");
            out.println("<td>" + restServerData.getSensorName() + "</td>");
            out.println("<td>" + Arrays.toString(restServerData.getValues()).replaceAll("\\[", "").replaceAll("\\]", "") + "</td>");
            out.println("</tr>");
            out.println("</table>");
        }

        out.println("<form style='margin-bottom: 20px;'>" +
                "<input type='button' id='reloadButton' class=\"material-button\" value='Reload' " +
                (autoReload ? "disabled" : "") + " onclick='location.reload();'>&nbsp;" +
                "<label><input type='checkbox' id='autoReloadCheckbox' " +
                (autoReload ? "checked" : "") +
                " onchange='setAutoReload(this.checked)'> Auto-Reload</label>" +
                "</form>");

    }
}
