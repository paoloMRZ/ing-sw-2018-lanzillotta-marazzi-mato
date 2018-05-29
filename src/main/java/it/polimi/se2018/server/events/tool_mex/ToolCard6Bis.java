package it.polimi.se2018.server.events.tool_mex;

import java.util.ArrayList;



public class ToolCard6Bis extends ToolMultiParam{

    private ArrayList<Integer> data;
    private boolean decision;

    public ToolCard6Bis(String player, int card, boolean letItDown, ArrayList<Integer> inputs){
        super(player,card);
        this.decision=letItDown;
        this.data=new ArrayList<>(inputs);
    }
    public ArrayList<Integer> getAttributes(){
        return data;
    }
    public boolean getDecision(){
        return decision;
    }
}
