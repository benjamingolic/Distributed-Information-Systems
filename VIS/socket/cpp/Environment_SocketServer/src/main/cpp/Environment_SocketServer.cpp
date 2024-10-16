/**
 * @Authors: Benjamin Golić (MC) & Amel Šahbaz (AC)
 */

#include "../headers/Environment_SocketServer.h"

#include <iostream>
#include <sys/socket.h>
#include <netinet/in.h>
#include <unistd.h>
#include <string.h>
#include <stdlib.h>
#include <arpa/inet.h>

#define BUFFER_SIZE 1024


/**
     * @brief Constructor for Environment_SocketServer.
     * @param mPort The port number on which the server will listen for connections.
     */
Environment_SocketServer::Environment_SocketServer(int mPort) : mPort(mPort) {
    mAddrlen = sizeof(mClientAddr);
}

/**
     * @brief Destructor for Environment_SocketServer.
     */
Environment_SocketServer::~Environment_SocketServer() {
// Destructor cleanup if needed
}

/**
 * @class Environment_SocketServer
 * @brief A socket server class designed to handle environment sensor data.
 *
 * Environment_SocketServer sets up a server socket, listens for incoming client connections,
 * accepts these connections, and then processes sensor data requests. It handles special commands
 * and manages multiple client connections using threading.
 */
