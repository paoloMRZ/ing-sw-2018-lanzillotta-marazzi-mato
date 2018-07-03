package it.polimi.se2018.client.cli.controller.states;


import it.polimi.se2018.client.cli.game.Game;
import it.polimi.se2018.client.cli.game.utensil.UtensilCard;
import it.polimi.se2018.client.cli.print.scenes.EnemyCardScene;
import it.polimi.se2018.client.cli.print.scenes.GameScene;
import it.polimi.se2018.client.cli.print.scenes.ShowRoundGridScene;
import it.polimi.se2018.client.message.ClientMessageCreator;
import it.polimi.se2018.client.message.ClientMessageParser;

/**
 * La clsee implemeta lo stato che gestisce gli input della rete e dell'utente nel suo turno di gioco.
 *
 * @author Marazzi Paolo
 */

public class MyTurnState  implements StateInterface{

    private static final String DEFAULT_MESSAGE = "NONE";
    private static final String NETWORK_CONNECT_MESSAGE = " si è connesso!";
    private static final String NETWORK_DISCONNECT_MESSAGE = " si è disconnesso!";
    private static final String ERROR_MESSAGE = "ERRORE: Inserisci un valore valido!";
    private static final String SUCCESS_PUT_MESSAGE = "Piazzamento avvenuto con successo!";
    private static final String ERROR_PUT_MESSAGE = "ERRORE: Piazzamento non consentito!";
    private static final String ERROR_ACTIVATE_UTENSIL_MESSAGE = "ERRORE: Non puoi utilizzare questa carta!";
    private static final String ERROR_UNAUTHORIZED_MESSAGE = "ERRORE: Hai già effettuato questa mossa!";

    private static final int EXIT_REQUEST = 1000; //Indica che il giocatore ha scelto di chiudere sagrada.
    private static final int INPUT_ERROR = 999; //Indica che è stato inserito un input non corretto.
    private static final int INPUT_INT_BACK_MENU = 888; //Indica che è stata inserita una richiesta per tornare al menù principale.

    private GameScene gameScene;
    private Game game;

    private int putDieCounter;
    private boolean puttingDie;

    private int putDieIndex;
    private int putDieRow;
    private int putDieCol;

    private boolean selectionUtensil;
    private int utensilSelected;

    /**
     * Costruttore della classe.
     * Stampa delle scena.
     */
    public MyTurnState(){
        game = Game.factoryGame();

        this.gameScene = new GameScene(game.getMyCard(), game.getDiceOnMyCard() ,game.getReserve(), game.getRoundgrid(),game.getAllObjectives(), game.getUtensils(), game.getFavours());
        gameScene.setMyTurnMenu();
        gameScene.updateTurn(game.getTurnOf());
        gameScene.printScene();

        putDieCounter = 0;
        puttingDie = false;
        selectionUtensil = false;
    }

    /**
     * Verifica se il numero corrisponde ad un utensile attivo per questa partita.
     * @param number numero da controllare.
     * @return true se il numero è valido.
     */
    private boolean isValidUtensilNumber(int number){

        for(UtensilCard card: game.getUtensils()){
            if(card.getNumber() == number)
                return true;
        }

        return false;
    }

    /**
     * Varifica se il numero identifica un dado della riserva.
     * @param index indice da controllare.
     * @return true se il valore identifica un dado
     */
    private boolean isValidReserveDieIndex(int index){
        return index >= 0 && index < game.getReserve().size();
    }

    /**
     * Verifica se il valore identifica una riga della carta di gioco.
     * @param index valore da controllare.
     * @return true se il valore è valido.
     */
    private boolean isValidRowIndex(int index){
        return index >= 0 && index <= 3;
    }

    /**
     * Verifica se il valore identifica una colonna della carta di gioco.
     * @param index valore da controllare.
     * @return true se il valore è valido.
     */
    private boolean isValidColIndex(int index){
        return index >= 0 && index <= 4;
    }

