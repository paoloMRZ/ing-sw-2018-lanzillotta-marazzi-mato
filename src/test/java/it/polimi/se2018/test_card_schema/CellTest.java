package it.polimi.se2018.test_card_schema;

import it.polimi.se2018.server.exceptions.InvalidCellException;
import it.polimi.se2018.server.exceptions.InvalidValueException;
import it.polimi.se2018.server.exceptions.invalid_cell_exceptios.InvalidColorException;
import it.polimi.se2018.server.exceptions.invalid_cell_exceptios.InvalidShadeException;
import it.polimi.se2018.server.exceptions.invalid_cell_exceptios.NotEmptyCellException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidShadeValueException;
import it.polimi.se2018.server.model.Color;
import it.polimi.se2018.server.model.card.card_schema.Cell;
import it.polimi.se2018.server.model.dice_sachet.Dice;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


public class CellTest {

    private Cell cellaLibera;
    private Cell cellaOccupata;
    private ArrayList<Dice> dadi;

    //Il metodo testa il costruttore che non accetta dice come parametro.
    @Before
    public void createCellWithoutDice() {

        //Testo passando parametri accettabili.
        try {
            cellaLibera = new Cell(Color.RED, 5);
            assertTrue(cellaLibera.getColor() == Color.RED && cellaLibera.getNumber() == 5);
        } catch (InvalidValueException e) {
            fail();
        }

        //Testo passando parametri non accettabili.
        try {
            new Cell(Color.WHITE, -1);
            fail();
        } catch (InvalidShadeValueException e) {
            assertTrue(true);
        }

        try {
            new Cell(Color.WHITE, 7);
            fail();
        } catch (InvalidShadeValueException e) {
            assertTrue(true);

        }
    }

    @Before
    public void createCellWithDice() {

        Dice dado = new Dice(Color.RED, 3);
        //Testo passando parametri accettabili.
        try {

            cellaOccupata = new Cell(Color.WHITE, 0, dado);
            assertTrue(cellaOccupata.getColor() == Color.WHITE &&
                    cellaOccupata.getNumber() == 0 &&
                    cellaOccupata.showDice() != null &&
                    cellaOccupata.showDice().getColor() == dado.getColor() &&
                    cellaOccupata.showDice().getNumber() == dado.getNumber());


        } catch (InvalidValueException e) {
            fail();
        }

        //Testo passando parametri non accettabili.
        try {
            new Cell(Color.WHITE, -1, dado);
            fail();
        } catch (InvalidShadeValueException e) {
            assertTrue(true);
        }

        try {
            new Cell(Color.WHITE, 7, dado);
            fail();
        } catch (InvalidShadeValueException e) {
            assertTrue(true);
        }

        try {
            new Cell(Color.WHITE, 0, null);
            fail();
        } catch (NullPointerException e) {
            assertTrue(true);
        } catch (InvalidShadeValueException e) {
            fail();
        }


    }

    @Before
    public void buildDice(){
        dadi = new ArrayList<>();
        dadi.add(new Dice(Color.RED, 5)); //Dado che rispetta le restrizioni della cella vuota.
        dadi.add(new Dice(Color.BLUE, 5)); //Dado che viola le restrizioni di colore della cella vuota.
        dadi.add(new Dice(Color.RED, 3)); //Dado che viola le restrizioni di sfumatura della cella vuota.
    }

    @Test
    public void putDiceTest() {

        //Put che non solleva eccezioni.
        try {

            cellaLibera.putDice(dadi.get(0));
            assertTrue(cellaLibera.showDice().getColor().equals(dadi.get(0).getColor()) &&
                    cellaLibera.showDice().getNumber() == dadi.get(0).getNumber());

        } catch (InvalidCellException e) {
            fail();
        }

        //Put che solleva l'eccezione della cella occupata.
        try {
            cellaLibera.putDice(dadi.get(1));
            fail();
        } catch (NotEmptyCellException e) {
            assertTrue(cellaLibera.showDice() != null &&
                    cellaLibera.showDice().getColor() == dadi.get(0).getColor() &&
                    cellaLibera.showDice().getNumber() == dadi.get(0).getNumber());
        } catch (InvalidColorException | InvalidShadeException e) {
            fail();
        }

        //Svuoto la cella.
        Dice dadoTolto = cellaLibera.pickDice();
        assertTrue( cellaLibera.showDice() == null &&
                dadoTolto.getNumber() == dadi.get(0).getNumber() &&
                dadoTolto.getColor() == dadi.get(0).getColor());

        //Put che solleveno eccezioni riguardanti le restrizioni di cella.
        ArrayList<Dice> d = new ArrayList<>();
        d.add(dadi.get(1));
        d.add(dadi.get(2));

        for (Dice dado : d) {
            try {
                cellaLibera.putDice(dado);
                fail();
            } catch (InvalidCellException e) {
                assertTrue(cellaLibera.showDice() == null);
            }
        }
    }

