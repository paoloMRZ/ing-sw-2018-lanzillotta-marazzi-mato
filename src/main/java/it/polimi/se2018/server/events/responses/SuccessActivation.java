package it.polimi.se2018.server.events.responses;

import it.polimi.se2018.server.events.EventMVC;

public class SuccessActivation extends EventMVC {
    //index of card played form the player
    private int i;
    private String message;


    public SuccessActivation(int index, String message, String player){
        super(player);
        this.i=index;
        this.message= message;
    }

    public String getMessage() {
        return message;
    }
    public int getIndex(){
        return i;
    }
}
