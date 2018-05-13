package it.polimi.se2018.server.events.tool_mex;

import java.util.ArrayList;
import java.util.Arrays;

public class ToolCard2 extends ToolMultiParam {

    private int row;
    private int col;
    private int newRow;
    private int newCol;
    public ToolCard2(String player, int card, int row, int col,int newRow,int newCol){
        super(player,card);
        this.row=row;
        this.col=col;
        this.newRow=newRow;
        this.newCol=newCol;
    }

    @Override
    public ArrayList<Integer> getAttributes() {
        return new ArrayList<>(Arrays.asList(row,col,newRow,newCol));
    }
}
