/**
 * @Author: Benjamin Golić (MC) & Amel Šahbaz(AC)
 */

#include <iostream>
#include <sys/socket.h>
#include <netinet/in.h>
#include <unistd.h>
#include <string.h>
#include <stdlib.h>
#include <arpa/inet.h>

#define BUFFER_SIZE 1024

// Declare global variables
int mPort, mServerSocket, mClientSocket, mBytesReceived;


/**
 * @brief Main function of the primitive socket server program.
 * @param _argc Number of command-line arguments.
 * @param _argv Array of command-line arguments.
 * @return Integer indicating the exit status.
 *
 * The main function sets up a server socket and listens for client connections on a specified port.
 * It accepts connections and handles incoming messages, responding to specific commands.
 * The server remains active until it receives a shutdown command.
 */
int main(int _argc, char* _argv[]) {
    bool isConnected = true;
    // Check if port is provided
    if (_argc == 2) {
        try {
            // Convert provided port to integer
            mPort = std::stoi(_argv[1]);
        } catch (std::exception& e) {
            std::cerr << "Invalid port provided. Exiting." << std::endl;
            return EXIT_FAILURE;
        }
    } else {
        // If no port provided, request user input for port
        std::cout << "No port provided. Please enter port: " << std::endl;
        std::cin >> mPort;
        std::cout << "Port set: " << mPort << std::endl;
    }
    // Create a server socket
    if ((mServerSocket = socket(AF_INET, SOCK_STREAM, 0)) == 0) {
        perror("socket failed");
        exit(EXIT_FAILURE);
    }
    // Configure server address
    sockaddr_in clientAddr;

    clientAddr.sin_family = AF_INET;
    clientAddr.sin_port = htons(mPort);
    clientAddr.sin_addr.s_addr = INADDR_ANY;


    memset(&(clientAddr.sin_zero), '\0', 8);

    // Bind server socket to server address
    if (bind(mServerSocket, (struct sockaddr *) &clientAddr, sizeof(clientAddr)) < 0) {
        perror("bind failed");
        exit(EXIT_FAILURE);
    }

    // Listen for client connections
    if (listen(mServerSocket, 3) < 0) {
        perror("listen");
        exit(EXIT_FAILURE);
    }

    int addrlen = sizeof(clientAddr);

    // Notify that the server is waiting for clients
    std::cout << "Primitive Socket Server waiting for clients on port " << mPort << std::endl;

    // Accept a client connection
    if ((mClientSocket = accept(mServerSocket, (struct sockaddr *) &clientAddr, (socklen_t * ) & addrlen)) < 0) {
        isConnected = false;
        perror("accept");
        exit(EXIT_FAILURE);
    }

    char clientIP[INET_ADDRSTRLEN];
    inet_ntop(AF_INET, &(clientAddr.sin_addr), clientIP, INET_ADDRSTRLEN);

    std::cout << "Connection established with client on ... \n"
              << "Socket[client ("
              << clientIP
              << ", "
              << ntohs(clientAddr.sin_port)
              << "); server ("
              << inet_ntoa(clientAddr.sin_addr)
              << ", "
              << mPort << ")]"
              << std::endl;


    char* clientMsg = new char[BUFFER_SIZE];
    const char *ack = "ACK\0";

    // Main loop to handle incoming messages
    while (true) {
        if (!isConnected) {
            // Wait for a new client connection if not connected
            std::cout << "Primitive Socket Server waiting for clients on port " << mPort << std::endl;

            if ((mClientSocket = accept(mServerSocket, (struct sockaddr *) &clientAddr, (socklen_t *) &addrlen)) < 0) {
                perror("accept");
                exit(EXIT_FAILURE);
            }

            char clientIP[INET_ADDRSTRLEN];
            inet_ntop(AF_INET, &(clientAddr.sin_addr), clientIP, INET_ADDRSTRLEN);

            std::cout << "Connection established with client on ... \n"
                      << "Socket[client ("
                      << clientIP
                      << ", "
                      << ntohs(clientAddr.sin_port)
                      << "); server ("
                      << inet_ntoa(clientAddr.sin_addr)
                      << ", "
                      << mPort << ")]"
                      << std::endl;

            isConnected = true;
        }
        // Handle incoming messages from client
        while (true) {
            mBytesReceived = recv(mClientSocket, clientMsg, BUFFER_SIZE, 0);

            // Check for client disconnection or receive error
            if (mBytesReceived == 0) {
                std::cout << "Client disconnected. Closing socket and waiting for new connection." << std::endl;
                close(mClientSocket);
                isConnected = false;
                break; // Exit the inner loop to allow the server to accept a new connection
            } else if (mBytesReceived < 0) {
                perror("recv failed");
                close(mClientSocket);
                isConnected = false;
                break; // Exit the inner loop and potentially handle the error
            } else {
                // Process the received message
                std::cout << "Message from client: " << clientMsg << std::endl;
            }
            // Check for specific commands from client
            if (strcmp(clientMsg, "drop") == 0) {
                std::cout << "Drop command received. Closing client." << std::endl;
                isConnected = false;
                close(mClientSocket);
                break;
            }

            if (strcmp(clientMsg, "shutdown") == 0) {
                std::cout << "Shutdown command received. Closing server." << std::endl;
                isConnected = false;
                close(mClientSocket);
                close(mServerSocket);
                return 0;
            }
            // Send acknowledgment to client
            if (send(mClientSocket, ack, strlen(ack), 0) < 0) {
                perror("send");
                exit(EXIT_FAILURE);
            }

            memset(clientMsg, 0, BUFFER_SIZE);
        }
    }
}