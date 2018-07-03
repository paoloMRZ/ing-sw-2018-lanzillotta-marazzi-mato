package it.polimi.se2018.server.events.responses;

import it.polimi.se2018.server.events.tool_mex.Activate;

public class SuccessColor extends Activate {
    private String value;

    public SuccessColor( String player,int index,String color){
        super(player,index);
        this.value=color ;

    }
    public String getValue(){
        return value;
    }
}
