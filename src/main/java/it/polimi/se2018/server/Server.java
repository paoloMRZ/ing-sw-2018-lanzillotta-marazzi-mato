package it.polimi.se2018.server;



import it.polimi.se2018.server.network.Lobby;
import it.polimi.se2018.server.network.implementation.ServerImplementationRMI;
import it.polimi.se2018.server.network.implementation.ServerImplementationSocket;
import org.fusesource.jansi.AnsiConsole;

import java.io.IOException;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * La classe permette di gestire l'avviemeto del server di gioco impostando la parita tramite i parametri forniti a linea
 * di comando dall'utente.
 *
 * @author Marazzi Paolo.
 */
public class Server {


    private static final int DEFAULT_PORT = 1234;
    private static final int DEFAULT_TIME_LOGIN = 30; //Tempo in secondi.
    private static final int DEFAULT_TIME_TURN = 600; //Tempo in secondi. (10 min. di default)

    /**
     * Cosrtuttore della classe.
     *
     * @param port porta per le connessioni socket.
     * @param loginTime tempo disponibile ai giocatori per connettersi al server prima dell'avvio della partita.
     * @param turnTime durata massima di un turno di gioco.
     * @param verbose indica se stampare a schermo i messaggi ricevuti e inviati dal server.
     */
    private Server(int port, int loginTime, int turnTime, boolean verbose) {

        AnsiConsole.systemInstall();

        welcomeMessage(port, loginTime, turnTime); //Stampo schermo i parametri scelti.

        Lobby.factoryLobby(loginTime, turnTime,verbose);

        try {

            ServerImplementationSocket serverImplementationSocket = new ServerImplementationSocket(port);
            new ServerImplementationRMI();

            //Lancio il thread di attesa delle connessioni.
            (new Thread(serverImplementationSocket)).start();

        }catch (IOException  e) {
            AnsiConsole.out.println(ansi().fgRed().a("[*] ERRORE impossibile avviare il server!").fgDefault());
            System.exit(0);
        }


    }

    /**
     * Stampa a schermo del messaggio che mostra i parametri scelti dall'utente.
     *
     * @param port porta per le connessioni socket.
     * @param loginTime tempo disponibile ai giocatori per connettersi al server prima dell'avvio della partita.
     * @param turnTime durata massima di un turno di gioco.
     */
    private void welcomeMessage(int port, int loginTime, int turnTime){

        AnsiConsole.out().println(ansi().bold().a("Sagrada Server\n").boldOff());
        AnsiConsole.out().println("Porta: " + port);
        AnsiConsole.out().println("Tempo di login: " + loginTime);
        AnsiConsole.out().println("Tempo turno di gioco: " + turnTime);
        AnsiConsole.out().println("----------------------------------------------------------------------------------");
    }

    /**
     * Metodo che stampa il messaggio di help.
     */
    private static void printHelp(){

        AnsiConsole.out.println("\nSargada Server (help)");
        AnsiConsole.out.println("");
        AnsiConsole.out.println(" *** Utilizzo ***");
        AnsiConsole.out.println("");
        AnsiConsole.out.println("#> java -jar (opzioni) (nome).jar porta tempoLogin tempoTurno");
        AnsiConsole.out.println("");
        AnsiConsole.out.println("porta : porta usata per le connessioni socket");
        AnsiConsole.out.println("tempoLogin : tempo (in secondi) che il server rimane in attesa di giocatori che voglio partecipare alla partita");
        AnsiConsole.out.println("tempoTurno : tempo (in secondi) che un giocatore ha per giocare il suo turno");
        AnsiConsole.out.println("");
        AnsiConsole.out.println(" *** Opzioni ***");
        AnsiConsole.out.println("");
        AnsiConsole.out.println("-v : mostra i messaggi un ingresso e uscita.");
        AnsiConsole.out.println("-h : mostra questo messaggio.");
        AnsiConsole.out.println("");
        AnsiConsole.out.println("Se non si specifica nessun argomento il server viene avviato con i seguenti parametri di default:");
        AnsiConsole.out.println("\nPorta: 1234");
        AnsiConsole.out.println("Tempo Login: 30 sec.");
        AnsiConsole.out.println("Tempo Turno: 10 min.\n");


    }

    public static void main(String[] args) {

        boolean started = false;

        if(args.length == 0)
            new Server(DEFAULT_PORT,DEFAULT_TIME_LOGIN,DEFAULT_TIME_TURN, false); //Default.

        else {

            if (args.length == 1 && args[0].equals("-h")) {
                Server.printHelp(); //Help.
                started = true;
            }


            if (args.length == 1 && args[0].equals("-v")) {
                new Server(DEFAULT_PORT, DEFAULT_TIME_LOGIN, DEFAULT_TIME_TURN, true); //Default verboso.
                started = true;
            }

            if (args.length == 4 && args[0].equals("-v")) {
                new Server(Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]), true); //Verboso con parametri dell'utente.
                started = true;
            }

            if(args.length == 3) {
                new Server(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]), false);
                started = true;
            }

            if(!started)
                Server.printHelp();
        }

    }

}
