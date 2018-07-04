package it.polimi.se2018.server.events;

/**
 * Classe padre di tutti gli eventi del mvc.
 * @author Kevin Mato
 */
public class EventMVC{
    protected String name;

    public EventMVC(String pName){
        this.name= pName;
    }
    public String getPlayer(){
        return name;
    }
}
