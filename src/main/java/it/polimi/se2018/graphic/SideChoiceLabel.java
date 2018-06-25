package it.polimi.se2018.graphic;

import it.polimi.se2018.client.connection_handler.ConnectionHandler;
import it.polimi.se2018.client.message.ClientMessageCreator;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import static it.polimi.se2018.graphic.Utility.*;
import java.net.MalformedURLException;
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
    private String numberChoice;
    private String nameChoice;
    private String favours;

    public SideChoiceLabel(List<String> sideSelection, ConnectionHandler connectionHandler) throws MalformedURLException {

        //Settaggio della finestra
        Label labelRequest = setFontStyle(new Label("Scegli la tua carta Window:"),35);
        labelRequest.setAlignment(Pos.CENTER);

        ImageView continueButton = shadowEffect(configureImageView("","button-continue", ".png",250,110));
        continueButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> connectionHandler.sendToServer(ClientMessageCreator.getSideReplyMessage(connectionHandler.getNickname(), numberChoice)));

        Stream<String> firstStream = sideSelection.stream().filter(s -> (sideSelection.indexOf(s) % 2 == 0));
        Stream<String> secondStream = sideSelection.stream().filter(s -> (sideSelection.indexOf(s) % 2 != 0));

        List<String> sides = firstStream.collect(Collectors.toList());
        List<String> numberFavours = secondStream.collect(Collectors.toList());

        HBox firstLevel = configureLevelSide(sides.get(0), "0", numberFavours.get(0), sides.get(1), "1", numberFavours.get(1));
        HBox secondLevel = configureLevelSide(sides.get(2), "2",numberFavours.get(2), sides.get(3),"3", numberFavours.get(3));

        VBox label = new VBox(30);

        sideChoise = new AnchorPane();
        sideChoise.setPrefSize(1000, 820);
        sideChoise.setBackground(configureBackground("back-choice-side",1300,1020));

        label.getChildren().addAll(labelRequest,firstLevel,secondLevel,continueButton);
        label.setAlignment(Pos.CENTER);

        configureAnchorPane(sideChoise,label,200d,200d,200d, 200d);
    }

    public AnchorPane getSideChoise() {
        return sideChoise;
    }

    private HBox configureLevelSide(String firstSide, String firstUserData, String firstFavours, String secondSide, String secondUserData, String secondFavours) throws MalformedURLException {
        HBox hBox = new HBox(70);
        ImageView choiceOne = shadowEffect(configureImageView(SUBDIRECTORY, firstSide, EXTENSION, 360,303));
        choiceOne.setFitWidth(360);
        choiceOne.setFitHeight(303);

        ImageView choiceTwo = shadowEffect(configureImageView(SUBDIRECTORY, secondSide, EXTENSION, 360,303));
        choiceTwo.setFitWidth(360);
        choiceTwo.setFitHeight(303);

        choiceOne.setUserData(firstUserData);
        choiceTwo.setUserData(secondUserData);

        choiceOne.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            numberChoice = choiceOne.getUserData().toString();
            nameChoice = firstSide;
            favours = firstFavours;
        });
        choiceTwo.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            numberChoice = choiceTwo.getUserData().toString();
            nameChoice = secondSide;
            favours = secondFavours;
        });

        hBox.getChildren().addAll(choiceOne,choiceTwo);
        hBox.setAlignment(Pos.CENTER);
        return hBox;
    }

    public String getNameChoice() {
        return nameChoice;
    }

    public String getFavours() {
        return favours;
    }
}
