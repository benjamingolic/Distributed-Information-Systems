/**
 * @Authors: Benjamin Golić (MC) & Amel Šahbaz (AC)
 */

#include "../headers/Ipv6_SocketClient.h"
#include <iostream>

int mPort;
std::string mIP;

/**
 * @brief Main function of the IPv6 socket client program.
 * @param _argc Number of command-line arguments.
 * @param _argv Array of command-line arguments.
 * @return Integer indicating the exit status.
 *
 * The main function checks for the necessary command-line arguments (port and IP).
 * If not provided, it prompts the user to input them. It then initializes, connects,
 * and handles communication through an Ipv6_SocketClient instance.
 */
int main(int _argc, char* _argv[]) {
    // Check if port and IP address are provided as command-line arguments
    if (_argc == 3) {
        try {
            // Convert the first argument to an integer for the port
            mPort = std::stoi(_argv[1]);
            // Assign the second argument as the IP address
            mIP = _argv[2];
        } catch (std::exception& e) {
            // Handle the exception if the port number is not valid
            std::cerr << "Invalid port provided. Exiting." << std::endl;
            return EXIT_FAILURE;
        }
    } else {
        // Request port number from the user if not provided
        std::cout << "No port provided. Please enter port: " << std::endl;
        std::cin >> mPort;
        std::cout << "Port set: " << mPort << std::endl;

        // Request IP address from the user if not provided
        std::cout << "No IP provided. Please enter IP: " << std::endl;
        std::cin >> mIP;
        std::cout << "IP set: " << mIP << std::endl;
    }

    // Create an instance of Ipv6_SocketClient
    Ipv6_SocketClient *ipv6SocketClient = new Ipv6_SocketClient(mPort, (mIP.c_str()));
    // Initialize the IPv6 socket
    ipv6SocketClient->InitializeSocket();
    // Establish a connection with the server
    ipv6SocketClient->connectHandler();
    // Handle the communication with the server
    ipv6SocketClient->commHandler();

    return 0;
}
