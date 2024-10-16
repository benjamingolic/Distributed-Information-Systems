/**
 * @Authors: Benjamin Golić (MC) & Amel Šahbaz (AC)
 */

#include <iostream>
#include <arpa/inet.h>
#include "../headers/Udp_SocketClient.h"

#define BUFFER_SIZE 1024

// Constructor for Udp_SocketClient
Udp_SocketClient::Udp_SocketClient(int mPort, const char *mIP) : mPort(mPort), mIP(mIP) {
    std::cout << "Udp_SocketClient created" << std::endl;
    mAddrlen = sizeof(serverAddr);
}

// Destructor for Udp_SocketClient
Udp_SocketClient::~Udp_SocketClient() {
    // Destructor cleanup if needed
}

// Initialize the UDP client socket
void Udp_SocketClient::InitializeSocket() {
    // Create a UDP socket
    if ((udpClientSocket = socket(AF_INET, SOCK_DGRAM, IPPROTO_UDP)) < 0) {
        perror("Socket creation error");
        exit(EXIT_FAILURE);
    }

    // Configure server address
    serverAddr.sin_family = AF_INET;
    serverAddr.sin_port = htons(mPort);
    serverAddr.sin_addr.s_addr = inet_addr(mIP);

    memset(&(serverAddr.sin_zero), '\0', 8);
}

// Handle the overall communication process of the client
void Udp_SocketClient::commHandler() {
    char msg[BUFFER_SIZE];

    socklen_t clientAddrSize = sizeof(clientAddr);

    while (true) {
        sendHandler();
        if (receiveHandler()) {
            break;
        }
    }
}

// Handle sending messages to the server
void Udp_SocketClient::sendHandler() {
    char *input = new char[BUFFER_SIZE];
    std::cout << "Enter message to send: ";
    std::cin.getline(input, BUFFER_SIZE);

    // Special commands to quit, drop, or shutdown
    if (strcmp(input, "quit") == 0 || strcmp(input, "shutdown") == 0 || strcmp(input, "drop") == 0) {
        sendto(udpClientSocket, input, BUFFER_SIZE, 0, (struct sockaddr *) &serverAddr, sizeof(serverAddr));
        if (strcmp(input, "quit") == 0) {
            std::cout << "Quitting the client" << std::endl;
        }
        else if (strcmp(input, "drop") == 0 ) {
            std::cout << "Dropping the client" << std::endl;
        }
        else if (strcmp(input, "shutdown") == 0) {
            std::cout << "Shutting down" << std::endl;
        }
        exit(EXIT_SUCCESS);
    }

    // Send a message to the server
    if (sendto(udpClientSocket, input, BUFFER_SIZE, 0, (struct sockaddr *) &serverAddr, sizeof(serverAddr)) < 0) {
        perror("sendto failed");
        exit(EXIT_FAILURE);
    }
}

// Handle receiving responses from the server
bool Udp_SocketClient::receiveHandler() {
    char msg[BUFFER_SIZE];
    socklen_t clientAddrSize = sizeof(clientAddr);

    // Receive a message from the server
    int rcvVal = recvfrom(udpClientSocket, msg, BUFFER_SIZE, 0, (struct sockaddr *) &clientAddr, &clientAddrSize);
    if (rcvVal < 0) {
        perror("recvfrom failed");
        exit(EXIT_FAILURE);
    }

    msg[rcvVal] = '\0';
    std::cout << "Received message: " << msg << std::endl;

    // Check if the received message is a shutdown command
    if (strcmp(msg, "shutdown") == 0) {
        std::cout << "Shutdown command received. Closing server." << std::endl;
        return true;
    }

    return false;
}

