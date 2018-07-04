package it.polimi.se2018.server.events.responses;
/**
 * Evento lanciato nel caso di errore di selezione per il piazzamento.
 * @author Kevin Mato
 */
public class ErrorSelection extends SuccessSimpleMove {

    public  ErrorSelection(int dieI, int row, int col, String player){
        super(dieI, row, col, player);
    }
}
