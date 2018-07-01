package it.polimi.se2018.server.events.tool_mex;

import java.util.ArrayList;

public  class ToolMultiParam extends Activate{
    private ArrayList<Integer> data;

    public ToolMultiParam(String player,int card,ArrayList<Integer> inputs){
        super(player,card);
        data= new ArrayList<>(inputs);
    }

    //tutte le classi figlie dovranno implementare
    //se possibile l'uscita deve essere posta in questo modo
    //extra/DiePerIndexDie/Box Round Grid/In Box RoundGrid/Row1 Vecchia/Col1 Vecchia/Row1New/Col1New/Row2 vecchia/Col2 Vecchia
    public ArrayList<Integer> getAttributes(){
        return data;
    }


}
