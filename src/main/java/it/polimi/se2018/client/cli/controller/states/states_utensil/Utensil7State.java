package it.polimi.se2018.client.cli.controller.states.states_utensil;


import it.polimi.se2018.client.cli.controller.states.StateInterface;
import it.polimi.se2018.client.cli.game.Game;
import it.polimi.se2018.client.cli.game.utensil.UtensilCard;
import it.polimi.se2018.client.cli.print.scenes.UtensilReserveScene;
import it.polimi.se2018.client.message.ClientMessageCreator;

import java.security.InvalidParameterException;
import java.util.ArrayList;

/**
 * La classe implementa lo stato che gestisce l'utensile 7.
 *
 * @author Marazzi Paolo
 */

public class Utensil7State implements StateInterface {

    private static final String DEFAULT_MESSAGE = "NONE";
    private static final String TEXT = "Procedi (0)";

    private Game game;
    private int indexOfCard;
    private UtensilCard utensilCard;
    private UtensilReserveScene utensilReserveScene;


    /**
     * Costruttore della classe.
     *
     * @param cardIndex indice dell'utensile 7 nella collezione di carte utensili di questa partita.
     */
    public Utensil7State(int cardIndex) {

        if (cardIndex >= 0 && cardIndex <= 2) {
            game = Game.factoryGame();

            this.indexOfCard = cardIndex;

            this.utensilCard = game.getUtensils().get(indexOfCard);

            utensilReserveScene = new UtensilReserveScene(utensilCard);
            utensilReserveScene.setText(TEXT);
            utensilReserveScene.printScene();
        } else
            throw new InvalidParameterException();
    }

    /**
     * Il metodo gestisce l'input immesso dall'utente.
     * In questo stato il giocatore può immettere solo il numero '0', come conferma dell'azione scelta in precedenza.
     *
     * @param request input immesso dall'utente.
     * @return eventuale messaggio da mandare al server.
     */
    @Override
    public String handleInput(int request) {

        if (request == 0) //In questo stato lo standard input.
            return ClientMessageCreator.getUseUtensilMessage(game.getMyNickname(), String.valueOf(indexOfCard), String.valueOf(utensilCard.getNumber()), new ArrayList<>());
        else {
            utensilReserveScene.printScene();
            return DEFAULT_MESSAGE;
        }
    }

    /**
     * Metodo che gestisce i messaggi provenienti dalla rete.
     * In questo stato vengono ignorati tutti i messaggi inviati dal server.
     *
     * @param request messaggio inviato dal server.
     */
    @Override
    public void handleNetwork(String request) {
        //Questo stato non deve gestire nessun messaggio di rete.
    }
}
