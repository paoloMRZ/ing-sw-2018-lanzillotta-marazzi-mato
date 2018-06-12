package it.polimi.se2018.server.events.responses;

import it.polimi.se2018.server.events.UpdateReq;

public class UpdateM extends UpdateReq{
    private String content;

    public UpdateM(String player,String what,String contenuto){
        super(player,what);
        this.content=contenuto;
    }
    public String getContent() {
        return content;
    }


}
