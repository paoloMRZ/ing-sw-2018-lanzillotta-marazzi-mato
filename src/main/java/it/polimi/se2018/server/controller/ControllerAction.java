package it.polimi.se2018.server.controller;


import it.polimi.se2018.server.exceptions.InvalidCellException;
import it.polimi.se2018.server.exceptions.InvalidValueException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidCoordinatesException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidSomethingWasNotDoneGood;
import it.polimi.se2018.server.model.Table;
import it.polimi.se2018.server.model.dice_sachet.Dice;
import it.polimi.se2018.server.model.reserve.Reserve;

public class ControllerAction {
    private Table lobby;


    public ControllerAction(Table LOBBY){
        this.lobby=LOBBY;
    }

    public void workOnSide(String name,Dice d, int row, int col)throws InvalidValueException, InvalidCellException, InvalidCoordinatesException {
        lobby.callPlayerByName(name).putDice(d,row,col);
    }
    public void workOnSideIgnoreColor(String name,int oldRow, int oldCol,int newRow, int newCol)throws InvalidValueException, InvalidCellException, InvalidCoordinatesException{
        lobby.callPlayerByName(name).putDiceIgnoreColor(oldRow,oldCol,newRow,newCol);
    }
    public void workOnSideIgnoreValue(String name,int oldRow, int oldCol,int newRow, int newCol)throws InvalidValueException, InvalidCellException, InvalidCoordinatesException{
        lobby.callPlayerByName(name).putDiceIgnoreValue(oldRow,oldCol,newRow,newCol);
    }
    public void moveStuffOnSide(String name,int oldRow, int oldCol,int newRow, int newCol) throws InvalidValueException, InvalidCellException {
        lobby.callPlayerByName(name).putDice(lobby.callPlayerByName(name).showSelectedCell(oldRow,oldCol).pickDice(),newRow,newCol);
    }

    public Dice pickFromReserve(int whichOne) throws InvalidValueException, InvalidSomethingWasNotDoneGood {
        try {
            return lobby.pickFromReserve(whichOne);
        }
        catch(ArrayIndexOutOfBoundsException  e){
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
}
