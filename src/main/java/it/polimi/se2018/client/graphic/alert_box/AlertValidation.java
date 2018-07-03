package it.polimi.se2018.client.graphic.alert_box;

import it.polimi.se2018.client.graphic.graphic_element.ButtonLabelCreator;
import it.polimi.se2018.client.graphic.graphic_element.Utility;
import javafx.geometry.Pos;
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
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import static it.polimi.se2018.client.graphic.graphic_element.Utility.*;


/**
 * Classe AlertValidation utilizzata per mostrare tramite PoPup messaggi informativi durante la sessione di gioco. In particolare per visualizzare a monitor
 * i seguenti contenuti:
 *
 *  -> Notifiche Successo/Errore relativi al gioco (Piazzamenti errati, utilizzi invalidi di carte Utensili ecc.
 *
 * @author Simone Lanzillotta
 */



public class AlertValidation{


    private AlertValidation(){}


    /**
     * Metodo statico utilizzato per configurare il contenuto del PoPup sollevato durante la sessione di gioco.
     *
     * @param title Titolo del PoPup
     * @param message Contenuto informativo
     */


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


        Label label = Utility.setFontStyle(new Label(), 25);
        label.setText(message);
        label.setTextAlignment(TextAlignment.CENTER);

        //Configurazione backButton
        ButtonLabelCreator buttonLabelCreator = new ButtonLabelCreator(128,70);
        buttonLabelCreator.getBackButton().addEventHandler(MouseEvent.MOUSE_CLICKED, e -> window.close());


        //Configurazione Cornice della finestra
        stackValidation.getChildren().add(setFrameWindow(stackValidation));


        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, buttonLabelCreator.getBackButton());
        layout.setAlignment(Pos.CENTER);
        stackValidation.getChildren().add(layout);
        Scene scene = new Scene(stackValidation);

        window.setScene(scene);
        window.showAndWait();
    }
}
