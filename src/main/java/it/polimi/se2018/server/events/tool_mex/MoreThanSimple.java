package it.polimi.se2018.server.events.tool_mex;

import java.util.ArrayList;
import java.util.Arrays;

//LEGATO ALLA TOOL CARD 1
public class MoreThanSimple extends ToolMultiParam{
    private int row;
    private int col;
    private int die;
    private boolean decision;//se valore da aggiungere all'attuale valore del dado

    public MoreThanSimple (String player, int cardIndex, boolean addUp, int iDie,int row,int col){

        super(player,cardIndex);

            this.die=iDie;
            this.row=row;
            this.col=col;
            this.decision=addUp;


    }
    public ArrayList<Integer> getAttributes(){
        return new ArrayList<>(Arrays.asList(die,row,col));
    }
    public boolean getDecision(){
        return decision;
    }
}
