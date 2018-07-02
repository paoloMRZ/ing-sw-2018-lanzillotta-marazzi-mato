package it.polimi.se2018.server.events.tool_mex;

import java.util.ArrayList;
import java.util.Arrays;


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
