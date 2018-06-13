package it.polimi.se2018.server.network.fake_client;

public interface FakeClientObserver {
    void notifyFromFakeClient(String message);
}
