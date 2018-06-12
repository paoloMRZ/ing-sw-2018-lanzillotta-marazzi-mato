package it.polimi.se2018.server.events.responses;

public class SuccessActivationFinalized extends SuccessActivation {
    public SuccessActivationFinalized(int index, String message, String player){
        super(message,index, 0, 0,player);
    }
}
