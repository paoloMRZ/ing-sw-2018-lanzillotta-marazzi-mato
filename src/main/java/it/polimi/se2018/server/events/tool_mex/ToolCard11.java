package it.polimi.se2018.server.events.tool_mex;

public class ToolCard11 extends ToolCard10 {
    private boolean isBis=false;
    public ToolCard11(String player,int cardIndex,int die){
        super(player,cardIndex,die);
    }
    public boolean getisBis(){
        return isBis;
    }
}
