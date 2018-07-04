package it.polimi.se2018.server.events.tool_mex;

/**
 * Evento specializzato nell'uso della toolcard numero 7.
 * Costruttore ereditato direttamente da activate poiè non ha bisogno di paramtri aggiuntivi.
 * @author Kevin Mato
 */
public class ToolCard7 extends Activate {
    public ToolCard7(String player, int cardIndex ){
        super(player,cardIndex);
    }
}
