package it.polimi.se2018.client.cli.controller.states;

public interface StateInterface {

    String handleInput(int request);
    void handleNetwork(String request);
}
