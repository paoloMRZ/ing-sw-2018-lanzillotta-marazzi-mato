package it.polimi.se2018.server.events.tool_mex;

import java.util.ArrayList;

/**
 * Evento specializzato nell'uso della toolcard numero 12.
 * Costruttore ereditato dalla multiparam
 * @author Kevin Mato
 */

public class ToolCard12 extends ToolMultiParam{

    public ToolCard12(String player, int cardIndex, ArrayList<Integer> inputs){
        super(player,cardIndex,inputs);
    }

}
