package it.polimi.se2018.server.events;

/**
 * L'evento si occupa di comunincare al controller il volere del server di scongelare un giocatore.
 * @author Kevin Mato
 */
public class Unfreeze extends EventMVC {
    public Unfreeze(String name){
        super(name);
    }
}
