package it.polimi.se2018.client;

import java.rmi.RemoteException;

public class App {

    public static void main (String[] args){

        Client client;
        try {
            client = new Client();
            client.run();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }
}
