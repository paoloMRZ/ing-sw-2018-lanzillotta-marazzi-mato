package it.polimi.se2018.server.model;

import it.polimi.se2018.server.events.ObservableBack;
import it.polimi.se2018.server.events.ObserverBack;
import it.polimi.se2018.server.events.responses.*;

public class TableChat implements ObservableBack {

    private ObserverBack view;

    public void register(ObserverBack o){
        this.view=o;
    }

    public void unregister(){
        this.view=null;
    }

    public void notifyObserver(SuccessSimpleMove mex){
        view.update(mex);
    }
    public void notifyObserver(SuccessColor mex){
        view.update(mex);
    }
    public void notifyObserver(SuccessValue mex){
        view.update(mex);
    }
    public void notifyObserver(SuccessActivation mex){
        view.update(mex);
    }
    public void notifyObserver(SuccessActivationFinalized mex){
        view.update(mex);
    }
    public void notifyObserver(ErrorSelection mex){
        view.update(mex);
    }
    public void notifyObserver(ErrorActivation mex){
        view.update(mex);
    }
    public void notifyObserver(ErrorSomethingNotGood mex){
        view.update(mex);
    }
    public void notifyObserver(UpdateM mex){
        view.update(mex);
    }

}
