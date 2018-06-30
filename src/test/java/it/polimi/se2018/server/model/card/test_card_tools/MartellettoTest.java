package it.polimi.se2018.server.model.card.test_card_tools;

import it.polimi.se2018.server.controller.Controller;
import it.polimi.se2018.server.exceptions.InvalidActivationException;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static junit.framework.TestCase.fail;


public class MartellettoTest {

    private Martelletto mar= new Martelletto();


    private ArrayList<String> playersName= new ArrayList<>(Arrays.asList("1", "3", "2"));
    private Controller controller=new Controller(playersName);
    private Reserve tester=null;


    private ArrayList<Integer> countingBefore;
    private ArrayList<Color> colorsBefore;

    private ArrayList<Integer> countingAfter;
    private ArrayList<Color> colorsAfter;





    @Before
    public void settings() throws InvalidValueException, InvalidHowManyTimes{

        countingBefore=new ArrayList<>();
        colorsBefore=new ArrayList<>();

        countingAfter=new ArrayList<>();
        colorsAfter=new ArrayList<>();
    }



    @Test
    public void happy(){
        try {
            /*
            controller.getcTurn().setRound();
            controller.getcTurn().setTurn();
            controller.getcTurn().setTurn();
            controller.getcTurn().setTurn();
            controller.getcTurn().setTurn();

*/
            Dice d1 = new Dice(Color.BLUE, 1);
            Dice d2 = new Dice(Color.GREEN, 4);
            Dice d3 = new Dice(Color.PURPLE, 4);
            Dice d4 = new Dice(Color.YELLOW, 4);
            Dice d5 = new Dice(Color.YELLOW, 1);

            this.tester = new Reserve(new ArrayList<>(Arrays.asList(d1, d2, d3, d4, d5)));
            controller.getcAction().resettingReserve(tester);
        }
        catch(Exception e){
            fail("setting dentro test"+e);
        }

        try {
            counter(true);

            mar.function(controller);

            counter(false);

            comparing();
        }
        catch(Exception e){
            fail("ha lanciato eccezione"+ e);
        }

    }
    @Test(expected = InvalidActivationException.class)
    public void invalid() throws InvalidHowManyTimes, InvalidValueException, InvalidActivationException, InvalidSomethingWasNotDoneGood {
           /* controller.getcTurn().setRound();
            controller.getcTurn().setTurn();
            controller.getcTurn().setTurn();
*/



            Dice d1 = new Dice(Color.BLUE, 1);
            Dice d2 = new Dice(Color.GREEN, 4);
            Dice d3 = new Dice(Color.PURPLE, 4);
            Dice d4 = new Dice(Color.YELLOW, 4);
            Dice d5 = new Dice(Color.YELLOW, 1);

            this.tester = new Reserve(new ArrayList<>(Arrays.asList(d1, d2, d3, d4, d5)));
            controller.getcAction().resettingReserve(tester);



            counter(true);

            mar.function(controller);

            counter(false);

            comparing();

            fail("non ha lanciato excp");

    }

    private void counter(boolean isBefore){
        ArrayList<Dice> container = controller.getcAction().getReserve().getDices();

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
