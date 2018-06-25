package it.polimi.se2018.server.model.card.card_utensils;

import it.polimi.se2018.server.controller.Controller;
import it.polimi.se2018.server.controller.Visitor;
import it.polimi.se2018.server.events.tool_mex.ToolCard7;
import it.polimi.se2018.server.exceptions.InvalidActivationException;
import it.polimi.se2018.server.exceptions.InvalidValueException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidSomethingWasNotDoneGood;
import it.polimi.se2018.server.model.Color;
import it.polimi.se2018.server.model.dice_sachet.Dice;
import it.polimi.se2018.server.model.reserve.Reserve;

import java.util.ArrayList;

public class Martelletto extends  Utensils {

    public Martelletto(){
        super(7,"martelletto", Color.BLUE,"Tira nuovamente tutti i dadi della" +
                " Riserva Questa carta può essera usata solo durante il tuo secondo turno, prima di " +
                "scegliere il secondo dad");
    }
    public void accept(Visitor c, ToolCard7 m){
        c.visit(this,m);
    }
    public void function(Controller controller) throws InvalidValueException, InvalidActivationException {
        if(controller.getTurn().getHowManyTurns() == 0 && !controller.getTurn().getDidPlayDie()) {
            Reserve rolled= new Reserve(new ArrayList<Dice>());
            Reserve tmpRes= controller.getcAction().getReserve();
            ArrayList<Dice> toRoll= tmpRes.getDices();

            for(Dice D : toRoll){
                D.rollDice();
                rolled.put(D);
            }
            controller.getcAction().resettingReserve(rolled);
            controller.getcAction().playerActivatedCard(controller.getTurn().getName(),this.getPreviousCost());
        }
        else throw new InvalidActivationException();
    }
}
