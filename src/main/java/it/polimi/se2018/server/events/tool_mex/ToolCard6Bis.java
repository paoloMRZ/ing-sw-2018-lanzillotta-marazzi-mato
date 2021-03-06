package it.polimi.se2018.server.events.tool_mex;

import java.util.ArrayList;



public class ToolCard6Bis extends ToolDouble{

    private boolean decision;
    private ArrayList<Integer> data;

    public ToolCard6Bis(String player, int card, boolean letItDown, ArrayList<Integer> inputs){
        super(player,card,0,true);
        this.decision=letItDown;
        this.data=new ArrayList<>(inputs);
    }

    public boolean getDecision(){
        return decision;
    }
    public ArrayList<Integer> getAttributes(){
        return data;
    }
}
