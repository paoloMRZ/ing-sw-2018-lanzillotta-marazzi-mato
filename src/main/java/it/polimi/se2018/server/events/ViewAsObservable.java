package it.polimi.se2018.server.events;

import it.polimi.se2018.server.events.tool_mex.*;

public interface ViewAsObservable {

    public void register(ControllerAsObserver o);

    public void notifyObserver(Activate mex);
    public void notifyObserver(SimpleMove mex);

    public void notifyObserver(UpdateReq mex);

    public void notifyObserver(HookMessage mex);

    public void notifyObserver(Freeze mex);

    public void notifyObserver(Unfreeze mex);
}
