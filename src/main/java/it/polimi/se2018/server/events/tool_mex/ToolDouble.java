package it.polimi.se2018.server.events.tool_mex;

public class ToolDouble extends Activate {
    private boolean isBis;
    private int die;
    public ToolDouble(String player,int card,int die,boolean is){
        super(player,card);
        this.die=die;
        this.isBis=is;
    }
    public boolean getisBis(){
        return isBis;
    }

    public int getDie() {
        return die;
    }
}
