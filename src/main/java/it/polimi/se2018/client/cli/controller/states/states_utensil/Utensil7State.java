package it.polimi.se2018.client.cli.controller.states.states_utensil;


import it.polimi.se2018.client.cli.controller.states.StateInterface;
import it.polimi.se2018.client.cli.game.Game;
import it.polimi.se2018.client.cli.game.utenil.UtensilCard;
import it.polimi.se2018.client.cli.print.scenes.UtensilReserveScene;
import it.polimi.se2018.client.message.ClientMessageCreator;

import java.security.InvalidParameterException;
import java.util.ArrayList;

public class Utensil7State implements StateInterface {

    private static final String DEFAULT_MESSAGE = "NONE";
    private static final String TEXT = "Procedi (0)";

    private Game game;
    private int indexOfCard;
    private UtensilCard utensilCard;
    private UtensilReserveScene utensilReserveScene;

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

    @Override
    public String handleInput(int request) {

        if (request == 0) //In questo stato lo standard input.
            return ClientMessageCreator.getUseUtensilMessage(game.getMyNickname(), String.valueOf(indexOfCard), String.valueOf(utensilCard.getNumber()), new ArrayList<>());
        else {
            utensilReserveScene.printScene();
            return DEFAULT_MESSAGE;
        }
    }


    @Override
    public void handleNetwork(String request) {
        //Questo stato non deve gestire nessun messaggio di rete.
    }
}