    /**
     * Il metodo gestisce l'input immesso dal giocatore per posizionare un dado sulla carta.
     * @param request input immesso dal giocatore.
     * @return step da eseguire nel "metodo principale"
     */
    private int managePutInput(int request){

        if(request == INPUT_INT_BACK_MENU)
            return INPUT_INT_BACK_MENU;

        else {
            //putDieCounter indica quale significato ha l'intero che si riceve.

            if (putDieCounter == 1 && isValidReserveDieIndex(request)) { //Verifico che l'indice passato non esca dalle dimensioni della riserva.
                this.putDieIndex = request; //Il numero che ricevo corrisponde all'indice del dado selezionato.
                return 5;
            }

            if (putDieCounter == 2 && isValidRowIndex(request)) { //Verifico che l'indice inseriro non esca dai limiti di riga.
                this.putDieRow = request; //Il numero che ricevo corrisponde alla riga in cui inserire il dado.
                return 5;
            }

            if (putDieCounter == 3 && isValidColIndex(request)) { //Verifico che l'indice inseriro non esca dai limiti di colonna.
                this.putDieCol = request; //Il numero che ricevo corrisponde alla colonna in cui inserire il dado.
                return 5;
            }
        }

        return INPUT_ERROR; //Non dovrebbe mai arrivare a questa riga.
    }


    /**
     * Il metodo gestisce l'input immesso dal giocatore per selezionare una carta utensile.
     * @param request input immesso dal giocatore.
     * @return step da eseguire nel "metodo principale"
     */
    private int manageSelectionUtensilInput(int request){

        if(request == INPUT_INT_BACK_MENU)
            return INPUT_INT_BACK_MENU;

        if(isValidUtensilNumber(request)) {
            this.utensilSelected = request;
            return 6;
        }
        else
            return INPUT_ERROR;
    }

    /**
     * Il metodo mostra schermo le carte utensili.
     */
    private void showUtensils(){
        gameScene.setShowUtensils();
        gameScene.printScene();
    }

    /**
     * Il metodo mostra schermo le carte obiettivo.
     */
    private void showObjective(){
        gameScene.setShowObjective();
        gameScene.printScene();
    }

    /**
     * Il metodo mostra schermo le carte degli avversari.
     */
    private void showEnemyCards(){
        EnemyCardScene enemyCardScene = new EnemyCardScene(game.getEnemyCards(), game.getEnemyNicknames(), game.getDiceOnEnemysCards());
        enemyCardScene.setMyTurnMenu();
        enemyCardScene.printScene();
    }

    /**
     * Il metodo mostra schermo la roundgrid completa.
     */

    private void showRoundgrid(){
        ShowRoundGridScene showRoundGridScene = new ShowRoundGridScene(game.getRoundgrid());
        showRoundGridScene.setMyTurnMenu();
        showRoundGridScene.printScene();
    }

    /**
     * Il metodo gestisce la richiesta dell'utente di annullare un'operazione e tornare al menù principale.
     */

    private void backToMainMenu(){
        puttingDie = false;
        putDieCounter = 0;

        selectionUtensil = false;

        gameScene.setMyTurnMenu();
        gameScene.printScene();
    }

    /**
     * Il metodo mostra a schermo un messaggio di errore.
     */
    private void showErrorMessage(){
        gameScene.addMessage(ERROR_MESSAGE);
        gameScene.printScene();
    }


