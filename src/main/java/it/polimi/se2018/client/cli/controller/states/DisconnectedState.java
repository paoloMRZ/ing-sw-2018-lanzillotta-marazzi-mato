package it.polimi.se2018.client.cli.controller.states;

import static org.fusesource.jansi.Ansi.ansi;

public class DisconnectedState implements StateInterface{


    private static final String DEFAULT_MESSAGE = "NONE";
    private static final String TITLE = "Sei stato disconnesso!";


    public DisconnectedState(){

        System.out.print(ansi().eraseScreen().cursor(3,5).bold().a(TITLE).boldOff());
    }

    @Override
    public String handleInput(int request) {
        System.out.print(ansi().eraseScreen().cursor(3,5).bold().a(TITLE).boldOff());
        return DEFAULT_MESSAGE;
    }

    @Override
    public void handleNetwork(String request) {
        //Non è possibile che arrivano messaggi dalla rete.
    }
}
