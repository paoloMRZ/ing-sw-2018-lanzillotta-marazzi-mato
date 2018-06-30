package it.polimi.se2018.server.network.implementation;

import it.polimi.se2018.server.message.network_message.NetworkMessageCreator;
import it.polimi.se2018.server.network.Lobby;
import it.polimi.se2018.server.network.fake_client.FakeClientSocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class SocketLoginRoutine implements Runnable {


    private Socket socket;
    private Lobby lobby = Lobby.factoryLobby();

    private BufferedReader reader;
    private OutputStreamWriter out;

    SocketLoginRoutine(Socket socket) {
        this.socket = socket;
    }

    private void addToLobby(String nickname) throws IOException {

        FakeClientSocket fakeClientSocket = new FakeClientSocket(socket, nickname); //Creo il fake client.
        (new Thread(fakeClientSocket)).start(); //Avvio il thread lettura della socket.

        lobby.add(fakeClientSocket); //Aggiungo il fake client alla lobby.
    }

    private void sendMessageAndCloseConnection(String message) throws IOException {

        out.write(message);
        out.flush();

        reader.close();
        out.close();
        socket.close();
    }

    @Override
    public void run() {
        try {


            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new OutputStreamWriter(socket.getOutputStream());


            String nickname = reader.readLine(); //Ricevo il nickname dal giocatore.

            if (lobby.getNicknames().contains(nickname)) { //Controllo se esiste qualcuno connesso con lo steso nickname.

                if (lobby.isFreezedFakeClient(nickname)) { //Se il gioctore con lo stesso nickanme è congelato allora aggiono quel giocatore con questa connessione.

                    //In pratica per aggiornare la connessine del giocatore basta aggiungerlo alla lobby.
                    addToLobby(nickname);

                } else { //Se il nickname è usato da un giocatore attivo notifico il client che sta tendando di connetersi con un messaggio di errore e chiudo la connessione.
                    sendMessageAndCloseConnection(NetworkMessageCreator.getConnectionErrorInvalidNickanmeMessage(nickname));
                }

            }else { //Se nessun giocatore connesso possiede il nickname ricevuto controllo se la lobby è aperta (cioè se la partita è già iniziata).


                if (!lobby.isOpen()) { //Se la partita è già iniziata (= lobby chiusa) notifico il client con un messaggio di errore e chiudo la connessione.
                    sendMessageAndCloseConnection(NetworkMessageCreator.getConnectionErrorGameStartedMessage(nickname));

                } else { //Se la partita non è ancora iniziata aggiungo il client alla lobby.

                    //Se tutto è ok creo un nuovo fake client e lo aggiungo alla lobby.
                    addToLobby(nickname);
                }
            }

        } catch (IOException e) {
            // Non c'è bisgno d'effettuare nessuna oprazione. Infatti questa eccezzione può essere sollevata solo durante
            // la notifica di connessione non avvenuta. Nell'implementazione da me scelta il server si disinteressa se la
            // chiusura di una connessine non è andata a buon fine.
        }
    }

}
