package it.polimi.se2018.server.network.fake_client;

import it.polimi.se2018.server.exceptions.ConnectionCloseException;
import it.polimi.se2018.server.message.network_message.NetworkMessageCreator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;


/**
 * La classe rappresenta l'entità associata a lato server che si occupa di gestire la comunicazione con uno specifico client
 * che ha scelto di connettersi tramite socket.
 *
 * @author Marazzi Paolo
 */
public class FakeClientSocket extends FakeClient implements Runnable {

    private Socket socket;
    private OutputStreamWriter out;
    private BufferedReader reader;

    private boolean isFreezed; //Indica se il fake client è congelato.

    /**
     * Costruttore della classe.
     *
     * @param socket socket del client di cui la classe deve gestire la comunicazione.
     * @param nickname nickname scelto dal giocatore.
     */
    public FakeClientSocket(Socket socket, String nickname) throws IOException {
        super(nickname); //Richiamo il costruttore padre.

        if(socket != null){
            this.isFreezed = false;
            this.socket = socket;
            this.out = new OutputStreamWriter(socket.getOutputStream());
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }
    }

    /**
     * Loop di ascolto sulla socket che deve essere lancito su un thread dedicato.
     * Appene viene ricevuto un messaggio sulla socket la classe lo notifyFromFakeView alla lobby che si occuperà di
     * passare l'informazione ai destinatari.
     *
     * Nel caso in cui viene ricevuto un messaggio di chiusura della comunicazione, oltre a notificarlo alla lobby, viene interrotto il loop.
     */
    @Override
    public void run() {

        while (!isFreezed) {
            try {

                String message;

                message = reader.readLine();

                if (message != null)
                    lobby.notifyFromFakeClient(message); //Notifico la lobby del messaggio ricevuto.

            } catch (IOException e) {
                isFreezed = true;
                lobby.notifyFromFakeClient(NetworkMessageCreator.getClientDisconnectMessage(super.getNickname()));
            }
        }

        //Se il loop si interrompe chiudo la socket.
        try {
            reader.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            //ignoro
        }
    }

    /**
     * Il metodo aggiorna il client inviandogli un messaggio.
     * La comunicazione col client avviene tramite socket.
     * @param message messaggio da inviare al client.
     */
    @Override
    public void update(String message) throws ConnectionCloseException {
        if(!isFreezed) {
            try {
                out.write(message);
                out.flush();
            } catch (IOException e) {
                throw new ConnectionCloseException();
            }
        }
    }

    /**
     * Il metodo chiude la connessione col client inviandogli un messaggio di notifica e chiudendo la socket.
     */
    @Override
    public void closeConnection() {
        try {
            isFreezed = true; //Interrompo il loop di lettura.
            
            out.close(); //Chiudo il buffer di scrittura.
            reader.close(); //Chiudo il buffer di lettura.
            socket.close(); //Chiudo la socket.
        } catch (IOException e) {
            // Il server si disinteressa se la chiusura della connessione non è andata a buon fine perchè da questo momento
            // in poi non comunicherà più tramite questa socket.
        }
    }

    /**
     * Restituisce un booleano che indica se il fake client che congelato.
     * @return true se il fake client è congelato.
     */
    @Override
    public boolean isFreezed() {
        return isFreezed;
    }
}
