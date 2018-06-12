package it.polimi.se2018.server.events.responses;

public class SuccessColor extends SuccessActivation {
    private String value;
    private int die;

    public SuccessColor(int indexedCard,String mess,String name,String color,int die){
        super(mess,indexedCard, 0,0, name);
        this.value=color ;
        this.die=die;
    }
    public int getDie(){
        return die;
    }
    public String getValue(){
        return value;
    }
}
