package it.polimi.se2018.TestAbility;

import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidFavoursValueException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidShadeValueException;
import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.server.model.card.card_objective.Objective;
import it.polimi.se2018.server.model.card.card_objective.obj_algos.algos.ShadesOfCard;
import it.polimi.se2018.server.model.card.card_schema.Cell;
import it.polimi.se2018.server.model.card.card_schema.Side;
import it.polimi.se2018.server.model.dice_sachet.Dice;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ShadesOfCardTest {

    private static ArrayList<Cell> casualList = new ArrayList<>(20);
    private static Side casualSide;
    private static Player player;
    private static Objective obj;

    @BeforeClass
    public static void setup() throws InvalidShadeValueException, InvalidFavoursValueException {
        casualList.add(new Cell("red", 0));
        casualList.add(new Cell("white", 0));
        casualList.add(new Cell("green", 0));
        casualList.add(new Cell("white", 0));
        casualList.add(new Cell("yellow", 0));
        casualList.add(new Cell("white", 0));
        casualList.add(new Cell("purple", 0));
        casualList.add(new Cell("yellow", 0));
        casualList.add(new Cell("white", 0));
        casualList.add(new Cell("red", 0));
        casualList.add(new Cell("yellow", 0));
        casualList.add(new Cell("red", 0));
        casualList.add(new Cell("white", 0));
        casualList.add(new Cell("white", 0));
        casualList.add(new Cell("purple", 0));
        casualList.add(new Cell("green", 0));
        casualList.add(new Cell("white", 0));
        casualList.add(new Cell("green", 0));
        casualList.add(new Cell("purple", 0));
        casualList.add(new Cell("yellow", 0));

        casualSide = new Side("test", 4, casualList);

    }


    @Test
    public void purpleCounterTest(){
        try {

            ShadesOfCard shadesOfPurple = new ShadesOfCard("purple");
            obj = new Objective("TestName", "TestDescription", 3, shadesOfPurple, false);
            player = new Player(casualSide, obj, 4, "Tester");

            casualSide.put(0,0,new Dice("red", 3));
            casualSide.put(1,1,new Dice("purple", 2));
            casualSide.put(2,1,new Dice("red", 6));
            casualSide.put(3,2,new Dice("green", 5));
            casualSide.put(3,3,new Dice("purple", 1));
            casualSide.put(2,4,new Dice("purple", 1));
            assertEquals(4,obj.useAlgorithm(player));

        } catch (Exception e) { fail();}

    }

    @Test
    public void redCounterTest(){
        try {
            ShadesOfCard shadesOfRed = new ShadesOfCard("red");
            obj = new Objective("TestName", "TestDescription", 3, shadesOfRed, false);
            player = new Player(casualSide, obj, 4, "Tester");

            casualSide.put(0,0,new Dice("red", 3));
            casualSide.put(1,1,new Dice("purple", 3));
            casualSide.put(2,1,new Dice("red", 6));
            casualSide.put(3,2,new Dice("green", 1));
            casualSide.put(3,3,new Dice("purple", 4));
            casualSide.put(2,4,new Dice("purple", 1));
            casualSide.put(1,4,new Dice("red", 5));
            assertEquals(14,obj.useAlgorithm(player));

        } catch (Exception e) {fail();}
    }

    @Test
    public void yellowCounterTest(){
        try {

            ShadesOfCard shadesOfYellow = new ShadesOfCard("yellow");
            obj = new Objective("TestName", "TestDescription", 3, shadesOfYellow, false);
            player = new Player(casualSide, obj, 4, "Tester");

            casualSide.put(0,0,new Dice("red", 3));
            casualSide.put(1,1,new Dice("purple", 3));
            casualSide.put(1,2,new Dice("yellow", 5));
            casualSide.put(2,0,new Dice("yellow", 4));
            casualSide.put(2,1,new Dice("red", 1));
            casualSide.put(3,2,new Dice("green", 2));
            casualSide.put(3,3,new Dice("purple", 4));
            casualSide.put(3,4,new Dice("yellow", 1));
            casualSide.put(0,3,new Dice("blue", 4));
            casualSide.put(0,4,new Dice("yellow", 6));
            assertEquals(16,obj.useAlgorithm(player));

        } catch (Exception e) {fail();}

    }
}