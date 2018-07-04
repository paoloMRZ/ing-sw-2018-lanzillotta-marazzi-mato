package it.polimi.se2018.server.events.responses;


import it.polimi.se2018.server.events.EventMVC;

/**
 * Evento che viene lanciato per segnalare la fine del tempo per un turno ad un giocatore.
 * L'evento rapprensenta anche la chiusura dei canali di comunicazione nel mvc.
 * Determina la pulizia dei buffer in ingresso e uscita dei messaggi.
 */
public class TimeIsUp extends EventMVC {
    public TimeIsUp(String player){
        super(player);
    }
}
