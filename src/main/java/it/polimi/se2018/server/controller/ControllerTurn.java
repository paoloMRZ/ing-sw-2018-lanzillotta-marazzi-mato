package it.polimi.se2018.server.controller;

import it.polimi.se2018.server.exceptions.InvalidHowManyTimes;
import it.polimi.se2018.server.exceptions.InvalidValueException;
import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.server.model.Table;

public class ControllerTurn {
    private Table lobby;
    private String turnOf=null;
    private int numbOfPlayers;
    private int round;
    private int caller=0;
    private boolean upDown=true;


    public ControllerTurn(Table LOBBY){
        this.lobby=LOBBY;
    }

    public Player getTurn() throws InvalidValueException {
        return lobby.callPlayerByName(turnOf);
    }

    public int getRound() {
        return round;
    }

    public void setRound() throws InvalidHowManyTimes {
        turnOf=lobby.callPlayerByNumber(caller).getName();
        lobby.callPlayerByNumber(caller).reductor();

        if(caller==numbOfPlayers-1) upDown=false;
        if(caller==0) upDown=true;
        if(upDown) caller=caller+1;
        else caller=caller-1;
    }
}
