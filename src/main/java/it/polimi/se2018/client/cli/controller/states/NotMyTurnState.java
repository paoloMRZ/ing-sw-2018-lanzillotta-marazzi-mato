package it.polimi.se2018.client.cli.controller.states;


import it.polimi.se2018.client.cli.game.Game;
import it.polimi.se2018.client.cli.print.scenes.EnemyCardScene;
import it.polimi.se2018.client.cli.print.scenes.GameScene;
import it.polimi.se2018.client.cli.print.scenes.ShowRoundGridScene;
import it.polimi.se2018.client.message.ClientMessageCreator;
import it.polimi.se2018.client.message.ClientMessageParser;
/**
 * La clsee implemeta lo stato che gestisce gli input della rete e dell'utente nel turno di gioco di un avversario..
 *
 * @author Marazzi Paolo
 */
public class NotMyTurnState implements StateInterface {

    private static final String DEFAULT_MESSAGE = "NONE";
    private static final String NETWORK_CONNECT_MESSAGE = " si è connesso!";
    private static final String NETWORK_DISCONNECT_MESSAGE = " si è disconnesso!";
    private static final String ERROR_MESSAGE = "ERRORE: Inserisci un valore valido!";
    private static final int INPUT_ERROR = 999; //Indica che è stato inserito un input non corretto.
    private static final int INPUT_INT_BACK_MENU = 888; //Indica che è stata inserita una richiesta per tornare al menù principale.
    private static final int EXIT_REQUEST = 1000; //Indica che il giocatore ha scelto di chiudere sagrada.

    private Game game;

    private GameScene gameScene;

    /**
     * Costruttore della classe
     * Stampa della schermata.
     */
    public NotMyTurnState (){
        this.game = Game.factoryGame(); //Prendo il riferimento del modello del gioco (Game) contenuto nel gestore degli stati.

        this.gameScene = new GameScene(game.getMyCard(), game.getDiceOnMyCard() ,game.getReserve(), game.getRoundgrid(),game.getAllObjectives(), game.getUtensils(), game.getFavours());

        this.gameScene.updateTurn(game.getTurnOf());
        this.gameScene.setIsNotMyturnMenu();
        this.gameScene.printScene();
    }

    /**
     * Il metodo mostra schermo le carte utensili.
     */
    private void showUtensils(){
        gameScene.setShowUtensils();
        gameScene.setIsNotMyturnMenu();
        gameScene.printScene();
    }

    /**
     * Il metodo mostra schermo le carte obiettivo.
     */
    private void showObjective(){
        gameScene.setShowObjective();
        gameScene.setIsNotMyturnMenu();
        gameScene.printScene();
    }

    /**
     * Il metodo mostra schermo le carte degli avversari.
     */
    private void showEnemyCards(){
        EnemyCardScene enemyCardScene = new EnemyCardScene(game.getEnemyCards(), game.getEnemyNicknames(), game.getDiceOnEnemysCards());
        enemyCardScene.setNotMyTurnMenu();
        enemyCardScene.printScene();
    }

    /**
     * Il metodo mostra schermo la roundgrid completa.
     */
    private void showRoundgrid(){
        ShowRoundGridScene showRoundGridScene = new ShowRoundGridScene(game.getRoundgrid());
        showRoundGridScene.setNotMyTurnMenu();
        showRoundGridScene.printScene();
    }

    /**
     * Il metodo mostra a schermo un messaggio di errore.
     */
    private void showErrorMessage(){
        gameScene.addMessage(ERROR_MESSAGE);
        gameScene.printScene();
    }

    /**
     * Il metodo effettua un'azione specifica in base all'input immesso dall'utente.
     * @param request input immesso dall'utente.
     * @return eventuale messaggio da inviare al server.
     */
    @Override
    public String handleInput(int request) {
        switch (request){

            case 1: showUtensils(); break; //Visualizzo gli utensili

            case 2: showObjective(); break; //Visualizzo gli obiettivi.

            case 3: showEnemyCards(); break; //Visualizzo le carte degli avversari.

            case 4: showRoundgrid();break; //Visualizzo la roundgrid completa.

            case INPUT_INT_BACK_MENU: gameScene.printScene(); break; //Ristampo la scena principale in modo da stampare il menù principale.

            case INPUT_ERROR: showErrorMessage(); break; //Visualizzo il messagio di errore.

            case EXIT_REQUEST: return ClientMessageCreator.getDisconnectMessage(game.getMyNickname());

            default: showErrorMessage(); break; //Visualizzo il messagio di errore.
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

        if(ClientMessageParser.isUpdateRoundgridMessage(request)){
            gameScene.updateRoundGrid(game.getRoundgrid());
            gameScene.printScene();
        }

        if(ClientMessageParser.isUpdateSideMessage(request)){ //Mi deve essere passato solo il messaggio che aggiorna la MIA carta!!!!
            gameScene.updateCard(game.getDiceOnMyCard());
            gameScene.printScene();
        }
    }

}
