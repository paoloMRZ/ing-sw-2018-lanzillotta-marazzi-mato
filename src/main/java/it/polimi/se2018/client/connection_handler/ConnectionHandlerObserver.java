package it.polimi.se2018.client.connection_handler;

/**
 * Questa interfaccia deve essere implementata da quelle classi che devono essere notificate dal connection handler
 * ogni volta che arriva un messaggio.
 *
 * @author Marazzi Paolo
 */
public interface ConnectionHandlerObserver {


    /**
     * Metodo tramite il quale il connetion handler notifica i suoi osservatori.
     * @param message messaggio ricevuto che deve essere passato all'osservatore.
     */
    void NetworkRequest(String message);
}
