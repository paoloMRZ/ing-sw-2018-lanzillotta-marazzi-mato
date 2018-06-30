package it.polimi.se2018.server.model.card.card_utensils;

import it.polimi.se2018.server.controller.Controller;
import it.polimi.se2018.server.controller.Visitor;
import it.polimi.se2018.server.events.tool_mex.Activate;
import it.polimi.se2018.server.events.tool_mex.ToolCard4;
import it.polimi.se2018.server.exceptions.InvalidCellException;
import it.polimi.se2018.server.exceptions.InvalidValueException;
import it.polimi.se2018.server.model.Color;
import it.polimi.se2018.server.model.card.Visitable;
import it.polimi.se2018.server.model.dice_sachet.Dice;

import java.util.ArrayList;

public class Lathekin extends Utensils implements Visitable{

    public Lathekin(){
        super(4,"lathekin", Color.YELLOW,"Muovi esattamente due dadi, rispettando tutte le restrizioni di piazzamento");
    }
    public void accept(Visitor c, Activate m){
        c.visit(this,m);
    }
    public void function(Controller controller, ToolCard4 myMessage) throws InvalidValueException, InvalidCellException {
        //unwrapping message

        ArrayList<Integer> messageCont= new ArrayList<>(myMessage.getAttributes());
        String name= myMessage.getPlayer();

        int oldRow=messageCont.get(0);
        int oldCol=messageCont.get(1);
        int oldRow2=messageCont.get(4);
        int oldCol2=messageCont.get(5);

        int newRow=messageCont.get(2);
        int newCol=messageCont.get(3);
        int newRow2=messageCont.get(6);
        int newCol2=messageCont.get(7);

        Dice d1=controller.getcAction().takeALookToDie(name,oldRow,oldCol);
        Dice d2=controller.getcAction().takeALookToDie(name,oldRow2,oldCol2);

        Dice firstFromSide= controller.getPlayerByName(name).sidePick(oldRow,oldCol);
        controller.setHoldingADiceMoveInProgress(firstFromSide);

        controller.getcAction()
                .workOnSide(name,d1,newRow,newCol);


        Dice secFromSide= controller.getPlayerByName(name).sidePick(oldRow2,oldCol2);
        controller.setHoldingResDie(secFromSide);//solo per comodità

        controller.getcAction()
                .workOnSide(name,d2,newRow2,newCol2);

    }
}
