package it.polimi.se2018.server.events;

import it.polimi.se2018.server.events.EventMVC;

public class UpdateReq extends EventMVC {
    protected String what;

    public UpdateReq(String player, String what){
        super(player);
        this.what= what;
    }

    public String getWhat() {
        return what;
    }
}
