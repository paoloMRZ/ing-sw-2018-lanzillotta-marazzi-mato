package it.polimi.se2018.client.graphic.alert_box;

import it.polimi.se2018.client.graphic.graphic_element.Utility;
import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import static it.polimi.se2018.client.graphic.graphic_element.ButtonLabelCreator.*;
import static it.polimi.se2018.client.graphic.graphic_element.Utility.*;

/**
 * Classe AlertCloseButton utilizzata per generare una finestra di riconferma nel caso si presentasse una richiesta di chiusura della finestra. Permette la scelta
 * di convalidare la propria intenzione di chiudere la finestra o eventualmente di tornare indietro e invalidarla.
 *
 * @author Simone Lanzillotta
 */



public class AlertCloseButton{

    private static boolean answer = false;

    private AlertCloseButton(){}


    /**
     * Metodo utilizzato per richiamare e configurare la schermata di chiusura.
     *
     * @param title Titolo della finestra
     * @param message Intestazione della finestra
     * @return Booleano del valore TRUE qualora si convalidasse la chiusura della finestra, FALSE in caso contrario
     */

    public static boolean display(String title,String message){

        //Configurazione della finestra
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setOnCloseRequest(Event::consume);
        setDecoration(window,title,500,400,15,15);


        //Label "CONTINUE BUTTON" e "BACK BUTTON"
        ImageView continueButton = getContinueButton(178,81);
        ImageView backButton = getBackButton(150,80);
        continueButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            answer = true;
            window.close();
        });

        backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            answer = false;
            window.close();
        });

        VBox layout = new VBox(20);
        HBox layoutButton = setInteractLabel(continueButton,backButton,15);
        layout.getChildren().addAll(setFontStyle(new Label(message),25), layoutButton);
        layout.setAlignment(Pos.CENTER);
        layout.setBackground(configureBackground("back-init-close", 600, 450));
        Scene scene = new Scene(layout);

        window.setScene(scene);
        window.showAndWait();

        return answer;
    }
}
