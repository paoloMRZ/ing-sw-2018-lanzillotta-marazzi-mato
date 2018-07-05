package it.polimi.se2018.client.graphic.alert_utensils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.se2018.client.connection_handler.ConnectionHandler;
import it.polimi.se2018.client.graphic.ReserveLabel;
import it.polimi.se2018.client.graphic.SideCardLabel;
import it.polimi.se2018.client.graphic.adapter_gui.AdapterResolution;
import it.polimi.se2018.client.message.ClientMessageParser;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static it.polimi.se2018.client.graphic.graphic_element.Utility.*;
import static it.polimi.se2018.client.graphic.graphic_element.ButtonLabelCreator.*;


/**
 * Classe utilizzata per configurare la finestra specifica della carta Utensile selezionata per l'attivazione da parte del giocatore. A seguito quindi della richiesta di attivazione
 * e della rispettiva conferma, viene aperta automaticamente una schermata che raccoglie tutti gli elementi con i quali l'utente è chiamato ad interagire.
 *
 * @author Simone Lanzillotta
 */




public class ActionUtensils {

    //Costanti di intestazione delle varie finestra
    private static final String EXTENSION = ".png";
    private static final String SUBDIRECTORY = "/cardUtensils/";

    private StackPane window = new StackPane();
    private ReserveLabel reserveLabel;



    public ActionUtensils(Map<String, String> dictionaryCardUtensils, String keyNameOfCard, ReserveLabel reserve, ConnectionHandler connectionHandler, String selection, SideCardLabel playerSide, AdapterResolution adapterResolution, String bisContent, Boolean isBisEffect) throws IOException {

        //Configurazione della Riserva
        this.reserveLabel = reserve;

        //Configurazione del Parser Jackson per la lettura da file
        InputStream is = getClass().getClassLoader().getResourceAsStream("cardUtensilInfo.json");
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(is);

        //Formattazione per la stampa del nome in ingresso (che è il percorso della carta selezionata)
        String name = setUpperWord(keyNameOfCard);

        //Configurazione Header Title
        VBox headerInfo = new VBox(5);
        headerInfo.setAlignment(Pos.CENTER);
        Label header = setFontStyle(new Label("Hai selezionato: " + name), 35);
        header.setTextAlignment(TextAlignment.CENTER);

        //Configurazione Header Body e importazione delle informazioni sulle varie intestazioni delle finestra dedicate tramite il Parser
        Label cardInfo;
        if(!isBisEffect) cardInfo = setFontStyle(new Label(root.at("/" + name).asText()), 25);
        else cardInfo = setFontStyle(new Label(root.at("/" + name.concat(" " + "Bis")).asText()), 25);
        cardInfo.setTextAlignment(TextAlignment.CENTER);
        headerInfo.getChildren().addAll(header, cardInfo);

        //Estrazione delle informazioni relative all'aativazione delle carte Utensili MultiParamatero
        List<String> infoBis = new ArrayList<>();
        if(isBisEffect) infoBis = ClientMessageParser.getInformationsFromMessage(bisContent);

        //Configurazione Body Content
        HBox bodyContent = new HBox(100);
        bodyContent.setAlignment(Pos.CENTER);
        ImageView cardImage = configureImageView(SUBDIRECTORY,keyNameOfCard,EXTENSION, 300,450);
        SelectorContent selectorContent = new SelectorContent(this.reserveLabel, connectionHandler,selection, playerSide,adapterResolution);
        if(!isBisEffect) bodyContent.getChildren().addAll(cardImage, selectorContent.configureNode(name));
        else bodyContent.getChildren().addAll(cardImage, selectorContent.configureNodeBis(name,infoBis.get(2)));

        //Configurazione End Content
        ImageView continueButton = getContinueButton(180,90);
        continueButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e ->{
            if(!isBisEffect) selectorContent.configureMessage(name, dictionaryCardUtensils, keyNameOfCard);
            else selectorContent.configureMessageBis(name, dictionaryCardUtensils, keyNameOfCard);

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




    /**
     * Metodo Getter che restituisce il riferimento al Parent della finestra configurata per la carta Utensile selezionata
     *
     * @return Riferimento al Parent window
     */

    public StackPane getWindow() {
        return window;
    }


}
