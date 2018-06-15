package it.polimi.se2018.graphic;

import it.polimi.se2018.client.connection_handler.ConnectionHandler;
import it.polimi.se2018.client.message.ClientMessageParser;
import it.polimi.se2018.graphic.alert_box.AlertCloseButton;
import it.polimi.se2018.graphic.alert_box.AlertConnection;
import it.polimi.se2018.graphic.alert_box.AlertValidation;
import it.polimi.se2018.server.exceptions.GameStartedException;
import it.polimi.se2018.server.exceptions.InvalidNicknameException;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static it.polimi.se2018.graphic.Utility.*;


public class InitWindow extends Application {

    private StackPane landscape;
    private BorderPane borderPane;
    private ConnectionHandler connectionHandler;
    private DropShadow shadow = new DropShadow();
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
    public void start(Stage primaryStage) throws GameStartedException, InvalidNicknameException, MalformedURLException, RemoteException, NotBoundException {

        //Imposto il nome della finestra principale
        primaryStage.setTitle("Sagrada");
        primaryStage.getIcons().add(new Image("iconPack/icon-sagrada.png", 10, 10, false, true));
        primaryStage.setOnCloseRequest(e -> closeWindow(primaryStage));


        //TODO: SCHERMATA LOGIN
        BackgroundImage init = new BackgroundImage(new Image("back-init.jpg", 878, 718, false, true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        ImageView continueButton = Utility.shadowEffect(Utility.configureImageView("button-continue", 200, 90));
        ImageView startButton = Utility.shadowEffect(Utility.configureImageView("button-start-game", 200, 90));
        startButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> AlertConnection.display("Sagrada", "Scegli la modalità di connessione:", this));

        borderPane = new BorderPane();
        borderPane.setBackground(new Background(init));

        HBox layuot = new HBox(50);
        layuot.getChildren().addAll(continueButton, startButton);
        layuot.setAlignment(Pos.CENTER);
        borderPane.setCenter(layuot);
        landscape = new StackPane();
        landscape.getChildren().add(borderPane);

        /*
            Scene sceneLogin = new Scene(landscape, 878, 718);
            primaryStage.setScene(sceneLogin);
            primaryStage.show();
        */


        //TODO: SCHERMATA DI GIOCO
        //StackPane da utilizzare solamente alla fine perchè ha priorità maggiore
        StackPane gameStack = new StackPane();

        //AnchorPane su cui ancorare gli elementi della schermata di gioco
        AnchorPane anchorGame = new AnchorPane();
        anchorGame.setPrefSize(1880, 1070);
        BackgroundImage anchorGameBack = new BackgroundImage(new Image("prova-sfondo.png", 1880, 1073, false, true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        anchorGame.setBackground(new Background(anchorGameBack));



        //Aggiungo gli elementi della schermata -> SOLO PROVA POSIZIONE NELLA SCHERMATA -> TESTING

        //ROUNDGRID -> PROVA SENZA MESSAGGIO PER POSIZIONE
        RoundLabel roundEX = new RoundLabel(70, 70);
        configureAnchorPane(anchorGame, roundEX.getAnchorRound(), 0d, 930d, 800d, 100d);
        ArrayList<String> diceRoundInfo = new ArrayList<>();
        diceRoundInfo.add("blue2");
        diceRoundInfo.add("red2");
        roundEX.proceedRound(diceRoundInfo);
        roundEX.proceedRound(diceRoundInfo);



        //CARTA OBBIETTIVO PRIVATA -> PROVA SENZA MESSAGGIO PER POSIZIONE
        ArrayList<String> cardInfoPrivate = new ArrayList<>();
        cardInfoPrivate.add("sfumature-gialle-privata");
        CardCreatorLabel objectivePrivate = new CardCreatorLabel(cardInfoPrivate, true, null);
        configureAnchorPane(anchorGame, objectivePrivate.getCardObjective(), 580d, 490d, 855d, 320d);



        //CARTE OBBIETTIVO PUBBLICHE E UTENSILI-> PROVA SENZA MESSAGGIO PER POSIZIONE
        ArrayList<String> cardInfoPubblic = new ArrayList<>(Arrays.asList("cardObjective/sfumature-scure","cardObjective/sfumature-medie","cardObjective/sfumature-diverse"));
        ArrayList<String> cardInfoPubblic2 = new ArrayList<>(Arrays.asList("cardUtensils/pinza-sgrossatrice","cardUtensils/pennello-per-eglomise","cardUtensils/alesatore-per-la-laminda-di-rame"));
        CardCreatorLabel objectivePubblic = new CardCreatorLabel(cardInfoPubblic, false, "cardObjective");
        CardCreatorLabel utensilsPubblic = new CardCreatorLabel(cardInfoPubblic2, false, "cardUtensils");
        TabCardLabel tabCardLabelEX = new TabCardLabel();
        tabCardLabelEX.configureTabObjective(objectivePubblic.getCardObjective());
        tabCardLabelEX.configureTabUtensils(utensilsPubblic.getCardObjective());
        BorderPane borderPaneEX = tabCardLabelEX.getBorderPane();
        configureAnchorPane(anchorGame, borderPaneEX, 40d, 500d, 1180d, 350d);



        //RISERVA -> PROVA SENZA MESSAGGIO PER POSIZIONE
        ArrayList<String> diceInfo = new ArrayList<>(Arrays.asList("blue2","red2","yellow2","red2","blue2","purple2" ));
        ReserveLabel reserveEX = new ReserveLabel(diceInfo);
        configureAnchorPane(anchorGame, reserveEX.getHBox(), 1070d, 1000d, 0d, 150d);



        //CARTE SIDE AVVERSARIE -> PROVA SENZA MESSAGGIO PER POSIZIONE
        ArrayList<String> nOfP = new ArrayList<>(Arrays.asList("Gennaro", "Paolo", "Kevin"));
        ArrayList<String> sName = new ArrayList<>(Arrays.asList("via-lux", "vitrus", "sun-catcher"));
        SideEnemyLabel enemies = new SideEnemyLabel(nOfP, sName);
        HBox sideEnemy = (enemies.getLabelSideEnemy());
        sideEnemy.setMaxHeight(200);
        configureAnchorPane(anchorGame, sideEnemy, 25d, 50d, 825d, 770d);
        ArrayList<String> infDie = new ArrayList<>(Arrays.asList("Gennaro", "blue2", "blue1","blue5","white0","white0","white0","white0","white0","white0","white0","white0",
                "white0","white0","white0","white0","white0","white0","white0","white0","white0"));
        enemies.updateSideEnemies(infDie);




        //CARTA SIDE GIOCATORE E DADI AZIONE -> PROVA SENZA MESSAGGIO PER POSIZIONE
        ArrayList<Integer> sizeGrid = new ArrayList<>(Arrays.asList(88, 88));
        ArrayList<Integer> sizeSide = new ArrayList<>(Arrays.asList(450, 393));
        ArrayList<Double> positionGrid = new ArrayList<>(Arrays.asList(0d, 0d, 0d, 26d));

        SideCardLabel pSide = new SideCardLabel("aurora-sagradis", "SIMONE", sizeGrid, positionGrid, sizeSide, true, reserveEX);
        AnchorPane playerAnchorePane = pSide.getAnchorPane();
        playerAnchorePane.setPrefSize(450, 393);
        ButtonGameLabel buttonGameEX = new ButtonGameLabel(connectionHandler,reserveEX,pSide,utensilsPubblic);

        configureAnchorPane(anchorGame, playerAnchorePane, 1110d, 0d, 50d, 250d);
        configureAnchorPane(anchorGame, buttonGameEX.getLabelButtonGame(), 1120d, 720d, 50d, 300d);

        //ArrayList<String> putEX = new ArrayList<>(Arrays.asList("2","2","3"));
        //pSide.updateSideAfterPut(putEX);




        //NICKNAME LABEL -> PROVA SENZA MESSAGGIO PER POSIZIONAMENTO
        SettingLabel nick = new SettingLabel("Paolo", "5", "4", "Kevin");
        nick.updateTurn("Simone");
        //nick.updateAction();
        configureAnchorPane(anchorGame, nick.getSettingLabel(), 1120d, 160d, 70d, 1050d);



        //SCENA E LANCIO -> PROVA PERPOSIZIONAMENTO
        gameStack.getChildren().add(anchorGame);
        gameStack.setPrefSize(1880, 1073);
        Scene sceneGameEX = new Scene(gameStack,1880,1073);
        primaryStage.setScene(sceneGameEX);
        primaryStage.setMaximized(true);
        primaryStage.show();



        //Listener per il cambio di scena e valutazione del messaggio
        message.textProperty().addListener((obs, oldText, newText) -> {

            //TODO: MESSAGGI DI TIPO START
            if (ClientMessageParser.isStartMessage(message.getText())) {

                //MESSAGGIO START PER LA SCELTA DELLE CARTE SIDE
                if (ClientMessageParser.isStartChoseSideMessage(message.getText())) {
                    //SCHERMATA DI SCELTA SIDE
                    List<String> sideSelection = ClientMessageParser.getInformationsFromMessage(message.getText());
                    sideChoiceLabel = new SideChoiceLabel(sideSelection, connectionHandler);
                    Scene sceneChoice = new Scene(sideChoiceLabel.getSideChoise(), 1300, 1020);
                    primaryStage.setScene(sceneChoice);
                    primaryStage.setMaximized(true);
                    primaryStage.show();
                }


                //MESSAGGIO START PER L'ASSEGNAMENTO DELLE CARTE SIDE AVVERSARIE
                if (ClientMessageParser.isStartSideListMessage(message.getText())) {
                    List<String> sideInfo = ClientMessageParser.getInformationsFromMessage(message.getText());
                    Stream<String> nameOfPlayers = sideInfo.stream().filter(s -> (sideInfo.indexOf(s) % 2 == 0));
                    Stream<String> sideName = sideInfo.stream().filter(s -> (sideInfo.indexOf(s) % 2 != 0));
                    enemiesSide = new SideEnemyLabel(nameOfPlayers.collect(Collectors.toList()), sideName.collect(Collectors.toList()));
                    configureAnchorPane(anchorGame, enemies.getLabelSideEnemy(), 40d, 500d, 1180d, 350d);
                }


                //MESSAGGIO START PER L'ASSEGNAMENTO DELLA CARTA OBBIETTIVO PRIVATA
                if (ClientMessageParser.isStartPrivateObjectiveMessage(message.getText())) {
                    privateObjective = new CardCreatorLabel(ClientMessageParser.getInformationsFromMessage(message.getText()), true,null);
                    configureAnchorPane(anchorGame, privateObjective.getCardObjective(), 580d, 470d, 855d, 320d);
                }


                //MESSAGGIO START PER L'ASSEGNAMENTO DELLE CARTE OBBIETTIVO PUBBLICHE
                if (ClientMessageParser.isStartPublicObjectiveMessage(message.getText())) {
                    List<String> objectivePublicInfo = ClientMessageParser.getInformationsFromMessage(message.getText());
                    publicObjective = new CardCreatorLabel(objectivePublicInfo, false, "cardObjective");
                    tabCardLabel.configureTabObjective(publicObjective.getCardObjective());
                }

                //MESSSAGGIO START PER L'ASSEGNAMENTO DELLE CARTE UTENSILI
                if (ClientMessageParser.isStartUtensilMessage(message.getText())) {
                    List<String> cardUtensilsInfo = ClientMessageParser.getInformationsFromMessage(message.getText());
                    cardUtensils = new CardCreatorLabel(cardUtensilsInfo, false, "cardUtensils");
                    tabCardLabel.configureTabObjective(cardUtensils.getCardObjective());
                }
            }


            //TODO: MESSAGGI DI TIPO UPDATE
            if (ClientMessageParser.isUpdateMessage(message.getText())) {

                //MESSAGGIO UPDATE PER IL CAMBIO DEL ROUND
                if (ClientMessageParser.isUpdateRoundMessage(message.getText())) {
                    List<String> roundInfo = ClientMessageParser.getInformationsFromMessage(message.getText());
                    settingLabel.updateTurn(roundInfo.get(0));
                    roundInfo.remove(0);
                    roundLabel.proceedRound(roundInfo);
                }


                //MESSAGGIO UPDATE DELLA ROUNDGRID QUANDO EVENTUALMENTE SI CAMBIANO I SUOI DADI (UTILIZZO UTENSILE)
                if (ClientMessageParser.isUpdateRoundgridMessage(message.getText())) {
                    List<List<String>> roundGridInfo = ClientMessageParser.getInformationsFromUpdateRoundgridMessage(message.getText());
                    anchorGame.getChildren().remove(roundLabel);
                    roundLabel = new RoundLabel(70, 70);
                    configureAnchorPane(anchorGame, roundLabel.getAnchorRound(), 0d, 930d, 800d, 100d);

                    for (List<String> dieInfo : roundGridInfo) {
                        roundLabel.proceedRound(dieInfo);
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
                        configureAnchorPane(anchorGame, tabCardLabel.getBorderPane(), 40d, 480d, 1180d, 300d);

                        //Posiziono la roundGrid
                        roundLabel = new RoundLabel(70, 70);
                        configureAnchorPane(anchorGame, roundLabel.getAnchorRound(), 0d, 930d, 800d, 100d);

                        //Posiziono la griglia con le informazioni sul giocatore
                        settingLabel = new SettingLabel(connectionHandler.getNickname(), "2", sideChoiceLabel.getFavours(), ClientMessageParser.getInformationsFromMessage(message.getText()).get(0));
                        configureAnchorPane(anchorGame, nick.getSettingLabel(), 1120d, 160d, 70d, 1050d);

                        //Posiziono la carta scelta dal giocatore
                        ArrayList<Integer> sizeGridPlayer = new ArrayList<>(Arrays.asList(88, 88));
                        ArrayList<Integer> sizeSidePlayer = new ArrayList<>(Arrays.asList(450, 393));
                        ArrayList<Double> positionGridPlayer = new ArrayList<>(Arrays.asList(0d, 0d, 0d, 26d));
                        playerSide = new SideCardLabel(sideChoiceLabel.getNameChoice(), connectionHandler.getNickname(), sizeGridPlayer, positionGridPlayer, sizeSidePlayer, true, reserve);
                        configureAnchorPane(anchorGame, playerSide.getAnchorPane(), 1110d, 0d, 50d, 250d);

                        //Posiziono la griglia dei pulsanti
                        buttonGameLabel = new ButtonGameLabel(connectionHandler,reserve,playerSide,cardUtensils);
                        configureAnchorPane(anchorGame, buttonGameLabel.getLabelButtonGame(), 1120d, 720d, 50d, 300d);

                        Scene sceneGame = new Scene(gameStack, 1880, 1073);
                        startGame=false;
                        primaryStage.setScene(sceneGame);
                        primaryStage.setMaximized(true);
                        primaryStage.show();
                    }

                    else settingLabel.updateTurn(ClientMessageParser.getInformationsFromMessage(message.getText()).get(0));
                }


                //MESSAGGIO UPDATE DELLA RISERVA
                if(ClientMessageParser.isUpdateReserveMessage(message.getText())){
                    reserve = new ReserveLabel(ClientMessageParser.getInformationsFromMessage(message.getText()));
                    configureAnchorPane(anchorGame, reserveEX.getHBox(), 1070d, 1000d, 0d, 150d);
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
                    //TODO
                    buttonGameLabel.setUseUtensils(true);
                }
            }


            //TODO: MESSAGGIO DI TIPO UTENSILE

            //MESSAGGIO SUCCESSO UTILIZZO CARTA UTENSILE
            if(ClientMessageParser.isUseUtensilEndMessage(message.getText())){
                settingLabel.updateAction();
                settingLabel.updateFavours();
                //TODO aggiornamento del prezzo della carta utensile
            }


            //TODO: MESSAGGI DI TIPO ERRORE
            if(ClientMessageParser.isErrorMessage(message.getText())){
                if(ClientMessageParser.isErrorPutMessage(message.getText())) AlertValidation.display("Errore", "Il tuo posizionamento non è valido!");
                if(ClientMessageParser.isErrorUseUtensilMessage(message.getText())) {
                    buttonGameLabel.setUseUtensils(false);
                    AlertValidation.display("Errore", "Non puoi utilizzare la carta selezionata!");
                }
                if(ClientMessageParser.isClientDisconnectedMessage(message.getText())) AlertValidation.display("Disconnessione", ClientMessageParser.getInformationsFromMessage(message.getText()) + "si è disconnesso");

            }

        });
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