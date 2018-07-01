package it.polimi.se2018.test_controller;

import it.polimi.se2018.server.controller.Controller;
import it.polimi.se2018.server.fake_view.FakeView;
import it.polimi.se2018.server.model.Color;
import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.server.model.card.card_schema.Cell;
import it.polimi.se2018.server.model.card.card_schema.Side;
import it.polimi.se2018.server.model.card.card_utensils.AlesatorePerLaminaDiRame;
import it.polimi.se2018.server.model.card.card_utensils.PennelloPerEglomise;
import it.polimi.se2018.server.model.card.card_utensils.PinzaSgrossatrice;
import it.polimi.se2018.server.model.dice_sachet.Dice;
import it.polimi.se2018.server.model.reserve.Reserve;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class ControllerTurnTest{


    private Side chosenOne = null;
    private FakeView fake;
    private Controller controller;
    private Reserve supportReserve = null;
    private ArrayList<Side> sides = new ArrayList<>();

    @Before
    public void setup() throws Exception {

        ArrayList<Cell> sideContent = new ArrayList<>(20);
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
        controller= new Controller(new ArrayList<>(Arrays.asList("primo","secondo")));
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
    public void pass(){
        fake.messageIncoming("/primo/###/update/turn/?");
        fake.messageIncoming("/secondo/###/update/turn/?");
        fake.messageIncoming("/secondo/###/update/turn/?");

        assertEquals("/###/!/update/turn/primo\n",fake.getMessage());
    }
    @Test
    public void freeze(){
        fake.messageIncoming("/###/###/network/freeze/secondo");
        fake.messageIncoming("/primo/###/update/turn/?");
        assertEquals("/###/!/update/turn/primo\n",fake.getMessage());
    }
    @Test
    public void unfreeze(){
        fake.messageIncoming("/###/###/network/freeze/secondo");
        fake.messageIncoming("/primo/###/update/turn/?");
        assertEquals("/###/!/update/turn/primo\n",fake.getMessage());
        fake.messageIncoming("/###/###/network/unfreeze/secondo");
        fake.messageIncoming("/primo/###/update/turn/?");
        assertEquals("/###/!/update/turn/secondo\n",fake.getMessage());
    }
    @Test
    public void alreadyDonepiazzamento(){
        controller.resetUtensils(new ArrayList<>(Arrays.asList(new PinzaSgrossatrice(),
                new PennelloPerEglomise(),
                new AlesatorePerLaminaDiRame())));
        Dice d1 = new Dice(Color.RED, 5);
        Dice d2 = new Dice(Color.BLUE, 5);
        Dice d3 = new Dice(Color.BLUE, 6);
        Dice d4 = new Dice(Color.BLUE, 6);
        Dice d5 = new Dice(Color.BLUE, 6);
        this.supportReserve = new Reserve(new ArrayList<>(Arrays.asList(d1, d2, d3, d4, d5)));
        controller.getcAction().resettingReserve(supportReserve);

        fake.messageIncoming("/primo/###/put/?/0&0&0");
        assertEquals("/###/primo/success/put/0&0&0\n",fake.getMessage());
        fake.messageIncoming("/primo/###/put/?/0&0&1");
        assertEquals("/###/primo/error/unauthorized\n",fake.getMessage());

        fake.messageIncoming("/primo/###/utensil/activate/0");
        assertEquals("/###/primo/success/activate/0&1&2&5\n",fake.getMessage());
        fake.messageIncoming("/primo/###/utensil/use/0&1&1&0");
        assertEquals("/###/primo/utensil/end/0&1&2&4\n",fake.getMessage());

        fake.messageIncoming("/primo/###/utensil/activate/0");
        assertEquals("/###/primo/error/unauthorized\n",fake.getMessage());
    }
    @Test
    public void alreadyDoneCard(){
        controller.resetUtensils(new ArrayList<>(Arrays.asList(new PinzaSgrossatrice(),
                new PennelloPerEglomise(),
                new AlesatorePerLaminaDiRame())));
        Dice d1 = new Dice(Color.RED, 5);
        Dice d2 = new Dice(Color.BLUE, 5);
        Dice d3 = new Dice(Color.BLUE, 6);
        Dice d4 = new Dice(Color.BLUE, 6);
        Dice d5 = new Dice(Color.BLUE, 6);
        this.supportReserve = new Reserve(new ArrayList<>(Arrays.asList(d1, d2, d3, d4, d5)));
        controller.getcAction().resettingReserve(supportReserve);

        fake.messageIncoming("/primo/###/utensil/activate/0");
        assertEquals("/###/primo/success/activate/0&1&2&5\n",fake.getMessage());
        fake.messageIncoming("/primo/###/utensil/use/0&1&1&0");
        assertEquals("/###/primo/utensil/end/0&1&2&4\n",fake.getMessage());

        fake.messageIncoming("/primo/###/utensil/activate/0");
        assertEquals("/###/primo/error/unauthorized\n",fake.getMessage());
    }

}
