package it.polimi.se2018.server.model.card.card_utensils;

import it.polimi.se2018.server.controller.Controller;
import it.polimi.se2018.server.events.tool_mex.ToolCard9;
import it.polimi.se2018.server.exceptions.InvalidValueException;
import it.polimi.se2018.server.exceptions.invalid_cell_exceptios.InvalidColorException;
import it.polimi.se2018.server.exceptions.invalid_cell_exceptios.InvalidShadeException;
import it.polimi.se2018.server.exceptions.invalid_cell_exceptios.NotEmptyCellException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidSomethingWasNotDoneGood;
import it.polimi.se2018.server.model.Color;

import java.util.ArrayList;

public class RigaInSughero extends Utensils {

    public RigaInSughero(){
        super(9,"RigaInSughero", Color.YELLOW,"Dopo aver scelto un dado, piazzalo in una casella " +
                "che non sia adiacente a un altro dado Devi rispettare tutte le restrizioni di piazzamento");

    }
    public void function(Controller controller, ToolCard9 myMessage) throws InvalidValueException, InvalidShadeException, NotEmptyCellException, InvalidColorException, InvalidSomethingWasNotDoneGood {
        //unwrapping message

        ArrayList<Integer> messageCont= new ArrayList<>(myMessage.getAttributes());
        String name= myMessage.getPlayer();
        int die=messageCont.get(0);
        int row=messageCont.get(1);
        int col=messageCont.get(2);

        controller.getcAction().putNoNeighbours(name,die,row,col);

        controller.getcAction().playerActivatedCard(name);
    }
}
