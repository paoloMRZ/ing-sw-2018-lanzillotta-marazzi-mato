package it.polimi.se2018.server.model.card.card_utensils;

import it.polimi.se2018.server.controller.Controller;
import it.polimi.se2018.server.controller.Visitor;
import it.polimi.se2018.server.events.tool_mex.Activate;
import it.polimi.se2018.server.exceptions.InvalidActivationException;
import it.polimi.se2018.server.model.Color;
import it.polimi.se2018.server.model.card.Visitable;
import it.polimi.se2018.server.model.dice_sachet.Dice;
import it.polimi.se2018.server.model.reserve.Reserve;

import java.util.ArrayList;
/**
 * Classe che si occupa dell'esecuzione dell'effetto contrattuato dalla carta utensile numero 7.
 * Possiede un costruttore in cui sono contenuti i parametri di base della carta, hardcodati per un motivi di tempo.
 * Implementa l'interfaccia visitable, implementando così il metodo accept che permetterà l'attivazine della funzione,
 * passando per il suo visitor cioè il controller delle carte.
 * -------------------------------------------------------------------------------------------------------------------
 */
public class Martelletto extends  Utensils implements Visitable {

    public Martelletto(){
        super(7,"martelletto", Color.BLUE,"Tira nuovamente tutti i dadi della" +
                " Riserva Questa carta può essera usata solo durante il tuo secondo turno, prima di " +
                "scegliere il secondo dad");
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
     * La funzione è un "draft" di ogni dado della riserva.
     * @param controller controller del gioco
     * @throws InvalidActivationException lanciato nel caso in cui sebbene l'attivazione sia valida, non siamo nel se
     * -condo turno del giocatore.
     */
    public void function(Controller controller) throws InvalidActivationException {
        if(controller.getTurn().getHowManyTurns() == 0 && !controller.getTurn().getDidPlayDie()) {
            Reserve rolled= new Reserve(new ArrayList<>());
            Reserve tmpRes= controller.getcAction().getReserve();
            ArrayList<Dice> toRoll= tmpRes.getDices();

            for(Dice D : toRoll){
                D.rollDice();
                rolled.put(D);
            }
            controller.getcAction().resettingReserve(rolled);
        }
        else throw new InvalidActivationException();
    }
}
