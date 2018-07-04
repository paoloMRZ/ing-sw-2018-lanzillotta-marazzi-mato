package it.polimi.se2018.server.events;

/**
 * Evento che nasce per collegare il model con la fakeview. Traposrta la reference della fakeview chat fino alla classe cominu
 * catore del model ed esso si occuperà di registrarlo.
 * @author Kevin Mato
 */
public class HookMessage {
    private final ViewAsObserver observer;

    public HookMessage(ViewAsObserver chat){
        this.observer=chat;
    }

    public ViewAsObserver getObserver() {
        return observer;
    }
}
