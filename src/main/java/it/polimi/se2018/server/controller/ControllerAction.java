package it.polimi.se2018.server.controller;


import it.polimi.se2018.server.exceptions.InvalidCellException;
import it.polimi.se2018.server.exceptions.InvalidValueException;
import it.polimi.se2018.server.exceptions.invalid_cell_exceptios.InvalidColorException;
import it.polimi.se2018.server.exceptions.invalid_cell_exceptios.InvalidShadeException;
import it.polimi.se2018.server.exceptions.invalid_cell_exceptios.NotEmptyCellException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidCoordinatesException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidSomethingWasNotDoneGood;
import it.polimi.se2018.server.model.Table;
import it.polimi.se2018.server.model.dice_sachet.Dice;
import it.polimi.se2018.server.model.reserve.Reserve;

import javax.naming.spi.ResolveResult;

public class ControllerAction {
    private Table lobby;
    private Dice holdingADiceMoveInProgress;


    public ControllerAction(Table LOBBY){
        this.lobby=LOBBY;
    }


    public void workOnSide(String name,Dice d, int row, int col)throws InvalidValueException, InvalidCellException {
        lobby.callPlayerByName(name).putDice(d,row,col);
    }
    public void workOnSideIgnoreColor(String name,int oldRow, int oldCol,int newRow, int newCol)throws InvalidValueException, InvalidCellException{
        lobby.callPlayerByName(name).putDiceIgnoreColor(oldRow,oldCol,newRow,newCol);
    }
    public void workOnSideIgnoreValue(String name,int oldRow, int oldCol,int newRow, int newCol)throws InvalidValueException, InvalidCellException{
        lobby.callPlayerByName(name).putDiceIgnoreValue(oldRow,oldCol,newRow,newCol);
    }

    public void putNoNeighbours(String name, int die, int row, int col) throws InvalidValueException, InvalidShadeException, NotEmptyCellException, InvalidColorException {
        Dice tmp= lobby.pickFromReserve(die);
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
        this.holdingADiceMoveInProgress=d;
    }
    public Dice getHoldingADiceMoveInProgress(){//todo di fretta riguardare se giusto
        Dice tmp= holdingADiceMoveInProgress;
        holdingADiceMoveInProgress=null;
        return tmp;
    }
    public void responder(String typeResponse, String message, String content ){//da finire per le risposte
        switch(typeResponse){
            case "SuccessColor":
                //todo segnalare successColor alla fakeview
            case "SuccesValue":
                //todo
        }
    }
    public Dice extractDieAgain(Dice die){
        lobby.getDiceSachet().reput(die);
        return lobby.getDiceSachet().getDiceFromSachet();
    }

}
