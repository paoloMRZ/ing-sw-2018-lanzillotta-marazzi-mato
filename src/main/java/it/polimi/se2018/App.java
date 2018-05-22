package it.polimi.se2018;

import it.polimi.se2018.server.Network.ServerInterface;
import it.polimi.se2018.server.Network.SingletonServer;

import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1099);
            SingletonServer server = SingletonServer.getInstance();

            server.run();
            Naming.rebind("Server", server);

        } catch (RemoteException | MalformedURLException e) {
            e.printStackTrace();
        }

    }
}
