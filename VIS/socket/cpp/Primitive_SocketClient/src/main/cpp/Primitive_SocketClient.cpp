/**
 * @Author: Benjamin Golić (MC) & Amel Šahbaz(AC)
 */

#include <iostream>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <string.h>
#include <stdlib.h>

#define BUFFER_SIZE 1024
// Global variables for port, server and client socket, and IP address
int mPort, mServerSocket, mClientSocket;
std::string mIP;


/**
 * @brief Main function of the socket client program.
 * @param _argc Number of command-line arguments.
 * @param _argv Array of command-line arguments.
 * @return Integer indicating the exit status.
 *
 * The main function processes command-line arguments for the server's IP address and port.
 * If these are not provided, it prompts the user for them. Then, it establishes a socket connection
 * to the server, handles user input, and processes server responses.
 */
int main(int _argc, char* _argv[]) {
    bool isConnected = false;
    // Process command-line arguments for port and IP address
    if (_argc == 3) {
        try {
            mPort = std::stoi(_argv[1]);// Convert port from string to integer
            mIP = _argv[2];// Assign IP address
        } catch (std::exception& e) {
            std::cerr << "Invalid port provided. Exiting." << std::endl;
            return EXIT_FAILURE;
        }
    } else {
        // Request port and IP from user if not provided as arguments
        std::cout << "No port provided. Please enter port: " << std::endl;
        std::cin >> mPort;
        std::cout << "Port set: " << mPort << std::endl;

        std::cout << "No IP provided. Please enter IP: " << std::endl;
        std::cin >> mIP;
        std::cout << "IP set: " << mIP << std::endl;
    }

    // create socket
    if ((mClientSocket = socket(AF_INET, SOCK_STREAM, 0)) < 0) {
        std::cerr << "Socket creation error" << std::endl;
        return -1;
    }

    bool mBreak = true;

    sockaddr_in serverAddr;
    serverAddr.sin_family = AF_INET;
    serverAddr.sin_port = htons(mPort);
    serverAddr.sin_addr.s_addr = INADDR_ANY;
    memset(&(serverAddr.sin_zero), '\0', 8);

    char buffer[BUFFER_SIZE] = {0};

    // Convert IP address from text to binary form
    if(inet_pton(AF_INET, (mIP.c_str()), &serverAddr.sin_addr) <= 0) {
        std::cerr << "Invalid address/ Address not supported" << std::endl;
        return -1;
    }

    // Connect to the server
    if (connect(mClientSocket, (struct sockaddr *)&serverAddr, sizeof(serverAddr)) < 0) {
        std::cerr << "Connection Failed" << std::endl;
        return -1;
    }

    std::cout << "Connected to the server on port " << mPort << std::endl;

    // Main loop for communication with the server
    while (true) {

        std::string input;
        std::cout << "Enter message: ";
        std::getline(std::cin, input);

        // Check for specific commands to quit, drop, or shutdown
        if (input == "quit") {
            std::cout << "Quitting the client" << std::endl;
            //close(mClientSocket);
            break;
        } else if (input == "drop") {
            send(mClientSocket, input.c_str(), input.size(), 0);
            break;
        } else if (input == "shutdown") {
            send(mClientSocket, input.c_str(), input.size(), 0);
            break;
        }

        // Send message to server
        send(mClientSocket, input.c_str(), input.size(), 0);
        std::cout << "Message sent to server" << std::endl;
        // Read server response
        int valread = read(mClientSocket, buffer, BUFFER_SIZE-1);
        buffer[valread] = '\0';

        std::string serverResponse(buffer);
        // Check for valid server response
        if (serverResponse == "ACK" || serverResponse.rfind("ECHO: ", 0) == 0) {
            std::cout << "Server response: " << buffer << std::endl;
            memset(buffer, 0, BUFFER_SIZE);
        } else {
            std::cerr << "Server response invalid: " << buffer << std::endl;
            break;
        }
    }
    std::cout << "Closing the socket" << std::endl;
    close(mClientSocket);

    return 0;
}