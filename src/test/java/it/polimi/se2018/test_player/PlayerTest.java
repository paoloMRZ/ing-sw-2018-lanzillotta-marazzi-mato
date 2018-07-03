package it.polimi.se2018.test_player;

import it.polimi.se2018.server.controller.Controller;
import it.polimi.se2018.server.exceptions.InvalidCellException;
import it.polimi.se2018.server.exceptions.InvalidValueException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidColorValueException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidCoordinatesException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidFavoursValueException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidShadeValueException;
import it.polimi.se2018.server.fake_view.FakeView;
import it.polimi.se2018.server.model.Color;
import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.server.model.card.card_objective.Objective;
import it.polimi.se2018.server.model.card.card_objective.obj_algos.algos.ColorDiagonals;
import it.polimi.se2018.server.model.card.card_schema.Cell;
import it.polimi.se2018.server.model.card.card_schema.Side;
import it.polimi.se2018.server.model.dice_sachet.Dice;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static it.polimi.se2018.server.model.Color.WHITE;
import static it.polimi.se2018.server.model.Color.YELLOW;
import static org.junit.Assert.*;

public class PlayerTest {

    //Carta testata: Kaleidoscopic Dream

    private static ArrayList<Cell> casualList = new ArrayList<>(20);
    private static Side casualSide;
    private static Player player;
    private static Objective obj;
    private static ArrayList<Side> sides = new ArrayList<>();

    @Before
    public void setup() throws InvalidValueException {
        casualList.add(new Cell(YELLOW, 0));
        casualList.add(new Cell(Color.BLUE, 0));
        casualList.add(new Cell(Color.WHITE, 0));
        casualList.add(new Cell(Color.WHITE, 0));
        casualList.add(new Cell(Color.WHITE, 1));
        casualList.add(new Cell(Color.GREEN, 0));
        casualList.add(new Cell(Color.WHITE, 0));
        casualList.add(new Cell(Color.WHITE, 5));
        casualList.add(new Cell(Color.WHITE, 0));
        casualList.add(new Cell(Color.WHITE, 4));
        casualList.add(new Cell(Color.WHITE, 3));
        casualList.add(new Cell(Color.WHITE, 0));
        casualList.add(new Cell(Color.RED, 0));
        casualList.add(new Cell(Color.WHITE, 0));
        casualList.add(new Cell(Color.GREEN, 0));
        casualList.add(new Cell(Color.WHITE, 0));
        casualList.add(new Cell(Color.WHITE, 0));
        casualList.add(new Cell(Color.WHITE, 0));
        casualList.add(new Cell(Color.BLUE, 0));
        casualList.add(new Cell(YELLOW, 0));

        casualSide = new Side("test", 4, casualList);
        sides.add(casualSide);
        FakeView fake = new FakeView();
        Controller controller = new Controller(new ArrayList<>(Arrays.asList("Tester","secondo")),120);
        fake.register(controller);
        ColorDiagonals testerCard = new ColorDiagonals();
        obj = new Objective("TestName", "TestDescription", 4, testerCard, false);
        player = controller.getPlayerByName("Tester");

    }


    @Test
    public void getColorCellTest() throws Exception {
        player.setSideSelection(sides);
        player.setMySide(0);

        assertTrue(player.getColorCell(0, 4) == WHITE);
    }

    @Test
    public void getNumberCellTest() throws Exception {
        player.setSideSelection(sides);
        player.setMySide(0);

        assertTrue(player.getNumberCell(1, 2) == 5);
    }

    @Test
    public void putDiceTest() throws InvalidCellException, InvalidCoordinatesException, InvalidShadeValueException {
        player.setSideSelection(sides);
        player.setMySide(0);

        player.putDice(new Dice(Color.GREEN, 1), 0, 4);

        assertTrue(player.showSelectedCell(0, 4).showDice().getNumber() == 1);
    }

    @Test
    public void showSelectedCellTest() throws InvalidCellException, InvalidCoordinatesException, InvalidShadeValueException {
        player.setSideSelection(sides);
        player.setMySide(0);

        player.putDice(new Dice(Color.RED, 4), 1, 4);

        assertTrue(player.showSelectedCell(0, 0).getColor() == Color.YELLOW);
        assertTrue(player.showSelectedCell(1, 4).showDice().getColor() == Color.RED);

    }



    //TODO: trovato bug nel metodo putDiceIgnoreColor -> non eleimina il riferiemnto al dado spostato nella vecchia cella
/*
    @Test
    public void putDiceIgnoreTest() throws InvalidCellException, InvalidValueException {
        player.setSideSelection(sides);
        player.setMySide(0);

        player.putDice(new Dice(Color.YELLOW, 5), 0, 0);
        player.putDice(new Dice(Color.GREEN, 2), 1, 0);
        player.putDiceIgnoreColor(1, 0, 0, 1);

        assertTrue(player.showSelectedCell(0, 1).getColor() == Color.BLUE && player.showSelectedCell(0, 1).showDice().getColor() == Color.GREEN);
        assertTrue(player.showSelectedCell(1,0).showDice().getColor()==Color.GREEN);


        player.putDice(new Dice(Color.GREEN, 4), 1, 0);
        player.putDice(new Dice(Color.YELLOW, 2), 1, 0);
        player.putDiceIgnoreValue(1, 0, 1, 2);

        assertTrue(player.showSelectedCell(1, 2).getNumber() == 5 && player.showSelectedCell(0, 1).showDice().getNumber() == 4);

    }

*/

@Test
    public void setMySideTest() throws InvalidShadeValueException, InvalidCoordinatesException {
        player.setSideSelection(sides);
        player.setMySide(0);

        assertEquals(YELLOW, player.showSelectedCell(0, 0).getColor());
    }
}

