/**
 * @Authors: Benjamin Golić (MC) & Amel Šahbaz (AC)
 */

#ifndef VIS_IPV6_SOCKETSERVER_H
#define VIS_IPV6_SOCKETSERVER_H
#include <netinet/in.h>

// Definition der Klasse Ipv6_SocketServer
class Ipv6_SocketServer {
public:
    // Konstruktor: Initialisiert den Server mit einem spezifischen Port
    explicit Ipv6_SocketServer(int mPort);
    // Destruktor: Behandelt die Bereinigung, falls notwendig
    ~Ipv6_SocketServer();

    // InitializeSocket: Richtet den IPv6-Socket ein und initialisiert ihn
    void InitializeSocket();
    // listenHandler: Lauscht auf eingehende Client-Verbindungen
    void listenHandler();
    // acceptHandler: Akzeptiert eingehende Client-Verbindungen
    void acceptHandler();
    // commHandler: Behandelt die Kommunikation mit den verbundenen Clients
    void commHandler();


private:
    // Socket-Deskriptor für den Server und die Kommunikation mit Clients
    int mServerSocket, mComm_Socket;
    // Strukturen zur Speicherung der Server- und Client-Adressinformationen für IPv6
    sockaddr_in6 mServerAddr, mClientAddr;
    // Länge der Serveradresse
    int mAddrlen;
    // Portnummer, auf der der Server operiert
    int mPort;
    // Puffer zur Speicherung der IP-Adresse des Clients
    char mClientIP[INET_ADDRSTRLEN];
};

#endif //VIS_IPV6_SOCKETSERVER_H
