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

public class TamponeDiamantato extends Utensils {

    public TamponeDiamantato(){
        super(10,"tampone-diamantato", Color.GREEN,"Dopo aver scelto un dado," +
                " giralo sulla faccia opposta 6  diventa 1, 5 diventa 2, 4 diventa 3 ecc.");
    }

    public void accept(Visitor c,Activate m){
        c.visit(this,m);
    }
    public void function(Controller controller, ToolCard10 myMessage) throws InvalidValueException, InvalidSomethingWasNotDoneGood {
        int die=myMessage.getDie();

        controller.setHoldingResDie(controller.getcAction().pickFromReserve(die));

        Dice d= new Dice(controller.getHoldingResDie().getColor());
        d.manualSet(controller.getHoldingResDie().getNumber());

        controller.getcAction().putBackInReserve(flip(d));

    }

    private Dice flip(Dice d){
        int val=d.getNumber();
        return  new Dice(d.getColor(),7-val);
    }
}
