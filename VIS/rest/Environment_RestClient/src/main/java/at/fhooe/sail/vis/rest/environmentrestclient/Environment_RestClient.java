package at.fhooe.sail.vis.rest.environmentrestclient;

import at.fhooe.sail.vis.rest.environmentrestserver.EnvData;
import at.fhooe.sail.vis.rest.environmentrestserver.EnvDataList;
import at.fhooe.sail.vis.rest.environmentrestserver.Sensors;

import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.IOException;
import java.util.Arrays;

/**
 * A REST client for fetching environmental data from a RESTful web service.
 * This client demonstrates how to request and deserialize XML data into Java objects.
 *
 * @Authors: Benjamin Golić (MC) & Amel Šahbaz (AC)
 */
public class Environment_RestClient {

    public Environment_RestClient() {
    }

    /**
     * Main method to demonstrate fetching and processing environmental data from the RESTful service.
     *
     * @param args Command line arguments (not used).
     * @throws JAXBException If an error occurs during XML unmarshalling.
     * @throws IOException If an error occurs in communication with the web service.
     */
    public static void main(String[] args) throws JAXBException, IOException {
        Environment_RestClient environmentRestClient = new Environment_RestClient();
        System.out.println("--");
        System.out.println(Arrays.toString(environmentRestClient.requestEnvironmentDataTypes()));
        System.out.println("--");
        String environmentDataType = Arrays.toString(environmentRestClient.requestEnvironmentDataTypes()).replaceAll("^\\[|]$", "");
        System.out.println(environmentRestClient.requestEnvironmentData(environmentDataType));
        System.out.println("--");
        System.out.println(Arrays.toString(environmentRestClient.requestAll()));

    }

    /**
     * Requests all available environmental data.
     *
     * @return An array of {@link EnvData} representing all available environmental data.
     * @throws IOException If an error occurs in communication with the web service.
     * @throws JAXBException If an error occurs during XML unmarshalling.
     */
    public EnvData[] requestAll() throws IOException, JAXBException {
        return deserializeXMLToEnvDataList(fetchContentFromWeb("http://localhost:8080/Environment_RestServer/data/ALL?output=XML"));
    }

    /**
     * Deserializes XML to an array of {@link EnvData}.
     *
     * @param _xml The XML string to deserialize.
     * @return An array of {@link EnvData} extracted from the XML.
     * @throws JAXBException If an error occurs during unmarshalling.
     */
    public EnvData[] deserializeXMLToEnvDataList(String _xml) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(EnvDataList.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        EnvDataList envDataList = (EnvDataList) unmarshaller.unmarshal(new StringReader(_xml));
        return envDataList.getEnvData();
    }

    /**
     * Requests environmental data for a specific type.
     *
     * @param _type The type of environmental data to request.
     * @return An instance of {@link EnvData} representing the requested data.
     * @throws IOException If an error occurs in communication with the web service.
     * @throws JAXBException If an error occurs during XML unmarshalling.
     */
    public EnvData requestEnvironmentData(String _type) throws IOException, JAXBException {
        return deserializeXMLToEnvData(fetchContentFromWeb("http://localhost:8080/Environment_RestServer/data/" + _type + "?output=XML"));
    }

    /**
     * Deserializes XML to a single instance of {@link EnvData}.
     *
     * @param _xml The XML string to deserialize.
     * @return An {@link EnvData} object extracted from the XML.
     * @throws JAXBException If an error occurs during unmarshalling.
     */
    public EnvData deserializeXMLToEnvData(String _xml) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(EnvData.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (EnvData) unmarshaller.unmarshal(new StringReader(_xml));
    }

    /**
     * Requests the types of environmental data available.
     *
     * @return An array of Strings representing the available data types.
     * @throws IOException If an error occurs in communication with the web service.
     * @throws JAXBException If an error occurs during XML unmarshalling.
     */
    public String[] requestEnvironmentDataTypes() throws IOException, JAXBException {
        Sensors sensors = deserializeXMLToSensors(fetchContentFromWeb("http://localhost:8080/Environment_RestServer/info/sensortypes?output=XML"));
        return sensors.getSensorTypes();
    }

    /**
     * Deserializes XML to an instance of {@link Sensors}.
     *
     * @param _xml The XML string to deserialize.
     * @return A {@link Sensors} object representing the sensor types.
     * @throws JAXBException If an error occurs during unmarshalling.
     */
    public Sensors deserializeXMLToSensors(String _xml) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Sensors.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (Sensors) unmarshaller.unmarshal(new StringReader(_xml));
    }

    /**
     * Fetches content from a specified web address.
     *
     * @param _webAddress The URL from which to fetch content.
     * @return A String containing the content fetched from the specified URL.
     * @throws IOException If an error occurs in communication with the web service.
     */
    public String fetchContentFromWeb(String _webAddress) throws IOException {
        URL targetUrl = new URL(_webAddress);
        HttpURLConnection connection = (HttpURLConnection) targetUrl.openConnection();
        connection.setRequestMethod("GET");

        if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new IOException("Request Failed: HTTP Status Code: " + connection.getResponseCode());
        }

        StringBuilder result = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            connection.disconnect();
        }

        // System.out.println("Fetched content from " + webAddress + " of type " + contentType + ": " + result.toString()); debugging sout
        return result.toString();
    }

}