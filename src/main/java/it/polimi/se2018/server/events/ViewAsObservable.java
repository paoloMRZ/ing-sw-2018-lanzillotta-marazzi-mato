package it.polimi.se2018.server.events;

import it.polimi.se2018.server.events.tool_mex.*;

public interface ViewAsObservable {

    public void register(ControllerAsObserver o);

    public void notifyObserver(Activate mex);
    public void notifyObserver(SimpleMove mex);

    public void notifyObserver(MoreThanSimple mex);
    public void notifyObserver(ToolCard2 mex);
    public void notifyObserver(ToolCard3 mex);
    public void notifyObserver(ToolCard4 mex);
    public void notifyObserver(ToolCard5 mex);
    public void notifyObserver(ToolCard6 mex);
    public void notifyObserver(ToolCard6Bis mex);
    public void notifyObserver(ToolCard7 mex);
    public void notifyObserver(ToolCard8 mex);
    public void notifyObserver(ToolCard9 mex);
    public void notifyObserver(ToolCard10 mex);
    public void notifyObserver(ToolCard11 mex);
    public void notifyObserver(ToolCard11Bis mex);
    public void notifyObserver(ToolCard12 mex);


    public void notifyObserver(UpdateReq mex);

    public void notifyObserver(HookMessage mex);

    public void notifyObserver(Freeze mex);

    public void notifyObserver(Unfreeze mex);
}
