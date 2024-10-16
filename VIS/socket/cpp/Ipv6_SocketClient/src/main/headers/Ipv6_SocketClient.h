/**
 * @Authors: Benjamin Golić (MC) & Amel Šahbaz (AC)
 */

#ifndef VIS_IPV6_SOCKETCLIENT_H
#define VIS_IPV6_SOCKETCLIENT_H
#include <netinet/in.h>

// Definition of the Ipv6_SocketClient class
class Ipv6_SocketClient {
public:
    // Constructor: Initializes the client with a specific port and IP address
    explicit Ipv6_SocketClient(int mPort, const char *mIP);
    // Destructor: Handles any cleanup if necessary
    ~Ipv6_SocketClient();
    // InitializeSocket: Sets up and initializes the IPv6 client socket
    void InitializeSocket();
    // connectHandler: Manages the process of connecting to the IPv6 server
    void connectHandler();
    // commHandler: Handles the overall communication process with the server
    void commHandler();
private:
    // Socket descriptor for the IPv6 client
    int mIpv6ClientSocket;
    // Struct to store server address information for IPv6
    sockaddr_in6 mServerAddr;
    // Port number on which the client intends to communicate
    int mPort;
    // IP address of the server
    const char* mIP;
};

#endif //VIS_IPV6_SOCKETCLIENT_H
