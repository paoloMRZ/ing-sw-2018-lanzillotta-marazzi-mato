package it.polimi.se2018.server.timer;

import java.util.ArrayList;

public abstract class ObservableTimer {

    private ArrayList<ObserverTimer> osservatori;

    public ObservableTimer(){
        osservatori = new ArrayList<>();
    }

    public void add(ObserverTimer observerTimer){
        if(observerTimer != null) //TODO Ci sarebbe da controllare anche se è già presente.
            osservatori.add(observerTimer);
    }

    public void remove(ObserverTimer observerTimer){
        if(observerTimer != null) //TODO Ci sarebbe da controllare anche se è già presente.
            osservatori.remove(observerTimer);
    }

    public void timerNotify(){
        for(ObserverTimer osservatore : osservatori)
            osservatore.timerUpdate();
    }
}
