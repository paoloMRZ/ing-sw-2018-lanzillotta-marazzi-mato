package it.polimi.se2018.server.model;

import it.polimi.se2018.server.exceptions.InvalidCellException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidCoordinatesException;
import it.polimi.se2018.server.model.card.card_objective.Objective;
import it.polimi.se2018.server.model.card.card_schema.Side;
import it.polimi.se2018.server.model.dice_sachet.Dice;


/*
    Ho provissoriamente inserito nella calsse player dei metodi che mi permettano di estrarre le informazioni sia
    sulle celle del Side delgiocatore sia sugli eventuali dadi che vi sono posizionati.

    L'ho fatto per poter scrivere gli algoritmi 1,2, ma essendo che si tartta di scelte implementative comuni, ho
    solo scritto due algoritmi che ne fanno uso. Gli altri dovrò completarli non appena definiamo bene cosa va
    dentro Player

*/

public class Player {
    private String name;
    private Side choicePattern;
    private Objective obj;
    private int favours;
    private boolean isMyTurn;

    public Player(Side choicePattern, Objective obj, int favours, String nomine) {
        this.choicePattern = choicePattern;
        this.obj = obj;
        this.favours = favours;
        this.name=nomine;
    }

    public String getColorCell(int i, int j) throws Exception{
        return choicePattern.getColor(i,j);
    }

    public int getNumberCell(int i, int j)  throws Exception{
        return choicePattern.getNumber(i,j);
    }


    //Ritorna il colore del dado posizionato nella cella (i,j), se esiste
    public String getDiceColor(int i, int j) throws Exception{
        return choicePattern.getCellsDiceColorInformation(i,j);
    }

    //Ritorna il numero del dado posizionato nella cella (i,j), se esiste
    public int getDiceNumber(int i, int j) throws Exception{
        return choicePattern.getCellsDiceNumberInformation(i,j);
    }

    public void putDice(Dice d, int row, int col) throws InvalidCellException, InvalidCoordinatesException{
        this.choicePattern.put(row,col,d);
    }

}
