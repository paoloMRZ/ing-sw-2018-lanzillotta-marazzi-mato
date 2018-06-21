package it.polimi.se2018.server.events.responses;

import it.polimi.se2018.server.events.tool_mex.Activate;

public class SuccessValue extends Activate{
    private int value;
    private int die;

    public SuccessValue( String player,int index,int numb){
        super(player,index);
        this.value= numb ;
        this.die=die;
    }
    public int getDie(){
        return die;
    }
    public int getValue(){
        return value;
    }
}
