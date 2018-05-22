package it.polimi.se2018.server.network.socket;

import it.polimi.se2018.server.network.Lobby;

import java.io.IOException;
import java.net.ServerSocket;

public class ListenerSocket implements Runnable{

    private Lobby lobby;
    private ServerSocket serverSocket;

    public ListenerSocket(Lobby lobby, ServerSocket serverSocket) {
        this.lobby = lobby;
        this.serverSocket = serverSocket;
    }

    public void run(){

        boolean isOpen = true;

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
}
