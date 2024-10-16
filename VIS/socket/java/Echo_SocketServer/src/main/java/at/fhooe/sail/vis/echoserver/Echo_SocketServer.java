/**
 * @Authors: Benjamin Golić (MC) & Amel Šahbaz (AC)
 */

package at.fhooe.sail.vis.echoserver;
import java.io.*;
import java.net.*;

public class Echo_SocketServer {
    /**
     * The main method initiates the server application. It sets up a server socket to listen
     * for incoming client connections and processes messages received from clients.
     * It also handles specific commands to control server and client connections.
     *
     * @param _args Command-line arguments (not used).
     */
    public static void main(String[] _args) {
        int mPort = 4949;
        ServerSocket mServerSocket = null;
        Socket mClientSocket = null;

        try {
            // Create a server socket listening on the specified port
            mServerSocket = new ServerSocket(mPort);


            // Infinite loop to accept client connections
            while (true) {
                System.out.println("Echo Socket Server waiting for clients on mPort " + mPort + "...");

                // Accept a client connection
                mClientSocket = mServerSocket.accept();
                InetAddress clientIP = mClientSocket.getInetAddress();
                int clientPort = mClientSocket.getPort();

                // Display the client connection details
                System.out.println("Connection established with client on..." +
                        "\nSocket[client(" + clientIP + ", " + clientPort +
                        "), server(127.0.0.1, " + mPort + ")]");

                // Set up input and output streams for the connection
                InputStream inStream = mClientSocket.getInputStream();
                OutputStream outStream = mClientSocket.getOutputStream();
                int data = -1;
                StringBuffer line = new StringBuffer();

                // Read data sent by the client and process it
                while ((data = inStream.read()) != -1) {
                    char dataChar = (char) data;
                    line.append(dataChar);

                    // Process complete lines (ending with newline character)
                    if (dataChar == '\n') {
                        String received = line.toString();
                        System.out.print("Received from Client: " + received);

                        if (received.trim().equals("shutdown")) {
                            // Shutdown command: close the server socket and exit
                            System.out.println("Shutdown command received. Shutting down.");
                            outStream.write("Shutting down the server.".getBytes());
                            outStream.flush();
                            mServerSocket.close();
                            return;
                        } else if (received.trim().equals("drop")) {
                            // Drop command: close the current client connection
                            System.out.println("Drop command received. Client is getting dropped.");
                            mClientSocket.close();
                            break;
                        } else if (received.trim().equals("quit")) {
                            // Quit command: exit the current loop, but continue listening for new connections
                            System.out.println("Quit command received. Client is quitting.");
                        }else {
                            // Echo back the received message to the client
                            outStream.write(("ECHO: " + received).getBytes());
                            outStream.flush();
                            line = new StringBuffer();
                        }
                    }
                }

                // Close the client socket if it's not already closed
                if (!mClientSocket.isClosed()) {
                    mClientSocket.close();
                }
            }
        } catch (IOException e) {
            // Handle exceptions related to input/output operations
            System.err.println("Server Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
