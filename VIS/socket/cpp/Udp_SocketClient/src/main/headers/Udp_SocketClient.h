/**
 * @Authors: Benjamin Golić (MC) & Amel Šahbaz (AC)
 */

#ifndef VIS_UDP_SOCKETCLIENT_H
#define VIS_UDP_SOCKETCLIENT_H
#include <netinet/in.h>

// Declaration of the Udp_SocketClient class
class Udp_SocketClient {
public:
    // Constructor: Initializes the client with a specific port and IP address
    explicit Udp_SocketClient(int mPort, const char *mIP);
    // Destructor: Handles any cleanup if necessary
    ~Udp_SocketClient();
    // InitializeSocket: Sets up and initializes the UDP client socket
    void InitializeSocket();
    // commHandler: Handles the overall communication process of the client
    void commHandler();
    // receiveHandler: Manages the reception of messages from the server
    bool receiveHandler();
    // sendHandler: Handles sending messages to the server
    void sendHandler();

private:
    // Socket descriptor for the UDP client
    int udpClientSocket;
    // Struct to store server address information
    sockaddr_in serverAddr;
    // Struct to store client address information
    sockaddr_in clientAddr;
    // Length of the server address
    int mAddrlen;
    // Port number on which the client operates
    int mPort;
    // IP address of the server
    const char* mIP;
    // Buffer to store the IP address of the server
    char clientIP[INET_ADDRSTRLEN];
};

#endif //VIS_UDP_SOCKETCLIENT_H