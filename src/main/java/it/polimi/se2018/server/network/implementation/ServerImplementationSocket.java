package it.polimi.se2018.server.network.implementation;

import it.polimi.se2018.server.exceptions.GameStartedException;
import it.polimi.se2018.server.exceptions.InvalidNicknameException;
import it.polimi.se2018.server.message.network.NetworkMessageCreator;
import it.polimi.se2018.server.network.Lobby;
import it.polimi.se2018.server.network.fake_client.FakeClient;
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

    private static final int PORT = 1234; //Porta della socket.

    private Lobby lobby;
    private ServerSocket serverSocket;

    private boolean isOpen = true;

    private OutputStreamWriter out = null;
    private BufferedReader reader = null;


    /**
     * Costruttore della classe. Ha il compito di aprire la socket.
     * @param lobby sala d'attesa in cui vengono inseriti tutti i client che superano la verifica del nickname.
     */
    public ServerImplementationSocket(Lobby lobby) throws IOException {
        if(lobby != null)
            this.lobby = lobby;

        serverSocket = new ServerSocket(PORT);
    }


    private void sendMessageAndClose(FakeClient fakeClient, String message){
        try {
            out.write(message); //viene notificata al client l'invalidità del nickname.
            out.flush();
            fakeClient.closeConnection(); //La connessione viene chiusa anche se la notifyFromFakeView del nickname non valido non è andata a buon fine.

        } catch (IOException e) {
            fakeClient.closeConnection(); //La connessione viene chiusa anche se la notifyFromFakeView del nickname non valido non è andata a buon fine.
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

        FakeClientSocket fakeClient = null;

        while(isOpen){
            try {
                Socket socket = serverSocket.accept();

                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new OutputStreamWriter(socket.getOutputStream());

                String nick = reader.readLine(); //Attendo che il client mi mandi il suo nickname.
                fakeClient = new FakeClientSocket(socket, lobby,nick ); //Creo il fake client associato al client appena connesso.


                lobby.add(fakeClient); //Provo ad aggiungerlo alla lobby.

                (new Thread(fakeClient)).start(); //Avvio il thread dedicato all'ascolto dei messaggi destinati al fake client.

            } catch (InvalidNicknameException e) {
                //Se il nickname non è valido viene sollevata questa eccezione dal metodo 'add' della classe lobby.
                sendMessageAndClose(fakeClient, NetworkMessageCreator.getConnectionErrorInvalidNickanmeMessage(fakeClient.getNickname()));

            } catch (GameStartedException e) {
                //Se la partita è già stata avviata ed il client che si è connesso non era stato congelato viene sollevata questa eccezione.
                sendMessageAndClose(fakeClient, NetworkMessageCreator.getConnectionErrorGameStartedMessage(fakeClient.getNickname()));

            } catch (IOException e) {
                if(fakeClient != null)
                    fakeClient.closeConnection();
            }
        }
    }


    public void close() throws IOException {
        isOpen = false;
        if(out!= null) out.close();
        if(reader!= null) reader.close();
        if(serverSocket!= null) serverSocket.close();
    }
}
