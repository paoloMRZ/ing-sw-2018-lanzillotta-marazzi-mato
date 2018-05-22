package it.polimi.se2018.server.Network;

import it.polimi.se2018.client.ClientInterface;

public interface ServerInterface {
    void add(ClientInterface clientInterface, String nickname);
}
