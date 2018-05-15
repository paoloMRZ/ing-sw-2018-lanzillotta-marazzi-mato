package it.polimi.se2018.test_card_schema;

import it.polimi.se2018.server.exceptions.InvalidCellException;
import it.polimi.se2018.server.exceptions.InvalidValueException;
import it.polimi.se2018.server.exceptions.invalid_cell_exceptios.InvalidColorException;
import it.polimi.se2018.server.exceptions.invalid_cell_exceptios.InvalidShadeException;
import it.polimi.se2018.server.exceptions.invalid_cell_exceptios.NotEmptyCellException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidColorValueException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidShadeValueException;
import it.polimi.se2018.server.model.Color;
import it.polimi.se2018.server.model.card.card_schema.Cell;
import it.polimi.se2018.server.model.dice_sachet.Dice;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


public class CellTest {

    //Il metodo testa il costruttore che non accetta dice come parametro.
    @Test
    public void createCellWithoutDiceTest(){
        Cell cella;

        //Testo passando parametri accettabili.
        try{
            for(Color colore: Color.values()) {
                for (int i = 0; i <= 6; i++) {
                    cella = new Cell(colore, i);
                    assertTrue(cella.getColor() == colore &&
                                        cella.getNumber() == i &&
                                        cella.showDice() == null );
                }
            }
        }catch (InvalidValueException e){
            fail();
        }

        //Testo passando parametri non accettabili.
        try{
            new Cell(Color.WHITE, -1);
            fail();
        }catch (InvalidShadeValueException e){
            assertTrue(true);

        } catch (InvalidColorValueException e) {
            fail();
        }

        try{
            new Cell(Color.WHITE, 7);
            fail();
        }catch (InvalidShadeValueException e){
            assertTrue(true);

        } catch (InvalidColorValueException e) {
            fail();
        }

    }

    @Test
    public void createCellWithDiceTest(){
        Cell cella;
        Dice dado = new Dice(Color.RED, 3);
        //Testo passando parametri accettabili.
        try{
            for(Color colore: Color.values()) {
                for (int i = 0; i <= 6; i++) {
                    cella = new Cell(colore, i, dado);
                    assertTrue(cella.getColor() == colore &&
                                        cella.getNumber() == i &&
                                        cella.showDice() != null &&
                                        cella.showDice().getColor() == dado.getColor() &&
                                        cella.showDice().getNumber() == dado.getNumber());
                }
            }
        }catch (InvalidValueException e){
            fail();
        }

        //Testo passando parametri non accettabili.
        try{
            new Cell(Color.WHITE, -1, dado);
            fail();
        }catch (InvalidShadeValueException e){
            assertTrue(true);

        } catch (InvalidColorValueException e) {
            fail();
        }

        try{
            new Cell(Color.WHITE, 7, dado);
            fail();
        }catch (InvalidShadeValueException e){
            assertTrue(true);

        } catch (InvalidColorValueException e) {
            fail();
        }
        

        try{
            new Cell(Color.WHITE, 0, null);
            fail();
        }catch (NullPointerException e) {
            assertTrue(true);
        } catch (InvalidColorValueException | InvalidShadeValueException e) {
            fail();
        }


    }

    @Test
    public void putDiceTest() {
        Dice dado = new Dice(Color.GREEN, 3);
        List<Cell> celle;
        Cell cella;

        //Put che non sollevano eccezioni.
        try {
             cella = new Cell(Color.WHITE, 0);


             cella.putDice(dado);
             assertTrue(cella.showDice().getColor().equals(dado.getColor()) &&
                                cella.showDice().getNumber() == dado.getNumber());

        } catch (InvalidValueException | InvalidCellException e) {
            fail();
        }

        //Put che solleveno eccezioni riguardanti le restrizioni di cella.
        try{
            celle = Arrays.asList(new Cell(Color.WHITE, 4), new Cell(Color.RED, 0) );
            for(Cell c: celle) {
                try {
                    c.putDice(dado);
                    fail();
                } catch (InvalidCellException e) {
                    assertTrue(c.showDice() == null);
                }
            }
        } catch (InvalidValueException  e) {
            fail();
        }

        //Put con cella già occupata.
        try {
            cella = new Cell(Color.WHITE, 0, dado);
            Dice tmp = cella.showDice();

            try {
                cella.putDice(dado);
                fail();
            } catch (NotEmptyCellException e) {
                assertTrue(cella.showDice().getColor().equals(tmp.getColor()) &&
                                    cella.showDice().getNumber() == tmp.getNumber());
            } catch (InvalidShadeException | InvalidColorException e) {
                fail();
            }
        } catch (InvalidShadeValueException | InvalidColorValueException e) {
            fail();
        }
    }

