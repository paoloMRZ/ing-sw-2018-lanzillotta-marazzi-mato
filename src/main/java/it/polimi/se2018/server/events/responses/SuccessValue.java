package it.polimi.se2018.server.events.responses;

public class SuccessValue extends SuccessActivation {
    private int value;
    private int die;

    public SuccessValue(int type,int index, int plfavours,int price,int value, String player,int die){
        super(type,index, plfavours, price,player);
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
