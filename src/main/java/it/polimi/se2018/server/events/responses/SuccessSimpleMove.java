package it.polimi.se2018.server.events.responses;

import it.polimi.se2018.server.events.SimpleMove;
/**
 * Evento laciato per indicare un successo di un piazzamento.Possiede gli stessi getter dell'evento di piazzamento.
 * @author Kevin Mato
 */
public class SuccessSimpleMove extends SimpleMove{

    public SuccessSimpleMove(int dieI, int row, int col, String player){
        super(dieI, row, col, player);
    }
}
