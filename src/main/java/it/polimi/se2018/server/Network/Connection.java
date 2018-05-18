package it.polimi.se2018.server.Network;

public interface Connection {

    public void send(String mex);
    public String receive();
}
