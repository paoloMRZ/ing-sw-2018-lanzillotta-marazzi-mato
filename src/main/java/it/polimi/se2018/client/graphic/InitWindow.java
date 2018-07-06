package it.polimi.se2018.client.graphic;

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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static it.polimi.se2018.client.graphic.graphic_element.Utility.*;
import static it.polimi.se2018.client.graphic.graphic_element.ButtonLabelCreator.*;


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
    private String action;
    private String sideSelectedByPlayer;
    private String turnOf;

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
    private static final String MARTELLETTO = "Martelletto";



    public static void main(String[] args) {
        launch(args);
    }


    /**
     * Metodo utilizzato per l'inizializzazione dell"interfaccia grafica dell'utente. Gestisce l'altgernanza tra le varie fase di gioco:
     *
     *  - Fase di Login: viene visualizzata la schermata che gestisce le scelte del giocatore tra tipologia di connessione e preferenze di interfaccia, nonchè la scelta
     *     del nickname;
     *  - Fase di scelta Carta Side: viene visualizzata la schermata che permette al gicatore la scelta della carta Side con la quale giocare la partita
     *  - Fase di gioco: viene visualizzata la schermata di gioco con tutti gli elementi configurati e posizionati
     *
     * @param init Riferimento alla PrimaryStage
     */

    @Override
    public void start(Stage init) {

        //TODO: SCHERMATA LOGIN
        this.primaryStage = init;

        //Configurazione della Schermata di Login
        setDecoration(primaryStage,SAGRADA,880,750,15,15);
        primaryStage.setOnCloseRequest(e -> closeWindow(primaryStage, e,false));

        ImageView startButton = getStartButton(190,90);
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
        primaryStage.show();



        //Formattazione dell'AnchorPane su cui ancorare gli elementi della schermata di gioco
        anchorGame = new AnchorPane();
        anchorGame.setStyle("-fx-background-image: url(prova-sfondo.png); -fx-background-size: contain; -fx-background-position: center; -fx-background-repeat: no-repeat;");


        //Listener per il cambio di scena e valutazione del messaggio
        message.textProperty().addListener((obs, oldText, newText) -> {
            if (!newText.equals("")) {
                if (!ClientMessageParser.isRiconnectMessage(message.getText())) {

                    //MESSAGGI DI TIPO START
                    if (ClientMessageParser.isStartMessage(message.getText())) {

                        //MESSAGGIO START PER LA SCELTA DELLE CARTE SIDE
                        if (ClientMessageParser.isStartChoseSideMessage(message.getText())) {

                            //SCHERMATA SCELTA SIDE
                            List<String> sideSelection = ClientMessageParser.getInformationsFromMessage(message.getText());
                            sideChoiceLabel = new SideChoiceLabel(sideSelection, connectionHandler, adapterResolution);

                            Scene sceneChoice = new Scene(sideChoiceLabel.getSideChoise(), adapterResolution.getSideChoiceLabelSize().get(2).get(0), adapterResolution.getSideChoiceLabelSize().get(2).get(1));
                            Platform.runLater(() -> {
                                alertSwitcher.closeAlert();
                                setDecoration(primaryStage, SAGRADA, adapterResolution.getSideChoiceLabelSize().get(2).get(0), adapterResolution.getSideChoiceLabelSize().get(2).get(1), 15, 15);
                                primaryStage.setScene(sceneChoice);
                            });
                        }


                        //MESSAGGIO START PER L'ASSEGNAMENTO DELLE CARTE SIDE AVVERSARIE
                        if (ClientMessageParser.isStartSideListMessage(message.getText())) setEnemiesSide(message.getText());

                        //MESSAGGIO START PER L'ASSEGNAMENTO DELLA CARTA OBBIETTIVO PRIVATA
                        if (ClientMessageParser.isStartPrivateObjectiveMessage(message.getText())) setPrivateObjective(message.getText());

                        //MESSAGGIO START PER L'ASSEGNAMENTO DELLE CARTE OBBIETTIVO PUBBLICHE
                        if (ClientMessageParser.isStartPublicObjectiveMessage(message.getText())) setTabObjective(message.getText());

                        //MESSSAGGIO START PER L'ASSEGNAMENTO DELLE CARTE UTENSILI
                        if (ClientMessageParser.isStartUtensilMessage(message.getText())) setTabUtensils(message.getText());

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
                            adapterResolution.putRoundGridLabel(anchorGame, nodeRoundGame);

                            //Posiziono la SettingLabel
                            favours = sideChoiceLabel.getFavours();
                            settingLabel = new SettingLabel(connectionHandler.getNickname(), "2", favours, ClientMessageParser.getInformationsFromMessage(message.getText()).get(0), adapterResolution);
                            nodeSetting = settingLabel.getSettingLabel();
                            action = "2";
                            adapterResolution.putSettingLabel(anchorGame, nodeSetting);

                            //Posiziono le carte Side avversarie
                            adapterResolution.putSideEnemyLabel(anchorGame, nodeCardOfEnemies);

                            //Posiziono la carta Side del giocatore
                            playerSide = new SideCardLabel(sideSelectedByPlayer, connectionHandler.getNickname(), true, false, adapterResolution);
                            nodeSidePlayer = playerSide.getAnchorPane();
                            adapterResolution.putSideLabel(anchorGame, nodeSidePlayer);

                            //Posiziono la griglia dei pulsanti
                            costUtensilHistory = new ArrayList<>(Arrays.asList("1", "1", "1"));
                            setButtonLabel(message.getText());
                            adapterResolution.putButtonLabel(anchorGame, nodeButton);


                            Scene sceneGame = new Scene(anchorGame, 1880, 1073);
                            startGame = false;
                            Platform.runLater(() -> {
                                AlertLoadingGame.closeAlert();
                                setDecoration(primaryStage, SAGRADA, adapterResolution.getPrimaryStageSize().get(0), adapterResolution.getPrimaryStageSize().get(1), 15, 15);
                                primaryStage.setOnCloseRequest(e -> closeWindow(primaryStage, e, true));
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
                            updateGUI(newText, primaryStage);
                        }
                    } else updateGUI(newText, primaryStage);
                }

                else configureReconnectedPlayer(message.getText());
            }
        });
    }


    /**
     * Metodo utilizzato per l'update dei vari elementi grafici nel momento in cui subiscono variazioni per le giocate dei vari player. Si è deciso di inglobare la
     * gestione degli update in un'unico metodo gestiti dalla classe Platform , per evitare problemi di multithreading. Infatti la messagistica di aggiornamento è gestita
     * come messagistica BroadCast, pertanto più processi (in questo caso le interfacce grafiche degli utenti) sono interessati alle varie modifiche, pertanto si è ritenuto
     * necessario monitorare il tutto e renderlo l'interazione sequenziale.
     *
     * @param newValue Nuovo messaggio di update da analizzare
     * @param primaryStage Finestra principale di riferimento
     */

    private void updateGUI(String newValue, Stage primaryStage) {

        Platform.runLater(() -> {

            //MESSAGGI DI TIPO UPDATE
            if (ClientMessageParser.isUpdateMessage(newValue)) {


                //MESSAGGIO UPDATE PER IL CAMBIO DEL TURNO
                if (ClientMessageParser.isUpdateTurnMessage(newValue)) {

                    //Aggiornamento della Griglia informativa del Giocatore
                    turnOf = ClientMessageParser.getInformationsFromMessage(newValue).get(0);
                    resetSettingLabel(TURN);

                    //Aggiornamento della Griglia dei bottoni Azione
                    resetButtonLabel();

                    //Reset campo Azione
                    action = "2";

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

                    //Reset campo Azione
                    action = "2";

                    //Aggiornamento della Griglia dei Bottoni
                    anchorGame.getChildren().remove(nodeButton);
                    buttonGameLabel.checkPermission(connectionHandler.getNickname(), ClientMessageParser.getInformationsFromMessage(newValue).get(0));
                    nodeButton = buttonGameLabel.getLabelButtonGame();
                    adapterResolution.putButtonLabel(anchorGame, nodeButton);

                    //Aggiornamento della griglia Informazione del giocatore
                    turnOf = roundInfo.get(0);
                    resetSettingLabel(ROUND);
                }


                //MESSAGGIO UPDATE DELLA ROUNDGRID QUANDO EVENTUALMENTE SI CAMBIANO I SUOI DADI (UTILIZZO UTENSILE)
                if (ClientMessageParser.isUpdateRoundgridMessage(newValue)) {
                    ArrayList<List<String>> roundGridInfo = new ArrayList<>(ClientMessageParser.getInformationsFromUpdateRoundgridMessage(newValue));
                    roundGridInfo.remove(roundGridInfo.size()-1);
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
                        playerSide = new SideCardLabel(sideSelectedByPlayer, connectionHandler.getNickname(), true, false,adapterResolution);
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

                    reserve = new ReserveLabel(ClientMessageParser.getInformationsFromMessage(newValue), adapterResolution);

                    //Aggiornamento del riferimento alla nuova riserva ricevuta
                    anchorGame.getChildren().remove(nodeReserve);
                    nodeReserve = reserve.getHBox();
                    adapterResolution.putReserveLabel(anchorGame, nodeReserve);

                    //Aggiornamento del riferimento del ButtonGameLabel -> in caso di utilizzo di un utensile, questa operazione va fatta solamente nel messaggio di END ACTIVATE
                    if(!isUseUtensil) {
                        resetButtonLabel();
                    }
                }



                //MESSAGGIO UPDATE DEL COSTO DEGLI UTENSILI
                if(ClientMessageParser.isUpdatePriceMessage(newValue)){
                    //Aggiorno la lista storica dei costi delle Utensili
                    costUtensilHistory = (ArrayList<String>)ClientMessageParser.getInformationsFromMessage(newValue);
                }


            }


            //TODO: MESSAGGI DI TIPO SUCCESSO
            if (ClientMessageParser.isSuccessMessage(newValue)) {

                //MESSAGGIO SUCCESSO PER IL PIAZZAMENTO DEI DADI
                if (ClientMessageParser.isSuccessPutMessage(newValue)) {
                    action = String.valueOf(Integer.parseInt(action)-1);
                    resetSettingLabel(PUT);
                    AlertValidation.display(SAGRADA, "La tua azione è andata\n a buon fine!");
                }


                //MESSAGGIO SUCCESSO RICHIESTA DI ATTIVAZIONE DI UNA CARTA UTENSILE
                if (ClientMessageParser.isSuccessActivateUtensilMessage(newValue)) {

                    String check7 = setUpperWord(String.valueOf(cardUtensils.getKeyName().get(Integer.parseInt(alertCardUtensils.getSelection()))));
                    //Blocco gli aggiornamenti relativi a carta Side e Riserva fino alla ricezione del messaggio di End
                    isUseUtensil = true;

                    //Lancio la schermata specifica dell'utilizzo della carta Utensile selezionata
                    if(!check7.equals(MARTELLETTO)) alertCardUtensils.launchExecutionUtensil(false,null);
                    else {
                        //Aggiornamento della griglia Informazioni del giocatore
                        action = String.valueOf(Integer.parseInt(action)-1);
                        resetSettingLabel(UTENSIL);

                        //Chiusura della finestra dedicata alle carte utensili
                        alertCardUtensils.closeExecutionUtensil();

                        //Aggiornamento della girglia dei bottoni
                        resetButtonLabel();

                        //Termia azione Attivazione delle Utensili
                        isUseUtensil = false;
                        Platform.runLater(() -> AlertValidation.display("Successo", "La carta è stata attivata!!"));
                    }

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
                action = String.valueOf(Integer.parseInt(action)-1);
                favours = ClientMessageParser.getInformationsFromMessage(newValue).get(3);
                resetSettingLabel(UTENSIL);

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
                if (ClientMessageParser.isErrorUseUtensilMessage(newValue))
                    AlertValidation.display(ERROR, "Non hai inserito correttamente\ni parametri richiesti!");
            }


            //TODO: MESSAGGIO END DELLA PARTITA
            if(ClientMessageParser.isWinnerMessage(newValue)){
                AlertWinner.display(SAGRADA, primaryStage, ClientMessageParser.getInformationsFromMessage(newValue));
            }

        });
    }




    /**
     * Metodo utilizzato per la configurazione dell' interfaccia grafica qualora un giocatore si disconnettesse e in seguito di riconnetta. È necessario quindi che la
     * schermata di gioco sia impostata con i valori aggiornati al momento della riconnessione. in particolare si richiede la ricezione dei seguenti messaggi update:
     *
     *  - Carte Utensili estratte per la partita
     *  - Carte Obbiettivo Pubbliche estratte per la partita
     *  - Obbiettivo Privato del giocatore
     *  - Lista dei giocatori avversari
     *  - Segnalini Favore del giocatore
     *  - Riserva disponibile al momento della riconnessione
     *  - RoundGrid (con l'informazione dei dadi posti al suo interno)
     *  - Prezzo delle carte Utensili
     *  - Informazioni sul turnante
     *
     * Il metodo analizza gli stessi messaggi utilizzati nella normale connessione, ma resi diretti al giocatore che si è riconnesso (Non più in broadcast). La
     * motivazione della scelta implementativa è dovuta alla possibilità che insorgano problemi di sovrapposizione con la gestione dei messaggi in broadcast: i giocatori
     * avversari infatti devono essere ignari dei messaggi di riconnessione utilizzato per configurare la schermata di gioco del giocatore appena riconnesso.
     *
     *
     * @param message Messaggio da analizzare e da cui prelevare le varie informazione suddette
     */

    private void configureReconnectedPlayer(String message){
        Platform.runLater(() -> {

            //MESSAGGI DI TIPO START
            if(ClientMessageParser.isStartMessage(message)){

                //MESSSAGGIO START PER L'ASSEGNAMENTO DELLE CARTE UTENSILI
                if (ClientMessageParser.isStartUtensilMessage(message)) setTabUtensils(message);


                //MESSAGGIO START PER L'ASSEGNAMENTO DELLE CARTE OBBIETTIVO PUBBLICHE
                if (ClientMessageParser.isStartPublicObjectiveMessage(message)) setTabObjective(message);


                //MESSAGGIO START PER L'ASSEGNAMENTO DELLE CARTE SIDE AVVERSARIE
                if (ClientMessageParser.isStartSideListMessage(message)) setEnemiesSide(message);
            }

            //MESSAGGI DI TIPO UPDATE
            if(ClientMessageParser.isUpdateMessage(message)){

                //MESSAGGIO DI UPDATE PER IL RIPRISTINO DEI SEGNALINI FAVORE
                if (ClientMessageParser.isUpdateFavoursMessage(message)){
                    List<String> infoFavours = ClientMessageParser.getInformationsFromMessage(message);
                    favours = infoFavours.get(1);
                    action = "2";
                }


                //MESSAGGIO UPDATE DELLA RISERVA
                if (ClientMessageParser.isUpdateReserveMessage(message)) {
                    reserve = new ReserveLabel(ClientMessageParser.getInformationsFromMessage(message), adapterResolution);
                    nodeReserve = reserve.getHBox();
                }


                //MESSAGGIO UPDATE DELLA ROUNDGRID QUANDO EVENTUALMENTE SI CAMBIANO I SUOI DADI (UTILIZZO UTENSILE)
                if (ClientMessageParser.isUpdateRoundgridMessage(message)) {
                    ArrayList<List<String>> roundGridInfo = new ArrayList<>(ClientMessageParser.getInformationsFromUpdateRoundgridMessage(message));
                    roundGridInfo.remove(roundGridInfo.size()-1);
                    roundLabel = new RoundLabel(adapterResolution);
                    nodeRoundGame = roundLabel.getAnchorRound();

                    //Ripristino dei dadiposizionati sulla roundGrid
                    for (List<String> dieInfo : roundGridInfo) {
                        roundLabel.proceedRound(dieInfo);
                    }
                }


                //MESSAGGIO UPDATE DELLE CARTE SIDE
                if (ClientMessageParser.isUpdateSideMessage(message)) {
                    List<String> infoSide = ClientMessageParser.getInformationsFromMessage(message);
                    if(infoSide.get(0).equals(connectionHandler.getNickname())) {
                        playerSide = new SideCardLabel(sideSelectedByPlayer, connectionHandler.getNickname(), true, false,adapterResolution);
                        playerSide.updateSideAfterPut(ClientMessageParser.getInformationsFromMessage(message));
                        nodeSidePlayer = playerSide.getAnchorPane();
                    }
                    else {
                        enemiesSide.updateSideEnemies(infoSide);
                        nodeCardOfEnemies = enemiesSide.getLabelSideEnemy();
                    }
                }


                //MESSAGGIO UPDATE DEL COSTO DEGLI UTENSILI
                if(ClientMessageParser.isUpdatePriceMessage(message)){
                    //Aggiorno la lista storica dei costi delle Utensili
                    costUtensilHistory = (ArrayList<String>)ClientMessageParser.getInformationsFromMessage(message);
                }


                //MESSAGGIO UPDATE PER IL TURNO
                if (ClientMessageParser.isUpdateTurnMessage(message)) {

                    //Posiziono le carte Utensili e Pubbliche
                    adapterResolution.putTabPaneLabel(anchorGame, tabCardLabel.getGroupPane());

                    //Posiziono la roundGrid
                    adapterResolution.putRoundGridLabel(anchorGame, nodeRoundGame);

                    //Posiziono la griglia con le informazioni sul giocatore
                    settingLabel = new SettingLabel(connectionHandler.getNickname(), "2", favours, ClientMessageParser.getInformationsFromMessage(message).get(0), adapterResolution);
                    nodeSetting = settingLabel.getSettingLabel();
                    adapterResolution.putSettingLabel(anchorGame, nodeSetting);

                    //Posiziono la carta Side del giocatore
                    adapterResolution.putSideLabel(anchorGame,nodeSidePlayer);

                    //Posiziono la Riserva
                    adapterResolution.putReserveLabel(anchorGame, nodeReserve);

                    //Posizono le carte Side degli avversari
                    adapterResolution.putSideEnemyLabel(anchorGame,nodeCardOfEnemies);

                    //Posiziono la griglia dei pulsanti
                    setButtonLabel(message);
                    adapterResolution.putButtonLabel(anchorGame, nodeButton);



                    Scene sceneGame = new Scene(anchorGame, 1880, 1073);
                    startGame = false;

                    Platform.runLater(() -> {
                        alertSwitcher.closeAlert();
                        setDecoration(primaryStage, SAGRADA, adapterResolution.getPrimaryStageSize().get(0), adapterResolution.getPrimaryStageSize().get(1), 15, 15);
                        primaryStage.setOnCloseRequest(e -> closeWindow(primaryStage, e, true));
                        primaryStage.setScene(sceneGame);
                    });
                }
            }
        });

    }





    /**
     * Metodo utilizzato in fase di avvio per la configurazione della Griglia pulsanti. Viene anche utilizzato per il ripristino nel caso in cui un giocatore si
     * disconnetta.
     *
     * @param message Informazioni per il settaggio dell'attributo sideOfEnemies
     */

    private void setButtonLabel(String message){
        turnOf = ClientMessageParser.getInformationsFromMessage(message).get(0);
        buttonGameLabel = new ButtonGameLabel(connectionHandler, reserve, playerSide, cardUtensils, costUtensilHistory, adapterResolution);
        buttonGameLabel.checkPermission(connectionHandler.getNickname(), turnOf);
        nodeButton = buttonGameLabel.getLabelButtonGame();
        alertCardUtensils = buttonGameLabel.getAlertCardUtensils();
    }




    /**
     * Metodo utilizzato in fase di avvio per la configurazione della carta Obbiettivo Privata. Viene anche utilizzato per il ripristino nel caso in cui un giocatore si
     * disconnetta.
     *
     * @param message Informazioni per il settaggio dell'attributo sideOfEnemies
     */

    private void setPrivateObjective(String message){
        privateObjective = new CardCreatorLabel(ClientMessageParser.getInformationsFromMessage(message), null, true, null, adapterResolution);
        adapterResolution.putPrivateObjectiveLabel(anchorGame, privateObjective.getCardObjective());
    }




    /**
     * Metodo utilizzato in fase di avvio per la configurazione della Tab per le carte Obbiettivo Pubbliche. Viene anche utilizzato per il ripristino nel caso in cui un
     * giocatore si disconnetta.
     *
     * @param message Informazioni per il settaggio dell'attributo sideOfEnemies
     */

    private void setTabUtensils(String message){
        List<String> cardUtensilsInfo = ClientMessageParser.getInformationsFromMessage(message);
        cardUtensils = new CardCreatorLabel(cardUtensilsInfo, createDictionary(cardUtensilsInfo), false, "/cardUtensils/", adapterResolution);
        tabCardLabel.configureTabUtensils(cardUtensils.getCardObjective());
    }



    /**
     * Metodo utilizzato in fase di avvio per la configurazione della Tab per le carte Utensili. Viene anche utilizzato per il ripristino nel caso in cui un giocatore si
     * disconnetta.
     *
     * @param message Informazioni per il settaggio dell'attributo sideOfEnemies
     */

    private void setTabObjective(String message){
        List<String> objectivePublicInfo = ClientMessageParser.getInformationsFromMessage(message);
        publicObjective = new CardCreatorLabel(objectivePublicInfo, null, false, "/cardObjective/", adapterResolution);
        tabCardLabel.configureTabObjective(publicObjective.getCardObjective());
    }




    /**
     * Metodo utilizzato in fase di avvio per la configurazione delle carte Side avversarie. Viene anche utilizzato per il ripristino nel caso in cui un giocatore si
     * disconnetta.
     *
     * @param message Informazioni per il settaggio dell'attributo sideOfEnemies
     */

    private void setEnemiesSide(String message){
        List<String> sideInfo = ClientMessageParser.getInformationsFromMessage(message);
        nameOfEnemies = sideInfo.stream().filter(s -> (sideInfo.indexOf(s) % 2 == 0)).collect(Collectors.toList());
        sideOfEnemies = sideInfo.stream().filter(s -> (sideInfo.indexOf(s) % 2 != 0)).collect(Collectors.toList());
        sideSelectedByPlayer = sideOfEnemies.remove(nameOfEnemies.indexOf(connectionHandler.getNickname()));
        nameOfEnemies.remove(connectionHandler.getNickname());
        enemiesSide = new SideEnemyLabel(nameOfEnemies, sideOfEnemies, adapterResolution);
        nodeCardOfEnemies = enemiesSide.getLabelSideEnemy();
    }



    /**
     * Metodo di supporto alla fase di aggiornamento dell'elemento grafico SettingLabel. A seguito infatti di modifiche ad alcuni attributi di classe durante le fasi
     * di gioco, è necessario creare una nuova istanza dell'ogetto per aggiornare tutte le referenze. Vengono riconosciute quindi diverse fasi di aggiornamento:
     *
     *  -> Fase di aggiornamento Turnazione -> richiama updateTurn(String infoTurnOf)
     *  -> Fase di aggiornamento inizio nuovo Round -> crea un nuovo oggetto settato con i riferimenti ai segnalini Favore aggiornati
     *  -> Fase di aggiornamento Azione -> richiama updateAction()
     */

    private void resetSettingLabel(String typeUpdate){

        anchorGame.getChildren().remove(nodeSetting);
        switch (typeUpdate){
            case ROUND:
                settingLabel = new SettingLabel(connectionHandler.getNickname(), "2", favours, turnOf,adapterResolution);
                break;

            case PUT:
                settingLabel.updateAction(action);
                break;

            case TURN:
                settingLabel = new SettingLabel(connectionHandler.getNickname(), "2", favours, turnOf,adapterResolution);
                settingLabel.updateTurn(turnOf);
                break;

            case UTENSIL:
                settingLabel.updateAction(action);
                settingLabel.updateFavours(favours);

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
        buttonGameLabel.checkPermission(connectionHandler.getNickname(),turnOf);
        alertCardUtensils = buttonGameLabel.getAlertCardUtensils();
        nodeButton = buttonGameLabel.getLabelButtonGame();
        adapterResolution.putButtonLabel(anchorGame, nodeButton);
    }


    /**
     * Metodo Setter che imposta il riferimento al ConnectionHandler rappresentante del giocatore
     *
     * @param connectionHandler Riferimento all'oggetto rappresentante il player
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
     * @param e Evento di Close Window
     * @param disconnectionRequest booleano con valore TRUE se l'evento è generato durante la partita (quindi interpretato comunque come una disconessione),
     *                             altrimenti FALSE
     */

    private void closeWindow(Stage primaryStage, WindowEvent e, Boolean disconnectionRequest) {
        boolean answer = AlertCloseButton.display(SAGRADA, "Vuoi davvero uscire da Sagrada?");
        if (answer) {
            if(disconnectionRequest) connectionHandler.sendToServer(ClientMessageCreator.getDisconnectMessage(connectionHandler.getNickname()));
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