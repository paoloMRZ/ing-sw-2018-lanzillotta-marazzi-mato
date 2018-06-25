package it.polimi.se2018.server.model.card.card_utensils;

import it.polimi.se2018.server.controller.Controller;
import it.polimi.se2018.server.controller.Visitor;
import it.polimi.se2018.server.events.tool_mex.ToolCard2;
import it.polimi.se2018.server.exceptions.InvalidCellException;
import it.polimi.se2018.server.exceptions.InvalidValueException;
import it.polimi.se2018.server.model.Color;

import java.util.ArrayList;

public class PennelloPerEglomise extends Utensils {

    public PennelloPerEglomise(){
        super(2,"pennello-per-eglomise", Color.BLUE,"Muovi un qualsiasi dado nella tua vetrata" +
                " ignorando le restrizioni di colore... " +
                "Devi rispettare tutte le altre restrizioni di piazzamento");
    }

    public void accept(Visitor c, ToolCard2 m){
        c.visit(this,m);
    }
    public void function(Controller controller,ToolCard2 myMessage)throws InvalidValueException, InvalidCellException {
        //unwrapping message

        ArrayList<Integer> messageCont= new ArrayList<>(myMessage.getAttributes());
        String name= myMessage.getPlayer();

        int oldRow=messageCont.get(0);
        int oldCol=messageCont.get(1);
        int newRow=messageCont.get(2);
        int newCol=messageCont.get(3);

        controller.getcAction()
                .workOnSideIgnoreColor(name,oldRow,oldCol,newRow,newCol);

        controller.getcAction().playerActivatedCard(controller.getTurn().getName(),this.getPreviousCost());
    }

}
