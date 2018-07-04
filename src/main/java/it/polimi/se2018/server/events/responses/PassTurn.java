package it.polimi.se2018.server.events.responses;

import it.polimi.se2018.server.events.EventMVC;

/**
 * Evento che si occupa di esprimere il desiderio del giocatore di passare turno. All'interno ha io nome del giocatore che vuole passare il turno.
 * @author Kevin Mato
 */
public class PassTurn extends EventMVC {
    public PassTurn(String player){
        super(player);
    }
}
