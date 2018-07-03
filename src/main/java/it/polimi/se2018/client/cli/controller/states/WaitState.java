package it.polimi.se2018.client.cli.controller.states;

import it.polimi.se2018.client.cli.print.scenes.WaitScene;
import it.polimi.se2018.client.message.ClientMessageParser;

/**
 * La classe rappresenta uno stato di attesa, cioè uno stato in cui la cli attende un messaggio dal server per poter proseguire.
 */
public class WaitState implements StateInterface{

    private static final String DEFAULT_MESSAGE = "NONE";

    private static final String NETWORK_CONNECT_MESSAGE = " si è connesso!";
    private static final String NETWORK_DISCONNECT_MESSAGE = " si è disconnesso!";

    private WaitScene waitScene;

    /**
     * Costruttore della classe.
     * Stampa a schermo del messaggio.
     * @param message messaggio da mostrare a schermo.
     */
    public WaitState(String message){
        this.waitScene = new WaitScene(message);
        waitScene.printScene();
    }

    /**
     * Viene ignorato qualsiasi input immesso dall'utente.
     * @param request input dell'utente.
     * @return messaggio di default.
     */
    @Override
    public String handleInput(int request) {
        waitScene.printScene();
        return DEFAULT_MESSAGE;
    }

    /**
     * In questo stato vengono gestiti solo i messaggi che notificano connessioni/disconnessioni di altri giocatori.
     * Tutti gli altri messaggi vengono ignorati.
     * @param request messaggio inviato dal server
     */
    @Override
    public void handleNetwork(String request) {

        if(ClientMessageParser.isNewConnectionMessage(request)){
            waitScene.addMessage(ClientMessageParser.getInformationsFromMessage(request).get(0) + NETWORK_CONNECT_MESSAGE);
            waitScene.printScene();
        }

        if(ClientMessageParser.isClientDisconnectedMessage(request)){
            waitScene.addMessage(ClientMessageParser.getInformationsFromMessage(request).get(0) + NETWORK_DISCONNECT_MESSAGE);
            waitScene.printScene();
        }

    }
}
