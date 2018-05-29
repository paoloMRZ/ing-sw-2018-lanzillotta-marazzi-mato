package it.polimi.se2018.server.controller;

import it.polimi.se2018.server.exceptions.InvalidHowManyTimes;
import it.polimi.se2018.server.exceptions.InvalidValueException;
import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.server.model.Table;

public class ControllerTurn {
    private Table lobby;
    private String turnOf=null;
    private int numbOfPlayers;
    private int round=1;
    private int caller=0;
    private boolean upDown=true;
    private boolean andata=true;


    public ControllerTurn(Table LOBBY){

        this.lobby=LOBBY;
        this.numbOfPlayers=lobby.peopleCounter();
    }

    public Player getTurn() throws InvalidValueException {
        return lobby.callPlayerByName(turnOf);
    }

    public int getRound() {
        return round;
    }

    public void setTurn() throws InvalidHowManyTimes {

        turnOf=lobby.callPlayerByNumber(caller).getName();
        lobby.callPlayerByNumber(caller).reductor();

        callerModifier();


    }
    private void callerModifier(){
        if(andata) {
            if (caller == 0) upDown = true;
            if (caller == numbOfPlayers - 1) upDown = true;
        }
        else{
            if (caller == 0) upDown = false;
            if (caller == numbOfPlayers - 1) upDown = false;
        }
        if(upDown) caller=caller+1;
        else caller=caller-1;
        if(caller<0){
            caller=0;
            andata=true;
        }
        if(caller>numbOfPlayers-1){
            caller=numbOfPlayers-1;
            andata=false;
        }
    }
}
