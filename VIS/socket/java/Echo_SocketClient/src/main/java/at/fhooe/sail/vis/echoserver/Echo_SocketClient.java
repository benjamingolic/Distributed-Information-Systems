/**
 * @Authors: Benjamin Golić (MC) & Amel Šahbaz (AC)
 */

package at.fhooe.sail.vis.echoserver;

import java.io.*;
import java.net.*;

public class Echo_SocketClient {
    /**
     * The main method that initiates the client application. It establishes a connection to the server,
     * enables sending and receiving messages, and processes special commands.
     *
     * @param _args Command-line arguments (not used).
     */
    public static void main(String[] _args) {
        String mIP = "127.0.0.1";
        int mPort = 4949;

        try {
            Socket socket = new Socket(mIP, mPort);
            System.out.println("Connected to server port " + mPort + "!");

            OutputStream outputStream = socket.getOutputStream();
            InputStream inputStream = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            StringBuffer line = new StringBuffer();
            
            while (true) {
                System.out.print("Enter message: ");
                String input = reader.readLine() + "\n"; // read from console

                if (input.trim().equals("quit")) {
                    System.out.println("Quitting the client...");
                    outputStream.write(input.getBytes());
                    socket.close();
                    break;
                } else if (input.trim().equals("drop")) {
                    System.out.println("Server dropping the connection...");
                    outputStream.write(input.getBytes());
                    break;
                } else if (input.trim().equals("shutdown")) {
                    System.out.println("Shutting down...");
                    outputStream.write(input.getBytes());
                    break;
                } else {
                    outputStream.write(input.getBytes()); // send message to server
                    input = null;
                }

                int data = -1;
                line = new StringBuffer();
                while((data = inputStream.read()) != -1) {
                    if (!(((char)data) == '\n')) {
                        line.append((char) data); // append data to string buffer except '\n'
                    }
                    if (((char) data) == '\n') { // if end of line
                        System.out.println(line.toString());
                        break;
                    }

                }
            }
            System.out.println("Closing connection...");
            reader.close();
            outputStream.flush();
            outputStream.close();
            inputStream.close();
            socket.close();
        } catch (IOException e) {
            System.err.println("Server Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}