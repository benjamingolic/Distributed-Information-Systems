/**
 * @Authors: Benjamin Golić (MC) & Amel Šahbaz (AC)
 */

#include <iostream>
#include <sys/socket.h>
#include <netinet/in.h>
#include <unistd.h>
#include <string.h>
#include <stdlib.h>
#include <arpa/inet.h>
#include "../headers/Ipv6_SocketServer.h"

#define BUFFER_SIZE 1024

/**
 * @brief Constructor for Ipv6_SocketServer.
 * @param mPort The port number on which the server will listen for connections.
 *
 * Initializes a new instance of the IPv6 socket server and sets up the server address structure.
 */
Ipv6_SocketServer::Ipv6_SocketServer(int mPort) : mPort(mPort) {
    mAddrlen = sizeof(mServerAddr);
}

/**
 * @brief Destructor for Ipv6_SocketServer.
 *
 * Handles any necessary cleanup upon destruction of the server object. This could involve
 * closing sockets if they are still open.
 */
Ipv6_SocketServer::~Ipv6_SocketServer() {
    // Destructor cleanup if needed
}

/**
 * @brief Initializes the IPv6 server socket.
 *
 * Creates an IPv6 socket and binds it to the server's IP address and port number.
 * If the socket fails to create or bind, the program will exit with an error message.
 */
void Ipv6_SocketServer::InitializeSocket() {
    // Creating an IPv6 socket
    if ((mServerSocket = socket(AF_INET6, SOCK_STREAM, IPPROTO_TCP)) == 0) {
        perror("socket failed");
        exit(EXIT_FAILURE);
    }

    // Setting server address for IPv6
    mServerAddr.sin6_family = AF_INET6;
    mServerAddr.sin6_port = htons(mPort);
    mServerAddr.sin6_addr = in6addr_any;

    // Binding the socket to the server address
    if (bind(mServerSocket, reinterpret_cast<const sockaddr *>(&mServerAddr), sizeof(mServerAddr)) < 0) {
        perror("bind failed");
        exit(EXIT_FAILURE);
    }
}

/**
 * @brief Starts listening for client connections on the initialized socket.
 *
 * Puts the server into a listening state where it waits for client connection requests.
 * If listening fails, the program will exit with an error message.
 */
void Ipv6_SocketServer::listenHandler() {
    if (listen(mServerSocket, 3) < 0) {
        perror("listen");
        exit(EXIT_FAILURE);
    }

    std::cout << "IPV6-Socket-Server waiting for clients on port " << mPort << std::endl;

}

/**
 * @brief Accepts incoming client connections.
 *
 * Waits for and accepts a connection from a client. Upon a successful connection,
 * the server prints the details of the connected client.
 */
void Ipv6_SocketServer::acceptHandler() {
    socklen_t mClientAddrLen = sizeof(mClientAddr);
    if ((mComm_Socket = accept(mServerSocket, (struct sockaddr *) &mClientAddr,
                               &mClientAddrLen)) < 0) {
        perror("accept");
        exit(EXIT_FAILURE);
    }
    char ipv6Address[INET6_ADDRSTRLEN];

    char clientIP[INET_ADDRSTRLEN];
    inet_ntop(AF_INET, &(mClientAddr.sin6_addr), clientIP, INET_ADDRSTRLEN);

    std::cout << "Connection established with client on ... \n"
              << "Socket[client ("
              << clientIP
              << ", "
              << ntohs(mClientAddr.sin6_port)
              << "); server ("
              << inet_ntop(AF_INET6, &mClientAddr.sin6_addr, ipv6Address, INET6_ADDRSTRLEN)
              << ", "
              << mPort << ")]"
              << std::endl;
}

/**
 * @brief Handles communication with a connected client.
 *
 * Receives messages from the client and sends back an echo of the message.
 * Special commands like 'shutdown', 'drop', and 'quit' trigger corresponding actions.
 * The loop continues until the server or client decides to close the connection.
 */
void Ipv6_SocketServer::commHandler() {
    while (true) {
        char clientMsg[BUFFER_SIZE] = {0};
        int msg_len = recv(mComm_Socket, clientMsg, BUFFER_SIZE, 0);

        if (msg_len < 0) {
            perror("recv Server failed");
            break;
        } else if (msg_len <= 0) {
            std::cout << "Client disconnected." << std::endl;
            std::cout << "IPV6-Socket-Server waiting for clients on port " << mPort << std::endl;

            acceptHandler();
            msg_len = recv(mComm_Socket, clientMsg, BUFFER_SIZE, 0);
        }

        clientMsg[msg_len] = '\0';
        std::cout << "Message from client: " << clientMsg << std::endl;

        std::string echoMsg = "ECHO: " + std::string(clientMsg);
        send(mComm_Socket, echoMsg.c_str(), echoMsg.length(), 0);

        if (strcmp(clientMsg, "shutdown") == 0) {
            std::cout << "Shutdown command received. Closing server." << std::endl;
            if (close(mComm_Socket) < 0) {
                perror("close failed");
                exit(EXIT_FAILURE);
            }
            if (close(mServerSocket) < 0) {
                perror("close failed");
                exit(EXIT_FAILURE);
            }
            break;
        }

        if (strcmp(clientMsg, "drop") == 0) {
            std::cout << "Drop command received. Closing client." << std::endl;
            close(mComm_Socket);
            std::cout << "IPV6-Socket-Server waiting for clients on port " << mPort << std::endl;
            acceptHandler();

            continue;
        }

        if (strcmp(clientMsg, "quit") == 0) {
            std::cout << "Quitting the server" << std::endl;
            close(mComm_Socket);
            break;
        }

        memset(clientMsg, 0, BUFFER_SIZE);
    }
}




