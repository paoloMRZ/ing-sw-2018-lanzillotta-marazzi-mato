package it.polimi.se2018.client.cli.controller.states.states_utensil;


import it.polimi.se2018.client.cli.controller.states.StateInterface;
import it.polimi.se2018.client.cli.game.Game;
import it.polimi.se2018.client.cli.game.utenil.UtensilCard;
import it.polimi.se2018.client.cli.print.scenes.SideRoundgridScene;
import it.polimi.se2018.client.message.ClientMessageCreator;

import java.security.InvalidParameterException;
import java.util.ArrayList;

public class Utensil12State implements StateInterface {

    private static final String DEFAULT_MESSAGE = "NONE";

    private static final String TEXT1 = "Seleziona round";
    private static final String TEXT2 = "Seleziona dado dal round ( 0 = posizione più alta)";
    private static final String TEXT3 = "Seleziona dado (riga)";
    private static final String TEXT4 = "Seleziona dado (colonna)";
    private static final String TEXT5 = "Seleziona cella (riga)";
    private static final String TEXT6 = "Seleziona cella (colonna)";
    private static final String TEXT7 = "Vuoi spostare un altro dado? (1 = SI | 0 = NO)";



    private Game game;
    private SideRoundgridScene sideRoundgridScene;
    private UtensilCard utensilCard;

    private int indexOfUtensil;
    private int round;

    private int stepCounter;

    private ArrayList<String> paramList;

    public Utensil12State(int cardIndex){

        if(cardIndex >= 0 && cardIndex <= 2){
            this.indexOfUtensil = cardIndex;
            this.game = Game.factoryGame();

            this.stepCounter = 0;

            this.utensilCard = game.getUtensils().get(indexOfUtensil);

            this.paramList = new ArrayList<>();

            this.sideRoundgridScene = new SideRoundgridScene(utensilCard);
            this.sideRoundgridScene.setText(TEXT1);
            this.sideRoundgridScene.printScene();
        }else
            throw new InvalidParameterException();

    }


    private boolean isRound(int round){ return round >= 0 && round < game.getRoundgrid().size() && !(game.getRoundgrid().get(round)).isEmpty(); }

    private boolean isRoundDie(int index){
        return index >= 0 && index < (game.getRoundgrid().get(round)).size();
    }

    private boolean isValidRow(int row){
        return row >= 0 && row <= 3;
    }

    private boolean isValidCol(int col){
        return col >= 0 && col <= 4;
    }

    private void addValueAndNextStep(int value){
        paramList.add(String.valueOf(value));
        stepCounter++;
    }

    private void printText(String text){
        sideRoundgridScene.setText(text);
        sideRoundgridScene.printScene();
    }

    private void selectRound(int round){

        this.round = round-1;

        if(isRound(this.round)){
            addValueAndNextStep(this.round);
            printText(TEXT2);
        }else
            sideRoundgridScene.printScene();
    }

    private void selectDieFromRound(int index){

        if(isRoundDie(index)){
            addValueAndNextStep(index);
            printText(TEXT3);
        }else
            sideRoundgridScene.printScene();
    }

    private void selectRow(int row, String nextText){

        if(isValidRow(row)){
            addValueAndNextStep(row);
            printText(nextText);
        }else
            sideRoundgridScene.printScene();
    }

    private void selectCol(int col, String nextText){

        if(isValidCol(col)){
            addValueAndNextStep(col);
            printText(nextText);
        }else
            sideRoundgridScene.printScene();
    }

    private void anotherDie(int request){

        if(request == 0 || request == 1) { //Controllo se è stata inserita una risosta valida.

            if (request == 1) { //Controllo se il giocatore vuole spostare un'altro dado
                stepCounter++; //Se si vado allo step successivo.
                printText(TEXT3); //Stampo il testo dello step successivo.
            } else
                stepCounter = 11; //In caso contrario vado allo step che invia il messaggio.

        }else
            sideRoundgridScene.printScene();
    }

    @Override
    public String handleInput(int request) {
        switch (stepCounter){

            case 0: selectRound(request);break; //Seleziona round.

            case 1: selectDieFromRound(request); break; //Seleziona dado dal round round.

            case 2: selectRow(request,TEXT4);break; //Seleziona dado (riga).

            case 3: selectCol(request, TEXT5); break; //Seleziona dado (colonna).

            case 4: selectRow(request,TEXT6); break; //Seleziona cella (riga).

            case 5: selectCol(request, TEXT7); break; //Seleziona cella (colonna).

            case 6: anotherDie(request); break; //Vuoi spostare un altro dado?

            case 7: selectRow(request,TEXT4);break; //Seleziona dado (riga).

            case 8: selectCol(request, TEXT5); break; //Seleziona dado (colonna).

            case 9: selectRow(request,TEXT6); break; //Seleziona cella (riga).

            case 10: selectCol(request, TEXT7); break; //Seleziona cella (colonna).

            default: sideRoundgridScene.printScene(); //Pulisco la scena.
        }

        if(stepCounter == 11){
            stepCounter++;
            printText("");
            return ClientMessageCreator.getUseUtensilMessage(game.getMyNickname(), String.valueOf(indexOfUtensil), String.valueOf(utensilCard.getNumber()), paramList);
        }
        else
            return DEFAULT_MESSAGE;
    }

    @Override
    public void handleNetwork(String request) {
        //In questo stato non bisogna gestire nessun messaggio di rete.
    }
}
