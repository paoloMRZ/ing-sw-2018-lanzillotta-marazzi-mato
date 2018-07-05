package it.polimi.se2018.server.network.implementation;

import it.polimi.se2018.server.exceptions.GameStartedException;
import it.polimi.se2018.server.exceptions.InvalidNicknameException;
import it.polimi.se2018.server.message.network_message.NetworkMessageCreator;
import it.polimi.se2018.server.network.Lobby;
import it.polimi.se2018.server.network.fake_client.FakeClientSocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * La classe gestisce la procedura di login per una nuova connessione socket.
 *
 * @author Marazzi Paolo.
 */
public class SocketLoginRoutine implements Runnable {


    private Socket socket;
    private Lobby lobby = Lobby.factoryLobby();

    private BufferedReader reader;
    private OutputStreamWriter out;

    /**
     * Costruttore della classe.
     *
     * @param socket socket del client di cui gestire il login.
     */
    SocketLoginRoutine(Socket socket) {
        this.socket = socket;
    }

    /**
     * Il metodo invia un messaggio sulla socket dopo di che chiude la connessione.
     *
     * @param message messaggio da inviare.
     * @throws IOException sollevata in caso di errori di scrittura sulla socket.
     */
    private void sendMessageAndCloseConnection(String message) throws IOException {

        out.write(message);
        out.flush();

        reader.close();
        out.close();
        socket.close();
    }

    /**
     * Si tenta di aggiungere alla lobby il client appena connesso (o meglio, il suo relativo fake client).
     * Se non vengono sollevate eccezioni, cioè se il client si è connesso correttamente, viene avviato un thread che si occupa di stare in ascolto sulla
     * socket del client appena connesso.
     *
     * Se viene sollevata un'eccezione che notifica che la connessione non è andata abuon fine viene inviato al client il relativo messaggio di errore
     * e viene chiusa la socket.
     *
     * @param fakeClientSocket fake client da inserire nella lobby.
     * @throws IOException viene sollevata in caso di errori di scrittura/lettura sulla socket.
     */
    private void addToLobby(FakeClientSocket fakeClientSocket) throws IOException {

        try {
            lobby.add(fakeClientSocket); //Aggiungo il fake client alla lobby.

            (new Thread(fakeClientSocket)).start(); //Se sono stato agginto avvio il thread lettura della socket.

        } catch (InvalidNicknameException e) { //Se il nickname è già in uso da un giocatore attivo lo notifico il client e chiudo la connessione.
            sendMessageAndCloseConnection(NetworkMessageCreator.getConnectionErrorInvalidNickanmeMessage(fakeClientSocket.getNickname()));
        } catch (GameStartedException e) { //Se la partita è già iniziata lo notifico al client e chiudo la connessione.
            sendMessageAndCloseConnection(NetworkMessageCreator.getConnectionErrorGameStartedMessage(fakeClientSocket.getNickname()));
        }
    }

    /**
     * Routine di login da avviare su un thread dedicato.
     *
     * Si riceve il nickname scelto dal giocatore, viene creato il relativo fake client e si tenta di aggiungerlo alla lobby.
     *
     * Se l'inserimento va a buon fine si lancia il thread di lettura della socket del client appena connesso.
     * In caso contrario si notifica l'errore al client tramite un messaggio dedicato e si chiude la socket.
     */

    @Override
    public void run() {
        try {


            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new OutputStreamWriter(socket.getOutputStream());

            String nickname = reader.readLine(); //Ricevo il nickname dal giocatore.

            FakeClientSocket fakeClientSocket = new FakeClientSocket(socket, nickname); //Creo il fake client.

            addToLobby(fakeClientSocket);

        } catch (IOException e) {
            // Non c'è bisgno d'effettuare nessuna oprazione. Infatti questa eccezzione può essere sollevata solo durante
            // la notifica di connessione non avvenuta. Nell'implementazione da me scelta il server si disinteressa se la
            // chiusura di una connessine non è andata a buon fine.
        }
    }

}
