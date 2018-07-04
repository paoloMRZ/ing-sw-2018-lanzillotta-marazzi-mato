package it.polimi.se2018.client.cli.controller.states;

import it.polimi.se2018.client.cli.print.scenes.FatalErrorScene;

/**
 * La classe implementa lo stato in cui la Cli si pone quando viene sollevata un'eccezione che impedisce il proseguo del gioco.
 *
 * @author Marazzi Paolo
 */

public class FatalErrorState implements StateInterface {


    private static final String DEFAULT_MESSAGE = "NONE";

    /**
     * Costruttore della classe.
     * Stampa del messaggio di errore.
     * @param e eccezione che ha provocato il malfunzionamento.
     */
    public FatalErrorState(Exception e){
        FatalErrorScene fatalErrorScene = new FatalErrorScene(e);
        fatalErrorScene.printScene();
    }

    /**
     * Viene ignorato qualsiasi input da parte dell'utente.
     * @param request input dell'utente.
     * @return messaggio di default.
     */
    @Override
    public String handleInput(int request) {
        return DEFAULT_MESSAGE;
    }

    /**
     * Viene ignorato qualsiasi messaggio inviato dal server.
     * @param request messaggio inviato dal server.
     */
    @Override
    public void handleNetwork(String request) {
        //Non fa niente perchè in questo stato l'appliazione è insensibile alle notifiche del server.
    }
}
