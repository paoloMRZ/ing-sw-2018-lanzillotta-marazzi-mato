package it.polimi.se2018.server.model.card.test_card_tools;

import it.polimi.se2018.server.controller.Controller;
import it.polimi.se2018.server.events.tool_mex.ToolCard12;
import it.polimi.se2018.server.events.tool_mex.ToolCard4;
import it.polimi.se2018.server.exceptions.InvalidCellException;
import it.polimi.se2018.server.exceptions.InvalidValueException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidColorValueException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidFavoursValueException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidShadeValueException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidSomethingWasNotDoneGood;
import it.polimi.se2018.server.fake_view.FakeView;
import it.polimi.se2018.server.model.Color;
import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.server.model.card.card_schema.Cell;
import it.polimi.se2018.server.model.card.card_schema.Side;
import it.polimi.se2018.server.model.card.card_utensils.TaglierinaManuale;
import it.polimi.se2018.server.model.dice_sachet.Dice;
import it.polimi.se2018.server.model.reserve.Reserve;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

public class TaglierinaManualeTest {
    private ArrayList<Player> players = null;
    private TaglierinaManuale tag = null;
    private ArrayList<Cell> sideContent = null;
    private Controller controller = null;
    private ToolCard12 message = null;
    private Side chosenOne = null;
    private Reserve supportReserve = null;
    private ArrayList<Side> sides = new ArrayList<>();

    @Before
    public void settings() throws InvalidValueException {
        this.tag = new TaglierinaManuale();

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
        FakeView fake = new FakeView();
        controller = new Controller(new ArrayList<>(Arrays.asList("primo","secondo")),10);
        fake.register(controller);

        Player player1 = controller.getPlayerByName("primo");
        Player player2 = controller.getPlayerByName("secondo");
        player1.setSideSelection(sides);
        player1.setMySide(0);
        player1.setFavours();
        player2.setSideSelection(sides);
        player2.setMySide(0);
        player2.setFavours();
    }
/*
    @Test
    public int nothing(){
        return 1;
    }*/


