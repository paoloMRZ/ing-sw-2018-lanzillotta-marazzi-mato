package it.polimi.se2018.client.cli.controller.states.states_utensil;

import it.polimi.se2018.client.cli.controller.states.StateInterface;
import it.polimi.se2018.client.cli.game.Game;
import it.polimi.se2018.client.cli.game.utensil.UtensilCard;
import it.polimi.se2018.client.cli.print.scenes.UtensilSideScene;
import it.polimi.se2018.client.message.ClientMessageCreator;

import java.security.InvalidParameterException;
import java.util.ArrayList;

/**
 * La classe implementa lo stato che gestisce la carta utensile 4.
 *
 * @author Marazzi Paolo
 */

public class Utensil4State implements StateInterface {

    private static final String DEFAULT_MESSAGE = "NONE";

    private static final String TEXT1 = "Dado 1 (riga)";
    private static final String TEXT2 = "Dado 1 (colonna)";

    private static final String TEXT3 = "Cella 1 (riga)";
    private static final String TEXT4 = "Cella 1 (colonna)";

    private static final String TEXT5 = "Dado 2 (colonna)";
    private static final String TEXT6 = "Dado 2 (colonna)";

    private static final String TEXT7 = "Cella 2 (riga)";
    private static final String TEXT8 = "Cella 2 (colonna)";

    private UtensilSideScene utensilSideScene;
    private int indexOfCard;
    private UtensilCard utensilCard;

    private Game game;

    private int stepCounter;

    private ArrayList<String> paramList;

    /**
     * Costruttore della classe.
     *
     * @param cardIndex indice dell'utensile 4 nella collezione di carte utensili di questa partita.
     */

    public Utensil4State(int cardIndex){

        if(cardIndex >= 0 && cardIndex <= 2) {

            this.stepCounter = 0;

            this.indexOfCard = cardIndex;

            this.game = Game.factoryGame();

            this.utensilCard = game.getUtensils().get(indexOfCard);

            this.paramList = new ArrayList<>();

            utensilSideScene = new UtensilSideScene(utensilCard);
            utensilSideScene.setText(TEXT1);
            utensilSideScene.printScene();
        }else
            throw new InvalidParameterException();
    }

    /**
     * Il metodo verifica se il valore inserito è un indice di riga valido.
     *
     * @param row indice di riga da controllare
     * @return true se l'indice è valido.
     */
    private boolean isValidRow(int row){
        return row >= 0 && row <= 3;
    }

    /**
     * Il metodo verifica se il valore inserito è un indice di colonna valido.
     *
     * @param col indice di colonna da controllare
     * @return true se l'indice è valido.
     */
    private boolean isValidCol(int col){
        return col >= 0 && col <= 4;
    }

    /**
     * Il metodo gestisce l'input immesso  dall'utente per selezionare la riga.
     * @param row riga immessa dall'utente.
     * @param textNextStep messaggio da mostrare per l'inserimeto del prossimo input se la riga immessa è valida.
     */

    private void selectionRow(int row, String textNextStep) {

        if (isValidRow(row)) { //Controllo sull'indice della riga riga
            paramList.add(String.valueOf(row)); //Se è valida la aggiungo alla lista di parametri.
            stepCounter++; //Passo allo step successivo.
            utensilSideScene.setText(textNextStep); //Stampo il testo relativo allo step successivo.
            utensilSideScene.printScene(); //Aggiorno la scena.
        }

    }

    /**
     * Il metodo gestisce l'input immesso  dall'utente per selezionare la colonna.
     * @param col colonna immessa dall'utente.
     * @param textNextStep messaggio da mostrare per l'inserimeto del prossimo input se la riga immessa è valida.
     */

    private void selectionCol(int col, String textNextStep) {

        if (isValidCol(col)) { //Controllo sull'indice della colonna.
            paramList.add(String.valueOf(col)); //Se è valida la aggiungo alla lista dei parametri.
            stepCounter++; //Passo allo step successivo.
            utensilSideScene.setText(textNextStep); //Stampo il testo relativo allo step successivo.
            utensilSideScene.printScene(); //Aggiorno la scena.
        }
    }

    /**
     * Il metodo gestisce l'input immesso dall'utente.
     * In base allo "step" in cui ci si trova l'input assume un significato differente.
     *
     * @param request input immesso dal giocatore
     * @return eventuale messaggio da mandare al server.
     */

    @Override
    public String handleInput(int request) {

        switch (stepCounter){

            case 0: selectionRow(request, TEXT2); break; //Riga dado 1.

            case 1: selectionCol(request,TEXT3); break; //Colonna dado 1.

            case 2: selectionRow(request, TEXT4); break; //Riga destinazione 1.

            case 3: selectionCol(request,TEXT5); break; //Colonna destinazione 1.

            case 4: selectionRow(request, TEXT6); break; //Riga dado 2.

            case 5: selectionCol(request,TEXT7); break; //Colonna dado 2.

            case 6: selectionRow(request, TEXT8); break; //Riga destinazione 2.

            case 7: selectionCol(request,""); break; //Colonna destinazione 2.

            default: utensilSideScene.printScene(); //Pulisco lo schermo.
        }

        if(stepCounter == 8){
            stepCounter = 0; //Mi riporto nello stato iniziale.
            return ClientMessageCreator.getUseUtensilMessage(game.getMyNickname(),String.valueOf(indexOfCard),String.valueOf(utensilCard.getNumber()),paramList);
        }
        else
            return DEFAULT_MESSAGE;
    }


    /**
     * Metodo che gestisce i messaggi provenienti dalla rete.
     * In questo stato vengono ignorati tutti i messaggi inviati dal server.
     *
     * @param request messaggio inviato dal server.
     */
    @Override
    public void handleNetwork(String request) {
        //In questo stato non si devono gestire messaggi della rete.
    }
}
