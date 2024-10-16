/**
 * @Authors: Benjamin Golić (MC) & Amel Šahbaz (AC)
 */

#include <iostream>
#include "../headers/Ipv6_SocketServer.h"

int mPort;

/**
 * @brief Main function of the IPv6 socket server program.
 * @param _argc Number of command-line arguments.
 * @param _argv Array of command-line arguments.
 * @return Integer indicating the exit status.
 *
 * The main function checks for a port number provided as a command-line argument.
 * If not provided, it prompts the user to input the port number.
 * It then initializes an instance of Ipv6_SocketServer and handles incoming connections.
 */
int main(int _argc, char* _argv[]) {
    // Überprüfen, ob der Port als Befehlszeilenargument übergeben wurde
    if (_argc == 2) {
        try {
            // Versuchen, das erste Argument in eine Portnummer umzuwandeln
            mPort = std::stoi(_argv[1]);
        } catch (std::exception& e) {
            // Fehlerbehandlung, falls die Portnummer ungültig ist
            std::cerr << "Invalid port provided. Exiting." << std::endl;
            return EXIT_FAILURE;
        }
    } else {
        // Portnummer vom Benutzer anfordern, wenn sie nicht als Argument übergeben wurde
        std::cout << "No port provided. Please enter port: " << std::endl;
        std::cin >> mPort;
        std::cout << "Port set: " << mPort << std::endl;
    }
    // Erstellen einer Instanz des IPv6-Socket-Servers
    Ipv6_SocketServer *ipv6SocketServer = new Ipv6_SocketServer(mPort);
    // Initialisieren des Sockets
    ipv6SocketServer->InitializeSocket();
    // Lauschen auf eingehende Verbindungen
    ipv6SocketServer->listenHandler();
    // Akzeptieren eingehender Verbindungen
    ipv6SocketServer->acceptHandler();
    // Behandeln der Kommunikation
    ipv6SocketServer->commHandler();

    return 0;
}