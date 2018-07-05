package it.polimi.se2018.server.network.fake_client;

/**
 * Questa interfaccia deve essere implementata dalle classi che intendono osservare un fake client.
 *
 * @author Marazzi Paolo.
 */

public interface FakeClientObserver {

    /**
     * Metodo richiamato dal fake client per notificarre i suoi osservatori del messaggio appena ricevuto.
     * @param message messaggio da notificare.
     */
    void notifyFromFakeClient(String message);
}
