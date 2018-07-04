package it.polimi.se2018.server.events;

import it.polimi.se2018.server.events.responses.*;

 public interface ControllerAsObservable {

     void register(ViewAsObserver o);

     void notifyObserver(ErrorSomethingNotGood mex);
     void notifyObserver(SuccessColor mex);
     void notifyObserver(SuccessValue mex);
     void notifyObserver(SuccessActivation mex);
     void notifyObserver(SuccessActivationFinalized mex);
     void notifyObserver(ErrorSelection mex);
     void notifyObserver(ErrorActivation mex);
     void notifyObserver(ErrorSelectionUtensil mex);
     void notifyObserver(DisconnectPlayer mex);

     void notifyObserver(Freeze mex);

     void notifyObserver(IgnoreMex mex);
}
