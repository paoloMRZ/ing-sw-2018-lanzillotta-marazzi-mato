package it.polimi.se2018.server.model.card.card_utensils;

import it.polimi.se2018.server.controller.Controller;
import it.polimi.se2018.server.controller.Visitor;
import it.polimi.se2018.server.events.tool_mex.MoreThanSimple;
import it.polimi.se2018.server.exceptions.InvalidCellException;
import it.polimi.se2018.server.exceptions.InvalidValueException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidCoordinatesException;
import it.polimi.se2018.server.model.Color;
import it.polimi.se2018.server.model.dice_sachet.Dice;
import it.polimi.se2018.server.model.reserve.Reserve;

import java.util.ArrayList;

public class PinzaSgrossatrice extends Utensils {
//OVERVIEW classe di ...
    public PinzaSgrossatrice(){
        super(1,"PinzaSgrossatrice", Color.PURPLE,"Dopo aver scelto un dado, " +
                "aumenta o dominuisci il valore del dado scelto di 1 " +
                "Non puoi cambiare un 6 in 1 o un 1 in 6");

    }
    //TODO fare metodi di sostegno


    public void function(Controller controller, MoreThanSimple myMessage)throws InvalidValueException, InvalidCellException {
        //unwrapping message

        ArrayList<Integer> messageCont= new ArrayList<>(myMessage.getAttributes());
        String name= myMessage.getPlayer();
        int die=messageCont.get(0);
        boolean up=myMessage.getDecision();
        int row=messageCont.get(1);
        int col=messageCont.get(2);

        controller.getcAction()
            .workOnSide(name,   upper(controller.getcAction().pickFromReserve(die),up),     row,    col);

        controller.getcAction().playerActivatedCard(name);
    }

    private Dice upper(Dice d,boolean decision) throws InvalidValueException{
        if(decision) d.manualSet(d.getNumber()+1);
        else d.manualSet(d.getNumber()-1);
        return d;
    }
}
