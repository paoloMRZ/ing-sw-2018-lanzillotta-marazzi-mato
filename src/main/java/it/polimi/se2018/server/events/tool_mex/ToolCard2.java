package it.polimi.se2018.server.events.tool_mex;

import java.util.ArrayList;
import java.util.Arrays;

public class ToolCard2 extends ToolMultiParam {

    private int row;
    private int col;
    private int newRow;
    private int newCol;
    public ToolCard2(String player, int card, ArrayList<Integer> inputs){
        super(player,card);
        this.row=inputs.get(0);
        this.col=inputs.get(1);
        this.newRow=inputs.get(2);
        this.newCol=inputs.get(3);
    }

    @Override
    public ArrayList<Integer> getAttributes() {
        return new ArrayList<>(Arrays.asList(row,col,newRow,newCol));
    }
}
