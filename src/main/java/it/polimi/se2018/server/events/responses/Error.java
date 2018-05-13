package it.polimi.se2018.server.events.responses;

public class Error extends Response {

    public Error(int index, String message, String player){
        super(index,message,player);
    }

}
