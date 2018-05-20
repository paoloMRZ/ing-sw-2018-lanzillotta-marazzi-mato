package it.polimi.se2018.server.events.tool_mex;

import java.util.ArrayList;
import java.util.Arrays;

public class ToolCard5 extends ToolMultiParam {

    private  int indexDie;
    private int boxOnGrid;//visto come una coordinata orizzontale-row
    private int inBoxGrid;//visto come una coordinata verticale-col

    public ToolCard5(String player,int card,ArrayList<Integer> inputs){
        super(player,card);
        this.indexDie=inputs.get(0);
        this.boxOnGrid=inputs.get(1);
        this.inBoxGrid=inputs.get(2);
    }
    @Override
    public ArrayList<Integer> getAttributes() {
        return new ArrayList<>(Arrays.asList(indexDie,boxOnGrid,inBoxGrid));
    }
}
