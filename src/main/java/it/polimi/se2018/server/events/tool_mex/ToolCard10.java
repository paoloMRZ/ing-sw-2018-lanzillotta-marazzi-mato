package it.polimi.se2018.server.events.tool_mex;

public class ToolCard10 extends Activate {
    private int die;

    public ToolCard10(String player,int cardIndex,int die){
        super(player,cardIndex);
        this.die=die;
    }
    public int getDie(){
        return die;
    }
}

