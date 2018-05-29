package it.polimi.se2018.server.controller;

import it.polimi.se2018.server.exceptions.InvalidHowManyTimes;
import it.polimi.se2018.server.exceptions.InvalidValueException;
import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.server.model.Table;

import java.util.ArrayList;

public class ControllerTurn {
    private Table lobby;

    private int round;
    private String turnOf=null;


    private int caller=0;
    private int numbOfPlayers;

    private ArrayList<String> orderOfTurning;
//gestione delle fasi
    private boolean upDown=true;
    private boolean andata=true;
    private boolean started=false;

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

    public ArrayList<String> getOrderOfTurning() {
        return orderOfTurning;
    }

    public void setRound() throws InvalidHowManyTimes {
        if(!started){
            this.round=1;
            this.orderOfTurning=new ArrayList<>();
            turnOf=lobby.callPlayerByNumber(0).getName();
        }
        else{
            round=round+1;
            this.orderOfTurning=new ArrayList<>();
            setTurn();
        }

    }

    public void setTurn() throws InvalidHowManyTimes {

        turnOf=lobby.callPlayerByNumber(caller).getName();
        recorder(turnOf);
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
    private void recorder(String name){
        if(!orderOfTurning.contains(name)) orderOfTurning.add(name);
    }
}