    /**
     * Il metodo gestisce l'input immesso dall'utente.
     * I valori immessi possiedono un significato diverso in base allo step in cui ci si trova.
     * @param request input immesso dall'utente.
     * @return eventuale messaggio da inviare al server.
     */
    @Override
    public String handleInput(int request) {

        if(puttingDie) //Controllo se l'input che sto gestendo è relativo al posizionamento di un dado.
            request = managePutInput(request);

        if(selectionUtensil)
            request = manageSelectionUtensilInput(request);

        switch (request){


            case 1: showUtensils(); break; //Visualizzo le carte utensili.

            case 2: showObjective(); break; //Visualizzo le carte obiettivo.

            case 3: showEnemyCards(); break; //Visualizzo gli avversari.

            case 4: showRoundgrid(); break; //Visualizzo la roundgrid.

            case 5:{ //Getione della put.

                if(putDieCounter == 0){ //Devo mostrare il menù di scelta del dado dalla riserva.
                    puttingDie = true;
                    gameScene.setSelectDieMenu();
                    gameScene.printScene();
                }

                if(putDieCounter == 1){ //Devo mostrare il menù di scelta della riga.
                    gameScene.setSelectRowMenu();
                    gameScene.printScene();
                }

                if(putDieCounter == 2){ //Devo mostrare il menù di scelta della colonna.
                    gameScene.setSelectColMenu();
                    gameScene.printScene();
                }

                if(putDieCounter == 3){ //Devo inviare il messaggio.
                    this.putDieCounter = 0; //Resetto.
                    puttingDie = false;
                    gameScene.setMyTurnMenu();
                    gameScene.printScene();
                    return ClientMessageCreator.getPutDiceMessage(game.getMyNickname(),String.valueOf(this.putDieIndex), String.valueOf(this.putDieRow), String.valueOf(this.putDieCol));
                }

                this.putDieCounter++;

            }break;

            case 6:{ //Scelta dell'utensile.

                if(!selectionUtensil){
                    gameScene.setShowUtensils();
                    gameScene.setSelectUtensilMenu();
                    gameScene.printScene();

                    selectionUtensil = true;
                }else {

                    selectionUtensil = false;
                    return ClientMessageCreator.getActivateUtensilMessage(game.getMyNickname(), String.valueOf(game.getUtensilIndexFromNumber(utensilSelected)));
                }
            }break;

            case 7: return ClientMessageCreator.getPassTurnMessage(game.getMyNickname()); //Passa turno.

            case INPUT_INT_BACK_MENU: backToMainMenu(); break;

            case INPUT_ERROR: showErrorMessage(); break;

            case EXIT_REQUEST: return ClientMessageCreator.getDisconnectMessage(game.getMyNickname());

            default: showErrorMessage(); break;
        }

        return DEFAULT_MESSAGE;
    }


    /**
     * Il metodo gestisce i messaggi provenienti dal server.
     * Ad ogni messaggio corrisponde "una stampa" a schermo.
     * @param request messaggio inviato dal server.
     */
    @Override
    public void handleNetwork(String request) {
        if(ClientMessageParser.isClientDisconnectedMessage(request)){
            gameScene.addMessage(ClientMessageParser.getInformationsFromMessage(request).get(0) + NETWORK_DISCONNECT_MESSAGE);
            gameScene.printScene();
        }

        if(ClientMessageParser.isNewConnectionMessage(request)){
            gameScene.addMessage(ClientMessageParser.getInformationsFromMessage(request).get(0) + NETWORK_CONNECT_MESSAGE);
            gameScene.printScene();
        }

        if(ClientMessageParser.isUpdateReserveMessage(request)){
            gameScene.updateReserve(game.getReserve());
            gameScene.printScene();
        }

        if(ClientMessageParser.isUpdateRoundgridMessage(request) || ClientMessageParser.isUpdateRoundMessage(request)){
            gameScene.updateRoundGrid(game.getRoundgrid());
            gameScene.printScene();
        }

        if(ClientMessageParser.isUpdateSideMessage(request)){ //Mi deve essere passato solo il messaggio che aggiorna la MIA carta!!!!
            gameScene.updateCard(game.getDiceOnMyCard());
            gameScene.printScene();
        }

        if(ClientMessageParser.isSuccessPutMessage(request)){
            gameScene.addMessage(SUCCESS_PUT_MESSAGE);
            gameScene.printScene();
        }

        if(ClientMessageParser.isErrorPutMessage(request)){
            gameScene.addMessage(ERROR_PUT_MESSAGE);
            gameScene.printScene();
        }

        if(ClientMessageParser.isErrorActivateUtensilMessage(request)){
            gameScene.addMessage(ERROR_ACTIVATE_UTENSIL_MESSAGE);
            gameScene.printScene();
        }

        if(ClientMessageParser.isUnauthorizedPutMessage(request)){
            gameScene.addMessage(ERROR_UNAUTHORIZED_MESSAGE);
            gameScene.printScene();
        }
    }

}
