package it.polimi.se2018.server.model.card.card_utensils;

import it.polimi.se2018.server.controller.Controller;
import it.polimi.se2018.server.controller.Visitor;
import it.polimi.se2018.server.events.tool_mex.ToolCard8;
import it.polimi.se2018.server.exceptions.InvalidActivationException;
import it.polimi.se2018.server.exceptions.InvalidCellException;
import it.polimi.se2018.server.exceptions.InvalidHowManyTimes;
import it.polimi.se2018.server.exceptions.InvalidValueException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidSomethingWasNotDoneGood;
import it.polimi.se2018.server.model.Color;
import it.polimi.se2018.server.model.dice_sachet.Dice;

import java.util.ArrayList;

public class TenagliaARotelle extends Utensils {

    public TenagliaARotelle(){
        super(8,"tenaglia-a-rotelle", Color.RED,"Dopo il tuo primo turno scegli immediatamente" +
                " un altro dado Salta il tuo secondo turno in questo round");
    }
    public void accept(Visitor c, ToolCard8 m){
        c.visit(this,m);
    }
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
