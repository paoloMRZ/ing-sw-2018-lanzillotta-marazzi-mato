package it.polimi.se2018.client.cli.controller.states;

import it.polimi.se2018.client.cli.print.scenes.EndGameScene;

import java.util.List;

public class EndGameState implements StateInterface{

    private static final String DEFAULT_MESSAGE = "NONE";

    private EndGameScene endGameScene;

    public EndGameState(List<String> nicknames, List<Integer> scores){

        this.endGameScene = new EndGameScene(nicknames,scores);
        endGameScene.printScene();
    }


    @Override
    public String handleInput(int request) {
        endGameScene.printScene();
        return DEFAULT_MESSAGE;
    }

    @Override
    public void handleNetwork(String request) {
        //Si deve ignorare qualsiasi messaggio della rete.
    }
}
