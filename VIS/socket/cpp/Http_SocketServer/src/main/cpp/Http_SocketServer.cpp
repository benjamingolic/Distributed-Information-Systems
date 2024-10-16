/**
 * @Authors: Benjamin Golić (MC) & Amel Šahbaz (AC)
 */

#include "Http_SocketServer.h"
#include <iostream>
#include <sys/socket.h>
#include <netinet/in.h>
#include <unistd.h>
#include <string.h>
#include <stdlib.h>
#include <arpa/inet.h>
#include <sstream>
#include <vector>

#define BUFFER_SIZE 1024


/**
* @brief Constructor for Http_SocketServer.
* @param mPort The port number on which the server will listen for connections.
*/
Http_SocketServer::Http_SocketServer(int mPort): mPort(mPort) {
    // Constructor initialization if needed mClientAddr
    mAddrlen = sizeof(mClientAddr);
}

/**
@brief Destructor for Http_SocketServer.
*/
Http_SocketServer::~Http_SocketServer() {
    // Destructor cleanup if needed
}

/**
 * @brief Initializes the server socket and sets up the server address structure.
 */
void Http_SocketServer::InitializeSocket() {


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
 * @brief Handles listening for incoming client connections on the server socket.
 */
void Http_SocketServer::listenHandler() {
    // Socket Listening
    // Socket Accepting
    if (listen(mServerSocket, 3) < 0) {
        perror("listen");
        exit(EXIT_FAILURE);
    }

    std::cout << "Echo Socket Server waiting for clients on Port " << mPort << std::endl;

}

/**
 * @brief Accepts incoming client connections and establishes a communication socket.
 */
void Http_SocketServer::acceptHandler() {
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
 * @brief Handles communication with the connected client.
 *
 * This function handles receiving messages from the client, processing them,
 * and sending back responses. It also handles commands like 'shutdown' and 'drop'.
 */
void Http_SocketServer::commHandler() {
    // Socket Communication
    // Socket Receiving
    // Socket Sending
    while (true) {
        char clientMsg[BUFFER_SIZE] = {0};
        int msg_len = recv(mComm_Socket, clientMsg, BUFFER_SIZE, 0);

        std::string httpResponse = "<!DOCTYPE html>\n"
                                   "<html lang=\"en\">\n"
                                   "  <head>\n"
                                   "    <meta charset=\"UTF-8\">\n"
                                   "    <title>HTTP Socket-Server</title>\n"
                                   "  </head>\n"
                                   "  <body>\n"
                                   "    <main>\n"
                                   "        <h1>Browser Request</h1><p>\n";

        std::istringstream iss(clientMsg);
        std::string line;
        std::vector<std::string> lines;

        while (std::getline(iss, line)) {
            lines.push_back(line);
        }
        for(int i = 0; i < lines.size(); i++) {
            httpResponse.append(lines[i] + "<br>");
        }

        httpResponse.append("</body>\n</html>");

        if (send(mComm_Socket, httpResponse.c_str(), strlen(httpResponse.c_str()), 0) == -1) {
            std::cout << "send has an error!" << std::endl;
        }


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
        //send(mComm_Socket, echoMsg.c_str(), echoMsg.length(), 0);

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
