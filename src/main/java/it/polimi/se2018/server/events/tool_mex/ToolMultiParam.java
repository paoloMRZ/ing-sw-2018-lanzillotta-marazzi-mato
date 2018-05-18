package it.polimi.se2018.server.events.tool_mex;

import java.util.ArrayList;

public abstract class ToolMultiParam extends Activate{


    public ToolMultiParam(String player,int card){
        super(player,card);
    }

    //tutte le classi figlie dovranno implementare
    //se possibile l'uscita deve essere posta in questo modo
    //extra/DiePerIndexDie/Box Round Grid/In Box RoundGrid/Row1 Vecchia/Col1 Vecchia/Row1New/Col1New/Row2 vecchia/Col2 Vecchia
    public abstract ArrayList<Integer> getAttributes();

}
