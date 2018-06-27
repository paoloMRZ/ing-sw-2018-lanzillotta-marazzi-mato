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
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static it.polimi.se2018.graphic.Utility.*;


public class InitWindow extends Application {

    private StackPane landscape;
    private BorderPane borderPane;
    private ConnectionHandler connectionHandler;
    private String nickName;
    private TextField message = new TextField();
    private Boolean startGame = true;

    //Elementi della schermata game
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



    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage){

        //Imposto il nome della finestra principale
        primaryStage.setTitle("Sagrada");
        primaryStage.getIcons().add(new Image("iconPack/icon-sagrada.png", 10, 10, false, true));
        primaryStage.setOnCloseRequest(e -> closeWindow(primaryStage));

        //TODO: SCHERMATA LOGIN
        BackgroundImage init = new BackgroundImage(new Image("back-init.jpg", 878, 718, false, true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        ImageView continueButton = shadowEffect(configureImageView("","button-continue", ".png", 200, 90));
        ImageView startButton = shadowEffect(configureImageView("" ,"button-start-game", ".png", 200, 90));
        AlertSwitcher alertSwitcher = new AlertSwitcher();
        startButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> alertSwitcher.display("Sagrada", "Scegli la modalità di connessione:", this));

        borderPane = new BorderPane();
        borderPane.setBackground(new Background(init));

        HBox layout = new HBox(50);
        layout.getChildren().addAll(continueButton, startButton);
        layout.setAlignment(Pos.CENTER);
        borderPane.setCenter(layout);
        landscape = new StackPane(borderPane);

        Scene sceneLogin = new Scene(landscape,718,878);
        primaryStage.setScene(sceneLogin);
        primaryStage.centerOnScreen();
        primaryStage.setMaxHeight(718);
        primaryStage.setMaxWidth(878);
        primaryStage.show();



        //TODO: SCHERMATA DI GIOCO

        //AnchorPane su cui ancorare gli elementi della schermata di gioco
        AnchorPane anchorGame = new AnchorPane();

        ImageView imageView = new ImageView("prova-sfondo.png");
        imageView.setFitWidth(Screen.getPrimary().getVisualBounds().getWidth());
        imageView.setFitHeight(Screen.getPrimary().getVisualBounds().getHeight());
        anchorGame.setStyle("-fx-background-image: url(prova-sfondo.png); -fx-background-size: contain; -fx-background-position: center; -fx-background-repeat: no-repeat;");

/*
        //Aggiungo gli elementi della schermata -> SOLO PROVA POSIZIONE NELLA SCHERMATA -> TESTING

        //ROUNDGRID -> PROVA SENZA MESSAGGIO PER POSIZIONE
        ArrayList<Double> positionGridRoundEX = new ArrayList<>(Arrays.asList(20d, 0d, 0d, 0d));
        RoundLabel roundEX = new RoundLabel(70, 70,930,90, positionGridRoundEX);
        AnchorPane anchorPaneRound = roundEX.getAnchorRound();
        configureAnchorPane(anchorGame,anchorPaneRound, 20d, 670d, 610d, 60d);
        ArrayList<String> diceRoundInfo = new ArrayList<>();
        diceRoundInfo.add("blue2");
        diceRoundInfo.add("red2");
        diceRoundInfo.add("red2");

        for(int i=0; i<10;i++){
            roundEX.proceedRound(diceRoundInfo,62,60);
        }


        //CARTA OBBIETTIVO PRIVATA -> PROVA SENZA MESSAGGIO PER POSIZIONE
        ArrayList<String> cardInfoPrivate = new ArrayList<>();
        cardInfoPrivate.add("sfumature-gialle-privata");
        CardCreatorLabel objectivePrivate = new CardCreatorLabel(cardInfoPrivate, null, true, null);
        AnchorPane anchorPaneObjective = objectivePrivate.getCardObjective();
        anchorPaneObjective.setMaxSize(240,300);
        anchorPaneObjective.setMinSize(240,300);
        configureAnchorPane(anchorGame, anchorPaneObjective, 0d, 310d, 635d, 300d);


        //CARTE OBBIETTIVO PUBBLICHE E UTENSILI-> PROVA SENZA MESSAGGIO PER POSIZIONE
        ArrayList<String> cardInfoPubblic = new ArrayList<>(Arrays.asList("sfumature-scure","sfumature-medie","sfumature-diverse"));
        ArrayList<String> cardInfoPubblic2 = new ArrayList<>(Arrays.asList("cardUtensils/alesatore-per-la-laminda-di-rame","cardUtensils/diluente-per-pasta-salda","cardUtensils/lathekin"));
        ArrayList<String> cardInfoPubblic3 = new ArrayList<>(Arrays.asList("cardUtensils/martelletto","cardUtensils/pennello-per-eglomise","cardUtensils/pennello-per-pasta-salda"));
        ArrayList<String> cardInfoPubblic4 = new ArrayList<>(Arrays.asList("cardUtensils/pinza-sgrossatrice","cardUtensils/riga-in-sughero","cardUtensils/tenaglia-a-rotelle"));
        ArrayList<String> cardInfoPubblic5 = new ArrayList<>(Arrays.asList("lathekin","taglierina-manuale","tampone-diamantato"));


        CardCreatorLabel objectivePubblic = new CardCreatorLabel(cardInfoPubblic, null,false, "/cardObjective/");
        //CardCreatorLabel utensilsPubblic = new CardCreatorLabel(cardInfoPubblic2, false, "cardUtensils");
        //CardCreatorLabel utensilsPubblic = new CardCreatorLabel(cardInfoPubblic3, false, "cardUtensils");
        //CardCreatorLabel utensilsPubblic = new CardCreatorLabel(cardInfoPubblic4, false, "cardUtensils");
        CardCreatorLabel utensilsPubblic = new CardCreatorLabel(cardInfoPubblic5, null,false, "/cardUtensils/");
        TabCardLabel tabCardLabelEX = new TabCardLabel();
        tabCardLabelEX.configureTabObjective(objectivePubblic.getCardObjective());
        tabCardLabelEX.configureTabUtensils(utensilsPubblic.getCardObjective());
        BorderPane borderPaneEX = tabCardLabelEX.getGroupPane();
        configureAnchorPane(anchorGame, borderPaneEX, 40d, 330d, 880d, 210d);


        //RISERVA -> PROVA SENZA MESSAGGIO PER POSIZIONE
        ArrayList<String> diceInfo = new ArrayList<>(Arrays.asList("blue2","red1","yellow6","red2","blue2","purple2","purple2","purple2","purple2" ));
        ReserveLabel reserveEX = new ReserveLabel(diceInfo,48,48,48,48);
        configureAnchorPane(anchorGame, reserveEX.getHBox(), 790d, 760d, 0d, 150d);


        //CARTE SIDE AVVERSARIE -> PROVA SENZA MESSAGGIO PER POSIZIONE
        ArrayList<String> nOfP = new ArrayList<>(Arrays.asList("Paolo", "Kevin", "Gennaro"));
        ArrayList<String> sName = new ArrayList<>(Arrays.asList("via-lux", "vitrus", "sun-catcher"));
        SideEnemyLabel enemies = new SideEnemyLabel(nOfP, sName);
        HBox sideEnemy = (enemies.getLabelSideEnemy());
        sideEnemy.setMaxHeight(200);
        configureAnchorPane(anchorGame, sideEnemy, 20d, 60d, 610d, 585d);
        ArrayList<String> infDie = new ArrayList<>(Arrays.asList("Gennaro", "blue2", "blue1","blue5","white0","white0","white0","white0","white0","white0","white0","white0",
                "white0","white0","white0","white0","white0","white0","white0","white0","white0"));
        enemies.updateSideEnemies(infDie);


        //CARTA SIDE GIOCATORE E DADI AZIONE -> PROVA SENZA MESSAGGIO PER POSIZIONE
        ArrayList<Integer> sizeGrid = new ArrayList<>(Arrays.asList(67, 66));
        ArrayList<Integer> sizeSide = new ArrayList<>(Arrays.asList(340, 290));
        ArrayList<Double> positionGrid = new ArrayList<>(Arrays.asList(4d, 0d, 3d, 21d));
        ArrayList<Double> sizeRect = new ArrayList<>(Arrays.asList(65d, 65d));

        SideCardLabel pSide = new SideCardLabel("aurora-sagradis", "SIMONE", sizeGrid, positionGrid, sizeSide, sizeRect,true, reserveEX);
        AnchorPane playerAnchorePane = pSide.getAnchorPane();
        playerAnchorePane.setPrefSize(450, 393);
        ButtonGameLabel buttonGameEX = new ButtonGameLabel(connectionHandler,reserveEX,pSide,utensilsPubblic);

        configureAnchorPane(anchorGame, playerAnchorePane, 835d, 190d, 50d, 370d);
        configureAnchorPane(anchorGame, buttonGameEX.getLabelButtonGame(), 880d, 500d, 100d, 200d);

        //ArrayList<String> putEX = new ArrayList<>(Arrays.asList("2","2","3"));
        //pSide.updateSideAfterPut(putEX);


        //NICKNAME LABEL -> PROVA SENZA MESSAGGIO PER POSIZIONAMENTO
        SettingLabel nick = new SettingLabel("Paolo", "5", "4", "Kevin");
        nick.updateTurn("Simone");
        configureAnchorPane(anchorGame, nick.getSettingLabel(), 835d, 80d, 70d, 800d);


        //SCENA E LANCIO -> PROVA PERPOSIZIONAMENTO
        primaryStage.setScene(new Scene(anchorGame));
        primaryStage.centerOnScreen();
        primaryStage.setMaxWidth(1400);
        primaryStage.setMaxHeight(900);
        primaryStage.setMinWidth(1400);
        primaryStage.setMinHeight(900);
        primaryStage.show();
*/

        //Listener per il cambio di scena e valutazione del messaggio
        message.textProperty().addListener((obs, oldText, newText) -> {
        if(!message.getText().equals("")){
            //TODO: MESSAGGI DI TIPO START
            if (ClientMessageParser.isStartMessage(message.getText())) {

                //MESSAGGIO START PER LA SCELTA DELLE CARTE SIDE
                if (ClientMessageParser.isStartChoseSideMessage(message.getText())) {
                    //SCHERMATA DI SCELTA SIDE
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
                    privateObjective = new CardCreatorLabel(ClientMessageParser.getInformationsFromMessage(message.getText()), null,true,null);
                    configureAnchorPane(anchorGame, privateObjective.getCardObjective(), 0d, 310d, 635d, 300d);

                }


                //MESSAGGIO START PER L'ASSEGNAMENTO DELLE CARTE OBBIETTIVO PUBBLICHE
                if (ClientMessageParser.isStartPublicObjectiveMessage(message.getText())) {
                    List<String> objectivePublicInfo = ClientMessageParser.getInformationsFromMessage(message.getText());
                    publicObjective = new CardCreatorLabel(objectivePublicInfo, null,false, "/cardObjective/");
                    tabCardLabel.configureTabObjective(publicObjective.getCardObjective());
                }

                //MESSSAGGIO START PER L'ASSEGNAMENTO DELLE CARTE UTENSILI
                if (ClientMessageParser.isStartUtensilMessage(message.getText())) {
                    List<String> cardUtensilsInfo = ClientMessageParser.getInformationsFromMessage(message.getText());
                    cardUtensils = new CardCreatorLabel(cardUtensilsInfo, createDictionary(cardUtensilsInfo),false, "/cardUtensils/");
                    tabCardLabel.configureTabUtensils(cardUtensils.getCardObjective());
                }
            }


            //TODO: MESSAGGI DI TIPO UPDATE
            if (ClientMessageParser.isUpdateMessage(message.getText())) {

                //MESSAGGIO UPDATE PER IL CAMBIO DEL ROUND
                if (ClientMessageParser.isUpdateRoundMessage(message.getText())) {
                    List<String> roundInfo = ClientMessageParser.getInformationsFromMessage(message.getText());
                    roundLabel.proceedRound(roundInfo,120,70);
                }


                //MESSAGGIO UPDATE DELLA ROUNDGRID QUANDO EVENTUALMENTE SI CAMBIANO I SUOI DADI (UTILIZZO UTENSILE)
                if (ClientMessageParser.isUpdateRoundgridMessage(message.getText())) {
                    List<List<String>> roundGridInfo = ClientMessageParser.getInformationsFromUpdateRoundgridMessage(message.getText());

                    anchorGame.getChildren().remove(roundLabel);
                    ArrayList<Double> positionGridRound = new ArrayList<>(Arrays.asList(70d, 0d, 15d, 0d));
                    roundLabel = new RoundLabel(70, 70,910,90, positionGridRound);
                    configureAnchorPane(anchorGame, roundLabel.getAnchorRound(), 0d, 930d, 800d, 100d);

                    for (List<String> dieInfo : roundGridInfo) {
                        roundLabel.proceedRound(dieInfo,120,70);
                    }
                }


                //MESSAGGIO UPDATE DELLE CARTE SIDE AVVERSARIE
                if (ClientMessageParser.isUpdateSideMessage(message.getText())) {
                    List<String> infoSide = ClientMessageParser.getInformationsFromMessage(message.getText());
                    enemiesSide.updateSideEnemies(infoSide);
                }


                //MESSAGGIO UPDATE DEL TURNO DI GIOCO
                if(ClientMessageParser.isUpdateTurnMessage(message.getText())){
                    if(startGame){

                        //Posiziono le carte Utensili e Pubbliche
                        configureAnchorPane(anchorGame, tabCardLabel.getGroupPane(), 40d, 330d, 880d, 210d);

                        //Posiziono la roundGrid
                        ArrayList<Double> positionGridRound = new ArrayList<>(Arrays.asList(20d, 0d, 0d, 0d));
                        roundLabel = new RoundLabel(70, 70,930,90, positionGridRound);
                        configureAnchorPane(anchorGame,roundLabel.getAnchorRound(), 20d, 670d, 610d, 60d);

                        //Posiziono la griglia con le informazioni sul giocatore
                        settingLabel = new SettingLabel(connectionHandler.getNickname(), "2", sideChoiceLabel.getFavours(), ClientMessageParser.getInformationsFromMessage(message.getText()).get(0));
                        configureAnchorPane(anchorGame, settingLabel.getSettingLabel(), 835d, 80d, 70d, 800d);

                        //Posiziono la carta scelta dal giocatore
                        ArrayList<Integer> sizeGridPlayer = new ArrayList<>(Arrays.asList(67, 66));
                        ArrayList<Integer> sizeSidePlayer = new ArrayList<>(Arrays.asList(340, 290));
                        ArrayList<Double> positionGridPlayer = new ArrayList<>(Arrays.asList(4d, 0d, 3d, 21d));
                        ArrayList<Double> sizeRectPlayer = new ArrayList<>(Arrays.asList(65d, 65d));
                        playerSide = new SideCardLabel(sideChoiceLabel.getNameChoice(), connectionHandler.getNickname(), sizeGridPlayer, positionGridPlayer, sizeSidePlayer,sizeRectPlayer, true, reserve);
                        configureAnchorPane(anchorGame, playerSide.getAnchorPane(), 835d, 190d, 50d, 370d);

                        //Posiziono la griglia dei pulsanti
                        buttonGameLabel = new ButtonGameLabel(connectionHandler,reserve,playerSide,cardUtensils);
                        configureAnchorPane(anchorGame, buttonGameLabel.getLabelButtonGame(), 880d, 500d, 100d, 200d);

                        Scene sceneGame = new Scene(anchorGame, 1880, 1073);
                        startGame=false;
                        Platform.runLater(() ->{
                            AlertLoadingGame.closeAlert();
                            primaryStage.centerOnScreen();
                            primaryStage.setMaxWidth(1400);
                            primaryStage.setMaxHeight(900);
                            primaryStage.setMinWidth(1400);
                            primaryStage.setMinHeight(900);
                            primaryStage.setScene(sceneGame);
                        });
                    }

                    else settingLabel.updateTurn(ClientMessageParser.getInformationsFromMessage(message.getText()).get(0));
                }


                //MESSAGGIO UPDATE DELLA RISERVA
                if(ClientMessageParser.isUpdateReserveMessage(message.getText())){
                    reserve = new ReserveLabel(ClientMessageParser.getInformationsFromMessage(message.getText()),48,48,48,48);
                    configureAnchorPane(anchorGame, reserve.getHBox(), 790d, 760d, 0d, 150d);
                }
            }


            //TODO: MESSAGGI DI TIPO SUCCESSO
            if (ClientMessageParser.isSuccessMessage(message.getText())) {

                //MESSAGGIO SUCCESSO PER IL PIAZZAMENTO DEI DADI
                if (ClientMessageParser.isSuccessPutMessage(message.getText())) {
                    playerSide.updateSideAfterPut(ClientMessageParser.getInformationsFromMessage(message.getText()));
                    settingLabel.updateAction();
                }


                //MESSAGGIO SUCCESSO RICHIESTA DI ATTIVAZIONE DI UNA CARTA UTENSILE
                if(ClientMessageParser.isSuccessActivateUtensilMessage(message.getText())){
                    buttonGameLabel.getAlertCardUtensils().launchExecutionUtensil();

                }


                //MESSAGGIO SUCCESSO ATTIVAZIONE DI UNA CARTA UTENSILE MULTIPARAMETRO
                if(ClientMessageParser.isSuccessUseUtensilMessage(message.getText())){
                    //AlertValidation.display("Successo", "La carta è stata attivata!!");

                }
            }



            //TODO: MESSAGGIO DI TIPO UTENSILE

            //MESSAGGIO SUCCESSO UTILIZZO CARTA UTENSILE
            if(ClientMessageParser.isUseUtensilEndMessage(message.getText())){
                List<String> updateCost = ClientMessageParser.getInformationsFromMessage(message.getText());
                settingLabel.updateAction();
                settingLabel.updateFavours(updateCost.get(1));
            }



            //TODO: MESSAGGI DI TIPO ERRORE
            if(ClientMessageParser.isErrorMessage(message.getText())){
                if(ClientMessageParser.isErrorPutMessage(message.getText())) AlertValidation.display("Errore", "Il tuo posizionamento non è valido!");
                if(ClientMessageParser.isErrorActivateUtensilMessage(message.getText())) {
                    AlertValidation.display("Errore", "Non puoi utilizzare la carta selezionata!");
                }
                if(ClientMessageParser.isClientDisconnectedMessage(message.getText())) AlertValidation.display("Disconnessione", ClientMessageParser.getInformationsFromMessage(message.getText()) + "si è disconnesso");

            }

        }});
    }

    public void setNickName (String message){
        this.nickName = message;
    }

    public void setConnectionHandler (ConnectionHandler connectionHandler){
        this.connectionHandler = connectionHandler;
    }

    public ConnectionHandler getConnectionHandler () {
        return connectionHandler;
    }

    public TextField getMessage () {
        return message;
    }

    private void closeWindow(Stage primaryStage){
        boolean answer = AlertCloseButton.display("Sagrada", "Vuoi davvero uscire da Sagrada?");
        if(answer) primaryStage.close();
        else primaryStage.show();
    }

    public void sendToView(String message){
        //il messaggio in ingresso deve essere interpretato, quindi procedo con la classificazione
        this.message.setText(message);
    }


}