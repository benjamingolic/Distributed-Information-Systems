/**
 * @Authors: Benjamin Golić (MC) & Amel Šahbaz (AC)
 */

package at.fhooe.sail.vis.environmentclient;

import at.fhooe.sail.vis.environmenti.EnvData;
import at.fhooe.sail.vis.environmenti.IEnvService;

import java.io.*;
import java.net.*;
import java.util.*;

public class Environment_SocketClient implements IEnvService {
//public class Environment_SocketClient {

    /**
     * Task 1.3d)
     */

    private Socket mSocket;
    private Scanner mScanner;
    private PrintWriter mWriter;
    private BufferedReader mReader;
    private InputStreamReader mInputStreamReader;
    private String mRequestSensorTypes;


    /**
     * Constructs an {@code Environment_SocketClient} and initializes the connection to the server.
     *
     * @param _ip   The IP address of the server.
     * @param _port The port number of the server.
     */
    public Environment_SocketClient(String _ip, int _port) {
        init(_ip, _port);
    }
    /**
     * Initializes the client socket and I/O streams for communication with the server.
     *
     * @param _ip   The IP address of the server.
     * @param _port The port number of the server.
     */
    public void init(String _ip, int _port) {
        try {
            mSocket = new Socket(_ip, _port);
            mScanner = new Scanner(new InputStreamReader(mSocket.getInputStream()));
            mWriter = new PrintWriter(mSocket.getOutputStream(), true);
            mInputStreamReader = new InputStreamReader(mSocket.getInputStream());
            mReader = new BufferedReader(mInputStreamReader);
            mRequestSensorTypes = "getSensortypes()#";

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }



    /**
     * Requests the available sensor data types from the server.
     *
     * @return An array of strings representing the types of sensor data available.
     */
    @Override
    public String[] requestEnvironmentDataTypes() {
        mWriter.print(mRequestSensorTypes);
        mWriter.flush();
        String mResponse = mScanner.next();
        mResponse += mScanner.next();

        mResponse = processServerResponse(mResponse);

        String[] mSensorTypes = mResponse.split(";");
        mSensorTypes[2] = mSensorTypes[2].split("#")[0];
        return mSensorTypes;
    }

    /**
     * Requests specific environmental data based on the sensor type.
     *
     * @param _type The type of sensor data requested.
     * @return {@link EnvData} object containing the requested sensor data.
     */
    @Override
    public EnvData requestEnvironmentData(String _type) {
        mWriter.print("getSensor(" + _type + ")#");
        mWriter.flush();

        String mResponse = mScanner.next();
        mResponse += mScanner.next();
        mResponse = processServerResponse(mResponse);

        String[] mRData = mResponse.split("\\|");
        System.out.println(Arrays.toString(mRData));

        String[] mValues = mRData[1].split(";");
        long mTimestamp = Long.parseLong(mRData[0]);
        String[] mSensorValues = mRData[1].split("#");
        String[] mSensorValues2 = mSensorValues[0].split(";");

        int[] mSensorData = new int[mValues.length];
        for (int i = 0; i < mValues.length; i++) {
            mSensorData[i] = Integer.parseInt(mSensorValues2[i]);
        }

        return new EnvData(_type, mTimestamp, mSensorData);
    }

    /**
     * Requests all available environmental data from the server.
     *
     * @return An array of {@link EnvData} objects containing all available sensor data.
     */
    @Override
    public EnvData[] requestAll() {
        String[] mAllSensors = requestEnvironmentDataTypes();
        EnvData[] mAllSData = new EnvData[mAllSensors.length];
        for (int i = 0; i < mAllSData.length; i++) {
            mAllSData[i] = requestEnvironmentData(mAllSensors[i]);
        }
        return mAllSData;
    }

    /**
     * Processes the server response to extract useful information.
     *
     * @param mResponse The server response as a string.
     * @return A processed string with useful information extracted from the server response.
     */
    private String processServerResponse(String mResponse) {
        String[] mParts = mResponse.split("#", 2);
        if (mParts.length > 1) {
            return mParts[1];
        }
        return mResponse;
    }




    /**
     * Normal Environment_SocketClient
     */
/*
    public static void main(String[] args) {
        String mIP = "127.0.0.1"; // IP address of the server
        int mPort = 4949; // Port number to connect to

        try {
            // Establish a socket connection to the server
            Socket socket = new Socket(mIP, mPort);
            System.out.println("Connected to server on port " + mPort + "!");

            // Setup streams for communication
            OutputStream outputStream = socket.getOutputStream();
            InputStream inputStream = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            StringBuffer mLine = new StringBuffer();

            boolean mConnected = true; // Flag to keep track of connection status

            label:
            while (mConnected) {
                System.out.print("Enter message for server: ");
                String mInput = reader.readLine() + "\n"; // read from console

                if (mInput.trim().equals("quit")) {
                    System.out.println("Closing connection...");
                    socket.close();
                    break;
                }
                // Process various commands entered by the user
                switch (mInput.trim()) {
                    case "drop":
                        // Send specific commands to the server
                        outputStream.write(mInput.trim().concat("\0").getBytes());
                        break label;
                    case "shutdown":
                        outputStream.write(mInput.trim().concat("\0").getBytes());
                        break label;
                    case "getSensortypes()#":
                        outputStream.write(mInput.trim().concat("\0").getBytes());
                        break;
                    case "getSensor(air)#":
                        outputStream.write(mInput.trim().concat("\0").getBytes());
                        break;
                    case "getSensor(noise)#":
                        outputStream.write(mInput.trim().concat("\0").getBytes());
                        break;
                    case "getSensor(light)#":
                        outputStream.write(mInput.trim().concat("\0").getBytes());
                        break;
                    case "getAllSensors()#":
                        outputStream.write(mInput.trim().concat("\0").getBytes());
                        break;
                    default:
                        outputStream.write(mInput.getBytes()); // send message to server

                        mInput = null;
                        break;
                }

                // Read the response from the server
                int mData = -1;
                mLine = new StringBuffer();
                while(true) {
                    mData = inputStream.read(); // read from server
                    if (mData == -1) {
                        System.out.println("Connection closed by server!");
                        mConnected = false; // Update the flag if server closes connection
                        break;
                    }
                    if (!(((char)mData) == '\n')) {
                        mLine.append((char) mData); // append data to string buffer except '\n'
                    }
                    if (((char) mData) == '\n') { // if end of line
                        String rOut = mLine.toString();

                        int index = rOut.indexOf("#");
                        if(index != -1) {
                            rOut = rOut.substring(0, index+1) + "\n" + rOut.substring(index+1);
                        }

                        System.out.println("received: " + rOut);
                        break;
                    }

                }
            }
            // Close all resources and the socket
            System.out.println("Closing connection...");
            reader.close();
            outputStream.flush();
            outputStream.close();
            inputStream.close();
            socket.close();
        } catch (IOException e) {
            // Handle any IO exceptions
            System.err.println("Server Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

*/
}
