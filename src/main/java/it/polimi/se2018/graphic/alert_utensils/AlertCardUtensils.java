package it.polimi.se2018.graphic.alert_utensils;

import it.polimi.se2018.client.connection_handler.ConnectionHandler;
import it.polimi.se2018.graphic.alert_box.AlertValidation;
import it.polimi.se2018.graphic.ReserveLabel;
import it.polimi.se2018.graphic.SideCardLabel;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static it.polimi.se2018.graphic.Utility.*;

public class AlertCardUtensils {

    private static String selection;
    private static HashMap<StackPane, Boolean> cardHash = new HashMap<>();
    private static Group groupCard;
    private static ArrayList<String> nameOfCard = new ArrayList<>();

    public static void display(String title, String message, List<String> cardUtensils, ConnectionHandler connectionHandler, ReserveLabel reserve, Boolean useUtensils, SideCardLabel playerSide){

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setWidth(1200);
        window.setHeight(900);

        StackPane baseWindow = new StackPane();

        groupCard = new Group();
        Rectangle rect = new Rectangle(20, 20, 302, 452);
        rect.setFill(Color.TRANSPARENT);
        rect.setStroke(Color.RED);
        rect.setStrokeWidth(5d);
        groupCard.getChildren().add(rect);

        ImageView backWindow = configureImageView("cardUtensils/retro", 754,1049);
        backWindow.setFitHeight(1200);
        backWindow.setFitWidth(1500);
        backWindow.setOpacity(0.4);



        //Label Text
        Label labelText = setFontStyle(new Label(message),40);
        labelText.setAlignment(Pos.CENTER);

        //Layout Button
        ImageView backButton = shadowEffect(configureImageView("button-back",160,80));
        ImageView continueButton = shadowEffect(configureImageView("button-continue",180,80));
        backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> window.close());
        continueButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {

            //connectionHandler.sendToServer(ClientMessageCreator.getUseUtensilMessage(connectionHandler.getNickname(), selection, null));

            if(useUtensils) {
                ActionUtensils actionUtensils = new ActionUtensils(String.valueOf(cardUtensils.get(Integer.parseInt(selection))), reserve, connectionHandler,selection, playerSide);
                Scene scene = new Scene(actionUtensils.getWindow());
                window.setWidth(1200);
                window.setHeight(900);
                window.centerOnScreen();
                window.setScene(scene);
            }

            else {
                AlertValidation.display("Errore", "Non puoi usare questa carta!");
                window.close();
            }
        });

        HBox layuotButton = new HBox(25);
        layuotButton.getChildren().addAll(backButton, continueButton);
        layuotButton.setAlignment(Pos.CENTER);

        //Layout card
        HBox layoutCard = new HBox(40);
        layoutCard.setAlignment(Pos.CENTER);

        for (String card:cardUtensils) {
            VBox vbox = new VBox(10);
            StackPane node = new StackPane();
            node.setPrefSize(302,452);

            ImageView item = shadowEffect(configureImageView(card,302,452));
            cardHash.put(node, false);
            item.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {

                if(!cardHash.get(node)){
                    node.getChildren().add(groupCard);
                    cardHash.replace(node, false, true);
                }
                else {
                    node.getChildren().remove(groupCard);
                    cardHash.replace(node, true, false);
                }

                selection = String.valueOf(cardUtensils.indexOf(card));
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
        layoutFinal.getChildren().addAll(labelText, layoutCard, layuotButton);

        baseWindow.getChildren().addAll(backWindow, layoutFinal);

        Scene scene = new Scene(baseWindow);
        window.setScene(scene);
        window.showAndWait();
    }

}
