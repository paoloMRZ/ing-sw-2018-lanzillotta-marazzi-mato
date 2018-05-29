package it.polimi.se2018.server.model.card.test_card_tools;

import it.polimi.se2018.server.controller.Controller;
import it.polimi.se2018.server.events.tool_mex.ToolCard11;
import it.polimi.se2018.server.events.tool_mex.ToolCard11Bis;
import it.polimi.se2018.server.exceptions.InvalidCellException;
import it.polimi.se2018.server.exceptions.InvalidValueException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidSomethingWasNotDoneGood;
import it.polimi.se2018.server.model.Color;
import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.server.model.card.card_schema.Cell;
import it.polimi.se2018.server.model.card.card_schema.Side;
import it.polimi.se2018.server.model.card.card_utensils.DiluentePerPastaSalda;
import it.polimi.se2018.server.model.dice_sachet.Dice;
import it.polimi.se2018.server.model.reserve.Reserve;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

public class DiluentePerPastaSaldaTest {

    private DiluentePerPastaSalda dil = null;
    private ArrayList<Cell> sideContent = null;
    private Controller controller = null;
    private ToolCard11 message = null;
    private ToolCard11Bis mex= null;
    private Side chosenOne = null;
    private Reserve supportReserve = null;
    private ArrayList<Side> sides = new ArrayList<>();


    @Before
    public void settings() throws InvalidValueException {
        this.dil = new DiluentePerPastaSalda();
        ToolCard11 message = null;
        ToolCard11Bis mex= null;
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
    @Test
    public void wellDone() throws InvalidValueException, InvalidCellException, InvalidSomethingWasNotDoneGood {
        try {
            this.message = new ToolCard11("primo", 1, 0);
            this.mex=new ToolCard11Bis("primo",1,
                    new ArrayList(Arrays.asList(5,0,0)));

            Dice d1 = new Dice(Color.BLUE, 1);
            Dice d2 = new Dice(Color.GREEN, 2);
            Dice d3 = new Dice(Color.BLUE, 5);
            Dice d4 = new Dice(Color.BLUE, 1);
            Dice d5 = new Dice(Color.BLUE, 1);
            this.supportReserve = new Reserve(new ArrayList<Dice>(Arrays.asList(d1, d2, d3, d4, d5)));
            controller.getcAction().resettingReserve(supportReserve);


            dil.function(controller, message);
            dil.function(controller,mex);

            Dice itsHim = controller.getcAction().takeALookToDie(message.getPlayer(), 0, 0);

            assertEquals(5, itsHim.getNumber());

        }
        catch(Exception e){
            fail("Fail: ha lanciato eccezione!"+ e);
        }
    }
}
