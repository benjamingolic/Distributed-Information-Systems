/**
 * @Authors: Benjamin Golić (MC) & Amel Šahbaz (AC)
 */

package at.fhooe.sail.vis.hellormiclient;

import at.fhooe.sail.vis.hellormiinterface.Hello_RmiInterface;
//import at.fhooe.sail.vis.rmi.rmiInterface;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Hello_RmiClient {
    public static void main(String[] args) {
        try {

            /**my own jar**/
            // Get the registry
            Registry registry = LocateRegistry.getRegistry();
            // Look up the remote object
            Hello_RmiInterface stub = (Hello_RmiInterface) registry.lookup("Hello");

            // Call the remote method
            String response = stub.saySomething();

            System.out.println("Response: " + response);
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
