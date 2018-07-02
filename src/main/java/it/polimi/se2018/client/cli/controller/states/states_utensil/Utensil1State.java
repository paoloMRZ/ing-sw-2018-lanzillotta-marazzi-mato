package it.polimi.se2018.client.cli.controller.states.states_utensil;

import it.polimi.se2018.client.cli.controller.states.StateInterface;
import it.polimi.se2018.client.cli.game.Game;
import it.polimi.se2018.client.cli.game.utenil.UtensilCard;
import it.polimi.se2018.client.cli.print.scenes.UtensilReserveScene;
import it.polimi.se2018.client.message.ClientMessageCreator;

import java.security.InvalidParameterException;
import java.util.ArrayList;

public class Utensil1State implements StateInterface {

    private static final String DEFAULT_MESSAGE = "NONE";

    private static final String TEXT1 = "Seleziona un dado dalla riserva";
    private static final String TEXT2 = "Aumenta(1) | Diminuisci(0)";

    private UtensilCard utensilCard;
    private UtensilReserveScene utensilReserveScene;
    private Game game;

    private ArrayList<String> paramList;
    private boolean dieSelected;

    private int indexOfCard;

    public Utensil1State(int cardIndex){

        if(cardIndex >= 0 && cardIndex <= 2) {

            this.indexOfCard = cardIndex;

            this.paramList = new ArrayList<>();

            this.game = Game.factoryGame();
            this.utensilCard = game.getUtensils().get(indexOfCard);
            this. dieSelected = false;

            utensilReserveScene = new UtensilReserveScene(utensilCard);
            utensilReserveScene.setText(TEXT1);
            utensilReserveScene.printScene();

        }else
            throw new InvalidParameterException();
    }


    private String dieSelection(int request){

        if(request >= 0 && request < game.getReserve().size()){ //Controllo se il valore inserito non sfora dai limiti della riserva.
            paramList.add(String.valueOf(request)); //Aggiungo l'indice alla lista dei parametri che andranno a comporre il messaggio di risposta.
            dieSelected = true;

            utensilReserveScene.setText(TEXT2);
            utensilReserveScene.printScene();
        }
        else //Se il valore inserito non è valido ristampo a schermo e mantengo questo stato.
            utensilReserveScene.printScene();

        return DEFAULT_MESSAGE;
    }

    private String upOrDown(int request){

        if(request == 0 || request == 1){ //Controllo se il parametro è accettabile.
            paramList.add(String.valueOf(request)); //Aggiungo il parametro alla lista dei parametri.

            dieSelected = false; //Mi riporto nello stato iniziale.

            //Restiruisco il messaggio da mandare al server.
            return ClientMessageCreator.getUseUtensilMessage(game.getMyNickname(), String.valueOf(indexOfCard), String.valueOf(utensilCard.getNumber()), paramList);
        }else{

            //Se il parametro inserito non è valido pulisco la schermata e mantengo questo stato.
            utensilReserveScene.printScene();
            return DEFAULT_MESSAGE;
        }
    }

    @Override
    public String handleInput(int request) {

        if (!dieSelected) //Controllo se il dado è già stato selezionato.
            return dieSelection(request);
        else
            return upOrDown(request);
    }

    @Override
    public void handleNetwork(String request) {
        //Non serve gestire nessun messaggio proveniente dalla rete.
    }
}
