package it.polimi.se2018.server.events.responses;

import it.polimi.se2018.server.events.responses.UpdateM;

public class AskPlayer extends UpdateM {
    public AskPlayer(String player,String what,String content){
        super(player,what,content);
    }
}
