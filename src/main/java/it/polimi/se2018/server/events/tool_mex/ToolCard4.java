package it.polimi.se2018.server.events.tool_mex;

import java.util.ArrayList;
import java.util.Arrays;

public class ToolCard4 extends ToolCard2{
    private int row2;
    private int col2;
    private int row2New;
    private int col2New;

    public ToolCard4(String player, int cardIndex, int row, int col, int rowNew, int colNew, int row2, int col2, int row2New, int col2New){
        super(player,cardIndex,row,col,rowNew,colNew);
        this.row2=row2;
        this.col2=col2;
        this.row2New=row2New;
        this.col2New=col2New;
    }

    @Override
    public ArrayList<Integer> getAttributes() {
        return new ArrayList<>(Arrays.asList(row2,col2,row2New,col2New));
    }
}
