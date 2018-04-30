package it.polimi.se2018.server.model.grid;

import it.polimi.se2018.server.model.dice_sachet.Dice;

import java.util.ArrayList;

public class RoundGrid {
    private ArrayList<Dice> roundDices;
    private int actualRound;

    public RoundGrid(ArrayList<Dice> roundDices, int actualRound) {
        this.roundDices = roundDices;
        this.actualRound = actualRound;
    }

    /*
        Tenta di prelevare un Dice dall'ArrayList, le eccezioni che potrebbero insorgere riguardano
        una posizione p tale che p>10 or p<1 (ovvero si richiede un dado in una posizione che non esiste)
        oppure quando si sceglie una posizione in cui non c'è un Dice

        Pertanto la prima eccezione riguarda il range dell'Array -> ArrayOutOfBoundsException
        La seconda eccezione se la cella dell'array selezionata sia null -> NullPointerException

        Sono entrambe di tipo unchecked, quindi posso semplicemente inserire una generica Exception nel catch

    */

    public Dice pick(int pos){
        //..
        //..
    }

    public void put(int pos, Dice dice){
        roundDices.add(pos, dice);
    }
    public int getRound(){
        return actualRound;
    }
}
