package it.polimi.se2018.server.network.fake_client;

import it.polimi.se2018.server.network.Lobby;
import it.polimi.se2018.server.network.fake_client.FakeClient;

import java.io.*;
import java.net.Socket;

/**
 * La classe rappresenta l'entità associata a lato server che si occupa di gestire la comunicazione con uno specifico client
 * che ha scelto di connettersi tramite socket.
 *
 * @author Marazzi Paolo
 */
public class FakeClientSocket extends FakeClient implements Runnable {

    private Socket socket;

    /**
     * Costruttore della classe.
     *
     * @param socket socket del client di cui la classe deve gestire la comunicazione.
     * @param lobby stanza di gioco.
     * @param nickname nickname scelto dal giocatore.
     */
    public FakeClientSocket(Socket socket, Lobby lobby, String nickname){
        super(lobby, nickname); //Richiamo il costruttore padre.

        if(socket != null){
            this.socket = socket;
        }
    }

    /**
     * Loop di ascolto sulla socket che deve essere lancito su un thread dedicato.
     * Appene viene ricevuto un messaggio sulla socket la classe lo notifica alla lobby che si occuperà di
     * passare l'informazione ai destinatari.
     *
     * Nel caso in cui viene ricevuto un messaggio di chiusura della comunicazione, oltre a notificarlo alla lobby, viene interrotto il loop.
     */
    @Override
    public void run() {

        try {
            System.out.println("[*]Thread di ascolto avviato.\n");

            boolean isOpen = true;
            BufferedReader reader= new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String message;

            while(isOpen){
                message = reader.readLine();
                if( message != null && !message.trim().equals("")) {
                    if (message.equals("bye")) { //TODO Sistemare il messaggio passato per la chiusura della connessione.
                        lobby.remove(super.getNickname()); //Notifio la lobby della richiesta di chiusura della connessione.
                        isOpen = false; //Interrompo il loop di lettura.
                    } else {
                        System.out.println("[*]Ho ricevuto un messaggio da " + this.getNickname());
                        lobby.notifica(message.concat("\n")); //Notifico la lobby del messaggio ricevuto.
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("[*]ERRORE di rete!"); //TODO Definire bene come gestire quest'eccezione.
        }
    }

    /**
     * Il metodo aggiorna il client inviandogli un messaggio.
     * La comunicazione col client avviene tramite socket.
     * @param message messaggio da inviare al client.
     */
    @Override
    public void update(String message) {
        try {
            OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream());
            out.write(message);
            out.flush();
        } catch (IOException e) {
            System.out.println("[*]ERRORE nell'aprire lo stream di scrittura!"); //TODO definire bene come gestire questa eccezione.
        }
    }

    /**
     * Il metodo chiude la connessione col client inviandogli un messaggio di notifica e chiudendo la socket.
     */
    @Override
    public void closeConnection() { //TODO Sistemare i messaggi.
        update("bye\n"); //Invio un messaggio al client che notifica la chiusura della connessione. TODO il messaggio usato è provvisorio.
        try {
            socket.close(); //chiudo la socket.
        } catch (IOException e) {
            System.out.println("[*]ERRORE chiusura socket!\n"); //TODO definire bene come gestire questa eccezione.
        }
    }
}
