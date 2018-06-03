package it.polimi.se2018.server.events.responses;

import it.polimi.se2018.server.events.EventMVC;

public class ErrorSomethingNotGood extends EventMVC {

    public ErrorSomethingNotGood(String player){
        super(player);
    }
}
