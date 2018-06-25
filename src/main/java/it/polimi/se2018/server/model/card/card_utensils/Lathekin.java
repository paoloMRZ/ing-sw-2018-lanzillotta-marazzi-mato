package it.polimi.se2018.server.model.card.card_utensils;

import it.polimi.se2018.server.controller.Controller;
import it.polimi.se2018.server.controller.Visitor;
import it.polimi.se2018.server.events.tool_mex.ToolCard4;
import it.polimi.se2018.server.exceptions.InvalidCellException;
import it.polimi.se2018.server.exceptions.InvalidValueException;
import it.polimi.se2018.server.model.Color;

import java.util.ArrayList;

public class Lathekin extends Utensils {

    public Lathekin(){
        super(4,"lathekin", Color.YELLOW,"Muovi esattamente due dadi, rispettando tutte le restrizioni di piazzamento");
    }
    public void accept(Visitor c, ToolCard4 m){
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

        controller.getcAction()
                .moveStuffOnSide(name,oldRow,oldCol,newRow,newCol);
        controller.getcAction()
                .moveStuffOnSide(name,oldRow2,oldCol2,newRow2,newCol2);

        controller.getcAction().playerActivatedCard(controller.getTurn().getName(),this.getPreviousCost());

    }
}
