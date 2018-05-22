package it.polimi.se2018.client;

import it.polimi.se2018.server.Network.ServerInterface;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class App {

    public static void main (String[] args){

        Scanner in = new Scanner(System.in);
        ServerInterface server = null;

        System.out.println("Socket o RMI?");

        if(in.next().equals("RMI")){

            try {

                System.out.println("Dammi il tuo nick\n");

                server = (ServerInterface) Naming.lookup("//localhost/Server");
                Client client = new Client();
                ClientInterface remoteRef = (ClientInterface) UnicastRemoteObject.exportObject(client, 0);
                server.add(remoteRef,in.next());

            } catch (RemoteException | MalformedURLException | NotBoundException e) {
                e.printStackTrace();
            }

        }

        if(in.next().equals("Socket")){


        }



    }
}
