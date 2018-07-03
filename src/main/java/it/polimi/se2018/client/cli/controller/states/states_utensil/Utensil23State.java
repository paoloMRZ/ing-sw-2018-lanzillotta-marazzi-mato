package it.polimi.se2018.client.cli.controller.states.states_utensil;


import it.polimi.se2018.client.cli.controller.states.StateInterface;
import it.polimi.se2018.client.cli.game.Game;
import it.polimi.se2018.client.cli.game.utensil.UtensilCard;
import it.polimi.se2018.client.cli.print.scenes.UtensilSideScene;
import it.polimi.se2018.client.message.ClientMessageCreator;

import java.security.InvalidParameterException;
import java.util.ArrayList;

/**
 * La classe implementa lo stato che gestisce gli utensili 2 e 3.
 *
 * @author Marazzi Paolo
 */

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

    /**
     * Costruttore della classe.
     *
     * @param cardIndex indice dell'utensile 2 o 3 nella collezione di carte utensili di questa partita.
     */

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

    /**
     * Il metodo verifica se il valore inserito è un indice di riga valido.
     *
     * @param request indice di riga da controllare
     * @return true se l'indice è valido.
     */
    private boolean isValidRow(int request){
        return request >= 0 && request <= 3;
    }

    /**
     * Il metodo verifica se il valore inserito è un indice di colonna valido.
     *
     * @param request indice di colonna da controllare
     * @return true se l'indice è valido.
     */
    private boolean isValidCol(int request){
        return request >= 0 && request <= 4;
    }

    /**
     * Il metodo gestisce l'input immesso dal giocatore per selezionare un dado dalla sua carta di gioco.
     *
     * @param request input immesso dall'utente.
     * @return eventuale messaggio da inviare al server.
     */
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

    /**
     * Il metodo gestisce l'input immesso dal giocatore per selezionare una cella dalla sua carta di gioco.
     *
     * @param request input immesso dall'utente.
     * @return eventuale messaggio da inviare al server.
     */
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

    /**
     * Il metodo gestisce l'input immesso dall'utente.
     * In base allo "step" in cui ci si trova l'input assume un significato differente.
     *
     * @param request input immesso dal giocatore
     * @return eventuale messaggio da mandare al server.
     */
    @Override
    public String handleInput(int request) {

        if (!dieSelected)
            return selectDie(request);
        else
            return selectCell(request);
    }

    /**
     * Metodo che gestisce i messaggi provenienti dalla rete.
     * In questo stato vengono ignorati tutti i messaggi inviati dal server.
     *
     * @param request messaggio inviato dal server.
     */
    @Override
    public void handleNetwork(String request) {
        //In questo stato non bisogna gestire nessun messaggio della rete.
    }
}
