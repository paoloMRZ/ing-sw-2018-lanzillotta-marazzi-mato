package it.polimi.se2018.server.events.responses;

import it.polimi.se2018.server.events.EventMVC;
/**
 * Evento per esprimere la scelta della side all'inizio della partita da un gioatore.
 * Conterrà nome e indice della side selezionata.
 * @author Kevin Mato
 */
public class Choice extends EventMVC {
    private int index;
    public Choice(String player,int inde){
        super(player);
        this.index=inde;
    }

    public int getIndex() {
        return index;
    }
}
