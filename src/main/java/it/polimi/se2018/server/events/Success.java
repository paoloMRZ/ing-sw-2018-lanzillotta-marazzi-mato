package it.polimi.se2018.server.events;

public class Success {
    private String player;
    private String string;

    public Success(String name,String text){
        this.player= new String(name);
        this.string= new String(text);
    }
}
