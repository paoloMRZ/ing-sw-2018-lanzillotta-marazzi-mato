package it.polimi.se2018.graphic;

import it.polimi.se2018.client.connection_handler.ConnectionHandler;
import it.polimi.se2018.client.message.ClientMessageParser;
import it.polimi.se2018.graphic.alert_box.AlertCloseButton;
import it.polimi.se2018.graphic.alert_box.AlertLoadingGame;
import it.polimi.se2018.graphic.alert_box.AlertSwitcher;
import it.polimi.se2018.graphic.alert_box.AlertValidation;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

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
    private SideCardLabel playerSide;
    private ReserveLabel reserve;
    private CardCreatorLabel privateObjective;
    private TabCardLabel tabCardLabel = new TabCardLabel();
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


    //Costanti di intestazione delle varie finestra
    private static final String SAGRADA = "Sagrada";

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {

        //Imposto il nome della finestra principale
        primaryStage.setTitle(SAGRADA);
        primaryStage.getIcons().add(new Image("iconPack/icon-sagrada.png", 10, 10, false, true));
        primaryStage.setOnCloseRequest(e -> closeWindow(primaryStage));

        //TODO: SCHERMATA LOGIN
        ImageView continueButton = shadowEffect(configureImageView("", "button-continue", ".png", 200, 90));
        ImageView startButton = shadowEffect(configureImageView("", "button-start-game", ".png", 200, 90));
        AlertSwitcher alertSwitcher = new AlertSwitcher();
        startButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> alertSwitcher.display(SAGRADA, "Scegli la modalità di connessione:", this));

        borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-image: url(back-init.jpg); -fx-background-size: contain; -fx-background-position: center; -fx-background-repeat: no-repeat;");


        HBox layout = new HBox(50);
        layout.getChildren().addAll(continueButton, startButton);
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
                        sideChoiceLabel = new SideChoiceLabel(sideSelection, connectionHandler);

                        Scene sceneChoice = new Scene(sideChoiceLabel.getSideChoise(), 1150, 900);
                        Platform.runLater(() -> {
                            alertSwitcher.closeAlert();
                            primaryStage.setMaxHeight(900);
                            primaryStage.setMaxWidth(1400);
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
                        enemiesSide = new SideEnemyLabel(nameOfPlayers, sideName);
                        configureAnchorPane(anchorGame, enemiesSide.getLabelSideEnemy(), 20d, 60d, 610d, 585d);

                    }


                    //MESSAGGIO START PER L'ASSEGNAMENTO DELLA CARTA OBBIETTIVO PRIVATA
                    if (ClientMessageParser.isStartPrivateObjectiveMessage(message.getText())) {
                        privateObjective = new CardCreatorLabel(ClientMessageParser.getInformationsFromMessage(message.getText()), null, true, null);
                        configureAnchorPane(anchorGame, privateObjective.getCardObjective(), 0d, 310d, 635d, 300d);

                    }


                    //MESSAGGIO START PER L'ASSEGNAMENTO DELLE CARTE OBBIETTIVO PUBBLICHE
                    if (ClientMessageParser.isStartPublicObjectiveMessage(message.getText())) {
                        List<String> objectivePublicInfo = ClientMessageParser.getInformationsFromMessage(message.getText());
                        publicObjective = new CardCreatorLabel(objectivePublicInfo, null, false, "/cardObjective/");
                        tabCardLabel.configureTabObjective(publicObjective.getCardObjective());
                    }

                    //MESSSAGGIO START PER L'ASSEGNAMENTO DELLE CARTE UTENSILI
                    if (ClientMessageParser.isStartUtensilMessage(message.getText())) {
                        List<String> cardUtensilsInfo = ClientMessageParser.getInformationsFromMessage(message.getText());
                        cardUtensils = new CardCreatorLabel(cardUtensilsInfo, createDictionary(cardUtensilsInfo), false, "/cardUtensils/");
                        tabCardLabel.configureTabUtensils(cardUtensils.getCardObjective());
                    }
                }


                //TODO: MESSAGGI DI TIPO UPDATE
                else if (ClientMessageParser.isUpdateTurnMessage(message.getText())) {
                        if (startGame) {

                            //TODO: SCHERMATA DI GIOCO
                            //Posiziono le carte Utensili e Pubbliche
                            configureAnchorPane(anchorGame, tabCardLabel.getGroupPane(), 40d, 330d, 880d, 210d);

                            //Posiziono la roundGrid
                            ArrayList<Double> positionGridRound = new ArrayList<>(Arrays.asList(20d, 0d, 0d, 0d));
                            roundLabel = new RoundLabel(70, 70, 930, 90, positionGridRound);
                            configureAnchorPane(anchorGame, roundLabel.getAnchorRound(), 20d, 670d, 610d, 60d);

                            //Posiziono la griglia con le informazioni sul giocatore
                            settingLabel = new SettingLabel(connectionHandler.getNickname(), "2", sideChoiceLabel.getFavours(), ClientMessageParser.getInformationsFromMessage(message.getText()).get(0));
                            nodeSetting = settingLabel.getSettingLabel();
                            configureAnchorPane(anchorGame, nodeSetting, 835d, 80d, 70d, 800d);

                            //Posiziono la carta scelta dal giocatore
                            ArrayList<Integer> sizeGridPlayer = new ArrayList<>(Arrays.asList(67, 66));
                            ArrayList<Integer> sizeSidePlayer = new ArrayList<>(Arrays.asList(340, 290));
                            ArrayList<Double> positionGridPlayer = new ArrayList<>(Arrays.asList(4d, 0d, 3d, 21d));
                            ArrayList<Double> sizeRectPlayer = new ArrayList<>(Arrays.asList(65d, 65d));
                            playerSide = new SideCardLabel(sideChoiceLabel.getNameChoice(), connectionHandler.getNickname(), sizeGridPlayer, positionGridPlayer, sizeSidePlayer, sizeRectPlayer, true, reserve);
                            configureAnchorPane(anchorGame, playerSide.getAnchorPane(), 835d, 190d, 50d, 370d);

                            //Posiziono la griglia dei pulsanti
                            buttonGameLabel = new ButtonGameLabel(connectionHandler, reserve, playerSide, cardUtensils);
                            configureAnchorPane(anchorGame, buttonGameLabel.getLabelButtonGame(), 880d, 500d, 100d, 200d);

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
                        reserve = new ReserveLabel(ClientMessageParser.getInformationsFromMessage(message.getText()), 48, 48, 48, 48);
                        if (isInitReserve) {
                            nodeReserve = reserve.getHBox();
                            configureAnchorPane(anchorGame, nodeReserve, 790d, 760d, 0d, 150d);
                            isInitReserve = false;
                        } else {
                            updateGUI(newText);
                        }
                }

                else updateGUI(newText);
            }
        });
    }






    public void updateGUI(String newValue) {

        Platform.runLater(() -> {
            //TODO: MESSAGGI DI TIPO UPDATE
            System.out.println(newValue);
            if (ClientMessageParser.isUpdateMessage(newValue)) {

                //MESSAGGIO UPDATE PER IL CAMBIO DEL TURNO
                if (ClientMessageParser.isUpdateTurnMessage(newValue)) {
                    settingLabel = new SettingLabel(connectionHandler.getNickname(), "2", sideChoiceLabel.getFavours(), ClientMessageParser.getInformationsFromMessage(newValue).get(0));
                    anchorGame.getChildren().remove(nodeSetting);
                    settingLabel.updateTurn(ClientMessageParser.getInformationsFromMessage(newValue).get(0));
                    buttonGameLabel.checkPermission(connectionHandler.getNickname(), ClientMessageParser.getInformationsFromMessage(newValue).get(0));
                    nodeSetting = settingLabel.getSettingLabel();
                    configureAnchorPane(anchorGame,nodeSetting,835d, 80d, 70d, 800d);
                }


                //MESSAGGIO UPDATE PER IL CAMBIO DEL ROUND
                if (ClientMessageParser.isUpdateRoundMessage(newValue)) {
                    List<String> roundInfo = ClientMessageParser.getInformationsFromMessage(newValue);
                    roundLabel.proceedRound(roundInfo, 120, 70);
                    anchorGame.getChildren().remove(nodeSetting);
                    settingLabel = new SettingLabel(connectionHandler.getNickname(), "2", sideChoiceLabel.getFavours(), ClientMessageParser.getInformationsFromMessage(message.getText()).get(0));
                    nodeSetting = settingLabel.getSettingLabel();
                    buttonGameLabel.checkPermission(connectionHandler.getNickname(), ClientMessageParser.getInformationsFromMessage(newValue).get(0));
                    configureAnchorPane(anchorGame, nodeSetting, 835d, 80d, 70d, 800d);
                }


                //MESSAGGIO UPDATE DELLA ROUNDGRID QUANDO EVENTUALMENTE SI CAMBIANO I SUOI DADI (UTILIZZO UTENSILE)
                if (ClientMessageParser.isUpdateRoundgridMessage(newValue)) {
                    List<List<String>> roundGridInfo = ClientMessageParser.getInformationsFromUpdateRoundgridMessage(newValue);
                    anchorGame.getChildren().remove(roundLabel);
                    ArrayList<Double> positionGridRound = new ArrayList<>(Arrays.asList(70d, 0d, 15d, 0d));
                    roundLabel = new RoundLabel(70, 70, 910, 90, positionGridRound);
                    configureAnchorPane(anchorGame, roundLabel.getAnchorRound(), 0d, 930d, 800d, 100d);

                    for (List<String> dieInfo : roundGridInfo) {
                        roundLabel.proceedRound(dieInfo, 120, 70);
                    }
                }


                //MESSAGGIO UPDATE DELLE CARTE SIDE
                if (ClientMessageParser.isUpdateSideMessage(newValue)) {
                    List<String> infoSide = ClientMessageParser.getInformationsFromMessage(newValue);
                    if(infoSide.get(0).equals(connectionHandler.getNickname())) playerSide.updateSideAfterPut(ClientMessageParser.getInformationsFromMessage(newValue), 55, 55);
                    else enemiesSide.updateSideEnemies(infoSide);
                }


                //MESSAGGIO UPDATE DELLA RISERVA
                if (ClientMessageParser.isUpdateReserveMessage(newValue)) {
                    anchorGame.getChildren().remove(nodeReserve);
                    anchorGame.getChildren().remove(buttonGameLabel);
                    nodeReserve = reserve.getHBox();
                    buttonGameLabel = new ButtonGameLabel(connectionHandler, reserve, playerSide, cardUtensils);
                    configureAnchorPane(anchorGame, buttonGameLabel.getLabelButtonGame(), 880d, 500d, 100d, 200d);
                    configureAnchorPane(anchorGame, nodeReserve, 790d, 760d, 0d, 150d);
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
                    configureAnchorPane(anchorGame,nodeSetting,835d, 80d, 70d, 800d);
                }


                //MESSAGGIO SUCCESSO RICHIESTA DI ATTIVAZIONE DI UNA CARTA UTENSILE
                if (ClientMessageParser.isSuccessActivateUtensilMessage(newValue)) {
                    buttonGameLabel.getAlertCardUtensils().launchExecutionUtensil();
                }


                //MESSAGGIO SUCCESSO ATTIVAZIONE DI UNA CARTA UTENSILE MULTIPARAMETRO
                if (ClientMessageParser.isSuccessUseUtensilMessage(newValue)) {
                    //AlertValidation.display("Successo", "La carta è stata attivata!!");
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
                configureAnchorPane(anchorGame,nodeSetting,835d, 80d, 70d, 800d);
            }


            //TODO: MESSAGGI DI TIPO ERRORE
            if (ClientMessageParser.isErrorMessage(newValue)) {
                if (ClientMessageParser.isErrorPutMessage(newValue))
                    AlertValidation.display("Errore", "Il tuo posizionamento non è valido!");
                if (ClientMessageParser.isErrorActivateUtensilMessage(newValue))
                    AlertValidation.display("Errore", "Non puoi utilizzare la carta selezionata!");
                if (ClientMessageParser.isClientDisconnectedMessage(newValue))
                    AlertValidation.display("Disconnessione", ClientMessageParser.getInformationsFromMessage(newValue) + "si è disconnesso");
            }

        });
    }


    public void setConnectionHandler(ConnectionHandler connectionHandler) {
        this.connectionHandler = connectionHandler;
    }

    public ConnectionHandler getConnectionHandler() {
        return connectionHandler;
    }

    public TextField getMessage() {
        return message;
    }

    private void closeWindow(Stage primaryStage) {
        boolean answer = AlertCloseButton.display(SAGRADA, "Vuoi davvero uscire da Sagrada?");
        if (answer) primaryStage.close();
        else primaryStage.show();
    }

    public void sendToView(String message) {
        //il messaggio in ingresso deve essere interpretato, quindi procedo con la classificazione
        this.message.setText(message);
    }
}