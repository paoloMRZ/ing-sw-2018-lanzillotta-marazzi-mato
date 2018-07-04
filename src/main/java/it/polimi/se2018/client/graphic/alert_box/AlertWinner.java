package it.polimi.se2018.client.graphic.alert_box;

import it.polimi.se2018.client.graphic.graphic_element.Utility;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;

import static it.polimi.se2018.client.graphic.graphic_element.Utility.*;


/**
 * Classe AlertWinner che viene invocato una volta che il gioco è stato terminato. Visualizza a schermo una classifica parziale dei punteggi
 * ottenuti dai giocatori e decreta il vincitore della partita.
 *
 * @author Simone Lanzillotta
 */


public class AlertWinner {


    public static void display(String title, Stage primaryStage, List<String> scorePoint){

        Stage window = new Stage();
        BorderPane borderPane = new BorderPane();
        AnchorPane anchorWinner = new AnchorPane();
        anchorWinner.setMaxSize(800,700);
        anchorWinner.setMinSize(800,700);


        List<String> namePlayer = scorePoint.stream().filter(s -> (scorePoint.indexOf(s) % 2 == 0)).collect(Collectors.toList());
        List<String> pointPlayer = scorePoint.stream().filter(s -> (scorePoint.indexOf(s) % 2 != 0)).collect(Collectors.toList());

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMaxWidth(800);
        window.setMaxHeight(700);
        window.setMinWidth(800);
        window.setMinHeight(735);

        //Configurazione Header
        Label headerTitle = Utility.setFontStyle(new Label("Complimenti,"), 40);
        headerTitle.setTextAlignment(TextAlignment.CENTER);
        Label headerName = Utility.setFontStyle(new Label(namePlayer.get(namePlayer.size()-1)), 50);
        headerName.setTextAlignment(TextAlignment.CENTER);
        Label headerInfo = Utility.setFontStyle(new Label("Per la tua coloratissima vittoria  al gioco Sagrada!"), 32);
        headerName.setTextAlignment(TextAlignment.CENTER);
        VBox header = new VBox(headerTitle,headerName,headerInfo);
        header.setAlignment(Pos.CENTER);
        borderPane.setTop(header);


        //Configurazione sfondo della finestra
        ImageView back = configureImageView("","back-winner",".jpg",1000,800);
        back.setFitHeight(700);
        back.setFitWidth(800);
        back.setOpacity(0.5);
        anchorWinner.getChildren().add(back);


        //Configurazione Body Content (classifica parziale)
        GridPane scoreGrid = new GridPane();
        scoreGrid.setAlignment(Pos.CENTER);
        scoreGrid.setMaxSize(800,700);
        scoreGrid.setMinSize(800,700);
        scoreGrid.setPadding(new Insets(15));
        int i= namePlayer.size();
        int k=0;
        for (String player: namePlayer) {
            Label itemName = setFontStyle(new Label(player),35);
            itemName.setMaxSize(200,70);
            itemName.setMinSize(200,70);
            scoreGrid.add(itemName,k,i);
            GridPane.setHalignment(itemName, HPos.LEFT);
            Label itemScore = setFontStyle(new Label(pointPlayer.get(namePlayer.indexOf(player))),35);
            itemScore.setMaxSize(200,50);
            itemScore.setMinSize(200,50);
            scoreGrid.add(itemScore, k+1,i);
            GridPane.setHalignment(itemScore, HPos.CENTER);
            i--;
        }



        //Configurazione End content
        ImageView backButton = shadowEffect(configureImageView("", "button-back", ".png", 170, 90));
        backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            primaryStage.close();
            window.close();
        });


        borderPane.setMaxSize(800,700);
        borderPane.setMinSize(800,700);
        borderPane.setBottom(backButton);

        BorderPane.setAlignment(backButton,Pos.BOTTOM_CENTER);

        anchorWinner.getChildren().add(borderPane);
        configureAnchorPane(anchorWinner,scoreGrid,100d,130d,100d,100d);
        Scene scene = new Scene(anchorWinner,800,700);

        window.setScene(scene);
        window.showAndWait();
    }
}

