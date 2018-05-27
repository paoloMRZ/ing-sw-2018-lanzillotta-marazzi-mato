package it.polimi.se2018.test_table;

import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidCoordinatesException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidShadeValueException;
import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.server.model.Table;
import it.polimi.se2018.server.model.card.card_objective.Objective;
import it.polimi.se2018.server.model.card.card_objective.obj_algos.algos.ColorDiagonals;
import it.polimi.se2018.server.model.card.card_utensils.Utensils;
import it.polimi.se2018.server.model.dice_sachet.DiceSachet;
import it.polimi.se2018.server.model.grid.RoundGrid;
import it.polimi.se2018.server.model.grid.ScoreGrid;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class TableTest {

    private static Table table;
    private static ArrayList<Player> players;
    private static Objective obj;

    @Before
    public void setUp(){
        ColorDiagonals colorDiagonalTest = new ColorDiagonals();
        obj = new Objective("TestName", "TestDescription", 4, colorDiagonalTest, false);

        players = new ArrayList<>();

        ArrayList<Utensils> utensilsDeck = new ArrayList<>();
        ArrayList<Objective> objectiveDeck = new ArrayList<>();
        DiceSachet diceSachet = new DiceSachet();
        ScoreGrid scoreGrid = null;
        RoundGrid roundGrid = new RoundGrid();

        table = new Table(utensilsDeck, objectiveDeck,players, diceSachet, scoreGrid, roundGrid);

    }



    @Test
    public void singlePlayerExtraction() throws IOException, InvalidCoordinatesException, InvalidShadeValueException {

        players.add(new Player(obj, 4, "Tester"));

        table.setCardPlayer(players);
        players.get(0).setMySide(0);
        assertTrue(players.get(0).showSelectedCell(0,0)!=null);
    }


    @Test
    public void completePlayersExtraction() throws IOException, InvalidCoordinatesException, InvalidShadeValueException {

        players.add(new Player(obj, 1, "Tester1"));
        players.add(new Player(obj, 2, "Tester2"));
        players.add(new Player(obj, 3, "Tester3"));
        players.add(new Player(obj, 4, "Tester4"));

        table.setCardPlayer(players);
        players.get(0).setMySide(0);
        players.get(1).setMySide(1);
        players.get(2).setMySide(2);
        players.get(3).setMySide(3);

        assertTrue(players.get(0).showSelectedCell(0,0)!=null);
        assertTrue(players.get(1).showSelectedCell(0,0)!=null);
        assertTrue(players.get(2).showSelectedCell(0,0)!=null);
        assertTrue(players.get(3).showSelectedCell(0,0)!=null);

    }
}