package it.polimi.se2018.server.network.rmi;

import it.polimi.se2018.client.ClientInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote{
    void add(ClientInterface clientInterface, String nickname) throws RemoteException;
}
