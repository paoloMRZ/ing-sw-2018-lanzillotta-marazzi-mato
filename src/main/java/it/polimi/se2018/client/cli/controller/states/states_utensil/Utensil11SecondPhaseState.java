package it.polimi.se2018.client.cli.controller.states.states_utensil;


import it.polimi.se2018.client.cli.controller.states.StateInterface;
import it.polimi.se2018.client.cli.game.Game;
import it.polimi.se2018.client.cli.game.utensil.UtensilCard;
import it.polimi.se2018.client.cli.print.scenes.SideColorScene;
import it.polimi.se2018.client.message.ClientMessageCreator;

import java.security.InvalidParameterException;
import java.util.ArrayList;

public class Utensil11SecondPhaseState implements StateInterface {


    private static final String DEFAULT_MESSAGE = "NONE";

    private static final String TEXT1 = "Vuoi piazzare il dado? (1 = SI | 0 = NO)";
    private static final String TEXT2 = "Scegli il valore del dado";
    private static final String TEXT3 = "Seleziona cella (riga)";
    private static final String TEXT4 = "Seleziona cella (colonna)";

    private SideColorScene sideColorScene;
    private Game game;
    private UtensilCard utensilCard;
    private int indexOfUtensil;
    private int stepCounter;

    private ArrayList<String> paramList;

    public Utensil11SecondPhaseState (int cardIndex){

        if(cardIndex >= 0 && cardIndex <= 2){

            this.indexOfUtensil = cardIndex;
            this.stepCounter = 0;

            this.game = Game.factoryGame();

            this.paramList = new ArrayList<>();

            this.utensilCard = game.getUtensils().get(indexOfUtensil);

            this.sideColorScene = new SideColorScene(game.getSecondPhaseColor(), utensilCard);
            this.sideColorScene.setText(TEXT1);
            this.sideColorScene.printScene();

        }else
            throw new InvalidParameterException();

    }


    private boolean isValidRow(int row){
        return row >= 0 && row <= 3;
    }

    private boolean isValidCol(int col){
        return col >= 0 && col <= 4;
    }

    private void placeDie(int request){

        if(request == 0 || request == 1){

            paramList.add(String.valueOf(request)); //Aggiungo la scelta fatta alla lista dei parametri da includere nel messagio.

            if (request == 1){ //Piazza dado.
                stepCounter ++; //Passo allo step successivo.

                sideColorScene.setText(TEXT2); //Stampo le informazioni per lo step successivo.
                sideColorScene.printScene();

            }else //Non piazza il dado.
                stepCounter = 4; //Vado allo step che invia il messaggio.
        }else
            sideColorScene.printScene(); //Pulisco lo schermo.
    }

    private void selectValue(int value){

        if(value >= 1 && value <= 6){

            paramList.add(String.valueOf(value)); //Aggiungo la scelta fatta alla lista dei parametri da includere nel messagio.

            stepCounter ++; //Passo allo step successivo.

            sideColorScene.setText(TEXT3); //Stampo le informazioni per lo step successivo.
            sideColorScene.printScene();


        }else
            sideColorScene.printScene();
    }

    private void selectRow(int row){

        if(isValidRow(row)){

            paramList.add(String.valueOf(row)); //Aggiungo la scelta fatta alla lista dei parametri da includere nel messagio.

            stepCounter ++; //Passo allo step successivo.

            sideColorScene.setText(TEXT4); //Stampo le informazioni per lo step successivo.
            sideColorScene.printScene();


        }else
            sideColorScene.printScene();
    }

    private void selectCol(int col){

        if(isValidCol(col)){

            paramList.add(String.valueOf(col)); //Aggiungo la scelta fatta alla lista dei parametri da includere nel messagio.

            stepCounter ++; //Passo allo step successivo.

            sideColorScene.setText("");
            sideColorScene.printScene();


        }else
            sideColorScene.printScene();
    }

    @Override
    public String handleInput(int request) {

        switch (stepCounter){

            case 0: placeDie(request); break;

            case 1: selectValue(request); break;

            case 2: selectRow(request); break;

            case 3: selectCol(request); break;

            default: sideColorScene.printScene(); // Pulisco lo schermo.
        }

        if(stepCounter == 4)
            return ClientMessageCreator.getUseUtensilMessage(game.getMyNickname(),String.valueOf(indexOfUtensil), String.valueOf(utensilCard.getNumber())+"bis",paramList);
        else
            return DEFAULT_MESSAGE;
    }

    @Override
    public void handleNetwork(String request) {
        //Non bisogna gestire nessun messaggio proveniente dalla rete.
    }
}
