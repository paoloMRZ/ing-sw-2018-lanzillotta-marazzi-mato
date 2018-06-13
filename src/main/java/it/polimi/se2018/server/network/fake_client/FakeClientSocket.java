package it.polimi.se2018.server.network.fake_client;

import it.polimi.se2018.server.exceptions.ConnectionCloseException;
import it.polimi.se2018.server.network.Lobby;

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

    private boolean isOpen; //Indica se la connessione è aperta, quindi se il loop di lettura "sta girando".

    /**
     * Costruttore della classe.
     *
     * @param socket socket del client di cui la classe deve gestire la comunicazione.
     * @param lobby stanza di gioco.
     * @param nickname nickname scelto dal giocatore.
     */
    public FakeClientSocket(Socket socket, Lobby lobby, String nickname) throws IOException {
        super(lobby, nickname); //Richiamo il costruttore padre.

        if(socket != null){
            this.isOpen = true;
            this.socket = socket;
            this.out = new OutputStreamWriter(socket.getOutputStream());
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }
    }

    /**
     * Loop di ascolto sulla socket che deve essere lancito su un thread dedicato.
     * Appene viene ricevuto un messaggio sulla socket la classe lo receiveNotify alla lobby che si occuperà di
     * passare l'informazione ai destinatari.
     *
     * Nel caso in cui viene ricevuto un messaggio di chiusura della comunicazione, oltre a notificarlo alla lobby, viene interrotto il loop.
     */
    @Override
    public void run() {

        try {

            String message;

            while(isOpen) {


                message = reader.readLine();

                if (message == null) { //Controllo se è caduta la connessione.
                    lobby.notifyFromFakeClient("/" + getNickname() + "/###/rete/?/disconnect\n");

                } else { //In tutti gli altri casi devo passare il messaggio al controllore.
                    lobby.notifyFromFakeClient((message.concat("\n"))); //Notifico la lobby del messaggio ricevuto.
                }
            }

        } catch (IOException e) {
            lobby.notifyFromFakeClient(("/" + getNickname() + "/###/rete/?/disconnect\n"));
        }
    }

    /**
     * Il metodo aggiorna il client inviandogli un messaggio.
     * La comunicazione col client avviene tramite socket.
     * @param message messaggio da inviare al client.
     */
    @Override
    public void update(String message) throws ConnectionCloseException {
        if(isOpen) {
            try {
                out.write(message);
                out.flush();
            } catch (IOException e) {
                throw new ConnectionCloseException();
            }
        }
    }

    /**
     * Il metodo chiude la connessione col client inviandogli un messaggio di receiveNotify e chiudendo la socket.
     */
    @Override
    public void closeConnection() {
        try {
            out.close(); //Chiudo il buffer di scrittura.
            reader.close(); //Chiudo il buffer di lettura.
            socket.close(); //Chiudo la socket.
            isOpen = false; //Interrompo il loop di lettura.
        } catch (IOException e) {
            isOpen = false;
        }
    }

    @Override
    public boolean isFreezed() {
        return !isOpen;
    }
}
