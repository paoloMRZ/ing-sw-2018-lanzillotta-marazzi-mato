package it.polimi.se2018.server.timer;

import java.util.TimerTask;

public class Clock extends TimerTask {

    private SagradaTimer sagradaTimer;

    public Clock(SagradaTimer sagradaTimer){
        if(sagradaTimer != null) {
            this.sagradaTimer = sagradaTimer;
        }
    }

    @Override
    public void run() {
        sagradaTimer.clockNotify();
    }
}
