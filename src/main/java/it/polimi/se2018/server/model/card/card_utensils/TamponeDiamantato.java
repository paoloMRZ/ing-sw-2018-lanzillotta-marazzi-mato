package it.polimi.se2018.server.model.card.card_utensils;

import it.polimi.se2018.server.controller.Controller;
import it.polimi.se2018.server.controller.Visitor;
import it.polimi.se2018.server.events.tool_mex.Activate;
import it.polimi.se2018.server.events.tool_mex.ToolCard10;
import it.polimi.se2018.server.exceptions.InvalidValueException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidSomethingWasNotDoneGood;
import it.polimi.se2018.server.model.Color;
import it.polimi.se2018.server.model.card.Visitable;
import it.polimi.se2018.server.model.dice_sachet.Dice;
/**
 * Classe che si occupa dell'esecuzione dell'effetto contrattuato dalla carta utensile numero 10.
 * Possiede un costruttore in cui sono contenuti i parametri di base della carta, hardcodati per un motivi di tempo.
 * Implementa l'interfaccia visitable, implementando così il metodo accept che permetterà l'attivazine della funzione,
 * passando per il suo visitor cioè il controller delle carte.
 * -------------------------------------------------------------------------------------------------------------------
 */
public class TamponeDiamantato extends Utensils implements Visitable {

    public TamponeDiamantato(){
        super(10,"tampone-diamantato", Color.GREEN,"Dopo aver scelto un dado," +
                " giralo sulla faccia opposta 6  diventa 1, 5 diventa 2, 4 diventa 3 ecc.");
    }

    /**
     * Metodo dell'interfaccia visitable che viene overridato.
     * @param c reference del visitatore.
     * @param m evento che richiede l'uso di una carta.
     */
    public void accept(Visitor c,Activate m){
        c.visit(this,m);
    }

    /**
     * Metodo che cambia il valore di un dado che scegliamo nella riserva, e lo pone uguale al valore opposto.
     * @param controller controller del gioco
     * @param myMessage evento contenente i parametri utili alla giocata
     * @throws InvalidValueException lanciato nel caso in cui i parametri siano scorretti.
     * @throws InvalidSomethingWasNotDoneGood lanciato in casi di mala progettazione.
     */
    public void function(Controller controller, ToolCard10 myMessage) throws InvalidValueException, InvalidSomethingWasNotDoneGood {
        int die=myMessage.getDie();

        controller.setHoldingResDie(controller.getcAction().pickFromReserve(die));

        Dice d= new Dice(controller.getHoldingResDie().getColor());
        d.manualSet(controller.getHoldingResDie().getNumber());

        controller.getcAction().putBackInReserve(flip(d));

    }

    /**
     * Metodo che volta un dado sulla faccia opposta.
     * @param d reference del dado da voltare.
     * @return un dado con le caratteristiche richieste.
     */
    private Dice flip(Dice d){
        int val=d.getNumber();
        return  new Dice(d.getColor(),7-val);
    }
}
