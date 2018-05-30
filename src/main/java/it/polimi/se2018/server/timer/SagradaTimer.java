package it.polimi.se2018.server.timer;

import java.util.Timer;

public class SagradaTimer extends ObservableTimer {
    private int lifeTime;
    private int counter;
    private Timer timer;
    private boolean isStarted;

    public SagradaTimer(int tick){
        if(tick > 0){
            this.lifeTime = tick;
            this.counter = tick;
            isStarted = false;
        }
    }

    //TODO la classe astratta serve veramente a qualcosa ?!?!?!?!?!
    public void add(ObserverTimer observerTimer){
        super.add(observerTimer);
    }

    public void remove(ObserverTimer observerTimer){
        super.remove(observerTimer);
    }



    public void start() {
        if(!isStarted) {
            timer = new Timer();
            timer.scheduleAtFixedRate(new Clock(this), 0, 1000);
            isStarted = true;
        }
    }

    public void stop() {
        if(isStarted) {
            timer.cancel();
            isStarted = false;
        }
    }

    public boolean isStarted(){
        return isStarted;
    }

    public void clockNotify(){
        System.out.print(counter); //TODO da rimuovere!
        counter--;

        if(counter== -1){
            //Quando il contarore è arrivato a zero lo resetto al valore impostato dall'utente.
            counter = lifeTime;

            //notifico gli osseravtori per avvisarli che il tempo è scaduto.
            super.timerNotify();
        }
    }
}
