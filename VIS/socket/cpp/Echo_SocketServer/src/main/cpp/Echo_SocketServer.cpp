/**
 * @Authors: Benjamin Golić (MC) & Amel Šahbaz (AC)
 */

#include "Echo_SocketServer.h"
#include <iostream>
#include <sys/socket.h>
#include <netinet/in.h>
#include <unistd.h>
#include <string.h>
#include <stdlib.h>
#include <arpa/inet.h>

#define BUFFER_SIZE 1024

// Constructor of the Echo_SocketServer class
Echo_SocketServer::Echo_SocketServer(int mPort): mPort(mPort) {
    // Constructor initialization if needed mClientAddr
    mAddrlen = sizeof(mClientAddr);
}

Echo_SocketServer::~Echo_SocketServer() {
    // Destructor cleanup if needed
}


// Initialize the server socket
void Echo_SocketServer::InitializeSocket() {


    // Create a socket using TCP/IP
    if ((mServerSocket = socket(AF_INET, SOCK_STREAM, 0)) == 0) {
        perror("socket failed");
        exit(EXIT_FAILURE);
    }

    // Set the properties of the server address structure
    mClientAddr.sin_family = AF_INET;
    mClientAddr.sin_port = htons(mPort);
    mClientAddr.sin_addr.s_addr = INADDR_ANY;
    memset(&(mClientAddr.sin_zero), '\0', 8);

    // Bind the socket to the server address
    if (bind(mServerSocket, (struct sockaddr *) &mClientAddr, sizeof(mClientAddr)) < 0) {
        perror("bind failed");
        exit(EXIT_FAILURE);
    }


}
/**
 * @brief Listens for client connections.
 *
 * Sets up the server to listen on the socket and waits for client connections.
 */
void Echo_SocketServer::listenHandler() {
    // Socket Listening
    // Socket Accepting
    if (listen(mServerSocket, 3) < 0) {
        perror("listen");
        exit(EXIT_FAILURE);
    }

    std::cout << "Echo Socket Server waiting for clients on Port " << mPort << std::endl;

}

/**
 * @brief Accepts incoming client connections.
 *
 * Accepts a connection from a client and prints the connection details.
 */
void Echo_SocketServer::acceptHandler() {
    if ((mComm_Socket = accept(mServerSocket, (struct sockaddr *) &mClientAddr, (socklen_t * ) & mAddrlen)) < 0) {
        perror("accept");
        exit(EXIT_FAILURE);
    }

    char clientIP[INET_ADDRSTRLEN];
    inet_ntop(AF_INET, &(mClientAddr.sin_addr), clientIP, INET_ADDRSTRLEN);

    std::cout << "Connection established with client on ... \n"
              << "Socket[client ("
              << clientIP
              << ", "
              << ntohs(mClientAddr.sin_port)
              << "); server ("
              << inet_ntoa(mClientAddr.sin_addr)
              << ", "
              << mPort << ")]"
              << std::endl;

}

/**
 * @brief Handles communication with connected clients.
 *
 * Manages the receiving and sending of messages to and from clients,
 * and processes special commands like 'shutdown' and 'drop'.
 */
void Echo_SocketServer::commHandler() {
    // Socket Communication
    // Socket Receiving
    // Socket Sending
    while (true) {
        char clientMsg[BUFFER_SIZE] = {0};
        int msg_len = recv(mComm_Socket, clientMsg, BUFFER_SIZE, 0);

        if (msg_len < 0) {
            perror("recv failed");
            break;
        } else if (msg_len == 0) {
            std::cout << "Client disconnected." << std::endl;
            std::cout << "Echo Socket Server waiting for clients on Port " << mPort << std::endl;

            acceptHandler();
            msg_len = recv(mComm_Socket, clientMsg, BUFFER_SIZE, 0);
        }

        clientMsg[msg_len] = '\0';
        std::cout << "Message from client: " << clientMsg << std::endl;

        std::string echoMsg = "ECHO: " + std::string(clientMsg);
        send(mComm_Socket, echoMsg.c_str(), echoMsg.length(), 0);

        if (strcmp(clientMsg, "shutdown") == 0) {
            std::cout << "Shutdown command received. Closing server." << std::endl;
            close(mComm_Socket);
            close(mServerSocket);
            break;
        }

        if (strcmp(clientMsg, "drop") == 0) {
            std::cout << "Drop command received. Closing client." << std::endl;
            close(mComm_Socket);
            std::cout << "Echo Socket Server waiting for clients on Port " << mPort << std::endl;
            acceptHandler();

            continue;
        }

        memset(clientMsg, 0, BUFFER_SIZE);
    }
}
