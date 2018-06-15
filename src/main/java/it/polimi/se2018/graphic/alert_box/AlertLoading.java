package it.polimi.se2018.graphic.alert_box;

import it.polimi.se2018.client.message.ClientMessageParser;
import it.polimi.se2018.graphic.InitWindow;
import it.polimi.se2018.graphic.Utility;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class AlertLoading{

    public static void display(String title, String messagge, InitWindow init){

        Stage window = new Stage();
        init.getMessage().textProperty().addListener((obs, oldText, newText) -> {
            if(!init.getMessage().getText().trim().isEmpty() && ClientMessageParser.isStartChoseSideMessage(init.getMessage().getText())) window.close();
        });

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setWidth(550);
        window.setHeight(480);
        window.setOnCloseRequest(e -> init.getConnectionHandler().sendToServer("/" + init.getConnectionHandler().getNickname() + "/###/rete/?/disconnect\n"));

        Label label = Utility.setFontStyle(new Label(),25);
        label.setText(messagge);

        ImageView loading = new ImageView(new Image("resources/big-ajax-loader.gif",50,50,false,true));

        HBox layuot = new HBox(10);
        layuot.getChildren().addAll(loading, label);
        layuot.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layuot);
        window.setScene(scene);
        window.showAndWait();
    }
}
