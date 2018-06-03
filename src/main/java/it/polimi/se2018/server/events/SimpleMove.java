package it.polimi.se2018.server.events;


import java.util.Arrays;
import java.util.ArrayList;

public class SimpleMove extends EventMVC {
    private int dieIndex;
    private int row;
    private int col;

    public SimpleMove(int dieI, int row, int col, String player){
        super(player);
        this.dieIndex=dieI;
        this.row=row;
        this.col=col;
    }
    public int getDiceIndex(){
        return dieIndex;
    }
    public ArrayList<Integer> getCoord(){
        return  new ArrayList<Integer>(Arrays.asList(row,col));
    }
}
