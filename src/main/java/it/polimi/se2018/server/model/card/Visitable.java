package it.polimi.se2018.server.model.card;

import it.polimi.se2018.server.controller.Visitor;
import it.polimi.se2018.server.events.tool_mex.Activate;

/**
 * Interfaccia implementata dalle carte utensile per l'implementazione del pattern visitor.
 */
public interface Visitable {
        void accept(Visitor visitor,Activate m);
}
