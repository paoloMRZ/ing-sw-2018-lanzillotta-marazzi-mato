package it.polimi.se2018.server.events;

import it.polimi.se2018.server.events.responses.*;

/**
 * Interfaccia del patern observable istaurato tra la fakeview e la classe cominucatore  del model.
 * @author Kevin Mato
 */
 public interface ModelObservable {

     void register(ViewAsObserver o);

     void unregister();

     void notifyObserver(SuccessSimpleMove mex);
     void notifyObserver(ErrorSomethingNotGood mex);
     void notifyObserver(UpdateM mex);
     void notifyObserver(ErrorSelection mex);
     void notifyObserver(AskPlayer mex);
        
    

}
