package it.polimi.se2018.server.network.implementation;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * La classe gestisce tutte le richieste di connessione che avvengono sulla socket aperta dal server.
 *
 * @author Marazzi Paolo
 */
public class ServerImplementationSocket implements Runnable {

    //TODO gestione della scelta della porta.
    private static final int PORT = 1234; //Porta della socket.

    private boolean isOpen = true;
    private ServerSocket serverSocket;


    /**
     * Costruttore della classe. Ha il compito di aprire la socket.
     */
    public ServerImplementationSocket() throws IOException {
        serverSocket = new ServerSocket(PORT);
    }


    /**
     * Loop di gestione delle connessioni sulla socket che deve essere lanciato su un thread dedicato.
     * Appena un client si connette il server si mette in attesa del suo nickname. Una volta ricevuto crea un fake client
     * dedicato al client appena connesso e prova ad inserirlo nella lobby. Se esiste già un client con quel nickname
     * la procedura d'inserimento fallisce, il client ne viene informato e la connessione viene chiusa.
     */
    @Override
    public void run() {


        while (isOpen) {

            try {
                new Thread(new SocketLoginRoutine(serverSocket.accept())).start(); //Appena si connette un client avvio una routine di connessione su un thread dedicato.
            } catch (IOException e) {
                isOpen = false;
                System.out.println("[*] ERRORE Socket chiusa, il server non può più accettare connessioni."); //TODO Stampare un messaggio più decente.
            }

        }

    }

    public void close(){

        try {
            serverSocket.close();
            isOpen = false;
        } catch (IOException e) {
            //Non serve gestire questa eccezione perchè se stiamo chiudendo il server significa che non si ha più
            //intenzione d'usarlo, di conseguenza non ci interessa se la procedura di chiusura non è andata a buon fine.
        }
    }
}