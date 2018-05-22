package it.polimi.se2018.server.Network;

import it.polimi.se2018.client.ClientInterface;
import it.polimi.se2018.server.controller.Controller;
import it.polimi.se2018.server.fake_view.FakeView;

import java.io.IOException;
import java.net.ServerSocket;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class SingletonServer extends UnicastRemoteObject implements ServerInterface {

    private final int PORT = 1234;

    private Controller controller;
    private FakeView virtualView;
    private Lobby lobby;
    private ServerSocket serverSocket;
    private boolean isOpen;

    private static SingletonServer ourInstance;

    static {
        try {
            ourInstance = new SingletonServer();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static SingletonServer getInstance() {
        return ourInstance;
    }

    private SingletonServer() throws RemoteException  {
        super(0);

        controller = null;
        virtualView = null;
        lobby = new Lobby();
        isOpen = true;

        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            System.out.println("[*] ERRORE: impossibile aprire la socket.");
        }
    }


    /**
     * Metodo run() che avvia il thread dedicato alla connessione socket
     *
     */


    public void run(){
        Runnable runnable = new ListenerSocket(lobby, serverSocket);
        Thread threadSocket = new Thread(runnable);

        threadSocket.start();
    }


    /**
     * Metodo che aggiunge un nuovo Client che ha richiesto una connessione RMI alla lista di giocatori
     *
     * @param clientInterface tipologia di connessione (in questo caso RMI) scelta dal client
     * @param nickname nome del giocatore
     */

    @Override
    public void add(ClientInterface clientInterface, String nickname) {
        Client client = new Client(nickname, new ConnectionRMI(), null);
    }
}
