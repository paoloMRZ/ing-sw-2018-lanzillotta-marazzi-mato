package it.polimi.se2018.server.events.responses;

import it.polimi.se2018.server.events.EventMVC;

public class SuccessActivation extends EventMVC {
    //index of card played form the player
    private int i;
    private int plfavour;
    private int cardPrice;
    private String typo;


    public SuccessActivation(String type,int index, int plfavours,int price, String player){
        super(player);
        this.i=index;
        this.plfavour= plfavours;
        this.cardPrice=price;
        this.typo=type;
    }

    public int getFavs() {
        return plfavour;
    }
    public int getCardPrice(){
        return cardPrice;
    }
    public int getIndex(){
        return i;
    }
    public String getTypo(){
        return typo;
    }
}
