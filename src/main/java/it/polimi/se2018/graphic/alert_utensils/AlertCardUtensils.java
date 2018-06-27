package it.polimi.se2018.graphic.alert_utensils;

import it.polimi.se2018.client.connection_handler.ConnectionHandler;
import it.polimi.se2018.client.message.ClientMessageCreator;
import it.polimi.se2018.graphic.CardCreatorLabel;
import it.polimi.se2018.graphic.ReserveLabel;
import it.polimi.se2018.graphic.SideCardLabel;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static it.polimi.se2018.graphic.Utility.*;


/**
 * Classe che rappresenta un ALertBox relativo alle carte Utensili. A seguito del click sul pulsante "USA UTENSILE" del Parent, viene visualizzata una finestra in cui l'utente può
 * interagire con le carte Utensili disponibili nella partita, scegliendone poi una per la sua attivazione.
 *
 * @author  Simone Lanzillotta
 *
 */


public class AlertCardUtensils {

    private static final String EXTENSION = ".png";
    private static final String SUBDIRECTORY = "";

    private String selection;
    private HashMap<StackPane, Boolean> cardHash = new HashMap<>();
    private Group groupCard;

    private CardCreatorLabel cardUtensils;
    private ConnectionHandler connectionHandler;
    private ReserveLabel reserve;
    private SideCardLabel playerSide;
    private Stage window;


    /**
     * Costruttore della classe
     *
     * @param cardUtensils Riferimento alla collezione di carte Utensili disponibile nella partita
     * @param connectionHandler Riferimento all'oggetto ConnectionHandler rappresentante il giocatore
     * @param reserve Riferimento alla riserva attuale
     * @param playerSide Riferimento alla carta Side del giocatore
     */

    public AlertCardUtensils(CardCreatorLabel cardUtensils, ConnectionHandler connectionHandler, ReserveLabel reserve, SideCardLabel playerSide){
        this.cardUtensils = cardUtensils;
        this.connectionHandler = connectionHandler;
        this.reserve = reserve;
        this.playerSide = playerSide;
    }


    /**
     * Metodo che configura la finestra che permette la visualizzazione delle carte Utensili disponibili
     *
     * @param title Titolo della finestra
     * @param message Header della finestra
     */

    public void display(String title, String message){


        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setWidth(1100);
        window.setHeight(800);

        StackPane baseWindow = new StackPane();

        groupCard = new Group();
        Rectangle rect = new Rectangle(20, 20, 302, 452);
        rect.setFill(Color.TRANSPARENT);
        rect.setStroke(Color.RED);
        rect.setStrokeWidth(5d);
        groupCard.getChildren().add(rect);

        ImageView backWindow = configureImageView("/cardUtensils/","retro", EXTENSION,  754,1049);
        backWindow.setFitHeight(1200);
        backWindow.setFitWidth(1500);
        backWindow.setOpacity(0.4);


        //Label Text
        Label labelText = setFontStyle(new Label(message),35);
        labelText.setAlignment(Pos.CENTER);


        //Layout Button
        ImageView backButton = shadowEffect(configureImageView(SUBDIRECTORY,"button-back",EXTENSION,160,80));
        ImageView continueButton = shadowEffect(configureImageView(SUBDIRECTORY,"button-continue",EXTENSION,180,80));
        backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> window.close());
        continueButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> connectionHandler.sendToServer(ClientMessageCreator.getActivateUtensilMessage(connectionHandler.getNickname(), selection)));
        HBox layoutButton = new HBox(25);
        layoutButton.getChildren().addAll(backButton, continueButton);
        layoutButton.setAlignment(Pos.CENTER);


        //Layout card
        HBox layoutCard = new HBox(40);
        layoutCard.setAlignment(Pos.CENTER);

        for (String card:cardUtensils.getKeyName()) {
            VBox vbox = new VBox(10);
            StackPane node = new StackPane();
            node.setPrefSize(302,452);

            ImageView item = shadowEffect(configureImageView("/cardUtensils/",card,EXTENSION,270,402));
            cardHash.put(node, false);
            item.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                setFocusStyle(cardHash,node,groupCard);
                selection = String.valueOf(cardUtensils.getCardName().indexOf(card));
            });

            //TODO: modificare aggiornamento con listener
            item.setUserData("1");
            node.getChildren().add(item);
            Label costFavours = setFontStyle(new Label("Costo: " + item.getUserData().toString()),40);
            costFavours.setAlignment(Pos.CENTER);
            vbox.getChildren().addAll(node, costFavours);
            vbox.setAlignment(Pos.CENTER);
            layoutCard.getChildren().add(vbox);
        }


        //Layout finale
        VBox layoutFinal = new VBox(37);
        layoutFinal.setAlignment(Pos.CENTER);
        layoutFinal.getChildren().addAll(labelText, layoutCard, layoutButton);

        baseWindow.getChildren().addAll(backWindow, layoutFinal);

        Scene scene = new Scene(baseWindow);
        window.setScene(scene);
        window.showAndWait();
    }


    /**
     * Metodo richiamato a seguito della convalida da parte del server dell'utilizo della carta Utensile selezionata
     *
     */

    public void launchExecutionUtensil(){
        ActionUtensils actionUtensils = null;
        try {
            actionUtensils = new ActionUtensils(cardUtensils.getDictionaryUtensils(),String.valueOf(cardUtensils.getKeyName().get(Integer.parseInt(selection))), reserve, connectionHandler,selection, playerSide, window);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        Scene scene = new Scene(actionUtensils.getWindow());
        window.setWidth(1200);
        window.setHeight(900);
        window.centerOnScreen();

        Platform.runLater(() -> window.setScene(scene));

    }

}
