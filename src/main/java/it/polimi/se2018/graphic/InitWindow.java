package it.polimi.se2018.graphic;

import it.polimi.se2018.client.connection_handler.ConnectionHandler;
import it.polimi.se2018.client.message.ClientMessageParser;
import it.polimi.se2018.graphic.adapterGUI.AdapterResolution;
import it.polimi.se2018.graphic.adapterGUI.FullAdapter;
import it.polimi.se2018.graphic.adapterGUI.MediumAdapter;
import it.polimi.se2018.graphic.adapterGUI.SmallAdapter;
import it.polimi.se2018.graphic.alert_box.AlertCloseButton;
import it.polimi.se2018.graphic.alert_box.AlertLoadingGame;
import it.polimi.se2018.graphic.alert_box.AlertSwitcher;
import it.polimi.se2018.graphic.alert_box.AlertValidation;
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

import static it.polimi.se2018.graphic.Utility.*;


public class InitWindow extends Application {

    //Elementi grafici e TextField per l'ascolto dei messaggi in ingresso
    private BorderPane borderPane;
    private ConnectionHandler connectionHandler;
    private TextField message = new TextField();
    private Boolean startGame = true;
    private Boolean isInitReserve = true;
    private AnchorPane anchorGame;

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

    //Elementi grafici per l'aggiornamento della scehrmata
    private HBox nodeReserve;
    private HBox nodeSetting;
    private VBox nodeButton;
    private String resolution;


