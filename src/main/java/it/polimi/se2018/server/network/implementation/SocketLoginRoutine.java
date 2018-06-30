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

    SocketLoginRoutine(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {


            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream());

            Lobby lobby = Lobby.factoryLobby();

            String nickname = reader.readLine();

            if (lobby.getNicknames().contains(nickname) && !lobby.isFreezedFakeClient(nickname)) { //Controllo se esiste qualcuno di non congelato connesso con lo steso nickname.
                out.write(NetworkMessageCreator.getConnectionErrorInvalidNickanmeMessage(nickname)); //nickname già usato.
                out.flush();

                reader.close();
                out.close();
                socket.close();
            } else {

                if (!lobby.isOpen()) {
                    out.write(NetworkMessageCreator.getConnectionErrorGameStartedMessage(nickname)); //partita già iniziata.
                    out.flush();

                    reader.close();
                    out.close();
                    socket.close();
                } else {
                    //Se tutto è ok creo un nuovo fake client e lo aggiungo alla lobby.

                    FakeClientSocket fakeClientSocket = new FakeClientSocket(socket,nickname); //Creo il fake client.
                    (new Thread(fakeClientSocket)).start(); //Avvio il thread lettura della socket.

                    lobby.add(fakeClientSocket); //Aggiungo il fake client alla lobby.
                }
            }
        } catch (IOException e) {
           // Non c'è bisgno d'effettuare nessuna oprazione. Infatti questa eccezzione può essere sollevata solo durante
           // la notifica di connessione non avvenuta. Nell'implementazione da me scelta il server si disinteressa se la
           // chiusura di una connessine non è andata a buon fine.
        }
    }

}