void Environment_SocketServer::InitializeSocket() {
    // Create a TCP socket for the server
    if ((mServerSocket = socket(AF_INET, SOCK_STREAM, 0)) == 0) {
        perror("socket failed");
        exit(EXIT_FAILURE);
    }

    // Configure the server address
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

// Handle listening for incoming client connections
void Environment_SocketServer::listenHandler() {
    if (listen(mServerSocket, 3) < 0) {
        perror("listen");
        exit(EXIT_FAILURE);
    }
    std::cout << "Environment-Socket-Server waiting for clients on port " << mPort << std::endl;
}

// Handle accepting incoming client connections
void Environment_SocketServer::acceptHandler() {
    while (isRunning) {

        // Accepting a client connection
        if ((mComm_Socket = accept(mServerSocket, (struct sockaddr *) &mClientAddr, (socklen_t *) &mAddrlen)) < 0) {
            perror("accept");
            exit(EXIT_FAILURE);
        }

        // Creating a thread for each client for handling communication
        commThreadParams *params = new commThreadParams();
        params->mComm_Socket = mComm_Socket;
        params->environment_SocketServer = this;
        pthread_t thread;
        if (pthread_create(&thread, NULL, (void *(*)(void *)) &commHandler, params) == -1) {
            std::cout << "Error: thread creation failed" << std::endl;

        } else {
            std::cout << "Thread created successfully" << std::endl;
        }


        // Log the connection details
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
}


// Static function for handling client communication
void *Environment_SocketServer::commHandler(void *_params) {
    // Extract parameters and start communication
    commThreadParams *params = (commThreadParams *) _params;
    int mComm_Socket = params->mComm_Socket;
    Environment_SocketServer *environment_SocketServer = params->environment_SocketServer;
    // Main communication loop
    char clientMsg[BUFFER_SIZE] = {0};
    while (true) {
        memset(clientMsg, 0, BUFFER_SIZE);
        int msg_len = recv(mComm_Socket, clientMsg, BUFFER_SIZE, 0);

        if (msg_len < 0) {
            perror("recv failed");
            break;
        } else if (msg_len == 0) {
            std::cout << "Client disconnected." << std::endl;
            std::cout << "Environment-Socket-Server waiting for clients on port " << environment_SocketServer->mPort
                      << std::endl;

            environment_SocketServer->acceptHandler();
            msg_len = recv(mComm_Socket, clientMsg, BUFFER_SIZE, 0);
        }

        clientMsg[msg_len] = '\0';
        std::cout << "Message from client: " << clientMsg << std::endl;

        std::string echoMsg = "ECHO: " + std::string(clientMsg);

        if (strcmp(clientMsg, "shutdown") == 0) {
            std::cout << "Shutdown command received. Closing server." << std::endl;
            close(mComm_Socket);
            close(environment_SocketServer->mServerSocket);
            environment_SocketServer->isRunning = false;
            break;
        } else if (strcmp(clientMsg, "drop") == 0) {
            std::cout << "Drop command received. Closing client." << std::endl;
            close(mComm_Socket);
            std::cout << "Environment-Socket-Server waiting for clients on port " << environment_SocketServer->mPort
                      << std::endl;
            break;
        } else if (strcmp(clientMsg, "getSensortypes()#") == 0) {
            std::string sensorTypes = "light;noise;air#\n";
            std::cout << "Sending SensorTypes: " << sensorTypes << std::endl;
            echoMsg += sensorTypes;

            if (send(mComm_Socket, echoMsg.c_str(), strlen(echoMsg.c_str()), 0) == -1) {
                std::cout << "send sensortypes has an error!" << std::endl;
            }
        } else if (strcmp(clientMsg, "getSensor(air)#") == 0) {
            int rTime = rand();
            int rAir1 = rand() % 100;
            int rAir2 = rand() % 100;
            int rAir3 = rand() % 100;

            std::string air = std::to_string(rTime) + "|" + std::to_string(rAir1) + ";" + std::to_string(rAir2) + ";" + std::to_string(rAir3) + "#" + "\n";
            std::cout << "Sending air: " << air<< std::endl;
            echoMsg = echoMsg + air;

            if (send(mComm_Socket, echoMsg.c_str(), strlen(echoMsg.c_str()), 0) == -1) {
                std::cout << "send air has an error!" << std::endl;
            }


        } else if (strcmp(clientMsg, "getSensor(noise)#") == 0) {
            int rTime = rand();
            int rNoise1 = rand() % 100;

            std::string noise = std::to_string(rTime) + "|" + std::to_string(rNoise1) + "#" + "\n";
            std::cout << "Sending noise: " << noise<< std::endl;
            echoMsg = echoMsg + noise;

            if (send(mComm_Socket, echoMsg.c_str(), strlen(echoMsg.c_str()), 0) == -1) {
                std::cout << "send noise has an error!" << std::endl;
            }
        } else if (strcmp(clientMsg, "getSensor(light)#") == 0) {
            int rTime = rand();
            int rLight1 = rand() % 100;

            std::string light = std::to_string(rTime) + "|" + std::to_string(rLight1) + "#" + "\n";
            std::cout << "Light: " << light << std::endl;
            echoMsg = echoMsg + light;

            if (send(mComm_Socket, echoMsg.c_str(), strlen(echoMsg.c_str()), 0) == -1) {
                std::cout << "send light has an error!" << std::endl;
            }
        } else if (strcmp(clientMsg, "getAllSensors()#") == 0) {
            int rTime = rand();
            int rAir1 = rand() % 100;
            int rAir2 = rand() % 100;
            int rAir3 = rand() % 100;
            int rNoise1 = rand() % 100;
            int rLight1 = rand() % 100;

            std::string allSensors = std::to_string(rTime) + "|air;" + std::to_string(rAir1) + ";" + std::to_string(rAir2) + ";" + std::to_string(rAir3) + "|noise;" + std::to_string(rNoise1) + "|light;" + std::to_string(rLight1) + "#" + "\n";
            std::cout << "All Sensors: " << allSensors<< std::endl;
            echoMsg = echoMsg + allSensors;

            if (send(mComm_Socket, echoMsg.c_str(), strlen(echoMsg.c_str()), 0) == -1) {
                std::cout << "send allSensors has an error!" << std::endl;
            }
        }
        else {
            send(mComm_Socket, echoMsg.c_str(), echoMsg.length(), 0);
        }

        memset(clientMsg, 0, BUFFER_SIZE);
    }
    pthread_exit(nullptr);
}




