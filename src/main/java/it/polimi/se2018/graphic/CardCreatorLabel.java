package it.polimi.se2018.graphic;

import it.polimi.se2018.graphic.alert_box.AlertInfoCard;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import java.util.ArrayList;
import java.util.List;

import static it.polimi.se2018.graphic.Utility.*;

public class CardCreatorLabel{

    private AnchorPane cardObjective;
    private VBox cardObjectiveLabel;
    private ArrayList<String> cardName = new ArrayList<>();

    public CardCreatorLabel(List<String> nameOfCard, boolean isPrivate, String path) {

        cardObjectiveLabel = new VBox(7);
        cardObjectiveLabel.setAlignment(Pos.CENTER);
        cardObjective = new AnchorPane();
        Label labelName = setFontStyle(new Label(), 25);
        labelName.setPrefSize(270,50);


        if(isPrivate){
            cardObjectiveLabel.setPrefSize(270,420);
            labelName.setText("Obbiettivo Privato");
            labelName.setAlignment(Pos.CENTER);

            ImageView objectivePrivate = configureImageView("cardObjective/" + nameOfCard.get(0), 270, 370);
            cardObjectiveLabel.getChildren().addAll(labelName,objectivePrivate);

            cardObjective.setPrefSize(270,370);
            cardObjective.getChildren().add(cardObjectiveLabel);

        }

        else{

            cardObjectiveLabel.setPrefSize(200*3,350);
            HBox cardSequence = new HBox(5);
            cardSequence.setPrefSize(200*3,300);
            cardSequence.setAlignment(Pos.CENTER);

            for (String nameCard: nameOfCard) {
                ImageView item = shadowEffect(configureImageView(nameCard, 200, 300));
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
}
