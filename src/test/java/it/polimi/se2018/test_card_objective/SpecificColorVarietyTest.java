package it.polimi.se2018.test_card_objective;

import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidColorValueException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidFavoursValueException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidShadeValueException;
import it.polimi.se2018.server.model.Color;
import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.server.model.card.card_objective.Objective;
import it.polimi.se2018.server.model.card.card_objective.obj_algos.algos.SpecificColorVariety;
import it.polimi.se2018.server.model.card.card_schema.Cell;
import it.polimi.se2018.server.model.card.card_schema.Side;
import it.polimi.se2018.server.model.dice_sachet.Dice;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;

//Carta testata: Kaleidoscopic Dream

public class SpecificColorVarietyTest {

    private static ArrayList<Cell> casualList = new ArrayList<>(20);
    private static Side casualSide;
    private static Player player;
    private static Objective obj;

    @Before
    public void setup() throws InvalidShadeValueException, InvalidFavoursValueException{
        casualList.add(new Cell(Color.YELLOW , 0));
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
        casualList.add(new Cell(Color.YELLOW, 0));

        casualSide = new Side("test", 4, casualList);

    }


    @Test
    public void singleSetTestColumn(){
        try {

            SpecificColorVariety columnColorVarietyTest = new SpecificColorVariety(5,4);
            obj = new Objective("TestName", "TestDescription", 4, columnColorVarietyTest, false);
            player = new Player(obj, "Tester",null);

            casualSide.put(0,0,new Dice(Color.YELLOW, 4));
            casualSide.put(1,0,new Dice(Color.GREEN, 2));
            casualSide.put(2,0,new Dice(Color.RED, 3));
            casualSide.put(3,0,new Dice(Color.BLUE, 2));

            ArrayList<Side> sides = new ArrayList<>();
            sides.add(casualSide);
            player.setSideSelection(sides);
            player.setMySide(0);

            assertEquals(5,obj.useAlgorithm(player));

        } catch (Exception e) { fail();}

    }

    @Test
    public void completeSetTestColumn(){
        try {

            SpecificColorVariety columnColorVarietyTest = new SpecificColorVariety(5,4);
            obj = new Objective("TestName", "TestDescription", 4, columnColorVarietyTest, false);
            player = new Player(obj, "Tester", null);

            casualSide.put(0,0,new Dice(Color.YELLOW, 4));
            casualSide.put(1,0,new Dice(Color.GREEN, 5));
            casualSide.put(2,0,new Dice(Color.BLUE, 3));
            casualSide.put(3,0,new Dice(Color.PURPLE, 2));

            casualSide.put(0,1,new Dice(Color.BLUE, 2));
            casualSide.put(1,1,new Dice(Color.PURPLE, 3));
            casualSide.put(2,1,new Dice(Color.YELLOW, 6));
            casualSide.put(3,1,new Dice(Color.GREEN, 1));

            casualSide.put(0,2,new Dice(Color.GREEN, 6));
            casualSide.put(1,2,new Dice(Color.BLUE, 5));
            casualSide.put(2,2,new Dice(Color.RED, 2));
            casualSide.put(3,2,new Dice(Color.YELLOW, 4));

            casualSide.put(0,3,new Dice(Color.RED, 3));
            casualSide.put(1,3,new Dice(Color.YELLOW, 2));
            casualSide.put(2,3,new Dice(Color.PURPLE, 5));
            casualSide.put(3,3,new Dice(Color.BLUE, 1));

            casualSide.put(0,4,new Dice(Color.PURPLE, 1));
            casualSide.put(1,4,new Dice(Color.RED, 4));
            casualSide.put(2,4,new Dice(Color.GREEN, 1));
            casualSide.put(3,4,new Dice(Color.YELLOW, 6));

            ArrayList<Side> sides = new ArrayList<>();
            sides.add(casualSide);
            player.setSideSelection(sides);
            player.setMySide(0);

            assertEquals(25,obj.useAlgorithm(player));

        } catch (Exception e) { fail();}

    }

