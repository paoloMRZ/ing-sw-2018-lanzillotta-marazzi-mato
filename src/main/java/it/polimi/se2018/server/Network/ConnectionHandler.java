package it.polimi.se2018.server.Network;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ConnectionHandler implements Runnable {

    private Lobby lobby;
    private Socket socket;

    public ConnectionHandler(Lobby lobby, Socket socket) {
        this.lobby = lobby;
        this.socket = socket;
    }

    public void run(){

        try {
            Scanner in = new Scanner(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            String nick;

            out.writeChars("Ciao! Dammi il tuo nick\n");
            do{
                nick = in.next();

                if(lobby.getNicknameList().contains(nick) ){
                    out.writeChars("Questo nick non è disponibile, scegline un altro.\n");
                }
            }while(lobby.getNicknameList().contains(nick));

            lobby.add(new Client(nick, new ConnectionSocket(socket), null));
            out.writeChars("Sei stato messo in attesa\n");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
