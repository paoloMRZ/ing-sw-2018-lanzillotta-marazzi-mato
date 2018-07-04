package it.polimi.se2018.server.events.responses;

import it.polimi.se2018.server.events.tool_mex.Activate;

/**
 * Evento lanciato nel caso di errore di selezione per l'utensile.
 * @author Kevin Mato
 */
public class ErrorSelectionUtensil extends Activate {
     public ErrorSelectionUtensil(String player,int card){
         super(player,card);
     }
}
