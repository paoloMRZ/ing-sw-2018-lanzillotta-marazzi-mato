package it.polimi.se2018.server.model.card.card_utensils;

import it.polimi.se2018.server.controller.Controller;
import it.polimi.se2018.server.controller.Visitor;
import it.polimi.se2018.server.events.tool_mex.ToolCard12;
import it.polimi.se2018.server.exceptions.InvalidCellException;
import it.polimi.se2018.server.exceptions.InvalidValueException;
import it.polimi.se2018.server.model.Color;
import it.polimi.se2018.server.model.dice_sachet.Dice;

import java.util.ArrayList;

public class TaglierinaManuale extends Utensils {

    public TaglierinaManuale(){
        super(12,"taglierina-manuale", Color.BLUE,"Muovi fino a due dadi dello stesso colore di un solo dado sul" +
                " Tracciato dei Round Devi rispettare tutte le restrizioni di piazzamento");
    }

    public void accept(Visitor c,  ToolCard12 m){
        c.visit(this,m);
    }

    public void function(Controller controller, ToolCard12 myMessage) throws InvalidValueException, InvalidCellException {
        ArrayList<Integer> messageCont= new ArrayList<>(myMessage.getAttributes());
        String name= myMessage.getPlayer();

        int onBox= messageCont.get(0);
        int inBox= messageCont.get(1);
        int oldRow1=messageCont.get(2);
        int oldCol1=messageCont.get(3);
        int newRow1=messageCont.get(4);
        int newCol1=messageCont.get(5);
        int oldRow2=messageCont.get(6);
        int oldCol2=messageCont.get(7);
        int newRow2=messageCont.get(8);
        int newCol2=messageCont.get(9);

        Dice fromGrid=controller.getcAction().takeALookToDieInGrid(onBox,inBox);
        Dice d1=controller.getcAction().takeALookToDie(name,oldRow1,oldCol1);
        Dice d2=controller.getcAction().takeALookToDie(name,oldRow2,oldCol2);
        //controllo che i dadi selezionati siano consoni alla carta
        //se no lancio eccezione che mi dice che i dati scelti sono sbagliati
        if(fromGrid.getColor()==d1.getColor() && fromGrid.getColor()==d2.getColor() ){

            Dice firstFromSide= controller.getPlayerByName(name).sidePick(oldRow1,oldCol1);
            Dice copyfS= new Dice(firstFromSide.getColor());
            copyfS.manualSet(firstFromSide.getNumber());
            controller.setHoldingADiceMoveInProgress(firstFromSide);

            Dice secFromSide= controller.getPlayerByName(name).sidePick(oldRow2,oldCol2);
            Dice copysS= new Dice(secFromSide.getColor());
            copysS.manualSet(secFromSide.getNumber());
            controller.setHoldingResDie(secFromSide);//solo per comodità


            controller.getcAction()
                    .workOnSide(name,copyfS,newRow1,newCol1);
            controller.getcAction()
                    .workOnSide(name,copysS,newRow2,newCol2);

        }
        else throw new InvalidValueException();

    }
}
