package it.polimi.se2018.server.events.tool_mex;

import java.util.ArrayList;

/**
 * Evento specializzato nell'uso della toolcard numero 11bis.
 * Costruttore misto di multiparametro e della
 * toolcard 1 ma necessitando dell'attributo se è bis o no, che viene settato di default e se tenere il dado o no.
 * @author Kevin Mato
 */

public class ToolCard11Bis extends ToolDouble {
    private ArrayList<Integer> data;
    private boolean decision;

    public ToolCard11Bis(String player, int cardIndex, boolean throwAway,ArrayList<Integer> inputs){
        super(player,cardIndex,0,true);
        this.data=new ArrayList<>(inputs);
        this.decision=throwAway;
    }
    public ArrayList<Integer> getAttributes(){
        return data;
    }
    public boolean getDecision(){
        return decision;
    }
}
