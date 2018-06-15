package it.polimi.se2018.graphic.alert_box;

import it.polimi.se2018.client.connection_handler.ConnectionHandlerRMI;
import it.polimi.se2018.client.connection_handler.ConnectionHandlerSocket;
import it.polimi.se2018.graphic.InitWindow;
import it.polimi.se2018.server.exceptions.GameStartedException;
import it.polimi.se2018.server.exceptions.InvalidNicknameException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import static it.polimi.se2018.graphic.Utility.*;

import java.io.IOException;
import java.rmi.NotBoundException;

public class AlertNickName{

    public static void display(String title, String messagge, String connectionType, InitWindow init, int port, String iP) {

        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setWidth(550);
        window.setHeight(480);

        //Label per accogliere il titolo della finestra
        Label labelTitle = setFontStyle(new Label(),25);
        labelTitle.setText(messagge);


        VBox layout = new VBox(10);
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(12);
        grid.setHgap(14);

        TextField textNick = new TextField();
        textNick.setPromptText("nickname");
        textNick.setPrefSize(200,20);

        GridPane.setConstraints(textNick, 0, 0);
        grid.setAlignment(Pos.CENTER);
        grid.getChildren().add(textNick);

        HBox labelButton = new HBox(20);
        labelButton.setAlignment(Pos.CENTER);

        ImageView backButton = shadowEffect(configureImageView("button-back",138,71));
        ImageView continueButton = shadowEffect(configureImageView("button-continue",168,71));

        labelButton.getChildren().addAll(continueButton, backButton);


        layout.getChildren().addAll(labelTitle, grid, labelButton);
        layout.setAlignment(Pos.CENTER);


        //Validation of input
        continueButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> isValidInput(window,textNick.getText(), connectionType, init, port, iP));

        //Formattazione finale della Finestra
        layout.setBackground(configureBackground("back-init-close", 550,480));

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }


    private static void isValidInput(Stage window, String input, String connectionType, InitWindow init, int port, String iP) {

        if (input.trim().isEmpty()) {
            AlertValidation.display("Sagrada", "Attenzione! Nickname non valido.");
        } else {

            try {
                if (connectionType.equals("Socket")) init.setConnectionHandler(new ConnectionHandlerSocket(input, init, iP, port));
                else if (connectionType.equals("RMI")) init.setConnectionHandler(new ConnectionHandlerRMI(input, init, iP));
                init.setNickName(input);
                AlertLoading.display("Sagrada", "Sei stato messo in attesa..", init);
                window.close();
            } catch (InvalidNicknameException e) {
                AlertValidation.display("Sagrada", "Attenzione! Nickname già utilizzato!.");
            } catch (GameStartedException e) {
                AlertValidation.display("Sagrada", "Attenzione! Partita già in corso!");
            } catch (NotBoundException | IOException e) {
                AlertValidation.display("Sagrada", "Attenzione! Errore di rete, riprova!");
            }
        }
    }
}
