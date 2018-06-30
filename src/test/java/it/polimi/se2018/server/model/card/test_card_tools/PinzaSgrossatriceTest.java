package it.polimi.se2018.server.model.card.test_card_tools;

import it.polimi.se2018.server.controller.Controller;
import it.polimi.se2018.server.events.tool_mex.MoreThanSimple;
import it.polimi.se2018.server.exceptions.InvalidCellException;
import it.polimi.se2018.server.exceptions.InvalidValueException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.*;
import it.polimi.se2018.server.fake_view.FakeView;
import it.polimi.se2018.server.model.Color;
import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.server.model.card.card_schema.Cell;
import it.polimi.se2018.server.model.card.card_schema.Side;
import it.polimi.se2018.server.model.card.card_utensils.PinzaSgrossatrice;
import it.polimi.se2018.server.model.dice_sachet.Dice;
import it.polimi.se2018.server.model.reserve.Reserve;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

//To make these test: set the message and the dices you want, put some dices if needed. Kev.

public class PinzaSgrossatriceTest {
    private PinzaSgrossatrice pinza=null;
    private Controller controller=null;
    private MoreThanSimple message=null;
    private Reserve supportReserve=null;

    @Before
    public void settings(){
        this.pinza=new PinzaSgrossatrice();
        FakeView fake = new FakeView();
        controller = new Controller(new ArrayList<>(Arrays.asList("primo","secondo")));
        fake.register(controller);

    }

    //ricreo una riserva con un consono numero di dadi in base a quanti giovatori ho inserito


    //I SEGUENTI TEST SONO VARIAZIONI NEL MESSAGGIO
    @Test(expected = InvalidValueException.class)
    public void decisionNotValidWithTrue() throws InvalidValueException, InvalidSomethingWasNotDoneGood {

            this.message = new MoreThanSimple("primo", 1, true,1);

            Dice d1 = new Dice(Color.BLUE, 6);
            Dice d2 = new Dice(Color.BLUE, 6);
            Dice d3 = new Dice(Color.BLUE, 6);
            Dice d4 = new Dice(Color.BLUE, 6);
            Dice d5 = new Dice(Color.BLUE, 6);
            this.supportReserve = new Reserve(new ArrayList<Dice>(Arrays.asList(d1, d2, d3, d4, d5)));
            controller.getcAction().resettingReserve(supportReserve);

            pinza.function(controller,message);


            fail("Fail: Non ha lanciato eccezione!");
    }
    @Test(expected = InvalidValueException.class)
    public void decisionNotValidWithFalse() throws InvalidValueException, InvalidSomethingWasNotDoneGood {

        this.message = new MoreThanSimple("primo", 1, false,1);

        Dice d1 = new Dice(Color.BLUE, 1);
        Dice d2 = new Dice(Color.BLUE, 1);
        Dice d3 = new Dice(Color.BLUE, 1);
        Dice d4 = new Dice(Color.BLUE, 1);
        Dice d5 = new Dice(Color.BLUE, 1);
        this.supportReserve = new Reserve(new ArrayList<Dice>(Arrays.asList(d1, d2, d3, d4, d5)));
        controller.getcAction().resettingReserve(supportReserve);
        pinza.function(controller,message);


        fail("Fail: Non ha lanciato eccezione!");
    }
    @Test
    public void decisionIsValidFalse(){
         try{
             this.message = new MoreThanSimple("primo", 1, false,2);
                Dice d1 = new Dice(Color.BLUE, 3);
                Dice d2 = new Dice(Color.PURPLE, 3);
                Dice d3 = new Dice(Color.GREEN, 3);
                Dice d4 = new Dice(Color.YELLOW, 3);
                Dice d5 = new Dice(Color.RED, 3);

                this.supportReserve = new Reserve(new ArrayList<Dice>(Arrays.asList(d1, d2, d3, d4, d5)));
                controller.getcAction().resettingReserve(supportReserve);
                pinza.function(controller,message);

                Dice tester=controller.getcAction().pickFromReserve(4);
                //metto direttamente 4 perchè so che lo dovrebbe appendere alla riserva
             assertEquals(2,tester.getNumber());
             assertEquals(Color.GREEN,tester.getColor());
        }
        catch(Exception e){
            fail("Fail: ha lanciato eccezione!");
        }
    }

    @Test
    public void decisionIsValidTrue(){
        try{
            this.message = new MoreThanSimple("primo", 1, true,1);
            Dice d1 = new Dice(Color.BLUE, 3);
            Dice d2 = new Dice(Color.PURPLE, 3);
            Dice d3 = new Dice(Color.GREEN, 3);
            Dice d4 = new Dice(Color.YELLOW, 3);
            Dice d5 = new Dice(Color.RED, 3);

            this.supportReserve = new Reserve(new ArrayList<Dice>(Arrays.asList(d1, d2, d3, d4, d5)));
            controller.getcAction().resettingReserve(supportReserve);
            pinza.function(controller,message);

            Dice tester=controller.getcAction().pickFromReserve(4);
            //metto direttamente 4 perchè so che lo dovrebbe appendere alla riserva
            assertEquals(4,tester.getNumber());
            assertEquals(Color.PURPLE,tester.getColor());
        }
        catch(Exception e){
            fail("Fail: ha lanciato eccezione!");
        }
    }
    /*
    @Test(expected = InvalidValueException.class)
    public void playerNameCorrupted() throws InvalidValueException, InvalidSomethingWasNotDoneGood {

        this.message = new MoreThanSimple("pippo", 1, true,1);

        Dice d1 = new Dice(Color.BLUE, 1);
        Dice d2 = new Dice(Color.BLUE, 1);
        Dice d3 = new Dice(Color.BLUE, 1);
        Dice d4 = new Dice(Color.BLUE, 1);
        Dice d5 = new Dice(Color.BLUE, 1);
        this.supportReserve = new Reserve(new ArrayList<Dice>(Arrays.asList(d1, d2, d3, d4, d5)));
        controller.getcAction().resettingReserve(supportReserve);
        pinza.function(controller,message);


        fail("Fail: Non ha lanciato eccezione!");
    }*/

    @Test(expected = InvalidValueException.class)
    public void dieNotExisting() throws InvalidValueException, InvalidSomethingWasNotDoneGood {

        this.message = new MoreThanSimple("primo", 1, true,999);

        Dice d1 = new Dice(Color.BLUE, 3);
        Dice d2 = new Dice(Color.PURPLE, 3);
        Dice d3 = new Dice(Color.GREEN, 3);
        Dice d4 = new Dice(Color.YELLOW, 3);
        Dice d5 = new Dice(Color.RED, 3);
        this.supportReserve = new Reserve(new ArrayList<Dice>(Arrays.asList(d1, d2, d3, d4, d5)));
        controller.getcAction().resettingReserve(supportReserve);
        pinza.function(controller,message);


        fail("Fail: Non ha lanciato eccezione!");
    }

}
