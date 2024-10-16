/**
 * @Authors: Benjamin Golić (MC) & Amel Šahbaz (AC)
 */

#include "../headers/Ipv6_SocketClient.h"
#include <iostream>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <string.h>
#include <stdlib.h>

#define BUFFER_SIZE 1024


/**
 * @brief Constructor for IPv6_SocketClient.
 * @param mPort The port number to connect to.
 * @param mIP The IP address of the server in IPv6 format.
 *
 * Initializes a new instance of the IPv6_SocketClient and sets up the socket.
 */
Ipv6_SocketClient::Ipv6_SocketClient(int mPort, const char *mIP) : mPort(mPort), mIP(mIP) {
    // Initialize the socket upon object creation
    InitializeSocket();
}

/**
 * @brief Destructor for IPv6_SocketClient.
 *
 * Closes the socket when the object is destroyed.
 */
Ipv6_SocketClient::~Ipv6_SocketClient() {
    // Close the socket when the object is destroyed
    close(mIpv6ClientSocket);
}

/**
 * @brief Initializes the IPv6 client socket.
 *
 * Sets up the socket for IPv6 communication, specifying the socket family, type, and protocol.
 * It also configures the server address using the provided IP and port.
 */
void Ipv6_SocketClient::InitializeSocket() {
    // Create an IPv6 socket
    mIpv6ClientSocket = socket(AF_INET6, SOCK_STREAM, IPPROTO_TCP);

    if (mIpv6ClientSocket < 0) {
        perror("Socket creation failed");
        exit(EXIT_FAILURE);
    }

    // Configure the server address for IPv6
    memset(&mServerAddr, 0, sizeof(mServerAddr));
    mServerAddr.sin6_family = AF_INET6;
    mServerAddr.sin6_port = htons(mPort);
    if (inet_pton(AF_INET6, mIP, &mServerAddr.sin6_addr) != 1) {
        perror("Invalid address");
        exit(EXIT_FAILURE);
    }
}

/**
 * @brief Connects to the IPv6 server.
 *
 * Establishes a connection with the server at the specified IP and port.
 * On success, it displays the server address and port number.
 */
void Ipv6_SocketClient::connectHandler() {
    if (connect(mIpv6ClientSocket, (sockaddr *)&mServerAddr, sizeof(mServerAddr)) < 0) {
        perror("Connect failed");
        exit(EXIT_FAILURE);
    }

    char mIPv6AddressString[INET6_ADDRSTRLEN];
    inet_ntop(AF_INET6, &mServerAddr.sin6_addr, mIPv6AddressString, INET6_ADDRSTRLEN);
    std::cout << "Connected to server at " << mIPv6AddressString << " port " << mPort << std::endl;

}

/**
 * @brief Handles the communication with the server.
 *
 * Enables the user to send messages to the

*server and receive responses. Special commands like 'quit', 'drop', and 'shutdown' have specific behaviors.

*The method continuously listens for user input and sends it to the server, displaying the server's responses.
    */
void Ipv6_SocketClient::commHandler() {
    char buffer[BUFFER_SIZE] = {0};
    while (true) {

        std::string input;
        std::cout << "Enter message: ";
        std::getline(std::cin, input);


        if (input == "quit") {
            std::cout << "Quitting the client" << std::endl;
            break;
        } else if (input == "drop") {
            send(mIpv6ClientSocket, input.c_str(), input.size(), 0);
            break;
        } else if (input == "shutdown") {
            send(mIpv6ClientSocket, input.c_str(), input.size(), 0);
            break;
        }

        send(mIpv6ClientSocket, input.c_str(), input.size(), 0);
        std::cout << "Message sent to server" << std::endl;
        // Receive a response from the server
        int valread = recv(mIpv6ClientSocket, buffer, BUFFER_SIZE-1,0);
        buffer[valread] = '\0';

        std::string serverResponse(buffer);
        // Check if the server response is valid
        if (serverResponse.rfind("ECHO: ", 0) == 0) {
            std::cout << "Server response: " << buffer << std::endl;
            memset(buffer, 0, BUFFER_SIZE);
        } else {
            std::cerr << "Server response invalid: " << buffer << std::endl;
            break;
        }

    }
}
