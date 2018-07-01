package it.polimi.se2018.server.events.tool_mex;

import java.util.ArrayList;
import java.util.Arrays;


public class ToolCard11Bis extends ToolDouble {
    private ArrayList<Integer> data;

    public ToolCard11Bis(String player, int cardIndex, ArrayList<Integer> inputs){
        super(player,cardIndex,0,true);
        this.data=new ArrayList<>(inputs);
    }
    public ArrayList<Integer> getAttributes(){
        return data;
    }

}
