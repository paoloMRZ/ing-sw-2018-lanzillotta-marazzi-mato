package it.polimi.se2018.server.events.responses;

import it.polimi.se2018.server.events.EventMVC;

public class Choice extends EventMVC {
    private int index;
    public Choice(String player,int inde){
        super(player);
        this.index=inde;
    }

    public int getIndex() {
        return index;
    }
}
