package it.polimi.se2018.server.events.tool_mex;

import java.util.ArrayList;

/**
 * Evento specializzato nell'uso della toolcard numero 8.
 * Costruttore ereditato dalla multiparam
 * @author Kevin Mato
 */
public class ToolCard8 extends ToolMultiParam {
    public ToolCard8(String player, int cardIndex, ArrayList<Integer> inputs){
        super(player,cardIndex,inputs);
    }

}
