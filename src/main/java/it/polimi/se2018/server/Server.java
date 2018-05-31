package it.polimi.se2018.server;



import it.polimi.se2018.server.network.Lobby;
import it.polimi.se2018.server.network.implementation.ServerImplementationRMI;
import it.polimi.se2018.server.network.implementation.ServerImplementationSocket;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Server {

    private final int PORT = 1099;

    private Lobby lobby;
    private ServerImplementationSocket serverImplementationSocket;
    private ServerImplementationRMI serverImplementationRMI;

    public Server() {
        lobby = new Lobby(30);
        serverImplementationSocket = new ServerImplementationSocket(lobby);

        //Lancio il thread di attesa delle connessioni.
        (new Thread(serverImplementationSocket)).start();

        try {
            serverImplementationRMI = new ServerImplementationRMI(lobby);
        } catch (RemoteException e) {
            System.out.println("[*]ERRORE nella creazione del server RMI!");
        }

        //Aggiungo l'interfaccia del server al registry.
        try {
            System.out.println("[*]Lancio il registry sulla porta 1099.");
            LocateRegistry.createRegistry(PORT);

            Naming.rebind("//localhost/MyServer", serverImplementationRMI);
        } catch (RemoteException e) {
            System.out.println("[*]ERRORE registry già presente!");
        } catch (MalformedURLException e) {
            System.out.println("[*]ERRORE nell'aggiunta dell'interfaccia la registry!");
        }
    }


    public static void main(String args[]) {
        new Server();
    }
}
