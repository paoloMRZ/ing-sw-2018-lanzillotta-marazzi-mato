package it.polimi.se2018.client.cli.controller.states.states_utensil;


import it.polimi.se2018.client.cli.controller.states.StateInterface;
import it.polimi.se2018.client.cli.game.Game;
import it.polimi.se2018.client.cli.game.utensil.UtensilCard;
import it.polimi.se2018.client.cli.print.scenes.SideReserverScene;
import it.polimi.se2018.client.message.ClientMessageCreator;

import java.security.InvalidParameterException;
import java.util.ArrayList;

/**
 * La classe implementa lo stato che gestisce gli utensili 8 e 9.
 *
 * @author Marazzi Paolo
 */

public class Utensil89State implements StateInterface {

    private static final String TEXT1 = "Seleziona dado (riserva)";
    private static final String TEXT2 = "Seleziona cella (riga)";
    private static final String TEXT3 = "Seleziona cella (colonna)";

    private static final String DEFAULT_MESSAGE = "NONE";

    private SideReserverScene sideReserverScene;
    private UtensilCard utensilCard;
    private Game game;
    private int indexOfUtensil;

    private int stepCounter;

    private ArrayList<String> paramList;

    /**
     * Costruttore della classe.
     *
     * @param cardIndex indice dell'utensile 8 o 9 nella collezione di carte utensili di questa partita.
     */
    public Utensil89State(int cardIndex){

        if(cardIndex >= 0 && cardIndex <= 3){
            game = Game.factoryGame();
            this.indexOfUtensil = cardIndex;

            this.stepCounter = 0;

            paramList = new ArrayList<>();

            utensilCard = game.getUtensils().get(indexOfUtensil);

            sideReserverScene = new SideReserverScene(utensilCard);
            sideReserverScene.setText(TEXT1);
            sideReserverScene.printScene();
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
     * Il metodo verifica se l'indice immesso rientra nei limiti della riserva.
     * @param index indice da controllare.
     * @return true se l'indice rientra nei limiti.
     */
    private boolean isValidReserveIndex(int index){ return index >= 0 && index < game.getReserve().size(); }

    /**
     * Il metodo gestisce l'input immesso dal giocatore per selezionare un dado dalla riserva.
     * @param index input del giocatore.
     * @return eventuale messaggio da inviare la server.
     */

    private String selectDie(int index){

        if(isValidReserveIndex(index)){ //Se l'indice non esce dai limiti della riserva lo aggiungo alla lista dei parametri e passo allo step successivo.
            paramList.add(String.valueOf(index));
            stepCounter++;

            sideReserverScene.setText(TEXT2); //Stampo le indicazioni per lo step successivo.
            sideReserverScene.printScene();
        }else
            sideReserverScene.printScene(); //Se il valore inserito non è valido pulisco lo schermo e rimango in questo step.

        return DEFAULT_MESSAGE;
    }

    /**
     * Il metodo gestisce l'input immesso dal giocatore per selezionare una riga dalla carta di gioco.
     * @param row input del giocatore.
     * @return eventuale messaggio da inviare la server.
     */
    private String selectRow(int row){

        if(isValidRow(row)){
            paramList.add(String.valueOf(row));
            stepCounter++;

            sideReserverScene.setText(TEXT3); //Stampo le indicazioni per lo step successivo.
            sideReserverScene.printScene();
        }else
            sideReserverScene.printScene(); //Se il valore inserito non è valido pulisco lo schermo e rimango in questo step.

        return DEFAULT_MESSAGE;
    }

    /**
     * Il metodo gestisce l'input immesso dal giocatore per selezionare una colonna dalla carta di gioco.
     * @param col input del giocatore.
     * @return eventuale messaggio da inviare la server.
     */

    private String selectCol(int col) {
        if (isValidCol(col)) {
            paramList.add(String.valueOf(col));
            stepCounter++;

            sideReserverScene.setText(""); //Stampo le indicazioni per lo step successivo.
            sideReserverScene.printScene();

            //Restituisco il messaggio da inviare al server.
            return ClientMessageCreator.getUseUtensilMessage(game.getMyNickname(), String.valueOf(indexOfUtensil), String.valueOf(utensilCard.getNumber()), paramList);
        } else {
            sideReserverScene.printScene(); //Se il valore inserito non è valido pulisco lo schermo e rimango in questo step.
            return DEFAULT_MESSAGE;
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
            case 0: return selectDie(request);  //Selezione del dado.

            case 1: return selectRow(request);  //Seleziono riga.

            case 2: return selectCol(request);  //Seleziono colonna.

            default:{
                sideReserverScene.printScene(); //pulisco lo schermo.
                return DEFAULT_MESSAGE;
            }
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
        //In questo stato non devo gestire nessun messaggio di rete.
    }
}
