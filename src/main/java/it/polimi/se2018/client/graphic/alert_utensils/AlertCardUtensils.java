package it.polimi.se2018.client.graphic.alert_utensils;

import it.polimi.se2018.client.connection_handler.ConnectionHandler;
import it.polimi.se2018.client.message.ClientMessageCreator;
import it.polimi.se2018.client.graphic.CardCreatorLabel;
import it.polimi.se2018.client.graphic.ReserveLabel;
import it.polimi.se2018.client.graphic.SideCardLabel;
import it.polimi.se2018.client.graphic.adapter_gui.AdapterResolution;
import javafx.application.Platform;
import javafx.event.Event;
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
import org.fusesource.jansi.AnsiConsole;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static it.polimi.se2018.client.graphic.graphic_element.ButtonLabelCreator.*;
import static it.polimi.se2018.client.graphic.graphic_element.Utility.*;


/**
 * Classe che rappresenta un ALertBox relativo alle carte Utensili. A seguito del click sul pulsante "USA UTENSILE" del Parent, viene visualizzata una finestra in cui l'utente può
 * interagire con le carte Utensili disponibili nella partita, scegliendone poi una per la sua attivazione.
 *
 * @author  Simone Lanzillotta
 *
 */


public class AlertCardUtensils{

    //Costanti di intestazione delle varie finestra
    private static final String EXTENSION = ".png";

    private String selection;
    private HashMap<StackPane, Boolean> cardHash = new HashMap<>();
    private Group groupCard;

    private CardCreatorLabel cardUtensils;
    private ConnectionHandler connectionHandler;
    private ReserveLabel reserve;
    private SideCardLabel playerSide;
    private Stage window;
    private AdapterResolution adapter;
    private ArrayList<String> infoCostHistory;
    private HBox cardSelection;


    /**
     * Costruttore della classe
     *
     * @param cardUtensils Riferimento alla collezione di carte Utensili disponibile nella partita
     * @param connectionHandler Riferimento all'oggetto ConnectionHandler rappresentante il giocatore
     * @param reserve Riferimento alla riserva attuale
     * @param playerSide Riferimento alla carta Side del giocatore
     * @param updateCostUtensil Riferimento al costo delle utensili aggiornato
     * @param adapterResolution Riferimento all'adapter per il dimensionamento
     */

    public AlertCardUtensils(CardCreatorLabel cardUtensils, ConnectionHandler connectionHandler, ReserveLabel reserve, SideCardLabel playerSide, List<String> updateCostUtensil, AdapterResolution adapterResolution){
        this.cardUtensils = cardUtensils;
        this.connectionHandler = connectionHandler;
        this.reserve = reserve;
        this.playerSide = playerSide;
        this.adapter = adapterResolution;
        this.infoCostHistory = new ArrayList<>(updateCostUtensil);
    }


    /**
     * Metodo che configura la finestra che permette la visualizzazione delle carte Utensili disponibili
     *
     * @param title Titolo della finestra
     * @param message Header della finestra
     */

    public void display(String title, String message){

        //Configurazione della finestra visualizzata
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setOnCloseRequest(Event::consume);
        setDecoration(window, title, 1200,900,15,15);

        StackPane baseWindow = new StackPane();

        groupCard = new Group();
        Rectangle rect = new Rectangle(20, 20, 280, 402);
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


        //Label "CONTINUE BUTTON" e "BACK BUTTON"
        ImageView continueButton = getContinueButton(180,80);
        ImageView backButton = getBackButton(160,80);

        backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> window.close());
        continueButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> connectionHandler.sendToServer(ClientMessageCreator.getActivateUtensilMessage(connectionHandler.getNickname(), selection)));
        HBox layoutButton = setInteractLabel(continueButton,backButton,25);


        //Layout card
        setCardCollection();


        //Layout finale
        VBox layoutFinal = new VBox(37);
        layoutFinal.setAlignment(Pos.CENTER);
        layoutFinal.getChildren().addAll(labelText, cardSelection, layoutButton);

        baseWindow.getChildren().addAll(backWindow, layoutFinal);

        Scene scene = new Scene(baseWindow);
        window.setScene(scene);
        window.showAndWait();
    }


    /**
     * Metodo richiamato a seguito della convalida da parte del server dell'utilizzo della carta Utensile selezionata
     *
     * @param isBisActivate Booleano del valore TRUE se è necessario attivare una bis Utensile, altrimenti FALSE
     * @param bisContent Eventuale contenuto per l'utilizzo della carta Utensile Bis
     */

    public void launchExecutionUtensil(Boolean isBisActivate, String bisContent){
        ActionUtensils actionUtensils = null;
        try {
            actionUtensils = new ActionUtensils(cardUtensils.getDictionaryUtensils(),String.valueOf(cardUtensils.getKeyName().get(Integer.parseInt(selection))),reserve,connectionHandler,selection, playerSide,adapter,bisContent,isBisActivate);
        } catch (IOException e1) {
            AnsiConsole.out().println(e1.toString());
        }

        window.setWidth(1200);
        window.setHeight(900);
        window.centerOnScreen();


        Scene scene = null;
        if(actionUtensils!=null) scene = new Scene(actionUtensils.getWindow());
        Scene finalScene = scene;
        Platform.runLater(() -> window.setScene(finalScene));

    }


    /**
     * Metodo utilizzato per chiudere la finestra di interazione con le carte Utensili
     *
     */

    public void closeExecutionUtensil(){
        window.close();
    }



    /**
     * Metodo utlizzato per la configurazione dell'elemento grafico contenente le carte Utensile utilizzabili per la partita corrente
     *
     */

    private void setCardCollection(){

        //Configurazione elemento Grafico che conterrà le immagini delle carte Utensili estratte
        cardSelection = new HBox(40);
        cardSelection.setAlignment(Pos.CENTER);
        Label costFavours;

        for (String card:cardUtensils.getKeyName()) {
            VBox vbox = new VBox(10);
            StackPane node = new StackPane();
            node.setPrefSize(302,452);

            ImageView item = shadowEffect(configureImageView("/cardUtensils/",card,EXTENSION,280,402));
            cardHash.put(node, false);
            item.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                setFocusStyle(cardHash,node,groupCard);
                selection = String.valueOf(cardUtensils.getKeyName().indexOf(card));
            });

            item.setUserData(String.valueOf(infoCostHistory.get(cardUtensils.getKeyName().indexOf(card))));
            costFavours = setFontStyle(new Label("Costo: " + item.getUserData().toString()),40);
            costFavours.setAlignment(Pos.CENTER);

            node.getChildren().add(item);
            vbox.getChildren().addAll(node, costFavours);
            vbox.setAlignment(Pos.CENTER);
            cardSelection.getChildren().add(vbox);
        }


    }


    /**
     * Metodo Getter utilizzato per accedere all'informazione sulla selezione del giocatore
     *
     * @return Riferimento all'attributo selection
     */

    public String getSelection(){
        return selection;
    }
}
