# Distributed Information Systems

**A Gradle-based project implementing distributed system communication using C++, Java, TCP/IP sockets, RMI, and REST-based web services.**

## Overview

This project was developed as part of the Distributed Information Systems (DIS) module at our university, in collaboration with my fellow student, [Amel Šahbaz](https://github.com/amelshbz). 
It showcases a comprehensive approach to distributed systems, integrating multiple technologies and programming paradigms. Built using **C++** and **Java**, it spans from low-level socket communication to high-level web services with **Java Servlets**.

## Project Structure

The repository is divided into the following core components:

- **Primitive Socket Communication (C++)**: A TCP/IP socket-based client-server application that facilitates communication between distributed systems. The server listens on a specified port and accepts incoming connections, while the client sends messages. The server maintains persistence by continuously accepting connections from new clients.
  
- **Refined Socket Communication**: Error handling, message acknowledgment, and multi-client support were added to extend the primitive client-server model.

- **UDP Socket Communication (C++)**: A lightweight, connectionless UDP server-client implementation, showcasing alternative network communication methods.

- **Environment Server (C++)**: A multithreaded server designed to handle simultaneous client requests. The server simulates sensor data and responds to client queries using a specified protocol.

- **Java RMI (Remote Method Invocation)**: Implementing a client-server communication model using Java RMI, allowing remote method invocations from the client on the server. This system handles environmental sensor data requests and responses in real-time.

- **Java Servlets**: A REST-based web service, using **Java Servlets** to handle HTTP requests and responses. Multiple endpoints were created, providing environmental sensor data in both XML and JSON formats.

## Features

- **Multithreaded Server**: The C++ environment server handles multiple clients using threading, ensuring high availability and scalability.
- **Cross-Protocol Communication**: Implementations for both TCP and UDP protocols demonstrate flexibility in network communication.
- **RMI Integration**: A Java-based solution for remote object communication, streamlining server-client interactions over a network.
- **REST-based Web Services**: Using Java Servlets, environmental sensor data is exposed via REST APIs, supporting both XML and JSON formats.

## Screenshot Section

*Screenshots will be added soon*
