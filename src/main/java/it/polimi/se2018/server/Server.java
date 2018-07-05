package it.polimi.se2018.server;



import it.polimi.se2018.server.network.Lobby;
import it.polimi.se2018.server.network.implementation.ServerImplementationRMI;
import it.polimi.se2018.server.network.implementation.ServerImplementationSocket;

import java.io.IOException;

public class Server {


    private static final int DEFAULT_PORT = 1234;
    private static final int DEFAULT_TIME_LOGIN = 30; //Tempo in secondi.
    private static final int DEFAULT_TIME_TURN = 1000; //Tempo in secondi //todo modificare!!!.


    private Server(int port, int loginTimer, int turnTime) {

        Lobby.factoryLobby(loginTimer, turnTime);

        try {

            ServerImplementationSocket serverImplementationSocket = new ServerImplementationSocket(port);
            new ServerImplementationRMI();

            //Lancio il thread di attesa delle connessioni.
            (new Thread(serverImplementationSocket)).start();

        }catch (IOException e) {
            System.out.println("[*] ERRORE impossibile avviare il server!");
            System.exit(0);
        }



    }

    private static void printHelp(){

        System.out.println("Sargada Server (help)");
        System.out.println("#> java -jar server.jar porta tempoLogin tempoTurno");
        System.out.println("");
        System.out.println("porta = porta usata per le connessioni socket");
        System.out.println("tempoLogin = tempo (in secondi) che il server rimane in attesa di giocatori che volgio partecipare alla partita");
        System.out.println("tempoTurno = tempo (in secondi) che un giocatore ha per giocare il suo turno");
    }

    public static void main(String[] args) {

        if(args.length > 0){

            if(args[0].equals("-h")) //Controllo se è stata inserita la richiesta dell'help.
                Server.printHelp();

            if(args.length == 3)
                new Server(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));

        }

        if(args.length == 0)
            new Server(DEFAULT_PORT, DEFAULT_TIME_LOGIN, DEFAULT_TIME_TURN); //In tutti gli altri casi avvio il server con i parametri di default.
        else
            Server.printHelp();
    }
}
