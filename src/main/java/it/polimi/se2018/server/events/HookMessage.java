package it.polimi.se2018.server.events;

import it.polimi.se2018.server.fake_view.FakeVChat;

public class HookMessage {
    private final ObserverBack observer;

    public HookMessage(ObserverBack chat){
        this.observer=chat;
    }

    public ObserverBack getObserver() {
        return observer;
    }
}
