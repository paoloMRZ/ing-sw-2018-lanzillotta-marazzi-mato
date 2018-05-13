package it.polimi.se2018.TestAbility;

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

public class ShadesOfBlueTest {

        private static ArrayList<Cell> casualList = new ArrayList<>(20);
        private Dice die1 = new Dice("blue", 1);
        private Dice die2 = new Dice("blue", 2);
        private Dice die3 = new Dice("blue", 3);
        private Dice die4 = new Dice("blue", 4);


        //Prima di lanciare il metodo, setta la Pattern Card

        @BeforeClass
        public static void setup() throws InvalidShadeValueException {
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

        }


        @Test
        public void diagonalCounter(){
            try {
                Side casualSide = new Side("test", 4, casualList);
                casualSide.put(0,0,die1);
                casualSide.put(1,1,die3);
                casualSide.put(2,2,die2);
                casualSide.put(3,3,die4);

                ShadesOfCard shadesOfBlue = new ShadesOfCard("blue");
                Objective obj = new Objective("TestName", "TestDescription", 3, shadesOfBlue, false);
                Player player = new Player(casualSide, obj, 4, "Tester");

                assertEquals(10,shadesOfBlue.use(player));

            } catch (Exception e) { fail();}

        }

        @Test
        public void diagonalMaxCounter(){
            try {
                Side casualSide = new Side("test", 4, casualList);
                casualSide.put(0,0,die1);
                casualSide.put(1,1,die3);
                casualSide.put(2,2,die2);
                casualSide.put(3,3,die4);
                casualSide.put(2,4,die4);
                casualSide.put(1,3,die4);
                casualSide.put(0,2,die4);

                ShadesOfCard shadesOfBlue = new ShadesOfCard("blue");
                Objective obj = new Objective("TestName", "TestDescription", 3, shadesOfBlue, false);
                Player player = new Player(casualSide, obj, 4, "Tester");

                assertEquals(22,shadesOfBlue.use(player));

            } catch (Exception e) {fail();}
        }

        @Test
        public void chessBoardCounter(){
            try {
                Side casualSide = new Side("test", 4, casualList);
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



                ShadesOfCard shadesOfBlue = new ShadesOfCard("blue");
                Objective obj = new Objective("TestName", "TestDescription", 3, shadesOfBlue, false);
                Player player = new Player(casualSide, obj, 4, "Tester");

                assertEquals(34,shadesOfBlue.use(player));

            } catch (Exception e) {fail();}

        }
}