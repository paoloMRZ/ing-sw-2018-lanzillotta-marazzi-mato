package it.polimi.se2018.server.model.card.test_card_tools;

import it.polimi.se2018.server.controller.Controller;
import it.polimi.se2018.server.events.tool_mex.ToolCard5;
import it.polimi.se2018.server.exceptions.InvalidValueException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidFavoursValueException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidShadeValueException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidSomethingWasNotDoneGood;
import it.polimi.se2018.server.fake_view.FakeView;
import it.polimi.se2018.server.model.Color;
import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.server.model.card.card_schema.Cell;
import it.polimi.se2018.server.model.card.card_schema.Side;
import it.polimi.se2018.server.model.card.card_utensils.TaglierinaCircolare;
import it.polimi.se2018.server.model.dice_sachet.Dice;
import it.polimi.se2018.server.model.reserve.Reserve;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

public class TaglierinaCircolareTest {
    private TaglierinaCircolare taglierina=null;
    private  ArrayList<Cell> sideContent = null;
    private Controller controller=null;
    private ToolCard5 message=null;
    private Side chosenOne= null;
    private Reserve supportReserve=null;
    private static ArrayList<Side> sides = new ArrayList<>();

    @Before
    public void settings() throws InvalidValueException {
        this.taglierina=new TaglierinaCircolare();

        this.sideContent= new ArrayList<>(20);
        //Aurorae Magnificus

        sideContent.add(new Cell(Color.WHITE , 5));
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

        this.chosenOne=new Side("toTEST",5,this.sideContent);
        sides.add(chosenOne);
        FakeView fake = new FakeView();
        controller = new Controller(new ArrayList<>(Arrays.asList("primo","secondo")));
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

    //ricreo una riserva con un consono numero di dadi in base a quanti giovatori ho inserito

    @Test
    public void perfectFinish() throws InvalidValueException, InvalidSomethingWasNotDoneGood {
    try {
        this.message = new ToolCard5("primo", 1, new ArrayList<>(Arrays.asList(1, 0, 0)));

        Dice d1 = new Dice(Color.BLUE, 1);
        Dice d2 = new Dice(Color.BLUE, 1);
        Dice d3 = new Dice(Color.BLUE, 1);
        Dice d4 = new Dice(Color.BLUE, 1);
        Dice d5 = new Dice(Color.BLUE, 1);
        this.supportReserve = new Reserve(new ArrayList<Dice>(Arrays.asList(d1, d2, d3, d4, d5)));
        controller.getcAction().resettingReserve(supportReserve);

        controller.getcAction().putOnGrid(0, new Dice(Color.YELLOW, 4));

        taglierina.function(controller, message);

        Dice itsHim = controller.getcAction().pickFromReserve(4);
        Dice itsHim2 = controller.getcAction().takeFromGrid(0, 0);
        try {
            assertEquals(4, itsHim.getNumber());
            assertEquals(Color.YELLOW, itsHim.getColor());
            assertEquals(1, itsHim2.getNumber());
            assertEquals(Color.BLUE, itsHim2.getColor());
        } catch (Exception e) {
            Assert.fail("errore su uguaglinza");
        }
    }
    catch(Exception e) {
        fail("Fail: Non ha lanciato eccezione!");
    }
    }

    @Test(expected = InvalidValueException.class)
    public void dieNotExisting() throws InvalidValueException, InvalidSomethingWasNotDoneGood{
        this.message = new ToolCard5("primo", 1, new ArrayList<>(Arrays.asList(555, 0, 0)));

        Dice d1 = new Dice(Color.BLUE, 1);
        Dice d2 = new Dice(Color.BLUE, 1);
        Dice d3 = new Dice(Color.BLUE, 1);
        Dice d4 = new Dice(Color.BLUE, 1);
        Dice d5 = new Dice(Color.BLUE, 1);
        this.supportReserve = new Reserve(new ArrayList<Dice>(Arrays.asList(d1, d2, d3, d4, d5)));
        controller.getcAction().resettingReserve(supportReserve);

        controller.getcAction().putOnGrid(0, new Dice(Color.YELLOW, 4));

        taglierina.function(controller, message);

        Dice itsHim = controller.getcAction().pickFromReserve(4);
        Dice itsHim2 = controller.getcAction().takeFromGrid(0, 0);
        try {
            assertEquals(4, itsHim.getNumber());
            assertEquals(Color.YELLOW, itsHim.getColor());
            assertEquals(1, itsHim2.getNumber());
            assertEquals(Color.BLUE, itsHim2.getColor());
        } catch (Exception e) {
            Assert.fail("errore su uguaglinza");
        }
        fail("non ha lanciato eccezione");
    }
    @Test(expected = InvalidValueException.class)
    public void dieNotExistingOnGrid() throws InvalidValueException, InvalidSomethingWasNotDoneGood {
        this.message = new ToolCard5("primo", 1, new ArrayList<>(Arrays.asList(1, 11, 0)));

        Dice d1 = new Dice(Color.BLUE, 1);
        Dice d2 = new Dice(Color.BLUE, 1);
        Dice d3 = new Dice(Color.BLUE, 1);
        Dice d4 = new Dice(Color.BLUE, 1);
        Dice d5 = new Dice(Color.BLUE, 1);
        this.supportReserve = new Reserve(new ArrayList<Dice>(Arrays.asList(d1, d2, d3, d4, d5)));
        controller.getcAction().resettingReserve(supportReserve);

        controller.getcAction().putOnGrid(0, new Dice(Color.YELLOW, 4));

        taglierina.function(controller, message);

        Dice itsHim = controller.getcAction().pickFromReserve(4);
        Dice itsHim2 = controller.getcAction().takeFromGrid(0, 0);
        try {
            assertEquals(4, itsHim.getNumber());
            assertEquals(Color.YELLOW, itsHim.getColor());
            assertEquals(1, itsHim2.getNumber());
            assertEquals(Color.BLUE, itsHim2.getColor());
        } catch (Exception e) {
            Assert.fail("errore su uguaglinza");
        }
        fail("Fail: Non ha lanciato eccezione!");
    }
    
}
