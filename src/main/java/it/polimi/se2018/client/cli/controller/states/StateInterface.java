package it.polimi.se2018.client.cli.controller.states;

/**
 * Interfaccia implementata da tutte le classi che rappresentano uno stato in cui la classe Cli si può trovare durante
 * la partita.
 */
public interface StateInterface {

    /**
     * Metodo di gestione dei valor immessi su stdin dall'utente.
     * @param request input dell'utente.
     * @return eventuale messaggio da mandare al server.
     */
    String handleInput(int request);

    /**
     * Metodo di gesione dei messaggi provenienti dal server.
     * @param request messaggio inviato dal server
     */
    void handleNetwork(String request);
}
