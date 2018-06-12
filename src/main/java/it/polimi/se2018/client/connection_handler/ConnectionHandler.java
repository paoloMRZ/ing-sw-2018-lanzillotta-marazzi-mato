package it.polimi.se2018.client.connection_handler;

import it.polimi.se2018.graphic.InitWindow;

/**
 * La classe ha il compito di gestire la connessione tra client e server in base alla tecnologia di comunicazione scelta dal client.
 * @author Marazzi Paolo
 */
public abstract class ConnectionHandler  {

    private InitWindow view;
    private String nickname;

    public ConnectionHandler(InitWindow view, String nickname){
        this.view = view;
        this.nickname = nickname;
    }

    /**
     *  Il metodo receiveNotify la view di un evento tramite un messaggio.
     * @param message messaggio da inviare alla view.
     */
    void notifica(String message){
        view.sendToView(message);
    }

    /**
     * La view receiveNotify il server di un evento tramite un messaggio.
     * @param message messaggio da inviare al server.
     */
    public abstract void sendToServer(String message);

    public String getNickname(){
        return nickname;
    }
}