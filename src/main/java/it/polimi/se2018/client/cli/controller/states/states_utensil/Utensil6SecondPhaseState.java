package it.polimi.se2018.client.cli.controller.states.states_utensil;

import it.polimi.se2018.client.cli.controller.states.StateInterface;
import it.polimi.se2018.client.cli.game.Game;
import it.polimi.se2018.client.cli.game.info.DieInfo;
import it.polimi.se2018.client.cli.game.utensil.UtensilCard;
import it.polimi.se2018.client.cli.print.scenes.SideDieScene;
import it.polimi.se2018.client.message.ClientMessageCreator;

import java.security.InvalidParameterException;
import java.util.ArrayList;

public class Utensil6SecondPhaseState implements StateInterface {

    private static final String DEFAULT_MESSAGE = "NONE";

    private static final String TEXT1 = "Vuoi piazzare il dado? (0 = SI | 1 = NO )";
    private static final String TEXT2 = "Seleziona cella (riga)";
    private static final String TEXT3 = "Seleziona cella (colonna)";



    private Game game;
    private UtensilCard utensilCard;
    private int indexOfUtensil;
    private int stepCounter;

    private SideDieScene sideDieScene;

    private ArrayList<String> paramList;

    public Utensil6SecondPhaseState(int cardIndex){

        if(cardIndex >= 0 && cardIndex <= 2) {
            this.indexOfUtensil = cardIndex;
            this.game = Game.factoryGame();
            this.stepCounter = 0;

            this.paramList = new ArrayList<>();

            this.utensilCard = game.getUtensils().get(indexOfUtensil);

            DieInfo newDie = new DieInfo(game.getSecondPhaseDie().getColor(), game.getSecondPhaseValue());

            this.sideDieScene = new SideDieScene(utensilCard, newDie);
            this.sideDieScene.setText(TEXT1);
            this.sideDieScene.printScene();
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
        if(request == 0 || request == 1){ //Conrollo se il valore passato è valido.

            paramList.add(String.valueOf(request)); // Aggiungo alla lista dei parametri del messaggio la scelta fatta dal giocatore.

            if(request == 0){ //Controllo se vuole piazzare il dado.

                this.stepCounter++; //Passo allo step successivo.

                sideDieScene.setText(TEXT2); //Stampo le indicazioni del prossimo step.
                sideDieScene.printScene();

            }else
                stepCounter = 3; //Passo allo step che invia il messaggio.

        }else
            sideDieScene.printScene(); //Pulisco lo schermo.
    }

    private void selectRow(int row){

        if(isValidRow(row)){ //Cotrollo se l'indice inserito è valido.

            paramList.add(String.valueOf(row)); //Aggiungo l'indice alla lista dei parametri.
            stepCounter++; //Passo allo step successivo.

            sideDieScene.setText(TEXT3); //Stampo le indicazioni dello step successivo.
            sideDieScene.printScene();
        }else
            sideDieScene.printScene(); //Pulisco lo schermo.
    }

    private void selectCol(int col){

        if(isValidCol(col)){ //Controllo se l'indice inserito è valido.

            paramList.add(String.valueOf(col)); //Aggiungo l'indice alla lista dei parametri.
            stepCounter++; //Passo allo step che invia il messaggio.

            sideDieScene.setText(""); //Non stampo nessuna indicazione.
            sideDieScene.printScene();

        }else
            sideDieScene.printScene();//Pulisco lo schermo.

    }

    @Override
    public String handleInput(int request) {

        switch(stepCounter){

            case 0: placeDie(request); break; //Vuoi posizionare il dado?

            case 1: selectRow(request); break; //Scegli riga.

            case 2: selectCol(request); break; //Scegli colonna.

            default: sideDieScene.printScene(); //pulisci shermo.
        }

        if(stepCounter == 3){
            stepCounter = 0; //Mi riporto allo step iniziale.
            return ClientMessageCreator.getUseUtensilMessage(game.getMyNickname(), String.valueOf(indexOfUtensil), String.valueOf(utensilCard.getNumber()) + "bis", paramList); //invia messaggio
        }else
            return DEFAULT_MESSAGE;
    }

    @Override
    public void handleNetwork(String request) {
        //In questo stato non deve gestire nessun messaggio proveniente dalla rete.
    }
}
