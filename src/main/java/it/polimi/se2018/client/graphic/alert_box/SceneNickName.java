package it.polimi.se2018.client.graphic.alert_box;

import it.polimi.se2018.client.cli.Cli;
import it.polimi.se2018.client.connection_handler.ConnectionHandlerRMI;
import it.polimi.se2018.client.connection_handler.ConnectionHandlerSocket;
import it.polimi.se2018.client.graphic.InitWindow;
import it.polimi.se2018.server.exceptions.GameStartedException;
import it.polimi.se2018.server.exceptions.InvalidNicknameException;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.NotBoundException;

import static it.polimi.se2018.client.graphic.Utility.*;

public class SceneNickName {

    private static final String EXTENSION = ".png";
    private static final String SUBDIRECTORY = "";
    private Scene sceneNickName;

    public SceneNickName(Stage window, String connectionType, String interfaceType, InitWindow init, int port, String iP, Scene sceneLoading, Scene sceneConnection){

        //Label per accogliere il titolo della finestra
        Label labelTitle = setFontStyle(new Label("Scegli il tuo nickName"),25);

        //Label per l'inserimento del nickname
        VBox layout = new VBox(10);
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(12);
        grid.setHgap(14);

        TextField textNick = new TextField();
        textNick.setPromptText("nickname");
        textNick.setPrefSize(200,20);
        textNick.setFont(Font.font("Verdana", FontWeight.THIN, 18));

        GridPane.setConstraints(textNick, 0, 0);
        grid.setAlignment(Pos.CENTER);
        grid.getChildren().add(textNick);

        //Label button continue/back e check validation
        HBox labelButton = new HBox(20);
        labelButton.setAlignment(Pos.CENTER);

        ImageView backButton = shadowEffect(configureImageView(SUBDIRECTORY,"button-back",EXTENSION,138,71));
        ImageView continueButton = shadowEffect(configureImageView(SUBDIRECTORY,"button-continue",EXTENSION,168,71));

        labelButton.getChildren().addAll(continueButton, backButton);

        layout.getChildren().addAll(labelTitle, grid, labelButton);
        layout.setAlignment(Pos.CENTER);

        //Bach to connection
        backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e ->{
            Platform.runLater(() -> {
                window.setScene(sceneConnection);
            });
        });

        //Validation of input
        continueButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            Platform.runLater(() -> {
                if(isValidInput(textNick.getText(), connectionType, interfaceType, init, port, iP)) {
                    if(interfaceType.equals("Gui")) window.setScene(sceneLoading);
                    else {
                        init.getPrimaryStage().hide();
                        window.close();
                    }
                }
            });
        });

        //Formattazione finale della Finestra
        layout.setBackground(configureBackground("back-init-close", 550,480));

        sceneNickName = new Scene(layout,550,480);
}


    private static boolean isValidInput(String input, String connectionType, String interfaceType, InitWindow init, int port, String iP) {

        boolean value = false;
        if (input.trim().isEmpty()) {
            AlertValidation.display("Sagrada", "Attenzione! Nickname non valido.");
        } else {

            try {
                if (connectionType.equals("Socket")) {
                    init.setConnectionHandler(new ConnectionHandlerSocket(input, init, iP, port));
                    if(interfaceType.equals("Cli")) init.setInitCli(new Cli(init.getConnectionHandler().getNickname()));
                }
                else if (connectionType.equals("Rmi")) {
                    init.setConnectionHandler(new ConnectionHandlerRMI(input, init, iP));
                    if (interfaceType.equals("Cli")) init.setInitCli(new Cli(init.getConnectionHandler().getNickname()));
                }
                value = true;
            } catch (InvalidNicknameException e) {
                AlertValidation.display("Sagrada", "Attenzione! Nickname già utilizzato!.");
            } catch (GameStartedException e) {
                AlertValidation.display("Sagrada", "Attenzione! Partita già in corso!");
            } catch (NotBoundException | IOException e) {
                AlertValidation.display("Sagrada", "Attenzione! Errore di rete, riprova!");
            }
        }

        return value;
    }

    public Scene getSceneNickName() {
        return sceneNickName;
    }
}
