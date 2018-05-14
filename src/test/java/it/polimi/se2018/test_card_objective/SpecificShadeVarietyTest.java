package it.polimi.se2018.test_card_objective;

import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidColorValueException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidFavoursValueException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidShadeValueException;
import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.server.model.card.card_objective.Objective;
import it.polimi.se2018.server.model.card.card_objective.obj_algos.algos.SpecificShadeVariety;
import it.polimi.se2018.server.model.card.card_schema.Cell;
import it.polimi.se2018.server.model.card.card_schema.Side;
import it.polimi.se2018.server.model.dice_sachet.Dice;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;

//Carta testata: Kaleidoscopic Dream

public class SpecificShadeVarietyTest {
    private static ArrayList<Cell> casualList = new ArrayList<>(20);
    private static Side casualSide;
    private static Player player;
    private static Objective obj;

    @Before
    public void setup() throws InvalidShadeValueException, InvalidFavoursValueException, InvalidColorValueException {
        casualList.add(new Cell("yellow", 0));
        casualList.add(new Cell("blue", 0));
        casualList.add(new Cell("white", 0));
        casualList.add(new Cell("white", 0));
        casualList.add(new Cell("white", 1));
        casualList.add(new Cell("green", 0));
        casualList.add(new Cell("white", 0));
        casualList.add(new Cell("white", 5));
        casualList.add(new Cell("white", 0));
        casualList.add(new Cell("white", 4));
        casualList.add(new Cell("white", 3));
        casualList.add(new Cell("white", 0));
        casualList.add(new Cell("red", 0));
        casualList.add(new Cell("white", 0));
        casualList.add(new Cell("green", 0));
        casualList.add(new Cell("white", 0));
        casualList.add(new Cell("white", 0));
        casualList.add(new Cell("white", 0));
        casualList.add(new Cell("blue", 0));
        casualList.add(new Cell("yellow", 0));

        casualSide = new Side("test", 4, casualList);

    }


    @Test
    public void singleSetTestColumn(){
        try {

            SpecificShadeVariety columnShadeVarietyTest = new SpecificShadeVariety(5,4);
            obj = new Objective("TestName", "TestDescription", 4, columnShadeVarietyTest, false);
            player = new Player(casualSide, obj, 4, "Tester");

            casualSide.put(0,0,new Dice("yellow", 4));
            casualSide.put(1,0,new Dice("green", 5));
            casualSide.put(2,0,new Dice("red", 3));
            casualSide.put(3,0,new Dice("blue", 2));

            assertEquals(4,obj.useAlgorithm(player));

        } catch (Exception e) { fail();}

    }

    @Test
    public void completeSetTestColumn(){
        try {

            SpecificShadeVariety columnShadeVarietyTest = new SpecificShadeVariety(5,4);
            obj = new Objective("TestName", "TestDescription", 4, columnShadeVarietyTest, false);
            player = new Player(casualSide, obj, 4, "Tester");

            casualSide.put(0,0,new Dice("yellow", 4));
            casualSide.put(1,0,new Dice("green", 5));
            casualSide.put(2,0,new Dice("red", 3));
            casualSide.put(3,0,new Dice("blue", 2));

            casualSide.put(0,1,new Dice("blue", 2));
            casualSide.put(1,1,new Dice("purple", 3));
            casualSide.put(2,1,new Dice("yellow", 1));
            casualSide.put(3,1,new Dice("green", 6));

            casualSide.put(0,2,new Dice("red", 1));
            casualSide.put(1,2,new Dice("blue", 5));
            casualSide.put(2,2,new Dice("red", 4));
            casualSide.put(3,2,new Dice("purple", 2));

            casualSide.put(0,3,new Dice("yellow", 3));
            casualSide.put(1,3,new Dice("purple", 2));
            casualSide.put(2,3,new Dice("yellow", 6));
            casualSide.put(3,3,new Dice("blue", 1));

            casualSide.put(0,4,new Dice("green", 1));
            casualSide.put(1,4,new Dice("blue", 4));
            casualSide.put(2,4,new Dice("green", 2));
            casualSide.put(3,4,new Dice("yellow", 3));

            assertEquals(20,obj.useAlgorithm(player));

        } catch (Exception e) { fail();}

    }

