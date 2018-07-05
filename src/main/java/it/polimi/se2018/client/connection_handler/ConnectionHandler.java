package it.polimi.se2018.client.connection_handler;

/**
 * La classe ha il compito di gestire la connessione tra client e server in base alla tecnologia di comunicazione scelta dal client.
 * @author Marazzi Paolo
 */
public abstract class ConnectionHandler  {

    private ConnectionHandlerObserver view;
    private String nickname;

    /**
     * Costruttore della classe.
     *
     * @param view un qualsiasi oggetto che implementa l'interfaccia 'ConnectionHandlerObserver'.
     * @param nickname nickname scelto per connettersi al server.
     */
    public ConnectionHandler(ConnectionHandlerObserver view, String nickname){
        this.view = view;
        this.nickname = nickname;
    }

    /**
     *  Questo metodo richiama il metodo apposito della view per passarle il messaggio appena ricevuto dal server.
     * @param message messaggio da inviare alla view.
     */
    void notifica(String message){
        view.NetworkRequest(message);
    }

    /**
     * Tramite questo metodo la view passa al connection handler il messaggio da inviare al server.
     * Il connection handler procede con l'invio del messaggio.
     *
     * @param message messaggio da inviare al server.
     */
    public abstract void sendToServer(String message);

    /**
     * Il metodo restituisce il nickname del giocatore di cui sta gestendo la connessione.
     * @return nickname del giocatore.
     */
    public String getNickname(){
        return nickname;
    }
}