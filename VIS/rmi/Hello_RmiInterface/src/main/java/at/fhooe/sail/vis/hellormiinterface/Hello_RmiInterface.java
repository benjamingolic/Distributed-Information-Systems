/**
 * @Authors: Benjamin Golić (MC) & Amel Šahbaz (AC)
 */

package at.fhooe.sail.vis.hellormiinterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Hello_RmiInterface extends Remote {
    /**
     * A remote method that, when implemented, should return a string message.
     * This method can be used for simple testing or demonstration purposes of RMI functionality.
     *
     * @return A string message from the remote object.
     * @throws RemoteException if a remote invocation error occurs.
     */
    String saySomething() throws RemoteException;
}