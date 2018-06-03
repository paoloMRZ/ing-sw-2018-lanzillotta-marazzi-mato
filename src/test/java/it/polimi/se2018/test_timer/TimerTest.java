package it.polimi.se2018.test_timer;

import it.polimi.se2018.server.timer.ObserverTimer;
import it.polimi.se2018.server.timer.SagradaTimer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TimerTest {

    private class ObserverTest implements ObserverTimer{

        @Override
        public void timerUpdate() {

        }
    }

    private SagradaTimer timer;


    @Before
    public void setUpTimer(){
        timer = new SagradaTimer(0);
        assertTrue(timer.getCounter() == 30);

        timer = new SagradaTimer(120);
        assertTrue(timer.getCounter() == 120);

        ObserverTimer observerTimer = new ObserverTest();
        timer.add(observerTimer);
    }

    @Test
    public void startTest(){
        assertTrue(!timer.isStarted());

        timer.start();
        assertTrue(timer.isStarted());

        timer.start(); //Serve a verificare che il secondo start non ha effetto.
        assertTrue(timer.isStarted());

        timer.stop();
    }

    @Test
    public void stopTest(){
        assertTrue(!timer.isStarted());

        timer.start();
        assertTrue(timer.isStarted());

        timer.stop();
        assertTrue(!timer.isStarted());

        timer.stop();//Serve a verificare che il secondo stop non ha effetto.
        assertTrue(!timer.isStarted());
    }

    @Test
    public void resetTest(){
        assertTrue(!timer.isStarted());

        timer.reset();
        assertTrue(!timer.isStarted());

        timer.start();
        assertTrue(timer.isStarted());

        timer.reset();
        assertTrue(timer.isStarted());

        timer.stop();

    }


}
