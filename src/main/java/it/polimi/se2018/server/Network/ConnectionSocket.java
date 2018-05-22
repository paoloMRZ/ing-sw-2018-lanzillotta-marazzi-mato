package it.polimi.se2018.server.Network;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ConnectionSocket implements Connection {


    private Socket socket;

    public ConnectionSocket(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void send(String mex) {
        try {
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeChars(mex);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String receive() {
        try {
            Scanner in = new Scanner(socket.getInputStream());
            return in.next();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
