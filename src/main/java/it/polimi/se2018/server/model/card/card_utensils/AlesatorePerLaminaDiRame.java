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
 * Implementa l'interfaccia visitable, implementando così il metodo accept che permetterà l'attivazine della funzione,
 * passando per il suo visitor cioè il controller delle carte.
 * -------------------------------------------------------------------------------------------------------------------
 */
public class AlesatorePerLaminaDiRame extends Utensils implements Visitable{

        public AlesatorePerLaminaDiRame(){
            super(3,"alesatore-per-lamina-di-rame", Color.RED,"Muovi un qualsiasi dado nella tua vetrata ignorando le restrizioni di " +
                    "valore Devi rispettare tutte le altre restrizioni di piazzamento");
        }

    /**
     * Metodo dell'interfaccia visitable che viene overridato.
     * @param c reference del visitatore.
     * @param m evento che richiede l'uso di una carta.
     */
    public void accept(Visitor c, Activate m){
        c.visit(this,m);
    }

    /**
     * Metodo che attua lo spostamento di un dado nella carta schema,
     * ignorando restrizioni di valore. Prende un evento tool3.
     * -------------------------------------------------------------------------------------
     * Si prendono le coordinate vecchie e quelle nuove che si vogliono assegnare al dado.
     * Il tutto avviene secondo le metodologie di workOnSideIgnoreValue, nel controllerAction.
     * @param controller controller del gioco
     * @param myMessage evento contenente i parametri utili alla giocata
     * @throws InvalidValueException lanciato nel caso in cui i parametri siano scorretti.
     * @throws InvalidCellException lanciato nel caso in cui la cella richiesta per il piazzamento sia occupata o non buona.
     */
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
