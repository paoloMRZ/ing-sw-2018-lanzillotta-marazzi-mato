package it.polimi.se2018.client.graphic.alert_box;

import it.polimi.se2018.client.graphic.graphic_element.Utility;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import static it.polimi.se2018.client.graphic.graphic_element.Utility.configureImageView;
import static it.polimi.se2018.client.graphic.graphic_element.Utility.setFrameWindow;


/**
 * Classe AlertLoadingGame utilizzata per far rimanere in attesa i vari giocatori fino all'inizio della partita.
 *
 * @author Simone Lanzillotta
 */



public class AlertLoadingGame {

        private static Stage window;
        private AlertLoadingGame(){}


    /**
     * Metodo utlizzato per configurare la finestra di attesa dei giocatori per l'inizio della partita. Consente quindi la visualizzazione di una finestra PoPup dopo
     * la scelta del giocatore della carta Side con la quale partecipare alla sessione di gioco: le caratteristiche della finestra sono di non poter essere in alcun
     * modo chiusa volutamente dall'Utente, in questo modo si evitano alla radice problemi di invalidazione della scelta o la generazione di messaggi non previsti
     * (per esempio cambiare la scelta della carta Side una volta inviata la conferma sulla scelta precedente)
     *
     * @param title Titolo della finestra
     * @param message Intestazione della finestra
     */


    public static void display(String title,String message){

        //Configurazione della finestra
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.initStyle(StageStyle.TRANSPARENT);
        window.setTitle(title);
        window.setWidth(450);
        window.setHeight(250);

        //Configurazione Cornice della finestra
        StackPane stackLoading = new StackPane();
        stackLoading.getChildren().add(setFrameWindow(stackLoading));

        //Configurazione Body Content
        Label label = Utility.setFontStyle(new Label(message), 20);
        label.setTextAlignment(TextAlignment.CENTER);
        ImageView loading = configureImageView("","big-ajax-loader",".gif",50,50);

        HBox layuot = new HBox(10);
        layuot.getChildren().addAll(loading, label);
        layuot.setAlignment(Pos.CENTER);

        VBox finalLayout = new VBox(15);
        finalLayout.setAlignment(Pos.CENTER);
        finalLayout.setPadding(new Insets(50,50,50,50));
        finalLayout.getChildren().addAll(layuot);

        //Configurazione finale
        stackLoading.getChildren().addAll(finalLayout);
        Scene scene = new Scene(stackLoading);

        window.setScene(scene);
        window.showAndWait();
    }




    /**
     * Metodo richiamato dalla InitWindow dell'Utente non appena la partita ha inizio. Viene utilizzato per chiudere automaticamente la finestra di attesa
     *
     */

    public static void closeAlert(){
        window.close();
    }

}
