package it.polimi.se2018.client;

import it.polimi.se2018.server.network.rmi.ServerInterface;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class Client extends UnicastRemoteObject implements ClientInterface {


    public Client() throws RemoteException {
    }

    @Override
    public void send(String mex) throws RemoteException {
        if(mex.equals("no")) run();

        //Aggiungiamo nel buffer il messaggio.
    }

    @Override
    public String receive() throws RemoteException {
        return null;

        //Leggo il messaggio dal buffer.
    }

    public void run(){
        Scanner in = new Scanner(System.in);
        ServerInterface server;

        System.out.println("Socket o RMI?");

        if(in.next().equals("RMI")){

            try {

                System.out.println("Dammi il tuo nick\n");

                server = (ServerInterface) Naming.lookup("//localhost/Server");

                server.add(this,in.next());

            } catch (RemoteException | MalformedURLException | NotBoundException e) {
                e.printStackTrace();
            }

        }

        if(in.next().equals("Socket")){


        }


    }

}
