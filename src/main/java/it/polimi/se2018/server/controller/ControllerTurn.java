package it.polimi.se2018.server.controller;

import it.polimi.se2018.server.model.Table;

public class ControllerTurn {
    private Table lobby;
    private String turnOf;
    private int numbOfPlayers;
    private int round;


    public ControllerTurn(Table LOBBY){
        this.lobby=LOBBY;
    }

    public String getTurn(){
        return turnOf;
    }

    public int getRound() {
        return round;
    }

}
