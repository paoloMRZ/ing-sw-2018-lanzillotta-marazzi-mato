package it.polimi.se2018.server.events.responses;


import it.polimi.se2018.server.events.EventMVC;

public class TimeIsUp extends EventMVC {
    public TimeIsUp(String player){
        super(player);
    }
}
