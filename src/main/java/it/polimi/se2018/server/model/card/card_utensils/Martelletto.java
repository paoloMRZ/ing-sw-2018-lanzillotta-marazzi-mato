package it.polimi.se2018.server.model.card.card_utensils;

import it.polimi.se2018.server.controller.Controller;
import it.polimi.se2018.server.exceptions.InvalidActivationException;
import it.polimi.se2018.server.exceptions.InvalidValueException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidSomethingWasNotDoneGood;
import it.polimi.se2018.server.model.Color;
import it.polimi.se2018.server.model.dice_sachet.Dice;
import it.polimi.se2018.server.model.reserve.Reserve;

import java.util.ArrayList;

public class Martelletto extends  Utensils {

    public Martelletto(){
        super(7,"Martelletto", Color.BLUE,"Tira nuovamente tutti i dadi della" +
                " Riserva Questa carta può essera usata solo durante il tuo secondo turno, prima di " +
                "scegliere il secondo dad");
    }
    public void function(Controller controller) throws InvalidValueException, InvalidActivationException {
        if(controller.getcTurn().getTurn().getHowManyTurns() == 1 && !controller.getcTurn().getTurn().getDidPlayDie()) {
            Reserve rolled= new Reserve(new ArrayList<Dice>());
            Reserve tmpRes= controller.getcAction().getReserve();
            ArrayList<Dice> toRoll= tmpRes.getDices();

            for(Dice D : toRoll){
                D.rollDice();
                rolled.put(D);
            }
            controller.getcAction().resettingReserve(rolled);
            controller.getcAction().playerActivatedCard(controller.getcTurn()
                                                                        .getTurn().getName());
        }
        else throw new InvalidActivationException();
    }
}
