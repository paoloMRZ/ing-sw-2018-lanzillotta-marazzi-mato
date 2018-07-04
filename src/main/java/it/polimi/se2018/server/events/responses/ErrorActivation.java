package it.polimi.se2018.server.events.responses;

/**
 * Evento lanciato nel caso di errore di attivazione.
 * @author Kevin Mato
 */
public class ErrorActivation extends SuccessActivation {
    /**
     * Costruttore
     * @param type numero della carta
     * @param index indice della carta
     * @param plfavours favori del giocatore attuali
     * @param price costo attuale della carta
     * @param player nome del giocatore
     */
    public ErrorActivation(int type,int index, int plfavours,int price, String player){
        super(type,index, plfavours, price,player);
    }

}
