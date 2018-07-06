package it.polimi.se2018.client.graphic.alert_box;

import it.polimi.se2018.client.graphic.graphic_element.Utility;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import static it.polimi.se2018.client.graphic.graphic_element.Utility.*;
import static it.polimi.se2018.client.graphic.graphic_element.ButtonLabelCreator.*;


/**
 * Classe AlertValidation utilizzata per mostrare tramite PoPup messaggi informativi durante la sessione di gioco. In particolare per visualizzare a monitor
 * i seguenti contenuti:
 *
 *  - Notifiche Successo/Errore relativi al gioco (Piazzamenti errati, utilizzi invalidi di carte Utensili ecc.
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
        setDecoration(window,title,500,300,15,15);


        //Formattazione Body
        StackPane stackValidation = new StackPane();
        stackValidation.setPrefSize(500,300);


        Label label = Utility.setFontStyle(new Label(), 25);
        label.setText(message);
        label.setTextAlignment(TextAlignment.CENTER);

        //Configurazione backButton
        ImageView backButton = getBackButton(128,70);
        backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> window.close());


        //Configurazione Cornice della finestra
        stackValidation.getChildren().add(setFrameWindow(stackValidation));

        //Configurazione finela edella finestra
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, backButton);
        layout.setAlignment(Pos.CENTER);
        stackValidation.getChildren().add(layout);
        Scene scene = new Scene(stackValidation);

        window.setScene(scene);
        window.showAndWait();
    }
}
