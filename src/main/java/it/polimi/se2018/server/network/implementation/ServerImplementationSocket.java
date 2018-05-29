package it.polimi.se2018.server.network.implementation;

import it.polimi.se2018.server.exceptions.InvalidNicknameException;
import it.polimi.se2018.server.network.Lobby;
import it.polimi.se2018.server.network.fake_client.FakeClientSocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * La classe gestisce tutte le richieste di connessione che avvengono sulla socket aperta dal server.
 *
 * @author Marazzi Paolo
 */
public class ServerImplementationSocket implements Runnable {

    private final int PORT = 1234; //Porta della socket.

    private Lobby lobby;
    private ServerSocket serverSocket;

    /**
     * Costruttore della classe. Ha il compito di aprire la socket.
     * @param lobby sala d'attesa in cui vengono inseriti tutti i client che superano la verifica del nickname.
     */
    public ServerImplementationSocket(Lobby lobby){
        if(lobby != null)
            this.lobby = lobby;

        System.out.println("[*]Apro la socket.");
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            System.out.println("[*]ERRORE nell'apertura della socket!");
        }
    }

    /**
     * Loop di gestione delle connessioni sulla socket che deve essere lanciato su un thread dedicato.
     * Appena un client si connette il server si mette in attesa del suo nickname. Una volta ricevuto crea un fake client
     * dedicato al client appena connesso e prova ad inserirlo nella lobby. Se esiste già un client con quel nickname
     * la procedura d'inserimento fallisce, il client ne viene informato e la connessione viene chiusa.
     */
    @Override
    public void run() {

        System.out.println("[*]Mi metto in attesa di connessioni sulla socket.\n");

        boolean isOpen = true;
        FakeClientSocket fakeClient = null;
        OutputStreamWriter out = null;
        BufferedReader reader = null;

        while(isOpen){
            try {
                Socket socket = serverSocket.accept(); //Accetto la connessione dal client.
                System.out.println("\n[*]Si è connesso un client sulla socket.");


                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new OutputStreamWriter(socket.getOutputStream());

                String nick = reader.readLine(); //Attendo che il client mi mandi il suo nickname.
                fakeClient = new FakeClientSocket(socket, lobby,nick ); //Creo il fake client associato al client appena connesso.


                lobby.add(fakeClient); //Provo ad aggiungerlo alla lobby.
                out.write("OK\n"); //Se l'inserimento va a buon fine lo notifico al client. TODO Il messaggio di notifica è provvisorio.,
                out.flush();
                System.out.println("[*]Aggiunto alla lobby.");

                (new Thread(fakeClient)).start(); //Avvio il thread dedicato all'ascolto dei messaggi destinati al fake client.

            } catch (InvalidNicknameException e) {
                //Se il nickname non è valido viene sollevata questa eccezione dal metodo 'add' della classe lobby.
                try {
                    System.out.println("[*]Connessione rifiutata");
                    out.write("NO\n"); //viene notificata al client l'invalidità del nickname. TODO Il messaggio di notifica è privvisorio.
                    out.flush();

                } catch (IOException e1) {
                    System.out.println("[*]ERRORE nella connessione su socket col client."); //TODO definire come gestire questa eccezione.
                }
                fakeClient.closeConnection(); //La connessione viene chiusa anche se la notifica del nickname non valido non è andata a buon fine.
            }
            catch (IOException e) {
                System.out.println("[*]ERRORE nella connessione su socket col client."); //TODO definire come gestire questa eccezione.
            }
        }
    }
}
