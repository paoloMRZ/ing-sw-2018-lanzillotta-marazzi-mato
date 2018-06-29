package it.polimi.se2018.server.events.tool_mex;

public class ToolCard6 extends Activate{
    private int die;
    private boolean isBis=false;
    public ToolCard6(String player,int card,int die){
        super(player,card);
        this.die=die;
    }
    public int getDie(){
        return die;
    }
    public boolean getisBis(){
        return isBis;
    }
}
