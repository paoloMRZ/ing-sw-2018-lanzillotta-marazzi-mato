package it.polimi.se2018.server.events;

import it.polimi.se2018.server.events.responses.*;

public interface ModelObservable {

    public void register(ViewAsObserver o);

    public void unregister();

    public void notifyObserver(SuccessSimpleMove mex);
    public void notifyObserver(ErrorSomethingNotGood mex);
    public void notifyObserver(UpdateM mex);
        
    

}
