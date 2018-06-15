package it.polimi.se2018.graphic;

import it.polimi.se2018.client.connection_handler.ConnectionHandler;
import it.polimi.se2018.client.message.ClientMessageCreator;
import it.polimi.se2018.graphic.alert_utensils.AlertCardUtensils;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import static it.polimi.se2018.graphic.Utility.*;

public class ButtonGameLabel {

    private VBox labelButtonGame;
    private ImageView buttonGame;
    private ImageView buttonUtensils;
    private ImageView buttonTurn;
    private Boolean useUtensils = true;


    public ButtonGameLabel(ConnectionHandler connectionHandler, ReserveLabel reserve, SideCardLabel playerSide, CardCreatorLabel cardUtensils){
        buttonGame = shadowEffect(configureImageView("button-game-die", 352, 104));
        buttonGame.setFitWidth(250);
        buttonGame.setFitHeight(80);
        buttonGame.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            connectionHandler.sendToServer(ClientMessageCreator.getPutDiceMessage(connectionHandler.getNickname(), reserve.getPos(), playerSide.getPosX(), playerSide.getPosY()));
        });

        buttonUtensils = Utility.shadowEffect(configureImageView("button-game-utensil", 352, 104));
        buttonUtensils.setFitWidth(250);
        buttonUtensils.setFitHeight(80);
        buttonUtensils.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> AlertCardUtensils.display("Sagrada", "Scegli al carta che vuoi attivare", cardUtensils.getCardName(), connectionHandler, reserve, useUtensils, playerSide));

        buttonTurn = Utility.shadowEffect(configureImageView("button-game-turn", 352, 104));
        buttonTurn.setFitWidth(250);
        buttonTurn.setFitHeight(80);
        buttonTurn.addEventHandler(MouseEvent.MOUSE_CLICKED, e ->{
            connectionHandler.sendToServer(ClientMessageCreator.getPassTurnMessage(connectionHandler.getNickname()));
        });


        labelButtonGame = new VBox(20);
        labelButtonGame.setAlignment(Pos.CENTER);

        HBox labelButton = new HBox(40);
        labelButton.setAlignment(Pos.CENTER);
        labelButton.getChildren().addAll(buttonGame, buttonUtensils);

        labelButtonGame.getChildren().addAll(buttonTurn, labelButton);
    }

    public VBox getLabelButtonGame() {
        return labelButtonGame;
    }

    public void setUseUtensils(boolean value){
        this.useUtensils = value;
    }

}
