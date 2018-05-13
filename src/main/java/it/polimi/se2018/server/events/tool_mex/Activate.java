package it.polimi.se2018.server.events.tool_mex;

import it.polimi.se2018.server.events.EventMVC;

public class Activate extends EventMVC{
    protected int index;

    public Activate(String player,int cardIndex){
        super(player);
        this.index=cardIndex;
    }
    public int getCard(){
        return index;
    }

}
