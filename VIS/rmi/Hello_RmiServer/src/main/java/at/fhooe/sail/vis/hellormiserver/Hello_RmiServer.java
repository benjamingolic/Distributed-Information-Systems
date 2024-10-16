/**
 * @Authors: Benjamin Golić (MC) & Amel Šahbaz (AC)
 */

package at.fhooe.sail.vis.hellormiserver;

import at.fhooe.sail.vis.hellormiinterface.Hello_RmiInterface;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Hello_RmiServer extends UnicastRemoteObject implements Hello_RmiInterface {

    /**
     * Constructs a {@code Hello_RmiServer} object. This constructor calls the parent
     * constructor of {@link UnicastRemoteObject}, which exports the remote object
     * to receive incoming remote method calls.
     *
     * @throws RemoteException if an error occurs during the creation of the remote object.
     */
    public Hello_RmiServer() throws RemoteException {
        super();
    }

    /**
     * Implements the {@code saySomething} method from {@code Hello_RmiInterface}.
     * This method returns a simple string message.
     *
     * @return A string message "cookies!".
     * @throws RemoteException if a remote invocation error occurs.
     */
    public String saySomething() throws RemoteException {
        return "cookies!";
    }

    /**
     * The main method for the RMI server. It creates the remote object and binds it
     * in the RMI registry, making it available for client connections.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        try {
            // Create the remote object
            Hello_RmiServer server = new Hello_RmiServer();

            // Get registry
            Registry registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);

            // Bind the remote object's stub in the registry
            registry.rebind("Hello", server);

            System.out.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
