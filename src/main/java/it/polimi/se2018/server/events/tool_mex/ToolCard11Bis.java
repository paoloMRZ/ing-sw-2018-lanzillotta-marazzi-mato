package it.polimi.se2018.server.events.tool_mex;

import java.util.ArrayList;
import java.util.Arrays;


public class ToolCard11Bis extends ToolMultiParam {
    private int value;
    private int row;
    private int col;
    private boolean isBis=true;

    public ToolCard11Bis(String player, int cardIndex, ArrayList<Integer> inputs){

        super(player,cardIndex);
        this.value=inputs.get(0);
        this.row=inputs.get(1);
        this.col=inputs.get(2);
    }

    public ArrayList<Integer> getAttributes() {
        return new ArrayList<>(Arrays.asList(value,row,col));
    }
    public boolean getisBis(){
        return isBis;
    }
}
