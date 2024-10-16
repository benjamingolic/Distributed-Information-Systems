/**
 * @Authors: Benjamin Golić (MC) & Amel Šahbaz (AC)
 */

#include <iostream>
#include "../headers/Udp_SocketClient.h"

int mPort;
std::string mIP;


#define BUFFER_SIZE 1024
/**
 * @brief Main function of the UDP socket client program.
 * @param _argc Number of command-line arguments.
 * @param _argv Array of command-line arguments.
 * @return Integer indicating the exit status.
 *
 * The main function processes command-line arguments for the server's IP address and port.
 * If these are not provided, it prompts the user for them. Then, it initializes an instance of Udp_SocketClient
 * and handles communication with the server.
 */
int main(int _argc, char* _argv[]) {
    // Check if the port and IP address are provided as command-line arguments
    if (_argc == 3) {
        try {
            // Check if the port and IP address are provided as command-line arguments
            mPort = std::stoi(_argv[1]);
            // The second argument is the IP address
            mIP = _argv[2];
        } catch (std::exception& e) {
            // Catch and handle any exception if the port number is not valid
            std::cerr << "Invalid port provided. Exiting." << std::endl;
            return EXIT_FAILURE;
        }
    } else {
        // If no port or IP address is provided, request them from the user
        std::cout << "No port provided. Please enter port: " << std::endl;
        std::cin >> mPort;
        std::cout << "Port set: " << mPort << std::endl;

        std::cout << "No IP provided. Please enter IP: " << std::endl;
        std::cin >> mIP;
        std::cout << "IP set: " << mIP << std::endl;
    }

    // Create a new instance of the Udp_SocketClient class
    Udp_SocketClient *udpSocketClient = new Udp_SocketClient(mPort, (mIP.c_str()));
    // Initialize the UDP client socket
    udpSocketClient->InitializeSocket();
    // Handle the communication with the server
    udpSocketClient->commHandler();

    return 0;
}