    @Test
    public void putDiceIgnoringColorTest(){

        //Put che non sollevano eccezioni.
        try {
            List<Cell> celle = Arrays.asList(new Cell(Color.RED, 0), new Cell(Color.GREEN, 0), new Cell(Color.YELLOW, 1), new Cell(Color.BLUE, 1) );
            Dice dado = new Dice(Color.PURPLE, 1);

            for(Cell cella: celle){
                cella.putDiceIgnoringColor(dado);
                assertTrue( cella.showDice().getColor().equals(dado.getColor()) &&
                                     cella.showDice().getNumber() == dado.getNumber());
            }
        } catch (NotEmptyCellException | InvalidShadeValueException | InvalidShadeException  | InvalidColorValueException e) {
            fail();
        }

        //Put che sollevano eccezioni.
        try {
            List<Cell> celle = Arrays.asList(new Cell(Color.RED, 2), new Cell(Color.GREEN, 3), new Cell(Color.YELLOW, 4) );
            Dice dado = new Dice(Color.PURPLE, 1);

            for(Cell cella : celle){
                try {
                    cella.putDiceIgnoringColor(dado);
                    fail();
                } catch (NotEmptyCellException e) {
                    fail();
                } catch (InvalidShadeException e) {
                    assertTrue(cella.showDice() == null);
                }
            }
        } catch (InvalidShadeValueException | InvalidColorValueException e) {
            fail();
        }

        Dice dado = new Dice(Color.PURPLE, 1);
        try {
            Cell cella = new Cell(Color.BLUE, 0, dado);
            Dice tmp = cella.showDice();

            try {
                cella.putDiceIgnoringColor(dado);
            } catch (NotEmptyCellException e) {
                assertTrue((tmp.getColor().equals(cella.showDice().getColor())) && (tmp.getNumber() == cella.showDice().getNumber()));
            }

        } catch ( InvalidShadeException | InvalidShadeValueException | InvalidColorValueException  e) {
            fail();
        }
    }

    @Test
    public void putDiceIgnoringShadeTest(){
        try {
            List<Cell> celle = Arrays.asList(new Cell(Color.WHITE, 1), new Cell(Color.WHITE, 2), new Cell(Color.RED, 3));
            Dice dado = new Dice(Color.RED, 5);

            for(Cell cella : celle){
                try {
                    cella.putDiceIgnoringShade(dado);
                    assertTrue( cella.showDice().getColor().equals(dado.getColor()) &&
                                        cella.showDice().getNumber() == dado.getNumber());
                } catch (InvalidColorException | NotEmptyCellException e) {
                    fail();
                }
            }
        } catch (InvalidShadeValueException | InvalidColorValueException e) {
            fail();
        }


        try {
            List<Cell> celle = Arrays.asList(new Cell(Color.PURPLE, 1), new Cell(Color.GREEN, 2), new Cell(Color.YELLOW, 3));
            Dice dado = new Dice(Color.RED, 5);

            for(Cell cella : celle){
                try {
                    cella.putDiceIgnoringShade(dado);
                    fail();
                } catch (InvalidColorException | NotEmptyCellException e) {
                    assertTrue(cella.showDice() == null);
                }
            }
        } catch (InvalidShadeValueException | InvalidColorValueException e) {
            fail();
        }

        Dice dado = new Dice(Color.PURPLE, 1);
        try {
            Cell cella = new Cell(Color.PURPLE, 3, dado);
            Dice tmp = cella.showDice();

            try {
                cella.putDiceIgnoringShade(dado);
            } catch (NotEmptyCellException e) {
                assertTrue((tmp.getColor().equals(cella.showDice().getColor())) && (tmp.getNumber() == cella.showDice().getNumber()));
            }

        } catch ( InvalidColorException | InvalidShadeValueException | InvalidColorValueException  e) {
            fail();
        }

    }

    @Test
    public void pickDiceTest(){
        try {
            Cell cella = new Cell(Color.RED, 3);
            Dice dadoA = new Dice(Color.RED, 3);

            cella.putDice(dadoA);
            assertTrue(cella.showDice().getColor().equals(dadoA.getColor()) &&
                                cella.showDice().getNumber() == dadoA.getNumber());

            Dice dadoB = cella.pickDice();
            assertTrue(dadoB.getColor().equals(dadoA.getColor()) &&
                                dadoB.getNumber() == dadoA.getNumber());

            Dice dadoC = cella.pickDice();
            assertTrue(dadoC == null);

        } catch ( InvalidColorException | NotEmptyCellException | InvalidShadeException | InvalidColorValueException | InvalidShadeValueException e) {
            fail();
        }

    }

    @Test
    public void getCellsDiceColorTest(){

        try {
            Cell cella = new Cell(Color.RED, 3);
            Dice dado = new Dice(Color.RED, 3);

            assertTrue(cella.getCellsDiceColor() == null);

            cella.putDice(dado);
            assertTrue(cella.getCellsDiceColor().equals(dado.getColor()));

        } catch (InvalidShadeException | InvalidColorException | NotEmptyCellException | InvalidColorValueException | InvalidShadeValueException e) {
            fail();
        }

    }

    @Test
    public void getCellsDiceNumberTest(){
        try {
            Cell cella = new Cell(Color.RED, 3);
            Dice dado = new Dice(Color.RED, 3);

            assertTrue(cella.getCellsDiceNumber() == 0);

            cella.putDice(dado);
            assertTrue(cella.getCellsDiceNumber() == dado.getNumber());

        } catch (InvalidShadeException | InvalidColorException | NotEmptyCellException | InvalidColorValueException | InvalidShadeValueException e) {
            fail();
        }
    }
}
