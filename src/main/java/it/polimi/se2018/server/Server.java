package it.polimi.se2018.server;



import it.polimi.se2018.server.network.Lobby;
import it.polimi.se2018.server.network.implementation.ServerImplementationRMI;
import it.polimi.se2018.server.network.implementation.ServerImplementationSocket;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Scanner;

public class Server {



    private Lobby lobby;
    private ServerImplementationSocket serverImplementationSocket;
    private ServerImplementationRMI serverImplementationRMI;

    public Server() {
        lobby = new Lobby(30);

        try {
            serverImplementationSocket = new ServerImplementationSocket(lobby);
        } catch (IOException e) {
            System.out.println("[*] ERRORE impossibile aprire la socke!");
            System.exit(0);
        }

        //Lancio il thread di attesa delle connessioni.
        (new Thread(serverImplementationSocket)).start();

        try {
            serverImplementationRMI = new ServerImplementationRMI(lobby);
        } catch (MalformedURLException | RemoteException e) {
            System.out.println("[*]ERRORE nella creazione del server RMI!");
            try {
                serverImplementationSocket.close();
                System.exit(0);

            } catch (IOException e1) {
                System.exit(0);
            }
        }


    }


    public static void main(String args[]) {
        new Server();
    }
}
