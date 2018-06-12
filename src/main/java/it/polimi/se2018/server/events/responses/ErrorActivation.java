package it.polimi.se2018.server.events.responses;

public class ErrorActivation extends SuccessActivation {

    public ErrorActivation(int index, String message, String player){

        super(message,index, 0, 0,player);    }

}
