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

public class SocketLoginRoutine implements Runnable {


    private Socket socket;
    private Lobby lobby = Lobby.factoryLobby();

    private BufferedReader reader;
    private OutputStreamWriter out;

    SocketLoginRoutine(Socket socket) {
        this.socket = socket;
    }


    private void sendMessageAndCloseConnection(String message) throws IOException {

        out.write(message);
        out.flush();

        reader.close();
        out.close();
        socket.close();
    }

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
