package it.polimi.se2018.client.graphic.alert_box;

import it.polimi.se2018.client.graphic.InitWindow;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import static it.polimi.se2018.client.graphic.graphic_element.Utility.*;
import static it.polimi.se2018.client.graphic.graphic_element.ButtonLabelCreator.*;


/**
 * La classe identifica l'insieme delle schermate per la gestione del login da parte dei giocatori. Si tratta di una clsse che gestisce lo switch delle scene per quanto
 * riguarda lo stage principale nell'ordine seguente:
 *
 *  -> Scene Connection
 *  -> Scene NickName
 *  -> Scene Loading
 *
 *  @author Simone Lanzillotta
 *
 */

public class AlertSwitcher{

    //Costanti di intestazione delle varie finestra
    private static final String FONT = "Matura MT Script Capitals";
    private static final String TEXTFILL = "-fx-text-fill: black;";
    private static final String SOCKET = "Socket";
    private static final String RMI = "Rmi";

    //Elementi della schermata di connessione
    private ToggleGroup groupConnection;
    private ToggleGroup groupInterface;
    private VBox portConfiguration = new VBox(5);
    private VBox iPConfiguration = new VBox(5);
    private Stage window;

    //Scene corrispondenti alle varie fasi della Connessione
    private static Scene sceneConnection;
    private static Scene sceneNickName;
    private static Scene sceneLoading;

    //Informazioni relative alle scelte del Client
    private String connectionType = null;
    private String interfaceType = null;






    /**
     * Metodo richiamto per la costruzione della finestra e la visualizzazione del suo contenuto a seconda della fase di connessione in cui ci si trova.
     *
     * @param title Titolo della finestra
     * @param messagge Intestazione della finestra
     * @param init Riferimento alla Main Window dell'interfaccia grafica
     */

