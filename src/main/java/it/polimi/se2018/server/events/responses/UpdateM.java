package it.polimi.se2018.server.events.responses;

import it.polimi.se2018.server.events.UpdateReq;

public class UpdateM extends UpdateReq{
    private String content;
    private boolean isBroadcast;

    public UpdateM(String player,String what,String contenuto){
        super(player,what);
        this.content=contenuto;
        this.isBroadcast=true;
    }
    public UpdateM(String player,String what,String contenuto,boolean how){
        super(player,what);
        this.content=contenuto;
        this.isBroadcast=how;
    }
    public String getContent() {
        return content;
    }

    public boolean getIsbroadcast(){
        return isBroadcast;
    }

}
