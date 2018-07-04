package it.polimi.se2018.server.events.responses;

/**
 * Evento laciato per indicare un successo di fine uso dopo l'attivazione di una carta utensile.
 * La classe possiede un getter per ogni attributo.
 * @author Kevin Mato
 */

public class SuccessActivationFinalized extends SuccessActivation{
    public SuccessActivationFinalized(int type,int index, int plfavours,int price, String player){
        super(type,index, plfavours, price,player);    }
}
