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
import it.polimi.se2018.server.model.reserve.Reserve;
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
    private static ArrayList<Side> sides = new ArrayList<>();

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
        Player player1 = new Player(null, chosenOne.getFavours(), "primo");
        Player player2 = new Player(null, chosenOne.getFavours(), "secondo");
        player1.setSideSelection(sides);
        player1.setMySide(0);
        player2.setSideSelection(sides);
        player2.setMySide(0);
        ArrayList<Player> players = new ArrayList<>(Arrays.asList(player1, player2));
        this.players = players;
        this.controller = new Controller(players);
        player1.reductor();
        player2.reductor();
        player2.reductor();
    }
    @Test
    public void testerGood() throws InvalidActivationException, InvalidValueException, InvalidSomethingWasNotDoneGood, InvalidCellException, InvalidHowManyTimes {
        try {
            this.message = new ToolCard8("primo", 1, new ArrayList<>(Arrays.asList(0, 0, 2)));
            tenaglia.function(controller, message);
        }
        catch(  Exception e ){
            fail("error"+ e);
        }
    }
}
*/