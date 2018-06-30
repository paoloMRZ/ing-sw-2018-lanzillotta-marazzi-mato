package it.polimi.se2018.client.graphic.alert_box;

import it.polimi.se2018.client.graphic.Utility;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;

import static it.polimi.se2018.client.graphic.Utility.*;

public class AlertValidation{


    public static void display(String title,String message){

        //Formattazione finestra
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.initStyle(StageStyle.TRANSPARENT);
        window.setTitle(title);
        window.setMaxWidth(500);
        window.setMaxHeight(300);
        window.setMinWidth(500);
        window.setMinHeight(300);
        window.getIcons().add(new Image("iconPack/icon-sagrada.png", 10, 10, false, true));


        //Formattazione Body
        StackPane stackValidation = new StackPane();
        stackValidation.setPrefSize(500,300);
        //stackValidation.setStyle("-fx-background-image: url(back-init-close.png); -fx-background-size: auto; -fx-background-position: center; -fx-background-repeat: no-repeat;");

        Label label = Utility.setFontStyle(new Label(), 25);
        label.setText(message);
        label.setTextAlignment(TextAlignment.CENTER);

        ImageView backButton = shadowEffect(configureImageView("","button-back", ".png",128,70));
        backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> window.close());


        //Formattazione cornice
        Rectangle rect = new Rectangle();
        rect.setFill(javafx.scene.paint.Color.TRANSPARENT);
        rect.setStroke(Color.BLACK);
        rect.setStrokeWidth(10d);
        rect.widthProperty().bind(stackValidation.widthProperty());
        rect.heightProperty().bind(stackValidation.heightProperty());
        stackValidation.getChildren().add(rect);


        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, backButton);
        layout.setAlignment(Pos.CENTER);
        stackValidation.getChildren().add(layout);
        Scene scene = new Scene(stackValidation);

        window.setScene(scene);
        window.showAndWait();
    }
}
