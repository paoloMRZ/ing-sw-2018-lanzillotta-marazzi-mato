package it.polimi.se2018.server.Network;

import java.net.Socket;

public class ConnectionSocket implements Connection {


    private Socket socket;

    public ConnectionSocket(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void send(String mex) {

    }

    @Override
    public String receive() {
        return null;
    }
}
