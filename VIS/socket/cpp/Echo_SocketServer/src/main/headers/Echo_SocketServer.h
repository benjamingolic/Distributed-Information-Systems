/**
 * @Authors: Benjamin Golić (MC) & Amel Šahbaz (AC)
 */

#ifndef VIS_ECHO_SOCKETSERVER_H
#define VIS_ECHO_SOCKETSERVER_H

#include <netinet/in.h>
// Echo_SocketServer class definition
class Echo_SocketServer {
public:
    // Constructor: Initializes the server with a specific port number
    explicit Echo_SocketServer(int mPort);
    // Destructor: Handles any necessary cleanup
    ~Echo_SocketServer();

    // InitializeSocket: Sets up and initializes the server socket
    void InitializeSocket();
    // listenHandler: Manages the process of listening for incoming client connections
    void listenHandler();
    // acceptHandler: Handles the acceptance of incoming client connections
    void acceptHandler();
    // commHandler: Handles the communication with connected clients
    // Typically involves echoing back the received messages
    void commHandler();


private:
    // mServerSocket: The socket descriptor for the server
    // mComm_Socket: The socket descriptor for communication with a client
    // mClientAddr: Struct to store the address information of the client
    int mServerSocket, mComm_Socket;
    // mClientAddr: Struct to store the address information of the client
    sockaddr_in mClientAddr;
    // mAddrlen: The length of the client address structure
    int mAddrlen;
    // mPort: The port number on which the server is listening
    int mPort;
};

#endif //VIS_ECHO_SOCKETSERVER_H
