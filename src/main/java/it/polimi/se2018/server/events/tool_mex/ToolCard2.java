package it.polimi.se2018.server.events.tool_mex;

import java.util.ArrayList;

/**
 * Evento specializzato nell'uso della toolcard numero 2.
 * Costruttore erediatato dalla multiparam
 * @author Kevin Mato
 */
public class ToolCard2 extends ToolMultiParam {

    public ToolCard2(String player, int card, ArrayList<Integer> inputs){
        super(player,card,inputs);
    }

}
