package it.polimi.se2018.graphic.alert_box;

import it.polimi.se2018.client.message.ClientMessageCreator;
import it.polimi.se2018.client.message.ClientMessageParser;
import it.polimi.se2018.graphic.InitWindow;
import it.polimi.se2018.graphic.Utility;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static it.polimi.se2018.graphic.Utility.configureImageView;
import static it.polimi.se2018.graphic.Utility.shadowEffect;

public class SceneLoading {

    private Scene sceneLoading;

    public SceneLoading(Stage window, InitWindow init){

        //Label text di caricamento
        Label label = Utility.setFontStyle(new Label("Sei stato messo in attesa..."),25);
        ImageView loading = new ImageView(new Image("big-ajax-loader.gif",50,50,false,true));

        HBox layuot = new HBox(10);
        layuot.getChildren().addAll(loading, label);
        layuot.setAlignment(Pos.CENTER);

        VBox finalLayout = new VBox(15);
        finalLayout.setAlignment(Pos.CENTER);
        finalLayout.setPadding(new Insets(50,50,50,50));
        ImageView backButton = shadowEffect(configureImageView("","button-back", ".png",138,71));
        backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            init.getConnectionHandler().sendToServer(ClientMessageCreator.getDisconnectMessage(init.getConnectionHandler().getNickname()));
            Platform.runLater(() -> window.setScene(AlertSwitcher.getSceneNickName()));

        });
        finalLayout.getChildren().addAll(layuot,backButton);


        sceneLoading = new Scene(finalLayout,480,350);
    }

    public Scene getSceneLoading() {
        return sceneLoading;
    }
}
