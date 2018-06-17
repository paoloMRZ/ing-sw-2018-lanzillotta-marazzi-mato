package it.polimi.se2018.server.events.responses;

import it.polimi.se2018.server.events.tool_mex.Activate;

public class SuccessActivationFinalized extends Activate{
    public SuccessActivationFinalized(int index,  String player){
        super(player,index);
    }
}
