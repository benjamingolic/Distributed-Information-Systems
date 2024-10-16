/**
 * @Authors: Benjamin Golić (MC) & Amel Šahbaz (AC)
 */

package at.fhooe.sail.vis.environmentrmiserver;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


public class Dummy_RmiServer extends UnicastRemoteObject {
    /**
     * Constructs a {@code Dummy_RmiServer} object.
     * This constructor calls the parent constructor of {@link UnicastRemoteObject},
     * which exports the remote object to receive incoming remote method calls.
     *
     * @throws RemoteException if an error occurs during the creation of the remote object.
     */
    protected Dummy_RmiServer() throws RemoteException {
        super();
    }
}
