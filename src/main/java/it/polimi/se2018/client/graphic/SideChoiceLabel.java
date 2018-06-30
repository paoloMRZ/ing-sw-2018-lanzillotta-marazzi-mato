package it.polimi.se2018.client.graphic;

import it.polimi.se2018.client.connection_handler.ConnectionHandler;
import it.polimi.se2018.client.message.ClientMessageCreator;
import it.polimi.se2018.client.graphic.adapter_gui.AdapterResolution;
import it.polimi.se2018.client.graphic.alert_box.AlertLoadingGame;
import it.polimi.se2018.client.graphic.alert_box.AlertValidation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static it.polimi.se2018.client.graphic.Utility.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Classe che configura la scena in cui vengono visualizzate al giocatore le carte Window tra cui diver la scegliere la propria per iniziare la partita. La sua visualizzazione
 * viene interpretata come un messaggio di sincronizzazione tra tutti i partecipanti alla partita. Una volta che tutti i giocatori, avranno scelto la propria carta Window,
 * la partita si avvierà automaticamente.
 *
 * @author Simone Lanzillotta
 */


public class SideChoiceLabel{

    private static final String EXTENSION = ".png";
    private static final String SUBDIRECTORY = "/cardPack/";

    private AnchorPane sideChoise;
    private String numberChoice = null;
    private String nameChoice;
    private String favours;
    private HashMap<StackPane, Boolean> cell = new HashMap<>();
    private Group group = new Group();
    private AdapterResolution adapter;


    /**
     * Costruttore della classe
     *
     * @param sideSelection Riferimento alla collezione di carte Side estratte per la scelta del giocatore
     * @param connectionHandler Riferimento all'oggetto ConnectionHandler rappresentante del giocatore
     * @param adapterResolution Riferimento all'adapter per il dimensionamento
     */

