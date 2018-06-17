package it.polimi.se2018.server.events.responses;

import it.polimi.se2018.server.events.SimpleMove;

public class SuccessSimpleMove extends SimpleMove{

    public SuccessSimpleMove(int dieI, int row, int col, String player){
        super(dieI, row, col, player);
    }
}
