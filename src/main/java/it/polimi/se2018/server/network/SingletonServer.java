package it.polimi.se2018.server.network;

import it.polimi.se2018.client.ClientInterface;
import it.polimi.se2018.server.controller.Controller;
import it.polimi.se2018.server.fake_view.FakeView;
import it.polimi.se2018.server.network.rmi.ConnectionRMI;
import it.polimi.se2018.server.network.rmi.ServerInterface;
import it.polimi.se2018.server.network.socket.ListenerSocket;

import java.io.IOException;
import java.net.ServerSocket;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class SingletonServer extends UnicastRemoteObject implements ServerInterface {

    private final int PORT = 1234;

    private Controller controller;
    private FakeView virtualView;
    private Lobby lobby;
    private ServerSocket serverSocket;

    private static SingletonServer ourInstance;

    public static SingletonServer getInstance() {

        if (ourInstance == null) {
            try {
                ourInstance = new SingletonServer();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return ourInstance;
    }

    public SingletonServer() throws RemoteException  {
        super(0);

        controller = null;
        virtualView = null;
        lobby = new Lobby();

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
     * Metodo che aggiunge un nuovo FakeClient che ha richiesto una connessione RMI alla lista di giocatori
     *
     * @param clientInterface tipologia di connessione (in questo caso RMI) scelta dal client
     * @param nickname nome del giocatore
     */

    @Override
    public void add(ClientInterface clientInterface, String nickname) {
        if(lobby.getNicknameList().contains(nickname)) {
            try {
                clientInterface.send("no");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        else

        lobby.add(new FakeClient(nickname, new ConnectionRMI(clientInterface), null));

    }
}
