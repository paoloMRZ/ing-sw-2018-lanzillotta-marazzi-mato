package it.polimi.se2018.graphic;

import it.polimi.se2018.graphic.alert_box.AlertInfoCard;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.util.*;
import java.util.stream.Collectors;

import static it.polimi.se2018.graphic.Utility.*;

public class CardCreatorLabel{

    private static final String EXTENSION = ".png";
    private static final String MATCHDIRECTORY = "/cardUtensils/";
    private AnchorPane cardObjective;
    private VBox cardObjectiveLabel;
    private ArrayList<String> cardName = new ArrayList<>();
    private HashMap<String, String> dictionaryUtensils;
    private List <String> valueNumber;
    private List <String> keyName;

    public CardCreatorLabel(List<String> nameOfCard, Map<String, String> dictionaryUtensils, boolean isPrivate, String path) {

        this.dictionaryUtensils = (HashMap<String, String>)dictionaryUtensils;
        cardObjectiveLabel = new VBox(0);
        cardObjectiveLabel.setAlignment(Pos.CENTER);
        cardObjective = new AnchorPane();
        Label labelName = setFontStyle(new Label(), 15);
        labelName.setPrefSize(270,50);


        if(isPrivate){
            cardObjectiveLabel.setPrefSize(200,330);
            labelName.setText("Obbiettivo Privato");
            labelName.setAlignment(Pos.CENTER);

            ImageView objectivePrivate = configureImageView("/cardObjective/", nameOfCard.get(0), EXTENSION, 200, 300);
            objectivePrivate.setFitHeight(280);
            objectivePrivate.setFitWidth(200);
            cardObjectiveLabel.getChildren().addAll(labelName,objectivePrivate);
            cardObjective.getChildren().add(cardObjectiveLabel);

        }

        else{

            cardObjectiveLabel.setPrefSize(200*3,350);
            HBox cardSequence = configureCardSequence(path,nameOfCard,200,300);
            cardSequence.setPrefSize(200*3,300);
            cardSequence.setAlignment(Pos.CENTER);

            cardObjectiveLabel.getChildren().addAll(cardSequence);
            configureAnchorPane(cardObjective,cardObjectiveLabel,0d,0d,0d,0d);
        }
    }


    public AnchorPane getCardObjective() {
        return cardObjective;
    }

    public List<String> getCardName() {
        return cardName;
    }

    public Map<String, String> getDictionaryUtensils() {
        return dictionaryUtensils;
    }

    private HBox configureCardSequence(String path, List<String> nameOfCard, int requestedWidth, int requestedHeight){

        ArrayList<String> tempCollection = new ArrayList<>(nameOfCard);

        if(nameOfCard.size()>3){
            keyName = nameOfCard.stream().filter(s -> (nameOfCard.indexOf(s) % 2 == 0)).collect(Collectors.toList());
            valueNumber = nameOfCard.stream().filter(s -> (nameOfCard.indexOf(s) % 2 != 0)).collect(Collectors.toList());
            tempCollection.clear();
            tempCollection.addAll(keyName);
        }

        HBox cardSequence = new HBox(5);
        for (String nameCard: tempCollection) {
            ImageView item = shadowEffect(configureImageView(path, nameCard, EXTENSION, requestedWidth, requestedHeight));
            item.setFitWidth(150);
            item.setFitHeight(225);
            item.addEventHandler(MouseEvent.MOUSE_CLICKED, e-> {
                if(nameOfCard.size()>3) AlertInfoCard.display(keyName,path);
                else AlertInfoCard.display(cardName,path);
            });
            cardSequence.getChildren().add(item);
            cardName.add(nameCard);
            if(nameOfCard.size()>3) cardName.add(valueNumber.get(tempCollection.indexOf(nameCard)));
        }

        return cardSequence;
    }


    public List<String> getKeyName() {
        return keyName;
    }
}
