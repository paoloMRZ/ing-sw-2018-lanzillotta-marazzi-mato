package it.polimi.se2018.server.model.card.test_card_tools;

import it.polimi.se2018.server.controller.Controller;
import it.polimi.se2018.server.events.tool_mex.ToolCard9;
import it.polimi.se2018.server.exceptions.InvalidCellException;
import it.polimi.se2018.server.exceptions.InvalidValueException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.*;
import it.polimi.se2018.server.model.Color;
import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.server.model.card.card_schema.Cell;
import it.polimi.se2018.server.model.card.card_schema.Side;
import it.polimi.se2018.server.model.card.card_utensils.RigaInSughero;
import it.polimi.se2018.server.model.dice_sachet.Dice;
import it.polimi.se2018.server.model.reserve.Reserve;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

public class RigaInSugheroTest {
    private RigaInSughero riga = null;
    private ArrayList<Cell> sideContent = null;
    private Controller controller = null;
    private ToolCard9 message = null;
    private Side chosenOne = null;
    private Reserve supportReserve = null;
    private ArrayList<Side> sides = new ArrayList<>();

    @Before
    public void settings() throws InvalidValueException {
        this.riga = new RigaInSughero();

        this.sideContent = new ArrayList<>(20);
        //Aurorae Magnificus

        sideContent.add(new Cell(Color.WHITE, 5));
        sideContent.add(new Cell(Color.GREEN, 0));
        sideContent.add(new Cell(Color.BLUE, 0));
        sideContent.add(new Cell(Color.PURPLE, 0));
        sideContent.add(new Cell(Color.WHITE, 2));

        sideContent.add(new Cell(Color.PURPLE, 0));
        sideContent.add(new Cell(Color.WHITE, 0));
        sideContent.add(new Cell(Color.WHITE, 0));
        sideContent.add(new Cell(Color.WHITE, 0));
        sideContent.add(new Cell(Color.YELLOW, 0));

        sideContent.add(new Cell(Color.YELLOW, 0));
        sideContent.add(new Cell(Color.WHITE, 0));
        sideContent.add(new Cell(Color.WHITE, 6));
        sideContent.add(new Cell(Color.WHITE, 0));
        sideContent.add(new Cell(Color.PURPLE, 0));

        sideContent.add(new Cell(Color.WHITE, 1));
        sideContent.add(new Cell(Color.WHITE, 0));
        sideContent.add(new Cell(Color.WHITE, 0));
        sideContent.add(new Cell(Color.GREEN, 0));
        sideContent.add(new Cell(Color.WHITE, 4));

        this.chosenOne = new Side("toTEST", 5, this.sideContent);
        sides.add(chosenOne);
        controller = new Controller(new ArrayList<>(Arrays.asList("primo","secondo")));


        Player player1 = controller.getPlayerByName("primo");
        Player player2 = controller.getPlayerByName("secondo");
        player1.setSideSelection(sides);
        player1.setMySide(0);
        player1.setFavours();
        player2.setSideSelection(sides);
        player2.setMySide(0);
        player2.setFavours();
    }

