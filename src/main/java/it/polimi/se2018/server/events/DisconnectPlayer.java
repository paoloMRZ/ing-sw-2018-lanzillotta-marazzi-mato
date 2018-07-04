package it.polimi.se2018.server.events;

/**
 * Messaggio inviato dal controller al server per disconnettere un giocatore definitivamente.
 * Poichè il giocatore non avrà scleto la sua carta schema.
 * @author Kevin Mato.
 */
public class DisconnectPlayer extends EventMVC  {
    public DisconnectPlayer(String turnOf) {
        super(turnOf);
    }
}
