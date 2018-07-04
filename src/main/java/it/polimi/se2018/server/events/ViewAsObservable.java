package it.polimi.se2018.server.events;

import it.polimi.se2018.server.events.tool_mex.*;

/**
 * Interfaccia per patter observable.
 * @author Kevin Mato
 */
public interface ViewAsObservable {

    void register(ControllerAsObserver o);

    void notifyObserver(Activate mex);
    void notifyObserver(SimpleMove mex);

    void notifyObserver(HookMessage mex);

    void notifyObserver(Freeze mex);

    void notifyObserver(Unfreeze mex);
}
