package it.polimi.se2018.server.controller;


import it.polimi.se2018.server.events.HookMessage;
import it.polimi.se2018.server.events.SimpleMove;
import it.polimi.se2018.server.events.UpdateReq;
import it.polimi.se2018.server.events.responses.*;
import it.polimi.se2018.server.events.tool_mex.Activate;
import it.polimi.se2018.server.exceptions.InvalidCellException;
import it.polimi.se2018.server.exceptions.InvalidValueException;
import it.polimi.se2018.server.exceptions.invalid_cell_exceptios.InvalidColorException;
import it.polimi.se2018.server.exceptions.invalid_cell_exceptios.InvalidShadeException;
import it.polimi.se2018.server.exceptions.invalid_cell_exceptios.NotEmptyCellException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidCoordinatesException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidSomethingWasNotDoneGood;
import it.polimi.se2018.server.model.Table;
import it.polimi.se2018.server.model.card.card_utensils.Utensils;
import it.polimi.se2018.server.model.dice_sachet.Dice;
import it.polimi.se2018.server.model.reserve.Reserve;


import java.util.ArrayList;

public class ControllerAction {
    private final Table lobby;

    private final Controller controller;


    public ControllerAction(Table LOBBY,Controller controller){
        this.lobby=LOBBY;
        this.controller=controller;
    }

///////////////////////////////////////////////////////////////////////


    public void workOnSide(String name,Dice d, int row, int col)throws InvalidValueException, InvalidCellException {
        lobby.callPlayerByName(name).putDice(d,row,col);
    }
    public void workOnSideIgnoreColor(String name,int oldRow, int oldCol,int newRow, int newCol)throws InvalidValueException, InvalidCellException{
        lobby.callPlayerByName(name).putDiceIgnoreColor(oldRow,oldCol,newRow,newCol);
    }
    public void workOnSideIgnoreValue(String name,int oldRow, int oldCol,int newRow, int newCol)throws InvalidValueException, InvalidCellException{
        lobby.callPlayerByName(name).putDiceIgnoreValue(oldRow,oldCol,newRow,newCol);
    }

    public void putNoNeighbours(String name, int die, int row, int col) throws InvalidValueException, InvalidShadeException, NotEmptyCellException, InvalidColorException, InvalidSomethingWasNotDoneGood {
        Dice tmp= pickFromReserve(die);
        lobby.callPlayerByName(name).putWithoutDicesNear(row,col,tmp);
    }
    //si poteva scrivere usando wokOnSide ma ho voluto rendere ciascun metodo indipendente
    public void moveStuffOnSide(String name,int oldRow, int oldCol,int newRow, int newCol) throws InvalidValueException, InvalidCellException {
        lobby.callPlayerByName(name).putDice(lobby.callPlayerByName(name).sidePick(oldRow,oldCol),newRow,newCol);
    }

    public Reserve getReserve(){
        return lobby.getReserve();
    }

    public void putBackInReserve(){
        Reserve toStoreAgain=lobby.getReserve();
        toStoreAgain.put(getHoldingADiceMoveInProgress());
        lobby.setReserve(toStoreAgain);
    }
    public void putBackInReserve(Dice D){
        Reserve toStoreAgain=lobby.getReserve();
        toStoreAgain.put(D);
        lobby.setReserve(toStoreAgain);
    }
    public Dice pickFromReserve(int whichOne) throws InvalidValueException, InvalidSomethingWasNotDoneGood {
        try {
            return lobby.pickFromReserve(whichOne);
        }
        catch(IndexOutOfBoundsException  e){
            throw new InvalidValueException();
        }
        catch(NullPointerException e){
            throw new InvalidSomethingWasNotDoneGood();
        }
    }
    public void playerActivatedCard(String name)throws InvalidValueException{
        lobby.callPlayerByName(name).setDidPlayCard();
    }

    public void resettingReserve(Reserve reserve){
        lobby.setReserve(reserve);
    }
    public Dice takeFromGrid(int onGrid,int inGrid) throws InvalidValueException {
        return lobby.getRoundGrid().pick(onGrid,inGrid);
    }
    public void putOnGrid(int onGrid,Dice d) throws InvalidValueException {
        lobby.getRoundGrid().put(onGrid,d);
    }
    public Dice pickOnGrid(int onGrid,int inGrid) throws InvalidValueException {
        return lobby.getRoundGrid().pick(onGrid,inGrid);
    }


    public Dice takeALookToDie(String name, int row, int col) throws InvalidValueException {
        return lobby.callPlayerByName(name).showSelectedCell(row,col).showDice();
    }
    public Dice takeALookToDieInGrid(int onBox, int inBox) throws InvalidValueException {
        return lobby.getRoundGrid().show(onBox,inBox);
    }

    public void setHoldingADiceMoveInProgress(Dice d){
        lobby.setHoldingADiceMoveInProgress(d);
    }

    public Dice getHoldingADiceMoveInProgress(){
        return lobby.getHoldingADiceMoveInProgress();
    }


    public Dice extractDieAgain(Dice die){
        lobby.getDiceSachet().reput(die);
        return lobby.getDiceSachet().getDiceFromSachet();
    }

    public int peopleCounter(){
        return lobby.peopleCounter();
    }


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void simpleMove(SimpleMove move){
        try{
            String dude = move.getPlayer();
            Dice picked = pickFromReserve(move.getDiceIndex());
            ArrayList<Integer> coords = move.getCoord();
            int row = coords.get(0);
            int col = coords.get(1);

            workOnSide(dude, picked, row, col);
            //success yeeeeaaaaaaa
            lobby.setUpdateReserve();
            lobby.callPlayerByName(dude).setUpdateSide();
            lobby.responder().notifyObserver(new SuccessSimpleMove(dude));
        }
        catch(Exception e){
            String destination=lobby.callPlayerByItsHisTurn().getName();
            lobby.responder().notifyObserver(new ErrorSelection(destination));
        }
    }


    public void refresher(UpdateReq m){
        try{
        lobby.refresh(m);
        controller.getTurn().refresh(m);}
        catch (Exception e ){
            lobby.responder().notifyObserver(new
                    ErrorSomethingNotGood());
        }
    }
///////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////

///////////////////////////////////////////////////////////////////////////////////////////////
    public void hook(HookMessage message){
        lobby.responder().register(message.getObserver());
    }

}