    @Test
    public void noSetTestColumn(){
        try {

            SpecificShadeVariety columnShadeVarietyTest = new SpecificShadeVariety(5,4);
            obj = new Objective("TestName", "TestDescription", 4, columnShadeVarietyTest, false);
            player = new Player(casualSide, obj, 4, "Tester");

            casualSide.put(0,0,new Dice("yellow", 4));
            casualSide.put(1,0,new Dice("green", 5));
            casualSide.put(2,0,new Dice("red", 3));
            casualSide.put(3,0,new Dice("blue", 5));

            assertEquals(0,obj.useAlgorithm(player));

        } catch (Exception e) { fail();}

    }


    @Test
    public void singleSetTestRow(){
        try {

            SpecificShadeVariety rowShadeVarietyTest = new SpecificShadeVariety(4,5);
            obj = new Objective("TestName", "TestDescription", 4, rowShadeVarietyTest, false);
            player = new Player(casualSide, obj, 4, "Tester");

            casualSide.put(0,0,new Dice("yellow", 4));
            casualSide.put(0,1,new Dice("blue", 2));
            casualSide.put(0,2,new Dice("red", 6));
            casualSide.put(0,3,new Dice("green", 3));
            casualSide.put(0,4,new Dice("purple", 1));

            assertEquals(5,obj.useAlgorithm(player));

        } catch (Exception e) { fail();}

    }

    @Test
    public void completeSetTestRow(){
        try {

            SpecificShadeVariety rowShadeVarietyTest = new SpecificShadeVariety(4,5);
            obj = new Objective("TestName", "TestDescription", 4, rowShadeVarietyTest, false);
            player = new Player(casualSide, obj, 4, "Tester");

            casualSide.put(0,0,new Dice("yellow", 4));
            casualSide.put(0,1,new Dice("blue", 2));
            casualSide.put(0,2,new Dice("red", 6));
            casualSide.put(0,3,new Dice("green", 3));
            casualSide.put(0,4,new Dice("purple", 1));

            casualSide.put(1,0,new Dice("green", 2));
            casualSide.put(1,1,new Dice("purple", 3));
            casualSide.put(1,2,new Dice("blue", 5));
            casualSide.put(1,3,new Dice("red", 1));
            casualSide.put(1,4,new Dice("yellow", 4));

            casualSide.put(2,0,new Dice("blue", 3));
            casualSide.put(2,1,new Dice("yellow", 6));
            casualSide.put(2,2,new Dice("red", 2));
            casualSide.put(2,3,new Dice("purple", 5));
            casualSide.put(2,4,new Dice("green", 1));

            casualSide.put(3,0,new Dice("yellow", 2));
            casualSide.put(3,1,new Dice("green", 1));
            casualSide.put(3,2,new Dice("yellow", 5));
            casualSide.put(3,3,new Dice("blue", 4));
            casualSide.put(3,4,new Dice("yellow", 6));

            assertEquals(20,obj.useAlgorithm(player));

        } catch (Exception e) { fail();}

    }

    @Test
    public void noSetTestRow(){
        try {

            SpecificShadeVariety rowShadeVarietyTest = new SpecificShadeVariety(4,5);
            obj = new Objective("TestName", "TestDescription", 4, rowShadeVarietyTest, false);
            player = new Player(casualSide, obj, 4, "Tester");

            casualSide.put(0,0,new Dice("yellow", 4));
            casualSide.put(0,1,new Dice("blue", 5));
            casualSide.put(0,2,new Dice("red", 6));
            casualSide.put(0,3,new Dice("green", 5));
            casualSide.put(0,4,new Dice("purple", 1));

            assertEquals(0,obj.useAlgorithm(player));

        } catch (Exception e) { fail();}

    }





}