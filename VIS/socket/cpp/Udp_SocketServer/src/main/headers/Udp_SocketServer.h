/**
 * @Authors: Benjamin Golić (MC) & Amel Šahbaz (AC)
 */

#ifndef VIS_UDP_SOCKETSERVER_H
#define VIS_UDP_SOCKETSERVER_H
#include <netinet/in.h>
// Declaration of the Udp_SocketServer class
class Udp_SocketServer {
public:
    // Constructor: Initializes the server with a specific port
    explicit Udp_SocketServer(int mPort);
    // Destructor: Handles any cleanup if necessary
    ~Udp_SocketServer();

    // InitializeSocket: Sets up and initializes the UDP socket
    void InitializeSocket();
    // commHandler: Handles the overall communication process
    void commHandler();
    // receiveHandler: Manages the reception of messages from clients
    bool receiveHandler();
    // sendHandler: Handles sending responses back to the client
    void sendHandler();
private:
    // Socket descriptor for the UDP server
    int mUdpServerSocket;
    // Struct to store server address information
    sockaddr_in mServerAddr;
    // Struct to store client address information
    sockaddr_in mClientAddr;
    // Length of the server address
    int mAddrlen;
    // Port number on which the server operates
    int mPort;
    // Buffer to store messages received from clients
    char msg[1024];
    // Buffer to store messages received from clients
    char mClientIP[INET_ADDRSTRLEN];
    // Size of the client address struct
    socklen_t mClientAddrSize;
};

#endif //VIS_UDP_SOCKETSERVER_H
