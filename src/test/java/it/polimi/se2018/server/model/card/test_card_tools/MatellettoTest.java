package it.polimi.se2018.server.model.card.test_card_tools;

import it.polimi.se2018.server.controller.Controller;
import it.polimi.se2018.server.exceptions.InvalidHowManyTimes;
import it.polimi.se2018.server.exceptions.InvalidValueException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidSomethingWasNotDoneGood;
import it.polimi.se2018.server.model.Color;
import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.server.model.Table;
import it.polimi.se2018.server.model.card.card_utensils.Martelletto;
import it.polimi.se2018.server.model.dice_sachet.Dice;
import it.polimi.se2018.server.model.dice_sachet.DiceSachet;
import it.polimi.se2018.server.model.reserve.Reserve;
import org.junit.Before;
import org.junit.Test;
import sun.font.TrueTypeFont;

import java.util.ArrayList;
import java.util.Arrays;

import static junit.framework.TestCase.fail;

public class MatellettoTest {
    private Reserve tester=null;
    private Martelletto mar= new Martelletto();
    private ArrayList<Player> players= new ArrayList<>(Arrays.asList(
            new Player(null,0,"1"),
            new Player(null,0,"3"),
            new Player(null,0,"4"),
            new Player(null,0,"2")   ));

    private Controller controller=new Controller(players);
    private Table lobby = new Table(null,null,players,new DiceSachet(),null,null);

    private ArrayList<Integer> countingBefore=new ArrayList<>();
    private ArrayList<Color> colorsBefore=new ArrayList<>();

    private ArrayList<Integer> countingAfter=new ArrayList<>();
    private ArrayList<Color> colorsAfter=new ArrayList<>();



    @Before
    public void settings() throws InvalidValueException, InvalidHowManyTimes {
        tester=lobby.createReserve();
        controller.getcAction().resettingReserve(tester);
        controller.getcTurn().setRound();
        controller.getcTurn().getTurn().restoreValues();
        controller.getcTurn().getTurn().reductor();
        controller.getcTurn().getTurn().reductor();

    }
    @Test
    public void counting(){

        try {
            counter(true);
            //non uso un messaggio perchè non ha una funzionalità vera e propria
            mar.function(controller);
            counter(false);
            comparing();
        }
        catch(Exception e){
            fail("ha lanciato eccezione"+ e);
        }

    }

    private void counter(boolean isBefore){
        ArrayList<Dice> container= tester.getDices();
                for (Dice d: container) {
                if(isBefore){
                    if(colorsBefore.contains(d.getColor())){
                        int where=colorsBefore.indexOf(d.getColor());
                        countingBefore.set(where,countingBefore.get(where)+1);
                    }
                    else{
                        colorsBefore.add(d.getColor());
                        countingBefore.add(1);
                    }
                }
                else{
                    if(colorsAfter.contains(d.getColor())){
                        int where=colorsAfter.indexOf(d.getColor());
                        countingAfter.set(where,countingAfter.get(where)+1);
                    }
                    else{
                        colorsAfter.add(d.getColor());
                        countingAfter.add(1);
                    }
                }
        }
    }
    private void comparing() throws InvalidSomethingWasNotDoneGood {
        for(Color c: colorsBefore){
            if(countingBefore.get(colorsBefore.indexOf(c))!= countingAfter.get(colorsAfter.indexOf(c))) throw new InvalidSomethingWasNotDoneGood();
        }
    }
}
