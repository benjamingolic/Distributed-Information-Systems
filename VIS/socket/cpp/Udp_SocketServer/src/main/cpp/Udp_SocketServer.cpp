/**
 * @Authors: Benjamin Golić (MC) & Amel Šahbaz (AC)
 */

#include "../headers/Udp_SocketServer.h"
#include <iostream>
#include <unistd.h>
#include <arpa/inet.h>

#define BUFFER_SIZE 1024
/**
 * @brief Constructor for Udp_SocketServer.
 * @param mPort The port number on which the server will listen for connections.
 *
 * Initializes a new instance of the Udp_SocketServer and sets up the server address for UDP communication.
 */
Udp_SocketServer::Udp_SocketServer(int mPort) : mPort(mPort) {
    mAddrlen = sizeof(mServerAddr);
}

/**
 * @brief Destructor for Udp_SocketServer.
 *
 * Handles any necessary cleanup upon destruction of the server object.
 */
Udp_SocketServer::~Udp_SocketServer() {
    // Destructor cleanup if needed
}

/**
 * @brief Initializes the UDP server socket.
 *
 * Creates a UDP server socket and binds it to the server's IP address and port number.
 * If the socket fails to create or bind, the program will exit with an error message.
 */
void Udp_SocketServer::InitializeSocket() {
    std::cout << "UDP Socket Server waiting for new messages on port " << mPort << "..." << std::endl;
    // Creating a UDP socket
    if ((mUdpServerSocket = socket(AF_INET, SOCK_DGRAM, IPPROTO_UDP)) == 0) {
        perror("socket failed");
        exit(EXIT_FAILURE);
    }
    // Setting server address
    mServerAddr.sin_family = AF_INET;
    mServerAddr.sin_port = htons(mPort);
    mServerAddr.sin_addr.s_addr = INADDR_ANY;
    memset(&(mServerAddr.sin_zero), '\0', 8);
    // Binding the socket to the server address
    if (bind(mUdpServerSocket, (struct sockaddr *) &mServerAddr, sizeof(mServerAddr)) < 0) {
        perror("bind failed");
        exit(EXIT_FAILURE);
    }

}

/**
 * @brief Handles the overall communication process with clients.
 *
 * Manages receiving messages from clients and sending responses in a loop until a 'shutdown' command is received.
 */
void Udp_SocketServer::commHandler() {
    while (true) {
        if(!receiveHandler()) {
            break;
        }
        sendHandler();
    }
    memset(msg, 0, BUFFER_SIZE);
}

/**
 * @brief Receives messages from clients.
 * @return Boolean indicating if a 'shutdown' command is received.
 *
 * Listens for and processes messages from clients. Prints received messages and handles special commands.
 * Returns false on receiving a 'shutdown' command, otherwise true.
 */
bool Udp_SocketServer::receiveHandler() {
    mClientAddrSize = sizeof(mClientAddr);
    // Receiving messages from the client
    int rcvVal = recvfrom(mUdpServerSocket, msg, BUFFER_SIZE, 0, (struct sockaddr *) &mClientAddr, &mClientAddrSize);

    if (rcvVal < 0) {
        perror("recvfrom failed");
        exit(EXIT_FAILURE);
    } else {
        msg[rcvVal] = '\0';
        // Printing the received message along with client's IP and port
        std::string clientIP = inet_ntoa(mClientAddr.sin_addr);
        int clientPort = ntohs(mClientAddr.sin_port);
        std::cout << "Received message from UDP-Client: [" << clientIP << ", " << clientPort << "] | " << msg << std::endl;
    }
    // Handling specific commands like 'quit', 'shutdown', and 'drop'
    if (strcmp(msg, "quit") == 0) {
        std::cout << "Waiting for new messages..." << std::endl;
        return true;
    }

    if (strcmp(msg, "shutdown") == 0) {
        std::cout << "Shutdown command received. Closing server." << std::endl;
        return false;
    }

    if (strcmp(msg, "drop") == 0) {
        std::cout << "Drop command received. Closing client." << std::endl;
        std::cout << "Waiting for new messages..." << std::endl;
        return true;
    }
    return true;
}

void Udp_SocketServer::sendHandler() {
    // Handler for sending messages
    mClientAddrSize = sizeof(mClientAddr);
    // Echoing the received message back to the client
    std::string echoMsg = "ECHO: " + std::string(msg);
    if (sendto(mUdpServerSocket, echoMsg.c_str(), echoMsg.length(), 0, (struct sockaddr *) &mClientAddr, mClientAddrSize) <
        0) {
        perror("sendto failed");
        exit(EXIT_FAILURE);
    }
}


