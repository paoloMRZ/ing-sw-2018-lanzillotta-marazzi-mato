package it.polimi.se2018.server.events.responses;

import it.polimi.se2018.server.events.UpdateReq;
/**
 * Evento che si occupa di trasportare dati e rappresentazioni secondo protocollo e convenzioni stabilite attraverso l'mvc verso la fakeview.
 * Possiede unn getter per ogni attributo.
 *  @author Kevin Mato
 */
public class UpdateM extends UpdateReq{
    private String content;
    private boolean isBroadcast;

    /**
     * Costruttore
     * @param player nomde del giocatore a cui deve essere indirizzato
     * @param what topic dell'aggiornamento
     * @param contenuto informazioni in stringa
     */
    public UpdateM(String player,String what,String contenuto){
        super(player,what);
        this.content=contenuto;
        this.isBroadcast=true;
    }

    /**
     * Costruttore
     * @param player nomde del giocatore a cui deve essere indirizzato
     * @param what topic dell'aggiornamento
     * @param contenuto informazioni in stringa
     * @param how booleano che indica se leggere il campo player
     */
    public UpdateM(String player,String what,String contenuto,boolean how){
        super(player,what);
        this.content=contenuto;
        this.isBroadcast=how;
    }
    public String getContent() {
        return content;
    }

    public boolean getIsbroadcast(){
        return isBroadcast;
    }

}