    public SideChoiceLabel(List<String> sideSelection, ConnectionHandler connectionHandler, AdapterResolution adapterResolution){

        //Configurazione adapter per il ridimensionamento
        this.adapter = adapterResolution;

        //Lista per il dimensionamento
        ArrayList<Integer> sizeRect = (ArrayList<Integer>) adapter.getSideChoiceLabelSize().get(0);
        ArrayList<Integer> sizeNode = (ArrayList<Integer>) adapter.getSideChoiceLabelSize().get(1);
        ArrayList<Integer> sizeWindow = (ArrayList<Integer>) adapter.getSideChoiceLabelSize().get(2);


        //Configurazione Focus Style
        Rectangle rect = new Rectangle(20, 20, sizeRect.get(0),sizeRect.get(1));
        rect.setFill(Color.TRANSPARENT);
        rect.setStroke(Color.RED);
        rect.setStrokeWidth(3d);
        group.getChildren().add(rect);

        //Settaggio della finestra
        Label labelRequest = setFontStyle(new Label("Scegli la tua carta Window:"),sizeNode.get(0));
        labelRequest.setAlignment(Pos.CENTER);

        ImageView continueButton = shadowEffect(configureImageView("","button-continue", ".png",416,202));
        continueButton.setFitWidth(sizeNode.get(1));
        continueButton.setFitHeight(sizeNode.get(2));
        continueButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if(numberChoice==null) AlertValidation.display("Sagrada", "Selezionare una carta!");
            else {
                connectionHandler.sendToServer(ClientMessageCreator.getSideReplyMessage(connectionHandler.getNickname(), numberChoice));
                AlertLoadingGame.display("Sagrada", "Attendere la scelta degli\naltri giocatori...");
            }
        });

        Stream<String> firstStream = sideSelection.stream().filter(s -> (sideSelection.indexOf(s) % 2 == 0));
        Stream<String> secondStream = sideSelection.stream().filter(s -> (sideSelection.indexOf(s) % 2 != 0));

        List<String> sides = firstStream.collect(Collectors.toList());
        List<String> numberFavours = secondStream.collect(Collectors.toList());

        HBox firstLevel = configureLevelSide(sides.get(0), "0", numberFavours.get(0), sides.get(1), "1", numberFavours.get(1));
        HBox secondLevel = configureLevelSide(sides.get(2), "2",numberFavours.get(2), sides.get(3),"3", numberFavours.get(3));

        VBox label = new VBox(sizeNode.get(3));

        sideChoise = new AnchorPane();
        sideChoise.setPrefSize(sizeWindow.get(0), sizeWindow.get(1));
        sideChoise.setStyle("-fx-background-image: url(back-choice-side.png); -fx-background-size: contain; -fx-background-position: center; -fx-background-repeat: no-repeat;");

        label.getChildren().addAll(labelRequest,firstLevel,secondLevel,continueButton);
        label.setAlignment(Pos.CENTER);

        configureAnchorPane(sideChoise,label,200d,200d,200d, 200d);
    }




    /**
     * Metodo utilizzato per configurare l'elemento grafico che mostra le carte Side estratte. Si tratta di un metodo che crea un livello con una coppia di Side
     * (a cui assegna rispettivamente il numero corrispondente al suo posizionamento nel deck di carte estratte): richiamato due volte quindi, i due livelli vengono
     * uniti tra di loro e viene composto in seguito l'elemento completo con le quattro Side disponibili.
     *
     * @param firstSide Riferimento alla risorsa corrispondente alla prima Side del livello
     * @param firstUserData Dato associato alla prima Side del livello (posizione nel deck delle carte Side estratte)
     * @param firstFavours Dato relativo ai segnalini Favore della prima Side del livello
     * @param secondSide Riferimento alla risorsa corrispondente alla seconda Side del livello
     * @param secondUserData Dato associato alla seconda Side del livello (posizione nel deck delle carte Side estratte)
     * @param secondFavours Dato relativo ai segnalini Favore della seconda Side del livello
     * @return
     */

    private HBox configureLevelSide(String firstSide, String firstUserData, String firstFavours, String secondSide, String secondUserData, String secondFavours){

        //Lista per il dimensionamento
        ArrayList<Integer> sizeSide = (ArrayList<Integer>) adapter.getSideChoiceLabelSize().get(3);

        StackPane buttonSideFirst = new StackPane();
        StackPane buttonSideSecond = new StackPane();
        HBox hBox = new HBox(sizeSide.get(0));

        ImageView choiceOne = shadowEffect(configureImageView(SUBDIRECTORY, firstSide, EXTENSION, 630,540));
        choiceOne.setFitWidth(sizeSide.get(1));
        choiceOne.setFitHeight(sizeSide.get(2));
        buttonSideFirst.getChildren().add(choiceOne);

        ImageView choiceTwo = shadowEffect(configureImageView(SUBDIRECTORY, secondSide, EXTENSION, 630,540));
        choiceTwo.setFitWidth(sizeSide.get(1));
        choiceTwo.setFitHeight(sizeSide.get(2));
        buttonSideSecond.getChildren().add(choiceTwo);

        choiceOne.setUserData(firstUserData);
        choiceTwo.setUserData(secondUserData);

        cell.put(buttonSideFirst,false);
        cell.put(buttonSideSecond,false);

        choiceOne.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            setFocusStyle(cell,buttonSideFirst,group);
            numberChoice = choiceOne.getUserData().toString();
            nameChoice = firstSide;
            favours = firstFavours;
        });
        choiceTwo.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            setFocusStyle(cell,buttonSideSecond,group);
            numberChoice = choiceTwo.getUserData().toString();
            nameChoice = secondSide;
            favours = secondFavours;
        });

        hBox.getChildren().addAll(buttonSideFirst,buttonSideSecond);
        hBox.setAlignment(Pos.CENTER);
        return hBox;
    }




    /**
     * Metodo Getter che restituisce il nome della carta Side scelta dal giocatore
     *
     * @return Riferimento alla Stringa nameChoice
     */

    public String getNameChoice() {
        return nameChoice;
    }




    /**
     * Metodo Getter che restituisce i segnalini Favore della carta Side scelta dal giocatore
     *
     * @return Riferimento alla Stringa favours
     */

    public String getFavours() {
        return favours;
    }




    /**
     * Metodo Getter che restituisce il nome della carta Side scelta dal giocatore
     *
     * @return Riferimento alla Stringa nameChoice
     */

    public AnchorPane getSideChoise() {
        return sideChoise;
    }

}
