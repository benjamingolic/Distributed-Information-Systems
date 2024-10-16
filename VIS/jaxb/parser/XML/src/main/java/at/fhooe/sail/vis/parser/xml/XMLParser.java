package at.fhooe.sail.vis.parser.xml;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.StringReader;

/**
 * SAX handler to parse XML data into {@link WindData} objects.
 * This parser handles XML elements specific to wind information,
 * such as wind speed and direction (degree).
 *
 * @Authors: Benjamin Golić (MC) & Amel Šahbaz (AC)
 */
public class XMLParser extends DefaultHandler {

    private WindData windData; // Holder for wind data being parsed
    private StringBuilder elementValue; // Builder for reading character data
    private boolean parsingSpeed; // Flag indicating if speed is currently being parsed
    private boolean parsingDegree; // Flag indicating if degree is currently being parsed

    /**
     * Gets the parsed wind data.
     *
     * @return The {@link WindData} object populated during XML parsing.
     */
    public WindData getWindData() {
        return windData;
    }

    /**
     * Invoked by the SAX parser at the start of the document. Initializes the objects required for parsing.
     */
    @Override
    public void startDocument() {
        windData = new WindData();
        elementValue = new StringBuilder();
    }

    /**
     * Invoked by the SAX parser at the start of an XML element. Determines if the current element is either
     * <speed> or <deg> to prepare for parsing these values.
     *
     * @param uri The Namespace URI, or the empty string if the element has no Namespace URI or if Namespace
     *            processing is not being performed.
     * @param localName The local name (without prefix), or the empty string if Namespace processing is not being
     *                  performed.
     * @param qName The qualified name (with prefix), or the empty string if qualified names are not available.
     * @param attributes The attributes attached to the element. If there are no attributes, it shall be an empty
     *                   Attributes object.
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if ("speed".equalsIgnoreCase(qName)) {
            parsingSpeed = true;
        } else if ("deg".equalsIgnoreCase(qName)) {
            parsingDegree = true;
        }
    }

    /**
     * Invoked by the SAX parser to process character data (text) encountered in the XML document.
     * If the parser is currently inside a <speed> or <deg> element, this method accumulates the character
     * data inside these elements.
     *
     * @param ch The characters from the XML document.
     * @param start The start position in the array.
     * @param length The number of characters to read from the array.
     */
    @Override
    public void characters(char[] ch, int start, int length) {
        if (parsingSpeed || parsingDegree) {
            elementValue.append(ch, start, length);
        }
    }

    /**
     * Invoked by the SAX parser at the end of an XML element. If the element is <speed> or <deg>,
     * this method parses the accumulated character data to the appropriate type and stores it in the
     * {@link WindData} object.
     *
     * @param uri The Namespace URI, or the empty string if the element has no Namespace URI or if Namespace
     *            processing is not being performed.
     * @param localName The local name (without prefix), or the empty string if Namespace processing is not being
     *                  performed.
     * @param qName The qualified name (with prefix), or the empty string if qualified names are not available.
     */
    @Override
    public void endElement(String uri, String localName, String qName) {
        if (parsingSpeed) {
            windData.setSpeed(Double.parseDouble(elementValue.toString().trim()));
            parsingSpeed = false;
        } else if (parsingDegree) {
            windData.setDegree(Integer.parseInt(elementValue.toString().trim()));
            parsingDegree = false;
        }
        elementValue.setLength(0);
    }

    /**
     * Main method to demonstrate XML parsing using this SAX handler.
     * It parses a simple XML string to extract wind speed and degree.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            XMLParser parser = new XMLParser();

            String xmlContent = "<wind><speed>50.25</speed><deg>225</deg></wind>";
            saxParser.parse(new InputSource(new StringReader(xmlContent)), parser);

            WindData windData = parser.getWindData();
            System.out.println("Wind Speed: " + windData.getSpeed());
            System.out.println("Wind Degree: " + windData.getDegree());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
