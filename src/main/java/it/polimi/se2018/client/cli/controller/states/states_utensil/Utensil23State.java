package it.polimi.se2018.client.cli.controller.states.states_utensil;


import it.polimi.se2018.client.cli.controller.states.StateInterface;
import it.polimi.se2018.client.cli.game.Game;
import it.polimi.se2018.client.cli.game.utenil.UtensilCard;
import it.polimi.se2018.client.cli.print.scenes.UtensilSideScene;
import it.polimi.se2018.client.message.ClientMessageCreator;

import java.security.InvalidParameterException;
import java.util.ArrayList;

public class Utensil23State implements StateInterface {

    private static final String DEFAULT_MESSAGE = "NONE";

    private static final String TEXT1 = "Seleziona dado (riga)";
    private static final String TEXT2 = "Seleziona dado (colonna)";

    private static final String TEXT3 = "Seleziona destinazione (riga)";
    private static final String TEXT4 = "Seleziona destinazione (colonna)";

    private UtensilSideScene utensilSideScene;
    private UtensilCard utensilCard;

    private int indexOfCard;
    private Game game;

    private boolean dieSelected;
    private boolean rowSelected;

    private ArrayList<String> paramList;

    public Utensil23State(int cardIndex){

        if(cardIndex >= 0 && cardIndex <= 2){

            this.indexOfCard = cardIndex;

            this.game = Game.factoryGame();
            this.utensilCard = game.getUtensils().get(indexOfCard);


            this.paramList = new ArrayList<>();

            this.dieSelected = false;
            this.rowSelected = false;

            this.utensilSideScene = new UtensilSideScene(utensilCard);
            this.utensilSideScene.setText(TEXT1);
            this.utensilSideScene.printScene();

        }else
            throw new InvalidParameterException();
    }

    private boolean isValidRow(int request){
        return request >= 0 && request <= 3;
    }

    private boolean isValidCol(int request){
        return request >= 0 && request <= 4;
    }

    private String selectDie(int request){

        //Selezione del dado da spostare.

        if(!rowSelected){

            if(isValidRow(request)){
                paramList.add(String.valueOf(request));
                rowSelected = true;
                utensilSideScene.setText(TEXT2);
                utensilSideScene.printScene();

            }else
                utensilSideScene.printScene();

        }else {

            if(isValidCol(request)){
                paramList.add(String.valueOf(request));
                rowSelected = false;
                dieSelected = true;
                utensilSideScene.setText(TEXT3);
                utensilSideScene.printScene();

            }else
                utensilSideScene.printScene();
        }

        return DEFAULT_MESSAGE;
    }

    private String selectCell(int request){

        //Selezione del punto d'arrivo.

        if(!rowSelected){

            if(isValidRow(request)){
                paramList.add(String.valueOf(request));
                rowSelected = true;
                utensilSideScene.setText(TEXT4);
                utensilSideScene.printScene();

            }else
                utensilSideScene.printScene();

        }else {

            if(isValidCol(request)){
                paramList.add(String.valueOf(request));

                return ClientMessageCreator.getUseUtensilMessage(game.getMyNickname(), String.valueOf(indexOfCard), String.valueOf(utensilCard.getNumber()), paramList);

            }else
                utensilSideScene.printScene();
        }

        return DEFAULT_MESSAGE;
    }

    @Override
    public String handleInput(int request) {

        if (!dieSelected)
            return selectDie(request);
        else
            return selectCell(request);
    }

    @Override
    public void handleNetwork(String request) {
        //In questo stato non bisogna gestire nessun messaggio della rete.
    }
}
