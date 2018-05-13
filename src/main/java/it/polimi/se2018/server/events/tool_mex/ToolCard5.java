package it.polimi.se2018.server.events.tool_mex;

import java.util.ArrayList;
import java.util.Arrays;

public class ToolCard5 extends ToolMultiParam {

    private  int indexDie;
    private int boxOnGrid;//visto come una coordinata orizzontale-row
    private int inBoxGrid;//visto come una coordinata verticale-col

    public ToolCard5(String player,int card,int die,int box,int inBox){
        super(player,card);
        this.indexDie=die;
        this.boxOnGrid=box;
        this.inBoxGrid=inBox;
    }
    @Override
    public ArrayList<Integer> getAttributes() {
        return new ArrayList<>(Arrays.asList(indexDie,boxOnGrid,inBoxGrid));
    }
}
