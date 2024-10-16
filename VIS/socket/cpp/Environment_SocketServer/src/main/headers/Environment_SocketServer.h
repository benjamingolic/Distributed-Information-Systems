/**
 * @Authors: Benjamin Golić (MC) & Amel Šahbaz (AC)
 */

#ifndef VIS_ENVIRONMENT_SOCKETSERVER_H
#define VIS_ENVIRONMENT_SOCKETSERVER_H

#include <netinet/in.h>
#include <vector>

// Forward declaration of the Environment_SocketServer class
class Environment_SocketServer;

// Structure for passing parameters to the communication handler thread
struct commThreadParams {
    Environment_SocketServer *environment_SocketServer;
    int mComm_Socket;
};

// Definition of the Environment_SocketServer class
class Environment_SocketServer {

public:
    // Constructor: Initializes the server with a specific port
    explicit Environment_SocketServer(int mPort);
    // Destructor: Handles any cleanup if necessary
    ~Environment_SocketServer();

    // InitializeSocket: Sets up and initializes the server socket
    void InitializeSocket();
    // listenHandler: Manages the process of listening for incoming connections
    void listenHandler();
    // acceptHandler: Handles accepting incoming client connections
    void acceptHandler();
    // commHandler: Static method for handling client communication in a separate thread
    static void *commHandler(void * _params);

private:
    // Server socket and communication socket descriptors
    int mServerSocket, mComm_Socket;
    // Struct to store client address information
    sockaddr_in mClientAddr;
    // Length of the client address structure
    int mAddrlen;
    // Port number on which the server operates
    int mPort;
    // Vector to keep track of thread IDs
    std::vector<pthread_t> mThreads;
    // Flag to control the server's running state
    bool isRunning = true;
};

#endif //VIS_ENVIRONMENT_SOCKETSERVER_H
