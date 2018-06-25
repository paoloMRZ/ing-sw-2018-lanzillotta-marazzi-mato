package it.polimi.se2018.graphic.alert_box;

import it.polimi.se2018.graphic.Utility;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

import static it.polimi.se2018.graphic.Utility.*;

public class AlertValidation{


    public static void display(String title,String message){

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setWidth(350);
        window.setHeight(250);
        Label label = Utility.setFontStyle(new Label(), 20);
        label.setText(message);
        label.setTextAlignment(TextAlignment.CENTER);

        ImageView backButton = shadowEffect(configureImageView("","button-back", ".png",128,61));
        backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, backButton);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);

        window.setScene(scene);
        window.showAndWait();
    }
}
