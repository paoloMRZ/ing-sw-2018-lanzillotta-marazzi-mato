package it.polimi.se2018.client.graphic;

import it.polimi.se2018.client.cli.Cli;
import it.polimi.se2018.client.connection_handler.ConnectionHandler;
import it.polimi.se2018.client.connection_handler.ConnectionHandlerObserver;
import it.polimi.se2018.client.graphic.alert_box.*;
import it.polimi.se2018.client.graphic.alert_utensils.AlertCardUtensils;
import it.polimi.se2018.client.message.ClientMessageCreator;
import it.polimi.se2018.client.message.ClientMessageParser;
import it.polimi.se2018.client.graphic.adapter_gui.AdapterResolution;
import it.polimi.se2018.client.graphic.adapter_gui.FullAdapter;
import it.polimi.se2018.client.graphic.adapter_gui.MediumAdapter;
import it.polimi.se2018.client.graphic.adapter_gui.SmallAdapter;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static it.polimi.se2018.client.graphic.Utility.*;


/**
 * Classe InitWindow utilizzata per il collegamento con il server da parte del Client. Configurerà tutti i settaggi scelti dal giocatore (Tipo di Connessione, Modalità di Gioco)
 * per permettergli dio giocare una partita qualora ci fosse collegato almeno un giocatore avversario. L'interfaccia che permette al client di interagire con il gioco è
 * per una prima fase interamente gestita con la GUI del programma e, solamente in seguito a una scelta dello stesso, potrà proseguire la aprtita continuando sulla GUI oppure
 * giocando tramite la Cli di sistema.
 *
 * @author Simone Lanzillotta
 */



public class InitWindow extends Application implements ConnectionHandlerObserver{

    //Riferimenti alla schermata Parent della schermata di gioco
    private AnchorPane anchorGame;
    private Stage primaryStage;
    private String resolution;
    private AlertSwitcher alertSwitcher;

    //Riferimenti descrittivi del giocatore
    private ConnectionHandler connectionHandler;
    private String favours;

    //Elementi di controllo messagistica
    private TextField message = new TextField();
    private Boolean startGame = true;
    private Boolean isInitReserve = true;
    private Boolean isUseUtensil = false;

    //Elementi grafici della schermata game
    private AdapterResolution adapterResolution;
    private SideCardLabel playerSide;
    private ReserveLabel reserve;
    private CardCreatorLabel privateObjective;
    private TabCardLabel tabCardLabel;
    private CardCreatorLabel publicObjective;
    private CardCreatorLabel cardUtensils;
    private SideChoiceLabel sideChoiceLabel;
    private SideEnemyLabel enemiesSide;
    private RoundLabel roundLabel;
    private ButtonGameLabel buttonGameLabel;
    private SettingLabel settingLabel;

    //Elementi grafici per l'aggiornamento della schermata
    private HBox nodeReserve;
    private HBox nodeSetting;
    private VBox nodeButton;
    private AnchorPane nodeSidePlayer;
    private AnchorPane nodeRoundGame;
    private HBox nodeCardOfEnemies;
    private AlertCardUtensils alertCardUtensils;
    private ArrayList<String> costUtensilHistory;
    private List<String> nameOfEnemies;
    private List<String> sideOfEnemies;

