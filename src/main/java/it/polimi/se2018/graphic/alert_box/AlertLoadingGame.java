package it.polimi.se2018.graphic.alert_box;

import it.polimi.se2018.graphic.Utility;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class AlertLoadingGame {

        public static void display(String title,String message){

            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle(title);
            window.setWidth(450);
            window.setHeight(250);
            window.initStyle(StageStyle.TRANSPARENT);
            Label label = Utility.setFontStyle(new Label(), 20);
            label.setText(message);
            label.setTextAlignment(TextAlignment.CENTER);

            ImageView loading = new ImageView(new Image("big-ajax-loader.gif",50,50,false,true));

            HBox layuot = new HBox(10);
            layuot.getChildren().addAll(loading, label);
            layuot.setAlignment(Pos.CENTER);

            VBox finalLayout = new VBox(15);
            finalLayout.setAlignment(Pos.CENTER);
            finalLayout.setPadding(new Insets(50,50,50,50));

            finalLayout.getChildren().addAll(layuot);
            Scene scene = new Scene(finalLayout);

            window.setScene(scene);
            window.showAndWait();
        }

}
