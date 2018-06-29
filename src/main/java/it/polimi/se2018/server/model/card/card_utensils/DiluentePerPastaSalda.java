package it.polimi.se2018.server.model.card.card_utensils;

import it.polimi.se2018.server.controller.Controller;
import it.polimi.se2018.server.controller.Visitor;
import it.polimi.se2018.server.events.responses.SuccessColor;
import it.polimi.se2018.server.events.tool_mex.Activate;
import it.polimi.se2018.server.events.tool_mex.ToolCard11;
import it.polimi.se2018.server.events.tool_mex.ToolCard11Bis;
import it.polimi.se2018.server.exceptions.InvalidCellException;
import it.polimi.se2018.server.exceptions.InvalidValueException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidSomethingWasNotDoneGood;
import it.polimi.se2018.server.model.Color;
import it.polimi.se2018.server.model.card.Visitable;
import it.polimi.se2018.server.model.dice_sachet.Dice;

import java.util.ArrayList;

public class DiluentePerPastaSalda extends Utensils  implements Visitable {

    public DiluentePerPastaSalda(){
        super(11,"diluente-per-pasta-salda", Color.PURPLE,"Dopo aver scelto un dado, riponilo nel Sacchetto, poi pescane uno dal" +
                " Sacchetto Scegli il valore del nuovo dado e piazzalo, " +
                "rispettando tutte le restrizioni di piazzamento");
    }

    public void accept(Visitor c, Activate m){
        c.visit(this,m);
    }


    public String function(Controller controller, ToolCard11 myMessage) throws InvalidValueException, InvalidSomethingWasNotDoneGood {
        int die= myMessage.getDie();
        Dice toReput=controller.getcAction().pickFromReserve(die);
        controller.setHoldingResDie(toReput);
        Dice d= new Dice(toReput.getColor());
        d.manualSet(toReput.getNumber());
        Dice picked=controller.getcAction().extractDieAgain(d);
        controller.setHoldingADiceMoveInProgress(picked);
        return picked.getColor().name();
    }

    public void function(Controller controller, ToolCard11Bis myMessage) throws InvalidValueException, InvalidCellException {
        ArrayList<Integer> messageCont= new ArrayList<>(myMessage.getAttributes());
        String name= myMessage.getPlayer();
        int value=messageCont.get(0);
        int row=messageCont.get(1);
        int col=messageCont.get(2);

        Dice dadozzo= controller.getHoldingADiceMoveInProgress();
        Dice copyofDadozzo=new Dice(dadozzo.getColor());
        copyofDadozzo.manualSet(value);
        controller.getcAction().workOnSide(name,copyofDadozzo,row,col);

    }


}
