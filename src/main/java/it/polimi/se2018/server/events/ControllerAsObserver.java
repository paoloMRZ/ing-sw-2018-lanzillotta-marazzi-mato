package it.polimi.se2018.server.events;

import it.polimi.se2018.server.events.responses.Choice;
import it.polimi.se2018.server.events.responses.PassTurn;
import it.polimi.se2018.server.events.tool_mex.*;

 public interface ControllerAsObserver {

     void update(Activate mex);
     void update(SimpleMove mex);


     void update(HookMessage mex);


     void update(PassTurn mex);

     void update(Choice mex);

     void update(Freeze mex);

     void update(Unfreeze mex);
}
