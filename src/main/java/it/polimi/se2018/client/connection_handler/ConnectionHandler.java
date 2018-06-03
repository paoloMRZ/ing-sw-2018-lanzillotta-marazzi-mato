package it.polimi.se2018.client.connection_handler;
/**
 * La classe ha il compito di gestire la connessione tra client e server in base alla tecnologia di comunicazione scelta dal client.
 * @author Marazzi Paolo
 */
public abstract class ConnectionHandler  {
    /**
     *  Il metodo receiveNotify la view di un evento tramite un messaggio.
     * @param message messaggio da inviare alla view.
     */
    public void notifica(String message){
        //Questo metodo serve a gestire i messaggi mandati dal server.

        System.out.println("\n[#]Ho ricevuto il messaggio: " + message + "\n");
        //TODO questo metodo non si limita a stampare a schermo ma ha il compito di creare eventi da passsare alla view.
    }

    /**
     * La view receiveNotify il server di un evento tramite un messaggio.
     * @param message messaggio da inviare al server.
     */
    public abstract void sendToServer(String message);
}
