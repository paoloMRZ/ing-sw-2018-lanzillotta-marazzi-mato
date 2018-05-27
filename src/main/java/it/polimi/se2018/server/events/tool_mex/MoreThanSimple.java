package it.polimi.se2018.server.events.tool_mex;

import it.polimi.se2018.server.model.dice_sachet.Dice;

import java.util.ArrayList;
import java.util.Arrays;

//LEGATO ALLA TOOL CARD 1
public class MoreThanSimple extends Activate{
    private int die;
    private boolean decision;//se valore da aggiungere all'attuale valore del dado
    // il vecchio prototipo
    //da allineare a tutti quelli troppo lunghi
    //era public MoreThanSimple (String player, int cardIndex, boolean addUp, int iDie)

    public MoreThanSimple (String player, int cardIndex, boolean addUp, int whichDie){

        super(player,cardIndex);

            this.die=whichDie;
            this.decision=addUp;


    }

    public int getDie(){
        return die;
    }
    public boolean getDecision(){
        return decision;
    }
}
