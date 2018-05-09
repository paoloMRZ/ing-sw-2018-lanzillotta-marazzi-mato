package it.polimi.se2018.server.events;

import java.util.ArrayList;

public class SimpleMove extends Move{
    private int diceIndex;
    private int row;
    private int col;

    public SimpleMove(int diceI, int row, int col, String player){
        super(player);
        this.diceIndex=diceI;
        this.row=row;
        this.col=col;
    }
    public int getDiceIndex(){
        return diceIndex;
    }
    //todo riguardare
    /*public ArrayList<int> getCoord(){

    }*/
}
