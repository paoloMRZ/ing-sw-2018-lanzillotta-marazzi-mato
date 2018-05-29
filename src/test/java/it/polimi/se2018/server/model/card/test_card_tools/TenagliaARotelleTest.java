package it.polimi.se2018.server.model.card.test_card_tools;

import it.polimi.se2018.server.controller.Controller;
import it.polimi.se2018.server.events.tool_mex.ToolCard8;
import it.polimi.se2018.server.exceptions.InvalidActivationException;
import it.polimi.se2018.server.exceptions.InvalidCellException;
import it.polimi.se2018.server.exceptions.InvalidHowManyTimes;
import it.polimi.se2018.server.exceptions.InvalidValueException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidColorValueException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidFavoursValueException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidShadeValueException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidSomethingWasNotDoneGood;
import it.polimi.se2018.server.model.Color;
import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.server.model.card.card_schema.Cell;
import it.polimi.se2018.server.model.card.card_schema.Side;
import it.polimi.se2018.server.model.card.card_utensils.TenagliaARotelle;
import it.polimi.se2018.server.model.dice_sachet.Dice;
import it.polimi.se2018.server.model.reserve.Reserve;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.omg.CORBA.DynAnyPackage.InvalidValue;

import java.util.ArrayList;
import java.util.Arrays;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

public class TenagliaARotelleTest {


    private ArrayList<Player> players = null;
    private TenagliaARotelle tenaglia = null;
    private ArrayList<Cell> sideContent = null;
    private Controller controller = null;
    private Side chosenOne = null;
    private Reserve supportReserve = null;
    private ToolCard8 message=null;
    private ArrayList<Side> sides = new ArrayList<>();

    @Before
    public void settings() throws InvalidValueException, InvalidHowManyTimes {
        this.tenaglia = new TenagliaARotelle();

        this.sideContent = new ArrayList<>(20);
        //Aurorae Magnificus

        sideContent.add(new Cell(Color.WHITE, 5));
        sideContent.add(new Cell(Color.GREEN, 0));
        sideContent.add(new Cell(Color.BLUE, 0));
        sideContent.add(new Cell(Color.PURPLE, 0));
        sideContent.add(new Cell(Color.WHITE, 2));

        sideContent.add(new Cell(Color.PURPLE, 0));
        sideContent.add(new Cell(Color.WHITE, 0));
        sideContent.add(new Cell(Color.WHITE, 0));
        sideContent.add(new Cell(Color.WHITE, 0));
        sideContent.add(new Cell(Color.YELLOW, 0));

        sideContent.add(new Cell(Color.YELLOW, 0));
        sideContent.add(new Cell(Color.WHITE, 0));
        sideContent.add(new Cell(Color.WHITE, 6));
        sideContent.add(new Cell(Color.WHITE, 0));
        sideContent.add(new Cell(Color.PURPLE, 0));

        sideContent.add(new Cell(Color.WHITE, 1));
        sideContent.add(new Cell(Color.WHITE, 0));
        sideContent.add(new Cell(Color.WHITE, 0));
        sideContent.add(new Cell(Color.GREEN, 0));
        sideContent.add(new Cell(Color.WHITE, 4));

        this.chosenOne = new Side("toTEST", 5, this.sideContent);
        sides.add(chosenOne);
        controller = new Controller(new ArrayList<>(Arrays.asList("primo","secondo")));


        Player player1 = controller.getPlayerByName("primo");
        Player player2 = controller.getPlayerByName("secondo");
        player1.setSideSelection(sides);
        player1.setMySide(0);
        player1.setFavours();
        player2.setSideSelection(sides);
        player2.setMySide(0);
        player2.setFavours();

    }
    @Test(expected = InvalidValueException.class)
    public void playerNameCorrupted() throws InvalidValueException, InvalidCellException, InvalidSomethingWasNotDoneGood, InvalidHowManyTimes, InvalidActivationException {

        this.message = new ToolCard8("pippo", 1, new ArrayList(Arrays.asList(0, 1, 1)));

        Dice d1 = new Dice(Color.BLUE, 1);
        Dice d2 = new Dice(Color.BLUE, 1);
        Dice d3 = new Dice(Color.BLUE, 1);
        Dice d4 = new Dice(Color.BLUE, 1);
        Dice d5 = new Dice(Color.BLUE, 1);
        this.supportReserve = new Reserve(new ArrayList<Dice>(Arrays.asList(d1, d2, d3, d4, d5)));
        controller.getcAction().resettingReserve(supportReserve);

        tenaglia.function(controller, message);


        Assert.fail("Fail: Non ha lanciato eccezione!");
    }

