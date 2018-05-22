package it.polimi.se2018.server.Network;

import it.polimi.se2018.client.ClientInterface;
import it.polimi.se2018.server.controller.Controller;
import it.polimi.se2018.server.fake_view.FakeView;

import java.io.IOException;
import java.net.ServerSocket;
import java.rmi.Remote;

public class SingletonServer implements ServerInterface, Remote {

    private final int PORT = 1234;

    private Controller controller;
    private FakeView virtualView;
    private Lobby lobby;
    private ServerSocket serverSocket;
    private boolean isOpen;

    private static SingletonServer ourInstance = new SingletonServer();

    public static SingletonServer getInstance() {
        return ourInstance;
    }

    private SingletonServer() {

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

    public void run(){

        System.out.println("[*] In attesa di connessioni.");
        while(isOpen){

            try {
                Runnable r = new ConnectionHandler(lobby, serverSocket.accept());
                new Thread(r).start();

            } catch (IOException e) {
                System.out.println("[*] ERRORE: impossibile aprire la socket.");
            }

            System.out.println("[*] Ho ricevuto una connessione.");
        }
    }

    @Override
    public void add(ClientInterface clientInterface, String nickname) {
        Client client = new Client(new)
    }
}
