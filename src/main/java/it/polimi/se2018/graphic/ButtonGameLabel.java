package it.polimi.se2018.graphic;

import it.polimi.se2018.client.connection_handler.ConnectionHandler;
import it.polimi.se2018.client.message.ClientMessageCreator;
import it.polimi.se2018.graphic.alert_box.AlertValidation;
import it.polimi.se2018.graphic.alert_utensils.AlertCardUtensils;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import static it.polimi.se2018.graphic.Utility.*;

public class ButtonGameLabel {

    private static final String EXTENSION = ".png";
    private static final String SUBDIRECTORY = "";

    private VBox labelButtonGame;
    private ImageView buttonGame;
    private ImageView buttonUtensils;
    private ImageView buttonTurn;
    private AlertCardUtensils alertCardUtensils;


    public ButtonGameLabel(ConnectionHandler connectionHandler, ReserveLabel reserve, SideCardLabel playerSide, CardCreatorLabel cardUtensils){


        alertCardUtensils = new AlertCardUtensils(cardUtensils,connectionHandler,reserve,playerSide);
        buttonGame = shadowEffect(configureImageView(SUBDIRECTORY,"button-game-die", EXTENSION,352, 104));
        buttonGame.setFitWidth(180);
        buttonGame.setFitHeight(60);
        buttonGame.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if(reserve.getPos() != null) connectionHandler.sendToServer(ClientMessageCreator.getPutDiceMessage(connectionHandler.getNickname(), reserve.getPos(), playerSide.getPosX(), playerSide.getPosY()));
            else AlertValidation.display("Errore", "Non hai selezionato nella riserva\nil dado da inserire!");
        });

        buttonUtensils = shadowEffect(configureImageView(SUBDIRECTORY,"button-game-utensil",EXTENSION, 352, 104));
        buttonUtensils.setFitWidth(180);
        buttonUtensils.setFitHeight(60);
        buttonUtensils.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> alertCardUtensils.display("Sagrada", "Scegli al carta che vuoi attivare"));

        buttonTurn = shadowEffect(configureImageView(SUBDIRECTORY,"button-game-turn",EXTENSION, 352, 104));
        buttonTurn.setFitWidth(180);
        buttonTurn.setFitHeight(60);
        buttonTurn.addEventHandler(MouseEvent.MOUSE_CLICKED, e ->{
            connectionHandler.sendToServer(ClientMessageCreator.getPassTurnMessage(connectionHandler.getNickname()));
        });


        labelButtonGame = new VBox(10);
        labelButtonGame.setAlignment(Pos.CENTER);

        HBox labelButton = new HBox(30);
        labelButton.setAlignment(Pos.CENTER);
        labelButton.getChildren().addAll(buttonGame, buttonUtensils);

        labelButtonGame.getChildren().addAll(buttonTurn, labelButton);
    }

    public VBox getLabelButtonGame() {
        return labelButtonGame;
    }

    public AlertCardUtensils getAlertCardUtensils() {
        return alertCardUtensils;
    }

    public void checkPermission(String nickname, String turnOf){

            if (!nickname.equals(turnOf)) {
                buttonGame.setDisable(true);
                buttonUtensils.setDisable(true);
                buttonTurn.setDisable(true);
                buttonGame.setOpacity(0.7);
                buttonUtensils.setOpacity(0.7);
                buttonTurn.setOpacity(0.7);
            } else {
                buttonGame.setDisable(false);
                buttonUtensils.setDisable(false);
                buttonTurn.setDisable(false);
                buttonGame.setOpacity(1.0);
                buttonUtensils.setOpacity(1.0);
                buttonTurn.setOpacity(1.0);
            }
    }
}
