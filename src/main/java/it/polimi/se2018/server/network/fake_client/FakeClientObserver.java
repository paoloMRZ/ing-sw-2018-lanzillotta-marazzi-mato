package it.polimi.se2018.server.network.fake_client;

/**
 * Questa interfaccia deve essere implementata dalle classi che intendono osservare un fake client.
 *
 * @author Marazzi Paolo.
 */

public interface FakeClientObserver {
    void notifyFromFakeClient(String message);
}
