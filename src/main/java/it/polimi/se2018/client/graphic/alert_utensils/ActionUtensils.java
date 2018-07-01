package it.polimi.se2018.client.graphic.alert_utensils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.se2018.client.connection_handler.ConnectionHandler;
import it.polimi.se2018.client.graphic.ReserveLabel;
import it.polimi.se2018.client.graphic.SideCardLabel;
import it.polimi.se2018.client.graphic.adapter_gui.AdapterResolution;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;


import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import static it.polimi.se2018.client.graphic.Utility.*;

public class ActionUtensils {

    private static final String EXTENSION = ".png";
    private static final String SUBDIRECTORY = "/cardUtensils/";

    private StackPane window = new StackPane();
    private ReserveLabel reserveLabel;
    private boolean isBisEffect = false;

    public ActionUtensils(Map<String, String> dictionaryCardUtensils, String keyNameOfCard, ReserveLabel reserve, ConnectionHandler connectionHandler, String selection, SideCardLabel playerSide, Stage primaryWindow, AdapterResolution adapterResolution, String bisContent) throws IOException {

        this.reserveLabel = reserve;

        InputStream is = getClass().getClassLoader().getResourceAsStream("cardUtensilInfo.json");
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(is);

        //Formattazione per la stampa del nome in ingresso (che è il percorso della carta selezionata)
        String[] stretchSplit = keyNameOfCard.split("-");
        String name = stretchSplit[0].substring(0, 1).toUpperCase().concat(stretchSplit[0].substring(1));
        for (int i = 1; i < stretchSplit.length; i++) {
            name = name.concat(" ".concat(stretchSplit[i].substring(0, 1).toUpperCase().concat(stretchSplit[i].substring(1))));
        }


        //Header Info
        VBox headerInfo = new VBox(5);
        headerInfo.setAlignment(Pos.CENTER);
        Label header = setFontStyle(new Label("Hai selezionato: " + name), 35);
        header.setTextAlignment(TextAlignment.CENTER);

        Label cardInfo = setFontStyle(new Label(root.at("/" + name).asText()), 25);
        cardInfo.setTextAlignment(TextAlignment.CENTER);
        headerInfo.getChildren().addAll(header, cardInfo);

        //Body content
        HBox bodyContent = new HBox(100);
        ImageView cardImage = configureImageView(SUBDIRECTORY,keyNameOfCard,EXTENSION, 300,450);
        SelectorContent selectorContent = new SelectorContent(this.reserveLabel, connectionHandler,selection, playerSide,adapterResolution);
        if(!isBisEffect) bodyContent.getChildren().addAll(cardImage, selectorContent.configureNode(name,bisContent));
        else {
            name = name.concat(" ".concat("Bis"));
            bodyContent.getChildren().addAll(cardImage, selectorContent.configureNode(name,bisContent));
        }
        bodyContent.setAlignment(Pos.CENTER);

        //End content
        ImageView continueButton = shadowEffect(configureImageView("","button-continue",EXTENSION,180,90));
        String finalName = name;
        String finalPath = keyNameOfCard;
        continueButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e ->{
            if(!isBisEffect) selectorContent.configureMessage(finalName, dictionaryCardUtensils, finalPath);
            else{
                switch (finalName){
                    case "Diluente Per Pasta Salda":
                        selectorContent.configureMessage(finalName, dictionaryCardUtensils, finalPath);
                        break;

                    case "Pennello Per Pasta Salda":
                        selectorContent.configureMessage(finalName, dictionaryCardUtensils, finalPath);
                        break;
                }
            }
        });

        HBox endContent = new HBox(continueButton);
        endContent.setAlignment(Pos.CENTER);


        //Configurazione finale della finestra
        VBox finalLayout = new VBox(30);
        finalLayout.setAlignment(Pos.CENTER);
        finalLayout.getChildren().addAll(headerInfo,bodyContent,endContent);

        ImageView back = configureImageView(SUBDIRECTORY, "retro", EXTENSION,700,850);
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

    public void setBisEffect(boolean activate) {
        isBisEffect = activate;
    }

}
