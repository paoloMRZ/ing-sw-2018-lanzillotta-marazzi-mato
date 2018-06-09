package it.polimi.se2018.server.events;

public class HookMessage {
    private final ViewAsObserver observer;

    public HookMessage(ViewAsObserver chat){
        this.observer=chat;
    }

    public ViewAsObserver getObserver() {
        return observer;
    }
}
