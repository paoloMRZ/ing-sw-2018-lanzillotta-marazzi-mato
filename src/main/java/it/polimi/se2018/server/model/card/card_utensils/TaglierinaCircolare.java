package it.polimi.se2018.server.model.card.card_utensils;


import it.polimi.se2018.server.controller.Controller;
import it.polimi.se2018.server.controller.Visitor;
import it.polimi.se2018.server.events.tool_mex.Activate;
import it.polimi.se2018.server.events.tool_mex.ToolCard5;
import it.polimi.se2018.server.exceptions.InvalidValueException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidSomethingWasNotDoneGood;
import it.polimi.se2018.server.model.Color;
import it.polimi.se2018.server.model.card.Visitable;
import it.polimi.se2018.server.model.dice_sachet.Dice;

import java.util.ArrayList;

/**
 * Classe che si occupa dell'esecuzione dell'effetto contrattuato dalla carta utensile numero 5.
 * Possiede un costruttore in cui sono contenuti i parametri di base della carta, hardcodati per un motivi di tempo.
 * Implementa l'interfaccia visitable, implementando così il metodo accept che permetterà l'attivazine della funzione,
 * passando per il suo visitor cioè il controller delle carte.
 * -------------------------------------------------------------------------------------------------------------------
 */
public class TaglierinaCircolare extends Utensils implements Visitable {

    public TaglierinaCircolare() {
        super(5, "taglierina-circolare", Color.GREEN, "Dopo aver scelto un dado, scambia quel dado con un dado sul Tracciato dei Round");
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
     * Metodo che scambia un dado della riserva con uno della roundgrid.
     * @param controller controller del gioco
     * @param myMessage evento contenente i parametri utili alla giocata
     * @throws InvalidValueException lanciato nel caso in cui i parametri siano scorretti.
     * @throws InvalidSomethingWasNotDoneGood lanciato in casi inaspettati.
     */
    public void function(Controller controller, ToolCard5 myMessage) throws InvalidValueException, InvalidSomethingWasNotDoneGood {
        ArrayList<Integer> messageCont= new ArrayList<>(myMessage.getAttributes());

        int indexDie= messageCont.get(0);
        int boxOnGrid=messageCont.get(1);
        int boxInGrid=messageCont.get(2);
        //si poteva fare in un secondo modo ma ho preferito questo perchè c'è un netta separazione
        //fra le azioni e il ripristino della situazione iniziale sembra più semplice
        //(intendo con copie e rimozione successiva, ma gli errori se saltano saltano subito)

        Dice fromGrid=controller.getcAction().takeFromGrid(boxOnGrid,boxInGrid);
        controller.setHoldingRoundGDie(fromGrid);
        Dice copyfG= new Dice(fromGrid.getColor());
        copyfG.manualSet(fromGrid.getNumber());


        Dice fromReserve= controller.getcAction().pickFromReserve(indexDie);
        Dice copyfR= new Dice(fromReserve.getColor());
        copyfR.manualSet(fromReserve.getNumber());
        controller.setHoldingResDie(fromReserve);

        controller.getcAction().putOnGrid(boxOnGrid,copyfR);
        controller.getcAction().putBackInReserve(copyfG);

    }

}
