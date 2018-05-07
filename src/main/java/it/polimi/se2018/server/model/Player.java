package it.polimi.se2018.server.model;

import it.polimi.se2018.server.exceptions.InvalidCellException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidCoordinatesException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidFavoursValueException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidShadeValueException;
import it.polimi.se2018.server.model.card.card_objective.Objective;
import it.polimi.se2018.server.model.card.card_schema.Side;
import it.polimi.se2018.server.model.dice_sachet.Dice;

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

    private String name;
    private Side mySide;
    private Objective myobjective;
    private int favours;
    private boolean isMyTurn;

    public Player(Side choicePattern, Objective objective, int favours, String nomine) {
        this.mySide = choicePattern;
        this.myobjective = objective;
        this.favours = favours;
        this.name=nomine;
    }

    public String getColorCell(int i, int j) throws Exception{
        return mySide.getColor(i,j);
    }

    public int getNumberCell(int i, int j)  throws Exception{
        return mySide.getNumber(i,j);
    }

    //Ritorna il colore del dado posizionato nella cella (i,j), se esiste
    public String getDiceColor(int i, int j) throws Exception{
        return mySide.getCellsDiceColorInformation(i,j);
    }

    //Ritorna il numero del dado posizionato nella cella (i,j), se esiste
    public int getDiceNumber(int i, int j) throws Exception{
        return mySide.getCellsDiceNumberInformation(i,j);
    }

    public void putDice(Dice d, int row, int col) throws InvalidCellException, InvalidCoordinatesException{
        this.mySide.put(row,col,d);
    }
}