    @Test
    public void putDiceIgnoringColorTest(){

        //Put che non solleva eccezioni.
        try {
            cellaLibera.putDiceIgnoringColor(dadi.get(1));
            assertTrue(cellaLibera.showDice() != null &&
                                cellaLibera.showDice().getColor() == dadi.get(1).getColor() &&
                                cellaLibera.showDice().getNumber() == dadi.get(1).getNumber());

        } catch (NotEmptyCellException | InvalidShadeException  e) {
            fail();
        }

        //Put che solleva l'eccezione della cella occupata.
        try {
            cellaLibera.putDiceIgnoringColor(dadi.get(0));
            fail();
        } catch (NotEmptyCellException e) {
            assertTrue(cellaLibera.showDice() != null &&
                                cellaLibera.showDice().getColor() == dadi.get(1).getColor() &&
                                cellaLibera.showDice().getNumber() == dadi.get(1).getNumber());
        } catch (InvalidShadeException e) {
            fail();
        }

        //Svuoto la cella.
         Dice dadoTolto = cellaLibera.pickDice();
        assertTrue( cellaLibera.showDice() == null &&
                            dadoTolto.getNumber() == dadi.get(1).getNumber() &&
                            dadoTolto.getColor() == dadi.get(1).getColor());


        //Put che sollevana l'eccezione riguardante la restrizione di sfumatura.
        try {
            cellaLibera.putDiceIgnoringColor(dadi.get(2));
            fail();
        } catch (NotEmptyCellException e) {
            fail();
        } catch (InvalidShadeException e){
            assertTrue(cellaLibera.showDice() == null);
        }
    }

    @Test
    public void putDiceIgnoringShadeTest(){

        //Put che non solleva eccezioni.

        try {
            cellaLibera.putDiceIgnoringShade(dadi.get(2));
            assertTrue(cellaLibera.showDice() != null &&
                                cellaLibera.showDice().getNumber() == dadi.get(2).getNumber() &&
                                cellaLibera.showDice().getColor() == dadi.get(2).getColor());
        } catch (InvalidColorException | NotEmptyCellException e) {
            fail();
        }

        //Put che solleva l'eccezione della cella occupata.
        try {
            cellaLibera.putDiceIgnoringShade(dadi.get(0));
            fail();
        } catch (NotEmptyCellException e) {
            assertTrue(cellaLibera.showDice() != null &&
                    cellaLibera.showDice().getColor() == dadi.get(2).getColor() &&
                    cellaLibera.showDice().getNumber() == dadi.get(2).getNumber());
        } catch (InvalidColorException e) {
            fail();
        }

        //Svuoto la cella.
        Dice dadoTolto = cellaLibera.pickDice();
        assertTrue( cellaLibera.showDice() == null &&
                dadoTolto.getNumber() == dadi.get(2).getNumber() &&
                dadoTolto.getColor() == dadi.get(2).getColor());

        //Put che solleva l'eccezione riguardante la restrizione di colore della cella.
        try{
            cellaLibera.putDiceIgnoringShade(dadi.get(1));
            fail();
        } catch (NotEmptyCellException e) {
            fail();
        } catch (InvalidColorException e) {
            assertTrue(cellaLibera.showDice() == null);
        }

    }

    @Test
    public void getCellsDiceColorTest(){
            assertTrue(cellaOccupata.getCellsDiceColor() == Color.RED);
            assertTrue(cellaLibera.getCellsDiceColor() == null);
    }

    @Test
    public void getCellsDiceNumberTest(){
            assertTrue(cellaOccupata.getCellsDiceNumber() == 3);
            assertTrue(cellaLibera.getCellsDiceNumber() == 0);
    }
}