    @Test(expected = InvalidValueException.class)
    public void playerNameCorrupted() throws InvalidValueException, InvalidCellException, InvalidSomethingWasNotDoneGood {

        this.message = new ToolCard9("pippo", 1, new ArrayList(Arrays.asList(0, 0, 2)));

        Dice d1 = new Dice(Color.BLUE, 1);
        Dice d2 = new Dice(Color.BLUE, 1);
        Dice d3 = new Dice(Color.BLUE, 1);
        Dice d4 = new Dice(Color.BLUE, 1);
        Dice d5 = new Dice(Color.BLUE, 1);
        this.supportReserve = new Reserve(new ArrayList<Dice>(Arrays.asList(d1, d2, d3, d4, d5)));
        controller.getcAction().resettingReserve(supportReserve);


        riga.function(controller, message);


        fail("Fail: Non ha lanciato eccezione!");
    }
    @Test(expected = InvalidValueException.class)
    public void dieNotExisting() throws InvalidValueException, InvalidCellException, InvalidSomethingWasNotDoneGood {
        //i dati dell'input nel messaggio seguono la convenzione stabilita in MultiParam
        this.message = new ToolCard9("primo", 1, new ArrayList<>(Arrays.asList(11, 0, 2)));

        Dice d1 = new Dice(Color.BLUE, 1);
        Dice d2 = new Dice(Color.BLUE, 1);
        Dice d3 = new Dice(Color.BLUE, 1);
        Dice d4 = new Dice(Color.BLUE, 1);
        Dice d5 = new Dice(Color.BLUE, 1);
        this.supportReserve = new Reserve(new ArrayList<Dice>(Arrays.asList(d1, d2, d3, d4, d5)));
        controller.getcAction().resettingReserve(supportReserve);

        riga.function(controller, message);

        fail("Fail: Non ha lanciato eccezione!");
    }
    @Test(expected = InvalidCoordinatesException.class)
    public void cellNotExistingPicking() throws InvalidValueException, InvalidCellException, InvalidSomethingWasNotDoneGood {
        //i dati dell'input nel messaggio seguono la convenzione stabilita in MultiParam
        this.message = new ToolCard9("primo", 1, new ArrayList<>(Arrays.asList(0, 12, 3)));

        Dice d1 = new Dice(Color.BLUE, 1);
        Dice d2 = new Dice(Color.BLUE, 1);
        Dice d3 = new Dice(Color.BLUE, 1);
        Dice d4 = new Dice(Color.BLUE, 1);
        Dice d5 = new Dice(Color.BLUE, 1);
        this.supportReserve = new Reserve(new ArrayList<Dice>(Arrays.asList(d1, d2, d3, d4, d5)));
        controller.getcAction().resettingReserve(supportReserve);

        riga.function(controller, message);


        fail("Fail: Non ha lanciato eccezione!");
    }
    @Test
    public void puttingWhiteAndNumber() throws InvalidValueException, InvalidCellException, InvalidSomethingWasNotDoneGood {
        try {
            //i dati dell'input nel messaggio seguono la convenzione stabilita in MultiParam
            this.message = new ToolCard9("primo", 1, new ArrayList<>(Arrays.asList(1, 0, 0)));

            Dice d1 = new Dice(Color.BLUE, 1);
            Dice d2 = new Dice(Color.GREEN, 4);
            Dice d3 = new Dice(Color.PURPLE, 4);
            Dice d4 = new Dice(Color.PURPLE, 5);
            Dice d5 = new Dice(Color.YELLOW, 1);

            this.supportReserve = new Reserve(new ArrayList<Dice>(Arrays.asList(d1, d2, d3, d4, d5)));
            controller.getcAction().resettingReserve(supportReserve);
            //posiziono il dado da spostare in seguito
            //POSIZIONO DUE DADI PER RISPETTARE ADIACENZA DEI DADI VOLTA PER VOLTA
            controller.getcAction().workOnSide("primo", controller.getcAction()
                    .pickFromReserve(0), 0, 2);
            //pesco di nuovo poszione zero perchè una volta rimosso il primo cambia l'indexing
            controller.getcAction().workOnSide("primo", controller.getcAction()
                    .pickFromReserve(1), 0, 3);

            riga.function(controller, message);
            //vedo se il dado contenuto nella cella è effettivamente il mio
            //il dado è stato posizionato dalla funzione nella cella
            Dice itsHim = controller.getcAction().takeALookToDie(message.getPlayer(), 0, 0);
            try {
                assertEquals(5, itsHim.getNumber());
                assertEquals(Color.PURPLE, itsHim.getColor());
            } catch (Exception e) {
                fail("errore su uguaglinza"+e);
            }

        } catch (Exception e) {
            fail("Fail: ha lanciato eccezione!");
        }
    }
    @Test
    public void puttingInColor() throws InvalidValueException, InvalidCellException, InvalidSomethingWasNotDoneGood {
        try {
            //i dati dell'input nel messaggio seguono la convenzione stabilita in MultiParam
            this.message = new ToolCard9("primo", 1, new ArrayList<>(Arrays.asList(0, 3, 3)));

            Dice d1 = new Dice(Color.BLUE, 1);
            Dice d2 = new Dice(Color.GREEN, 4);
            Dice d3 = new Dice(Color.PURPLE, 4);
            Dice d4 = new Dice(Color.PURPLE, 5);
            Dice d5 = new Dice(Color.YELLOW, 1);

            this.supportReserve = new Reserve(new ArrayList<Dice>(Arrays.asList(d1, d2, d3, d4, d5)));
            controller.getcAction().resettingReserve(supportReserve);
            //posiziono il dado da spostare in seguito
            //POSIZIONO DUE DADI PER RISPETTARE ADIACENZA DEI DADI VOLTA PER VOLTA
            controller.getcAction().workOnSide("primo", controller.getcAction()
                    .pickFromReserve(0), 0, 2);
            //pesco di nuovo poszione zero perchè una volta rimosso il primo cambia l'indexing
            controller.getcAction().workOnSide("primo", controller.getcAction()
                    .pickFromReserve(1), 0, 3);

            riga.function(controller, message);
            //vedo se il dado contenuto nella cella è effettivamente il mio
            //il dado è stato posizionato dalla funzione nella cella
            Dice itsHim = controller.getcAction().takeALookToDie(message.getPlayer(), 3, 3);
            try {
                assertEquals(4, itsHim.getNumber());
                assertEquals(Color.GREEN, itsHim.getColor());
            } catch (Exception e) {
                fail("errore su uguaglinza");
            }

        } catch (Exception e) {
            fail("Fail: ha lanciato eccezione!"+e);
        }
    }
    @Test
    public void firstTimer() throws InvalidValueException, InvalidCellException, InvalidSomethingWasNotDoneGood {
        try {

            this.message = new ToolCard9("primo", 1, new ArrayList<>(Arrays.asList(1, 3, 3)));

            Dice d1 = new Dice(Color.BLUE, 1);
            Dice d2 = new Dice(Color.GREEN, 4);
            Dice d3 = new Dice(Color.PURPLE, 4);
            Dice d4 = new Dice(Color.PURPLE, 5);
            Dice d5 = new Dice(Color.YELLOW, 1);

            this.supportReserve = new Reserve(new ArrayList<Dice>(Arrays.asList(d1, d2, d3, d4, d5)));
            controller.getcAction().resettingReserve(supportReserve);

            riga.function(controller, message);

            Dice itsHim = controller.getcAction().takeALookToDie(message.getPlayer(), 3, 3);

                assertEquals(4, itsHim.getNumber());
                assertEquals(Color.GREEN, itsHim.getColor());

        } catch (Exception e) {
            fail("Fail: ha lanciato eccezione!"+e);
        }
    }
    @Test(expected = InvalidCoordinatesException.class)
    public void adiacenza() throws InvalidValueException, InvalidCellException, InvalidSomethingWasNotDoneGood {

            //i dati dell'input nel messaggio seguono la convenzione stabilita in MultiParam
            this.message = new ToolCard9("primo", 1, new ArrayList<>(Arrays.asList(0, 0, 1)));

            Dice d1 = new Dice(Color.BLUE, 2);
            Dice d2 = new Dice(Color.GREEN, 4);
            Dice d3 = new Dice(Color.PURPLE, 4);
            Dice d4 = new Dice(Color.PURPLE, 5);
            Dice d5 = new Dice(Color.YELLOW, 1);

            this.supportReserve = new Reserve(new ArrayList<Dice>(Arrays.asList(d1, d2, d3, d4, d5)));
            controller.getcAction().resettingReserve(supportReserve);
            //posiziono il dado da spostare in seguito
            //POSIZIONO DUE DADI PER RISPETTARE ADIACENZA DEI DADI VOLTA PER VOLTA
            controller.getcAction().workOnSide("primo", controller.getcAction()
                    .pickFromReserve(0), 0, 2);

            riga.function(controller, message);
            Dice itsHim = controller.getcAction().takeALookToDie(message.getPlayer(), 0, 1);

                assertEquals(4, itsHim.getNumber());
                assertEquals(Color.GREEN, itsHim.getColor());

    }
}
