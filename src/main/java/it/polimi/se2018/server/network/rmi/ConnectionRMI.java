package it.polimi.se2018.server.network.rmi;

import it.polimi.se2018.client.ClientInterface;
import it.polimi.se2018.server.network.Connection;

import java.rmi.RemoteException;
import java.util.LinkedList;

public class ConnectionRMI implements Connection {

    private ClientInterface client;
    private LinkedList<String> buffer;

    public ConnectionRMI(ClientInterface client){
        if(client != null)
            this.client = client;

        buffer = new LinkedList<>();
    }

    @Override
    public void send(String mex) {
        try {
            client.send(mex);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String receive() {
        return buffer.getFirst();
    }
}
