/**
 * The {@code ServiceMgmt} class manages a Remote Method Invocation (RMI) service.
 * It provides functionality to start, stop, and quit the RMI server.
 * The main method includes a command-line menu for the user to control the RMI service.
 * This class demonstrates basic RMI server management operations.
 *
 * @author Benjamin Golić (MC) & Amel Šahbaz (AC)
 */

package at.fhooe.sail.vis.environmentrmiserver;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class ServiceMgmt {

    private static Dummy_RmiServer mDummyRmiServer;
    private Registry mRegistry;
    private Environment_RmiServer mRmiServer = null;

    /**
     * Constructs a new ServiceMgmt instance and creates a RMI registry.
     * @throws RemoteException if an error occurs during the creation of the registry.
     */
    public ServiceMgmt() throws RemoteException {
        mRegistry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
    }

    /**
     * The main method that provides a command-line interface for managing the RMI service.
     * It allows starting, stopping, and quitting the RMI service.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);
            ServiceMgmt serviceMgmt = new ServiceMgmt();
            boolean isRunning = false;
            boolean isQuitting = false;

            while(true) {
                System.out.print("Command-Menu:\n- Start\n- Stop\n- Quit\nEnter a command: ");
                String commandInput = sc.nextLine();

                switch(commandInput) {
                    case "Start":
                        if (!isRunning) {
                            serviceMgmt.startRmi();
                            isRunning = true;
                        }
                        break;
                    case "Stop":
                        if (isRunning) {
                            serviceMgmt.stopRmi();
                            isRunning = false;
                        }
                        break;
                    case "Quit":
                        if (isRunning) {
                            serviceMgmt.quitRmi();
                            isRunning = false;
                        }
                        isQuitting = true;
                        break;
                    default:
                        System.out.println("Invalid command.");
                        break;
                }

                if (isQuitting) {
                    break;
                }
            }

            /**
             // Task 2.1b):
             Environment_RmiServer server = new Environment_RmiServer();
             Registry registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
             registry.rebind("EnvironmentServiceI", server);
             System.out.println("Environment RMI Server is ready.");
             */

        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }

    /**
     * Starts the RMI service by creating and binding the RMI server objects.
     * Outputs a message indicating the status of the service.
     */
    public void startRmi() {
        try {
            mRmiServer = new Environment_RmiServer();
            mDummyRmiServer = new Dummy_RmiServer();
            mRegistry.rebind("EnvironmentServiceI", mRmiServer);
            mRegistry.rebind("DummyServiceI", mDummyRmiServer);
            System.out.println("RMI service started.");
            System.out.println("Server is waiting…");
        } catch (RemoteException e) {
            System.out.println("Failed to start RMI service.");
            e.printStackTrace();
        }
    }

    /**
     * Stops the RMI service by unbinding the RMI server objects and unexporting them.
     * Outputs a message indicating that the service has been stopped.
     */
    public void stopRmi() {
        try {
            mRegistry.unbind("EnvironmentServiceI");
            mRegistry.unbind("DummyServiceI");
            UnicastRemoteObject.unexportObject(mRmiServer, true);
            UnicastRemoteObject.unexportObject(mDummyRmiServer, true);
            System.out.println("RMI service stopped.");
        } catch (RemoteException | NotBoundException e) {
            System.out.println("Failed to stop RMI service.");
            e.printStackTrace();
        }

    }

    /**
     * Quits the RMI service by stopping it and exiting the application.
     * Outputs a message indicating that the service is stopping and the application is exiting.
     */
    public void quitRmi() {
        try {
            mRegistry.unbind("EnvironmentServiceI");
            mRegistry.unbind("DummyServiceI");
            java.rmi.server.UnicastRemoteObject.unexportObject(mRmiServer, true);
            java.rmi.server.UnicastRemoteObject.unexportObject(mDummyRmiServer, true);
            System.out.println("RMI service stopped. Exiting ServiceMgmt.");
            System.exit(0);
        } catch (RemoteException | NotBoundException e) {
            System.out.println("Failed to stop RMI service.");
            e.printStackTrace();
        }
    }
}


