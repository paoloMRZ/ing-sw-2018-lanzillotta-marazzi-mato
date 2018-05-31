package it.polimi.se2018.client;


import it.polimi.se2018.client.connection_handler.ConnectionHandler;
import it.polimi.se2018.client.connection_handler.ConnectionHandlerRMI;
import it.polimi.se2018.client.connection_handler.ConnectionHandlerSocket;
import it.polimi.se2018.server.exceptions.InvalidNicknameException;

import java.util.Scanner;

public class Client {

    public static void main(String args[]){

        boolean isOpen = true;
        Scanner scanner = new Scanner(System.in);
        ConnectionHandler connectionHandler = null;
        String tmp = null;
        int scelta = 0;


        do {
            //Richiesta del tipo di connessione.
            System.out.println("[*]Che tipo di connessione vuoi usare?");
            System.out.println("[1] RMI");
            System.out.println("[2] Socket");
            System.out.print(">> ");
            scelta = scanner.nextInt();

            switch (scelta){
                case 1: {
                    System.out.println("[*]dammi nick");
                    String nick = scanner.next();
                    try {
                        connectionHandler = new ConnectionHandlerRMI(nick);
                    } catch (InvalidNicknameException e) {
                        System.out.println("NO");
                    }

                } break;

                case 2: {
                    System.out.println("[*]dammi nick");
                    String nick = scanner.next();
                    try {
                        connectionHandler = new ConnectionHandlerSocket(nick.concat("\n"));
                        (new Thread((ConnectionHandlerSocket)connectionHandler)).start();
                    } catch (InvalidNicknameException e) {
                        System.out.println("NO");
                    }
                } break;

                default: {
                    System.out.println("ERRORE valore inserito non accettabile!");
                    scelta = 0;
                }
            }

        }while(scelta == 0);
/*
        //Ciclo dedicato alla lettura da CLI.
        System.out.println("[*]Lancio il thread di lettura dallo standard input.\n");

        while(isOpen){
            System.out.print("\nDammi un messaggio da inviare >> ");
            tmp = scanner.nextLine();

            if(tmp != null && !tmp.trim().equals("")) {
                connectionHandler.sendToServer(tmp.concat("\n"));
            }
        }*/
    }
}
