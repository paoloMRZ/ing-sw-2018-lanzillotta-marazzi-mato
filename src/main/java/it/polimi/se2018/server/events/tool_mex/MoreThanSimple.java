package it.polimi.se2018.server.events.tool_mex;

import java.util.ArrayList;
import java.util.Arrays;

//LEGATO ALLA TOOL CARD 1
public class MoreThanSimple extends ToolMultiParam{
    private int row;
    private int col;
    private int die;
    private boolean decision;//se valore da aggiungere all'attuale valore del dado
    // il vecchio prototipo
    //da allineare a tutti quelli troppo lunghi
    //era public MoreThanSimple (String player, int cardIndex, boolean addUp, int iDie,int row,int col)

    public MoreThanSimple (String player, int cardIndex, boolean addUp, ArrayList<Integer> inputs){

        super(player,cardIndex);

            this.die=inputs.get(0);
            this.row=inputs.get(1);
            this.col=inputs.get(2);
            this.decision=addUp;


    }

    public ArrayList<Integer> getAttributes(){
        return new ArrayList<>(Arrays.asList(die,row,col));
    }
    public boolean getDecision(){
        return decision;
    }
}
