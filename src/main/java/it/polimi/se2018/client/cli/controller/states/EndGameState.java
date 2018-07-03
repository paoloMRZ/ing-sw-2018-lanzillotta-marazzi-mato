package it.polimi.se2018.client.cli.controller.states;

import it.polimi.se2018.client.cli.print.scenes.EndGameScene;

import java.util.List;

/**
 * La classe implementa lo stato di fine partita.
 * Ci si pone in questo stato quando si riceve un messaggio che notifica la fine della partita.
 *
 * Viene mostrato il punteggio di ogni giocatore.
 *
 * @author Marazzi Paolo
 */

public class EndGameState implements StateInterface{

    private static final String DEFAULT_MESSAGE = "NONE";

    private EndGameScene endGameScene;

    /**
     * Costruttore della classe.
     * Vengono stampati i punteggi.
     *
     * @param nicknames nicknames dei giocatori.
     * @param scores punteggi dei giocatori.
     */
    public EndGameState(List<String> nicknames, List<Integer> scores){

        this.endGameScene = new EndGameScene(nicknames,scores);
        endGameScene.printScene();
    }

    /**
     * Viene ignorato qualsiasi input da parte dell'utente.
     * @param request input dell'utente.
     * @return messaggio di default.
     */
    @Override
    public String handleInput(int request) {
        endGameScene.printScene();
        return DEFAULT_MESSAGE;
    }

    /**
     * Viene ignorato qualsiasi messaggio inviato dal server.
     * @param request messaggio inviato dal server.
     */
    @Override
    public void handleNetwork(String request) {
        //Si deve ignorare qualsiasi messaggio della rete.
    }
}
