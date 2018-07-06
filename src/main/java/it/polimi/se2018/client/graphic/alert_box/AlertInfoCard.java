package it.polimi.se2018.client.graphic.alert_box;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import static it.polimi.se2018.client.graphic.graphic_element.Utility.*;
import static it.polimi.se2018.client.graphic.graphic_element.ButtonLabelCreator.*;

import java.util.*;


/**
 * Classe AlertInfoCard utilizzata per configurare la finestra dedicata alla consultazione delle carte utensili e Obbiettivo Pubblico disponibili per la partita.
 * La finestra viene mostrata non appena si clicca su qualsiasi carta all'interno dell'elemento grafico TabCardLabel.
 *
 * @author Simone Lanzillotta
 */



public class AlertInfoCard {

    private static final String EXTENSION = ".png";

    private static ArrayList<String> nameCard = new ArrayList<>();
    private static String infoData;

    private AlertInfoCard(){}



    /**
     * Metodo utilizzato per richiamare e configurare la schermata di consultazione per le carte Pubbliche.
     *
     * @param cardSelection Lista delle carte disponibili per il turno (Utensili o Obbiettivo Pubbliche)
     * @param path Percorso per l'importazione delle risorse
     */

    public static void display(List<String> cardSelection, String path){

        //Configurazione della finestra
        Stage window = new Stage();
        setDecoration(window,"Info Carte",650,770,15,15);
        window.initModality(Modality.APPLICATION_MODAL);
        window.setOnCloseRequest(Event::consume);

        ImageView back = configureImageView(path, "retro", EXTENSION,700,850);
        back.setOpacity(0.2);
        StackPane root = new StackPane(back);

        //Configuro la collezione riguardo nome della carta Formattato e immagine associata
        ArrayList<ImageView> imageCard = new ArrayList<>();
        ArrayList<Label> labelName = new ArrayList<>();

        for (String card: cardSelection) {
            imageCard.add(configureImageView(path, card, EXTENSION,300,450));
            String name = setUpperWord(card);
            Label item = setFontStyle(new Label(name), 40);
            nameCard.add(name);
            labelName.add(item);
        }


        //Inizializzo le schermate
        HBox view1 = new HBox(30);
        HBox view2 = new HBox(30);
        HBox view3 = new HBox(30);

        //Configurazione del contenuto delle schermate
        VBox labelView1 = setLabelView(labelName.get(0),view1,window);
        VBox labelView2 = setLabelView(labelName.get(1),view2,window);
        VBox labelView3 = setLabelView(labelName.get(2),view3,window);

        //Inizializzo la root Window con una delel schermate create
        root.getChildren().add(labelView1);

        //Configurazione delle schermate
        view1.getChildren().addAll(setOpacity(setRotation(180d),0.6),imageCard.get(0),shadowEffect(setActionOnImage(labelView2,labelView1,root,0d,false,null)));
        view1.setAlignment(Pos.CENTER);

        view2.getChildren().addAll(shadowEffect(setActionOnImage(labelView1,labelView2,root,180d,false,null)),imageCard.get(1),shadowEffect(setActionOnImage(labelView3,labelView2,root,0d,false,null)));
        view2.setAlignment(Pos.CENTER);

        view3.getChildren().addAll(shadowEffect(setActionOnImage(labelView2,labelView3,root,180d,false,null)),imageCard.get(2),setOpacity(setRotation(0d),0.6));
        view3.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root);
        window.setScene(scene);
        window.showAndWait();

    }



    /**
     * Metodo di supporto per la configurazione del contenuto delle schermate che riportano le informazioni sulle carte Pubbliche
     *
     * @param label Intestazione del contenuto della finestra
     * @param view Elemento grafico di contenimento
     * @param window Riferimento alla PrimaryStage della schermata
     * @return Elemento grafico configurato per essere inserito nella schermata
     */

    private static VBox setLabelView(Label label, HBox view, Stage window){
        VBox labelView = new VBox(25);
        labelView.setAlignment(Pos.CENTER);
        labelView.getChildren().addAll(label,view,setActionOnBack(window,180,100));
        return labelView;
    }



    /**
     * Metodo utilizzato per configurare l'effetto di transazione tra le varie schermate dell'infoAlert. La schermata infatti è composta da un'immagine (la carta stessa)
     * e da un pulsante posto su ambo i lati dell'immagine: cliccando sul bottone si attiva la transazione che porta nella nuova schermata, rendendo l'effetto scorrimento
     * desiderato.
     * Il metodo prevede lo stesso utilizzo anche nel caso di attivazione della carta Utensile 1, per cui sono stati opportunatamente inseriti due parametri di gestione.
     *
     * @param switchWindow Riferimento alla schermata di destinazione della transazione
     * @param actualWindow Riferimento all'attuale schermata
     * @param root Riferimento al Parent root che ospita gli elementi dinamici della schermata
     * @param rotate Valore di rotazione per l'immagine utilizzata come bottone
     * @param accessUtensil Booleano dal valore TRUE se il metodo viene acceduto per l'attivazione della carta Utensile 1, altrimenti FALSE
     * @param dieUtensil Riferimento all'immagine del dado visualizzato nella schermata
     * @return Riferimento all'elemento grafico Button
     */

    public static ImageView setActionOnImage(Node switchWindow, Node actualWindow, StackPane root, Double rotate, Boolean accessUtensil, ImageView dieUtensil){
        ImageView button = new ImageView(new Image("iconPack/icon-next.png",80,80,false,true));
        button.setRotate(rotate);
        button.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            root.getChildren().add(switchWindow);
            double width = root.getWidth();
            KeyFrame start = new KeyFrame(Duration.ZERO,
                    new KeyValue(switchWindow.translateXProperty(), width),
                    new KeyValue(actualWindow.translateXProperty(), 0));
            KeyFrame end = new KeyFrame(Duration.millis(0.1),
                    new KeyValue(switchWindow.translateXProperty(), 0),
                    new KeyValue(actualWindow.translateXProperty(), -width));

            if(accessUtensil) infoData = dieUtensil.getUserData().toString();
            Timeline slide = new Timeline(start, end);
            slide.setOnFinished(ex -> root.getChildren().remove(actualWindow));
            slide.play();
        });
        return button;
    }



    /**
     * Metodo utilizzato per applicare l'effetto Opacity su una determinata immagine.
     *
     * @param node Riferimento all'elemento grafico contenente l'immagine a cui applicare l'effetto
     * @param opacity Valore di intensità dell'effetto
     * @return Riferimento all'elemento grafico contenente l'immagine modificata
     */

    public static ImageView setOpacity(ImageView node, Double opacity){
        node.setOpacity(opacity);
        return node;
    }



    /**
     * Metodo utilizzato per applicare una rotazione su una determinata immagine.
     *
     * @param grade Valore di rotazione
     * @return Riferimento all'elemento grafico contenente l'immagine modificata
     */

    public static ImageView setRotation(Double grade){
        ImageView button = new ImageView(new Image("iconPack/icon-next.png",80,80,false,true));
        button.setRotate(grade);
        return button;
    }


    /**
     * Metodo di configuraione dellázione associata al BackButton
     *
     * @param window Riferimento alal finestra a cui è destinata l'azione del BackButton
     * @param requestWidth Larghezza del bottone "Back"
     * @param requestHeight Altezza del bottone "Back"
     * @return Immagine relativa al BackButton opportunamente configurato
     */

    private static ImageView setActionOnBack(Stage window,int requestWidth, int requestHeight){
        ImageView backButton = getBackButton(requestWidth,requestHeight);
        backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> window.close());
        return backButton;
    }


    /**
     * Metodo utilizzato per prelevare l'informazione del dado scelto a seguito dell'utilizzo della carta Utensile 1
     *
     * @return Riferimento all'attributo infoData
     */

    public static String getInfoData() {
        return infoData;
    }
}