package it.polimi.se2018.graphic;

import it.polimi.se2018.graphic.alert_box.AlertInfoCard;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static it.polimi.se2018.graphic.Utility.*;

public class CardCreatorLabel{

    //Il nome della carta quando viene ricercata la risorsa devo contenere direttamente in ingresso anche la subDirectory

    private AnchorPane cardObjective;
    private VBox cardObjectiveLabel;
    private ArrayList<String> cardName = new ArrayList<>();
    private HashMap<String, String> dictionaryUtensils;

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

            ImageView objectivePrivate = configureImageView("/cardObjective/", nameOfCard.get(0), ".png", 200, 300);
            objectivePrivate.setFitHeight(280);
            objectivePrivate.setFitWidth(200);
            cardObjectiveLabel.getChildren().addAll(labelName,objectivePrivate);
            cardObjective.getChildren().add(cardObjectiveLabel);

        }

        else{

            cardObjectiveLabel.setPrefSize(200*3,350);
            HBox cardSequence = new HBox(5);
            cardSequence.setPrefSize(200*3,300);
            cardSequence.setAlignment(Pos.CENTER);

            for (String nameCard: nameOfCard) {
                ImageView item = shadowEffect(configureImageView(path, nameCard, ".png", 200, 300));
                item.setFitWidth(150);
                item.setFitHeight(225);
                item.addEventHandler(MouseEvent.MOUSE_CLICKED, e-> AlertInfoCard.display(cardName,path));
                cardSequence.getChildren().add(item);
                cardName.add(nameCard);
            }

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
}
