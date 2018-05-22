package it.polimi.se2018.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote {
    void send(String mex) throws RemoteException;
    String receive() throws  RemoteException;
}
