/**
 * @Authors: Benjamin Golić (MC) & Amel Šahbaz (AC)
 */

#include <iostream>
#include "../headers/Http_SocketServer.h"

int mPort;

/**
 * @brief Main function to start the HTTP Socket Server.
 *
 * @param _argc Number of command-line arguments.
 * @param _argv Array of command-line arguments.
 * @return int Returns EXIT_FAILURE on invalid port, otherwise 0.
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

    /**
    * @brief Creates an instance of Http_SocketServer.
    */
    Http_SocketServer *echoSocketServer = new Http_SocketServer(mPort);
    /**
    * @brief Initialize and start handling socket communications.
    */
    echoSocketServer->InitializeSocket();
    echoSocketServer->listenHandler();
    echoSocketServer->acceptHandler();
    echoSocketServer->commHandler();

    return 0;
}
