package it.polimi.se2018.server.model.card.test_card_tools;

import it.polimi.se2018.server.controller.Controller;
import it.polimi.se2018.server.events.tool_mex.ToolCard6;
import it.polimi.se2018.server.events.tool_mex.ToolCard6Bis;
import it.polimi.se2018.server.exceptions.InvalidCellException;
import it.polimi.se2018.server.exceptions.InvalidValueException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidSomethingWasNotDoneGood;
import it.polimi.se2018.server.fake_view.FakeView;
import it.polimi.se2018.server.model.Color;
import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.server.model.card.card_schema.Cell;
import it.polimi.se2018.server.model.card.card_schema.Side;
import it.polimi.se2018.server.model.card.card_utensils.PennelloPerPastaSalda;
import it.polimi.se2018.server.model.dice_sachet.Dice;
import it.polimi.se2018.server.model.reserve.Reserve;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

public class PennelloPerPastaSaldaTest {
    private PennelloPerPastaSalda pen = null;
    private ArrayList<Cell> sideContent = null;
    private Controller controller = null;
    private ToolCard6 message = null;
    private ToolCard6Bis mex= null;
    private Side chosenOne = null;
    private Reserve supportReserve = null;
    private ArrayList<Side> sides = new ArrayList<>();
    private Player player1;
    private Player player2;


    @Before
    public void settings() throws InvalidValueException {
        this.pen = new PennelloPerPastaSalda();
        ToolCard6 message = null;
        ToolCard6Bis mex= null;
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

        chosenOne = new Side("toTEST", 5, sideContent);
        sides.add(chosenOne);
        FakeView fake = new FakeView();
        controller = new Controller(new ArrayList<>(Arrays.asList("primo","secondo")));
        fake.register(controller);

        player1 = controller.getPlayerByName("primo");
        player2 = controller.getPlayerByName("secondo");
        player1.setSideSelection(sides);
        player1.setMySide(0);
        player1.setFavours();
        player2.setSideSelection(sides);
        player2.setMySide(0);
        player2.setFavours();

    }
    @Test
    public void wellDone() throws InvalidValueException, InvalidCellException, InvalidSomethingWasNotDoneGood {
        try {
            this.message = new ToolCard6("primo", 1, 0);
            this.mex=new ToolCard6Bis("primo",1,false,
                    new ArrayList(Arrays.asList(0,2)));

            Dice d1 = new Dice(Color.BLUE, 1);
            Dice d2 = new Dice(Color.GREEN, 2);
            Dice d3 = new Dice(Color.BLUE, 5);
            Dice d4 = new Dice(Color.BLUE, 1);
            Dice d5 = new Dice(Color.BLUE, 1);
            this.supportReserve = new Reserve(new ArrayList<Dice>(Arrays.asList(d1, d2, d3, d4, d5)));
            controller.getcAction().resettingReserve(supportReserve);

            pen.function(controller, message);

            //controller.getcAction().workOnSide("primo", controller.getcAction()
                    //.pickFromReserve(0), 0, 1);


            pen.function(controller,mex);

            Dice itsHim = controller.getcAction().takeALookToDie(message.getPlayer(), 0, 2);

            assertEquals(Color.BLUE, itsHim.getColor());

        }
        catch(Exception e){
            fail("Fail: ha lanciato eccezione!"+ e);
        }
    }
    @Test(expected = NullPointerException.class)
    public void letItDown() throws InvalidValueException, InvalidCellException, InvalidSomethingWasNotDoneGood {

            this.message = new ToolCard6("primo", 1, 0);
            this.mex=new ToolCard6Bis("primo",1,true,
                    new ArrayList(Arrays.asList(0,2)));

            Dice d1 = new Dice(Color.BLUE, 1);
            Dice d2 = new Dice(Color.GREEN, 2);
            Dice d3 = new Dice(Color.BLUE, 5);
            Dice d4 = new Dice(Color.BLUE, 1);
            Dice d5 = new Dice(Color.BLUE, 1);
            this.supportReserve = new Reserve(new ArrayList<Dice>(Arrays.asList(d1, d2, d3, d4, d5)));
            controller.getcAction().resettingReserve(supportReserve);

            pen.function(controller, message);

            pen.function(controller,mex);

            Dice itsHim = controller.getcAction().takeALookToDie(message.getPlayer(), 0, 2);

            assertEquals(Color.BLUE, itsHim.getColor());

    }

}
