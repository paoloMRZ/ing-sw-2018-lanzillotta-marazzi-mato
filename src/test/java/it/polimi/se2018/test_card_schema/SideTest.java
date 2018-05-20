package it.polimi.se2018.test_card_schema;

import it.polimi.se2018.server.exceptions.SagradaException;
import it.polimi.se2018.server.exceptions.invalid_cell_exceptios.*;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidCoordinatesException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidFavoursValueException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidShadeValueException;
import it.polimi.se2018.server.model.Color;
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
    private ArrayList<Side> lati = new ArrayList<>();
    private ArrayList<Cell> celle = new ArrayList<>();

    @Before
    public void buildSide() {

        try {


            //Prima riga.
            for (int i = 0; i < 5; i++) {
                celle.add(new Cell(Color.WHITE, 0));
            }

            //Seconda riga.
            celle.add(new Cell(Color.YELLOW, 0));
            celle.add(new Cell(Color.BLUE, 4));
            celle.add(new Cell(Color.GREEN, 5));
            celle.add(new Cell(Color.WHITE, 0));
            celle.add(new Cell(Color.WHITE, 0));

            //Terza riga.
            celle.add(new Cell(Color.WHITE, 3));
            celle.add(new Cell(Color.RED, 0));
            celle.add(new Cell(Color.WHITE, 1));
            celle.add(new Cell(Color.GREEN, 0));
            celle.add(new Cell(Color.WHITE, 0));

            //Quarta riga.
            celle.add(new Cell(Color.WHITE, 0));
            celle.add(new Cell(Color.WHITE, 1));
            celle.add(new Cell(Color.WHITE, 2));
            celle.add(new Cell(Color.GREEN, 0));
            celle.add(new Cell(Color.GREEN, 0));


            //Passo un valore errato al parametro favours in modo che venga sollevata un'eccezzione.
            lato = new Side("test", 0, celle);
            fail();
        } catch (InvalidShadeValueException e) {
            fail();
        } catch (InvalidFavoursValueException e) {
            assertTrue(lato == null);
        }

        try {
            lato = new Side("test", 3, celle);

            lati.add(new Side("test", 3, celle));
            lati.add(new Side("test", 3, celle));
            lati.add(new Side("test", 3, celle));
            lati.add(new Side("test", 3, celle));

        } catch (InvalidShadeValueException | InvalidFavoursValueException e) {
            fail();
        }

    }

    //Testo le combinazioni di coordinate che danno false.
    //Provo a fare delle put passando coordinate non valide.

    @Test
    public void areValidCoordinatesTest() {

        Dice dado = new Dice(Color.GREEN,3);
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
    public void edgeCoordinatesTest() {

    Dice dado = new Dice(Color.YELLOW, 1);
    int[] row = {0,3,1,1};
    int[] col = {1,1,0,4};
    int i = 0;

        for (Side lato:lati){
            try {
                lato.put(row[i], col[i],dado);
            } catch (SagradaException e) {
                fail();
            }
            i++;
        }

    }

    @Test
    public void fisrtWrongPutTest(){
        Dice dado = new Dice(Color.YELLOW, 3);
        int i = 0;


        for (Side l : lati) {
            try {
                switch (i) {
                    case 0: {
                        l.put(2, 1, dado);
                        fail();
                    } break;

                    case 1: {
                        l.putIgnoringShade(2, 1, dado);
                        fail();
                    } break;

                    case 2: {
                        l.putIgnoringColor(2, 1, dado);
                        fail();
                    } break;

                    case 3: {
                        l.putWithoutDicesNear(2, 1, dado);
                        fail();
                    } break;
                }

            } catch (InvalidCoordinatesException e) {
                assertTrue(true);
            } catch (SagradaException e) {
                fail();
            }

            i++;
        }
    }

    @Test
    public void secondPutWithoutDicesNear(){
        Dice dado = new Dice(Color.YELLOW, 3);
        int i = 0;

        for(Side l:lati) {
            try {
                l.put(0,0,dado);
            } catch (SagradaException e) {
                fail();
            }
        }

        for (Side l : lati) {
            try {
                switch (i) {
                    case 0: {
                        l.put(3, 0, dado);
                        fail();
                    } break;

                    case 1: {
                        l.putIgnoringShade(3, 0, dado);
                        fail();
                    } break;

                    case 2: {
                        l.putIgnoringColor(3, 0, dado);
                        fail();
                    } break;

                    case 3: {
                        l.putWithoutDicesNear(3, 0, dado);
                        assertTrue(l.showCell(3,0).showDice().getColor() == dado.getColor() &&
                                            l.showCell(3,0).showDice().getNumber() == dado.getNumber());
                    } break;
                }

            } catch (NoDicesNearException e) {
                try {
                    assertTrue(l.showCell(3,0).showDice() == null);
                } catch (InvalidCoordinatesException | InvalidShadeValueException e1) {
                    fail();
                }
            } catch (SagradaException e) {
                fail();
            }

            i++;
        }
    }

    @Test
    public void invalidCoordinatesPut() {
        Dice dado = new Dice(Color.YELLOW, 3);
        int i = 0;


        for (Side l : lati) {
            try {
                switch (i) {
                    case 0: {
                        l.put(-1, 0, dado);
                        fail();
                    } break;

                    case 1: {
                        l.putIgnoringShade(-1, 0, dado);
                        fail();
                    } break;

                    case 2: {
                        l.putIgnoringColor(-1, 0, dado);
                        fail();
                    } break;

                    case 3: {
                        l.putWithoutDicesNear(-1, 0, dado);
                        fail();
                    } break;
                }

            } catch (InvalidCoordinatesException e) {
                assertTrue(true);
            } catch (SagradaException e) {
                fail();
            }

            i++;
        }

    }

    @Test
    public void putOrtogonalErrorTest(){
        Dice dado = new Dice(Color.YELLOW, 3);
        int i = 0;

        for(Side l:lati) {
            try {
                l.put(0,4,dado);
            } catch (SagradaException e) {
                fail();
            }
        }

        for (Side l : lati) {
            try {
                switch (i) {
                    case 0: {
                        l.put(1, 4, dado);
                        fail();
                    } break;

                    case 1: {
                        l.putIgnoringShade(1, 4, dado);
                        fail();
                    } break;

                    case 2: {
                        l.putIgnoringColor(1, 4, dado);
                        fail();
                    } break;

                    case 3: {
                        l.putWithoutDicesNear(1, 4, dado);
                        fail();
                    } break;
                }

            } catch (NearDiceInvalidException e) {
                try {
                    assertTrue(l.showCell(1,4).showDice() == null);
                } catch (InvalidCoordinatesException | InvalidShadeValueException e1) {
                    fail();
                }
            } catch (InvalidCoordinatesException e){
                try {
                    assertTrue(l.showCell(1,4).showDice() == null && i == 3);
                } catch (InvalidShadeValueException | InvalidCoordinatesException e1) {
                    fail();
                }
            } catch (SagradaException e) {
                fail();
            }

            i++;
        }
    }

    @Test
    public void secondCorrectPutTest(){
        Dice dado = new Dice(Color.YELLOW, 3);
        int i = 0;

        for(Side l:lati) {
            try {
                l.put(0,4,dado);
            } catch (SagradaException e) {
                fail();
            }
        }

        for (Side l : lati) {
            try {
                switch (i) {
                    case 0: {
                        l.put(1, 3, dado);
                        assertTrue(l.showCell(1,3).showDice().getColor() == dado.getColor() &&
                                            l.showCell(1,3).showDice().getNumber() == dado.getNumber());
                    } break;

                    case 1: {
                        l.putIgnoringShade(1, 3, dado);
                        assertTrue(l.showCell(1,3).showDice().getColor() == dado.getColor() &&
                                l.showCell(1,3).showDice().getNumber() == dado.getNumber());
                    } break;

                    case 2: {
                        l.putIgnoringColor(1, 3, dado);
                        assertTrue(l.showCell(1,3).showDice().getColor() == dado.getColor() &&
                                l.showCell(1,3).showDice().getNumber() == dado.getNumber());
                    } break;

                    case 3: {
                        l.putWithoutDicesNear(0, 0, dado);
                        assertTrue(l.showCell(0,0).showDice().getColor() == dado.getColor() &&
                                l.showCell(0,0).showDice().getNumber() == dado.getNumber());
                    } break;
                }
            } catch (SagradaException e) {
                fail();
            }

            i++;
        }
    }

    @Test
    public void pickTest() {
        Dice dadoA = new Dice(Color.GREEN, 3);
        Dice dadoB;

        try {
            lato.put(0,0,dadoA);
            dadoB = lato.pick(0,0);

            assertTrue(dadoB.getColor() == dadoA.getColor() &&
                    dadoB.getNumber() == dadoA.getNumber() &&
                    lato.showCell(0,0).showDice() == null);
        } catch (SagradaException e) {
            fail();
        }

        dadoB = null;
        try {
            dadoB = lato.pick(-1,0);

            fail();
        } catch ( InvalidCoordinatesException e) {
            assertTrue(dadoB == null);
        }
    }

    @Test
    public void getColorTest(){

        try {
            assertTrue(lato.getColor(0,0) == celle.get(0).getColor());
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
            assertTrue(lato.showCell(0,0).getColor() == (celle.get(0).getColor()) &&
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
