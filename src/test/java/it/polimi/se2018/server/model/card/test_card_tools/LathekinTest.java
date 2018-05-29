package it.polimi.se2018.server.model.card.test_card_tools;

import it.polimi.se2018.server.controller.Controller;
import it.polimi.se2018.server.events.tool_mex.ToolCard4;
import it.polimi.se2018.server.exceptions.InvalidCellException;
import it.polimi.se2018.server.exceptions.InvalidValueException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.*;
import it.polimi.se2018.server.model.Color;
import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.server.model.card.card_schema.Cell;
import it.polimi.se2018.server.model.card.card_schema.Side;
import it.polimi.se2018.server.model.card.card_utensils.Lathekin;
import it.polimi.se2018.server.model.dice_sachet.Dice;
import it.polimi.se2018.server.model.reserve.Reserve;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

public class LathekinTest{
    private Lathekin lat = null;
    private ArrayList<Cell> sideContent = null;
    private Controller controller = null;
    private ToolCard4 message = null;
    private Side chosenOne = null;
    private Reserve supportReserve = null;
    private ArrayList<Side> sides = new ArrayList<>();

    @Before
    public void settings() throws InvalidValueException, IOException {
        this.lat = new Lathekin();

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

    //ricreo una riserva con un consono numero di dadi in base a quanti giovatori ho inserito
    //sebbene la riserva non sia fondamentale preparo l'environment per evitare problematiche
    @Test(expected = InvalidValueException.class)
    public void playerNameCorrupted() throws InvalidValueException, InvalidCellException, InvalidSomethingWasNotDoneGood {

        this.message = new ToolCard4("pippo", 1, new ArrayList(Arrays.asList(0, 1, 1, 1,  0, 2, 1, 2)));

        Dice d1 = new Dice(Color.BLUE, 1);
        Dice d2 = new Dice(Color.GREEN, 2);
        Dice d3 = new Dice(Color.BLUE, 1);
        Dice d4 = new Dice(Color.BLUE, 1);
        Dice d5 = new Dice(Color.BLUE, 1);
        this.supportReserve = new Reserve(new ArrayList<Dice>(Arrays.asList(d1, d2, d3, d4, d5)));
        controller.getcAction().resettingReserve(supportReserve);
        controller.getcAction().workOnSide("primo", controller.getcAction()
                .pickFromReserve(0), 0, 2);
        controller.getcAction().workOnSide("primo", controller.getcAction()
                .pickFromReserve(0), 0, 1);

        lat.function(controller, message);


        Assert.fail("Fail: Non ha lanciato eccezione!");
    }

    @Test(expected = InvalidCoordinatesException.class)
    public void piazzamentoCellNotExistingPicking() throws InvalidValueException, InvalidCellException, InvalidSomethingWasNotDoneGood {
        this.message = new ToolCard4("primo", 1, new ArrayList(Arrays.asList(11, 1, 1, 1,  0, 2, 1, 2)));

        Dice d1 = new Dice(Color.BLUE, 1);
        Dice d2 = new Dice(Color.GREEN, 2);
        Dice d3 = new Dice(Color.BLUE, 1);
        Dice d4 = new Dice(Color.BLUE, 1);
        Dice d5 = new Dice(Color.BLUE, 1);
        this.supportReserve = new Reserve(new ArrayList<Dice>(Arrays.asList(d1, d2, d3, d4, d5)));
        controller.getcAction().resettingReserve(supportReserve);
        controller.getcAction().workOnSide("primo", controller.getcAction()
                .pickFromReserve(0), 0, 2);
        controller.getcAction().workOnSide("primo", controller.getcAction()
                .pickFromReserve(0), 0, 1);

        lat.function(controller, message);


        Assert.fail("Fail: Non ha lanciato eccezione!");
    }

    @Test(expected = InvalidCoordinatesException.class)//sulla casella del colore sbagliato
    public void piazzamentoCellNotExistingPutting() throws InvalidValueException, InvalidCellException, InvalidSomethingWasNotDoneGood {
        //i dati dell'input nel messaggio seguono la convenzione stabilita in MultiParam
        this.message = new ToolCard4("primo", 1, new ArrayList<>(Arrays.asList(0, 1, 1, 11,  0, 2, 11, 3)));


        Dice d1 = new Dice(Color.BLUE, 1);
        Dice d2 = new Dice(Color.GREEN, 2);
        Dice d3 = new Dice(Color.BLUE, 1);
        Dice d4 = new Dice(Color.BLUE, 1);
        Dice d5 = new Dice(Color.BLUE, 1);
        this.supportReserve = new Reserve(new ArrayList<Dice>(Arrays.asList(d1, d2, d3, d4, d5)));
        controller.getcAction().resettingReserve(supportReserve);
        controller.getcAction().workOnSide("primo", controller.getcAction()
                .pickFromReserve(0), 0, 2);
        controller.getcAction().workOnSide("primo", controller.getcAction()
                .pickFromReserve(0), 0, 1);

        lat.function(controller, message);


        Assert.fail("Fail: Non ha lanciato eccezione!");
    }


    @Test
    public void puttingIsGood() throws InvalidValueException, InvalidCellException, InvalidSomethingWasNotDoneGood {
        try {
            //i dati dell'input nel messaggio seguono la convenzione stabilita in MultiParam
            this.message = new ToolCard4("primo", 1, new ArrayList<>(Arrays.asList(0, 1, 1, 1,  0, 2, 1, 2)));

            Dice d1 = new Dice(Color.BLUE, 1);
            Dice d2 = new Dice(Color.GREEN, 4);
            Dice d3 = new Dice(Color.PURPLE, 4);
            Dice d4 = new Dice(Color.PURPLE, 4);
            Dice d5 = new Dice(Color.YELLOW, 1);

            this.supportReserve = new Reserve(new ArrayList<Dice>(Arrays.asList(d1, d2, d3, d4, d5)));
            controller.getcAction().resettingReserve(supportReserve);
            //posiziono il dado da spostare in seguito
            //POSIZIONO DUE DADI PER RISPETTARE ADIACENZA DEI DADI VOLTA PER VOLTA

            controller.getcAction().workOnSide("primo", controller.getcAction()
                    .pickFromReserve(0), 0, 2);

            controller.getcAction().workOnSide("primo", controller.getcAction()
                    .pickFromReserve(0), 0, 1);

            lat.function(controller, message);
            //vedo se il dado contenuto nella cella è effettivamente il mio
            //il dado è stato posizionato dalla funzione nella cella
            Dice itsHim = controller.getcAction().takeALookToDie(message.getPlayer(), 1, 1);
            Dice itsHim2 = controller.getcAction().takeALookToDie(message.getPlayer(), 1, 2);

            try {
                assertEquals(4, itsHim.getNumber());
                assertEquals(Color.GREEN, itsHim.getColor());
                assertEquals(1, itsHim2.getNumber());
                assertEquals(Color.BLUE, itsHim2.getColor());
            } catch (Exception e) {
                fail("errore su uguaglinza");
            }

        } catch (Exception e) {
            fail("Fail: ha lanciato eccezione!");
        }
    }


    @Test(expected = InvalidCellException.class)//sulla casella del vicino sbagliato
    public void adiacenza() throws InvalidValueException, InvalidCellException, InvalidSomethingWasNotDoneGood {

            //i dati dell'input nel messaggio seguono la convenzione stabilita in MultiParam
            this.message = new ToolCard4("primo", 1, new ArrayList<>(Arrays.asList(0, 1, 1, 1,  0, 2, 1, 2)));

            Dice d1 = new Dice(Color.BLUE, 1);
            Dice d2 = new Dice(Color.GREEN, 4);
            Dice d3 = new Dice(Color.YELLOW, 5);
            Dice d4 = new Dice(Color.PURPLE, 4);
            Dice d5 = new Dice(Color.YELLOW, 1);

            this.supportReserve = new Reserve(new ArrayList<Dice>(Arrays.asList(d1, d2, d3, d4, d5)));
            controller.getcAction().resettingReserve(supportReserve);
            //posiziono il dado da spostare in seguito
            //POSIZIONO DUE DADI PER RISPETTARE ADIACENZA DEI DADI VOLTA PER VOLTA

            controller.getcAction().workOnSide("primo", controller.getcAction()
                    .pickFromReserve(0), 0, 2);

            controller.getcAction().workOnSide("primo", controller.getcAction()
                    .pickFromReserve(0), 0, 1);
            controller.getcAction().workOnSide("primo", controller.getcAction()
                    .pickFromReserve(0), 0, 0);

            controller.getcAction().workOnSide("primo", controller.getcAction()
                    .pickFromReserve(0), 1, 0);

            lat.function(controller, message);
            //vedo se il dado contenuto nella cella è effettivamente il mio
            //il dado è stato posizionato dalla funzione nella cella
            Dice itsHim = controller.getcAction().takeALookToDie(message.getPlayer(), 1, 1);
            Dice itsHim2 = controller.getcAction().takeALookToDie(message.getPlayer(), 1, 2);

            try {
                assertEquals(4, itsHim.getNumber());
                assertEquals(Color.GREEN, itsHim.getColor());
                assertEquals(1, itsHim2.getNumber());
                assertEquals(Color.BLUE, itsHim2.getColor());
            } catch (Exception e) {
                fail("errore su uguaglinza");
            }
            fail("Non ha lanciato eccezione");

    }

}
