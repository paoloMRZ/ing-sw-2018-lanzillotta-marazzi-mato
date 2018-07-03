package it.polimi.se2018.client.cli.controller.states.states_utensil;


import it.polimi.se2018.client.cli.controller.states.StateInterface;
import it.polimi.se2018.client.cli.game.Game;
import it.polimi.se2018.client.cli.game.utensil.UtensilCard;
import it.polimi.se2018.client.cli.print.scenes.UtensilReserveScene;
import it.polimi.se2018.client.message.ClientMessageCreator;

import java.security.InvalidParameterException;
import java.util.ArrayList;

/**
 * La classe implementa lo stato che gestisce gli utensili 6, 10 e 11.
 *
 * @author Marazzi Paolo
 */

public class Utensil61011State implements StateInterface {

    private static final String DEFAULT_MESSAGE = "NONE";

    private static final String text = "Seleziona un dado dalla riserva";

    private UtensilCard utensilCard;
    private UtensilReserveScene utensilReserveScene;
    private Game game;

    private int indexOfCard;

    /**
     * Costruttore della classe.
     *
     * @param cardIndex indice dell'utensile 6, 10 o 11 nella collezione di carte utensili di questa partita.
     */
    public Utensil61011State(int cardIndex){

        if(cardIndex >= 0 && cardIndex <= 2) {

            this.indexOfCard = cardIndex;

            this.game = Game.factoryGame();

            this.utensilCard = game.getUtensils().get(indexOfCard);

            utensilReserveScene = new UtensilReserveScene(utensilCard);
            utensilReserveScene.setText(text);
            utensilReserveScene.printScene();
        }else
            throw new InvalidParameterException();

    }


    /**
     * Il metodo gestisce l'input immesso dall'utente per selesionare un dado dalla riserva.
     * @param request input immesso dall'utente.
     * @return eventuale messaggio da inviare la server.
     */
    @Override
    public String handleInput(int request) {

        ArrayList<String> paramList = new ArrayList<>();
        paramList.add(String.valueOf(request));

        if(request >= 0 && request < game.getReserve().size()) {

            if(utensilCard.getNumber() == 6 || utensilCard.getNumber() == 11) //Se ho a che fare con una carta che prevede una seconda fase mi salvo il dado selezionato in una variabile dedicata.
                game.setSecondPhaseDie(game.getReserve().get(request));

            return ClientMessageCreator.getUseUtensilMessage(game.getMyNickname(), String.valueOf(indexOfCard), String.valueOf(utensilCard.getNumber()), paramList);
        }
        else{
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
        //In questo stato non deve gestire nessun tipo di messaggio proveniente dalla rete.
    }
}
