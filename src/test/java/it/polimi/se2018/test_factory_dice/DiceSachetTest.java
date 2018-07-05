package it.polimi.se2018.test_factory_dice;

import it.polimi.se2018.server.model.dice_sachet.Dice;
import it.polimi.se2018.server.model.dice_sachet.DiceSachet;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.fail;

public class DiceSachetTest {

    DiceSachet sacchetto;
    @Before
    public void bef(){
        sacchetto= new DiceSachet();
    }
    @Test
    public void tryOltre90(){
        try {
            ArrayList<Dice> test = new ArrayList<Dice>();
            for (int i = 0; i < 90; i++) {
                test.add(sacchetto.getDiceFromSachet());
            }
            for (int i = 0; i < 90; i++) {
                sacchetto.reput(test.get(i));
            }
        }
        catch(Exception e){
            fail();
        }
    }
}
