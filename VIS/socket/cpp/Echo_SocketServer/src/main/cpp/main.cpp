/**
 * @Authors: Benjamin Golić (MC) & Amel Šahbaz (AC)
 */

#include <iostream>
#include "../headers/Echo_SocketServer.h"

int mPort;

/**
 * @brief Main function of the Echo Socket Server.
 *
 * Initializes and runs the Echo Socket Server. It accepts a port number as a command-line argument.
 * If no port number is provided, the user is prompted to enter one. The server is set up to listen on the
 * specified port and handle incoming client connections.
 *
 * @param _argc Number of command-line arguments.
 * @param _argv Array of command-line arguments.
 * @return int Returns 0 upon successful execution, or EXIT_FAILURE on error.
 */
int main(int _argc, char* _argv[]) {
    // Check if the port number is provided as a command-line argument
    if (_argc == 2) {
        try {
            // Attempt to convert the provided argument into an integer for the port
            mPort = std::stoi(_argv[1]);
        } catch (std::exception& e) {
            // Handle the exception if the port number is not valid
            std::cerr << "Invalid port provided. Exiting." << std::endl;
            return EXIT_FAILURE;
        }
    } else {
        // If no port number is provided, request it from the user
        std::cout << "No port provided. Please enter port: " << std::endl;
        std::cin >> mPort;
        std::cout << "Port set: " << mPort << std::endl;
    }

    // Create an instance of Echo_SocketServer with the specified port
    Echo_SocketServer *echoSocketServer = new Echo_SocketServer(mPort);
    // Initialize the socket for the server
    echoSocketServer->InitializeSocket();
    echoSocketServer->listenHandler();
    echoSocketServer->acceptHandler();
    echoSocketServer->commHandler();

    return 0;
}
