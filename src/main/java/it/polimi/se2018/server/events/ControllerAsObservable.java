package it.polimi.se2018.server.events;

import it.polimi.se2018.server.events.responses.*;

public interface ControllerAsObservable {

    public void register(ViewAsObserver o);

    public void notifyObserver(ErrorSomethingNotGood mex);
    public void notifyObserver(SuccessColor mex);
    public void notifyObserver(SuccessValue mex);
    public void notifyObserver(SuccessActivation mex);
    public void notifyObserver(SuccessActivationFinalized mex);
    public void notifyObserver(ErrorSelection mex);
    public void notifyObserver(ErrorActivation mex);

    public void notifyObserver(DisconnectPlayer mex);

    public void notifyObserver(Freeze mex);
}
