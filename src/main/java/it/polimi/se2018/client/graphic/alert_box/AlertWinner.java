package it.polimi.se2018.client.graphic.alert_box;

import it.polimi.se2018.client.graphic.Utility;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

import static it.polimi.se2018.client.graphic.Utility.configureImageView;
import static it.polimi.se2018.client.graphic.Utility.shadowEffect;


/**
 * Classe AlertWinner che viene invocato una volta che il gioco è stato terminato. Visualizza a schermo una classifica parziale dei punteggi
 * ottenuti dai giocatori e decreta il vincitore della partita.
 *
 * @author Simone Lanzillotta
 */


public class AlertWinner {


    public static void display(String title,String message, Stage primaryStage){

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setWidth(500);
        window.setHeight(500);
        Label label = Utility.setFontStyle(new Label(), 20);
        label.setText(message);
        label.setTextAlignment(TextAlignment.CENTER);


        AnchorPane anchorWinner = new AnchorPane();
        anchorWinner.setStyle("-fx-background-image: url(back-winner.png); -fx-background-size: contain; -fx-background-position: center; -fx-background-repeat: no-repeat;");

        ImageView backButton = shadowEffect(configureImageView("","button-back", ".png",128,61));
        backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            window.close();
            primaryStage.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, backButton);
        layout.setAlignment(Pos.CENTER);

        anchorWinner.getChildren().add(layout);
        Scene scene = new Scene(anchorWinner);

        window.setScene(scene);
        window.showAndWait();
    }
}