    //Costanti di intestazione delle varie finestra
    private static final String SAGRADA = "Sagrada";
    private static final String FULLSIZE = "1920x1080 (Full HD)";
    private static final String MEDIUMSIZE = "1400x900 (Scelta consigliata)";
    private static final String SMALLSIZE = "1366x768";



    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {

        //Imposto il nome della finestra principale
        primaryStage.setTitle(SAGRADA);
        primaryStage.getIcons().add(new Image("iconPack/icon-sagrada.png", 10, 10, false, true));
        primaryStage.setOnCloseRequest(e -> closeWindow(primaryStage, e));

        //TODO: SCHERMATA LOGIN
        ImageView startButton = shadowEffect(configureImageView("", "button-start-game", ".png", 200, 90));
        AlertSwitcher alertSwitcher = new AlertSwitcher();
        startButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            selectorAdapter();
            tabCardLabel = new TabCardLabel(adapterResolution);
            alertSwitcher.display(SAGRADA, "Scegli la modalità di connessione:", this);
        });

        borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-image: url(back-init.jpg); -fx-background-size: contain; -fx-background-position: center; -fx-background-repeat: no-repeat;");


        VBox layout = new VBox(50);
        layout.getChildren().addAll(startButton,setChoiceResolution());
        layout.setAlignment(Pos.CENTER);
        borderPane.setCenter(layout);

        Scene sceneLogin = new Scene(borderPane, 718, 878);
        primaryStage.setScene(sceneLogin);
        primaryStage.centerOnScreen();
        primaryStage.setMaxHeight(650);
        primaryStage.setMaxWidth(878);
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

                        Scene sceneChoice = new Scene(sideChoiceLabel.getSideChoise(), 1150, 900);
                        Platform.runLater(() -> {
                            alertSwitcher.closeAlert();
                            primaryStage.setMaxHeight(adapterResolution.getPrimaryStageSize().get(1));
                            primaryStage.setMaxWidth(adapterResolution.getPrimaryStageSize().get(0));
                            primaryStage.centerOnScreen();
                            primaryStage.setScene(sceneChoice);
                        });
                    }

                    //MESSAGGIO START PER L'ASSEGNAMENTO DELLE CARTE SIDE AVVERSARIE
                    if (ClientMessageParser.isStartSideListMessage(message.getText())) {
                        List<String> sideInfo = ClientMessageParser.getInformationsFromMessage(message.getText());
                        List<String> nameOfPlayers = sideInfo.stream().filter(s -> (sideInfo.indexOf(s) % 2 == 0)).collect(Collectors.toList());
                        List<String> sideName = sideInfo.stream().filter(s -> (sideInfo.indexOf(s) % 2 != 0)).collect(Collectors.toList());
                        sideName.remove(nameOfPlayers.indexOf(connectionHandler.getNickname()));
                        nameOfPlayers.remove(connectionHandler.getNickname());
                        enemiesSide = new SideEnemyLabel(nameOfPlayers, sideName,adapterResolution);
                        adapterResolution.putSideEnemyLabel(anchorGame,enemiesSide.getLabelSideEnemy());
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
                            adapterResolution.putRoundGridLabel(anchorGame, roundLabel.getAnchorRound());

                            //Posiziono la griglia con le informazioni sul giocatore
                            settingLabel = new SettingLabel(connectionHandler.getNickname(), "2", sideChoiceLabel.getFavours(), ClientMessageParser.getInformationsFromMessage(message.getText()).get(0), adapterResolution);
                            nodeSetting = settingLabel.getSettingLabel();
                            adapterResolution.putSettingLabel(anchorGame, nodeSetting);

                            //Posiziono la carta Side del giocatore
                            playerSide = new SideCardLabel(sideChoiceLabel.getNameChoice(), connectionHandler.getNickname(), true, adapterResolution);
                            adapterResolution.putSideLabel(anchorGame, playerSide.getAnchorPane());

                            //Posiziono la griglia dei pulsanti
                            buttonGameLabel = new ButtonGameLabel(connectionHandler, reserve, playerSide, cardUtensils, adapterResolution);
                            buttonGameLabel.checkPermission(connectionHandler.getNickname(),ClientMessageParser.getInformationsFromMessage(message.getText()).get(0));
                            nodeButton = buttonGameLabel.getLabelButtonGame();
                            adapterResolution.putButtonLabel(anchorGame, nodeButton);


                            Scene sceneGame = new Scene(anchorGame, 1880, 1073);
                            startGame = false;
                            Platform.runLater(() -> {
                                AlertLoadingGame.closeAlert();
                                primaryStage.centerOnScreen();
                                primaryStage.setMaxWidth(1400);
                                primaryStage.setMaxHeight(900);
                                primaryStage.setMinWidth(1400);
                                primaryStage.setMinHeight(900);
                                primaryStage.setScene(sceneGame);
                            });
                        } else {
                            updateGUI(newText);
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
                            updateGUI(newText);
                        }
                }

                else updateGUI(newText);
            }
        });
    }






    private void updateGUI(String newValue) {

        Platform.runLater(() -> {
            //TODO: MESSAGGI DI TIPO UPDATE
            System.out.println(newValue);
            if (ClientMessageParser.isUpdateMessage(newValue)) {

                //MESSAGGIO UPDATE PER IL CAMBIO DEL TURNO
                if (ClientMessageParser.isUpdateTurnMessage(newValue)) {
                    settingLabel = new SettingLabel(connectionHandler.getNickname(), "2", sideChoiceLabel.getFavours(), ClientMessageParser.getInformationsFromMessage(newValue).get(0),adapterResolution);
                    anchorGame.getChildren().remove(nodeSetting);
                    settingLabel.updateTurn(ClientMessageParser.getInformationsFromMessage(newValue).get(0));
                    nodeSetting = settingLabel.getSettingLabel();
                    adapterResolution.putSettingLabel(anchorGame, nodeSetting);


                    anchorGame.getChildren().remove(nodeButton);
                    buttonGameLabel.checkPermission(connectionHandler.getNickname(),ClientMessageParser.getInformationsFromMessage(newValue).get(0));
                    nodeButton = buttonGameLabel.getLabelButtonGame();
                    adapterResolution.putButtonLabel(anchorGame, nodeButton);
                }


                //MESSAGGIO UPDATE PER IL CAMBIO DEL ROUND
                if (ClientMessageParser.isUpdateRoundMessage(newValue)) {
                    List<String> roundInfo = ClientMessageParser.getInformationsFromMessage(newValue);
                    roundLabel.proceedRound(roundInfo);
                    settingLabel = new SettingLabel(connectionHandler.getNickname(), "2", sideChoiceLabel.getFavours(), ClientMessageParser.getInformationsFromMessage(newValue).get(0),adapterResolution);
                    anchorGame.getChildren().remove(nodeSetting);
                    anchorGame.getChildren().remove(nodeButton);


                    buttonGameLabel.checkPermission(connectionHandler.getNickname(), ClientMessageParser.getInformationsFromMessage(newValue).get(0));
                    nodeSetting = settingLabel.getSettingLabel();
                    nodeButton = buttonGameLabel.getLabelButtonGame();
                    adapterResolution.putSettingLabel(anchorGame, nodeSetting);
                    adapterResolution.putButtonLabel(anchorGame, nodeButton);
                }


                //MESSAGGIO UPDATE DELLA ROUNDGRID QUANDO EVENTUALMENTE SI CAMBIANO I SUOI DADI (UTILIZZO UTENSILE)
                if (ClientMessageParser.isUpdateRoundgridMessage(newValue)) {
                    List<List<String>> roundGridInfo = ClientMessageParser.getInformationsFromUpdateRoundgridMessage(newValue);
                    anchorGame.getChildren().remove(roundLabel);
                    roundLabel = new RoundLabel(adapterResolution);
                    adapterResolution.putRoundGridLabel(anchorGame, roundLabel.getAnchorRound());

                    for (List<String> dieInfo : roundGridInfo) {
                        roundLabel.proceedRound(dieInfo);
                    }
                }


                //MESSAGGIO UPDATE DELLE CARTE SIDE
                if (ClientMessageParser.isUpdateSideMessage(newValue)) {
                    List<String> infoSide = ClientMessageParser.getInformationsFromMessage(newValue);
                    if(infoSide.get(0).equals(connectionHandler.getNickname())) playerSide.updateSideAfterPut(ClientMessageParser.getInformationsFromMessage(newValue));
                    else enemiesSide.updateSideEnemies(infoSide);
                }


                //MESSAGGIO UPDATE DELLA RISERVA
                if (ClientMessageParser.isUpdateReserveMessage(newValue)) {
                    anchorGame.getChildren().remove(nodeReserve);
                    anchorGame.getChildren().remove(nodeButton);
                    nodeReserve = reserve.getHBox();
                    buttonGameLabel = new ButtonGameLabel(connectionHandler, reserve, playerSide, cardUtensils,adapterResolution);

                    nodeButton = buttonGameLabel.getLabelButtonGame();
                    adapterResolution.putButtonLabel(anchorGame, nodeButton);
                    adapterResolution.putReserveLabel(anchorGame, nodeReserve);
                }
            }


            //TODO: MESSAGGI DI TIPO SUCCESSO
            if (ClientMessageParser.isSuccessMessage(newValue)) {

                //MESSAGGIO SUCCESSO PER IL PIAZZAMENTO DEI DADI
                if (ClientMessageParser.isSuccessPutMessage(newValue)) {
                    AlertValidation.display(SAGRADA, "La tua azione è andata\n a buon fine!");
                    anchorGame.getChildren().remove(nodeSetting);
                    settingLabel.updateAction();
                    nodeSetting = settingLabel.getSettingLabel();
                    adapterResolution.putSettingLabel(anchorGame, nodeSetting);
                }


                //MESSAGGIO SUCCESSO RICHIESTA DI ATTIVAZIONE DI UNA CARTA UTENSILE
                if (ClientMessageParser.isSuccessActivateUtensilMessage(newValue)) {
                    buttonGameLabel.getAlertCardUtensils().launchExecutionUtensil();
                }


                //MESSAGGIO SUCCESSO ATTIVAZIONE DI UNA CARTA UTENSILE MULTIPARAMETRO
                if (ClientMessageParser.isSuccessUseUtensilMessage(newValue)) {

                }
            }


            //TODO: MESSAGGIO DI TIPO UTENSILE

            //MESSAGGIO SUCCESSO UTILIZZO CARTA UTENSILE
            if (ClientMessageParser.isUseUtensilEndMessage(newValue)) {

                List<String> updateInfoUtensil = ClientMessageParser.getInformationsFromMessage(newValue);
                anchorGame.getChildren().remove(nodeSetting);
                settingLabel.updateFavours(updateInfoUtensil.get(1));
                settingLabel.updateAction();
                nodeSetting = settingLabel.getSettingLabel();
                buttonGameLabel.getAlertCardUtensils().closeExecutionUtensil();
                AlertValidation.display("Successo", "La carta è stata attivata!!");
                adapterResolution.putSettingLabel(anchorGame, nodeSetting);

            }


            //TODO: MESSAGGI DI TIPO ERRORE
            if (ClientMessageParser.isErrorMessage(newValue)) {
                if (ClientMessageParser.isErrorPutMessage(newValue))
                    AlertValidation.display("Errore", "Il tuo posizionamento non è valido!");
                if (ClientMessageParser.isErrorActivateUtensilMessage(newValue))
                    AlertValidation.display("Errore", "Non puoi utilizzare la carta selezionata!");
                if (ClientMessageParser.isClientDisconnectedMessage(newValue))
                    AlertValidation.display("Disconnessione", ClientMessageParser.getInformationsFromMessage(newValue) + "si è disconnesso");
                if (ClientMessageParser.isUnauthorizedPutMessage(newValue))
                    AlertValidation.display("Errore", "Hai già effettutato\nquesta azione!");
            }

        });
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
        if (answer) primaryStage.close();
        else e.consume();
    }



    /**
     * Metodo utilizzato dalla classe Lobby per comunicare con l'interfaccia grafica
     *
     * @param message Riferimento al messaggio inviato dalla lobby all'interfaccia grafica
     */

    public void sendToView(String message) {
        //il messaggio in ingresso deve essere interpretato, quindi procedo con la classificazione
        this.message.setText(message);
    }



    private ComboBox setChoiceResolution(){
        ObservableList<String> comboItems = FXCollections.observableArrayList("1920x1080 (Full HD)", "1400x900 (Scelta consigliata)", "1366x768");
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

    private void selectorAdapter(){
        switch (resolution){
            case FULLSIZE: adapterResolution = new FullAdapter(); break;
            case MEDIUMSIZE: adapterResolution = new MediumAdapter(); break;
            case SMALLSIZE: adapterResolution = new SmallAdapter(); break;
        }
    }
}