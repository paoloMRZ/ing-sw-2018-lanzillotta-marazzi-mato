package it.polimi.se2018.graphic.alert_box;

import it.polimi.se2018.graphic.InitWindow;
import it.polimi.se2018.graphic.Utility;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import static it.polimi.se2018.graphic.Utility.*;


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

    private static String connectionType = null;
    private static String interfaceType = null;
    private static String font = "Matura MT Script Capitals";
    private static ToggleGroup groupConnection;
    private static ToggleGroup groupInterface;
    private static VBox portConfiguration = new VBox(5);
    private static VBox iPConfiguration = new VBox(5);


    private static Scene sceneConnection;
    private static Scene sceneNickName;
    private static Scene sceneLoading;

    private static Stage window;

    public AlertSwitcher(){}

    public static void display(String title, String messagge, InitWindow init) {

        window = new Stage();

        //Configurazione del pannello
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setWidth(550);
        window.setHeight(480);


        //Label "SCEGLI CONNESSIONE"
        Label labelChooseConnection = setFontStyle(new Label(messagge),22);

        RadioButton socketButton = new RadioButton("Socket");
        socketButton.setFont(Font.font (font, 20));
        RadioButton rmiButton = new RadioButton("Rmi");
        rmiButton.setFont(Font.font (font, 20));
        configureToggleGroup("Socket", "RMI", "connectionContent", socketButton,rmiButton);



        //Label "SCEGLI COORDINATE"
        Label labelChooseCoordinate = setFontStyle(new Label("Scegli le coordinate di connesione:"),22);

        TextField textPort = new TextField();
        Label textPortTag = setFontStyle(new Label("Porta"),20);
        TextField textIP = new TextField();
        Label textIPTag = setFontStyle(new Label("Ip Server"),20);

        configureToggleVBox(portConfiguration,textPortTag,textPort);
        configureToggleVBox(iPConfiguration,textIPTag,textIP);


        //Label "SCEGLI MODALITÀ DI GIOCO"
        Label labelChooseModality = setFontStyle(new Label("Scegli la modalità di gioco:"),22);

        RadioButton guiButton = new RadioButton("Gui");
        guiButton.setFont(Font.font (font, 20));
        RadioButton cliButton = new RadioButton("Cli");
        cliButton.setFont(Font.font (font, 20));
        configureToggleGroup("Gui", "Cli", "interfaceContent", guiButton, cliButton);



        //Label "CONTINUE BUTTON"
        ImageView continueButton = shadowEffect(configureImageView("","button-continue",".png",158,61));
        continueButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            Platform.runLater(() -> {
                if(isValidInput(connectionType,interfaceType, textPort.getText(), textIP.getText(),window,init)) {
                    int portValue = 0;
                    if(connectionType.equals("Socket")) portValue= Integer.parseInt(textPort.getText());
                    sceneLoading = new SceneLoading(window,init).getSceneLoading();
                    sceneNickName = new SceneNickName(window,connectionType,init,portValue,textIP.getText(),sceneLoading, sceneConnection).getSceneNickName();
                    window.setScene(sceneNickName);
                }
            });

        });


        //Composizione degli elementi
        VBox layuot = new VBox(30);
        HBox labelConnection = new HBox(20);
        HBox labelModality = new HBox(20);
        HBox labelCoordinate = new HBox(20);
        labelConnection.getChildren().addAll(socketButton, rmiButton);
        labelConnection.setAlignment(Pos.CENTER);
        labelModality.getChildren().addAll(guiButton, cliButton);
        labelModality.setAlignment(Pos.CENTER);
        labelCoordinate.getChildren().addAll(portConfiguration,iPConfiguration);
        labelCoordinate.setAlignment(Pos.CENTER);

        layuot.getChildren().addAll(configureVBox(labelChooseConnection, labelConnection,5),
                configureVBox(labelChooseModality, labelModality, 5),
                configureVBox(labelChooseCoordinate, labelCoordinate, 5), continueButton);

        layuot.setBackground(configureBackground("back-init-close",550,480));
        layuot.setAlignment(Pos.CENTER);

        sceneConnection = new Scene(layuot);
        window.setScene(sceneConnection);
        window.setOnCloseRequest(e -> window.close());
        window.showAndWait();
    }

    private static void configureToggleGroup(String firstContent, String secondContent, String kindOfSelection, RadioButton firstButton, RadioButton secondButton){

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

    private static void setVisible(VBox portConfiguration, VBox iPConfiguration){
        switch (connectionType){
            case "Socket": portConfiguration.setDisable(false);
                iPConfiguration.setDisable(false);
                portConfiguration.getChildren().get(0).setStyle("-fx-text-fill: black;");
                iPConfiguration.getChildren().get(0).setStyle("-fx-text-fill: black;");
                break;

            case "RMI": iPConfiguration.setDisable(false);
                iPConfiguration.getChildren().get(0).setStyle("-fx-text-fill: black;");
                portConfiguration.setDisable(true);
                portConfiguration.getChildren().get(0).setStyle("-fx-text-fill: grey;");
        }

    }

    private static void configureToggleVBox(VBox vBox, Node textPort, Node text){
        vBox.setAlignment(Pos.CENTER);
        vBox.setDisable(true);
        vBox.getChildren().addAll(textPort, text);
        vBox.getChildren().get(0).setStyle("-fx-text-fill: grey;");
    }

    private static boolean isValidInput(String connectionInput, String interfaceInput, String portText, String ipText, Stage window, InitWindow init) {

        boolean isValue = false;
        try{
            switch(connectionType){
                case "Socket": if(connectionInput == null || interfaceInput == null || portText.trim().isEmpty() || ipText.trim().isEmpty()) throw new NullPointerException();
                               else isValue = true;
                               break;
                case "RMI":    if(connectionInput == null || interfaceInput == null || ipText.trim().isEmpty()) throw new NullPointerException();
                               else isValue = true;
                               break;
            }
        }catch(NullPointerException e){
            AlertValidation.display("Sagrada", "Attenzione!\nRiempire tutti i campi opzione.");
            return false;
        }

        return isValue;
    }

    public static Scene getSceneNickName() {
        return sceneNickName;
    }

    public static void closeAlert(){
        window.close();
    }
}
