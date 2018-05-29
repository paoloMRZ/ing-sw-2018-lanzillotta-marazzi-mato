package it.polimi.se2018.server.model.card.card_utensils;

import it.polimi.se2018.server.controller.Controller;
import it.polimi.se2018.server.events.tool_mex.ToolCard6;
import it.polimi.se2018.server.events.tool_mex.ToolCard6Bis;
import it.polimi.se2018.server.exceptions.InvalidCellException;
import it.polimi.se2018.server.exceptions.InvalidValueException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidSomethingWasNotDoneGood;
import it.polimi.se2018.server.model.Color;
import it.polimi.se2018.server.model.dice_sachet.Dice;

import java.util.ArrayList;

public class PennelloPerPastaSalda extends Utensils {

   public PennelloPerPastaSalda(){
       super(6,"PennelloPerPastaSalda", Color.PURPLE,"Dopo aver scelto un dado, " +
               "tira nuovamente quel dado Se non puoi piazzarlo, " +
               "riponilo nella Riserva");
   }
    public void function(Controller controller, ToolCard6 myMessage) throws InvalidValueException, InvalidSomethingWasNotDoneGood {
        String name= myMessage.getPlayer();

        int indexDie= myMessage.getDie();
        Dice picked = controller.getcAction().pickFromReserve(indexDie);
        picked.rollDice();
        controller.getcAction().setHoldingADiceMoveInProgress(picked);
        controller.getcAction().responder("SuccesValue","",String.valueOf(picked.getNumber()));
   }

    public void function(Controller controller, ToolCard6Bis myMessage) throws InvalidValueException, InvalidCellException {
        String name= myMessage.getPlayer();

        if(myMessage.getDecision()){
            controller.getcAction().putBackInReserve();
            controller.getcAction().playerActivatedCard(name);
        }
        else{
            ArrayList<Integer> cont= myMessage.getAttributes();
            int row=cont.get(0);
            int col=cont.get(1);
            controller.getcAction().workOnSide(name,controller.getcAction().getHoldingADiceMoveInProgress(),row,col);
            controller.getcAction().playerActivatedCard(name);
        }
    }


}
