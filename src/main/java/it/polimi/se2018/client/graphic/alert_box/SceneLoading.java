package it.polimi.se2018.client.graphic.alert_box;

import it.polimi.se2018.client.graphic.graphic_element.ButtonLabelCreator;
import it.polimi.se2018.client.message.ClientMessageCreator;
import it.polimi.se2018.client.graphic.InitWindow;
import it.polimi.se2018.client.graphic.graphic_element.Utility;
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

import static it.polimi.se2018.client.graphic.graphic_element.ButtonLabelCreator.*;


/**
 * Classe SceneLoading che gestisce la fase di attesa dell'utente in seguito al corretto conseguimento della fase di connessione.
 *
 * @author Simone Lanzillotta
 */



public class SceneLoading {

    private Scene sceneLoading;


    /**
     * Costruttore della classe utilizzato direttamente per la configurazione
     *
     * @param window Riferimento alla Primary Stage dell'interfaccia Utente
     * @param init Riferimento all'oggetto InitWindow rappresentante dell'interfaccia Utente
     */

    public SceneLoading(Stage window, InitWindow init){

        //Configurazione del contenuto della finestra
        Label label = Utility.setFontStyle(new Label("Sei stato messo in attesa..."),25);
        ImageView loading = new ImageView(new Image("big-ajax-loader.gif",50,50,false,true));

        HBox layuot = new HBox(10);
        layuot.getChildren().addAll(loading, label);
        layuot.setAlignment(Pos.CENTER);

        VBox finalLayout = new VBox(15);
        finalLayout.setAlignment(Pos.CENTER);
        finalLayout.setPadding(new Insets(50,50,50,50));

        //Configurazione del BackBotton
        ImageView backButton = getBackButton(138,71);
        backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            init.getConnectionHandler().sendToServer(ClientMessageCreator.getDisconnectMessage(init.getConnectionHandler().getNickname()));
            Platform.runLater(() -> window.setScene(AlertSwitcher.getSceneNickName()));

        });
        finalLayout.getChildren().addAll(layuot,backButton);

        sceneLoading = new Scene(finalLayout,550,500);
    }


    /**
     * Metodo Getter per restituire la scena configurata, utiilizzato per l'effetto Switcher
     *
     * @return Riferimento alla scena sceneLoading
     */

    public Scene getSceneLoading() {
        return sceneLoading;
    }

}
