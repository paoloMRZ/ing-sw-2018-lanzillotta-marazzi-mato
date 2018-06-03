package it.polimi.se2018.server.events;

import it.polimi.se2018.server.events.tool_mex.*;

public interface ObserverForth {

    public void update(Activate mex);
    public void update(SimpleMove mex);

    public void update(MoreThanSimple mex);
    public void update(ToolCard2 mex);
    public void update(ToolCard3 mex);
    public void update(ToolCard4 mex);
    public void update(ToolCard5 mex);
    public void update(ToolCard6 mex);
    public void update(ToolCard6Bis mex);
    public void update(ToolCard7 mex);
    public void update(ToolCard8 mex);
    public void update(ToolCard9 mex);
    public void update(ToolCard10 mex);
    public void update(ToolCard11 mex);
    public void update(ToolCard11Bis mex);
    public void update(ToolCard12 mex);


    public void update(UpdateReq mex);
    public void update(HookMessage mex);
}
