/**
 * @Authors: Benjamin Golić (MC) & Amel Šahbaz (AC)
 */

#include "../headers/Udp_SocketServer.h"
#include <iostream>

int mPort;


/**
 * @brief Main function of the UDP socket server program.
 * @param _argc Number of command-line arguments.
 * @param _argv Array of command-line arguments.
 * @return Integer indicating the exit status.
 *
 * The main function processes command-line arguments for the server's port. If the port is not provided,
 * it prompts the user for it. Then, it initializes an instance of Udp_SocketServer and handles incoming
 * messages from clients.
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

    // Create a new instance of the Udp_SocketServer class
    Udp_SocketServer *udpSocketServer = new Udp_SocketServer(mPort);
    // Initialize the UDP socket with the specified port
    udpSocketServer->InitializeSocket();
    // Start handling the communication; this will keep running until the server is instructed to shutdown
    udpSocketServer->commHandler();

    return 0;
}
