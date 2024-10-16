package at.fhooe.sail.vis.bruteforce;

/**
 * Provides utility methods for extracting geographic coordinates from an XML fragment.
 *
 * @Authors: Benjamin Golić (MC) & Amel Šahbaz (AC)
 */
public class BruteForce {

    /**
     * Extracts latitude and longitude coordinates from a given XML fragment.
     * The XML fragment must contain the latitude and longitude within the tags
     * <latitude> and <longitude> respectively.
     *
     * @param xmlFragment The XML string containing the geographic coordinates.
     * @return A WGS84 object containing the extracted latitude and longitude.
     */
    public static WGS84 extractCoordinates(String xmlFragment) {
        WGS84 container = new WGS84();

        String latitudeStartTag = "<latitude>";
        String latitudeEndTag = "</latitude>";
        int latitudeStartIndex = xmlFragment.indexOf(latitudeStartTag) + latitudeStartTag.length();
        int latitudeEndIndex = xmlFragment.indexOf(latitudeEndTag);
        String latitudeString = xmlFragment.substring(latitudeStartIndex, latitudeEndIndex);
        container.setLatitude(Double.parseDouble(latitudeString.trim()));

        String longitudeStartTag = "<longitude>";
        String longitudeEndTag = "</longitude>";
        int longitudeStartIndex = xmlFragment.indexOf(longitudeStartTag) + longitudeStartTag.length();
        int longitudeEndIndex = xmlFragment.indexOf(longitudeEndTag);
        String longitudeString = xmlFragment.substring(longitudeStartIndex, longitudeEndIndex);
        container.setLongitude(Double.parseDouble(longitudeString.trim()));

        return container;
    }

    /**
     * Main method to demonstrate the extraction of coordinates from an XML string.
     *
     * @param args The command line arguments (not used).
     */
    public static void main(String[] args) {
        String xml = "<wgs84><latitude>48.31</latitude><longitude>14.29</longitude></wgs84>";
        WGS84 coordinates = extractCoordinates(xml);
        System.out.println("Latitude: " + coordinates.getLatitude());
        System.out.println("Longitude: " + coordinates.getLongitude());
    }
}
