package it.polimi.se2018.test_card_ability;

import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidColorValueException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidFavoursValueException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidShadeValueException;
import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.server.model.card.card_objective.Objective;
import it.polimi.se2018.server.model.card.card_objective.obj_algos.algos.ShadeVariety;
import it.polimi.se2018.server.model.card.card_schema.Cell;
import it.polimi.se2018.server.model.card.card_schema.Side;
import it.polimi.se2018.server.model.dice_sachet.Dice;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ShadeVarietyTest {

    private static ArrayList<Cell> casualList = new ArrayList<>(20);
    private static Side casualSide;
    private static Player player;
    private static Objective obj;


    @Before
    public void setup() throws InvalidShadeValueException, InvalidFavoursValueException, InvalidColorValueException {
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
        ShadeVariety shadesVarietyTest = new ShadeVariety();
        obj = new Objective("TestName", "TestDescription", 3, shadesVarietyTest, false);
        player = new Player(casualSide, obj, 4, "Tester");

    }


    @Test
    public void singleSetTest(){
        try {
            casualSide.put(0,0, new Dice("red", 1));
            casualSide.put(1,1,new Dice("purple", 2));
            casualSide.put(1,2,new Dice("yellow", 3));
            casualSide.put(2,1,new Dice("red", 4));
            casualSide.put(3,0,new Dice("green", 5));
            casualSide.put(2,0,new Dice("yellow", 6));
            assertEquals(5,obj.useAlgorithm(player));

        } catch (Exception e) { fail();}

    }

    @Test
    public void singleSetModifiedTest(){
        try {
            casualSide.put(0,0, new Dice("red", 1));
            casualSide.put(1,1,new Dice("purple", 2));
            casualSide.put(1,2,new Dice("yellow", 3));
            casualSide.put(2,1,new Dice("red", 4));
            casualSide.put(3,0,new Dice("green", 5));
            casualSide.put(2,0,new Dice("yellow", 6));

            casualSide.put(3,2, new Dice("green", 2));
            assertEquals(5,obj.useAlgorithm(player));

        } catch (Exception e) { fail();}

    }

    @Test
    public void noSetTest(){
        try {
            casualSide.put(0,0, new Dice("red", 1));
            casualSide.put(1,1,new Dice("purple", 2));
            casualSide.put(1,2,new Dice("yellow", 3));
            casualSide.put(2,1,new Dice("red", 4));
            casualSide.put(3,0,new Dice("green", 5));
            assertEquals(0,obj.useAlgorithm(player));

        } catch (Exception e) { fail();}

    }

}