package it.polimi.se2018.server.events.responses;

import it.polimi.se2018.server.events.EventMVC;

/**
 * Evento laciato per indicare un successo generico di attivazione di una carta utensile.
 * La classe possiede un getter per ogni attributo.
 * @author Kevin Mato
 */
public class SuccessActivation extends EventMVC {
    //index of card played form the player
    private int i;
    private int plfavour;
    private int cardPrice;
    private int typo;

    /**
     * Costruttore
     * @param type numero della carta
     * @param index indice della carta
     * @param plfavours favori del giocatore
     * @param price prezzo attuale della carta
     * @param player nome del player
     */
    public SuccessActivation(int type,int index, int plfavours,int price, String player){
        super(player);
        this.i=index;
        this.plfavour= plfavours;
        this.cardPrice=price;
        this.typo=type;
    }

    public int getFavs() {
        return plfavour;
    }
    public int getCardPrice(){
        return cardPrice;
    }
    public int getIndex(){
        return i;
    }
    public int getMyNumber(){
        return typo;
    }
}
