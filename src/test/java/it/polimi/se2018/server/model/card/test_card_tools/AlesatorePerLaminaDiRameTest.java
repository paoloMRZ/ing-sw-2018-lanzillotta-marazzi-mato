package it.polimi.se2018.server.model.card.test_card_tools;

import it.polimi.se2018.server.controller.Controller;
import it.polimi.se2018.server.events.tool_mex.ToolCard3;
import it.polimi.se2018.server.exceptions.InvalidCellException;
import it.polimi.se2018.server.exceptions.InvalidValueException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.*;
import it.polimi.se2018.server.model.Color;
import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.server.model.card.card_schema.Cell;
import it.polimi.se2018.server.model.card.card_schema.Side;
import it.polimi.se2018.server.model.card.card_utensils.AlesatorePerLaminaDiRame;
import it.polimi.se2018.server.model.dice_sachet.Dice;
import it.polimi.se2018.server.model.reserve.Reserve;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class AlesatorePerLaminaDiRameTest {
    private AlesatorePerLaminaDiRame lamina = null;
    private ArrayList<Cell> sideContent = null;
    private Controller controller = null;
    private ToolCard3 message = null;
    private Side chosenOne = null;
    private Reserve supportReserve = null;
    private ArrayList<Side> sides = new ArrayList<>();


    @Before
    public void settings() throws InvalidValueException {
        this.lamina = new AlesatorePerLaminaDiRame();

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

        chosenOne = new Side("toTEST", 5, sideContent);
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

    //ricreo una riserva con un consono numero di dadi in base a quanti giovatori ho inserito
    //sebbene la riserva non sia fondamentale preparo l'environment per evitare problematiche
    @Test(expected = InvalidValueException.class)
    public void playerNameCorrupted() throws InvalidValueException, InvalidCellException, InvalidSomethingWasNotDoneGood {

        this.message = new ToolCard3("pippo", 1, new ArrayList<>(Arrays.asList(0, 0, 1, 1)));

        Dice d1 = new Dice(Color.BLUE, 1);
        Dice d2 = new Dice(Color.BLUE, 1);
        Dice d3 = new Dice(Color.BLUE, 1);
        Dice d4 = new Dice(Color.BLUE, 1);
        Dice d5 = new Dice(Color.BLUE, 1);
        this.supportReserve = new Reserve(new ArrayList<>(Arrays.asList(d1, d2, d3, d4, d5)));
        controller.getcAction().resettingReserve(supportReserve);
        controller.getcAction().workOnSide("primo", controller.getcAction()
                .pickFromReserve(0), 0, 2);

        lamina.function(controller, message);


        fail("Fail: Non ha lanciato eccezione!");
    }

    @Test(expected = InvalidValueException.class)
    public void dieNotExisting() throws InvalidValueException, InvalidCellException{
        //i dati dell'input nel messaggio seguono la convenzione stabilita in MultiParam
        this.message = new ToolCard3("primo", 1, new ArrayList<>(Arrays.asList(0, 0, 0, 2)));

        Dice d1 = new Dice(Color.BLUE, 1);
        Dice d2 = new Dice(Color.BLUE, 1);
        Dice d3 = new Dice(Color.BLUE, 1);
        Dice d4 = new Dice(Color.BLUE, 1);
        Dice d5 = new Dice(Color.BLUE, 1);
        this.supportReserve = new Reserve(new ArrayList<>(Arrays.asList(d1, d2, d3, d4, d5)));
        controller.getcAction().resettingReserve(supportReserve);
        lamina.function(controller, message);


        fail("Fail: Non ha lanciato eccezione!");
    }

    @Test(expected = InvalidCoordinatesException.class)
    public void piazzamentoCellNotExistingPicking() throws InvalidValueException, InvalidCellException{
        //i dati dell'input nel messaggio seguono la convenzione stabilita in MultiParam
        this.message = new ToolCard3("primo", 1, new ArrayList<>(Arrays.asList(11, 11, 2, 3)));

        Dice d1 = new Dice(Color.BLUE, 1);
        Dice d2 = new Dice(Color.BLUE, 1);
        Dice d3 = new Dice(Color.BLUE, 1);
        Dice d4 = new Dice(Color.BLUE, 1);
        Dice d5 = new Dice(Color.BLUE, 1);
        this.supportReserve = new Reserve(new ArrayList<>(Arrays.asList(d1, d2, d3, d4, d5)));
        controller.getcAction().resettingReserve(supportReserve);
        lamina.function(controller, message);


        fail("Fail: Non ha lanciato eccezione!");
    }

    @Test(expected = InvalidCoordinatesException.class)//sulla casella del colore sbagliato
    public void piazzamentoCellNotExistingPutting() throws InvalidValueException, InvalidCellException, InvalidSomethingWasNotDoneGood {
        //i dati dell'input nel messaggio seguono la convenzione stabilita in MultiParam
        this.message = new ToolCard3("primo", 1, new ArrayList<>(Arrays.asList(0, 2, 11, 11)));


        Dice d1 = new Dice(Color.BLUE, 1);
        Dice d2 = new Dice(Color.GREEN, 2);
        Dice d3 = new Dice(Color.BLUE, 1);
        Dice d4 = new Dice(Color.BLUE, 1);
        Dice d5 = new Dice(Color.BLUE, 1);
        this.supportReserve = new Reserve(new ArrayList<>(Arrays.asList(d1, d2, d3, d4, d5)));
        controller.getcAction().resettingReserve(supportReserve);
        controller.getcAction().workOnSide("primo", controller.getcAction()
                .pickFromReserve(0), 0, 2);
        controller.getcAction().workOnSide("primo", controller.getcAction()
                .pickFromReserve(0), 0, 1);

        lamina.function(controller, message);


        Assert.fail("Fail: Non ha lanciato eccezione!");
    }


    @Test
    public void puttingIgnoreValueIsGood(){
        try {
            //i dati dell'input nel messaggio seguono la convenzione stabilita in MultiParam
            this.message = new ToolCard3("primo", 1, new ArrayList<>(Arrays.asList(0, 2, 0, 0)));

            Dice d1 = new Dice(Color.BLUE, 1);
            Dice d2 = new Dice(Color.GREEN, 4);
            Dice d3 = new Dice(Color.PURPLE, 4);
            Dice d4 = new Dice(Color.PURPLE, 4);
            Dice d5 = new Dice(Color.YELLOW, 1);

            this.supportReserve = new Reserve(new ArrayList<>(Arrays.asList(d1, d2, d3, d4, d5)));
            controller.getcAction().resettingReserve(supportReserve);
            //posiziono il dado da spostare in seguito
            //POSIZIONO DUE DADI PER RISPETTARE ADIACENZA DEI DADI VOLTA PER VOLTA
            controller.getcAction().workOnSide("primo", controller.getcAction()
                    .pickFromReserve(0), 0, 2);
            //pesco di nuovo poszione zero perchè una volta rimosso il primo cambia l'indexing
            controller.getcAction().workOnSide("primo", controller.getcAction()
                    .pickFromReserve(0), 0, 1);

            lamina.function(controller, message);
            //vedo se il dado contenuto nella cella è effettivamente il mio
            //il dado è stato posizionato dalla funzione nella cella
            Dice itsHim = controller.getcAction().takeALookToDie(message.getPlayer(), 0, 0);
            try {
                assertEquals(1, itsHim.getNumber());
                assertEquals(Color.BLUE, itsHim.getColor());
            } catch (Exception e) {
                fail("errore su uguaglinza");
            }

        } catch (Exception e) {
            fail("Fail: ha lanciato eccezione!");
        }
    }
    //qunado sposto il primo dado immesso
    @Test
    public void puttingIgnoreValueIsGoodFirstTimer(){
        try {
            //i dati dell'input nel messaggio seguono la convenzione stabilita in MultiParam
            this.message = new ToolCard3("primo", 1, new ArrayList<>(Arrays.asList(0, 2, 0, 0)));

            Dice d1 = new Dice(Color.BLUE, 4);
            Dice d2 = new Dice(Color.GREEN, 4);
            Dice d3 = new Dice(Color.PURPLE, 4);
            Dice d4 = new Dice(Color.PURPLE, 4);
            Dice d5 = new Dice(Color.YELLOW, 1);

            this.supportReserve = new Reserve(new ArrayList<>(Arrays.asList(d1, d2, d3, d4, d5)));
            controller.getcAction().resettingReserve(supportReserve);
            //posiziono il dado da spostare in seguito
            //POSIZIONO DUE DADI PER RISPETTARE ADIACENZA DEI DADI VOLTA PER VOLTA
            controller.getcAction().workOnSide("primo", controller.getcAction()
                    .pickFromReserve(0), 0, 2);

            lamina.function(controller, message);
            //vedo se il dado contenuto nella cella è effettivamente il mio
            //il dado è stato posizionato dalla funzione nella cella
            Dice itsHim = controller.getcAction().takeALookToDie(message.getPlayer(), 0, 0);
            try {
                assertEquals(4, itsHim.getNumber());
                assertEquals(Color.BLUE, itsHim.getColor());
            } catch (Exception e) {
                fail("errore su uguaglinza");
            }

        } catch (Exception e) {
            fail("Fail: ha lanciato eccezione!");
        }
    }
    //todo capire cosa si intende per restrizione di colore
    @Test(expected = InvalidCellException.class)//sulla casella del vicino sbagliato
    public void adiacenza() throws InvalidValueException, InvalidCellException, InvalidSomethingWasNotDoneGood {

            //i dati dell'input nel messaggio seguono la convenzione stabilita in MultiParam
            this.message = new ToolCard3("primo", 1, new ArrayList<>(Arrays.asList(0, 3, 0, 0)));

            Dice d1 = new Dice(Color.BLUE, 1);
            Dice d2 = new Dice(Color.GREEN, 4);
            Dice d3 = new Dice(Color.PURPLE, 4);
            Dice d4 = new Dice(Color.YELLOW, 4);
            Dice d5 = new Dice(Color.YELLOW, 1);

            this.supportReserve = new Reserve(new ArrayList<>(Arrays.asList(d1, d2, d3, d4, d5)));
            controller.getcAction().resettingReserve(supportReserve);
            //posiziono il dado da spostare in seguito
            //POSIZIONO DUE DADI PER RISPETTARE ADIACENZA DEI DADI VOLTA PER VOLTA
            controller.getcAction().workOnSide("primo", controller.getcAction()
                    .pickFromReserve(0), 0, 2);
            //pesco di nuovo poszione zero perchè una volta rimosso il primo cambia l'indexing
            controller.getcAction().workOnSide("primo", controller.getcAction()
                    .pickFromReserve(0), 0, 1);
            controller.getcAction().workOnSide("primo", controller.getcAction()
                    .pickFromReserve(0), 0, 3);

            lamina.function(controller, message);
            //vedo se il dado contenuto nella cella è effettivamente il mio
            //il dado è stato posizionato dalla funzione nella cella
            Dice itsHim = controller.getcAction().takeALookToDie(message.getPlayer(), 0, 1);
            try {
                assertEquals(4, itsHim.getNumber());
                assertEquals(Color.GREEN, itsHim.getColor());
            } catch (Exception e) {
                fail("errore su uguaglinza");
            }


            fail("Fail: non ha lanciato eccezione!");


    }

    @Test(expected = InvalidCellException.class)//sulla casella del vicino sbagliato
    public void testForColor() throws InvalidValueException, InvalidCellException, InvalidSomethingWasNotDoneGood {

            //i dati dell'input nel messaggio seguono la convenzione stabilita in MultiParam
            this.message = new ToolCard3("primo", 1, new ArrayList<>(Arrays.asList(0, 2, 0, 3)));

            Dice d1 = new Dice(Color.BLUE, 1);
            Dice d2 = new Dice(Color.GREEN, 4);
            Dice d3 = new Dice(Color.GREEN, 4);
            Dice d4 = new Dice(Color.YELLOW, 4);
            Dice d5 = new Dice(Color.YELLOW, 1);

            this.supportReserve = new Reserve(new ArrayList<>(Arrays.asList(d1, d2, d3, d4, d5)));
            controller.getcAction().resettingReserve(supportReserve);
            //posiziono il dado da spostare in seguito
            //POSIZIONO DUE DADI PER RISPETTARE ADIACENZA DEI DADI VOLTA PER VOLTA

            controller.getcAction().workOnSide("primo", controller.getcAction()
                    .pickFromReserve(0), 0, 2);
            //pesco di nuovo poszione zero perchè una volta rimosso il primo cambia l'indexing
            controller.getcAction().workOnSide("primo", controller.getcAction()
                    .pickFromReserve(0), 0, 1);

            lamina.function(controller, message);
            //vedo se il dado contenuto nella cella è effettivamente il mio
            //il dado è stato posizionato dalla funzione nella cella
            Dice itsHim = controller.getcAction().takeALookToDie(message.getPlayer(), 0, 3);
            try {
                assertEquals(1, itsHim.getNumber());
                assertEquals(Color.BLUE, itsHim.getColor());
            } catch (Exception e) {
                fail("errore su uguaglinza");
            }


            fail("Fail: non ha lanciato eccezione!");


    }
}
