package it.polimi.se2018.server.events;

public interface Observable{

    public void register(Observer o);

    public void unregister(Observer o);

    public void notifyObserver();
}
