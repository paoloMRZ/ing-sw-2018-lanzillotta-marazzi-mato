package it.polimi.se2018.client;

import it.polimi.se2018.server.network.rmi.ServerInterface;
import it.polimi.se2018.server.network.socket.ConnectionHandler;

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
        if(mex.equals("no")){
            System.out.println("Nickname già utilizzato!\n");
            nicknameRequest();
        }



        //Aggiungiamo nel buffer il messaggio.
    }

    @Override
    public String receive() throws RemoteException {
        return null;

        //Leggo il messaggio dal buffer.
    }


    public void nicknameRequest(){

        ServerInterface server;
        Scanner in = new Scanner(System.in);

        try {

            System.out.println("Dammi il tuo nick\n");

            server = (ServerInterface) Naming.lookup("//localhost/Server");

            server.add(this,in.next());

        } catch (RemoteException | MalformedURLException | NotBoundException e) {
            e.printStackTrace();
        }

    }

    public void run(){
        Scanner in = new Scanner(System.in);

        System.out.println("Socket o RMI?");

        if(in.next().equals("RMI")){

            nicknameRequest();
        }

        if(in.next().equals("Socket")){



        }


    }

}
