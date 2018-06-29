package it.polimi.se2018.server.model.card.card_utensils;


import it.polimi.se2018.server.controller.Controller;
import it.polimi.se2018.server.controller.Visitor;
import it.polimi.se2018.server.events.tool_mex.Activate;
import it.polimi.se2018.server.events.tool_mex.ToolCard5;
import it.polimi.se2018.server.exceptions.InvalidCellException;
import it.polimi.se2018.server.exceptions.InvalidValueException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidSomethingWasNotDoneGood;
import it.polimi.se2018.server.model.Color;
import it.polimi.se2018.server.model.card.Visitable;
import it.polimi.se2018.server.model.dice_sachet.Dice;

import java.util.ArrayList;

public class TaglierinaCircolare extends Utensils implements Visitable {

    public TaglierinaCircolare() {
        super(5, "taglierina-circolare", Color.GREEN, "Dopo aver scelto un dado, scambia quel dado con un dado sul Tracciato dei Round");
    }

    public void accept(Visitor c, Activate m){
        c.visit(this,m);
    }

    public void function(Controller controller, ToolCard5 myMessage) throws InvalidValueException, InvalidSomethingWasNotDoneGood {
        ArrayList<Integer> messageCont= new ArrayList<>(myMessage.getAttributes());

        int indexDie= messageCont.get(0);
        int boxOnGrid=messageCont.get(1);
        int boxInGrid=messageCont.get(2);


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
