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
import it.polimi.se2018.server.fake_view.FakeView;
import it.polimi.se2018.server.model.Color;
import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.server.model.card.card_schema.Cell;
import it.polimi.se2018.server.model.card.card_schema.Side;
import it.polimi.se2018.server.model.card.card_utensils.RigaInSughero;
import it.polimi.se2018.server.model.card.card_utensils.TamponeDiamantato;
import it.polimi.se2018.server.model.card.card_utensils.TenagliaARotelle;
import it.polimi.se2018.server.model.card.card_utensils.Utensils;
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
    private FakeView fake;


    @Before
    public void setup() throws InvalidValueException {

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

        fake=new FakeView();
        controller= new Controller(new ArrayList<String>(Arrays.asList("primo","secondo")),60);
        fake.register(controller);


    }

    @Test(expected= NullPointerException.class)//problemi di adiacenza
    public void testerBadValue() throws Exception {

        controller.START();
        fake.messageIncoming("/primo/###/start/side_reply/0");
        fake.messageIncoming("/secondo/###/start/side_reply/0");

        Player player1 = controller.getPlayerByName("primo");
        Player player2 = controller.getPlayerByName("secondo");
        player1.setSideSelection(sides);
        player1.setMySide(0);
        player1.setFavours();
        player2.setSideSelection(sides);
        player2.setMySide(0);
        player2.setFavours();

        controller.resetUtensils(new ArrayList<Utensils>(Arrays.asList(new TenagliaARotelle(),
                new RigaInSughero(),
                new TamponeDiamantato())));

        Dice d1 = new Dice(Color.BLUE, 5);
        Dice d2 = new Dice(Color.GREEN, 5);
        Dice d3 = new Dice(Color.PURPLE, 4);
        Dice d4 = new Dice(Color.YELLOW, 4);
        Dice d5 = new Dice(Color.YELLOW, 1);
        this.supportReserve = new Reserve(new ArrayList<Dice>(Arrays.asList(d1, d2, d3, d4, d5)));
        controller.getcAction().resettingReserve(supportReserve);

        fake.messageIncoming("/primo/###/put/?/1&0&1");

        fake.messageIncoming("/primo/###/utensil/activate/0");

        fake.messageIncoming("/primo/###/utensil/use/0&8&0&0&2");

        Dice itsHim = controller.getcAction().takeALookToDie("primo", 0, 2);
        assertEquals(5,itsHim.getNumber());
        assertEquals(Color.BLUE,itsHim.getColor());

    }

    @Test
    public void testerGood() throws Exception {

        controller.START();
        fake.messageIncoming("/primo/###/start/side_reply/0");
        fake.messageIncoming("/secondo/###/start/side_reply/0");

        Player player1 = controller.getPlayerByName("primo");
        Player player2 = controller.getPlayerByName("secondo");
        player1.setSideSelection(sides);
        player1.setMySide(0);
        player1.setFavours();
        player2.setSideSelection(sides);
        player2.setMySide(0);
        player2.setFavours();

        controller.resetUtensils(new ArrayList<Utensils>(Arrays.asList(new TenagliaARotelle(),
                new RigaInSughero(),
                new TamponeDiamantato())));

        Dice d1 = new Dice(Color.BLUE, 4);
        Dice d2 = new Dice(Color.GREEN, 5);
        Dice d3 = new Dice(Color.PURPLE, 4);
        Dice d4 = new Dice(Color.YELLOW, 4);
        Dice d5 = new Dice(Color.YELLOW, 1);
        this.supportReserve = new Reserve(new ArrayList<Dice>(Arrays.asList(d1, d2, d3, d4, d5)));
        controller.getcAction().resettingReserve(supportReserve);

        fake.messageIncoming("/primo/###/put/?/1&0&1");

        fake.messageIncoming("/primo/###/utensil/activate/0");

        fake.messageIncoming("/primo/###/utensil/use/0&8&0&0&2");

        Dice itsHim = controller.getcAction().takeALookToDie("primo", 0, 2);
        assertEquals(4,itsHim.getNumber());
        assertEquals(Color.BLUE,itsHim.getColor());

    }


}
