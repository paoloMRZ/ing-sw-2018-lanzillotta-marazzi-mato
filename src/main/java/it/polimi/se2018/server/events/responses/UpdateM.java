package it.polimi.se2018.server.events.responses;

public class UpdateM{
    private String content;
    private String what;

    public UpdateM(String what,String contenuto){
        this.content=contenuto;
        this.what=what;
    }
    public String getContent() {
        return content;
    }

    public String getWhat() {
        return what;
    }

}
