package it.polimi.se2018.server.model.card.card_utensils;


import it.polimi.se2018.server.controller.Controller;
import it.polimi.se2018.server.events.tool_mex.ToolCard5;
import it.polimi.se2018.server.exceptions.InvalidCellException;
import it.polimi.se2018.server.exceptions.InvalidValueException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidSomethingWasNotDoneGood;
import it.polimi.se2018.server.model.Color;
import it.polimi.se2018.server.model.dice_sachet.Dice;

import java.util.ArrayList;

public class TaglerinaCircolare extends Utensils{

    public TaglerinaCircolare(){
        super(5,"TaglerinaCircolare", Color.GREEN,"Dopo aver scelto un dado, scambia quel dado con un dado sul Tracciato dei Round");
    }

    //todo discutere cosa fare con quel dado perchè l'algoritmo rimane in sospseso
    public void function(Controller controller, ToolCard5 myMessage) throws InvalidValueException, InvalidSomethingWasNotDoneGood {
        ArrayList<Integer> messageCont= new ArrayList<>(myMessage.getAttributes());
        String name= myMessage.getPlayer();
        int indexDie= messageCont.get(0);
        int boxOnGrid=messageCont.get(1);
        int boxInGrid=messageCont.get(2);


        Dice fromGrid=controller.getcAction().takeFromGrid(boxOnGrid,boxInGrid);
        Dice fromReserve= controller.getcAction().pickFromReserve(indexDie);

        controller.getcAction().putOnGrid(boxOnGrid,fromReserve);
        controller.getcAction().putBackInReserve(fromGrid);


        controller.getcAction().playerActivatedCard(name);


    }

}
