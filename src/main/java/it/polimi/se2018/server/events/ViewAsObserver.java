package it.polimi.se2018.server.events;

import it.polimi.se2018.server.events.responses.*;

public interface ViewAsObserver {

    public void update(SuccessSimpleMove mex);
    public void update(SuccessColor mex);
    public void update(SuccessValue mex);
    public void update(SuccessActivation mex);
    public void update(SuccessActivationFinalized mex);
    public void update(ErrorSelection mex);
    public void update(ErrorActivation mex);
    public void update(ErrorSomethingNotGood mex);
    public void update(UpdateM mex);

}
