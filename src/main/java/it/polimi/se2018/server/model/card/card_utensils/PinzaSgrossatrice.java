package it.polimi.se2018.server.model.card.card_utensils;

import it.polimi.se2018.server.controller.Controller;
import it.polimi.se2018.server.controller.Visitor;
import it.polimi.se2018.server.events.tool_mex.Activate;
import it.polimi.se2018.server.events.tool_mex.MoreThanSimple;
import it.polimi.se2018.server.exceptions.InvalidValueException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidSomethingWasNotDoneGood;
import it.polimi.se2018.server.model.Color;
import it.polimi.se2018.server.model.card.Visitable;
import it.polimi.se2018.server.model.dice_sachet.Dice;

/**
 * Classe che si occupa dell'esecuzione dell'effetto contrattuato dalla carta utensile numero 1.
 * Possiede un costruttore in cui sono contenuti i parametri di base della carta, hardcodati per un motivi di tempo.
 * Implementa l'interfaccia visitable, implementando così il metodo accept che permetterà l'attivazine della funzione,
 * passando per il suo visitor cioè il controller delle carte.
 * -------------------------------------------------------------------------------------------------------------------
 */
public class PinzaSgrossatrice extends Utensils implements Visitable{

    public PinzaSgrossatrice(){
        super(1,"pinza-sgrossatrice", Color.PURPLE,"Dopo aver scelto un dado, " +
                "aumenta o dominuisci il valore del dado scelto di 1 " +
                "Non puoi cambiare un 6 in 1 o un 1 in 6");

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
     * Metodo che cambia aggiunge +1 o -1 al valore di un dado in base a un parametro che passiamo via messaggio
     * a un dado che scegliamo nella riserva
     * @param controller controller del gioco
     * @param myMessage evento contenente i parametri utili alla giocata
     * @throws InvalidValueException lanciato nel caso in cui i parametri siano scorretti.
     * @throws InvalidSomethingWasNotDoneGood lanciato in casi di mala progettazione.
     */
    public void function(Controller controller, MoreThanSimple myMessage) throws InvalidValueException, InvalidSomethingWasNotDoneGood {

        int die=myMessage.getDie();
        boolean up=myMessage.getDecision();

        controller.setHoldingResDie(controller.getcAction().pickFromReserve(die));

        Dice d=upper(controller.getHoldingResDie(),up);

        controller.getcAction().putBackInReserve(d);


    }

    /**
     * Metodo privato che si occupa del camabiamento del valore del dado che gli viene passato in base a un booleano.
     * @param d reference del dado da cambiare
     * @param decision true se +1 false -1
     * @return reference di un dado con i valori desiderati
     * @throws InvalidValueException se il dado non può subire le modifiche.
     */
    private Dice upper(Dice d,boolean decision) throws InvalidValueException{
        Dice tmp = new Dice(d.getColor());
        if(decision) tmp.manualSet(d.getNumber()+1);
        else tmp.manualSet(d.getNumber()-1);
        return tmp;
    }
}
