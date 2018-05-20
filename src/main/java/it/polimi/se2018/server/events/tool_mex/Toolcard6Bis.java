package it.polimi.se2018.server.events.tool_mex;

import java.util.ArrayList;

public class Toolcard6Bis extends MoreThanSimple{

    public Toolcard6Bis(String player,int card,boolean letItDown, ArrayList<Integer> inputs){
        super(player,card,letItDown,inputs);
    }
    public ArrayList<Integer> getAttributes(){
        return super.getAttributes();
    }
    public boolean getDecision(){
        return super.getDecision();
    }
}
