/**
 * @Authors: Benjamin Golić (MC) & Amel Šahbaz (AC)
 */

package at.fhooe.sail.vis.environmentrmiclient;

import at.fhooe.sail.vis.environmenti.EnvData;
import at.fhooe.sail.vis.environmenti.IEnvService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class  Environment_RmiClient {

    private Environment_RmiClient() {}

    /**
     * The main method for the RMI client. It connects to the RMI registry, retrieves
     * the list of available services, and continuously fetches and displays environmental data.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry();
            String[] list = registry.list();
            for (String s : list) {
                System.out.println(s);
            }
            IEnvService servObj = (IEnvService) registry.lookup("EnvironmentServiceI");


            while (true) {
                String[] sensors = servObj.requestEnvironmentDataTypes();
                for (String sensor : sensors) {
                    EnvData dataO = servObj.requestEnvironmentData(sensor);
                    System.out.print(dataO);
                    System.out.println();
                    System.out.println("*****************************");
                } // for sensor
                System.out.println();
                System.out.println();
                EnvData[] dataOs = servObj.requestAll();
                for (EnvData dataO : dataOs) {
                    System.out.println(dataO);
                } // for data
                try {
                    Thread.sleep(1000);
                } catch (Exception _e) {
                    _e.printStackTrace();
                }
            } // while true

        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
