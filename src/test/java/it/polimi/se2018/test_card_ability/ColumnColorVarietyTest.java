package it.polimi.se2018.test_card_ability;

import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidColorValueException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidFavoursValueException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidShadeValueException;
import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.server.model.card.card_objective.Objective;
import it.polimi.se2018.server.model.card.card_objective.obj_algos.algos.ColumnColorVariety;
import it.polimi.se2018.server.model.card.card_objective.obj_algos.algos.ShadesOfCard;
import it.polimi.se2018.server.model.card.card_schema.Cell;
import it.polimi.se2018.server.model.card.card_schema.Side;
import it.polimi.se2018.server.model.dice_sachet.Dice;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;

//Carta testata: Kaleidoscopic Dream

public class ColumnColorVarietyTest {
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
        ColumnColorVariety columnColorVarietyTest = new ColumnColorVariety();
        obj = new Objective("TestName", "TestDescription", 5, columnColorVarietyTest, false);
        player = new Player(casualSide, obj, 4, "Tester");

    }


    @Test
    public void singleSetTest(){
        try {
            casualSide.put(0,0,new Dice("yellow", 3));
            casualSide.put(1,0,new Dice("green", 2));
            casualSide.put(2,0,new Dice("red", 3));
            casualSide.put(3,0,new Dice("blue", 2));

            assertEquals(5,obj.useAlgorithm(player));

        } catch (Exception e) { fail();}

    }

    @Test
    public void singleSetModifiedTest(){
        try {
            casualSide.put(0,0,new Dice("yellow", 3));
            casualSide.put(1,0,new Dice("green", 2));
            casualSide.put(2,0,new Dice("red", 3));
            casualSide.put(3,0,new Dice("purple", 2));

            assertEquals(5,obj.useAlgorithm(player));

        } catch (Exception e) { fail();}

    }


}