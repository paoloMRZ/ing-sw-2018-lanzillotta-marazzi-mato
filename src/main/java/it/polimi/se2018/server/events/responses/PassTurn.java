package it.polimi.se2018.server.events.responses;

import it.polimi.se2018.server.events.EventMVC;

public class PassTurn extends EventMVC {
    public PassTurn(String player){
        super(player);
    }
}
