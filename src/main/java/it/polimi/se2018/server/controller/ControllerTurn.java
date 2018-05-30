package it.polimi.se2018.server.controller;

import it.polimi.se2018.server.exceptions.InvalidHowManyTimes;
import it.polimi.se2018.server.exceptions.InvalidValueException;
import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.server.model.Table;

import java.util.ArrayList;
import java.util.List;

public class ControllerTurn {
    private Table lobby;

    private int round=0;
    private String turnOf=null;
    private String firstPlayer=null;


    private int caller=0;
    private int numbOfPlayers;

    private ArrayList<String> orderOfTurning = new ArrayList<>();
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

    public List<String> getOrderOfTurning() {
        return orderOfTurning;
    }

    public void setRound() throws InvalidHowManyTimes {
        firstPlayer=lobby.callPlayerByNumber(caller).getName();
        round=round+1;
    }

    public void setTurn() throws InvalidHowManyTimes {

        turnOf=lobby.callPlayerByNumber(caller).getName();
        recorder(turnOf);
        lobby.callPlayerByNumber(caller).reductor();

        if(turnOf.equals(firstPlayer) && !andata){
            andata = !andata;
            callerModifier();
            //todo si potrebbe mettere qui il setround da vedere
        }
        else callerModifier();

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
