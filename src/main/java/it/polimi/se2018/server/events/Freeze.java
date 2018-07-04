package it.polimi.se2018.server.events;
/**
 * L'evento si occupa di comunincare al controller il volere del server di congelare un giocatore, e viceversa.
 * @author Kevin Mato
 */
public class Freeze extends EventMVC {

    public Freeze(String name){
        super(name);
    }

}
