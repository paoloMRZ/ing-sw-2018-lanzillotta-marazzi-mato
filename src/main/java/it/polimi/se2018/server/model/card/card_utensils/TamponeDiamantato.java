package it.polimi.se2018.server.model.card.card_utensils;

import it.polimi.se2018.server.controller.Controller;
import it.polimi.se2018.server.events.tool_mex.ToolCard10;
import it.polimi.se2018.server.exceptions.InvalidValueException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidSomethingWasNotDoneGood;
import it.polimi.se2018.server.model.Color;
import it.polimi.se2018.server.model.dice_sachet.Dice;

public class TamponeDiamantato extends Utensils {

    public TamponeDiamantato(){
        super(10,"TamponeDiamantato", Color.GREEN,"Dopo aver scelto un dado," +
                " giralo sulla faccia opposta 6  diventa 1, 5 diventa 2, 4 diventa 3 ecc.");
    }

    public void function(Controller controller, ToolCard10 myMessage) throws InvalidValueException, InvalidSomethingWasNotDoneGood {
        int die=myMessage.getDie();
        String name= myMessage.getPlayer();

        controller.getcAction().putBackInReserve(
                flip(controller.getcAction().pickFromReserve(die)));

        controller.getcAction().playerActivatedCard(name);
    }

    private Dice flip(Dice D){
        int val=D.getNumber();
        Dice tmp= new Dice(D.getColor(),7-val);

        return tmp;
    }
}
