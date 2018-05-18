package it.polimi.se2018.server.events.responses;

import it.polimi.se2018.server.events.responses.Success;

public class SuccessValue extends Success {
    private int value;
    private int die;

    public SuccessValue(int indexedCard,String mess,String name,int value,int die){
        super(indexedCard,mess,name);
        this.value=value;
        this.die=die;
    }
    public int getDie(){
        return die;
    }
    public int getValue(){
        return value;
    }
}
