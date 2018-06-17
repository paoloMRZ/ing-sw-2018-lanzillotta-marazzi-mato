package it.polimi.se2018.server.events.responses;

public class ErrorSelection extends SuccessSimpleMove {

    public  ErrorSelection(int dieI, int row, int col, String player){
        super(dieI, row, col, player);
    }
}
