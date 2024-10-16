/**
 * @Authors: Benjamin Golić (MC) & Amel Šahbaz (AC)
 */

package at.fhooe.sail.vis.environmentclient;

import at.fhooe.sail.vis.environmenti.EnvData;
import at.fhooe.sail.vis.environmenti.IEnvService;

import java.rmi.RemoteException;

/**
 * The {@code environmentTestingMain} class serves as a testing client for environmental data services.
 * It continuously requests and prints environmental data from an {@link IEnvService} implementation,
 * demonstrating the usage and response of the service.
 */
public class environmentTestingMain {
    public static void main(String[] _argv) throws RemoteException {
        IEnvService service = new Environment_SocketClient("127.0.0.1", 4949);
        while (true) {
            String[] sensors = service.requestEnvironmentDataTypes();
            for (String sensor : sensors) {
                EnvData dataO = service.requestEnvironmentData(sensor);
                System.out.print(dataO);
                System.out.println();
                System.out.println("*****************************");
            } // for sensor
            System.out.println();
            System.out.println();
            EnvData[] dataOs = service.requestAll();
            for (EnvData dataO : dataOs) {
                System.out.println(dataO);
            } // for data
            try {
                Thread.sleep(1000);
            } catch (Exception _e) {
                _e.printStackTrace();
            }
        } // while true
    }
}
