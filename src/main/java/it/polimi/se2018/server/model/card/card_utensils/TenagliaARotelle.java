package it.polimi.se2018.server.model.card.card_utensils;

import it.polimi.se2018.server.controller.Controller;
import it.polimi.se2018.server.controller.Visitor;
import it.polimi.se2018.server.events.tool_mex.Activate;
import it.polimi.se2018.server.events.tool_mex.ToolCard8;
import it.polimi.se2018.server.exceptions.InvalidActivationException;
import it.polimi.se2018.server.exceptions.InvalidCellException;
import it.polimi.se2018.server.exceptions.InvalidHowManyTimes;
import it.polimi.se2018.server.exceptions.InvalidValueException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidSomethingWasNotDoneGood;
import it.polimi.se2018.server.model.Color;
import it.polimi.se2018.server.model.card.Visitable;
import it.polimi.se2018.server.model.dice_sachet.Dice;

import java.util.ArrayList;
/**
 * Classe che si occupa dell'esecuzione dell'effetto contrattuato dalla carta utensile numero 8.
 * Possiede un costruttore in cui sono contenuti i parametri di base della carta, hardcodati per un motivi di tempo.
 * Implementa l'interfaccia visitable, implementando così il metodo accept che permetterà l'attivazine della funzione,
 * passando per il suo visitor cioè il controller delle carte.
 * -------------------------------------------------------------------------------------------------------------------
 */
public class TenagliaARotelle extends Utensils implements Visitable{

    public TenagliaARotelle(){
        super(8,"tenaglia-a-rotelle", Color.RED,"Dopo il tuo primo turno scegli immediatamente" +
                " un altro dado Salta il tuo secondo turno in questo round");
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
     * Metodo che permette un secondo piazzamento a un giocatore nel suo primo turno.
     * @param controller controller del gioco
     * @param myMessage evento contenente i parametri utili alla giocata
     * @throws InvalidValueException se i parametri sono scorretti, ad esempio le coordinate.
     * @throws InvalidSomethingWasNotDoneGood lanciata per eccezioni inaspettate.
     * @throws InvalidCellException se la cella per il piazzamento non è valida per la richiesta.
     * @throws InvalidActivationException se l'uso non avviene nel primo turno.
     * @throws InvalidHowManyTimes se il giocatore per una cattiva progettazione rischia di giocatore troppi turni.
     */
    public void function(Controller controller,ToolCard8 myMessage) throws InvalidValueException, InvalidSomethingWasNotDoneGood, InvalidCellException, InvalidActivationException, InvalidHowManyTimes {
        //mi deve interessare che sia soo nel primo turno per vietare il secondo
        String name= myMessage.getPlayer();
        String compare= controller.getTurn().getName();
        if(compare.equals(name)){
            if(controller.getTurn().getHowManyTurns()==1){
            ArrayList<Integer> messageCont= new ArrayList<>(myMessage.getAttributes());

            int die=messageCont.get(0);
            int row=messageCont.get(1);
            int col=messageCont.get(2);

                controller.setHoldingResDie(controller.getcAction().pickFromReserve(die));

                Dice d= new Dice(controller.getHoldingResDie().getColor());
                d.manualSet(controller.getHoldingResDie().getNumber());

            controller.getcAction().workOnSide(name,d,row,col);
            controller.getTurn().reductor();

            }
            else throw new InvalidActivationException();
        }
        else throw  new InvalidValueException();
    }
}
