package it.polimi.se2018.server.events;

public class EventMVC{
    //todo trovare modo obbligare le classi che necessitano di un bis di tipo simpleMOve ad eseguire questo
    //tipo di messaggio
    //todo risistemare i package dei messaggi
    protected String name;

    public EventMVC(String pName){
        this.name= pName;
    }
    public String getPlayer(){
        return name;
    }
}