    @Test
    public void noSetTestColumn(){
        try {

            SpecificColorVariety columnColorVarietyTest = new SpecificColorVariety(5,4);
            obj = new Objective("TestName", "TestDescription", 4, columnColorVarietyTest, false);
            player = new Player(obj, "Tester", null);

            casualSide.put(0,0,new Dice(Color.YELLOW, 4));
            casualSide.put(1,0,new Dice(Color.GREEN, 5));
            casualSide.put(2,0,new Dice(Color.RED, 3));
            casualSide.put(3,0,new Dice(Color.YELLOW, 5));

            ArrayList<Side> sides = new ArrayList<>();
            sides.add(casualSide);
            player.setSideSelection(sides);
            player.setMySide(0);

            assertEquals(0,obj.useAlgorithm(player));

        } catch (Exception e) { fail();}

    }



    @Test
    public void singleSetTestRow(){
        try {

            SpecificColorVariety columnColorVarietyTest = new SpecificColorVariety(4,5);
            obj = new Objective("TestName", "TestDescription", 4, columnColorVarietyTest, false);
            player = new Player(obj, "Tester",null);

            casualSide.put(0,0,new Dice(Color.YELLOW, 4));
            casualSide.put(0,1,new Dice(Color.BLUE, 2));
            casualSide.put(0,2,new Dice(Color.RED, 6));
            casualSide.put(0,3,new Dice(Color.GREEN, 3));
            casualSide.put(0,4,new Dice(Color.PURPLE, 1));

            ArrayList<Side> sides = new ArrayList<>();
            sides.add(casualSide);
            player.setSideSelection(sides);
            player.setMySide(0);

            assertEquals(6,obj.useAlgorithm(player));

        } catch (Exception e) { fail();}

    }

    @Test
    public void completeSetTestRow(){
        try {

            SpecificColorVariety columnColorVarietyTest = new SpecificColorVariety(4,5);
            obj = new Objective("TestName", "TestDescription", 4, columnColorVarietyTest, false);
            player = new Player(obj, "Tester", null);

            casualSide.put(0,0,new Dice(Color.YELLOW, 4));
            casualSide.put(0,1,new Dice(Color.BLUE, 2));
            casualSide.put(0,2,new Dice(Color.GREEN, 6));
            casualSide.put(0,3,new Dice(Color.RED, 3));
            casualSide.put(0,4,new Dice(Color.PURPLE, 1));

            casualSide.put(1,0,new Dice(Color.GREEN, 5));
            casualSide.put(1,1,new Dice(Color.PURPLE, 3));
            casualSide.put(1,2,new Dice(Color.BLUE, 5));
            casualSide.put(1,3,new Dice(Color.YELLOW, 2));
            casualSide.put(1,4,new Dice(Color.RED, 4));

            casualSide.put(2,0,new Dice(Color.BLUE, 3));
            casualSide.put(2,1,new Dice(Color.YELLOW, 6));
            casualSide.put(2,2,new Dice(Color.RED, 2));
            casualSide.put(2,3,new Dice(Color.PURPLE, 5));
            casualSide.put(2,4,new Dice(Color.GREEN, 1));

            casualSide.put(3,0,new Dice(Color.RED, 2));
            casualSide.put(3,1,new Dice(Color.GREEN, 1));
            casualSide.put(3,2,new Dice(Color.PURPLE, 4));
            casualSide.put(3,3,new Dice(Color.BLUE, 6));
            casualSide.put(3,4,new Dice(Color.YELLOW, 3));

            ArrayList<Side> sides = new ArrayList<>();
            sides.add(casualSide);
            player.setSideSelection(sides);
            player.setMySide(0);

            assertEquals(24,obj.useAlgorithm(player));

        } catch (Exception e) { fail();}

    }

    @Test
    public void noSetTestRow(){
        try {

            SpecificColorVariety columnColorVarietyTest = new SpecificColorVariety(4,5);
            obj = new Objective("TestName", "TestDescription", 4, columnColorVarietyTest, false);
            player = new Player(obj, "Tester", null);

            casualSide.put(0,0,new Dice(Color.YELLOW, 4));
            casualSide.put(0,1,new Dice(Color.BLUE, 5));
            casualSide.put(0,2,new Dice(Color.RED, 6));
            casualSide.put(0,3,new Dice(Color.GREEN, 5));
            casualSide.put(0,4,new Dice(Color.YELLOW, 1));

            ArrayList<Side> sides = new ArrayList<>();
            sides.add(casualSide);
            player.setSideSelection(sides);
            player.setMySide(0);

            assertEquals(0,obj.useAlgorithm(player));

        } catch (Exception e) { fail();}

    }





}