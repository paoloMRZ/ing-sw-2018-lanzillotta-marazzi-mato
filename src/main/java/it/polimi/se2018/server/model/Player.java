package it.polimi.se2018.server.model;

import it.polimi.se2018.server.exceptions.InvalidCellException;
import it.polimi.se2018.server.exceptions.InvalidHowManyTimes;
import it.polimi.se2018.server.exceptions.invalid_cell_exceptios.*;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidCoordinatesException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidShadeValueException;
import it.polimi.se2018.server.model.card.card_objective.Objective;
import it.polimi.se2018.server.model.card.card_schema.Cell;
import it.polimi.se2018.server.model.card.card_schema.Side;
import it.polimi.se2018.server.model.dice_sachet.Dice;

import java.util.List;

public class Player {

    /*
        OVERVIEW: la classe Player è una rappresentazione nel model del client (giocatore) partecipante alla sessione di gioco, identificato da un NOME,
        un SIDE, un OBJECTIVE e dai segnalini FAVORE.

        Si stava pensando a introdurre una modifica nel costruttore di player. Cosi come lo si è pensato, stiamo ipotizzando che chi chiama il costruttore
        di Player (si presume il controller ma anche lo stesso Table andrebbe bene) sappia già a priori quale sia la carta Schema da assegnarli. Quindi si
        dovrebbero creare strutture dati in cui memorizzare temporaneamente le informazioni relative al giocatore, attendere la scelta della carte e solo
        in seguito chiamare il costrutture.

        Sarebbe invece più corretto chiamare il costruttore di Player per settare gli attributi NAME, MYOBJECTIVE e FAVOURS, e solo in secondo luogo
        tramite magari un secondo costruttore in override, settare il campo MYSIDE con la scelta effettuata.

    */

    private List<Side> mySideSelection;

    private String name;
    private Side mySide;
    private Objective myobjective;
    private int favours;
    private boolean isMyTurn;
    private int howManyTurns;
    private boolean didPlayDie;
    private boolean didPlayCard;

    public Player(Side choicePattern, Objective objective, int favours, String nomine) {
        this.mySide = choicePattern;
        this.myobjective = objective;
        this.favours = favours;
        this.name=nomine;
        this.howManyTurns=2;
        this.didPlayCard=false;
        this.didPlayDie=false;
    }

    public Color getColorCell(int i, int j) throws Exception{
        return mySide.getColor(i,j);
    }

    public int getNumberCell(int i, int j)  throws Exception{
        return mySide.getNumber(i,j);
    }


    public void putDice(Dice d, int row, int col) throws InvalidCellException, InvalidCoordinatesException {
        this.mySide.put(row,col,d);
    }

    public Cell showSelectedCell(int row, int col) throws InvalidShadeValueException, InvalidCoordinatesException {
        return mySide.showCell(row,col);
    }

    public int getHowManyTurns(){
        return howManyTurns;
    }
    public boolean getDidPlayDie(){
        return didPlayDie;
    }
    public boolean getDidPlayCard(){
        return didPlayCard;
    }

    public void setDidPlayCard(){
        if(didPlayCard) didPlayCard=false;
        else didPlayCard=true;
    }
    public void setDidPlayDie(){
        if(didPlayDie) didPlayDie=false;
        else didPlayDie=true;
    }
    public void reductor()throws InvalidHowManyTimes {
        if(howManyTurns>0) howManyTurns=howManyTurns-1;
        else throw new InvalidHowManyTimes();
    }
    public void restoreValues(){
        this.howManyTurns=2;
        this.didPlayCard=false;
        this.didPlayDie=false;
    }

    public String getName(){
        return name;
    }

    public void putDiceIgnoreColor(int oldRow, int oldCol,int newRow, int newCol) throws InvalidCoordinatesException, InvalidShadeValueException, NoDicesNearException, NotEmptyCellException, InvalidShadeException, NearDiceInvalidException {
        mySide.putIgnoringColor(newRow,  newCol,    mySide.showCell(oldRow,oldCol).pickDice());
    }

    public void putDiceIgnoreValue(int oldRow, int oldCol,int newRow, int newCol) throws InvalidCoordinatesException, InvalidShadeValueException, NoDicesNearException, NotEmptyCellException, InvalidColorException, NearDiceInvalidException {
        mySide.putIgnoringShade(newRow,  newCol,    mySide.showCell(oldRow,oldCol).pickDice());
    }




    public void setSideSelection(List<Side> mySideSelection){
        this.mySideSelection = mySideSelection;
    }


}