    //Costanti di intestazione delle varie finestra
    private static final String SAGRADA = "Sagrada";
    private static final String FULLSIZE = "1920x1080 (Full HD)";
    private static final String MEDIUMSIZE = "1400x900 (Scelta consigliata)";
    private static final String SMALLSIZE = "1366x768";
    private static final String ERROR = "Error";
    private static final String ROUND = "RoundUpdate";
    private static final String TURN = "TurnUpdate";
    private static final String PUT = "PutUpdate";
    private static final String UTENSIL = "UtensilUpdate";



    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage init) {

        //TODO: SCHERMATA LOGIN
        this.primaryStage = init;

        //Configurazione della Schermata di Login
        primaryStage.setTitle(SAGRADA);
        primaryStage.getIcons().add(new Image("iconPack/icon-sagrada.png", 10, 10, false, true));
        primaryStage.setOnCloseRequest(e -> closeWindow(primaryStage, e));

        ImageView startButton = shadowEffect(configureImageView("", "button-start-game", ".png", 190, 90));
        ComboBox comboBox = setChoiceResolution();

        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-image: url(back-init.jpg); -fx-background-size: cover; -fx-background-position: center; -fx-background-repeat: no-repeat;");

        startButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if(comboBox.getValue()==null) AlertValidation.display(ERROR, "Seleziona la risuluzione\ndi gioco!");
            else {
                selectorAdapter();
                tabCardLabel = new TabCardLabel(adapterResolution);
                alertSwitcher = new AlertSwitcher();
                alertSwitcher.display(SAGRADA, "Scegli la modalità di connessione:", this);
            }
        });



        VBox layout = new VBox(50);
        layout.getChildren().addAll(startButton,comboBox);
        layout.setAlignment(Pos.CENTER);
        borderPane.setCenter(layout);


        Scene sceneLogin = new Scene(borderPane, 880,650);
        primaryStage.setScene(sceneLogin);
        primaryStage.centerOnScreen();
        primaryStage.setMaxHeight(650);
        primaryStage.setMaxWidth(880);
        primaryStage.setMinHeight(650);
        primaryStage.setMinWidth(880);
        primaryStage.show();



        //Formattazione dell'AnchorPane su cui ancorare gli elementi della schermata di gioco
        anchorGame = new AnchorPane();
        anchorGame.setStyle("-fx-background-image: url(prova-sfondo.png); -fx-background-size: contain; -fx-background-position: center; -fx-background-repeat: no-repeat;");


        //Listener per il cambio di scena e valutazione del messaggio
        message.textProperty().addListener((obs, oldText, newText) -> {
            if (!newText.equals("")) {

                //TODO: MESSAGGI DI TIPO START
                if (ClientMessageParser.isStartMessage(message.getText())) {

                    //MESSAGGIO START PER LA SCELTA DELLE CARTE SIDE
                    if (ClientMessageParser.isStartChoseSideMessage(message.getText())) {

                        //TODO: SCHERMATA SCELTA SIDE
                        List<String> sideSelection = ClientMessageParser.getInformationsFromMessage(message.getText());
                        sideChoiceLabel = new SideChoiceLabel(sideSelection, connectionHandler,adapterResolution);

                        Scene sceneChoice = new Scene(sideChoiceLabel.getSideChoise(), adapterResolution.getSideChoiceLabelSize().get(2).get(0), adapterResolution.getSideChoiceLabelSize().get(2).get(1));
                        Platform.runLater(() -> {
                            alertSwitcher.closeAlert();
                            primaryStage.setMaxHeight(adapterResolution.getSideChoiceLabelSize().get(2).get(1));
                            primaryStage.setMaxWidth(adapterResolution.getSideChoiceLabelSize().get(2).get(0));
                            primaryStage.setMinHeight(adapterResolution.getSideChoiceLabelSize().get(2).get(1));
                            primaryStage.setMinWidth(adapterResolution.getSideChoiceLabelSize().get(2).get(0));
                            primaryStage.centerOnScreen();
                            primaryStage.setScene(sceneChoice);
                        });
                    }

                    //MESSAGGIO START PER L'ASSEGNAMENTO DELLE CARTE SIDE AVVERSARIE
                    if (ClientMessageParser.isStartSideListMessage(message.getText())) {
                        List<String> sideInfo = ClientMessageParser.getInformationsFromMessage(message.getText());
                        nameOfEnemies = sideInfo.stream().filter(s -> (sideInfo.indexOf(s) % 2 == 0)).collect(Collectors.toList());
                        sideOfEnemies = sideInfo.stream().filter(s -> (sideInfo.indexOf(s) % 2 != 0)).collect(Collectors.toList());
                        sideOfEnemies.remove(nameOfEnemies.indexOf(connectionHandler.getNickname()));
                        nameOfEnemies.remove(connectionHandler.getNickname());
                        enemiesSide = new SideEnemyLabel(nameOfEnemies, sideOfEnemies, adapterResolution);
                        nodeCardOfEnemies = enemiesSide.getLabelSideEnemy();
                        adapterResolution.putSideEnemyLabel(anchorGame,nodeCardOfEnemies);
                    }


                    //MESSAGGIO START PER L'ASSEGNAMENTO DELLA CARTA OBBIETTIVO PRIVATA
                    if (ClientMessageParser.isStartPrivateObjectiveMessage(message.getText())) {
                        privateObjective = new CardCreatorLabel(ClientMessageParser.getInformationsFromMessage(message.getText()), null, true, null,adapterResolution);
                        adapterResolution.putPrivateObjectiveLabel(anchorGame, privateObjective.getCardObjective());

                    }


                    //MESSAGGIO START PER L'ASSEGNAMENTO DELLE CARTE OBBIETTIVO PUBBLICHE
                    if (ClientMessageParser.isStartPublicObjectiveMessage(message.getText())) {
                        List<String> objectivePublicInfo = ClientMessageParser.getInformationsFromMessage(message.getText());
                        publicObjective = new CardCreatorLabel(objectivePublicInfo, null, false, "/cardObjective/",adapterResolution);
                        tabCardLabel.configureTabObjective(publicObjective.getCardObjective());
                    }

                    //MESSSAGGIO START PER L'ASSEGNAMENTO DELLE CARTE UTENSILI
                    if (ClientMessageParser.isStartUtensilMessage(message.getText())) {
                        List<String> cardUtensilsInfo = ClientMessageParser.getInformationsFromMessage(message.getText());
                        cardUtensils = new CardCreatorLabel(cardUtensilsInfo, createDictionary(cardUtensilsInfo), false, "/cardUtensils/",adapterResolution);
                        tabCardLabel.configureTabUtensils(cardUtensils.getCardObjective());
                    }
                }


                //TODO: MESSAGGI DI TIPO UPDATE
                else if (ClientMessageParser.isUpdateTurnMessage(message.getText())) {
                        if (startGame) {

                            //TODO: SCHERMATA DI GIOCO
                            //Posiziono le carte Utensili e Pubbliche
                            adapterResolution.putTabPaneLabel(anchorGame, tabCardLabel.getGroupPane());

                            //Posiziono la roundGrid
                            roundLabel = new RoundLabel(adapterResolution);
                            nodeRoundGame = roundLabel.getAnchorRound();
                            adapterResolution.putRoundGridLabel(anchorGame,nodeRoundGame);

                            //Posiziono la griglia con le informazioni sul giocatore
                            settingLabel = new SettingLabel(connectionHandler.getNickname(), "2", sideChoiceLabel.getFavours(), ClientMessageParser.getInformationsFromMessage(message.getText()).get(0), adapterResolution);
                            nodeSetting = settingLabel.getSettingLabel();
                            adapterResolution.putSettingLabel(anchorGame, nodeSetting);

                            //Posiziono la carta Side del giocatore
                            playerSide = new SideCardLabel(sideChoiceLabel.getNameChoice(), connectionHandler.getNickname(), true, false, adapterResolution);
                            nodeSidePlayer = playerSide.getAnchorPane();
                            favours = sideChoiceLabel.getFavours();
                            adapterResolution.putSideLabel(anchorGame, nodeSidePlayer);

                            //Posiziono la griglia dei pulsanti
                            costUtensilHistory = new ArrayList<>(Arrays.asList("1","1","1"));
                            buttonGameLabel = new ButtonGameLabel(connectionHandler, reserve, playerSide, cardUtensils, costUtensilHistory,adapterResolution);
                            buttonGameLabel.checkPermission(connectionHandler.getNickname(),ClientMessageParser.getInformationsFromMessage(message.getText()).get(0));
                            nodeButton = buttonGameLabel.getLabelButtonGame();
                            alertCardUtensils = buttonGameLabel.getAlertCardUtensils();
                            adapterResolution.putButtonLabel(anchorGame, nodeButton);


                            Scene sceneGame = new Scene(anchorGame, 1880, 1073);
                            startGame = false;
                            Platform.runLater(() -> {
                                AlertLoadingGame.closeAlert();
                                primaryStage.centerOnScreen();
                                primaryStage.setMaxWidth(adapterResolution.getPrimaryStageSize().get(0));
                                primaryStage.setMaxHeight(adapterResolution.getPrimaryStageSize().get(1));
                                primaryStage.setMinWidth(adapterResolution.getPrimaryStageSize().get(0));
                                primaryStage.setMinHeight(adapterResolution.getPrimaryStageSize().get(1));
                                primaryStage.setScene(sceneGame);
                            });
                        } else {
                            updateGUI(newText, primaryStage);
                        }

                    }


                    //MESSAGGIO UPDATE DELLA RISERVA
                else if (ClientMessageParser.isUpdateReserveMessage(message.getText())) {
                        reserve = new ReserveLabel(ClientMessageParser.getInformationsFromMessage(message.getText()), adapterResolution);
                        if (isInitReserve) {
                            nodeReserve = reserve.getHBox();
                            adapterResolution.putReserveLabel(anchorGame, nodeReserve);
                            isInitReserve = false;
                        } else {
                            updateGUI(newText,primaryStage);
                        }
                }

                else updateGUI(newText,primaryStage);
            }
        });
    }






    private void updateGUI(String newValue, Stage primaryStage) {

        Platform.runLater(() -> {

            //MESSAGGI DI TIPO UPDATE
            if (ClientMessageParser.isUpdateMessage(newValue)) {

                //MESSAGGIO UPDATE PER IL CAMBIO DEL TURNO
                if (ClientMessageParser.isUpdateTurnMessage(newValue)) {

                    //Aggiornamento della Griglia informativa del Giocatore
                    resetSettingLabel(TURN,newValue);

                    //Abilitazione/Disabilitazione dei bottoni Azione
                    anchorGame.getChildren().remove(nodeButton);
                    buttonGameLabel.checkPermission(connectionHandler.getNickname(),ClientMessageParser.getInformationsFromMessage(newValue).get(0));
                    nodeButton = buttonGameLabel.getLabelButtonGame();
                    adapterResolution.putButtonLabel(anchorGame, nodeButton);
                }


                //MESSAGGIO UPDATE PER IL CAMBIO DEL ROUND
                if (ClientMessageParser.isUpdateRoundMessage(newValue)) {
                    List<String> roundInfo = ClientMessageParser.getInformationsFromMessage(newValue);

                    //Inserimento dei dadi residui della Riserva nella RoundGrid
                    roundLabel.proceedRound(roundInfo);

                    //Aggiornamento della Griglia dei Bottoni
                    anchorGame.getChildren().remove(nodeButton);
                    buttonGameLabel.checkPermission(connectionHandler.getNickname(), ClientMessageParser.getInformationsFromMessage(newValue).get(0));
                    nodeButton = buttonGameLabel.getLabelButtonGame();
                    adapterResolution.putButtonLabel(anchorGame, nodeButton);

                    //Aggiornamento della griglia Informazione del giocatore
                    resetSettingLabel(ROUND,newValue);
                }


                //MESSAGGIO UPDATE DELLA ROUNDGRID QUANDO EVENTUALMENTE SI CAMBIANO I SUOI DADI (UTILIZZO UTENSILE)
                if (ClientMessageParser.isUpdateRoundgridMessage(newValue)) {
                    List<List<String>> roundGridInfo = ClientMessageParser.getInformationsFromUpdateRoundgridMessage(newValue);
                    anchorGame.getChildren().remove(nodeRoundGame);
                    roundLabel = new RoundLabel(adapterResolution);
                    nodeRoundGame = roundLabel.getAnchorRound();
                    adapterResolution.putRoundGridLabel(anchorGame, nodeRoundGame);

                    for (List<String> dieInfo : roundGridInfo) {
                        roundLabel.proceedRound(dieInfo);
                    }
                }


                //MESSAGGIO UPDATE DELLE CARTE SIDE
                if (ClientMessageParser.isUpdateSideMessage(newValue)) {
                    List<String> infoSide = ClientMessageParser.getInformationsFromMessage(newValue);
                    if(infoSide.get(0).equals(connectionHandler.getNickname())) {
                        anchorGame.getChildren().remove(nodeSidePlayer);
                        playerSide = new SideCardLabel(sideChoiceLabel.getNameChoice(), connectionHandler.getNickname(), true, false,adapterResolution);
                        playerSide.updateSideAfterPut(ClientMessageParser.getInformationsFromMessage(newValue));
                        nodeSidePlayer = playerSide.getAnchorPane();
                        adapterResolution.putSideLabel(anchorGame,nodeSidePlayer);

                        if(!isUseUtensil) resetButtonLabel();
                    }
                    else {
                        anchorGame.getChildren().remove(nodeCardOfEnemies);
                        enemiesSide.updateSideEnemies(infoSide);
                        nodeCardOfEnemies = enemiesSide.getLabelSideEnemy();
                        adapterResolution.putSideEnemyLabel(anchorGame,nodeCardOfEnemies);

                    }
                }


                //MESSAGGIO UPDATE DELLA RISERVA
                if (ClientMessageParser.isUpdateReserveMessage(newValue)) {

                    //Aggiornamento del riferimento alla nuova riserva ricevuta
                    anchorGame.getChildren().remove(nodeReserve);
                    nodeReserve = reserve.getHBox();
                    adapterResolution.putReserveLabel(anchorGame, nodeReserve);

                    //Aggiornamento del riferimento del ButtonGameLabel -> in caso di utilizzo di un utensile, questa operazione va fatta solamente nel messaggio di END ACTIVATE
                    if(!isUseUtensil) resetButtonLabel();
                }
            }


            //TODO: MESSAGGI DI TIPO SUCCESSO
            if (ClientMessageParser.isSuccessMessage(newValue)) {

                //MESSAGGIO SUCCESSO PER IL PIAZZAMENTO DEI DADI
                if (ClientMessageParser.isSuccessPutMessage(newValue)) {
                    resetSettingLabel(PUT,newValue);
                    AlertValidation.display(SAGRADA, "La tua azione è andata\n a buon fine!");
                }


                //MESSAGGIO SUCCESSO RICHIESTA DI ATTIVAZIONE DI UNA CARTA UTENSILE
                if (ClientMessageParser.isSuccessActivateUtensilMessage(newValue)) {

                    //Prelevo le informazioni sulla Utensile Attivata
                    List<String> updateInfoUtensil = ClientMessageParser.getInformationsFromMessage(newValue);

                    //Aggiorno la lista storica dei costi delle Utensili dal campo 0 (indice carta Attivata) e dal campo 2 (nuovo costo Utensile)
                    costUtensilHistory.set(Integer.parseInt(updateInfoUtensil.get(0)),updateInfoUtensil.get(2));

                    //Blocco gli aggiornamenti relativi a carta Side e Riserva fino alla ricezione del messaggio di End
                    isUseUtensil = true;

                    //Lancio la schermata specifica dell'utilizzo della carta Utensile selezionata
                    alertCardUtensils.launchExecutionUtensil(false,null);
                }


                //MESSAGGIO SUCCESSO ATTIVAZIONE DI UNA CARTA UTENSILE MULTIPARAMETRO
                if (ClientMessageParser.isSuccessUseUtensilMessage(newValue)) {
                    alertCardUtensils.launchExecutionUtensil(true,newValue);
                }
            }


            //TODO: MESSAGGIO DI TIPO UTENSILE

            //MESSAGGIO SUCCESSO UTILIZZO CARTA UTENSILE
            if (ClientMessageParser.isUseUtensilEndMessage(newValue)) {

                //Aggiornamento della griglia Informazioni del giocatore
                resetSettingLabel(UTENSIL,newValue);

                //Chiusura della finestra dedicata alle carte utensili
                alertCardUtensils.closeExecutionUtensil();

                //Aggiornamento della girglia dei bottoni
                resetButtonLabel();

                //Termia azione Attivazione delle Utensili
                isUseUtensil = false;
                Platform.runLater(() -> AlertValidation.display("Successo", "La carta è stata attivata!!"));
            }


            //TODO: MESSAGGI DI TIPO ERRORE
            if (ClientMessageParser.isErrorMessage(newValue)) {
                if (ClientMessageParser.isErrorPutMessage(newValue))
                    AlertValidation.display(ERROR, "Il tuo posizionamento non è valido!");
                if (ClientMessageParser.isErrorActivateUtensilMessage(newValue))
                    AlertValidation.display(ERROR, "Non puoi utilizzare la carta selezionata!");
                if (ClientMessageParser.isClientDisconnectedMessage(newValue))
                    AlertValidation.display("Disconnessione", ClientMessageParser.getInformationsFromMessage(newValue) + "si è disconnesso");
                if (ClientMessageParser.isUnauthorizedPutMessage(newValue))
                    AlertValidation.display(ERROR, "Hai già effettutato\nquesta azione!");
                if(ClientMessageParser.isErrorUseUtensilMessage(newValue))
                    AlertValidation.display(ERROR, "Non hai inserito correttamente\ni parametri richiesti!");
            }


            //TODO: MESSAGGIO END DELLA PARTITA
            if(ClientMessageParser.isWinnerMessage(newValue)){
                AlertWinner.display(SAGRADA, "Complimenti!", primaryStage);
            }

        });
    }







    /**
     * Metodo di supporto alla fase di aggiornamento dell'elemento grafico SettingLabel. A seguito infatti di modifiche ad alcuni attributi di classe durante le fasi
     * di gioco, è necessario creare una nuova istanza dell'ogetto per aggiornare tutte le referenze. Vengono riconosciute quindi diverse fasi di aggiornamento:
     *
     *  -> Fase di aggiornamento Turnazione -> richiama updateTurn(String infoTurnOf)
     *  -> Fase di aggiornamento inizio nuovo Round -> crea un nuovo oggetto settato con i riferimenti ai segnalini Favore aggiornati
     *  -> Fase di aggiornamento Azione -> richiama updateAction()
     */

    private void resetSettingLabel(String typeUpdate, String infoUpdate){

        anchorGame.getChildren().remove(nodeSetting);
        switch (typeUpdate){
            case ROUND:
                settingLabel = new SettingLabel(connectionHandler.getNickname(), "2", favours, ClientMessageParser.getInformationsFromMessage(infoUpdate).get(0),adapterResolution);
                break;

            case PUT:
                settingLabel.updateAction();
                break;

            case TURN:
                settingLabel.updateTurn(ClientMessageParser.getInformationsFromMessage(infoUpdate).get(0));
                break;

            case UTENSIL:
                settingLabel.updateAction();
                settingLabel.updateFavours(ClientMessageParser.getInformationsFromMessage(infoUpdate).get(3));

        }

        nodeSetting = settingLabel.getSettingLabel();
        adapterResolution.putSettingLabel(anchorGame, nodeSetting);
    }





    /**
     * Metodo di supporto alla fase di aggiornamento dell'elemento grafico ButtonGameLabel. A seguito infatti di modifiche ad alcuni attributi di classe durante le fasi
     * di gioco, è necessario creare una nuova istanza dell'ogetto per aggiornare tutte le referenze.
     *
     */

    private void resetButtonLabel(){
        anchorGame.getChildren().remove(nodeButton);
        buttonGameLabel = new ButtonGameLabel(connectionHandler, reserve, playerSide, cardUtensils, costUtensilHistory, adapterResolution);
        alertCardUtensils = buttonGameLabel.getAlertCardUtensils();
        nodeButton = buttonGameLabel.getLabelButtonGame();
        adapterResolution.putButtonLabel(anchorGame, nodeButton);
    }



    /**
     * Metodo Setter che imposta il riferimento al ConnectionHandler rappresentante del giocatore
     *
     */

    public void setConnectionHandler(ConnectionHandler connectionHandler) {
        this.connectionHandler = connectionHandler;
    }



    /**
     * Metodo getter che restituisce un riferimento al ConnectionHandler rappresentante del giocatore
     *
     * @return Ruferimento all'oggetto ConnectionHandler
     */

    public ConnectionHandler getConnectionHandler() {
        return connectionHandler;
    }



    /**
     * Metodo getter che restituisce un riferimento al TextField utilizzato come listener dei messaggi provenienti dalla Lobby
     *
     * @return Ruferimento all'oggetto TextField
     */

    public TextField getMessage() {
        return message;
    }



    /**
     * Metodo utilizzato per modificare l'azione relativa al bottone "Chiude finestra". Permette la visualizzazione di un checker di riconferma o annullamento
     * dell'azione eseguita
     *
     * @param primaryStage Riferimento alla finestra che si intende chiudere
     */

    private void closeWindow(Stage primaryStage, WindowEvent e) {
        boolean answer = AlertCloseButton.display(SAGRADA, "Vuoi davvero uscire da Sagrada?");
        if (answer) {
            connectionHandler.sendToServer(ClientMessageCreator.getDisconnectMessage(connectionHandler.getNickname()));
            primaryStage.close();
        }
        else e.consume();
    }



    /**
     * Metodo utilizzato dalla classe Lobby per comunicare con l'interfaccia grafica
     *
     * @param message Riferimento al messaggio inviato dalla lobby all'interfaccia grafica
     */

    public void NetworkRequest(String message) {
        //il messaggio in ingresso deve essere interpretato, quindi procedo con la classificazione
        this.message.setText(message);
    }



    /**
     * Metodo utilizzato per configurare il Menù di scelta della risoluzione di gioco
     *
     * @return Riferimento all'elemento grafico in questione
     */

    @SuppressWarnings("unchecked")
    private ComboBox setChoiceResolution(){
        ObservableList<String> comboItems = FXCollections.observableArrayList(FULLSIZE, MEDIUMSIZE, SMALLSIZE);
        ComboBox comboBox = new ComboBox(comboItems);
        comboBox.setPromptText("Imposta Risoluzione");
        comboBox.setStyle("-fx-focus-color: black; -fx-font: 10px Verdana; -fx-font-size : 15pt; -fx-border-color: #C0C0C0; -fx-border-width: 3 3 3 3; -fx-background-color: #EFEFEF; -fx-background-insets: 0 0 -1 0, 0, 1, 2; -fx-background-radius: 3px, 3px, 2px, 1px; -fx-padding: 0.333333em 0.666667em 0.333333em 0.666667em; /* 4 8 4 8 */ -fx-text-fill: -fx-text-base-color; -fx-content-display: CENTER;");
        comboBox.setEditable(false);
        comboBox.setMaxWidth(420);
        comboBox.setMinWidth(420);
        comboBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                resolution = t1;

            }
        });
        return comboBox;
    }



    /**
     * Metodo utilizzato per impostare l'adapter adeguato alla risoluzione selezionata
     *
     */

    private void selectorAdapter(){
        switch (resolution){
            case FULLSIZE: adapterResolution = new FullAdapter(); break;
            case MEDIUMSIZE: adapterResolution = new MediumAdapter(); break;
            case SMALLSIZE: adapterResolution = new SmallAdapter(); break;
        }
    }




    /**
     * Metodo Getter per restituire la Finestra root dell'applicazione
     *
     * @return Rifeirimento al PrimaryStage
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }
}