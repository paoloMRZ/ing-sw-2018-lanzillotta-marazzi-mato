package it.polimi.se2018.server.model.card;

import it.polimi.se2018.server.controller.Visitor;
import it.polimi.se2018.server.events.tool_mex.Activate;


public interface Visitable {
        public void accept(Visitor visitor,Activate m);
}
