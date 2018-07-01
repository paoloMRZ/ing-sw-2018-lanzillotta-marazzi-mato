package it.polimi.se2018.test_controller;

import it.polimi.se2018.server.controller.Controller;
import it.polimi.se2018.server.exceptions.InvalidValueException;
import it.polimi.se2018.server.fake_view.FakeView;
import it.polimi.se2018.server.model.Color;
import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.server.model.card.card_schema.Cell;
import it.polimi.se2018.server.model.card.card_schema.Side;
import it.polimi.se2018.server.model.card.card_utensils.*;
import it.polimi.se2018.server.model.dice_sachet.Dice;
import it.polimi.se2018.server.model.reserve.Reserve;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MVCTest {
    private Side chosenOne = null;
    private ArrayList<Cell> sideContent = null;
    private FakeView fake;
    private Controller controller;
    private Reserve supportReserve = null;
    private ArrayList<Side> sides = new ArrayList<>();

    @Before
    public void setup() throws Exception {

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
        controller= new Controller(new ArrayList<String>(Arrays.asList("primo","secondo")));
        fake.register(controller);

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
    }
    @Test
    public void piazzamento() throws Exception {

        Dice d1 = new Dice(Color.BLUE, 5);
        Dice d2 = new Dice(Color.BLUE, 6);
        Dice d3 = new Dice(Color.BLUE, 6);
        Dice d4 = new Dice(Color.BLUE, 6);
        Dice d5 = new Dice(Color.BLUE, 6);
        this.supportReserve = new Reserve(new ArrayList<Dice>(Arrays.asList(d1, d2, d3, d4, d5)));
        controller.getcAction().resettingReserve(supportReserve);

        fake.messageIncoming("/primo/###/put/?/0&0&0");
        assertEquals("/###/primo/success/put/0&0&0\n",fake.getMessage());
    }
    @Test
    public void tool1() throws Exception {


        controller.resetUtensils(new ArrayList<Utensils>(Arrays.asList(new PinzaSgrossatrice(),
                                                                        new PennelloPerEglomise(),
                                                                            new AlesatorePerLaminaDiRame())));

        Dice d1 = new Dice(Color.RED, 5);
        Dice d2 = new Dice(Color.BLUE, 6);
        Dice d3 = new Dice(Color.BLUE, 6);
        Dice d4 = new Dice(Color.BLUE, 6);
        Dice d5 = new Dice(Color.BLUE, 6);
        this.supportReserve = new Reserve(new ArrayList<Dice>(Arrays.asList(d1, d2, d3, d4, d5)));
        controller.getcAction().resettingReserve(supportReserve);

        fake.messageIncoming("/primo/###/utensil/activate/0");

        assertEquals("/###/primo/success/activate/0&1&2&5\n",fake.getMessage());

        fake.messageIncoming("/primo/###/utensil/use/0&1&1&0");

        assertEquals("/###/primo/utensil/end/0&1&2&4\n",fake.getMessage());
    }
    @Test
    public void tool2() throws Exception {


        controller.resetUtensils(new ArrayList<Utensils>(Arrays.asList(new PinzaSgrossatrice(),
                new PennelloPerEglomise(),
                new AlesatorePerLaminaDiRame())));

        Dice d1 = new Dice(Color.BLUE, 4);
        Dice d2 = new Dice(Color.GREEN, 4);
        Dice d3 = new Dice(Color.PURPLE, 4);
        Dice d4 = new Dice(Color.PURPLE, 4);
        Dice d5 = new Dice(Color.YELLOW, 1);
        this.supportReserve = new Reserve(new ArrayList<Dice>(Arrays.asList(d1, d2, d3, d4, d5)));
        controller.getcAction().resettingReserve(supportReserve);

        fake.messageIncoming("/primo/###/put/?/0&0&2");

        assertEquals("/###/primo/success/put/0&0&2\n",fake.getMessage());

        fake.messageIncoming("/primo/###/utensil/activate/1");

        assertEquals("/###/primo/success/activate/1&2&2&5\n",fake.getMessage());

        fake.messageIncoming("/primo/###/utensil/use/1&2&0&2&0&3");

        assertEquals("/###/primo/utensil/end/1&2&2&4\n",fake.getMessage());
    }
    @Test
    public void tool3() throws Exception {



        controller.resetUtensils(new ArrayList<Utensils>(Arrays.asList(new PinzaSgrossatrice(),
                new PennelloPerEglomise(),
                new AlesatorePerLaminaDiRame())));

        Dice d1 = new Dice(Color.BLUE, 1);
        Dice d2 = new Dice(Color.GREEN, 4);
        Dice d3 = new Dice(Color.PURPLE, 4);
        Dice d4 = new Dice(Color.PURPLE, 4);
        Dice d5 = new Dice(Color.YELLOW, 1);
        this.supportReserve = new Reserve(new ArrayList<Dice>(Arrays.asList(d1, d2, d3, d4, d5)));
        controller.getcAction().resettingReserve(supportReserve);

        chosenOne.put(0,1,d2);
        chosenOne.put(0,2,d1);


        fake.messageIncoming("/primo/###/utensil/activate/2");

        assertEquals("/###/primo/success/activate/2&3&2&5\n",fake.getMessage());

        fake.messageIncoming("/primo/###/utensil/use/2&3&0&2&0&0");

        assertEquals("/###/primo/utensil/end/2&3&2&4\n",fake.getMessage());
    }
    @Test
    public void tool4() throws Exception {


        controller.resetUtensils(new ArrayList<Utensils>(Arrays.asList(new PinzaSgrossatrice(),
                new PennelloPerEglomise(),
                new Lathekin())));

        Dice d1 = new Dice(Color.BLUE, 1);
        Dice d2 = new Dice(Color.GREEN, 4);
        Dice d3 = new Dice(Color.PURPLE, 4);
        Dice d4 = new Dice(Color.PURPLE, 4);
        Dice d5 = new Dice(Color.YELLOW, 1);
        this.supportReserve = new Reserve(new ArrayList<Dice>(Arrays.asList(d1, d2, d3, d4, d5)));
        controller.getcAction().resettingReserve(supportReserve);

        chosenOne.put(0,1,d2);
        chosenOne.put(0,2,d1);


        fake.messageIncoming("/primo/###/utensil/activate/2");

        assertEquals("/###/primo/success/activate/2&4&2&5\n",fake.getMessage());

        fake.messageIncoming("/primo/###/utensil/use/2&4&0&1&1&1&0&2&1&2");

        assertEquals("/###/primo/utensil/end/2&4&2&4\n",fake.getMessage());
    }
    @Test
    public void tool5() throws Exception {


        controller.resetUtensils(new ArrayList<Utensils>(Arrays.asList(new TaglierinaCircolare(),
                new PennelloPerPastaSalda(),
                new Martelletto())));

        Dice d1 = new Dice(Color.BLUE, 1);
        Dice d2 = new Dice(Color.BLUE, 1);
        Dice d3 = new Dice(Color.BLUE, 1);
        Dice d4 = new Dice(Color.BLUE, 1);
        Dice d5 = new Dice(Color.BLUE, 1);
        this.supportReserve = new Reserve(new ArrayList<Dice>(Arrays.asList(d1, d2, d3, d4, d5)));
        controller.getcAction().resettingReserve(supportReserve);

        controller.getcAction().putOnGrid(0, new Dice(Color.YELLOW, 4));


        fake.messageIncoming("/primo/###/utensil/activate/0");

        assertEquals("/###/primo/success/activate/0&5&2&5\n",fake.getMessage());

        fake.messageIncoming("/primo/###/utensil/use/0&5&1&0&0");

        assertEquals("/###/primo/utensil/end/0&5&2&4\n",fake.getMessage());
    }
    @Test
    public void tool6() throws Exception {


        controller.resetUtensils(new ArrayList<Utensils>(Arrays.asList(new TaglierinaCircolare(),
                new PennelloPerPastaSalda(),
                new Martelletto())));

        Dice d1 = new Dice(Color.BLUE, 1);
        Dice d2 = new Dice(Color.BLUE, 1);
        Dice d3 = new Dice(Color.BLUE, 1);
        Dice d4 = new Dice(Color.BLUE, 1);
        Dice d5 = new Dice(Color.BLUE, 1);
        this.supportReserve = new Reserve(new ArrayList<Dice>(Arrays.asList(d1, d2, d3, d4, d5)));
        controller.getcAction().resettingReserve(supportReserve);



        fake.messageIncoming("/primo/###/utensil/activate/1");

        fake.messageIncoming("/primo/###/utensil/use/1&6&1&0");
        System.out.println(fake.getMessage());
        fake.messageIncoming("/primo/###/utensil/use/1&6bis&0&0&2");
        System.out.println(fake.getMessage());
        assertEquals("/###/primo/utensil/end/1&6&2&4\n",fake.getMessage());
    }
    @Test
    public void tool7ERROR() throws Exception {



        controller.resetUtensils(new ArrayList<Utensils>(Arrays.asList(new TaglierinaCircolare(),
                new PennelloPerPastaSalda(),
                new Martelletto())));

        Dice d1 = new Dice(Color.BLUE, 1);
        Dice d2 = new Dice(Color.BLUE, 1);
        Dice d3 = new Dice(Color.BLUE, 1);
        Dice d4 = new Dice(Color.BLUE, 1);
        Dice d5 = new Dice(Color.BLUE, 1);
        this.supportReserve = new Reserve(new ArrayList<Dice>(Arrays.asList(d1, d2, d3, d4, d5)));
        controller.getcAction().resettingReserve(supportReserve);



        fake.messageIncoming("/primo/###/utensil/activate/2");
        assertEquals("/###/primo/success/activate/2&7&2&5\n",fake.getMessage());
        fake.messageIncoming("/primo/###/utensil/use/2&7");

        assertEquals("/###/primo/error/activate/2&7&1&5\n",fake.getMessage());
    }
    @Test
    public void tool7() throws Exception {


        controller.resetUtensils(new ArrayList<Utensils>(Arrays.asList(new TaglierinaCircolare(),
                new PennelloPerPastaSalda(),
                new Martelletto())));

        Dice d1 = new Dice(Color.BLUE, 1);
        Dice d2 = new Dice(Color.BLUE, 1);
        Dice d3 = new Dice(Color.BLUE, 1);
        Dice d4 = new Dice(Color.BLUE, 1);
        Dice d5 = new Dice(Color.BLUE, 1);
        this.supportReserve = new Reserve(new ArrayList<Dice>(Arrays.asList(d1, d2, d3, d4, d5)));
        controller.getcAction().resettingReserve(supportReserve);

        fake.messageIncoming("/primo/###/update/turn/?");
        fake.messageIncoming("/secondo/###/update/turn/?");
        fake.messageIncoming("/secondo/###/update/turn/?");

        fake.messageIncoming("/primo/###/utensil/activate/2");
        assertEquals("/###/primo/success/activate/2&7&2&5\n",fake.getMessage());
        fake.messageIncoming("/primo/###/utensil/use/2&7");

        assertEquals("/###/primo/utensil/end/2&7&2&4\n",fake.getMessage());
    }
    @Test
    public void tool8() throws Exception {



        controller.resetUtensils(new ArrayList<Utensils>(Arrays.asList(new TenagliaARotelle(),
                new RigaInSughero(),
                new TamponeDiamantato())));

        Dice d1 = new Dice(Color.BLUE, 5);
        Dice d2 = new Dice(Color.GREEN, 4);
        Dice d3 = new Dice(Color.PURPLE, 4);
        Dice d4 = new Dice(Color.YELLOW, 4);
        Dice d5 = new Dice(Color.YELLOW, 1);
        this.supportReserve = new Reserve(new ArrayList<Dice>(Arrays.asList(d1, d2, d3, d4, d5)));
        controller.getcAction().resettingReserve(supportReserve);
        fake.messageIncoming("/primo/###/put/?/0&0&0");
        assertEquals("/###/primo/success/put/0&0&0\n",fake.getMessage());

        fake.messageIncoming("/primo/###/utensil/activate/0");
        assertEquals("/###/primo/success/activate/0&8&2&5\n",fake.getMessage());

        fake.messageIncoming("/primo/###/utensil/use/0&8&0&0&1");
        assertEquals("/###/primo/utensil/end/0&8&2&4\n",fake.getMessage());
    }
    @Test
    public void tool8ERROR() throws Exception {


        controller.resetUtensils(new ArrayList<Utensils>(Arrays.asList(new TenagliaARotelle(),
                new RigaInSughero(),
                new TamponeDiamantato())));

        Dice d1 = new Dice(Color.BLUE, 5);
        Dice d2 = new Dice(Color.GREEN, 4);
        Dice d3 = new Dice(Color.PURPLE, 4);
        Dice d4 = new Dice(Color.YELLOW, 4);
        Dice d5 = new Dice(Color.YELLOW, 1);
        this.supportReserve = new Reserve(new ArrayList<Dice>(Arrays.asList(d1, d2, d3, d4, d5)));
        controller.getcAction().resettingReserve(supportReserve);
        controller.getPlayerByName("primo").reductor();

        fake.messageIncoming("/primo/###/put/?/0&0&0");
        assertEquals("/###/primo/success/put/0&0&0\n",fake.getMessage());

        fake.messageIncoming("/primo/###/utensil/activate/0");
        assertEquals("/###/primo/success/activate/0&8&2&5\n",fake.getMessage());

        fake.messageIncoming("/primo/###/utensil/use/0&8&0&0&1");
        assertEquals("/###/primo/error/activate/0&8&1&5\n",fake.getMessage());
    }
    @Test
    public void tool9() throws Exception {



        controller.resetUtensils(new ArrayList<Utensils>(Arrays.asList(new TenagliaARotelle(),
                new RigaInSughero(),
                new TamponeDiamantato())));

        Dice d1 = new Dice(Color.BLUE, 5);
        Dice d2 = new Dice(Color.GREEN, 4);
        Dice d3 = new Dice(Color.PURPLE, 4);
        Dice d4 = new Dice(Color.YELLOW, 4);
        Dice d5 = new Dice(Color.YELLOW, 1);
        this.supportReserve = new Reserve(new ArrayList<Dice>(Arrays.asList(d1, d2, d3, d4, d5)));
        controller.getcAction().resettingReserve(supportReserve);

        fake.messageIncoming("/primo/###/utensil/activate/1");
        assertEquals("/###/primo/success/activate/1&9&2&5\n",fake.getMessage());

        fake.messageIncoming("/primo/###/utensil/use/1&9&1&0&1");
        assertEquals("/###/primo/utensil/end/1&9&2&4\n",fake.getMessage());
    }
    @Test
    public void tool10() throws Exception {


        controller.resetUtensils(new ArrayList<Utensils>(Arrays.asList(new TenagliaARotelle(),
                new RigaInSughero(),
                new TamponeDiamantato())));

        Dice d1 = new Dice(Color.BLUE, 5);
        Dice d2 = new Dice(Color.GREEN, 4);
        Dice d3 = new Dice(Color.PURPLE, 4);
        Dice d4 = new Dice(Color.YELLOW, 4);
        Dice d5 = new Dice(Color.YELLOW, 1);
        this.supportReserve = new Reserve(new ArrayList<Dice>(Arrays.asList(d1, d2, d3, d4, d5)));
        controller.getcAction().resettingReserve(supportReserve);

        fake.messageIncoming("/primo/###/utensil/activate/2");
        assertEquals("/###/primo/success/activate/2&10&2&5\n",fake.getMessage());

        fake.messageIncoming("/primo/###/utensil/use/2&10&1");
        assertEquals("/###/primo/utensil/end/2&10&2&4\n",fake.getMessage());
    }
    @Test
    public void tool11() throws Exception {



        controller.resetUtensils(new ArrayList<Utensils>(Arrays.asList(new TaglierinaCircolare(),
                new DiluentePerPastaSalda(),
                new TaglierinaManuale())));

        Dice d1 = new Dice(Color.BLUE, 1);
        Dice d2 = new Dice(Color.BLUE, 1);
        Dice d3 = new Dice(Color.BLUE, 1);
        Dice d4 = new Dice(Color.BLUE, 1);
        Dice d5 = new Dice(Color.BLUE, 1);
        this.supportReserve = new Reserve(new ArrayList<Dice>(Arrays.asList(d1, d2, d3, d4, d5)));
        controller.getcAction().resettingReserve(supportReserve);



        fake.messageIncoming("/primo/###/utensil/activate/1");

        fake.messageIncoming("/primo/###/utensil/use/1&11&1");
        System.out.println(fake.getMessage());
        fake.messageIncoming("/primo/###/utensil/use/1&11bis&5&0&0");
        System.out.println(fake.getMessage());
        System.out.println(controller.getcAction().getReserve().toString());
        System.out.println(controller.getPlayerByName("primo").getMySide().toString());

        assertEquals("/###/primo/utensil/end/1&11&2&4\n",fake.getMessage());
    }

    @Test
    public void tool12() throws Exception {


        controller.resetUtensils(new ArrayList<Utensils>(Arrays.asList(new TaglierinaCircolare(),
                new DiluentePerPastaSalda(),
                new TaglierinaManuale())));

        Dice d1 = new Dice(Color.BLUE, 1);
        Dice d2 = new Dice(Color.GREEN, 2);
        Dice d3 = new Dice(Color.BLUE, 5);
        Dice d4 = new Dice(Color.BLUE, 1);
        Dice d5 = new Dice(Color.BLUE, 1);
        this.supportReserve = new Reserve(new ArrayList<Dice>(Arrays.asList(d1, d2, d3, d4, d5)));
        controller.getcAction().resettingReserve(supportReserve);

        controller.getcAction().workOnSide("primo", controller.getcAction()
                .pickFromReserve(0), 0, 2);
        controller.getcAction().workOnSide("primo", controller.getcAction()
                .pickFromReserve(0), 0, 1);
        controller.getcAction().workOnSide("primo", controller.getcAction()
                .pickFromReserve(0), 1, 1);
        controller.getcAction().putOnGrid(0, new Dice(Color.BLUE, 4));


        fake.messageIncoming("/primo/###/utensil/activate/2");

        assertEquals("/###/primo/success/activate/2&12&2&5\n",fake.getMessage());

        fake.messageIncoming("/primo/###/utensil/use/2&12&0&0&1&1&0&0&0&2&1&1");

        assertEquals("/###/primo/utensil/end/2&12&2&4\n",fake.getMessage());
    }




}
