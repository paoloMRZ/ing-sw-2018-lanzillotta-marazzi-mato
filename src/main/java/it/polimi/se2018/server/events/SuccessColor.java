package it.polimi.se2018.server.events;

public class SuccessColor extends Success {
    private String value;
    private String namePlayer;
    private int indexedCard;

    public SuccessColor(String name, String color, int indexedCard){
        super(name,"");
        this.value=new String(color);
        this.namePlayer=new String(name);
        this.indexedCard=indexedCard;
    }
}
