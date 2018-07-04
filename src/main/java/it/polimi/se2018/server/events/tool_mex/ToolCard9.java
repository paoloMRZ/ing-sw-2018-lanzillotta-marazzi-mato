package it.polimi.se2018.server.events.tool_mex;
import java.util.ArrayList;
/**
 * Evento specializzato nell'uso della toolcard numero 9.
 * Costruttore ereditato dalla multiparam
 * @author Kevin Mato
 */
public class ToolCard9 extends ToolMultiParam{

    public ToolCard9(String player,int card, ArrayList<Integer> inputs){
        super(player,card,inputs);
    }

}
