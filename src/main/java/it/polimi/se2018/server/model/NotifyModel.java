package it.polimi.se2018.server.model;

import it.polimi.se2018.server.events.ModelObservable;
import it.polimi.se2018.server.events.ViewAsObserver;
import it.polimi.se2018.server.events.responses.*;

public class NotifyModel implements ModelObservable {

    private ViewAsObserver view;

    public void register(ViewAsObserver o){
        if(o!=null) this.view=o;
    }

    public void unregister(){
        this.view=null;
    }

    public void notifyObserver(SuccessSimpleMove mex){
        view.update(mex);
    }
    public void notifyObserver(ErrorSomethingNotGood mex){
        view.update(mex);
    }
    public void notifyObserver(UpdateM mex){
        view.update(mex);
    }
    public void notifyObserver(AskPlayer mex){
        view.update(mex);
    }
    public void notifyObserver(ErrorSelection mex){
        view.update(mex);
    }


}
