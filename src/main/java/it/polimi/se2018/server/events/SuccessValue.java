package it.polimi.se2018.server.events;

import it.polimi.se2018.server.model.Player;

public class SuccessValue extends Success{
    private int value;
    private String namePlayer;
    private int indexedCard;

    public SuccessValue(String name, int value, int indexedCard){
        super(name,"");
        this.value=value;
        this.namePlayer=new String(name);
        this.indexedCard=indexedCard;
    }
}
