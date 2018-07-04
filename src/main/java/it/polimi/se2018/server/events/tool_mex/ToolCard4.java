package it.polimi.se2018.server.events.tool_mex;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * Evento specializzato nell'uso della toolcard numero 4. All'interno viene fatto uno spacchettamento dei parametri.
 * Costruttore erediatato dalla multiparam
 * @author Kevin Mato
 */
public class ToolCard4 extends ToolMultiParam{
    private int row2;
    private int col2;
    private int row2New;
    private int col2New;

    public ToolCard4(String player, int cardIndex, ArrayList<Integer> inputs){
        super(player,cardIndex,new ArrayList<>(  Arrays.asList(inputs.get(0),inputs.get(1),inputs.get(2),inputs.get(3)) ));
        this.row2=inputs.get(4);
        this.col2=inputs.get(5);
        this.row2New=inputs.get(6);
        this.col2New=inputs.get(7);
    }

    @Override
    public ArrayList<Integer> getAttributes() {
        ArrayList<Integer> toRet= new ArrayList<>(super.getAttributes());
        toRet.add(row2);
        toRet.add(col2);
        toRet.add(row2New);
        toRet.add(col2New);
    return toRet;
    }
}
