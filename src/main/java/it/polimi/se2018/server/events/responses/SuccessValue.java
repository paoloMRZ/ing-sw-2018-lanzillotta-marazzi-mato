package it.polimi.se2018.server.events.responses;

public class SuccessValue extends SuccessActivation {
    private int value;
    private int die;

    public SuccessValue(int indexedCard,String mess,String name,int value,int die){
        super(mess,indexedCard, 0,0, name);
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
