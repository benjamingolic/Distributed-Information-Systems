/**
 * @Authors: Benjamin Golić (MC) & Amel Šahbaz (AC)
 */

package at.fhooe.sail.vis.environmentrmiserver;

import at.fhooe.sail.vis.environmenti.EnvData;
import at.fhooe.sail.vis.environmenti.IEnvService;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.time.Instant;
import java.util.Random;
import java.util.Scanner;
import java.sql.Timestamp;

public class Environment_RmiServer extends UnicastRemoteObject implements IEnvService {

    /**
     * Constructs an {@code Environment_RmiServer} object.
     *
     * @throws RemoteException if an error occurs during the creation of the remote object.
     */
    public Environment_RmiServer() throws RemoteException {
        super();
    }

    /**
     * Returns an array of strings representing the types of environment data this server can provide.
     * Currently, this server provides data only for "pressure".
     *
     * @return An array of data types available.
     * @throws RemoteException if a remote invocation error occurs.
     */
    @Override
    public String[] requestEnvironmentDataTypes() throws RemoteException {
        return new String[]{"pressure"};
    }

    /**
     * Provides environmental data for a given type. Currently, this method generates random data for air pressure.
     *
     * @param _type The type of environmental data requested.
     * @return An {@code EnvData} object containing the requested data.
     * @throws RemoteException if a remote invocation error occurs.
     */
    @Override
    public EnvData requestEnvironmentData(String _type) throws RemoteException {
        // generate a random Timestamp
        Timestamp timestamp = Timestamp.from(Instant.now());

        if(_type.equals("pressure")) {
            Random random = new Random();
            int pressureValue = random.nextInt(101) + 950;
            return new EnvData("pressure", timestamp.getTime(), new int[]{pressureValue});
        }
        return new EnvData();
    }

    /**
     * Provides all available environmental data. Currently, this method returns data for air pressure.
     *
     * @return An array of {@code EnvData} objects containing all available data.
     * @throws RemoteException if a remote invocation error occurs.
     */
    @Override
    public EnvData[] requestAll() throws RemoteException {
        return new EnvData[]{requestEnvironmentData("pressure")};
    }
}
