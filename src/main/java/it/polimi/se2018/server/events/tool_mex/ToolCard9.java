package it.polimi.se2018.server.events.tool_mex;

import it.polimi.se2018.server.events.SimpleMove;

import java.util.ArrayList;


//qui riutilizzo semplicemente il codice sebbene il significato degli attributi sia diverso
public class ToolCard9 extends ToolCard5{

    public ToolCard9(String player,int card,int die,int row,int col){
        super(player,card,die,row,col);
    }

    public ArrayList<Integer> getAttributes() {
        return super.getAttributes();
    }
}
