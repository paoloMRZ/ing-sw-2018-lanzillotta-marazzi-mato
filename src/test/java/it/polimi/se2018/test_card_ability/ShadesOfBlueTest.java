package it.polimi.se2018.test_card_ability;

import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidColorValueException;
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

public class ShadesOfBlueTest {

        private static ArrayList<Cell> casualList = new ArrayList<>(20);
        private static Side casualSide;
        private static Player player;
        private static Objective obj;

        //Prima di lanciare il metodo, setta la Pattern Card

        @Before
        public void setup() throws InvalidShadeValueException, InvalidFavoursValueException, InvalidColorValueException {
            casualList.add(new Cell("white", 0));
            casualList.add(new Cell("white", 0));
            casualList.add(new Cell("white", 0));
            casualList.add(new Cell("white", 0));
            casualList.add(new Cell("white", 0));
            casualList.add(new Cell("white", 0));
            casualList.add(new Cell("white", 0));
            casualList.add(new Cell("white", 0));
            casualList.add(new Cell("white", 0));
            casualList.add(new Cell("white", 0));
            casualList.add(new Cell("white", 0));
            casualList.add(new Cell("white", 0));
            casualList.add(new Cell("white", 0));
            casualList.add(new Cell("white", 0));
            casualList.add(new Cell("white", 0));
            casualList.add(new Cell("white", 0));
            casualList.add(new Cell("white", 0));
            casualList.add(new Cell("white", 0));
            casualList.add(new Cell("white", 0));
            casualList.add(new Cell("white", 0));

            casualSide = new Side("test", 4, casualList);
            ShadesOfCard shadesOfBlue = new ShadesOfCard("blue");
            obj = new Objective("TestName", "TestDescription", 3, shadesOfBlue, false);
            player = new Player(casualSide, obj, 4, "Tester");

        }


        @Test
        public void diagonalCounter(){
            try {
                casualSide.put(0,0,new Dice("blue", 1));
                casualSide.put(1,1,new Dice("blue", 3));
                casualSide.put(2,2,new Dice("blue", 2));
                casualSide.put(3,3,new Dice("blue", 4));
                assertEquals(10,obj.useAlgorithm(player));

            } catch (Exception e) { fail();}

        }


        @Test
        public void diagonalMaxCounter(){
            try {
                casualSide.put(0,0,new Dice("blue", 1));
                casualSide.put(1,1,new Dice("blue", 3));
                casualSide.put(2,2,new Dice("blue", 2));
                casualSide.put(3,3,new Dice("blue", 4));
                casualSide.put(2,4,new Dice("blue", 4));
                casualSide.put(1,3,new Dice("blue", 4));
                casualSide.put(0,2,new Dice("blue", 4));
                assertEquals(22,obj.useAlgorithm(player));

            } catch (Exception e) {fail();}
        }



        @Test
        public void chessBoardCounter(){
            try {
                casualSide.put(0,0,new Dice("blue", 1));
                casualSide.put(1,1,new Dice("blue", 3));
                casualSide.put(2,2,new Dice("blue", 2));
                casualSide.put(3,3,new Dice("blue", 4));
                casualSide.put(2,4,new Dice("blue", 4));
                casualSide.put(1,3,new Dice("blue", 4));
                casualSide.put(0,2,new Dice("blue", 4));
                casualSide.put(2,0,new Dice("blue", 4));
                casualSide.put(3,1,new Dice("blue", 4));
                casualSide.put(0,4,new Dice("blue", 4));

                assertEquals(34,obj.useAlgorithm(player));

            } catch (Exception e) {fail();}

        }
}