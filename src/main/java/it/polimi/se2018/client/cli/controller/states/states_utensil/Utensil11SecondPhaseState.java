package it.polimi.se2018.client.cli.controller.states.states_utensil;


import it.polimi.se2018.client.cli.controller.states.StateInterface;
import it.polimi.se2018.client.cli.game.Game;
import it.polimi.se2018.client.cli.game.utensil.UtensilCard;
import it.polimi.se2018.client.cli.print.scenes.SideColorScene;
import it.polimi.se2018.client.message.ClientMessageCreator;

import java.security.InvalidParameterException;
import java.util.ArrayList;

/**
 * La classe implementa lo stato che gestisce la seconda fase dell'utensile 11.
 *
 * @author Marazzi Paolo
 */

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

    /**
     * Costruttore della classe.
     *
     * @param cardIndex indice dell'utensile 11 nella collezione di carte utensili di questa partita.
     */

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
     * Il metodo gestisce l'input immesso dal giocatore per decidere se posizionare il dado estratto.
     * @param request input immesso dal giocatore.
     */
    private void placeDie(int request){

        if(request == 0 || request == 1){



            if (request == 1){ //Piazza dado.

                paramList.add(String.valueOf(request)); //Aggiungo la scelta fatta alla lista dei parametri da includere nel messagio.
                stepCounter ++; //Passo allo step successivo.

                sideColorScene.setText(TEXT2); //Stampo le informazioni per lo step successivo.
                sideColorScene.printScene();

            }else {//Non piazza il dado.

                paramList.add("0"); //Valore che verrà ignorato dal server.
                paramList.add(String.valueOf(request)); //Aggiungo la scelta fatta alla lista dei parametri da includere nel messagio.
                paramList.add("0"); //Valore che verrà ignorato dal server.
                paramList.add("0"); //Valore che verrà ignorato dal server.

                stepCounter = 4; //Vado allo step che invia il messaggio.
            }
        }else
            sideColorScene.printScene(); //Pulisco lo schermo.
    }

    /**
     * Il metodo gestisce l'input immesso dal giocatore per selezionare il valore del dado estratto.
     * @param value input immesso dal giocatore.
     */
    private void selectValue(int value){

        if(value >= 1 && value <= 6){

            String tmp = paramList.remove(0);
            paramList.add(String.valueOf(value)); //Aggiungo la scelta fatta alla lista dei parametri da includere nel messagio.
            paramList.add(tmp);

            stepCounter ++; //Passo allo step successivo.

            sideColorScene.setText(TEXT3); //Stampo le informazioni per lo step successivo.
            sideColorScene.printScene();


        }else
            sideColorScene.printScene();
    }

    /**
     * Il metodo gestisce l'input immesso  dall'utente per selezionare la riga.
     * @param row riga immessa dall'utente.
     */
    private void selectRow(int row){

        if(isValidRow(row)){

            paramList.add(String.valueOf(row)); //Aggiungo la scelta fatta alla lista dei parametri da includere nel messagio.

            stepCounter ++; //Passo allo step successivo.

            sideColorScene.setText(TEXT4); //Stampo le informazioni per lo step successivo.
            sideColorScene.printScene();


        }else
            sideColorScene.printScene();
    }

    /**
     * Il metodo gestisce l'input immesso  dall'utente per selezionare la colonna.
     * @param col riga immessa dall'utente.
     */
    private void selectCol(int col){

        if(isValidCol(col)){

            paramList.add(String.valueOf(col)); //Aggiungo la scelta fatta alla lista dei parametri da includere nel messagio.

            stepCounter ++; //Passo allo step successivo.

            sideColorScene.setText("");
            sideColorScene.printScene();


        }else
            sideColorScene.printScene();
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

    /**
     * Metodo che gestisce i messaggi provenienti dalla rete.
     * In questo stato vengono ignorati tutti i messaggi inviati dal server.
     *
     * @param request messaggio inviato dal server.
     */
    @Override
    public void handleNetwork(String request) {
        //Non bisogna gestire nessun messaggio proveniente dalla rete.
    }
}
