package it.polimi.se2018.graphic.alert_utensils;

import it.polimi.se2018.client.connection_handler.ConnectionHandler;
import it.polimi.se2018.graphic.ReserveLabel;
import it.polimi.se2018.graphic.SideCardLabel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;


import static it.polimi.se2018.graphic.Utility.*;

public class ActionUtensils {

    private StackPane window = new StackPane();
    private ReserveLabel reserveLabel;
    private HBox reserve;

    public ActionUtensils(String pathCard, ReserveLabel reserve, ConnectionHandler connectionHandler, String selection, SideCardLabel playerSide){

        this.reserveLabel = reserve;
        this.reserve = this.reserveLabel.callReserve();

        //Formattazione per la stampa del nome in ingresso (che è il percorso della carta selezionata)
        String[] slashSplit = pathCard.split("/");
        String[] stretchSplit = slashSplit[1].split("-");
        String name = stretchSplit[0].substring(0, 1).toUpperCase().concat(stretchSplit[0].substring(1));
        for (int i = 1; i < stretchSplit.length; i++) {
            name = name.concat(" ".concat(stretchSplit[i].substring(0, 1).toUpperCase().concat(stretchSplit[i].substring(1))));
        }


        //Header Info
        Label headerInfo = setFontStyle(new Label("Hai selezionato: " + name), 35);
        headerInfo.setTextAlignment(TextAlignment.LEFT);

        //Body content
        HBox bodyContent = new HBox(200);
        ImageView cardImage = configureImageView(pathCard, 300,450);
        SelectorContent selectorContent = new SelectorContent(this.reserveLabel, connectionHandler,selection, playerSide);
        bodyContent.getChildren().addAll(cardImage, selectorContent.configureNode(name));
        bodyContent.setAlignment(Pos.CENTER);

        //End content
        ImageView continueButton = shadowEffect(configureImageView("button-continue",200,100));
        String finalName = name;
        continueButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e ->{
            selectorContent.configureMessage(finalName);
        });
        HBox endContent = new HBox(continueButton);
        endContent.setAlignment(Pos.CENTER);


        //Final Window
        VBox finalLayout = new VBox(50);
        finalLayout.setAlignment(Pos.CENTER);
        finalLayout.getChildren().addAll(headerInfo,bodyContent,endContent);

        ImageView back = configureImageView("cardUtensils/retro",700,850);
        back.setFitHeight(1000);
        back.setFitWidth(1300);
        back.setOpacity(0.4);

        window.setPadding(new Insets(0,120,0,120));
        window.setPrefSize(300,300);
        window.getChildren().addAll(back,finalLayout);

    }

    public StackPane getWindow() {
        return window;
    }
}
