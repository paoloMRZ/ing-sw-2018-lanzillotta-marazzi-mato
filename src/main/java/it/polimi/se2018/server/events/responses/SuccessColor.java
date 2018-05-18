package it.polimi.se2018.server.events.responses;

import it.polimi.se2018.server.events.responses.Success;

public class SuccessColor extends Success {
    private String value;
    private int die;

    public SuccessColor(int indexedCard,String mess,String name,String color,int die){
        super(indexedCard,mess,name);
        this.value=new String(color);
        this.die=die;
    }
    public int getDie(){
        return die;
    }
    public String getValue(){
        return value;
    }
}
