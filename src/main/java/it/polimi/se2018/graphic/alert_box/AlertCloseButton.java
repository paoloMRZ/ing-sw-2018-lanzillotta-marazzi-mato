package it.polimi.se2018.graphic.alert_box;

import it.polimi.se2018.graphic.Utility;
import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import static it.polimi.se2018.graphic.Utility.*;


public class AlertCloseButton{

    private static boolean answer = false;

    public static boolean display(String title,String messagge){

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setOnCloseRequest(Event::consume);
        window.setTitle(title);
        window.setWidth(450);
        window.setHeight(350);
        Label label = Utility.setFontStyle(new Label(),25);
        label.setText(messagge);

        ImageView continueButton = shadowEffect(configureImageView("","button-continue", ".png", 178, 81));
        ImageView backButton = shadowEffect(configureImageView("","button-back", ".png", 150, 80));

        continueButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            answer = true;
            window.close();
        });

        backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            answer = false;
            window.close();
        });

        VBox layout = new VBox(10);
        HBox layoutButton = new HBox(continueButton,backButton);
        layoutButton.setSpacing(15d);
        layoutButton.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(label, layoutButton);
        layout.setAlignment(Pos.CENTER);
        layout.setBackground(configureBackground("back-init-close", 600, 450));
        Scene scene = new Scene(layout);

        window.setScene(scene);
        window.showAndWait();

        return answer;
    }
}
