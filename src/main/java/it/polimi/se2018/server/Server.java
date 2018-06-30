package it.polimi.se2018.server;



import it.polimi.se2018.server.network.Lobby;
import it.polimi.se2018.server.network.implementation.ServerImplementationRMI;
import it.polimi.se2018.server.network.implementation.ServerImplementationSocket;

import java.io.IOException;

public class Server {



    private Lobby lobby;
    private ServerImplementationSocket serverImplementationSocket;
    private ServerImplementationRMI serverImplementationRMI;

    public Server() {
        lobby = Lobby.factoryLobby(30);

        try {

            this.serverImplementationSocket = new ServerImplementationSocket();
            this.serverImplementationRMI = new ServerImplementationRMI();

            //Lancio il thread di attesa delle connessioni.
            (new Thread(serverImplementationSocket)).start();

        }catch (IOException e) {
            System.out.println("[*] ERRORE impossibile avviare il server!");
            System.exit(0);
        }
    }


    public static void main(String args[]) {
        new Server();
    }
}
