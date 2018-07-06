package it.polimi.se2018.server.network.implementation;

import org.fusesource.jansi.AnsiConsole;

import java.io.IOException;
import java.net.ServerSocket;
import java.security.InvalidParameterException;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * La classe gestisce tutte le richieste di connessione che avvengono sulla socket aperta dal server.
 *
 * @author Marazzi Paolo
 */
public class ServerImplementationSocket implements Runnable {

    private boolean isOpen = true;
    private ServerSocket serverSocket;


    /**
     * Costruttore della classe. Ha il compito di aprire la socket.
     *
     * @param port porta su cui aprire la socket.
     * @throws IOException Viene lanciata in caso di errore di lettura/scrittura dal buffer della socket.
     */
    public ServerImplementationSocket(int port) throws IOException {

        if(port > 0) {
            serverSocket = new ServerSocket(port);
        }else
            throw new InvalidParameterException();
    }


    /**
     * Loop di gestione delle connessioni sulla socket che deve essere lanciato su un thread dedicato.
     * Per ogni nuova connessione viene lanciato un thread che gestisce il login del nuovo giocatore.
     */
    @Override
    public void run() {


        while (isOpen) {

            try {
                new Thread(new SocketLoginRoutine(serverSocket.accept())).start(); //Appena si connette un client avvio una routine di connessione su un thread dedicato.
            } catch (IOException e) {
                isOpen = false;
                AnsiConsole.out.println(ansi().fgRed().a("[*] ERRORE di IO, chiusura della socket").fgDefault());
            }

        }

    }

    /**
     * Il metodo chiude la socket e interrompe il loop di lettura di essa.
     */
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