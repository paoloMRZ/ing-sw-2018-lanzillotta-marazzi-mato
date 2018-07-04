package it.polimi.se2018.server.model.card.card_utensils;

import it.polimi.se2018.server.controller.Controller;
import it.polimi.se2018.server.controller.Visitor;
import it.polimi.se2018.server.events.tool_mex.Activate;
import it.polimi.se2018.server.events.tool_mex.ToolCard9;
import it.polimi.se2018.server.exceptions.InvalidValueException;
import it.polimi.se2018.server.exceptions.invalid_cell_exceptios.InvalidColorException;
import it.polimi.se2018.server.exceptions.invalid_cell_exceptios.InvalidShadeException;
import it.polimi.se2018.server.exceptions.invalid_cell_exceptios.NotEmptyCellException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidSomethingWasNotDoneGood;
import it.polimi.se2018.server.model.Color;
import it.polimi.se2018.server.model.card.Visitable;

import java.util.ArrayList;

/**
 * Classe che si occupa dell'esecuzione dell'effetto contrattuato dalla carta utensile numero 9.
 * Possiede un costruttore in cui sono contenuti i parametri di base della carta, hardcodati per un motivi di tempo.
 * Implementa l'interfaccia visitable, implementando così il metodo accept che permetterà l'attivazine della funzione,
 * passando per il suo visitor cioè il controller delle carte.
 * -------------------------------------------------------------------------------------------------------------------
 */
public class RigaInSughero extends Utensils implements Visitable {

    public RigaInSughero(){
        super(9,"riga-in-sughero", Color.YELLOW,"Dopo aver scelto un dado, piazzalo in una casella " +
                "che non sia adiacente a un altro dado Devi rispettare tutte le restrizioni di piazzamento");

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
     * Metodo che effettua un piazzamento particolare.
     * @param controller controller del gioco
     * @param myMessage evento contenente i parametri utili alla giocata
     * @throws InvalidShadeException tipo di particolare di eccezione di cella.
     * @throws NotEmptyCellException tipo di particolare di eccezione di cella.
     * @throws InvalidColorException tipo di particolare di eccezione di cella.
     * @throws InvalidValueException lanciato nel caso in cui i parametri siano scorretti.
     * @throws InvalidSomethingWasNotDoneGood lanciato in casi di mala progettazione.
     */
    public void function(Controller controller, ToolCard9 myMessage) throws InvalidValueException, InvalidShadeException, NotEmptyCellException, InvalidColorException, InvalidSomethingWasNotDoneGood {

        ArrayList<Integer> messageCont= new ArrayList<>(myMessage.getAttributes());
        String name= myMessage.getPlayer();
        int die=messageCont.get(0);
        int row=messageCont.get(1);
        int col=messageCont.get(2);

        controller.getcAction().putNoNeighbours(name,die,row,col);

    }
}
