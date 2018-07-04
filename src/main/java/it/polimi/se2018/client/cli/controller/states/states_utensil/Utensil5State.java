package it.polimi.se2018.client.cli.controller.states.states_utensil;


import it.polimi.se2018.client.cli.controller.states.StateInterface;
import it.polimi.se2018.client.cli.game.Game;
import it.polimi.se2018.client.cli.game.utensil.UtensilCard;
import it.polimi.se2018.client.cli.print.scenes.ReserveRoundgridScene;
import it.polimi.se2018.client.message.ClientMessageCreator;

import java.security.InvalidParameterException;
import java.util.ArrayList;


/**
 * La classe implementa lo stato che gestisce la carta utensile 5.
 *
 * @author Marazzi Paolo
 */

public class Utensil5State implements StateInterface {

    private static final String DEFAULT_MESSAGE = "NONE";

    private static final String TEXT1 = "Seleziona dado dalla riserva";
    private static final String TEXT2 = "Seleziona round";
    private static final String TEXT3 = "Seleziona dado dal round ( 0 = posizione più alta)";


    private int stepCounter;
    private int round;
    private int indexOfUtensil;

    private Game game;
    private UtensilCard utensilCard;

    private ReserveRoundgridScene reserveRoundgridScene;

    private ArrayList<String> paramList;

    /**
     * Costruttore della classe.
     *
     * @param indexCard indice dell'utensile 5 nella collezione di carte utensili di questa partita.
     */

    public Utensil5State(int indexCard){

        if(indexCard >= 0 && indexCard <= 2){

            game = Game.factoryGame();
            this.indexOfUtensil = indexCard;
            this.utensilCard = game.getUtensils().get(indexOfUtensil);
            this.stepCounter = 0;

            this.paramList = new ArrayList<>();

            this.reserveRoundgridScene = new ReserveRoundgridScene(utensilCard);
            reserveRoundgridScene.setText(TEXT1);
            reserveRoundgridScene.printScene();

        }else
            throw new InvalidParameterException();
    }

    /**
     * Il metodo controlla se il valore identifica un round che sulla roungrid contiene dei dadi.
     *
     * @param round round da controllare.
     * @return true se il round selezionato contiene dadi.
     */

    private boolean isRound(int round){ return round >= 0 && round < game.getRoundgrid().size() && !(game.getRoundgrid().get(round)).isEmpty(); }

    /**
     * Il metodo controlla se l'indice identifica un dado sul round identificato dalla variabile (privata) omonima.
     *
     * @param index indice da controllare,
     * @return true se l'indice identifica un dado.
     */

    private boolean isRoundDie(int index){
        return index >= 0 && index < (game.getRoundgrid().get(round)).size();
    }

    /**
     * Il metodo controlla se l'indice identifica un dado della riserva.
     *
     * @param index indice da controllare.
     * @return true se l'indice identifica un dado.
     */

    private boolean isDieReserve(int index){
        return index >= 0 && index < game.getReserve().size();
    }


    /**
     * Il metodo gestisce l'input immesso dall'utente per selezionare un dado dalla riserva.
     *
     * @param index indice di selezione del dado.
     * @return eventuale messaggio da mandare al server.
     */
    private String selectDieFromReserve(int index){

        if(isDieReserve(index)){

            paramList.add(String.valueOf(index));

            stepCounter++;

            reserveRoundgridScene.setText(TEXT2);
            reserveRoundgridScene.printScene();
        }else
            reserveRoundgridScene.printScene();

        return DEFAULT_MESSAGE;
    }


    /**
     * Il metodo gestisce l'input immesso dall'utente per selezionare un round sulla roundgrid.
     *
     * @param index indice di selezione del round.
     * @return eventuale messaggio da mandare al server.
     */
    private String selectRound(int index){

        index--;

        if(isRound(index)){

            round = index;
            paramList.add(String.valueOf(round));


            stepCounter++;

            reserveRoundgridScene.setText(TEXT3);
            reserveRoundgridScene.printScene();
        }else
            reserveRoundgridScene.printScene();

        return DEFAULT_MESSAGE;
    }


    /**
     * Il metodo gestisce l'input immesso dall'utente per selezionare un dado all'interno di un rund.
     *
     * @param index indice di selezione del dado.
     * @return eventuale messaggio da mandare al server.
     */
    private String selectDieFromRound(int index) {

        if (isRoundDie(index)) {

            paramList.add(String.valueOf(index));

            stepCounter++;

            reserveRoundgridScene.setText("");
            reserveRoundgridScene.printScene();

            return ClientMessageCreator.getUseUtensilMessage(game.getMyNickname(), String.valueOf(indexOfUtensil), String.valueOf(utensilCard.getNumber()), paramList);

        } else {
            reserveRoundgridScene.printScene();
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

            case 0: return selectDieFromReserve(request);

            case 1: return selectRound(request);

            case 2: return selectDieFromRound(request);

            default:{
                reserveRoundgridScene.printScene();
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
        //In questo stato non bisogna gestire nessun messaggio della rete.
    }
}
