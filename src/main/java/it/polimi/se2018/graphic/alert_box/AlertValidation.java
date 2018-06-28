package it.polimi.se2018.graphic.alert_box;

import it.polimi.se2018.graphic.Utility;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

import static it.polimi.se2018.graphic.Utility.*;

public class AlertValidation{


    public static void display(String title,String message){

        //Formattazione finestra
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setWidth(400);
        window.setHeight(300);
        window.getIcons().add(new Image("iconPack/icon-sagrada.png", 10, 10, false, true));

        //Formattazione Body
        StackPane stackValidation = new StackPane();
        stackValidation.setStyle("-fx-background-image: url(back-init-close.png); -fx-background-size: auto; -fx-background-position: center; -fx-background-repeat: no-repeat;");

        Label label = Utility.setFontStyle(new Label(), 25);
        label.setText(message);
        label.setTextAlignment(TextAlignment.CENTER);

        ImageView backButton = shadowEffect(configureImageView("","button-back", ".png",128,61));
        backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> window.close());




        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, backButton);
        layout.setAlignment(Pos.CENTER);
        stackValidation.getChildren().add(layout);
        Scene scene = new Scene(stackValidation);

        window.setScene(scene);
        window.showAndWait();
    }
}
