package it.polimi.se2018.server.model.card.test_card_tools;

import it.polimi.se2018.server.controller.Controller;
import it.polimi.se2018.server.events.tool_mex.ToolCard10;
import it.polimi.se2018.server.exceptions.InvalidValueException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidSomethingWasNotDoneGood;
import it.polimi.se2018.server.fake_view.FakeView;
import it.polimi.se2018.server.model.Color;
import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.server.model.card.card_utensils.TamponeDiamantato;
import it.polimi.se2018.server.model.dice_sachet.Dice;
import it.polimi.se2018.server.model.reserve.Reserve;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

public class TamponeDiamantatoTest {
    private TamponeDiamantato tampone=null;
    private Controller controller=null;
    private ToolCard10 message=null;
    private Reserve supportReserve=null;

    @Before
    public void settings(){

        this.tampone=new TamponeDiamantato();
        FakeView fake = new FakeView();
        controller = new Controller(new ArrayList<>(Arrays.asList("primo","secondo")));
        fake.register(controller);

    }
    @Test(expected = InvalidValueException.class)
    public void dieNotExisting() throws InvalidValueException, InvalidSomethingWasNotDoneGood {

        this.message = new ToolCard10("primo", 1, 999);

        Dice d1 = new Dice(Color.BLUE, 3);
        Dice d2 = new Dice(Color.PURPLE, 3);
        Dice d3 = new Dice(Color.GREEN, 3);
        Dice d4 = new Dice(Color.YELLOW, 3);
        Dice d5 = new Dice(Color.RED, 3);
        this.supportReserve = new Reserve(new ArrayList<Dice>(Arrays.asList(d1, d2, d3, d4, d5)));
        controller.getcAction().resettingReserve(supportReserve);
        tampone.function(controller,message);


        fail("Fail: Non ha lanciato eccezione!");
    }
    @Test
    public void itWorks() throws InvalidValueException, InvalidSomethingWasNotDoneGood {

        Dice tester;

        Dice d1 = new Dice(Color.BLUE, 2);
        Dice d2 = new Dice(Color.PURPLE, 4);
        Dice d3 = new Dice(Color.GREEN, 3);
        Dice d4 = new Dice(Color.YELLOW, 1);
        Dice d5 = new Dice(Color.RED, 6);
        this.supportReserve = new Reserve(new ArrayList<Dice>(Arrays.asList(d1, d2, d3, d4, d5)));
        controller.getcAction().resettingReserve(supportReserve);

        try {
            this.message = new ToolCard10("primo", 1, 0);
            tampone.function(controller, message);
            tester = controller.getcAction().pickFromReserve(4);
            assertEquals(5, tester.getNumber());
            assertEquals(Color.BLUE, tester.getColor());
        } catch (Exception e) {
            fail("Fail1: ha lanciato eccezione!" + e);
        }
        try {
            this.message = new ToolCard10("primo", 1, 0);
            tampone.function(controller, message);
            tester = controller.getcAction().pickFromReserve(3);
            assertEquals(3, tester.getNumber());
            assertEquals(Color.PURPLE, tester.getColor());
        } catch (Exception e) {
            fail("Fail2: ha lanciato eccezione!" + e);
        }
        try {
            this.message = new ToolCard10("primo", 1, 0);
            tampone.function(controller, message);
            tester = controller.getcAction().pickFromReserve(2);
            assertEquals(4, tester.getNumber());
            assertEquals(Color.GREEN, tester.getColor());
        } catch (Exception e) {
            fail("Fail3: ha lanciato eccezione!" + e);
        }
        try {
            this.message = new ToolCard10("primo", 1, 0);
            tampone.function(controller, message);
            tester = controller.getcAction().pickFromReserve(1);
            assertEquals(6, tester.getNumber());
            assertEquals(Color.YELLOW, tester.getColor());
        } catch (Exception e) {
            fail("Fail4: ha lanciato eccezione!" + e);
        }
        try {
            this.message = new ToolCard10("primo", 1, 0);
            tampone.function(controller, message);
            tester = controller.getcAction().pickFromReserve(0);
            assertEquals(1, tester.getNumber());
            assertEquals(Color.RED, tester.getColor());
        } catch (Exception e) {
            fail("Fail5: ha lanciato eccezione!" + e);
        }
    }
}
