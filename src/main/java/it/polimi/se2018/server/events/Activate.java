package it.polimi.se2018.server.events;

public abstract class Activate extends Move {
    private int index;

    public Activate(String player,int cardIndex){
        super(player);
        this.index=cardIndex;
    }
    //public abstract ArrayList<int> getCoord();
}
