package it.polimi.se2018.server.model.card.test_card_tools;

import it.polimi.se2018.server.controller.Controller;
import it.polimi.se2018.server.exceptions.InvalidActivationException;
import it.polimi.se2018.server.exceptions.InvalidHowManyTimes;
import it.polimi.se2018.server.exceptions.InvalidValueException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidSomethingWasNotDoneGood;
import it.polimi.se2018.server.fake_view.FakeView;
import it.polimi.se2018.server.model.Color;
import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.server.model.Table;
import it.polimi.se2018.server.model.card.card_schema.Cell;
import it.polimi.se2018.server.model.card.card_schema.Side;
import it.polimi.se2018.server.model.card.card_utensils.Martelletto;
import it.polimi.se2018.server.model.card.card_utensils.PennelloPerPastaSalda;
import it.polimi.se2018.server.model.card.card_utensils.TaglierinaCircolare;
import it.polimi.se2018.server.model.card.card_utensils.Utensils;
import it.polimi.se2018.server.model.dice_sachet.Dice;
import it.polimi.se2018.server.model.dice_sachet.DiceSachet;
import it.polimi.se2018.server.model.reserve.Reserve;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;


public class MartellettoTest {

    private Martelletto mar= new Martelletto();

    private ArrayList<Integer> countingBefore;
    private ArrayList<Color> colorsBefore;

    private ArrayList<Integer> countingAfter;
    private ArrayList<Color> colorsAfter;


    private Side chosenOne = null;
    private ArrayList<Cell> sideContent = null;
    private FakeView fake;
    private Controller controller;
    private Reserve supportReserve = null;
    private ArrayList<Side> sides = new ArrayList<>();

    @Before
    public void setup() throws InvalidValueException {

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

        fake=new FakeView();
        controller= new Controller(new ArrayList<String>(Arrays.asList("primo","secondo")),10);
        fake.register(controller);

        countingBefore=new ArrayList<>();
        colorsBefore=new ArrayList<>();

        countingAfter=new ArrayList<>();
        colorsAfter=new ArrayList<>();

    }

    @Test
    public void tool7ERROR() throws Exception {

        controller.START();
        fake.messageIncoming("/primo/###/start/side_reply/0");
        fake.messageIncoming("/secondo/###/start/side_reply/0");

        Player player1 = controller.getPlayerByName("primo");
        Player player2 = controller.getPlayerByName("secondo");
        player1.setSideSelection(sides);
        player1.setMySide(0);
        player1.setFavours();
        player2.setSideSelection(sides);
        player2.setMySide(0);
        player2.setFavours();

        controller.resetUtensils(new ArrayList<Utensils>(Arrays.asList(new TaglierinaCircolare(),
                new PennelloPerPastaSalda(),
                new Martelletto())));

        Dice d1 = new Dice(Color.BLUE, 1);
        Dice d2 = new Dice(Color.GREEN, 4);
        Dice d3 = new Dice(Color.PURPLE, 4);
        Dice d4 = new Dice(Color.YELLOW, 4);
        Dice d5 = new Dice(Color.YELLOW, 1);
        this.supportReserve = new Reserve(new ArrayList<Dice>(Arrays.asList(d1, d2, d3, d4, d5)));
        controller.getcAction().resettingReserve(supportReserve);

        fake.messageIncoming("/primo/###/utensil/activate/2");
        counter(true);
        fake.messageIncoming("/primo/###/utensil/use/2&7");
        counter(false);
        assertEquals("/###/primo/error/activate/2&7&1&5\n",fake.getMessage());
        comparing();
    }
    @Test
    public void tool7() throws Exception {

        controller.START();
        fake.messageIncoming("/primo/###/start/side_reply/0");
        fake.messageIncoming("/secondo/###/start/side_reply/0");

        Player player1 = controller.getPlayerByName("primo");
        Player player2 = controller.getPlayerByName("secondo");
        player1.setSideSelection(sides);
        player1.setMySide(0);
        player1.setFavours();
        player2.setSideSelection(sides);
        player2.setMySide(0);
        player2.setFavours();

        controller.resetUtensils(new ArrayList<Utensils>(Arrays.asList(new TaglierinaCircolare(),
                new PennelloPerPastaSalda(),
                new Martelletto())));

        Dice d1 = new Dice(Color.BLUE, 1);
        Dice d2 = new Dice(Color.GREEN, 4);
        Dice d3 = new Dice(Color.PURPLE, 4);
        Dice d4 = new Dice(Color.YELLOW, 4);
        Dice d5 = new Dice(Color.YELLOW, 1);
        Dice d6 = new Dice(Color.BLUE, 1);
        Dice d7 = new Dice(Color.GREEN, 4);
        Dice d8 = new Dice(Color.PURPLE, 4);
        Dice d9 = new Dice(Color.YELLOW, 4);
        Dice d10 = new Dice(Color.YELLOW, 1);
        Dice d11 = new Dice(Color.BLUE, 1);
        Dice d12 = new Dice(Color.GREEN, 4);
        Dice d13 = new Dice(Color.PURPLE, 4);

        this.supportReserve = new Reserve(new ArrayList<Dice>(Arrays.asList(d1, d2, d3, d4, d5, d6, d7 ,d8, d9,d10,d11,d12,d13)));
        controller.getcAction().resettingReserve(supportReserve);
        String prima =supportReserve.toString();
        fake.messageIncoming("/primo/###/update/turn/?");
        fake.messageIncoming("/secondo/###/update/turn/?");
        fake.messageIncoming("/secondo/###/update/turn/?");

        fake.messageIncoming("/primo/###/utensil/activate/2");
        counter(true);
        fake.messageIncoming("/primo/###/utensil/use/2&7");
        counter(false);
        comparing();
        String dopo = controller.getcAction().getReserve().toString();
        System.out.println(prima+"!"+dopo);
        assertTrue(!prima.equals(dopo));
        //todo può essere utile come tipo di testing? ci sono probabilità, basse, che sia uguale a prima
    }


    private void counter(boolean isBefore){
        ArrayList<Dice> container = controller.getcAction().getReserve().getDices();

        for (Dice d: container) {
            if(isBefore){
                if(colorsBefore.contains(d.getColor())){
                    int where=colorsBefore.indexOf(d.getColor());
                    countingBefore.set(where,countingBefore.get(where)+1);
                }
                else{
                    colorsBefore.add(d.getColor());
                    countingBefore.add(1);
                }
            }
            else{
                if(colorsAfter.contains(d.getColor())){
                    int where=colorsAfter.indexOf(d.getColor());
                    countingAfter.set(where,countingAfter.get(where)+1);
                }
                else{
                    colorsAfter.add(d.getColor());
                    countingAfter.add(1);
                }
            }
        }
    }

    private void comparing() throws InvalidSomethingWasNotDoneGood {
        for(Color c: colorsBefore){
            if(countingBefore.get(colorsBefore.indexOf(c))!= countingAfter.get(colorsAfter.indexOf(c))) throw new InvalidSomethingWasNotDoneGood();
        }
    }
}
