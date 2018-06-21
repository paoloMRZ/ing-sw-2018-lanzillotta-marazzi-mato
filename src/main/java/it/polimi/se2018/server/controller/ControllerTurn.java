package it.polimi.se2018.server.controller;

import it.polimi.se2018.server.events.EventMVC;
import it.polimi.se2018.server.events.responses.*;
import it.polimi.se2018.server.exceptions.InvalidHowManyTimes;
import it.polimi.se2018.server.exceptions.InvalidValueException;
import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.server.model.Table;
import it.polimi.se2018.server.timer.ObserverTimer;
import it.polimi.se2018.server.timer.SagradaTimer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ControllerTurn implements ObserverTimer {
    private final Table lobby;
    private final Controller controller;
    private final SagradaTimer sagradaTimer;

    private int round=0;
    private String turnOf=null;
    private String firstPlayer=null;


    private int caller=0;
    private int numbOfPlayers;

    private ArrayList<String> orderOfTurning = new ArrayList<>();
//gestione delle fasi
    private boolean setting=true;
    private boolean upDown=true;
    private boolean andata=true;
    private boolean started=false;
    private boolean timeIsOn=false;


    public ControllerTurn(Table table,Controller controller){
        this.lobby=table;
        this.controller=controller;
        this.numbOfPlayers=lobby.peopleCounter();;
        this.sagradaTimer=new SagradaTimer(60);
        sagradaTimer.add(this);
    }

    public Player getTurn(){
        try{
            return lobby.callPlayerByName(turnOf);
        }
        catch(InvalidValueException e){
            controller.getcChat().notifyObserver(new ErrorSomethingNotGood(e));
        }
        return null;
    }

    public List<String> getOrderOfTurning() {
        return orderOfTurning;
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////

    //il primo metodo a venire lanciato in questa classe
    public void setRound() throws Exception {

        firstPlayer = lobby.callPlayerByNumber(caller).getName();
        round = round + 1;
        controller.getcChat().notifyObserver(new UpdateM(turnOf,"round",String.valueOf(round)));
        if(!started) {
            started=true;
            setTurn();
        }
        else {
            isGameFinished();
            setTurn();
        }
    }

    private void setTurn() throws InvalidHowManyTimes, InvalidValueException {

        if(turnOf!=null) lobby.callPlayerByName(turnOf).setIsMyTurner();

        if(lobby.callPlayerByNumber(caller).canYouPlay()) {//determina se un giocatore è stato disconnesso
            turnOf = lobby.callPlayerByNumber(caller).getName();
            recorder(turnOf);
            lobby.callPlayerByNumber(caller).reductor();
            lobby.callPlayerByNumber(caller).setIsMyTurner();
            sagradaTimer.start();
            timeIsOn = true;
            controller.getcChat().notifyObserver(new UpdateM(turnOf,"turn",turnOf));
            //parte il timer
        }
        else closeTurn();
    }
    public void closeTurn(){
        //azione del timer quando scade il turno posso dividere in due parti
        //o quando messaggi mi dicono che ha già fatto le cose che deve fare da checker
        try{
            sagradaTimer.stop();
            timeIsOn=false;
            controller.getcChat().notifyObserver( new TimeIsUp(turnOf));
            if(!lobby.callPlayerByNumber(caller).getDidPlayDie() && !lobby.callPlayerByNumber(caller).getDidPlayCard()) lobby.callPlayerByNumber(caller).forget();

            if(!andata && turnOf.equals(firstPlayer) ){
                andata = true;
                callerModifier();
                setRound();
            }
            else {
                callerModifier();
                setTurn();
            }
        }
        catch (Exception e){
            controller.getcChat().notifyObserver(new ErrorSomethingNotGood(e));
        }


    }
//////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////
public void setThePlayers() throws InvalidValueException {
    try{
        lobby.preparePlayers();
    }
    catch(IOException e){
        controller.getcChat().notifyObserver(new ErrorSomethingNotGood(e));
    }
    anotherPlayer();
}

    private void anotherPlayer() throws InvalidValueException{

            turnOf = lobby.callPlayerByNumber(caller).getName();
            lobby.callPlayerByNumber(caller).setIsMyTurner();

            sagradaTimer.start();
            timeIsOn = true;

            lobby.callPlayerByName(turnOf).ask();
            //parte il timer

    }
    public void actualPlayerIsDone(){
        //azione del timer quando scade il turno posso dividere in due parti
        //o quando messaggi mi dicono che ha già fatto le cose che deve fare da checker
        try{
            sagradaTimer.stop();
            timeIsOn=false;
            controller.getcChat().notifyObserver( new TimeIsUp(turnOf));

            if(lobby.callPlayerByName(turnOf).getMySide()==null) lobby.callPlayerByNumber(caller).forgetForever();

            lobby.callPlayerByNumber(caller).setIsMyTurner();

            if(caller!=lobby.peopleCounter()-1 ){
                resetQualities();
            }
            else {
                callerModifier();
                anotherPlayer();
            }
        }
        catch (Exception e){
            controller.getcChat().notifyObserver(new ErrorSomethingNotGood(e));
        }
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////


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

    private void isGameFinished() throws Exception {
        if(round>10) controller.finalizeTheGame();
    }

    public boolean messageComingChecking(EventMVC m){
        try{
            if(!timeIsOn) return false;
            else{
                if(lobby.callPlayerByName(m.getPlayer()).getDidPlayDie()&&lobby.callPlayerByName(m.getPlayer()).getDidPlayCard()){
                    closeTurn();
                    return false;
                }
                else return true;
            }
        }
        catch(InvalidValueException e){
            controller.getcChat().notifyObserver(new ErrorSomethingNotGood(e));
            return  false;
        }

    }
    private void resetQualities() throws Exception {
        round=0;
        turnOf=null;
        firstPlayer=null;

        numbOfPlayers=lobby.peopleCounter();
        caller=0;
        orderOfTurning = new ArrayList<>();

        upDown=true;
        andata=true;
        started=false;
        timeIsOn=false;
        setting=false;
        setRound();
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void timerUpdate(){
        if(setting) actualPlayerIsDone();
        else closeTurn();
    }
    public void passTurn(PassTurn m){
        if(m.getPlayer().equals(turnOf)){
            if(setting) actualPlayerIsDone();
            else closeTurn();
        }


    }
}
