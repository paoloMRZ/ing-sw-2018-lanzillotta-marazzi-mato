package it.polimi.se2018.client.cli.controller.states;

import it.polimi.se2018.client.cli.print.scenes.FatalErrorScene;

public class FatalErrorState implements StateInterface {


    private static final String DEFAULT_MESSAGE = "NONE";

    public FatalErrorState(Exception e){
        FatalErrorScene fatalErrorScene = new FatalErrorScene(e);
        fatalErrorScene.printScene();
    }

    @Override
    public String handleInput(int request) {
        return DEFAULT_MESSAGE;
    }

    @Override
    public void handleNetwork(String request) {
        //Non fa niente perchè in questo stato l'appliazione è insensibile alle notifiche del server.
    }
}
