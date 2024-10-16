/**
 * @Authors: Benjamin Golić (MC) & Amel Šahbaz (AC)
 */

#include "../headers/Environment_SocketServer.h"
#include <iostream>

int mPort;
/**
 * @file main.cpp
 * @brief Entry point for the Environment Socket Server application.
 *
 * This program initializes and runs an Environment Socket Server. It optionally accepts a port number
 * as a command-line argument or prompts the user to provide one. The server then listens for, accepts,
 * and handles client connections.
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

    // Create an instance of Environment_SocketServer with the specified port
    Environment_SocketServer server(mPort);
    // Initialize the socket for the server
    server.InitializeSocket();
    // Start listening for incoming connections
    server.listenHandler();
    // Handle accepting incoming connections
    server.acceptHandler();

    return 0;
}
