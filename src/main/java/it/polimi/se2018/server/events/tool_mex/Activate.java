package it.polimi.se2018.server.events.tool_mex;

import it.polimi.se2018.server.events.EventMVC;

/**
 * Evento che definisce una richiesta di attivazione di una carta utensile, discende dall'evento generico mvc quindi conterrà
 * il nome di chi fa la richiesta e l'indice della carta che si vuole attivare.
 * I suoi sotto tipi diretti sono le ToolDouble che servono a descrivere carte utensile che abbiamo una doppia fase, oltre quella di nìconferma dell'attivazione
 * e le ToolMultiParam che  contengono le strutture dati e i parametri necessari all'uso di determinate carte utensile.
 * Sotto queste ci sono tipi ancora più specifici che grazie al loro tipo trasportano un dato che è il numero della carta quindi se è risparmiato un campo,
 * ma oltretutto si piò modificare la struttura interna di ciascuno tipo specifico se si vuole cambiare l'effetto della carta richiedente
 * parametri nuovi ed eterogenei.
 * @author Kevin Mato
 */
public class Activate extends EventMVC{
    protected int index;

    /**
     * Costruttore che prende il nome del richiedente di attivazione e indice della carta da attivare.
     * @param player nome giocatore.
     * @param cardIndex indice della carta.
     */
    public Activate(String player,int cardIndex){
        super(player);
        this.index=cardIndex;
    }

    /**
     * Getter dell'indice della crat che richiediamo.
     * @return indice della crta nel deck di utensili.
     */
    public int getCard(){
        return index;
    }

}
