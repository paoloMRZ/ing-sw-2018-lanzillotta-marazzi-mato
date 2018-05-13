package it.polimi.se2018.test_card_schema;

import it.polimi.se2018.server.exceptions.InvalidCellException;
import it.polimi.se2018.server.exceptions.SagradaException;
import it.polimi.se2018.server.exceptions.invalid_cell_exceptios.*;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidColorValueException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidCoordinatesException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidFavoursValueException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidShadeValueException;
import it.polimi.se2018.server.model.card.card_schema.Cell;
import it.polimi.se2018.server.model.card.card_schema.Side;
import it.polimi.se2018.server.model.dice_sachet.Dice;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;


import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class SideTest {


    private Side lato = null;
    private ArrayList<Cell> celle = new ArrayList<>();
    private ArrayList<Dice> dadi = null;

    @Before
    public void buildSide() {

        try {


            //Prima riga.
            for (int i = 0; i < 5; i++) {
                celle.add(new Cell("white", 0));
            }

            //Seconda riga.
            celle.add(new Cell("yellow", 0));
            celle.add(new Cell("blue", 4));
            celle.add(new Cell("green", 5));
            celle.add(new Cell("white", 0));
            celle.add(new Cell("white", 0));

            //Terza riga.
            celle.add(new Cell("white", 3));
            celle.add(new Cell("red", 0));
            celle.add(new Cell("white", 1));
            celle.add(new Cell("green", 0));
            celle.add(new Cell("white", 0));

            //Quarta riga.
            celle.add(new Cell("white", 0));
            celle.add(new Cell("white", 1));
            celle.add(new Cell("white", 2));
            celle.add(new Cell("green", 0));
            celle.add(new Cell("white", 0));


            lato = new Side("test", 3, celle);
        } catch (InvalidColorValueException | InvalidShadeValueException | InvalidFavoursValueException e) {
            fail();
        }

    }


    @Before
    public void buildDice() {

        dadi = new ArrayList<>();
        dadi.add(new Dice("purple", 2));
        dadi.add(new Dice("blue", 4));

    }


    //Testo le combinazioni di coordinate che danno false.
    //Provo a fare delle put passando coordinate non valide.
    @Test
    public void areValidCoordinatesTest() {

        Dice dado = dadi.get(0);
        int[] row = {-1, 4, 0, 0};
        int[] col = {0, 0, -1, 5};

        for (int i = 0; i < 4; i++) {
            try {

                lato.put(row[i], col[i], dado);
                fail();

            } catch (InvalidCoordinatesException  e) {
                assertTrue(true);
            } catch (SagradaException e) {
                fail();
            }
        }

    }

    @Test
    public void pickTest() {
        Dice dado;

        try {
            lato.put(0,0,dadi.get(0));
            dado = lato.pick(0,0);

            assertTrue(dado.getColor().equals(dadi.get(0).getColor()) &&
                                dado.getNumber() == dadi.get(0).getNumber() &&
                                lato.showCell(0,0).showDice() == null);
        } catch (SagradaException e) {
            fail();
        }

        dado = null;
        try {
            dado = lato.pick(-1,0);

            fail();
        } catch ( InvalidCoordinatesException e) {
            assertTrue(dado == null);
        }



    }

    @Test
    public void putTest() {

        int i = 0;

        //Primo inserimento errato.
        try {
            lato.put(2, 2, new Dice("red", 1));
            fail();
        } catch (InvalidCoordinatesException  e) {
            try {
                assertTrue(lato.showCell(2,2).showDice() == null);
            } catch (SagradaException e1) {
                fail();
            }
        } catch (SagradaException e) {
            fail();
        }

        //Primo, secondo e terzo inserimento corretto.
        try {
            for (Dice dado : dadi) {
                lato.put(i, i, dado);
                assertTrue(lato.showCell(i, i).showDice().getColor().equals(dado.getColor()) &&
                        lato.showCell(i, i).showDice().getNumber() == dado.getNumber());

                i++;
            }

            lato.put(2, 1, new Dice("red", 3));

        } catch (SagradaException e) {
            fail();
        }


        //Inserimento errato a causa del dado vicino.
        try {
            lato.put(3, 1, new Dice("red", 1));
            fail();
        } catch (NearDiceInvalidException  e) {
            try {
                assertTrue(lato.showCell(3,1).showDice() == null);
            } catch (InvalidShadeValueException | InvalidColorValueException | InvalidCoordinatesException e1) {
                fail();
            }
        } catch (SagradaException e) {
            fail();
        }

        //Inserimeto (errato) senza dadi vicini.
        try {
            lato.put(0, 3, new Dice("red", 6));
            fail();
        } catch (NoDicesNearException e) {
            try {
                assertTrue(lato.showCell(3,1).showDice() == null);
            } catch (InvalidShadeValueException | InvalidColorValueException | InvalidCoordinatesException e1) {
                fail();
            }
        } catch (SagradaException e) {
            fail();
        }
    }

    @Test
    public void putIgnoringColorTest() {

        //Primo inserimento errato.
        try {
            lato.putIgnoringColor(2, 2, new Dice("red", 1));
            fail();
        } catch (InvalidCoordinatesException e) {
            try {
                assertTrue(lato.showCell(2, 2).showDice() == null);
            } catch (InvalidShadeValueException | InvalidColorValueException | InvalidCoordinatesException e1) {
                fail();
            }
        } catch (SagradaException e) {
            fail();
        }


        //Primo e secondo inserimento corretti.
        try {

            lato.putIgnoringColor(3, 3, dadi.get(0));

            assertTrue(lato.showCell(3, 3).showDice().getColor().equals(dadi.get(0).getColor()) &&
                                lato.showCell(3, 3).showDice().getNumber() == dadi.get(0).getNumber());

            lato.putIgnoringColor(2, 3, dadi.get(1));

            assertTrue(lato.showCell(2, 3).showDice().getColor().equals(dadi.get(1).getColor()) &&
                    lato.showCell(2, 3).showDice().getNumber() == dadi.get(1).getNumber());

        } catch (SagradaException e) {
            fail();
        }


        //Inserimento errato a causa del dado vicino.
        try {
            lato.putIgnoringColor(1, 3, new Dice("blue", 1));
            fail();
        }  catch (NearDiceInvalidException  e) {
            try {
                assertTrue(lato.showCell(1,3).showDice() == null);
            } catch (InvalidShadeValueException | InvalidColorValueException | InvalidCoordinatesException e1) {
                fail();
            }
        } catch (SagradaException e) {
            fail();
        }

        //Inserimento (errato) senza dadi vicini.
        try {
            lato.putIgnoringColor(0, 0, new Dice("red", 6));
            fail();
        }  catch (NoDicesNearException e) {
            try {
                assertTrue(lato.showCell(0,0).showDice() == null);
            } catch (InvalidShadeValueException | InvalidColorValueException | InvalidCoordinatesException e1) {
                fail();
            }
        } catch (SagradaException e) {
            fail();
        }

        //Inserimento con coordinate errate.
        try {
            lato.putIgnoringColor(-1, 3, new Dice("red", 6));
            fail();
        }  catch (InvalidCoordinatesException e) {

                assertTrue(true);
        } catch (SagradaException e) {
            fail();
        }
    }

    @Test
    public void  putIgnoringShadeTest(){

        //Primo inserimento errato.
        try {
            lato.putIgnoringShade(2, 2, new Dice("red", 1));
            fail();
        }  catch (InvalidCoordinatesException e) {
            try {
                assertTrue(lato.showCell(2, 2).showDice() == null);
            } catch (InvalidShadeValueException | InvalidColorValueException | InvalidCoordinatesException e1) {
                fail();
            }
        } catch (SagradaException e) {
            fail();
        }

        //Primo e secondo inserimento corretti.
        try {

            lato.putIgnoringShade(2, 0, dadi.get(0));

            assertTrue(lato.showCell(2, 0).showDice().getColor().equals(dadi.get(0).getColor()) &&
                    lato.showCell(2, 0).showDice().getNumber() == dadi.get(0).getNumber());

            lato.putIgnoringShade(3, 1, dadi.get(1));

            assertTrue(lato.showCell(3, 1).showDice().getColor().equals(dadi.get(1).getColor()) &&
                    lato.showCell(3, 1).showDice().getNumber() == dadi.get(1).getNumber());

        } catch (SagradaException e) {
            fail();
        }


        //Inserimento errato a causa del dado vicino.
        try {
            lato.putIgnoringShade(3, 2, new Dice("blue", 1));
            fail();
        }  catch (NearDiceInvalidException  e) {
            try {
                assertTrue(lato.showCell(3,2).showDice() == null);
            } catch (SagradaException e1) {
                fail();
            }
        } catch (SagradaException e) {
            fail();
        }

        //Inserimento (errato) senza dadi vicini.
        try {
            lato.putIgnoringShade(0, 3, new Dice("red", 6));
            fail();
        }  catch (NoDicesNearException e) {
            try {
                assertTrue(lato.showCell(0,3).showDice() == null);
            } catch (InvalidShadeValueException | InvalidColorValueException | InvalidCoordinatesException e1) {
                fail();
            }
        } catch (SagradaException e) {
            fail();
        }

        //Inserimento con coordinate errate.
        try {
            lato.putIgnoringShade(-1, 3, new Dice("red", 6));
            fail();
        }  catch (InvalidCoordinatesException e) {

            assertTrue(true);
        } catch (SagradaException e) {
            fail();
        }
    }

    @Test
    public void putWithoutNearDicesTest(){

        //Primo inserimento errato.
        try {
            lato.putWithoutDicesNear(2, 2, new Dice("red", 1));
            fail();
        }  catch (InvalidCoordinatesException e) {
            try {
                assertTrue(lato.showCell(2, 2).showDice() == null);
            } catch (SagradaException e1) {
                fail();
            }
        } catch (SagradaException  e) {
            fail();
        }

        //Primo e secondo inserimento corretti.
        try{
            lato.putWithoutDicesNear(0,0, dadi.get(0));

            assertTrue(lato.showCell(0,0).showDice().getNumber() == dadi.get(0).getNumber() &&
                                lato.showCell(0,0).showDice().getColor().equals(dadi.get(0).getColor()));

            lato.putWithoutDicesNear(1,3, dadi.get(1));

            assertTrue(lato.showCell(1,3).showDice().getNumber() == dadi.get(1).getNumber() &&
                    lato.showCell(1,3).showDice().getColor().equals(dadi.get(1).getColor()));

        } catch (SagradaException e) {
            fail();
        }

        //Inserimento errato a causa della presenza di un dado confinante.
        try{
            lato.putWithoutDicesNear(0,1, new Dice("green", 1));
            fail();
        } catch (InvalidCoordinatesException e){
            try {
                assertTrue(lato.showCell(0,1).showDice() == null);
            } catch (SagradaException e1) {
                fail();
            }
        } catch (SagradaException e) {
            fail();
        }

        //Inserimento errato a causa delle coordinate.
        try{
            lato.putWithoutDicesNear(-1,1, new Dice("green", 1));
            fail();
        } catch (InvalidCoordinatesException e){
                assertTrue(true);
        } catch (SagradaException e) {
            fail();
        }
    }

    @Test
    public void getColorTest(){

        try {
            assertTrue(lato.getColor(0,0).equals(celle.get(0).getColor()));
        } catch (InvalidCoordinatesException e) {
            fail();
        }

        try{
            lato.getColor(0,-1);
            fail();
        } catch (InvalidCoordinatesException e) {
            assertTrue(true);
        }
    }

    @Test
    public void getNumberTest(){

        try {
            assertTrue(lato.getNumber(0,0) == celle.get(0).getNumber());
        } catch (InvalidCoordinatesException e) {
            fail();
        }

        try{
            lato.getNumber(0,-1);
            fail();
        } catch (InvalidCoordinatesException e) {
            assertTrue(true);
        }
    }

    @Test
    public void showCellTest(){

        try {
            assertTrue(lato.showCell(0,0).getColor().equals(celle.get(0).getColor()) &&
                                lato.showCell(0,0).getNumber() == celle.get(0).getNumber());
        } catch (SagradaException e) {
            fail();
        }

        try{
            lato.showCell(-1,0);
            fail();
        } catch (InvalidCoordinatesException e) {
            assertTrue(true);
        } catch (SagradaException e) {
            fail();
        }
    }



}
