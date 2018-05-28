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
import it.polimi.se2018.server.model.card.card_utensils.Lathekin;
import it.polimi.se2018.server.model.card.card_utensils.TenagliaARotelle;
import it.polimi.se2018.server.model.dice_sachet.Dice;
import it.polimi.se2018.server.model.reserve.Reserve;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static junit.framework.TestCase.fail;
/*
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
    public void settings() throws InvalidColorValueException, InvalidShadeValueException, InvalidFavoursValueException, InvalidHowManyTimes {
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
        //setting players
        Player player1 = new Player(null, chosenOne.getFavours(), "primo");
        player1.setSideSelection(sides);
        player1.setMySide(0);
        //record players
        ArrayList<Player> players = new ArrayList<>(Arrays.asList(player1));
        this.players = players;
        this.controller = new Controller(players);
        player1.reductor();

    }
    @Test(expected = InvalidValueException.class)
    public void playerNameCorrupted() throws InvalidValueException, InvalidCellException, InvalidSomethingWasNotDoneGood, InvalidHowManyTimes, InvalidActivationException {

        this.message = new ToolCard8("pippo", 1, new ArrayList(Arrays.asList(0, 0, 1, 1)));

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
            Dice d2 = new Dice(Color.BLUE, 1);
            Dice d3 = new Dice(Color.BLUE, 1);
            Dice d4 = new Dice(Color.BLUE, 1);
            Dice d5 = new Dice(Color.BLUE, 1);
            this.supportReserve = new Reserve(new ArrayList<Dice>(Arrays.asList(d1, d2, d3, d4, d5)));
            controller.getcAction().resettingReserve(supportReserve);

            this.message = new ToolCard8("primo", 1, new ArrayList<>(Arrays.asList(0, 0, 2)));

            tenaglia.function(controller, message);
            Dice itsHim = controller.getcAction().takeALookToDie(message.getPlayer(), 0, 0);
            assertEquals(1,controller.getcAction().itsHim)
        }
        catch(  Exception e ){
            fail("error"+ e);
        }
    }
}*/
