package it.polimi.se2018.test_card_ability;

import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidFavoursValueException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidShadeValueException;
import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.server.model.card.card_objective.Objective;
import it.polimi.se2018.server.model.card.card_objective.obj_algos.algos.ShadesOfCard;
import it.polimi.se2018.server.model.card.card_schema.Cell;
import it.polimi.se2018.server.model.card.card_schema.Side;
import it.polimi.se2018.server.model.dice_sachet.Dice;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ShadesOfGreenTest {

    private static ArrayList<Cell> casualList = new ArrayList<>(20);
    private static Side casualSide;
    private static Player player;
    private static Objective obj;
    private Dice die1 = new Dice("green", 2);
    private Dice die2 = new Dice("green", 3);
    private Dice die3 = new Dice("green", 4);
    private Dice die4 = new Dice("green", 5);


    @Before
    public void setup() throws InvalidShadeValueException, InvalidFavoursValueException {
        casualList.add(new Cell("green", 0));
        casualList.add(new Cell("white", 0));
        casualList.add(new Cell("white", 0));
        casualList.add(new Cell("white", 0));
        casualList.add(new Cell("white", 0));
        casualList.add(new Cell("white", 0));
        casualList.add(new Cell("green", 0));
        casualList.add(new Cell("white", 0));
        casualList.add(new Cell("white", 0));
        casualList.add(new Cell("white", 0));
        casualList.add(new Cell("white", 0));
        casualList.add(new Cell("white", 0));
        casualList.add(new Cell("green", 0));
        casualList.add(new Cell("white", 0));
        casualList.add(new Cell("white", 0));
        casualList.add(new Cell("white", 0));
        casualList.add(new Cell("white", 0));
        casualList.add(new Cell("white", 0));
        casualList.add(new Cell("green", 0));
        casualList.add(new Cell("white", 0));

        casualSide = new Side("test", 4, casualList);
        ShadesOfCard shadesOfGreen = new ShadesOfCard("green");
        obj = new Objective("TestName", "TestDescription", 3, shadesOfGreen, false);
        player = new Player(casualSide, obj, 4, "Tester");

    }


    @Test
    public void diagonalCounter(){
        try {
            casualSide.put(0,0,die1);
            casualSide.put(1,1,die3);
            casualSide.put(2,2,die2);
            casualSide.put(3,3,die4);
            assertEquals(14,obj.useAlgorithm(player));

        } catch (Exception e) { fail();}

    }

    @Test
    public void diagonalMaxCounter(){
        try {
            casualSide.put(0,0,die1);
            casualSide.put(1,1,die3);
            casualSide.put(2,2,die2);
            casualSide.put(3,3,die4);
            casualSide.put(2,4,die4);
            casualSide.put(1,3,die4);
            casualSide.put(0,2,die4);
            assertEquals(29,obj.useAlgorithm(player));

        } catch (Exception e) {fail();}
    }

    @Test
    public void chessBoardCounter(){
        try {

            casualSide.put(0,0,die1);
            casualSide.put(1,1,die3);
            casualSide.put(2,2,die2);
            casualSide.put(3,3,die4);
            casualSide.put(2,4,die4);
            casualSide.put(1,3,die4);
            casualSide.put(0,2,die4);
            casualSide.put(2,0,die4);
            casualSide.put(3,1,die4);
            casualSide.put(0,4,die4);
            assertEquals(44,obj.useAlgorithm(player));

        } catch (Exception e) {fail();}

    }
}