    @Test(expected = InvalidValueException.class)
    public void playerNameCorrupted() throws InvalidValueException, InvalidCellException, InvalidSomethingWasNotDoneGood {

        this.message = new ToolCard12("pippo", 1,
                new ArrayList(Arrays.asList(0,0,  1, 1, 0, 0,  0, 2, 1, 1)));

        Dice d1 = new Dice(Color.BLUE, 1);
        Dice d2 = new Dice(Color.GREEN, 2);
        Dice d3 = new Dice(Color.BLUE, 5);
        Dice d4 = new Dice(Color.BLUE, 1);
        Dice d5 = new Dice(Color.BLUE, 1);
        this.supportReserve = new Reserve(new ArrayList<>(Arrays.asList(d1, d2, d3, d4, d5)));
        controller.getcAction().resettingReserve(supportReserve);

        controller.getcAction().workOnSide("primo", controller.getcAction()
                .pickFromReserve(0), 0, 2);
        controller.getcAction().workOnSide("primo", controller.getcAction()
                .pickFromReserve(0), 0, 1);
        controller.getcAction().workOnSide("primo", controller.getcAction()
                .pickFromReserve(0), 1, 1);
        controller.getcAction().putOnGrid(0, new Dice(Color.BLUE, 4));

        tag.function(controller, message);


        Assert.fail("Fail: Non ha lanciato eccezione!");
    }
    @Test
    public void wellDone(){
        try {
            this.message = new ToolCard12("primo", 1,
                    new ArrayList(Arrays.asList(0, 0, 1, 1, 0, 0, 0, 2, 1, 1)));

            Dice d1 = new Dice(Color.BLUE, 1);
            Dice d2 = new Dice(Color.GREEN, 2);
            Dice d3 = new Dice(Color.BLUE, 5);
            Dice d4 = new Dice(Color.BLUE, 1);
            Dice d5 = new Dice(Color.BLUE, 1);
            this.supportReserve = new Reserve(new ArrayList<Dice>(Arrays.asList(d1, d2, d3, d4, d5)));
            controller.getcAction().resettingReserve(supportReserve);

            controller.getcAction().workOnSide("primo", controller.getcAction()
                    .pickFromReserve(0), 0, 2);
            controller.getcAction().workOnSide("primo", controller.getcAction()
                    .pickFromReserve(0), 0, 1);
            controller.getcAction().workOnSide("primo", controller.getcAction()
                    .pickFromReserve(0), 1, 1);
            controller.getcAction().putOnGrid(0, new Dice(Color.BLUE, 4));

            tag.function(controller, message);

            Dice itsHim = controller.getcAction().takeALookToDie(message.getPlayer(), 0, 0);
            Dice itsHim2 = controller.getcAction().takeALookToDie(message.getPlayer(), 1, 1);


            assertEquals(5, itsHim.getNumber());
            assertEquals(Color.BLUE, itsHim.getColor());
            assertEquals(1, itsHim2.getNumber());
            assertEquals(Color.BLUE, itsHim2.getColor());

        }
        catch(Exception e){
            Assert.fail("Fail: ha lanciato eccezione!"+ e);
        }
    }
    @Test(expected = InvalidValueException.class)
    public void noGridDie() throws InvalidValueException, InvalidCellException, InvalidSomethingWasNotDoneGood {

            this.message = new ToolCard12("primo", 1,
                    new ArrayList(Arrays.asList(11, 0, 1, 1, 0, 0, 0, 2, 1, 1)));

            Dice d1 = new Dice(Color.BLUE, 1);
            Dice d2 = new Dice(Color.GREEN, 2);
            Dice d3 = new Dice(Color.BLUE, 5);
            Dice d4 = new Dice(Color.BLUE, 1);
            Dice d5 = new Dice(Color.BLUE, 1);
            this.supportReserve = new Reserve(new ArrayList<Dice>(Arrays.asList(d1, d2, d3, d4, d5)));
            controller.getcAction().resettingReserve(supportReserve);

            controller.getcAction().workOnSide("primo", controller.getcAction()
                    .pickFromReserve(0), 0, 2);
            controller.getcAction().workOnSide("primo", controller.getcAction()
                    .pickFromReserve(0), 0, 1);
            controller.getcAction().workOnSide("primo", controller.getcAction()
                    .pickFromReserve(0), 1, 1);
            controller.getcAction().putOnGrid(0, new Dice(Color.BLUE, 4));

            tag.function(controller, message);

            Dice itsHim = controller.getcAction().takeALookToDie(message.getPlayer(), 0, 0);
            Dice itsHim2 = controller.getcAction().takeALookToDie(message.getPlayer(), 1, 1);


            assertEquals(5, itsHim.getNumber());
            assertEquals(Color.BLUE, itsHim.getColor());
            assertEquals(1, itsHim2.getNumber());
            assertEquals(Color.BLUE, itsHim2.getColor());

            fail("non ha lanciato ecc!");
    }
    @Test(expected = InvalidValueException.class)
    public void notGoodCells() throws InvalidValueException, InvalidCellException, InvalidSomethingWasNotDoneGood {

        this.message = new ToolCard12("primo", 1,
                new ArrayList(Arrays.asList(0, 0, 1, 1, 0, 0, 0, 21, 11 , 1)));

        Dice d1 = new Dice(Color.BLUE, 1);
        Dice d2 = new Dice(Color.GREEN, 2);
        Dice d3 = new Dice(Color.BLUE, 5);
        Dice d4 = new Dice(Color.BLUE, 1);
        Dice d5 = new Dice(Color.BLUE, 1);
        this.supportReserve = new Reserve(new ArrayList<Dice>(Arrays.asList(d1, d2, d3, d4, d5)));
        controller.getcAction().resettingReserve(supportReserve);

        controller.getcAction().workOnSide("primo", controller.getcAction()
                .pickFromReserve(0), 0, 2);
        controller.getcAction().workOnSide("primo", controller.getcAction()
                .pickFromReserve(0), 0, 1);
        controller.getcAction().workOnSide("primo", controller.getcAction()
                .pickFromReserve(0), 1, 1);
        controller.getcAction().putOnGrid(0, new Dice(Color.BLUE, 4));

        tag.function(controller, message);

        Dice itsHim = controller.getcAction().takeALookToDie(message.getPlayer(), 0, 0);
        Dice itsHim2 = controller.getcAction().takeALookToDie(message.getPlayer(), 1, 1);


        assertEquals(5, itsHim.getNumber());
        assertEquals(Color.BLUE, itsHim.getColor());
        assertEquals(1, itsHim2.getNumber());
        assertEquals(Color.BLUE, itsHim2.getColor());

        fail("non ha lanciato ecc!");
    }
    @Test(expected = InvalidValueException.class)
    public void oneIsNoGood() throws InvalidValueException, InvalidCellException, InvalidSomethingWasNotDoneGood {

        this.message = new ToolCard12("primo", 1,
                new ArrayList(Arrays.asList(0, 0, 1, 1, 0, 0, 0, 21, 11 , 1)));

        Dice d1 = new Dice(Color.BLUE, 1);
        Dice d2 = new Dice(Color.GREEN, 2);
        Dice d3 = new Dice(Color.PURPLE, 5);
        Dice d4 = new Dice(Color.BLUE, 1);
        Dice d5 = new Dice(Color.BLUE, 1);
        this.supportReserve = new Reserve(new ArrayList<Dice>(Arrays.asList(d1, d2, d3, d4, d5)));
        controller.getcAction().resettingReserve(supportReserve);

        controller.getcAction().workOnSide("primo", controller.getcAction()
                .pickFromReserve(0), 0, 2);
        controller.getcAction().workOnSide("primo", controller.getcAction()
                .pickFromReserve(0), 0, 1);
        controller.getcAction().workOnSide("primo", controller.getcAction()
                .pickFromReserve(0), 1, 1);
        controller.getcAction().putOnGrid(0, new Dice(Color.BLUE, 4));

        tag.function(controller, message);

        Dice itsHim = controller.getcAction().takeALookToDie(message.getPlayer(), 0, 0);
        Dice itsHim2 = controller.getcAction().takeALookToDie(message.getPlayer(), 1, 1);


        assertEquals(5, itsHim.getNumber());
        assertEquals(Color.BLUE, itsHim.getColor());
        assertEquals(1, itsHim2.getNumber());
        assertEquals(Color.BLUE, itsHim2.getColor());

        fail("non ha lanciato ecc!");
    }
    @Test(expected = InvalidValueException.class)
    public void gridIsNoGood() throws InvalidValueException, InvalidCellException, InvalidSomethingWasNotDoneGood {

        this.message = new ToolCard12("primo", 1,
                new ArrayList(Arrays.asList(0, 0, 1, 1, 0, 0, 0, 21, 11 , 1)));

        Dice d1 = new Dice(Color.BLUE, 1);
        Dice d2 = new Dice(Color.GREEN, 2);
        Dice d3 = new Dice(Color.PURPLE, 5);
        Dice d4 = new Dice(Color.BLUE, 1);
        Dice d5 = new Dice(Color.BLUE, 1);
        this.supportReserve = new Reserve(new ArrayList<Dice>(Arrays.asList(d1, d2, d3, d4, d5)));
        controller.getcAction().resettingReserve(supportReserve);

        controller.getcAction().workOnSide("primo", controller.getcAction()
                .pickFromReserve(0), 0, 2);
        controller.getcAction().workOnSide("primo", controller.getcAction()
                .pickFromReserve(0), 0, 1);
        controller.getcAction().workOnSide("primo", controller.getcAction()
                .pickFromReserve(0), 1, 1);
        controller.getcAction().putOnGrid(0, new Dice(Color.YELLOW, 4));

        tag.function(controller, message);

        Dice itsHim = controller.getcAction().takeALookToDie(message.getPlayer(), 0, 0);
        Dice itsHim2 = controller.getcAction().takeALookToDie(message.getPlayer(), 1, 1);


        assertEquals(5, itsHim.getNumber());
        assertEquals(Color.BLUE, itsHim.getColor());
        assertEquals(1, itsHim2.getNumber());
        assertEquals(Color.BLUE, itsHim2.getColor());

        fail("non ha lanciato ecc!");
    }
    @Test(expected = InvalidCellException.class)
    public void adiacenza() throws InvalidValueException, InvalidCellException, InvalidSomethingWasNotDoneGood {

        this.message = new ToolCard12("primo", 1,
                new ArrayList(Arrays.asList(0, 0,   1, 1, 1, 3,     0, 2, 0, 0)));

        Dice d1 = new Dice(Color.BLUE, 5);
        Dice d2 = new Dice(Color.GREEN, 2);
        Dice d3 = new Dice(Color.BLUE, 1);
        Dice d4 = new Dice(Color.PURPLE, 1);
        Dice d5 = new Dice(Color.BLUE, 1);
        this.supportReserve = new Reserve(new ArrayList<Dice>(Arrays.asList(d1, d2, d3, d4, d5)));
        controller.getcAction().resettingReserve(supportReserve);

        controller.getcAction().workOnSide("primo", controller.getcAction()
                .pickFromReserve(0), 0, 2);
        controller.getcAction().workOnSide("primo", controller.getcAction()
                .pickFromReserve(0), 0, 1);
        controller.getcAction().workOnSide("primo", controller.getcAction()
                .pickFromReserve(0), 1, 1);
        controller.getcAction().workOnSide("primo", controller.getcAction()
                .pickFromReserve(0), 0, 3);
        controller.getcAction().putOnGrid(0, new Dice(Color.BLUE, 4));

        tag.function(controller, message);
        //bla bla
        Dice itsHim = controller.getcAction().takeALookToDie(message.getPlayer(), 0, 0);
        Dice itsHim2 = controller.getcAction().takeALookToDie(message.getPlayer(), 1, 1);


        assertEquals(5, itsHim.getNumber());
        assertEquals(Color.BLUE, itsHim.getColor());
        assertEquals(1, itsHim2.getNumber());
        assertEquals(Color.BLUE, itsHim2.getColor());

        fail("non ha lanciato ecc!");
    }

}
