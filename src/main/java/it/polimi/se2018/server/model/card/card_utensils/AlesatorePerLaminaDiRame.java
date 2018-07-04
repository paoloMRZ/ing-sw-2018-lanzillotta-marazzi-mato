package it.polimi.se2018.server.model.card.card_utensils;

import it.polimi.se2018.server.controller.Controller;
import it.polimi.se2018.server.controller.Visitor;
import it.polimi.se2018.server.events.tool_mex.Activate;
import it.polimi.se2018.server.events.tool_mex.ToolCard3;
import it.polimi.se2018.server.exceptions.InvalidCellException;
import it.polimi.se2018.server.exceptions.InvalidValueException;
import it.polimi.se2018.server.model.Color;
import it.polimi.se2018.server.model.card.Visitable;

import java.util.ArrayList;

/**
 * Classe che si occupa dell'esecuzione dell'effetto contrattuato dalla carta utensile numero 3.
 * Possiede un costruttore in cui sono contenuti i parametri di base della carta, hardcodati per un motivi di tempo.
 * Implementa l'interfaccia visitable, implementando così il metodo accep
 */
public class AlesatorePerLaminaDiRame extends Utensils implements Visitable{

        public AlesatorePerLaminaDiRame(){
            super(3,"alesatore-per-lamina-di-rame", Color.RED,"Muovi un qualsiasi dado nella tua vetrata ignorando le restrizioni di " +
                    "valore Devi rispettare tutte le altre restrizioni di piazzamento");
        }

    public void accept(Visitor c, Activate m){
        c.visit(this,m);
    }
    public void function(Controller controller, ToolCard3 myMessage)throws InvalidValueException, InvalidCellException {
        //unwrapping message

        ArrayList<Integer> messageCont= new ArrayList<>(myMessage.getAttributes());
        String name= myMessage.getPlayer();

        int oldRow=messageCont.get(0);
        int oldCol=messageCont.get(1);
        int newRow=messageCont.get(2);
        int newCol=messageCont.get(3);

        controller.getcAction()
                .workOnSideIgnoreValue(name,oldRow,oldCol,newRow,newCol);

    }
}
