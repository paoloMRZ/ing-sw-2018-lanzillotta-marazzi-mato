package it.polimi.se2018.server.events;

import it.polimi.se2018.server.events.responses.Choice;
import it.polimi.se2018.server.events.responses.PassTurn;
import it.polimi.se2018.server.events.tool_mex.*;

public interface ControllerAsObserver {

    public void update(Activate mex);
    public void update(SimpleMove mex);

    public void update(UpdateReq mex);
    public void update(HookMessage mex);


    public void update(PassTurn mex);

    public void update(Choice mex);
}