    public void display(String title, String messagge, InitWindow init) {

        window = new Stage();

        //Configurazione del pannello
        window.initModality(Modality.APPLICATION_MODAL);
        window.setOnCloseRequest(Event::consume);
        setDecoration(window,title,550,500,15,15);

        //Label "SCEGLI CONNESSIONE"
        Label labelChooseConnection = setFontStyle(new Label(messagge),22);

        RadioButton socketButton = new RadioButton(SOCKET);
        socketButton.setFont(Font.font (FONT, 20));
        RadioButton rmiButton = new RadioButton(RMI);
        rmiButton.setFont(Font.font (FONT, 20));
        configureToggleGroup(SOCKET, RMI, "connectionContent", socketButton,rmiButton);



        //Label "SCEGLI COORDINATE"
        Label labelChooseCoordinate = setFontStyle(new Label("Scegli le coordinate di connesione:"),22);

        TextField textPort = new TextField();
        textPort.setPromptText("ex: 1234");
        textPort.setFont(Font.font("Verdana", FontWeight.THIN, 14));
        Label textPortTag = setFontStyle(new Label("Porta"),20);
        TextField textIP = new TextField();
        textIP.setPromptText("ex: 127.0.0.1");
        textIP.setFont(Font.font("Verdana", FontWeight.THIN, 14));
        Label textIPTag = setFontStyle(new Label("Ip Server"),20);

        configureToggleVBox(portConfiguration,textPortTag,textPort);
        configureToggleVBox(iPConfiguration,textIPTag,textIP);


        //Label "SCEGLI MODALITÀ DI GIOCO"
        Label labelChooseModality = setFontStyle(new Label("Scegli la modalità di gioco:"),22);

        RadioButton guiButton = new RadioButton("Gui");
        guiButton.setFont(Font.font (FONT, 20));
        RadioButton cliButton = new RadioButton("Cli");
        cliButton.setFont(Font.font (FONT, 20));
        configureToggleGroup("Gui", "Cli", "interfaceContent", guiButton, cliButton);



        //Label "CONTINUE BUTTON" e "BACK BUTTON"
        ImageView continueButton = getContinueButton(140,65);
        ImageView backButton = getBackButton(110,65);
        continueButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            Platform.runLater(() -> {
                if(isValidInput(connectionType,interfaceType, textPort.getText(), textIP.getText())) {
                    int portValue = 0;
                    if(connectionType.equals(SOCKET)) portValue= Integer.parseInt(textPort.getText());
                    sceneLoading = new SceneLoading(window,init).getSceneLoading();
                    sceneNickName = new SceneNickName(window,connectionType,interfaceType,init,portValue,textIP.getText(),sceneLoading, sceneConnection).getSceneNickName();
                    window.setScene(sceneNickName);
                }
            });

        });

        backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> window.close());


        //Composizione degli elementi
        VBox layuot = new VBox(30);
        HBox labelConnection = new HBox(20);
        HBox labelModality = new HBox(20);
        HBox labelCoordinate = new HBox(20);
        HBox labelButton = setInteractLabel(continueButton,backButton,15);
        labelConnection.getChildren().addAll(socketButton, rmiButton);
        labelConnection.setAlignment(Pos.CENTER);
        labelModality.getChildren().addAll(guiButton, cliButton);
        labelModality.setAlignment(Pos.CENTER);
        labelCoordinate.getChildren().addAll(portConfiguration,iPConfiguration);
        labelCoordinate.setAlignment(Pos.CENTER);

        layuot.getChildren().addAll(configureVBox(labelChooseConnection, labelConnection,5),
                configureVBox(labelChooseModality, labelModality, 5),
                configureVBox(labelChooseCoordinate, labelCoordinate, 5), labelButton);

        layuot.setBackground(configureBackground("back-init-close",550,480));
        layuot.setAlignment(Pos.CENTER);
        sceneConnection = new Scene(layuot,550,480);
        window.setScene(sceneConnection);
        window.showAndWait();
    }




    /**
     * Metodo utilizzato per la configurazione dei ToggleButton sulle varie opzioni di connessione e interfaccia.
     *
     * @param firstContent Intestazione del primo campo inserimento
     * @param secondContent Intestazione del primo secondo inserimento
     * @param kindOfSelection Riferimento alla tipologia di configurazione (se per la connessione o per l'interfaccia)
     * @param firstButton Riferimento al primo bottone selezione
     * @param secondButton Riferimento al secondo bottone selezione
     */

    private void configureToggleGroup(String firstContent, String secondContent, String kindOfSelection, RadioButton firstButton, RadioButton secondButton){

        switch(kindOfSelection){
            case "connectionContent": groupConnection = new ToggleGroup();
                firstButton.setToggleGroup(groupConnection);
                firstButton.setUserData(firstContent);
                secondButton.setToggleGroup(groupConnection);
                secondButton.setUserData(secondContent);

                //Listener che valuta le scelte fatte dal client sulla selezione del toggle
                groupConnection.selectedToggleProperty().addListener((ov, oldTg, newTg) -> {
                    if (groupConnection.getSelectedToggle() != null) {
                        connectionType = groupConnection.getSelectedToggle().getUserData().toString();
                        setVisible(portConfiguration,iPConfiguration);
                    }
                });
                break;

            case "interfaceContent":  groupInterface = new ToggleGroup();
                firstButton.setToggleGroup(groupInterface);
                firstButton.setUserData(firstContent);
                secondButton.setToggleGroup(groupInterface);
                secondButton.setUserData(secondContent);

                //Listener che valuta le scelte fatte dal client sulla selezione del toggle
                groupInterface.selectedToggleProperty().addListener((ov, oldTg, newTg) -> {
                    if (groupInterface.getSelectedToggle() != null) interfaceType = groupInterface.getSelectedToggle().getUserData().toString();
                });
                break;
        }
    }





    /**
     * Metodo utilizzato per rendere disponibile l'inserimento dei dati nei campi interessati dal tipo di connesione scelta. In modo specifico infatti qualore l'utente scegliesse
     * come tipologia di connessione "RMI", si rende obbligatorio solo l'inserimento dell'indirizzo IP, mentre la richiesta della porta viene disabilitata.
     *
     * @param portConfiguration Riferimento all'elemento grafico contenente il campo inserimento della Porta
     * @param iPConfiguration Riferimento all'elemento grafico contenente il campo inserimento dell'indirizzo IP
     */

    private void setVisible(VBox portConfiguration, VBox iPConfiguration){
        switch (connectionType){
            case SOCKET: portConfiguration.setDisable(false);
                iPConfiguration.setDisable(false);
                portConfiguration.getChildren().get(0).setStyle(TEXTFILL);
                iPConfiguration.getChildren().get(0).setStyle(TEXTFILL);
                break;

            case RMI: iPConfiguration.setDisable(false);
                iPConfiguration.getChildren().get(0).setStyle(TEXTFILL);
                portConfiguration.setDisable(true);
                portConfiguration.getChildren().get(0).setStyle("-fx-text-fill: grey;");
        }

    }





    /**
     * Metodo utilizzato per configurare l'elemento grafico Finale contenente intestazione e gruppo di ToggleButton per le varie selezioni. Viene richiamato due volte
     * per impostare un primo livello di scelta della porta a cui connettersi e in seguito un secondo livello per la specifica sull'indirizzo IP.
     *
     * @param vBox Riferimento al Parent dell'elemento grafico
     * @param textPort Campo di immissione dei dati
     * @param text Intestazioen dell'elemento Grafico
     */

    private void configureToggleVBox(VBox vBox, Node textPort, Node text){
        vBox.setAlignment(Pos.CENTER);
        vBox.setDisable(true);
        vBox.getChildren().addAll(textPort, text);
        vBox.getChildren().get(0).setStyle("-fx-text-fill: grey;");
    }





    /**
     * Metodo Checher utilizzato per controllare che i campi di inserimento siano stati correttamente riempiti e non siano vuoti. Nel caso viene sollevata un'eccezione
     * risolta andando a mostrare un Alert sull'entutà dell'errore
     *
     * @param connectionInput Campo relativo al tipo di connessione scelta
     * @param interfaceInput Campo relativo al tipo di interfaccia scelta
     * @param portText Campo relativo alla porta tramite la quale (in caso di connessione Socket) comunicare con il Server
     * @param ipText Campo relativo all'indirizzo IP del Server a cui si intende connettersi
     * @return Booleano con valore TRUE in caso di corretta immissioni o FALSE nel caso contrario
     */

    private boolean isValidInput(String connectionInput, String interfaceInput, String portText, String ipText) {

        boolean isValue = false;
        try{
            switch(connectionType){
                case SOCKET: if(connectionInput == null || interfaceInput == null || portText.trim().isEmpty() || ipText.trim().isEmpty() || portText.matches("^[a-zA-Z ]*$") ) throw new NullPointerException();
                               else isValue = true;
                               break;
                case RMI:    if(connectionInput == null || interfaceInput == null || ipText.trim().isEmpty()) throw new NullPointerException();
                               else isValue = true;
                               break;
            }
        }catch(NullPointerException e){
            AlertValidation.display("Sagrada", "Attenzione!\nRiempire tutti i campi opzione.");
            return false;
        }

        return isValue;
    }




    /**
     * Metodo Getter utilizzato per la restituzione del riferiemnto alla scena relativa alla selzione del Nickname. Viene richiamato nella fase di switch
     * in cui da una schermata successiva si intende ritornare in una schermata antecedente.
     *
     * @return Riferimento alla scena sceneNickName
     */

    public static Scene getSceneNickName() {
        return sceneNickName;
    }




    /**
     * Metodo utilizzato per il Tear Down della finestra Alert aperta
     *
     */

    public void closeAlert(){
        window.close();
    }
}