    @Test
    public void testerGood() throws InvalidActivationException, InvalidValueException, InvalidSomethingWasNotDoneGood, InvalidCellException, InvalidHowManyTimes {
        try {
            Dice d1 = new Dice(Color.BLUE, 1);
            Dice d2 = new Dice(Color.BLUE, 2);
            Dice d3 = new Dice(Color.BLUE, 2);
            Dice d4 = new Dice(Color.BLUE, 2);
            Dice d5 = new Dice(Color.BLUE, 2);
            this.supportReserve = new Reserve(new ArrayList<Dice>(Arrays.asList(d1, d2, d3, d4, d5)));
            controller.getcAction().resettingReserve(supportReserve);
            controller.getcTurn().setTurn();

            this.message = new ToolCard8("primo", 1, new ArrayList<>(Arrays.asList(0, 0, 2)));

            tenaglia.function(controller, message);

            Dice itsHim = controller.getcAction().takeALookToDie(message.getPlayer(), 0, 2);
            assertEquals(1,itsHim.getNumber());
            assertEquals(Color.BLUE,itsHim.getColor());

        }
        catch(  Exception e ){
            fail("error"+ e);
        }
    }

    @Test(expected= InvalidValueException.class)
    public void testerBadValue() throws InvalidActivationException, InvalidValueException, InvalidSomethingWasNotDoneGood, InvalidCellException, InvalidHowManyTimes {

            Dice d1 = new Dice(Color.BLUE, 1);
            Dice d2 = new Dice(Color.BLUE, 2);
            Dice d3 = new Dice(Color.BLUE, 2);
            Dice d4 = new Dice(Color.BLUE, 2);
            Dice d5 = new Dice(Color.BLUE, 2);
            this.supportReserve = new Reserve(new ArrayList<Dice>(Arrays.asList(d1, d2, d3, d4, d5)));
            controller.getcAction().resettingReserve(supportReserve);

            controller.getcTurn().setTurn();
            controller.getcTurn().setTurn();
            controller.getcTurn().setTurn();


        this.message = new ToolCard8("primo", 1, new ArrayList<>(Arrays.asList(0, 0, 2)));

            tenaglia.function(controller, message);

            Dice itsHim = controller.getcAction().takeALookToDie(message.getPlayer(), 0, 2);
            assertEquals(1,itsHim.getNumber());
            assertEquals(Color.BLUE,itsHim.getColor());

            fail("error");

    }
    @Test(expected= InvalidActivationException.class)
    public void testerBadActivation() throws InvalidActivationException, InvalidValueException, InvalidSomethingWasNotDoneGood, InvalidCellException, InvalidHowManyTimes {

        Dice d1 = new Dice(Color.BLUE, 1);
        Dice d2 = new Dice(Color.BLUE, 2);
        Dice d3 = new Dice(Color.BLUE, 2);
        Dice d4 = new Dice(Color.BLUE, 2);
        Dice d5 = new Dice(Color.BLUE, 2);
        this.supportReserve = new Reserve(new ArrayList<Dice>(Arrays.asList(d1, d2, d3, d4, d5)));
        controller.getcAction().resettingReserve(supportReserve);

        controller.getcTurn().setTurn();
        controller.getcTurn().setTurn();
        controller.getcTurn().setTurn();
        controller.getcTurn().setTurn();

        this.message = new ToolCard8("primo", 1, new ArrayList<>(Arrays.asList(0, 0, 2)));

        tenaglia.function(controller, message);

        Dice itsHim = controller.getcAction().takeALookToDie(message.getPlayer(), 0, 2);
        assertEquals(1,itsHim.getNumber());
        assertEquals(Color.BLUE,itsHim.getColor());

        fail("error");

    }


}
